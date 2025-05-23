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

import java.time.format.DateTimeFormatter;

@PageTitle("Risk Alert Details")
@Route(value = "fraud-risk/risk-alert-details", layout = MainLayout.class)
public class RiskAlertDetails extends ViewFrame implements HasUrlParameter<String> {

    private ListItem alertId;
    private ListItem alertType;
    private ListItem severity;
    private ListItem status;
    private ListItem entityId;
    private ListItem entityName;
    private ListItem entityType;
    private ListItem generatedDate;
    private ListItem assignedTo;
    private ListItem description;

    private RiskAlertsManagement.RiskAlert riskAlert;

    @Override
    public void setParameter(BeforeEvent beforeEvent, String alertId) {
        // In a real application, this would fetch the risk alert from a service
        // For this example, we'll create a mock alert
        riskAlert = createMockRiskAlert(alertId);
        setViewContent(createContent());
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
        alertId = new ListItem(VaadinIcon.HASH.create(), "", "Alert ID");
        alertId.getPrimary().addClassName(LumoStyles.Heading.H2);
        alertId.setDividerVisible(true);
        alertId.setId("alertId");
        alertId.setReverse(true);

        alertType = new ListItem(VaadinIcon.EXCLAMATION_CIRCLE.create(), "", "Alert Type");
        alertType.setDividerVisible(true);
        alertType.setId("alertType");
        alertType.setReverse(true);

        severity = new ListItem(VaadinIcon.WARNING.create(), "", "Severity");
        severity.setDividerVisible(true);
        severity.setId("severity");
        severity.setReverse(true);

        status = new ListItem(VaadinIcon.FLAG.create(), "", "Status");
        status.setDividerVisible(true);
        status.setId("status");
        status.setReverse(true);

        entityId = new ListItem(VaadinIcon.HASH.create(), "", "Entity ID");
        entityId.setDividerVisible(true);
        entityId.setId("entityId");
        entityId.setReverse(true);

        entityName = new ListItem(VaadinIcon.BUILDING.create(), "", "Entity Name");
        entityName.setDividerVisible(true);
        entityName.setId("entityName");
        entityName.setReverse(true);

        entityType = new ListItem(VaadinIcon.FOLDER.create(), "", "Entity Type");
        entityType.setDividerVisible(true);
        entityType.setId("entityType");
        entityType.setReverse(true);

        generatedDate = new ListItem(VaadinIcon.CALENDAR.create(), "", "Generated Date");
        generatedDate.setDividerVisible(true);
        generatedDate.setId("generatedDate");
        generatedDate.setReverse(true);

        assignedTo = new ListItem(VaadinIcon.USER_CARD.create(), "", "Assigned To");
        assignedTo.setDividerVisible(true);
        assignedTo.setId("assignedTo");
        assignedTo.setReverse(true);

        description = new ListItem(VaadinIcon.CLIPBOARD_TEXT.create(), "", "Description");
        description.setDividerVisible(true);
        description.setId("description");
        description.setReverse(true);
        description.setWhiteSpace(WhiteSpace.PRE_LINE);

        // Populate fields with data
        alertId.setPrimaryText(riskAlert.getAlertId());
        alertType.setPrimaryText(riskAlert.getAlertType());
        severity.setPrimaryText(riskAlert.getSeverity());
        status.setPrimaryText(riskAlert.getStatus());
        entityId.setPrimaryText(riskAlert.getEntityId());
        entityName.setPrimaryText(riskAlert.getEntityName());
        entityType.setPrimaryText(riskAlert.getEntityType());
        
        if (riskAlert.getGeneratedDate() != null) {
            generatedDate.setPrimaryText(riskAlert.getGeneratedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        }
        
        assignedTo.setPrimaryText(riskAlert.getAssignedTo());
        description.setPrimaryText(riskAlert.getDescription());

        FlexBoxLayout listItems = new FlexBoxLayout(
                alertId,
                alertType,
                severity,
                status,
                entityId,
                entityName,
                entityType,
                generatedDate,
                assignedTo,
                description
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
            UI.getCurrent().navigate("fraud-risk/risk-alerts");
        });

        Button assignButton = new Button("Assign", VaadinIcon.USER_CARD.create());
        assignButton.addClickListener(e -> {
            // In a real application, this would open a dialog to assign the alert
            UI.getCurrent().navigate("fraud-risk/risk-alerts");
        });

        Button mitigateButton = new Button("Mitigate Risk", VaadinIcon.CHECK.create());
        mitigateButton.addClickListener(e -> {
            // In a real application, this would open a dialog to mitigate the risk
            UI.getCurrent().navigate("fraud-risk/risk-alerts");
        });

        Button investigateButton = new Button("Investigate", VaadinIcon.SEARCH.create());
        investigateButton.addClickListener(e -> {
            // In a real application, this would navigate to an investigation form
            UI.getCurrent().navigate("fraud-risk/investigations/new?alertId=" + riskAlert.getAlertId());
        });

        FlexBoxLayout actions = new FlexBoxLayout(
                updateButton,
                assignButton,
                mitigateButton,
                investigateButton
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
        appBar.getContextIcon().addClickListener(e -> UI.getCurrent().navigate("fraud-risk/risk-alerts"));
        appBar.setTitle("Risk Alert: " + riskAlert.getAlertId());
        return appBar;
    }

    private RiskAlertsManagement.RiskAlert createMockRiskAlert(String alertId) {
        RiskAlertsManagement.RiskAlert alert = new RiskAlertsManagement.RiskAlert();
        alert.setAlertId(alertId);
        alert.setAlertType("Operational Risk");
        alert.setSeverity("High");
        alert.setStatus("In Progress");
        alert.setEntityId("C10045");
        alert.setEntityName("John Smith");
        alert.setEntityType("Customer");
        alert.setGeneratedDate(java.time.LocalDateTime.now().minusDays(3));
        alert.setAssignedTo("Sarah Johnson");
        alert.setDescription("Unusual pattern of transactions detected for this customer. Multiple high-value transactions in short time period. Potential money laundering risk.");
        return alert;
    }
}