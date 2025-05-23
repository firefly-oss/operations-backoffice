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

@PageTitle("Investigation Details")
@Route(value = "fraud-risk/investigation-details", layout = MainLayout.class)
public class InvestigationDetails extends ViewFrame implements HasUrlParameter<String> {

    private ListItem caseId;
    private ListItem subject;
    private ListItem type;
    private ListItem status;
    private ListItem priority;
    private ListItem assignedTo;
    private ListItem openedDate;
    private ListItem lastUpdated;
    private ListItem description;

    private Investigations.Investigation investigation;

    @Override
    public void setParameter(BeforeEvent beforeEvent, String caseId) {
        // In a real application, this would fetch the investigation from a service
        // For this example, we'll create a mock investigation
        investigation = createMockInvestigation(caseId);
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
        caseId = new ListItem(VaadinIcon.HASH.create(), "", "Case ID");
        caseId.getPrimary().addClassName(LumoStyles.Heading.H2);
        caseId.setDividerVisible(true);
        caseId.setId("caseId");
        caseId.setReverse(true);

        subject = new ListItem(VaadinIcon.CLIPBOARD_TEXT.create(), "", "Subject");
        subject.setDividerVisible(true);
        subject.setId("subject");
        subject.setReverse(true);

        type = new ListItem(VaadinIcon.FOLDER.create(), "", "Type");
        type.setDividerVisible(true);
        type.setId("type");
        type.setReverse(true);

        status = new ListItem(VaadinIcon.FLAG.create(), "", "Status");
        status.setDividerVisible(true);
        status.setId("status");
        status.setReverse(true);

        priority = new ListItem(VaadinIcon.ARROW_UP.create(), "", "Priority");
        priority.setDividerVisible(true);
        priority.setId("priority");
        priority.setReverse(true);

        assignedTo = new ListItem(VaadinIcon.USER_CARD.create(), "", "Assigned To");
        assignedTo.setDividerVisible(true);
        assignedTo.setId("assignedTo");
        assignedTo.setReverse(true);

        openedDate = new ListItem(VaadinIcon.CALENDAR.create(), "", "Opened Date");
        openedDate.setDividerVisible(true);
        openedDate.setId("openedDate");
        openedDate.setReverse(true);

        lastUpdated = new ListItem(VaadinIcon.CALENDAR_CLOCK.create(), "", "Last Updated");
        lastUpdated.setDividerVisible(true);
        lastUpdated.setId("lastUpdated");
        lastUpdated.setReverse(true);

        description = new ListItem(VaadinIcon.CLIPBOARD_TEXT.create(), "", "Description");
        description.setDividerVisible(true);
        description.setId("description");
        description.setReverse(true);
        description.setWhiteSpace(WhiteSpace.PRE_LINE);

        // Populate fields with data
        caseId.setPrimaryText(investigation.getCaseId());
        subject.setPrimaryText(investigation.getSubject());
        type.setPrimaryText(investigation.getType());
        status.setPrimaryText(investigation.getStatus());
        priority.setPrimaryText(investigation.getPriority());
        assignedTo.setPrimaryText(investigation.getAssignedTo());
        
        if (investigation.getOpenedDate() != null) {
            openedDate.setPrimaryText(investigation.getOpenedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        }
        
        if (investigation.getLastUpdated() != null) {
            lastUpdated.setPrimaryText(investigation.getLastUpdated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        }
        
        description.setPrimaryText(investigation.getDescription());

        FlexBoxLayout listItems = new FlexBoxLayout(
                caseId,
                subject,
                type,
                status,
                priority,
                assignedTo,
                openedDate,
                lastUpdated,
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
            UI.getCurrent().navigate("fraud-risk/investigations");
        });

        Button assignButton = new Button("Assign", VaadinIcon.USER_CARD.create());
        assignButton.addClickListener(e -> {
            // In a real application, this would open a dialog to assign the investigation
            UI.getCurrent().navigate("fraud-risk/investigations");
        });

        Button closeButton = new Button("Close Investigation", VaadinIcon.CHECK.create());
        closeButton.addClickListener(e -> {
            // In a real application, this would close the investigation
            UI.getCurrent().navigate("fraud-risk/investigations");
        });

        Button addNoteButton = new Button("Add Note", VaadinIcon.COMMENT.create());
        addNoteButton.addClickListener(e -> {
            // In a real application, this would open a dialog to add a note
            UI.getCurrent().navigate("fraud-risk/investigations");
        });

        FlexBoxLayout actions = new FlexBoxLayout(
                updateButton,
                assignButton,
                closeButton,
                addNoteButton
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
        appBar.getContextIcon().addClickListener(e -> UI.getCurrent().navigate("fraud-risk/investigations"));
        appBar.setTitle("Investigation: " + investigation.getCaseId());
        return appBar;
    }

    private Investigations.Investigation createMockInvestigation(String caseId) {
        Investigations.Investigation investigation = new Investigations.Investigation();
        investigation.setCaseId(caseId);
        investigation.setSubject("Suspicious transaction pattern detected");
        investigation.setType("Fraud");
        investigation.setStatus("In Progress");
        investigation.setPriority("High");
        investigation.setAssignedTo("John Smith");
        investigation.setOpenedDate(java.time.LocalDateTime.now().minusDays(5));
        investigation.setLastUpdated(java.time.LocalDateTime.now().minusHours(12));
        investigation.setDescription("Investigation into suspicious transaction patterns across multiple accounts. Customer made several large transfers to offshore accounts within a short timeframe.");
        return investigation;
    }
}