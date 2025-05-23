package com.vaadin.starter.business.ui.views.customerservice;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.business.backend.CustomerRequest;
import com.vaadin.starter.business.backend.service.CustomerRequestService;
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.components.FlexBoxLayout;
import com.vaadin.starter.business.ui.components.ListItem;
import com.vaadin.starter.business.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.business.ui.layout.size.Bottom;
import com.vaadin.starter.business.ui.layout.size.Horizontal;
import com.vaadin.starter.business.ui.layout.size.Vertical;
import com.vaadin.starter.business.ui.util.LumoStyles;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.util.css.BoxSizing;
import com.vaadin.starter.business.ui.views.ViewFrame;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.format.DateTimeFormatter;

@PageTitle("Customer Request Details")
@Route(value = "customer-request-details", layout = MainLayout.class)
public class CustomerRequestDetails extends ViewFrame implements HasUrlParameter<String> {

    private ListItem requestId;
    private ListItem requestNumber;
    private ListItem subject;
    private ListItem status;
    private ListItem priority;
    private ListItem type;
    private ListItem customerId;
    private ListItem customerName;
    private ListItem assignedTo;
    private ListItem createdDate;
    private ListItem lastModifiedDate;
    private ListItem completionDate;
    private ListItem resolution;
    private ListItem channel;
    private ListItem description;

    private CustomerRequest customerRequest;
    private final CustomerRequestService customerRequestService;

    @Autowired
    public CustomerRequestDetails(CustomerRequestService customerRequestService) {
        this.customerRequestService = customerRequestService;
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String id) {
        customerRequest = customerRequestService.getCustomerRequestById(id);
        setViewContent(createContent());
    }

    private Component createContent() {
        FlexBoxLayout content = new FlexBoxLayout(
                createDetailsSection()
        );
        content.setFlexDirection(FlexDirection.COLUMN);
        content.setMargin(Horizontal.AUTO, Vertical.RESPONSIVE_L);
        content.setMaxWidth("840px");
        return content;
    }

    private FlexBoxLayout createDetailsSection() {
        requestId = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.HASH), "", "Request ID");
        requestId.getPrimary().addClassName(LumoStyles.Heading.H2);
        requestId.setDividerVisible(true);
        requestId.setId("requestId");
        requestId.setReverse(true);

        requestNumber = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.NOTEBOOK), "", "Request Number");
        requestNumber.setDividerVisible(true);
        requestNumber.setId("requestNumber");
        requestNumber.setReverse(true);

        subject = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.CLIPBOARD_TEXT), "", "Subject");
        subject.setDividerVisible(true);
        subject.setId("subject");
        subject.setReverse(true);

        status = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.INFO_CIRCLE), "", "Status");
        status.setDividerVisible(true);
        status.setId("status");
        status.setReverse(true);

        priority = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.FLAG), "", "Priority");
        priority.setDividerVisible(true);
        priority.setId("priority");
        priority.setReverse(true);

        type = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.TAGS), "", "Type");
        type.setDividerVisible(true);
        type.setId("type");
        type.setReverse(true);

        customerId = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.USER), "", "Customer ID");
        customerId.setDividerVisible(true);
        customerId.setId("customerId");
        customerId.setReverse(true);

        customerName = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.USER_CARD), "", "Customer Name");
        customerName.setDividerVisible(true);
        customerName.setId("customerName");
        customerName.setReverse(true);

        assignedTo = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.USER_STAR), "", "Assigned To");
        assignedTo.setDividerVisible(true);
        assignedTo.setId("assignedTo");
        assignedTo.setReverse(true);

        createdDate = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.CALENDAR), "", "Created Date");
        createdDate.setDividerVisible(true);
        createdDate.setId("createdDate");
        createdDate.setReverse(true);

        lastModifiedDate = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.CALENDAR_CLOCK), "", "Last Modified Date");
        lastModifiedDate.setDividerVisible(true);
        lastModifiedDate.setId("lastModifiedDate");
        lastModifiedDate.setReverse(true);

        completionDate = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.CALENDAR), "", "Completion Date");
        completionDate.setDividerVisible(true);
        completionDate.setId("completionDate");
        completionDate.setReverse(true);

        resolution = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.CHECK_SQUARE), "", "Resolution");
        resolution.setDividerVisible(true);
        resolution.setId("resolution");
        resolution.setReverse(true);

        channel = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.CONNECT), "", "Channel");
        channel.setDividerVisible(true);
        channel.setId("channel");
        channel.setReverse(true);

        description = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.CLIPBOARD_TEXT), "", "Description");
        description.setDividerVisible(true);
        description.setId("description");
        description.setReverse(true);

        FlexBoxLayout details = new FlexBoxLayout(
                requestId,
                requestNumber,
                subject,
                status,
                priority,
                type,
                customerId,
                customerName,
                assignedTo,
                createdDate,
                lastModifiedDate,
                completionDate,
                resolution,
                channel,
                description
        );
        details.setFlexDirection(FlexDirection.COLUMN);
        details.setBoxSizing(BoxSizing.BORDER_BOX);
        details.setMargin(Bottom.L);
        details.setPadding(Bottom.L);
        return details;
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        // Populate the details with data from the customer request
        if (customerRequest != null) {
            requestId.setPrimaryText(customerRequest.getId());
            requestNumber.setPrimaryText(customerRequest.getRequestNumber());
            subject.setPrimaryText(customerRequest.getSubject());
            status.setPrimaryText(customerRequest.getStatus());
            priority.setPrimaryText(customerRequest.getPriority());
            type.setPrimaryText(customerRequest.getType());
            customerId.setPrimaryText(customerRequest.getCustomerId());
            customerName.setPrimaryText(customerRequest.getCustomerName());
            assignedTo.setPrimaryText(customerRequest.getAssignedTo());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            if (customerRequest.getCreatedDate() != null) {
                createdDate.setPrimaryText(customerRequest.getCreatedDate().format(formatter));
            }

            if (customerRequest.getLastModifiedDate() != null) {
                lastModifiedDate.setPrimaryText(customerRequest.getLastModifiedDate().format(formatter));
            }

            if (customerRequest.getCompletionDate() != null) {
                completionDate.setPrimaryText(customerRequest.getCompletionDate().format(formatter));
            }

            resolution.setPrimaryText(customerRequest.getResolution());
            channel.setPrimaryText(customerRequest.getChannel());
            description.setPrimaryText(customerRequest.getDescription());
        }

        initAppBar();
    }

    private AppBar initAppBar() {
        AppBar appBar = MainLayout.get().getAppBar();
        appBar.setNaviMode(AppBar.NaviMode.CONTEXTUAL);
        appBar.getContextIcon().addClickListener(e -> UI.getCurrent().navigate(CustomerRequests.class));
        appBar.setTitle("Customer Request Details");
        return appBar;
    }
}
