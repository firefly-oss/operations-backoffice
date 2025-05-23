package com.vaadin.starter.business.ui.views.documentmanagement;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.business.backend.dto.documentmanagement.AuthorizationDTO;
import com.vaadin.starter.business.backend.dto.documentmanagement.SignatureDTO;
import com.vaadin.starter.business.backend.mapper.AuthorizationMapper;
import com.vaadin.starter.business.backend.mapper.SignatureMapper;
import com.vaadin.starter.business.backend.service.DocumentService;
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.components.Badge;
import com.vaadin.starter.business.ui.components.FlexBoxLayout;
import com.vaadin.starter.business.ui.constants.NavigationConstants;
import com.vaadin.starter.business.ui.layout.size.Horizontal;
import com.vaadin.starter.business.ui.layout.size.Top;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.util.css.BoxSizing;
import com.vaadin.starter.business.ui.util.css.lumo.BadgeColor;
import com.vaadin.starter.business.ui.util.css.lumo.BadgeShape;
import com.vaadin.starter.business.ui.util.css.lumo.BadgeSize;
import com.vaadin.starter.business.ui.views.ViewFrame;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle(NavigationConstants.SIGNATURES_AUTHORIZATIONS)
@Route(value = "document-management/signatures-authorizations", layout = MainLayout.class)
public class SignaturesAuthorizations extends ViewFrame {

    private Grid<Signature> signaturesGrid;
    private Grid<Authorization> authorizationsGrid;
    private ListDataProvider<Signature> signaturesDataProvider;
    private ListDataProvider<Authorization> authorizationsDataProvider;
    private Div contentArea;

    private final DocumentService documentService;
    private final SignatureMapper signatureMapper;
    private final AuthorizationMapper authorizationMapper;

    @Autowired
    public SignaturesAuthorizations(DocumentService documentService, SignatureMapper signatureMapper, AuthorizationMapper authorizationMapper) {
        this.documentService = documentService;
        this.signatureMapper = signatureMapper;
        this.authorizationMapper = authorizationMapper;
        setViewContent(createContent());
    }

    private Component createContent() {
        FlexBoxLayout content = new FlexBoxLayout(createHeader(), createTabs());
        content.setBoxSizing(BoxSizing.BORDER_BOX);
        content.setHeightFull();
        content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);
        content.setFlexDirection(FlexDirection.COLUMN);
        return content;
    }


    private Component createHeader() {
        H3 header = new H3("Signatures & Authorizations");
        header.getStyle().set("margin", "0");
        header.getStyle().set("padding", "1rem");
        return header;
    }

    private Component createTabs() {
        Tab signaturesTab = new Tab("Signatures");
        Tab authorizationsTab = new Tab("Authorizations");
        Tab templatesTab = new Tab("Templates");
        Tab settingsTab = new Tab("Settings");

        Tabs tabs = new Tabs(signaturesTab, authorizationsTab, templatesTab, settingsTab);
        tabs.getStyle().set("margin", "0 1rem");

        contentArea = new Div();
        contentArea.setSizeFull();
        contentArea.getStyle().set("padding", "1rem");

        // Show signatures tab by default
        showSignaturesTab();

        tabs.addSelectedChangeListener(event -> {
            contentArea.removeAll();
            if (event.getSelectedTab().equals(signaturesTab)) {
                showSignaturesTab();
            } else if (event.getSelectedTab().equals(authorizationsTab)) {
                showAuthorizationsTab();
            } else if (event.getSelectedTab().equals(templatesTab)) {
                showTemplatesTab();
            } else if (event.getSelectedTab().equals(settingsTab)) {
                showSettingsTab();
            }
        });

        VerticalLayout tabsLayout = new VerticalLayout(tabs, contentArea);
        tabsLayout.setPadding(false);
        tabsLayout.setSpacing(false);
        tabsLayout.setSizeFull();
        return tabsLayout;
    }

    private void showSignaturesTab() {
        VerticalLayout signaturesLayout = new VerticalLayout();
        signaturesLayout.setPadding(false);
        signaturesLayout.setSpacing(true);
        signaturesLayout.setSizeFull();

        // Add filter form
        signaturesLayout.add(createSignaturesFilterForm());

        // Add grid
        signaturesGrid = createSignaturesGrid();
        signaturesLayout.add(signaturesGrid);
        signaturesLayout.expand(signaturesGrid);

        // Add action buttons
        HorizontalLayout actionButtons = new HorizontalLayout();
        Button addSignatureButton = UIUtils.createPrimaryButton("Add Signature");
        Button importSignaturesButton = UIUtils.createTertiaryButton("Import Signatures");
        Button exportSignaturesButton = UIUtils.createTertiaryButton("Export Signatures");
        actionButtons.add(addSignatureButton, importSignaturesButton, exportSignaturesButton);
        actionButtons.setSpacing(true);
        signaturesLayout.add(actionButtons);

        contentArea.add(signaturesLayout);
    }

    private void showAuthorizationsTab() {
        VerticalLayout authorizationsLayout = new VerticalLayout();
        authorizationsLayout.setPadding(false);
        authorizationsLayout.setSpacing(true);
        authorizationsLayout.setSizeFull();

        // Add filter form
        authorizationsLayout.add(createAuthorizationsFilterForm());

        // Add grid
        authorizationsGrid = createAuthorizationsGrid();
        authorizationsLayout.add(authorizationsGrid);
        authorizationsLayout.expand(authorizationsGrid);

        // Add action buttons
        HorizontalLayout actionButtons = new HorizontalLayout();
        Button addAuthorizationButton = UIUtils.createPrimaryButton("Add Authorization");
        Button importAuthorizationsButton = UIUtils.createTertiaryButton("Import Authorizations");
        Button exportAuthorizationsButton = UIUtils.createTertiaryButton("Export Authorizations");
        actionButtons.add(addAuthorizationButton, importAuthorizationsButton, exportAuthorizationsButton);
        actionButtons.setSpacing(true);
        authorizationsLayout.add(actionButtons);

        contentArea.add(authorizationsLayout);
    }

    private void showTemplatesTab() {
        VerticalLayout templatesLayout = new VerticalLayout();
        templatesLayout.setPadding(false);
        templatesLayout.setSpacing(true);
        templatesLayout.setSizeFull();

        // Create template cards
        HorizontalLayout templateCards = new HorizontalLayout();
        templateCards.setWidthFull();
        templateCards.setSpacing(true);

        templateCards.add(createTemplateCard("Standard Signature", "Default signature template for individual customers", "template_standard.png"));
        templateCards.add(createTemplateCard("Joint Account", "Signature template for joint account holders", "template_joint.png"));
        templateCards.add(createTemplateCard("Corporate Authorization", "Authorization template for corporate entities", "template_corporate.png"));
        templateCards.add(createTemplateCard("Power of Attorney", "Template for power of attorney authorizations", "template_poa.png"));

        templatesLayout.add(templateCards);

        // Add action buttons
        HorizontalLayout actionButtons = new HorizontalLayout();
        Button createTemplateButton = UIUtils.createPrimaryButton("Create Template");
        Button importTemplateButton = UIUtils.createTertiaryButton("Import Template");
        actionButtons.add(createTemplateButton, importTemplateButton);
        actionButtons.setSpacing(true);
        templatesLayout.add(actionButtons);

        contentArea.add(templatesLayout);
    }

    private void showSettingsTab() {
        FormLayout settingsForm = new FormLayout();
        settingsForm.setResponsiveSteps(
            new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
            new FormLayout.ResponsiveStep("600px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP)
        );

        ComboBox<String> defaultSignatureType = new ComboBox<>("Default Signature Type");
        defaultSignatureType.setItems("Digital", "Electronic", "Handwritten");
        defaultSignatureType.setValue("Digital");

        ComboBox<String> signatureValidityPeriod = new ComboBox<>("Signature Validity Period");
        signatureValidityPeriod.setItems("1 Year", "2 Years", "3 Years", "5 Years");
        signatureValidityPeriod.setValue("2 Years");

        ComboBox<String> authorizationWorkflow = new ComboBox<>("Authorization Workflow");
        authorizationWorkflow.setItems("Standard", "Enhanced", "Strict");
        authorizationWorkflow.setValue("Standard");

        ComboBox<String> verificationMethod = new ComboBox<>("Verification Method");
        verificationMethod.setItems("Visual", "Biometric", "Multi-factor");
        verificationMethod.setValue("Visual");

        TextField emailNotificationRecipients = new TextField("Email Notification Recipients");
        emailNotificationRecipients.setValue("operations@example.com");
        emailNotificationRecipients.setWidthFull();

        settingsForm.add(defaultSignatureType, signatureValidityPeriod, authorizationWorkflow, 
                         verificationMethod, emailNotificationRecipients);

        // Add action buttons
        HorizontalLayout actionButtons = new HorizontalLayout();
        Button saveSettingsButton = UIUtils.createPrimaryButton("Save Settings");
        Button resetSettingsButton = UIUtils.createTertiaryButton("Reset to Defaults");
        actionButtons.add(saveSettingsButton, resetSettingsButton);
        actionButtons.setSpacing(true);

        VerticalLayout settingsLayout = new VerticalLayout(settingsForm, actionButtons);
        settingsLayout.setPadding(true);
        settingsLayout.setSpacing(true);

        contentArea.add(settingsLayout);
    }

    private Component createTemplateCard(String title, String description, String imageName) {
        Div card = new Div();
        card.getStyle().set("background-color", "var(--lumo-base-color)");
        card.getStyle().set("border-radius", "var(--lumo-border-radius)");
        card.getStyle().set("box-shadow", "0 0 0 1px var(--lumo-contrast-10pct)");
        card.getStyle().set("padding", "1rem");
        card.getStyle().set("width", "250px");
        card.getStyle().set("height", "300px");
        card.getStyle().set("display", "flex");
        card.getStyle().set("flex-direction", "column");

        H3 titleElement = new H3(title);
        titleElement.getStyle().set("margin-top", "0");
        titleElement.getStyle().set("margin-bottom", "0.5rem");

        Span descriptionElement = new Span(description);
        descriptionElement.getStyle().set("color", "var(--lumo-secondary-text-color)");
        descriptionElement.getStyle().set("margin-bottom", "1rem");

        // Placeholder for template image
        Div imagePlaceholder = new Div();
        imagePlaceholder.getStyle().set("background-color", "var(--lumo-contrast-10pct)");
        imagePlaceholder.getStyle().set("border-radius", "var(--lumo-border-radius)");
        imagePlaceholder.getStyle().set("width", "100%");
        imagePlaceholder.getStyle().set("height", "150px");
        imagePlaceholder.getStyle().set("display", "flex");
        imagePlaceholder.getStyle().set("align-items", "center");
        imagePlaceholder.getStyle().set("justify-content", "center");

        Span imageText = new Span("Template Preview");
        imageText.getStyle().set("color", "var(--lumo-secondary-text-color)");
        imagePlaceholder.add(imageText);

        HorizontalLayout actions = new HorizontalLayout();
        Button editButton = UIUtils.createSmallButton("Edit");
        editButton.setIcon(VaadinIcon.EDIT.create());
        Button deleteButton = UIUtils.createSmallButton("Delete");
        deleteButton.setIcon(VaadinIcon.TRASH.create());
        actions.add(editButton, deleteButton);
        actions.setSpacing(true);
        actions.getStyle().set("margin-top", "auto");

        card.add(titleElement, descriptionElement, imagePlaceholder, actions);
        return card;
    }

    private Component createSignaturesFilterForm() {
        // Initialize filter fields
        TextField customerIdFilter = new TextField();
        customerIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        customerIdFilter.setClearButtonVisible(true);

        TextField customerNameFilter = new TextField();
        customerNameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        customerNameFilter.setClearButtonVisible(true);

        ComboBox<String> signatureTypeFilter = new ComboBox<>();
        signatureTypeFilter.setItems("Digital", "Electronic", "Handwritten");
        signatureTypeFilter.setClearButtonVisible(true);

        ComboBox<String> statusFilter = new ComboBox<>();
        statusFilter.setItems("Active", "Expired", "Revoked", "Pending Verification");
        statusFilter.setClearButtonVisible(true);

        DatePicker creationDateFromFilter = new DatePicker();
        creationDateFromFilter.setClearButtonVisible(true);

        DatePicker creationDateToFilter = new DatePicker();
        creationDateToFilter.setClearButtonVisible(true);

        // Create buttons
        Button searchButton = UIUtils.createPrimaryButton("Search");
        Button clearButton = UIUtils.createTertiaryButton("Clear");

        // Create button layout
        HorizontalLayout buttonLayout = new HorizontalLayout(searchButton, clearButton);
        buttonLayout.setSpacing(true);

        // Create form layout
        FormLayout formLayout = new FormLayout();
        formLayout.addFormItem(customerIdFilter, "Customer ID");
        formLayout.addFormItem(customerNameFilter, "Customer Name");
        formLayout.addFormItem(signatureTypeFilter, "Signature Type");
        formLayout.addFormItem(statusFilter, "Status");
        formLayout.addFormItem(creationDateFromFilter, "Creation Date From");
        formLayout.addFormItem(creationDateToFilter, "Creation Date To");

        formLayout.setResponsiveSteps(
            new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
            new FormLayout.ResponsiveStep("600px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP),
            new FormLayout.ResponsiveStep("900px", 3, FormLayout.ResponsiveStep.LabelsPosition.TOP)
        );

        // Create a container for the form and buttons
        Div formContainer = new Div(formLayout, buttonLayout);
        formContainer.getStyle().set("padding", "1em");
        formContainer.getStyle().set("box-shadow", "0 0 0 1px var(--lumo-contrast-10pct)");
        formContainer.getStyle().set("border-radius", "var(--lumo-border-radius)");
        formContainer.getStyle().set("background-color", "var(--lumo-base-color)");
        formContainer.getStyle().set("margin-bottom", "1em");

        return formContainer;
    }

    private Component createAuthorizationsFilterForm() {
        // Initialize filter fields
        TextField authorizationIdFilter = new TextField();
        authorizationIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        authorizationIdFilter.setClearButtonVisible(true);

        TextField customerIdFilter = new TextField();
        customerIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        customerIdFilter.setClearButtonVisible(true);

        ComboBox<String> authorizationTypeFilter = new ComboBox<>();
        authorizationTypeFilter.setItems("Account Access", "Transaction Approval", "Document Signing", "Power of Attorney");
        authorizationTypeFilter.setClearButtonVisible(true);

        ComboBox<String> statusFilter = new ComboBox<>();
        statusFilter.setItems("Active", "Expired", "Revoked", "Pending Approval");
        statusFilter.setClearButtonVisible(true);

        DatePicker creationDateFromFilter = new DatePicker();
        creationDateFromFilter.setClearButtonVisible(true);

        DatePicker creationDateToFilter = new DatePicker();
        creationDateToFilter.setClearButtonVisible(true);

        // Create buttons
        Button searchButton = UIUtils.createPrimaryButton("Search");
        Button clearButton = UIUtils.createTertiaryButton("Clear");

        // Create button layout
        HorizontalLayout buttonLayout = new HorizontalLayout(searchButton, clearButton);
        buttonLayout.setSpacing(true);

        // Create form layout
        FormLayout formLayout = new FormLayout();
        formLayout.addFormItem(authorizationIdFilter, "Authorization ID");
        formLayout.addFormItem(customerIdFilter, "Customer ID");
        formLayout.addFormItem(authorizationTypeFilter, "Authorization Type");
        formLayout.addFormItem(statusFilter, "Status");
        formLayout.addFormItem(creationDateFromFilter, "Creation Date From");
        formLayout.addFormItem(creationDateToFilter, "Creation Date To");

        formLayout.setResponsiveSteps(
            new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
            new FormLayout.ResponsiveStep("600px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP),
            new FormLayout.ResponsiveStep("900px", 3, FormLayout.ResponsiveStep.LabelsPosition.TOP)
        );

        // Create a container for the form and buttons
        Div formContainer = new Div(formLayout, buttonLayout);
        formContainer.getStyle().set("padding", "1em");
        formContainer.getStyle().set("box-shadow", "0 0 0 1px var(--lumo-contrast-10pct)");
        formContainer.getStyle().set("border-radius", "var(--lumo-border-radius)");
        formContainer.getStyle().set("background-color", "var(--lumo-base-color)");
        formContainer.getStyle().set("margin-bottom", "1em");

        return formContainer;
    }

    private Grid<Signature> createSignaturesGrid() {
        Grid<Signature> grid = new Grid<>();

        // Initialize data provider with data from service
        Collection<SignatureDTO> signaturesDTO = documentService.getSignatures();
        Collection<Signature> signatures = signatureMapper.toEntityList(signaturesDTO);
        signaturesDataProvider = new ListDataProvider<>(signatures);
        grid.setDataProvider(signaturesDataProvider);

        grid.setId("signatures");
        grid.setSizeFull();

        // Add columns
        grid.addColumn(Signature::getSignatureId)
                .setHeader("Signature ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(Signature::getCustomerId)
                .setHeader("Customer ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(Signature::getCustomerName)
                .setHeader("Customer Name")
                .setSortable(true)
                .setWidth("180px");
        grid.addColumn(Signature::getSignatureType)
                .setHeader("Signature Type")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(signature -> createStatusBadge(signature.getStatus()))
                .setHeader("Status")
                .setSortable(true)
                .setComparator(Signature::getStatus)
                .setWidth("150px");
        grid.addColumn(signature -> {
                    if (signature.getCreationDate() != null) {
                        return signature.getCreationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    }
                    return "";
                })
                .setHeader("Creation Date")
                .setSortable(true)
                .setComparator(Signature::getCreationDate)
                .setWidth("150px");
        grid.addColumn(signature -> {
                    if (signature.getExpiryDate() != null) {
                        return signature.getExpiryDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    }
                    return "";
                })
                .setHeader("Expiry Date")
                .setSortable(true)
                .setComparator(Signature::getExpiryDate)
                .setWidth("150px");
        grid.addColumn(Signature::getVerifiedBy)
                .setHeader("Verified By")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(Signature::getNotes)
                .setHeader("Notes")
                .setSortable(true)
                .setWidth("250px");

        return grid;
    }

    private Grid<Authorization> createAuthorizationsGrid() {
        Grid<Authorization> grid = new Grid<>();

        // Initialize data provider with data from service
        Collection<AuthorizationDTO> authorizationsDTO = documentService.getAuthorizations();
        Collection<Authorization> authorizations = authorizationMapper.toEntityList(authorizationsDTO);
        authorizationsDataProvider = new ListDataProvider<>(authorizations);
        grid.setDataProvider(authorizationsDataProvider);

        grid.setId("authorizations");
        grid.setSizeFull();

        // Add columns
        grid.addColumn(Authorization::getAuthorizationId)
                .setHeader("Authorization ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(Authorization::getCustomerId)
                .setHeader("Customer ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(Authorization::getCustomerName)
                .setHeader("Customer Name")
                .setSortable(true)
                .setWidth("180px");
        grid.addColumn(Authorization::getAuthorizationType)
                .setHeader("Authorization Type")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(authorization -> createStatusBadge(authorization.getStatus()))
                .setHeader("Status")
                .setSortable(true)
                .setComparator(Authorization::getStatus)
                .setWidth("150px");
        grid.addColumn(Authorization::getAuthorizedTo)
                .setHeader("Authorized To")
                .setSortable(true)
                .setWidth("180px");
        grid.addColumn(authorization -> {
                    if (authorization.getCreationDate() != null) {
                        return authorization.getCreationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    }
                    return "";
                })
                .setHeader("Creation Date")
                .setSortable(true)
                .setComparator(Authorization::getCreationDate)
                .setWidth("150px");
        grid.addColumn(authorization -> {
                    if (authorization.getExpiryDate() != null) {
                        return authorization.getExpiryDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    }
                    return "";
                })
                .setHeader("Expiry Date")
                .setSortable(true)
                .setComparator(Authorization::getExpiryDate)
                .setWidth("150px");
        grid.addColumn(Authorization::getApprovedBy)
                .setHeader("Approved By")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(Authorization::getNotes)
                .setHeader("Notes")
                .setSortable(true)
                .setWidth("250px");

        return grid;
    }

    private Component createStatusBadge(String status) {
        BadgeColor color;
        switch (status) {
            case "Active":
                color = BadgeColor.SUCCESS;
                break;
            case "Expired":
                color = BadgeColor.ERROR_PRIMARY;
                break;
            case "Revoked":
                color = BadgeColor.ERROR;
                break;
            case "Pending Verification":
            case "Pending Approval":
                color = BadgeColor.CONTRAST;
                break;
            default:
                color = BadgeColor.NORMAL;
        }

        return new Badge(status, color, BadgeSize.S, BadgeShape.PILL);
    }

    private Collection<Signature> generateMockSignatures() {
        List<Signature> signatures = new ArrayList<>();
        Random random = new Random(42); // Fixed seed for reproducible data

        String[] customerIds = {"C10045", "C20056", "C30078", "C40023", "C50091", "C60112", "C70134", "C80156"};
        String[] customerNames = {
            "John Smith", 
            "Maria Garcia", 
            "Wei Chen", 
            "Sarah Johnson", 
            "Ahmed Hassan",
            "Olivia Wilson",
            "Michael Brown",
            "Fatima Al-Farsi"
        };

        String[] signatureTypes = {"Digital", "Electronic", "Handwritten"};
        String[] statuses = {"Active", "Expired", "Revoked", "Pending Verification"};

        String[] verifiedBy = {
            "John Smith", 
            "Maria Rodriguez", 
            "Wei Zhang", 
            "Sarah Johnson", 
            "Ahmed Hassan",
            "Olivia Wilson",
            "Michael Brown",
            "Fatima Al-Farsi"
        };

        String[] notes = {
            "Standard signature for account operations",
            "Signature verified with government ID",
            "Signature requires renewal",
            "Signature captured during branch visit",
            "Digital signature with certificate",
            "Signature pending verification by branch manager",
            "Signature for joint account operations",
            "Signature captured via mobile app"
        };

        for (int i = 1; i <= 20; i++) {
            Signature signature = new Signature();
            signature.setSignatureId("SIG" + String.format("%06d", i));

            int customerIndex = random.nextInt(customerIds.length);
            signature.setCustomerId(customerIds[customerIndex]);
            signature.setCustomerName(customerNames[customerIndex]);

            signature.setSignatureType(signatureTypes[random.nextInt(signatureTypes.length)]);
            signature.setStatus(statuses[random.nextInt(statuses.length)]);

            // Generate creation date within the last 2 years
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime creationDate = now.minusDays(random.nextInt(730));
            signature.setCreationDate(creationDate);

            // Generate expiry date 2-5 years after creation
            LocalDateTime expiryDate = creationDate.plusDays(730 + random.nextInt(1095));
            signature.setExpiryDate(expiryDate);

            signature.setVerifiedBy(verifiedBy[random.nextInt(verifiedBy.length)]);
            signature.setNotes(notes[random.nextInt(notes.length)]);

            signatures.add(signature);
        }

        return signatures;
    }

    private Collection<Authorization> generateMockAuthorizations() {
        List<Authorization> authorizations = new ArrayList<>();
        Random random = new Random(42); // Fixed seed for reproducible data

        String[] customerIds = {"C10045", "C20056", "C30078", "C40023", "C50091", "C60112", "C70134", "C80156"};
        String[] customerNames = {
            "John Smith", 
            "Maria Garcia", 
            "Wei Chen", 
            "Sarah Johnson", 
            "Ahmed Hassan",
            "Olivia Wilson",
            "Michael Brown",
            "Fatima Al-Farsi"
        };

        String[] authorizationTypes = {"Account Access", "Transaction Approval", "Document Signing", "Power of Attorney"};
        String[] statuses = {"Active", "Expired", "Revoked", "Pending Approval"};

        String[] authorizedTo = {
            "Jane Smith", 
            "Carlos Garcia", 
            "Li Chen", 
            "Thomas Johnson", 
            "Aisha Hassan",
            "James Wilson",
            "Emily Brown",
            "Khalid Al-Farsi"
        };

        String[] approvedBy = {
            "John Smith", 
            "Maria Rodriguez", 
            "Wei Zhang", 
            "Sarah Johnson", 
            "Ahmed Hassan",
            "Olivia Wilson",
            "Michael Brown",
            "Fatima Al-Farsi"
        };

        String[] notes = {
            "Limited account access authorization",
            "Full transaction approval rights",
            "Authorization for specific document types only",
            "Power of attorney for all banking operations",
            "Authorization limited to specific account",
            "Temporary authorization during customer absence",
            "Authorization for corporate account operations",
            "Limited time authorization for specific purpose"
        };

        for (int i = 1; i <= 20; i++) {
            Authorization authorization = new Authorization();
            authorization.setAuthorizationId("AUTH" + String.format("%06d", i));

            int customerIndex = random.nextInt(customerIds.length);
            authorization.setCustomerId(customerIds[customerIndex]);
            authorization.setCustomerName(customerNames[customerIndex]);

            authorization.setAuthorizationType(authorizationTypes[random.nextInt(authorizationTypes.length)]);
            authorization.setStatus(statuses[random.nextInt(statuses.length)]);
            authorization.setAuthorizedTo(authorizedTo[random.nextInt(authorizedTo.length)]);

            // Generate creation date within the last 2 years
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime creationDate = now.minusDays(random.nextInt(730));
            authorization.setCreationDate(creationDate);

            // Generate expiry date 1-3 years after creation
            LocalDateTime expiryDate = creationDate.plusDays(365 + random.nextInt(730));
            authorization.setExpiryDate(expiryDate);

            authorization.setApprovedBy(approvedBy[random.nextInt(approvedBy.length)]);
            authorization.setNotes(notes[random.nextInt(notes.length)]);

            authorizations.add(authorization);
        }

        return authorizations;
    }

    // Inner class to represent a signature
    public static class Signature {
        private String signatureId;
        private String customerId;
        private String customerName;
        private String signatureType;
        private String status;
        private LocalDateTime creationDate;
        private LocalDateTime expiryDate;
        private String verifiedBy;
        private String notes;

        public String getSignatureId() {
            return signatureId;
        }

        public void setSignatureId(String signatureId) {
            this.signatureId = signatureId;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getSignatureType() {
            return signatureType;
        }

        public void setSignatureType(String signatureType) {
            this.signatureType = signatureType;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public LocalDateTime getCreationDate() {
            return creationDate;
        }

        public void setCreationDate(LocalDateTime creationDate) {
            this.creationDate = creationDate;
        }

        public LocalDateTime getExpiryDate() {
            return expiryDate;
        }

        public void setExpiryDate(LocalDateTime expiryDate) {
            this.expiryDate = expiryDate;
        }

        public String getVerifiedBy() {
            return verifiedBy;
        }

        public void setVerifiedBy(String verifiedBy) {
            this.verifiedBy = verifiedBy;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }
    }

    // Inner class to represent an authorization
    public static class Authorization {
        private String authorizationId;
        private String customerId;
        private String customerName;
        private String authorizationType;
        private String status;
        private String authorizedTo;
        private LocalDateTime creationDate;
        private LocalDateTime expiryDate;
        private String approvedBy;
        private String notes;

        public String getAuthorizationId() {
            return authorizationId;
        }

        public void setAuthorizationId(String authorizationId) {
            this.authorizationId = authorizationId;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getAuthorizationType() {
            return authorizationType;
        }

        public void setAuthorizationType(String authorizationType) {
            this.authorizationType = authorizationType;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAuthorizedTo() {
            return authorizedTo;
        }

        public void setAuthorizedTo(String authorizedTo) {
            this.authorizedTo = authorizedTo;
        }

        public LocalDateTime getCreationDate() {
            return creationDate;
        }

        public void setCreationDate(LocalDateTime creationDate) {
            this.creationDate = creationDate;
        }

        public LocalDateTime getExpiryDate() {
            return expiryDate;
        }

        public void setExpiryDate(LocalDateTime expiryDate) {
            this.expiryDate = expiryDate;
        }

        public String getApprovedBy() {
            return approvedBy;
        }

        public void setApprovedBy(String approvedBy) {
            this.approvedBy = approvedBy;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }
    }
}
