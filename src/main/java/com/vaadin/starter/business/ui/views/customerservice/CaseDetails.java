package com.vaadin.starter.business.ui.views.customerservice;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.business.backend.Case;
import com.vaadin.starter.business.backend.service.CaseService;
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

@PageTitle("Case Details")
@Route(value = "customer-service/case-details", layout = MainLayout.class)
public class CaseDetails extends ViewFrame implements HasUrlParameter<String> {

    private ListItem id;
    private ListItem caseNumber;
    private ListItem subject;
    private ListItem description;
    private ListItem status;
    private ListItem priority;
    private ListItem category;
    private ListItem customer;
    private ListItem assignedTo;
    private ListItem createdDate;
    private ListItem lastModifiedDate;
    private ListItem dueDate;
    private ListItem resolution;
    private ListItem source;

    private Case caseItem;
    private final CaseService caseService;

    @Autowired
    public CaseDetails(CaseService caseService) {
        this.caseService = caseService;
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String id) {
        caseItem = caseService.getCaseById(id);
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
        id = new ListItem(VaadinIcon.HASH.create(), "", "ID");
        id.getPrimary().addClassName(LumoStyles.Heading.H2);
        id.setDividerVisible(true);
        id.setId("id");
        id.setReverse(true);

        caseNumber = new ListItem(VaadinIcon.NOTEBOOK.create(), "", "Case Number");
        caseNumber.setDividerVisible(true);
        caseNumber.setId("caseNumber");
        caseNumber.setReverse(true);

        subject = new ListItem(VaadinIcon.CLIPBOARD_TEXT.create(), "", "Subject");
        subject.setDividerVisible(true);
        subject.setId("subject");
        subject.setReverse(true);

        description = new ListItem(VaadinIcon.CLIPBOARD_TEXT.create(), "", "Description");
        description.setDividerVisible(true);
        description.setId("description");
        description.setReverse(true);
        description.setWhiteSpace(WhiteSpace.PRE_LINE);

        status = new ListItem(VaadinIcon.INFO_CIRCLE.create(), "", "Status");
        status.setDividerVisible(true);
        status.setId("status");
        status.setReverse(true);

        priority = new ListItem(VaadinIcon.FLAG.create(), "", "Priority");
        priority.setDividerVisible(true);
        priority.setId("priority");
        priority.setReverse(true);

        category = new ListItem(VaadinIcon.FOLDER.create(), "", "Category");
        category.setDividerVisible(true);
        category.setId("category");
        category.setReverse(true);

        customer = new ListItem(VaadinIcon.USER.create(), "", "Customer");
        customer.setDividerVisible(true);
        customer.setId("customer");
        customer.setReverse(true);

        assignedTo = new ListItem(VaadinIcon.USER_CARD.create(), "", "Assigned To");
        assignedTo.setDividerVisible(true);
        assignedTo.setId("assignedTo");
        assignedTo.setReverse(true);

        createdDate = new ListItem(VaadinIcon.CALENDAR.create(), "", "Created Date");
        createdDate.setDividerVisible(true);
        createdDate.setId("createdDate");
        createdDate.setReverse(true);

        lastModifiedDate = new ListItem(VaadinIcon.CALENDAR_CLOCK.create(), "", "Last Modified Date");
        lastModifiedDate.setDividerVisible(true);
        lastModifiedDate.setId("lastModifiedDate");
        lastModifiedDate.setReverse(true);

        dueDate = new ListItem(VaadinIcon.CALENDAR_CLOCK.create(), "", "Due Date");
        dueDate.setDividerVisible(true);
        dueDate.setId("dueDate");
        dueDate.setReverse(true);

        resolution = new ListItem(VaadinIcon.CHECK_SQUARE.create(), "", "Resolution");
        resolution.setDividerVisible(true);
        resolution.setId("resolution");
        resolution.setReverse(true);
        resolution.setWhiteSpace(WhiteSpace.PRE_LINE);

        source = new ListItem(VaadinIcon.CONNECT.create(), "", "Source");
        source.setReverse(true);
        source.setId("source");

        FlexBoxLayout listItems = new FlexBoxLayout(id, caseNumber, subject, description, status, priority, 
                                                  category, customer, assignedTo, createdDate, 
                                                  lastModifiedDate, dueDate, resolution, source);
        listItems.setFlexDirection(FlexDirection.COLUMN);

        FlexBoxLayout section = new FlexBoxLayout(listItems);
        section.addClassName(BoxShadowBorders.BOTTOM);
        section.setAlignItems(FlexComponent.Alignment.CENTER);
        section.setFlex("1", listItems);
        section.setFlexWrap(FlexWrap.WRAP);
        section.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        section.setPadding(Bottom.L);
        return section;
    }

    private Component createRelatedItemsHeader() {
        Span title = new Span("Related Items");
        title.addClassName(LumoStyles.Heading.H3);

        Button viewAll = new Button("View All");
        viewAll.addClickListener(
                e -> UI.getCurrent().getPage().executeJs("alert('Not implemented yet.');"));
        viewAll.addClassName(LumoStyles.Margin.Left.AUTO);

        FlexBoxLayout header = new FlexBoxLayout(title, viewAll);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setMargin(Bottom.M, Horizontal.RESPONSIVE_L, Top.L);
        return header;
    }

    private Component createRelatedItemsList() {
        Div items = new Div();
        items.addClassNames(BoxShadowBorders.BOTTOM, LumoStyles.Padding.Bottom.L);

        // Add a message if there are no related items
        Span message = new Span("No related items found.");
        message.getStyle().set("display", "block");
        message.getStyle().set("padding", "1em");
        message.getStyle().set("text-align", "center");
        items.add(message);

        return items;
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        initAppBar();
        UI.getCurrent().getPage().setTitle("Case " + caseItem.getCaseNumber());

        // Set values for the case details
        id.setPrimaryText(caseItem.getId());
        caseNumber.setPrimaryText(caseItem.getCaseNumber());
        subject.setPrimaryText(caseItem.getSubject());
        description.setPrimaryText(caseItem.getDescription());
        status.setPrimaryText(caseItem.getStatus());
        priority.setPrimaryText(caseItem.getPriority());
        category.setPrimaryText(caseItem.getCategory());
        
        // Format customer information
        String customerText = caseItem.getCustomerName();
        if (caseItem.getCustomerId() != null && !caseItem.getCustomerId().isEmpty()) {
            customerText += " (" + caseItem.getCustomerId() + ")";
        }
        customer.setPrimaryText(customerText);
        
        assignedTo.setPrimaryText(caseItem.getAssignedTo());

        // Format dates
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (caseItem.getCreatedDate() != null) {
            createdDate.setPrimaryText(caseItem.getCreatedDate().format(formatter));
        }
        if (caseItem.getLastModifiedDate() != null) {
            lastModifiedDate.setPrimaryText(caseItem.getLastModifiedDate().format(formatter));
        }
        if (caseItem.getDueDate() != null) {
            dueDate.setPrimaryText(caseItem.getDueDate().format(formatter));
        }

        resolution.setPrimaryText(caseItem.getResolution() != null ? caseItem.getResolution() : "Not resolved yet");
        source.setPrimaryText(caseItem.getSource());
    }

    private AppBar initAppBar() {
        AppBar appBar = MainLayout.get().getAppBar();
        appBar.setNaviMode(AppBar.NaviMode.CONTEXTUAL);
        appBar.getContextIcon().addClickListener(e -> UI.getCurrent().navigate(CaseManagement.class));
        appBar.setTitle("Case " + caseItem.getCaseNumber());
        return appBar;
    }
}