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

@PageTitle("Suspicious Activity Details")
@Route(value = "fraud-risk/suspicious-activity-details", layout = MainLayout.class)
public class SuspiciousActivityDetails extends ViewFrame implements HasUrlParameter<String> {

    private ListItem activityId;
    private ListItem customerId;
    private ListItem customerName;
    private ListItem accountNumber;
    private ListItem activityType;
    private ListItem riskLevel;
    private ListItem status;
    private ListItem detectedDate;
    private ListItem amount;
    private ListItem description;

    private SuspiciousActivityMonitoring.SuspiciousActivity activity;

    @Override
    public void setParameter(BeforeEvent beforeEvent, String activityId) {
        // In a real application, this would fetch the activity from a service
        // For this example, we'll create a mock activity
        activity = createMockActivity(activityId);
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
        activityId = new ListItem(VaadinIcon.HASH.create(), "", "Activity ID");
        activityId.getPrimary().addClassName(LumoStyles.Heading.H2);
        activityId.setDividerVisible(true);
        activityId.setId("activityId");
        activityId.setReverse(true);

        customerId = new ListItem(VaadinIcon.USER.create(), "", "Customer ID");
        customerId.setDividerVisible(true);
        customerId.setId("customerId");
        customerId.setReverse(true);

        customerName = new ListItem(VaadinIcon.USER.create(), "", "Customer Name");
        customerName.setDividerVisible(true);
        customerName.setId("customerName");
        customerName.setReverse(true);

        accountNumber = new ListItem(VaadinIcon.CREDIT_CARD.create(), "", "Account Number");
        accountNumber.setDividerVisible(true);
        accountNumber.setId("accountNumber");
        accountNumber.setReverse(true);

        activityType = new ListItem(VaadinIcon.EXCLAMATION_CIRCLE.create(), "", "Activity Type");
        activityType.setDividerVisible(true);
        activityType.setId("activityType");
        activityType.setReverse(true);

        riskLevel = new ListItem(VaadinIcon.WARNING.create(), "", "Risk Level");
        riskLevel.setDividerVisible(true);
        riskLevel.setId("riskLevel");
        riskLevel.setReverse(true);

        status = new ListItem(VaadinIcon.FLAG.create(), "", "Status");
        status.setDividerVisible(true);
        status.setId("status");
        status.setReverse(true);

        detectedDate = new ListItem(VaadinIcon.CALENDAR.create(), "", "Detected Date");
        detectedDate.setDividerVisible(true);
        detectedDate.setId("detectedDate");
        detectedDate.setReverse(true);

        amount = new ListItem(VaadinIcon.MONEY.create(), "", "Amount");
        amount.setDividerVisible(true);
        amount.setId("amount");
        amount.setReverse(true);

        description = new ListItem(VaadinIcon.CLIPBOARD_TEXT.create(), "", "Description");
        description.setDividerVisible(true);
        description.setId("description");
        description.setReverse(true);
        description.setWhiteSpace(WhiteSpace.PRE_LINE);

        // Populate fields with data
        activityId.setPrimaryText(activity.getActivityId());
        customerId.setPrimaryText(activity.getCustomerId());
        customerName.setPrimaryText(activity.getCustomerName());
        accountNumber.setPrimaryText(activity.getAccountNumber());
        activityType.setPrimaryText(activity.getActivityType());

        // For risk level, set the text
        riskLevel.setPrimaryText(activity.getRiskLevel());

        status.setPrimaryText(activity.getStatus());

        if (activity.getDetectedDate() != null) {
            detectedDate.setPrimaryText(activity.getDetectedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        }

        amount.setPrimaryText(String.format("$%.2f", activity.getAmount()));
        description.setPrimaryText(activity.getDescription());

        FlexBoxLayout listItems = new FlexBoxLayout(
                activityId,
                customerId,
                customerName,
                accountNumber,
                activityType,
                riskLevel,
                status,
                detectedDate,
                amount,
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
        Button investigateButton = new Button("Investigate", VaadinIcon.SEARCH.create());
        investigateButton.addClickListener(e -> {
            // In a real application, this would navigate to an investigation form
            UI.getCurrent().navigate("fraud-risk/investigations/new?activityId=" + activity.getActivityId());
        });

        Button closeButton = new Button("Close Activity", VaadinIcon.CHECK.create());
        closeButton.addClickListener(e -> {
            // In a real application, this would close the activity
            UI.getCurrent().navigate("fraud-risk/suspicious-activity");
        });

        Button escalateButton = new Button("Escalate", VaadinIcon.ARROW_UP.create());
        escalateButton.addClickListener(e -> {
            // In a real application, this would escalate the activity
            UI.getCurrent().navigate("fraud-risk/suspicious-activity");
        });

        FlexBoxLayout actions = new FlexBoxLayout(
                investigateButton,
                closeButton,
                escalateButton
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
        appBar.getContextIcon().addClickListener(e -> UI.getCurrent().navigate("fraud-risk/suspicious-activity"));
        appBar.setTitle("Suspicious Activity: " + activity.getActivityId());
        return appBar;
    }

    private SuspiciousActivityMonitoring.SuspiciousActivity createMockActivity(String activityId) {
        SuspiciousActivityMonitoring.SuspiciousActivity activity = new SuspiciousActivityMonitoring.SuspiciousActivity();
        activity.setActivityId(activityId);
        activity.setCustomerId("C10045");
        activity.setCustomerName("John Smith");
        activity.setAccountNumber("1234567890");
        activity.setActivityType("Unusual Transaction");
        activity.setRiskLevel("High");
        activity.setStatus("Under Review");
        activity.setDetectedDate(java.time.LocalDateTime.now().minusDays(2));
        activity.setAmount(5000.00);
        activity.setDescription("Multiple large transactions in short time period. Customer made 5 transactions totaling $25,000 within 2 hours, which is unusual for this account profile.");
        return activity;
    }
}
