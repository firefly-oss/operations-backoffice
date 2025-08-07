package com.vaadin.starter.business.ui.views.clients;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.components.Badge;
import com.vaadin.starter.business.ui.components.FlexBoxLayout;
import com.vaadin.starter.business.ui.constants.NavigationConstants;
import com.vaadin.starter.business.ui.layout.size.Horizontal;
import com.vaadin.starter.business.ui.layout.size.Top;
import com.vaadin.starter.business.ui.util.TextColor;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.util.css.BoxSizing;
import com.vaadin.starter.business.ui.util.css.lumo.BadgeColor;
import com.vaadin.starter.business.ui.util.css.lumo.BadgeShape;
import com.vaadin.starter.business.ui.util.css.lumo.BadgeSize;
import com.vaadin.starter.business.ui.views.ViewFrame;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@PageTitle(NavigationConstants.AML_KYC_CASES)
@Route(value = "clients/aml-kyc", layout = MainLayout.class)
public class AmlKycCases extends ViewFrame {

    private Grid<AmlCase> amlCasesGrid;
    private Grid<KycCase> kycCasesGrid;
    private ListDataProvider<AmlCase> amlCasesDataProvider;
    private ListDataProvider<KycCase> kycCasesDataProvider;
    private Div contentArea;

    public AmlKycCases() {
        setViewContent(createContent());
    }

    private Component createContent() {
        FlexBoxLayout content = new FlexBoxLayout(createTabs());
        content.setBoxSizing(BoxSizing.BORDER_BOX);
        content.setHeightFull();
        content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);
        content.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        return content;
    }

    private Component createHeader() {
        H2 header = new H2(NavigationConstants.AML_KYC_CASES);
        header.getStyle().set("margin", "0");
        header.getStyle().set("padding", "1rem");
        return header;
    }

    private Component createTabs() {
        Tab amlCasesTab = new Tab("AML Cases");
        Tab kycCasesTab = new Tab("KYC Cases");

        Tabs tabs = new Tabs(amlCasesTab, kycCasesTab);
        tabs.getStyle().set("margin", "0 1rem");
        // Add spacing between tabs
        amlCasesTab.getStyle().set("padding", "0 1rem");
        kycCasesTab.getStyle().set("padding", "0 1rem");

        contentArea = new Div();
        contentArea.setSizeFull();
        contentArea.getStyle().set("padding", "1rem");

        // Show AML Cases tab by default
        showAmlCasesTab();

        tabs.addSelectedChangeListener(event -> {
            contentArea.removeAll();
            if (event.getSelectedTab().equals(amlCasesTab)) {
                showAmlCasesTab();
            } else if (event.getSelectedTab().equals(kycCasesTab)) {
                showKycCasesTab();
            }
        });

        VerticalLayout tabsLayout = new VerticalLayout(tabs, contentArea);
        tabsLayout.setPadding(false);
        tabsLayout.setSpacing(false);
        tabsLayout.setSizeFull();
        return tabsLayout;
    }

    private void showAmlCasesTab() {
        VerticalLayout amlCasesLayout = new VerticalLayout();
        amlCasesLayout.setPadding(false);
        amlCasesLayout.setSpacing(true);
        amlCasesLayout.setSizeFull();

        // Add filter form
        amlCasesLayout.add(createAmlCasesFilterForm());

        // Add grid
        amlCasesGrid = createAmlCasesGrid();
        amlCasesLayout.add(amlCasesGrid);
        amlCasesLayout.expand(amlCasesGrid);

        contentArea.add(amlCasesLayout);
    }

    private void showKycCasesTab() {
        VerticalLayout kycCasesLayout = new VerticalLayout();
        kycCasesLayout.setPadding(false);
        kycCasesLayout.setSpacing(true);
        kycCasesLayout.setSizeFull();

        // Add filter form
        kycCasesLayout.add(createKycCasesFilterForm());

        // Add grid
        kycCasesGrid = createKycCasesGrid();
        kycCasesLayout.add(kycCasesGrid);
        kycCasesLayout.expand(kycCasesGrid);

        contentArea.add(kycCasesLayout);
    }

    private Component createAmlCasesFilterForm() {
        // Initialize filter fields
        TextField caseIdFilter = new TextField();
        caseIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        caseIdFilter.setClearButtonVisible(true);

        TextField customerIdFilter = new TextField();
        customerIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        customerIdFilter.setClearButtonVisible(true);

        ComboBox<String> caseTypeFilter = new ComboBox<>();
        caseTypeFilter.setItems("Transaction Monitoring", "Suspicious Activity", "Regulatory Alert", "Internal Investigation");
        caseTypeFilter.setClearButtonVisible(true);

        ComboBox<String> statusFilter = new ComboBox<>();
        statusFilter.setItems("Open", "Under Investigation", "Pending Review", "Closed", "Escalated");
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
        formLayout.addFormItem(caseIdFilter, "Case ID");
        formLayout.addFormItem(customerIdFilter, "Customer ID");
        formLayout.addFormItem(caseTypeFilter, "Case Type");
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

    private Component createKycCasesFilterForm() {
        // Initialize filter fields
        TextField caseIdFilter = new TextField();
        caseIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        caseIdFilter.setClearButtonVisible(true);

        TextField customerIdFilter = new TextField();
        customerIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        customerIdFilter.setClearButtonVisible(true);

        ComboBox<String> caseTypeFilter = new ComboBox<>();
        caseTypeFilter.setItems("Identity Verification", "Document Validation", "Enhanced Due Diligence", "Periodic Review");
        caseTypeFilter.setClearButtonVisible(true);

        ComboBox<String> statusFilter = new ComboBox<>();
        statusFilter.setItems("Pending", "In Progress", "Completed", "Rejected", "Additional Info Required");
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
        formLayout.addFormItem(caseIdFilter, "Case ID");
        formLayout.addFormItem(customerIdFilter, "Customer ID");
        formLayout.addFormItem(caseTypeFilter, "Case Type");
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

    private Grid<AmlCase> createAmlCasesGrid() {
        Grid<AmlCase> grid = new Grid<>();

        // Initialize data provider with mock data
        List<AmlCase> amlCases = generateMockAmlCases();
        amlCasesDataProvider = new ListDataProvider<>(amlCases);
        grid.setDataProvider(amlCasesDataProvider);

        grid.setId("amlCases");
        grid.setSizeFull();

        // Add columns
        grid.addColumn(AmlCase::getCaseId)
                .setHeader("Case ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(AmlCase::getCustomerId)
                .setHeader("Customer ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(AmlCase::getCustomerName)
                .setHeader("Customer Name")
                .setSortable(true)
                .setWidth("180px");
        grid.addColumn(AmlCase::getCaseType)
                .setHeader("Case Type")
                .setSortable(true)
                .setWidth("180px");
        grid.addColumn(amlCase -> createStatusBadge(amlCase.getStatus()))
                .setHeader("Status")
                .setSortable(true)
                .setComparator(AmlCase::getStatus)
                .setWidth("150px");
        grid.addColumn(amlCase -> {
                    if (amlCase.getCreationDate() != null) {
                        return amlCase.getCreationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    }
                    return "";
                })
                .setHeader("Creation Date")
                .setSortable(true)
                .setComparator(AmlCase::getCreationDate)
                .setWidth("150px");
        grid.addColumn(AmlCase::getAssignedTo)
                .setHeader("Assigned To")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(AmlCase::getRiskLevel)
                .setHeader("Risk Level")
                .setSortable(true)
                .setWidth("120px");

        // Add actions column
        grid.addColumn(new ComponentRenderer<>(this::createAmlCaseActions))
                .setHeader("Actions")
                .setTextAlign(ColumnTextAlign.CENTER)
                .setWidth("120px");

        return grid;
    }

    private Grid<KycCase> createKycCasesGrid() {
        Grid<KycCase> grid = new Grid<>();

        // Initialize data provider with mock data
        List<KycCase> kycCases = generateMockKycCases();
        kycCasesDataProvider = new ListDataProvider<>(kycCases);
        grid.setDataProvider(kycCasesDataProvider);

        grid.setId("kycCases");
        grid.setSizeFull();

        // Add columns
        grid.addColumn(KycCase::getCaseId)
                .setHeader("Case ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(KycCase::getCustomerId)
                .setHeader("Customer ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(KycCase::getCustomerName)
                .setHeader("Customer Name")
                .setSortable(true)
                .setWidth("180px");
        grid.addColumn(KycCase::getCaseType)
                .setHeader("Case Type")
                .setSortable(true)
                .setWidth("180px");
        grid.addColumn(kycCase -> createStatusBadge(kycCase.getStatus()))
                .setHeader("Status")
                .setSortable(true)
                .setComparator(KycCase::getStatus)
                .setWidth("150px");
        grid.addColumn(kycCase -> {
                    if (kycCase.getCreationDate() != null) {
                        return kycCase.getCreationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    }
                    return "";
                })
                .setHeader("Creation Date")
                .setSortable(true)
                .setComparator(KycCase::getCreationDate)
                .setWidth("150px");
        grid.addColumn(KycCase::getAssignedTo)
                .setHeader("Assigned To")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(KycCase::getCustomerType)
                .setHeader("Customer Type")
                .setSortable(true)
                .setWidth("150px");

        // Add actions column
        grid.addColumn(new ComponentRenderer<>(this::createKycCaseActions))
                .setHeader("Actions")
                .setTextAlign(ColumnTextAlign.CENTER)
                .setWidth("120px");

        return grid;
    }

    private Component createStatusBadge(String status) {
        BadgeColor color;
        switch (status) {
            case "Open":
            case "In Progress":
            case "Pending":
                color = BadgeColor.NORMAL;
                break;
            case "Under Investigation":
            case "Pending Review":
            case "Additional Info Required":
                color = BadgeColor.CONTRAST;
                break;
            case "Closed":
            case "Completed":
                color = BadgeColor.SUCCESS;
                break;
            case "Escalated":
            case "Rejected":
                color = BadgeColor.ERROR;
                break;
            default:
                color = BadgeColor.NORMAL;
        }

        return new Badge(status, color, BadgeSize.S, BadgeShape.PILL);
    }

    private Component createAmlCaseActions(AmlCase amlCase) {
        HorizontalLayout actions = new HorizontalLayout();

        Button viewButton = UIUtils.createButton(VaadinIcon.EYE, ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
        viewButton.getElement().setAttribute("aria-label", "View details");
        viewButton.addClickListener(e -> showAmlCaseDetails(amlCase));

        Button deleteButton = UIUtils.createButton(VaadinIcon.TRASH, ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_ERROR);
        deleteButton.getElement().setAttribute("aria-label", "Delete");
        deleteButton.addClickListener(e -> deleteAmlCase(amlCase));

        actions.add(viewButton, deleteButton);
        return actions;
    }

    private Component createKycCaseActions(KycCase kycCase) {
        HorizontalLayout actions = new HorizontalLayout();

        Button viewButton = UIUtils.createButton(VaadinIcon.EYE, ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
        viewButton.getElement().setAttribute("aria-label", "View details");
        viewButton.addClickListener(e -> showKycCaseDetails(kycCase));

        Button deleteButton = UIUtils.createButton(VaadinIcon.TRASH, ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_ERROR);
        deleteButton.getElement().setAttribute("aria-label", "Delete");
        deleteButton.addClickListener(e -> deleteKycCase(kycCase));

        actions.add(viewButton, deleteButton);
        return actions;
    }

    private void showAmlCaseDetails(AmlCase amlCase) {
        new AmlCaseDetails(amlCase).open();
    }

    private void deleteAmlCase(AmlCase amlCase) {
        // In a real application, this would delete the case from the backend
        amlCasesDataProvider.getItems().remove(amlCase);
        amlCasesDataProvider.refreshAll();
        UIUtils.showNotification("AML Case " + amlCase.getCaseId() + " deleted.");
    }

    private void showKycCaseDetails(KycCase kycCase) {
        new KycCaseDetails(kycCase).open();
    }

    private void deleteKycCase(KycCase kycCase) {
        // In a real application, this would delete the case from the backend
        kycCasesDataProvider.getItems().remove(kycCase);
        kycCasesDataProvider.refreshAll();
        UIUtils.showNotification("KYC Case " + kycCase.getCaseId() + " deleted.");
    }

    private List<AmlCase> generateMockAmlCases() {
        List<AmlCase> amlCases = new ArrayList<>();
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

        String[] caseTypes = {"Transaction Monitoring", "Suspicious Activity", "Regulatory Alert", "Internal Investigation"};
        String[] statuses = {"Open", "Under Investigation", "Pending Review", "Closed", "Escalated"};
        String[] riskLevels = {"Low", "Medium", "High", "Critical"};

        String[] assignedTo = {
            "John Smith", 
            "Maria Rodriguez", 
            "Wei Zhang", 
            "Sarah Johnson", 
            "Ahmed Hassan",
            "Olivia Wilson",
            "Michael Brown",
            "Fatima Al-Farsi"
        };

        for (int i = 1; i <= 20; i++) {
            AmlCase amlCase = new AmlCase();
            amlCase.setCaseId("AML" + String.format("%06d", i));

            int customerIndex = random.nextInt(customerIds.length);
            amlCase.setCustomerId(customerIds[customerIndex]);
            amlCase.setCustomerName(customerNames[customerIndex]);

            amlCase.setCaseType(caseTypes[random.nextInt(caseTypes.length)]);
            amlCase.setStatus(statuses[random.nextInt(statuses.length)]);
            amlCase.setRiskLevel(riskLevels[random.nextInt(riskLevels.length)]);

            // Generate creation date within the last 2 years
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime creationDate = now.minusDays(random.nextInt(730));
            amlCase.setCreationDate(creationDate);

            amlCase.setAssignedTo(assignedTo[random.nextInt(assignedTo.length)]);

            // Add some details
            amlCase.setDescription("Potential suspicious activity detected in customer account.");
            amlCase.setTransactionIds("TXN" + String.format("%06d", i * 10) + ", TXN" + String.format("%06d", i * 10 + 1));
            amlCase.setAlertSource("Automated Monitoring System");

            amlCases.add(amlCase);
        }

        return amlCases;
    }

    private List<KycCase> generateMockKycCases() {
        List<KycCase> kycCases = new ArrayList<>();
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

        String[] caseTypes = {"Identity Verification", "Document Validation", "Enhanced Due Diligence", "Periodic Review"};
        String[] statuses = {"Pending", "In Progress", "Completed", "Rejected", "Additional Info Required"};
        String[] customerTypes = {"Individual", "Corporate", "Trust", "Partnership"};

        String[] assignedTo = {
            "John Smith", 
            "Maria Rodriguez", 
            "Wei Zhang", 
            "Sarah Johnson", 
            "Ahmed Hassan",
            "Olivia Wilson",
            "Michael Brown",
            "Fatima Al-Farsi"
        };

        for (int i = 1; i <= 20; i++) {
            KycCase kycCase = new KycCase();
            kycCase.setCaseId("KYC" + String.format("%06d", i));

            int customerIndex = random.nextInt(customerIds.length);
            kycCase.setCustomerId(customerIds[customerIndex]);
            kycCase.setCustomerName(customerNames[customerIndex]);

            kycCase.setCaseType(caseTypes[random.nextInt(caseTypes.length)]);
            kycCase.setStatus(statuses[random.nextInt(statuses.length)]);
            kycCase.setCustomerType(customerTypes[random.nextInt(customerTypes.length)]);

            // Generate creation date within the last 2 years
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime creationDate = now.minusDays(random.nextInt(730));
            kycCase.setCreationDate(creationDate);

            kycCase.setAssignedTo(assignedTo[random.nextInt(assignedTo.length)]);

            // Add some details
            kycCase.setDescription("Customer onboarding KYC verification process.");
            kycCase.setDocumentIds("DOC" + String.format("%06d", i * 10) + ", DOC" + String.format("%06d", i * 10 + 1));
            kycCase.setVerificationMethod("Document Verification + Video Interview");

            kycCases.add(kycCase);
        }

        return kycCases;
    }

    // Inner class to represent an AML case
    public static class AmlCase {
        private String caseId;
        private String customerId;
        private String customerName;
        private String caseType;
        private String status;
        private LocalDateTime creationDate;
        private String assignedTo;
        private String riskLevel;
        private String description;
        private String transactionIds;
        private String alertSource;

        public String getCaseId() {
            return caseId;
        }

        public void setCaseId(String caseId) {
            this.caseId = caseId;
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

        public String getCaseType() {
            return caseType;
        }

        public void setCaseType(String caseType) {
            this.caseType = caseType;
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

        public String getAssignedTo() {
            return assignedTo;
        }

        public void setAssignedTo(String assignedTo) {
            this.assignedTo = assignedTo;
        }

        public String getRiskLevel() {
            return riskLevel;
        }

        public void setRiskLevel(String riskLevel) {
            this.riskLevel = riskLevel;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTransactionIds() {
            return transactionIds;
        }

        public void setTransactionIds(String transactionIds) {
            this.transactionIds = transactionIds;
        }

        public String getAlertSource() {
            return alertSource;
        }

        public void setAlertSource(String alertSource) {
            this.alertSource = alertSource;
        }
    }

    // Inner class to represent a KYC case
    public static class KycCase {
        private String caseId;
        private String customerId;
        private String customerName;
        private String caseType;
        private String status;
        private LocalDateTime creationDate;
        private String assignedTo;
        private String customerType;
        private String description;
        private String documentIds;
        private String verificationMethod;

        public String getCaseId() {
            return caseId;
        }

        public void setCaseId(String caseId) {
            this.caseId = caseId;
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

        public String getCaseType() {
            return caseType;
        }

        public void setCaseType(String caseType) {
            this.caseType = caseType;
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

        public String getAssignedTo() {
            return assignedTo;
        }

        public void setAssignedTo(String assignedTo) {
            this.assignedTo = assignedTo;
        }

        public String getCustomerType() {
            return customerType;
        }

        public void setCustomerType(String customerType) {
            this.customerType = customerType;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDocumentIds() {
            return documentIds;
        }

        public void setDocumentIds(String documentIds) {
            this.documentIds = documentIds;
        }

        public String getVerificationMethod() {
            return verificationMethod;
        }

        public void setVerificationMethod(String verificationMethod) {
            this.verificationMethod = verificationMethod;
        }
    }

    // Dialog for displaying AML case details
    public static class AmlCaseDetails extends Dialog {
        private final AmlCase amlCase;

        private TextField caseIdField;
        private TextField customerIdField;
        private TextField customerNameField;
        private ComboBox<String> caseTypeField;
        private ComboBox<String> statusField;
        private DatePicker creationDateField;
        private TextField assignedToField;
        private ComboBox<String> riskLevelField;
        private TextField descriptionField;
        private TextField transactionIdsField;
        private TextField alertSourceField;

        public AmlCaseDetails(AmlCase amlCase) {
            this.amlCase = amlCase;

            setWidth("800px");
            setHeight("auto");

            VerticalLayout content = new VerticalLayout();
            content.setPadding(true);
            content.setSpacing(true);

            H3 title = new H3("AML Case Details: " + amlCase.getCaseId());
            content.add(title);

            content.add(createForm());
            content.add(createButtonLayout());

            add(content);
        }

        private FormLayout createForm() {
            // Case ID
            caseIdField = new TextField();
            caseIdField.setValue(amlCase.getCaseId() != null ? amlCase.getCaseId() : "");
            caseIdField.setWidthFull();
            caseIdField.setReadOnly(true);

            // Customer ID
            customerIdField = new TextField();
            customerIdField.setValue(amlCase.getCustomerId() != null ? amlCase.getCustomerId() : "");
            customerIdField.setWidthFull();

            // Customer Name
            customerNameField = new TextField();
            customerNameField.setValue(amlCase.getCustomerName() != null ? amlCase.getCustomerName() : "");
            customerNameField.setWidthFull();

            // Case Type
            caseTypeField = new ComboBox<>();
            caseTypeField.setItems("Transaction Monitoring", "Suspicious Activity", "Regulatory Alert", "Internal Investigation");
            caseTypeField.setValue(amlCase.getCaseType());
            caseTypeField.setWidthFull();

            // Status
            statusField = new ComboBox<>();
            statusField.setItems("Open", "Under Investigation", "Pending Review", "Closed", "Escalated");
            statusField.setValue(amlCase.getStatus());
            statusField.setWidthFull();

            // Creation Date
            creationDateField = new DatePicker();
            creationDateField.setValue(amlCase.getCreationDate() != null ? amlCase.getCreationDate().toLocalDate() : LocalDate.now());
            creationDateField.setWidthFull();

            // Assigned To
            assignedToField = new TextField();
            assignedToField.setValue(amlCase.getAssignedTo() != null ? amlCase.getAssignedTo() : "");
            assignedToField.setWidthFull();

            // Risk Level
            riskLevelField = new ComboBox<>();
            riskLevelField.setItems("Low", "Medium", "High", "Critical");
            riskLevelField.setValue(amlCase.getRiskLevel());
            riskLevelField.setWidthFull();

            // Description
            descriptionField = new TextField();
            descriptionField.setValue(amlCase.getDescription() != null ? amlCase.getDescription() : "");
            descriptionField.setWidthFull();

            // Transaction IDs
            transactionIdsField = new TextField();
            transactionIdsField.setValue(amlCase.getTransactionIds() != null ? amlCase.getTransactionIds() : "");
            transactionIdsField.setWidthFull();

            // Alert Source
            alertSourceField = new TextField();
            alertSourceField.setValue(amlCase.getAlertSource() != null ? amlCase.getAlertSource() : "");
            alertSourceField.setWidthFull();

            // Form layout
            FormLayout form = new FormLayout();
            form.addClassName("padding-m");
            form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("600px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP)
            );

            form.addFormItem(caseIdField, "Case ID");
            form.addFormItem(customerIdField, "Customer ID");
            form.addFormItem(customerNameField, "Customer Name");
            form.addFormItem(caseTypeField, "Case Type");
            form.addFormItem(statusField, "Status");
            form.addFormItem(creationDateField, "Creation Date");
            form.addFormItem(assignedToField, "Assigned To");
            form.addFormItem(riskLevelField, "Risk Level");
            form.addFormItem(descriptionField, "Description");
            form.addFormItem(transactionIdsField, "Transaction IDs");
            form.addFormItem(alertSourceField, "Alert Source");

            return form;
        }

        private HorizontalLayout createButtonLayout() {
            Button saveButton = UIUtils.createPrimaryButton("Save");
            saveButton.addClickListener(e -> {
                saveCase();
                close();
            });

            Button cancelButton = UIUtils.createTertiaryButton("Cancel");
            cancelButton.addClickListener(e -> close());

            HorizontalLayout buttonLayout = new HorizontalLayout(saveButton, cancelButton);
            buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
            buttonLayout.setWidthFull();
            buttonLayout.setSpacing(true);

            return buttonLayout;
        }

        private void saveCase() {
            // In a real application, this would save the case data to the backend
            // For this example, we'll just show a notification
            UIUtils.showNotification("AML Case details saved.");

            // Here you would update the case with the new values
            // Example: amlCase.setCaseType(caseTypeField.getValue());
            // amlCaseService.updateCase(amlCase);
        }
    }

    // Dialog for displaying KYC case details
    public static class KycCaseDetails extends Dialog {
        private final KycCase kycCase;

        private TextField caseIdField;
        private TextField customerIdField;
        private TextField customerNameField;
        private ComboBox<String> caseTypeField;
        private ComboBox<String> statusField;
        private DatePicker creationDateField;
        private TextField assignedToField;
        private ComboBox<String> customerTypeField;
        private TextField descriptionField;
        private TextField documentIdsField;
        private TextField verificationMethodField;

        public KycCaseDetails(KycCase kycCase) {
            this.kycCase = kycCase;

            setWidth("800px");
            setHeight("auto");

            VerticalLayout content = new VerticalLayout();
            content.setPadding(true);
            content.setSpacing(true);

            H3 title = new H3("KYC Case Details: " + kycCase.getCaseId());
            content.add(title);

            content.add(createForm());
            content.add(createButtonLayout());

            add(content);
        }

        private FormLayout createForm() {
            // Case ID
            caseIdField = new TextField();
            caseIdField.setValue(kycCase.getCaseId() != null ? kycCase.getCaseId() : "");
            caseIdField.setWidthFull();
            caseIdField.setReadOnly(true);

            // Customer ID
            customerIdField = new TextField();
            customerIdField.setValue(kycCase.getCustomerId() != null ? kycCase.getCustomerId() : "");
            customerIdField.setWidthFull();

            // Customer Name
            customerNameField = new TextField();
            customerNameField.setValue(kycCase.getCustomerName() != null ? kycCase.getCustomerName() : "");
            customerNameField.setWidthFull();

            // Case Type
            caseTypeField = new ComboBox<>();
            caseTypeField.setItems("Identity Verification", "Document Validation", "Enhanced Due Diligence", "Periodic Review");
            caseTypeField.setValue(kycCase.getCaseType());
            caseTypeField.setWidthFull();

            // Status
            statusField = new ComboBox<>();
            statusField.setItems("Pending", "In Progress", "Completed", "Rejected", "Additional Info Required");
            statusField.setValue(kycCase.getStatus());
            statusField.setWidthFull();

            // Creation Date
            creationDateField = new DatePicker();
            creationDateField.setValue(kycCase.getCreationDate() != null ? kycCase.getCreationDate().toLocalDate() : LocalDate.now());
            creationDateField.setWidthFull();

            // Assigned To
            assignedToField = new TextField();
            assignedToField.setValue(kycCase.getAssignedTo() != null ? kycCase.getAssignedTo() : "");
            assignedToField.setWidthFull();

            // Customer Type
            customerTypeField = new ComboBox<>();
            customerTypeField.setItems("Individual", "Corporate", "Trust", "Partnership");
            customerTypeField.setValue(kycCase.getCustomerType());
            customerTypeField.setWidthFull();

            // Description
            descriptionField = new TextField();
            descriptionField.setValue(kycCase.getDescription() != null ? kycCase.getDescription() : "");
            descriptionField.setWidthFull();

            // Document IDs
            documentIdsField = new TextField();
            documentIdsField.setValue(kycCase.getDocumentIds() != null ? kycCase.getDocumentIds() : "");
            documentIdsField.setWidthFull();

            // Verification Method
            verificationMethodField = new TextField();
            verificationMethodField.setValue(kycCase.getVerificationMethod() != null ? kycCase.getVerificationMethod() : "");
            verificationMethodField.setWidthFull();

            // Form layout
            FormLayout form = new FormLayout();
            form.addClassName("padding-m");
            form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("600px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP)
            );

            form.addFormItem(caseIdField, "Case ID");
            form.addFormItem(customerIdField, "Customer ID");
            form.addFormItem(customerNameField, "Customer Name");
            form.addFormItem(caseTypeField, "Case Type");
            form.addFormItem(statusField, "Status");
            form.addFormItem(creationDateField, "Creation Date");
            form.addFormItem(assignedToField, "Assigned To");
            form.addFormItem(customerTypeField, "Customer Type");
            form.addFormItem(descriptionField, "Description");
            form.addFormItem(documentIdsField, "Document IDs");
            form.addFormItem(verificationMethodField, "Verification Method");

            return form;
        }

        private HorizontalLayout createButtonLayout() {
            Button saveButton = UIUtils.createPrimaryButton("Save");
            saveButton.addClickListener(e -> {
                saveCase();
                close();
            });

            Button cancelButton = UIUtils.createTertiaryButton("Cancel");
            cancelButton.addClickListener(e -> close());

            HorizontalLayout buttonLayout = new HorizontalLayout(saveButton, cancelButton);
            buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
            buttonLayout.setWidthFull();
            buttonLayout.setSpacing(true);

            return buttonLayout;
        }

        private void saveCase() {
            // In a real application, this would save the case data to the backend
            // For this example, we'll just show a notification
            UIUtils.showNotification("KYC Case details saved.");

            // Here you would update the case with the new values
            // Example: kycCase.setCaseType(caseTypeField.getValue());
            // kycCaseService.updateCase(kycCase);
        }
    }
}
