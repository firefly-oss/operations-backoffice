package com.vaadin.starter.business.ui.views.fraudriskoperations;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.business.backend.dto.fraudriskoperations.AmlKycCaseDTO;
import com.vaadin.starter.business.backend.service.FraudRiskOperationsService;
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.components.FlexBoxLayout;
import com.vaadin.starter.business.ui.components.ListItem;
import com.vaadin.starter.business.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.business.ui.layout.size.Bottom;
import com.vaadin.starter.business.ui.layout.size.Horizontal;
import com.vaadin.starter.business.ui.layout.size.Top;
import com.vaadin.starter.business.ui.layout.size.Vertical;
import com.vaadin.starter.business.ui.util.BoxShadowBorders;
import com.vaadin.starter.business.ui.util.LumoStyles;
import com.vaadin.starter.business.ui.util.css.WhiteSpace;
import com.vaadin.starter.business.ui.views.ViewFrame;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.format.DateTimeFormatter;

@PageTitle("AML/KYC Case Details")
@Route(value = "fraud-risk/aml-kyc-details", layout = MainLayout.class)
public class AmlKycCaseDetails extends ViewFrame implements HasUrlParameter<String> {

    private final FraudRiskOperationsService fraudRiskOperationsService;

    private ListItem caseId;
    private ListItem customerId;
    private ListItem customerName;
    private ListItem caseType;
    private ListItem status;
    private ListItem riskLevel;
    private ListItem assignedTo;
    private ListItem createdDate;
    private ListItem dueDate;
    private ListItem regulatoryBody;
    private ListItem notes;

    private AmlKycCases.AmlKycCase amlKycCase;

    @Autowired
    public AmlKycCaseDetails(FraudRiskOperationsService fraudRiskOperationsService) {
        this.fraudRiskOperationsService = fraudRiskOperationsService;
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String caseId) {
        // Fetch the AML/KYC case from the service
        AmlKycCaseDTO caseDTO = fraudRiskOperationsService.getAmlKycCaseById(caseId);
        if (caseDTO != null) {
            amlKycCase = convertToViewModel(caseDTO);
            setViewContent(createContent());
        } else {
            // Handle case not found
            UI.getCurrent().navigate("fraud-risk/aml-kyc");
        }
    }

    private AmlKycCases.AmlKycCase convertToViewModel(AmlKycCaseDTO dto) {
        AmlKycCases.AmlKycCase amlCase = new AmlKycCases.AmlKycCase();
        amlCase.setCaseId(dto.getCaseId());
        amlCase.setCustomerId(dto.getCustomerId());
        amlCase.setCustomerName(dto.getCustomerName());
        amlCase.setCaseType(dto.getCaseType());
        amlCase.setStatus(dto.getStatus());
        amlCase.setRiskLevel(dto.getRiskLevel());
        amlCase.setAssignedTo(dto.getAssignedTo());
        amlCase.setCreatedDate(dto.getCreatedDate());
        amlCase.setDueDate(dto.getDueDate());
        amlCase.setRegulatoryBody(dto.getRegulatoryBody());
        amlCase.setNotes(dto.getNotes());
        return amlCase;
    }

    private Component createContent() {
        FlexBoxLayout content = new FlexBoxLayout(
                createDetailsSection(),
                createRelatedItemsHeader(),
                createRelatedItemsList()
        );
        content.setFlexDirection(FlexDirection.COLUMN);
        content.setMargin(Horizontal.AUTO, Vertical.RESPONSIVE_L);
        content.setMaxWidth("840px");
        return content;
    }

    private FlexBoxLayout createDetailsSection() {
        caseId = new ListItem(VaadinIcon.HASH.create(), "", "Case ID");
        caseId.getPrimary().addClassName(LumoStyles.Heading.H2);
        caseId.setDividerVisible(true);
        caseId.setId("caseId");
        caseId.setReverse(true);

        customerId = new ListItem(VaadinIcon.USER.create(), "", "Customer ID");
        customerId.setDividerVisible(true);
        customerId.setId("customerId");
        customerId.setReverse(true);

        customerName = new ListItem(VaadinIcon.USER.create(), "", "Customer Name");
        customerName.setDividerVisible(true);
        customerName.setId("customerName");
        customerName.setReverse(true);

        caseType = new ListItem(VaadinIcon.FOLDER.create(), "", "Case Type");
        caseType.setDividerVisible(true);
        caseType.setId("caseType");
        caseType.setReverse(true);

        status = new ListItem(VaadinIcon.FLAG.create(), "", "Status");
        status.setDividerVisible(true);
        status.setId("status");
        status.setReverse(true);

        riskLevel = new ListItem(VaadinIcon.WARNING.create(), "", "Risk Level");
        riskLevel.setDividerVisible(true);
        riskLevel.setId("riskLevel");
        riskLevel.setReverse(true);

        assignedTo = new ListItem(VaadinIcon.USER_CARD.create(), "", "Assigned To");
        assignedTo.setDividerVisible(true);
        assignedTo.setId("assignedTo");
        assignedTo.setReverse(true);

        createdDate = new ListItem(VaadinIcon.CALENDAR.create(), "", "Created Date");
        createdDate.setDividerVisible(true);
        createdDate.setId("createdDate");
        createdDate.setReverse(true);

        dueDate = new ListItem(VaadinIcon.CALENDAR_CLOCK.create(), "", "Due Date");
        dueDate.setDividerVisible(true);
        dueDate.setId("dueDate");
        dueDate.setReverse(true);

        regulatoryBody = new ListItem(VaadinIcon.INSTITUTION.create(), "", "Regulatory Body");
        regulatoryBody.setDividerVisible(true);
        regulatoryBody.setId("regulatoryBody");
        regulatoryBody.setReverse(true);

        notes = new ListItem(VaadinIcon.CLIPBOARD_TEXT.create(), "", "Notes");
        notes.setDividerVisible(true);
        notes.setId("notes");
        notes.setReverse(true);
        notes.setWhiteSpace(WhiteSpace.PRE_LINE);

        // Populate fields with data
        caseId.setPrimaryText(amlKycCase.getCaseId());
        customerId.setPrimaryText(amlKycCase.getCustomerId());
        customerName.setPrimaryText(amlKycCase.getCustomerName());
        caseType.setPrimaryText(amlKycCase.getCaseType());
        status.setPrimaryText(amlKycCase.getStatus());
        riskLevel.setPrimaryText(amlKycCase.getRiskLevel());
        assignedTo.setPrimaryText(amlKycCase.getAssignedTo());

        if (amlKycCase.getCreatedDate() != null) {
            createdDate.setPrimaryText(amlKycCase.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        }

        if (amlKycCase.getDueDate() != null) {
            dueDate.setPrimaryText(amlKycCase.getDueDate().toString());
        }

        regulatoryBody.setPrimaryText(amlKycCase.getRegulatoryBody());
        notes.setPrimaryText(amlKycCase.getNotes());

        FlexBoxLayout listItems = new FlexBoxLayout(
                caseId,
                customerId,
                customerName,
                caseType,
                status,
                riskLevel,
                assignedTo,
                createdDate,
                dueDate,
                regulatoryBody,
                notes
        );
        listItems.setFlexDirection(FlexDirection.COLUMN);

        FlexBoxLayout details = new FlexBoxLayout(listItems);
        details.addClassName(BoxShadowBorders.BOTTOM);
        details.setAlignItems(FlexComponent.Alignment.CENTER);
        details.setFlex("1", listItems);
        details.setFlexWrap(FlexWrap.WRAP);
        details.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        details.setPadding(Bottom.L);
        return details;
    }

    private Component createRelatedItemsHeader() {
        FlexBoxLayout header = new FlexBoxLayout(
                new Span("Related Actions")
        );
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setMargin(Bottom.M, Horizontal.RESPONSIVE_L, Top.L);
        return header;
    }

    private Component createRelatedItemsList() {
        Button updateButton = new Button("Update Status", VaadinIcon.EDIT.create());
        updateButton.addClickListener(e -> {
            // In a real application, this would open a dialog to update the status
            UI.getCurrent().navigate("fraud-risk/aml-kyc");
        });

        Button documentButton = new Button("Request Documents", VaadinIcon.FILE_TEXT.create());
        documentButton.addClickListener(e -> {
            // In a real application, this would open a dialog to request documents
            UI.getCurrent().navigate("fraud-risk/aml-kyc");
        });

        Button reportButton = new Button("Generate Report", VaadinIcon.FILE_PRESENTATION.create());
        reportButton.addClickListener(e -> {
            // In a real application, this would generate a report
            UI.getCurrent().navigate("fraud-risk/aml-kyc");
        });

        Button closeButton = new Button("Close Case", VaadinIcon.CHECK.create());
        closeButton.addClickListener(e -> {
            // In a real application, this would close the case
            UI.getCurrent().navigate("fraud-risk/aml-kyc");
        });

        FlexBoxLayout actions = new FlexBoxLayout(
                updateButton,
                documentButton,
                reportButton,
                closeButton
        );
        actions.setFlexWrap(FlexWrap.WRAP);
        actions.setMargin(Horizontal.RESPONSIVE_L);
        return actions;
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        initAppBar();
    }

    private AppBar initAppBar() {
        AppBar appBar = MainLayout.get().getAppBar();
        appBar.setNaviMode(AppBar.NaviMode.CONTEXTUAL);
        appBar.getContextIcon().addClickListener(e -> UI.getCurrent().navigate("fraud-risk/aml-kyc"));
        appBar.setTitle("AML/KYC Case: " + amlKycCase.getCaseId());
        return appBar;
    }

}
