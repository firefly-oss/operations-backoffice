package com.vaadin.starter.business.ui.views.customerservice;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.business.backend.ServiceTicket;
import com.vaadin.starter.business.backend.service.ServiceTicketService;
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.components.FlexBoxLayout;
import com.vaadin.starter.business.ui.constants.NavigationConstants;
import com.vaadin.starter.business.ui.layout.size.Horizontal;
import com.vaadin.starter.business.ui.layout.size.Top;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.util.css.BoxSizing;
import com.vaadin.starter.business.ui.views.ViewFrame;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@PageTitle(NavigationConstants.SERVICE_TICKETS)
@Route(value = "service-tickets", layout = MainLayout.class)
public class ServiceTickets extends ViewFrame {

    private final ServiceTicketService serviceTicketService;
    private Grid<ServiceTicket> grid;
    private ListDataProvider<ServiceTicket> dataProvider;

    // Filter fields
    private TextField idFilter;
    private TextField ticketNumberFilter;
    private TextField subjectFilter;
    private ComboBox<String> statusFilter;
    private ComboBox<String> priorityFilter;
    private ComboBox<String> categoryFilter;
    private ComboBox<String> serviceTypeFilter;
    private TextField customerIdFilter;
    private TextField customerNameFilter;
    private TextField assignedToFilter;
    private ComboBox<String> departmentFilter;
    private DatePicker createdDateFromFilter;
    private DatePicker createdDateToFilter;
    private DatePicker dueDateFromFilter;
    private DatePicker dueDateToFilter;

    @Autowired
    public ServiceTickets(ServiceTicketService serviceTicketService) {
        this.serviceTicketService = serviceTicketService;
        setViewContent(createContent());
    }

    private Component createContent() {
        FlexBoxLayout content = new FlexBoxLayout(createFilterForm(), createGrid());
        content.setBoxSizing(BoxSizing.BORDER_BOX);
        content.setHeightFull();
        content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);
        content.setFlexDirection(FlexDirection.COLUMN);
        return content;
    }

    private void viewDetails(ServiceTicket ticket) {
        // TODO: Create ServiceTicketDetails view and uncomment the line below
        // UI.getCurrent().navigate(ServiceTicketDetails.class, ticket.getId());
    }

    private Component createFilterForm() {
        // Initialize filter fields
        idFilter = new TextField();
        idFilter.setValueChangeMode(ValueChangeMode.EAGER);
        idFilter.setClearButtonVisible(true);

        ticketNumberFilter = new TextField();
        ticketNumberFilter.setValueChangeMode(ValueChangeMode.EAGER);
        ticketNumberFilter.setClearButtonVisible(true);

        subjectFilter = new TextField();
        subjectFilter.setValueChangeMode(ValueChangeMode.EAGER);
        subjectFilter.setClearButtonVisible(true);

        statusFilter = new ComboBox<>();
        statusFilter.setItems(getTicketStatuses());
        statusFilter.setClearButtonVisible(true);

        priorityFilter = new ComboBox<>();
        priorityFilter.setItems(getTicketPriorities());
        priorityFilter.setClearButtonVisible(true);

        categoryFilter = new ComboBox<>();
        categoryFilter.setItems(getTicketCategories());
        categoryFilter.setClearButtonVisible(true);

        serviceTypeFilter = new ComboBox<>();
        serviceTypeFilter.setItems(getServiceTypes());
        serviceTypeFilter.setClearButtonVisible(true);

        customerIdFilter = new TextField();
        customerIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        customerIdFilter.setClearButtonVisible(true);

        customerNameFilter = new TextField();
        customerNameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        customerNameFilter.setClearButtonVisible(true);

        assignedToFilter = new TextField();
        assignedToFilter.setValueChangeMode(ValueChangeMode.EAGER);
        assignedToFilter.setClearButtonVisible(true);

        departmentFilter = new ComboBox<>();
        departmentFilter.setItems(getDepartments());
        departmentFilter.setClearButtonVisible(true);

        createdDateFromFilter = new DatePicker();
        createdDateFromFilter.setClearButtonVisible(true);

        createdDateToFilter = new DatePicker();
        createdDateToFilter.setClearButtonVisible(true);

        dueDateFromFilter = new DatePicker();
        dueDateFromFilter.setClearButtonVisible(true);

        dueDateToFilter = new DatePicker();
        dueDateToFilter.setClearButtonVisible(true);

        // Create buttons
        Button searchButton = UIUtils.createPrimaryButton("Search");
        searchButton.addClickListener(e -> applyFilters());

        Button clearButton = UIUtils.createTertiaryButton("Clear");
        clearButton.addClickListener(e -> clearFilters());

        // Create button layout
        HorizontalLayout buttonLayout = new HorizontalLayout(searchButton, clearButton);
        buttonLayout.setSpacing(true);

        // Create form layout
        FormLayout formLayout = new FormLayout();
        formLayout.addFormItem(idFilter, "ID");
        formLayout.addFormItem(ticketNumberFilter, "Ticket Number");
        formLayout.addFormItem(subjectFilter, "Subject");
        formLayout.addFormItem(statusFilter, "Status");
        formLayout.addFormItem(priorityFilter, "Priority");
        formLayout.addFormItem(categoryFilter, "Category");
        formLayout.addFormItem(serviceTypeFilter, "Service Type");
        formLayout.addFormItem(customerIdFilter, "Customer ID");
        formLayout.addFormItem(customerNameFilter, "Customer Name");
        formLayout.addFormItem(assignedToFilter, "Assigned To");
        formLayout.addFormItem(departmentFilter, "Department");
        formLayout.addFormItem(createdDateFromFilter, "Created Date From");
        formLayout.addFormItem(createdDateToFilter, "Created Date To");
        formLayout.addFormItem(dueDateFromFilter, "Due Date From");
        formLayout.addFormItem(dueDateToFilter, "Due Date To");

        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("600px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("900px", 4, FormLayout.ResponsiveStep.LabelsPosition.TOP)
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

    private Grid<ServiceTicket> createGrid() {
        grid = new Grid<>();
        dataProvider = new ListDataProvider<>(serviceTicketService.getServiceTickets());
        grid.setDataProvider(dataProvider);

        // Add columns to the grid
        grid.addColumn(ServiceTicket::getId)
                .setHeader("ID")
                .setFlexGrow(1)
                .setSortable(true);

        grid.addColumn(ServiceTicket::getTicketNumber)
                .setHeader("Ticket Number")
                .setFlexGrow(1)
                .setSortable(true);

        grid.addColumn(ServiceTicket::getSubject)
                .setHeader("Subject")
                .setFlexGrow(2)
                .setSortable(true);

        grid.addColumn(ServiceTicket::getStatus)
                .setHeader("Status")
                .setFlexGrow(1)
                .setSortable(true);

        grid.addColumn(ServiceTicket::getPriority)
                .setHeader("Priority")
                .setFlexGrow(1)
                .setSortable(true);

        grid.addColumn(ServiceTicket::getCategory)
                .setHeader("Category")
                .setFlexGrow(1)
                .setSortable(true);

        grid.addColumn(ServiceTicket::getServiceType)
                .setHeader("Service Type")
                .setFlexGrow(1)
                .setSortable(true);

        grid.addColumn(ServiceTicket::getCustomerName)
                .setHeader("Customer Name")
                .setFlexGrow(1)
                .setSortable(true);

        grid.addColumn(ServiceTicket::getAssignedTo)
                .setHeader("Assigned To")
                .setFlexGrow(1)
                .setSortable(true);

        grid.addColumn(ServiceTicket::getDepartment)
                .setHeader("Department")
                .setFlexGrow(1)
                .setSortable(true);

        grid.addColumn(ticket -> {
            LocalDateTime createdDate = ticket.getCreatedDate();
            if (createdDate != null) {
                return createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }
            return "";
        })
                .setHeader("Created Date")
                .setFlexGrow(1)
                .setSortable(true);

        grid.addColumn(ticket -> {
            LocalDateTime dueDate = ticket.getDueDate();
            if (dueDate != null) {
                return dueDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }
            return "";
        })
                .setHeader("Due Date")
                .setFlexGrow(1)
                .setSortable(true);

        // Add click listener to rows
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                viewDetails(event.getValue());
            }
        });

        grid.setHeightFull();
        return grid;
    }

    private void applyFilters() {
        dataProvider.setFilter(ticket -> {
            boolean matchesId = idFilter.getValue().isEmpty() || 
                    ticket.getId().toLowerCase().contains(idFilter.getValue().toLowerCase());

            boolean matchesTicketNumber = ticketNumberFilter.getValue().isEmpty() || 
                    (ticket.getTicketNumber() != null && 
                    ticket.getTicketNumber().toLowerCase().contains(ticketNumberFilter.getValue().toLowerCase()));

            boolean matchesSubject = subjectFilter.getValue().isEmpty() || 
                    (ticket.getSubject() != null && 
                    ticket.getSubject().toLowerCase().contains(subjectFilter.getValue().toLowerCase()));

            boolean matchesStatus = statusFilter.getValue() == null || 
                    (ticket.getStatus() != null && 
                    ticket.getStatus().equals(statusFilter.getValue()));

            boolean matchesPriority = priorityFilter.getValue() == null || 
                    (ticket.getPriority() != null && 
                    ticket.getPriority().equals(priorityFilter.getValue()));

            boolean matchesCategory = categoryFilter.getValue() == null || 
                    (ticket.getCategory() != null && 
                    ticket.getCategory().equals(categoryFilter.getValue()));

            boolean matchesServiceType = serviceTypeFilter.getValue() == null || 
                    (ticket.getServiceType() != null && 
                    ticket.getServiceType().equals(serviceTypeFilter.getValue()));

            boolean matchesCustomerId = customerIdFilter.getValue().isEmpty() || 
                    (ticket.getCustomerId() != null && 
                    ticket.getCustomerId().toLowerCase().contains(customerIdFilter.getValue().toLowerCase()));

            boolean matchesCustomerName = customerNameFilter.getValue().isEmpty() || 
                    (ticket.getCustomerName() != null && 
                    ticket.getCustomerName().toLowerCase().contains(customerNameFilter.getValue().toLowerCase()));

            boolean matchesAssignedTo = assignedToFilter.getValue().isEmpty() || 
                    (ticket.getAssignedTo() != null && 
                    ticket.getAssignedTo().toLowerCase().contains(assignedToFilter.getValue().toLowerCase()));

            boolean matchesDepartment = departmentFilter.getValue() == null || 
                    (ticket.getDepartment() != null && 
                    ticket.getDepartment().equals(departmentFilter.getValue()));

            boolean matchesCreatedDateFrom = createdDateFromFilter.getValue() == null || 
                    (ticket.getCreatedDate() != null && 
                    !ticket.getCreatedDate().toLocalDate().isBefore(createdDateFromFilter.getValue()));

            boolean matchesCreatedDateTo = createdDateToFilter.getValue() == null || 
                    (ticket.getCreatedDate() != null && 
                    !ticket.getCreatedDate().toLocalDate().isAfter(createdDateToFilter.getValue()));

            boolean matchesDueDateFrom = dueDateFromFilter.getValue() == null || 
                    (ticket.getDueDate() != null && 
                    !ticket.getDueDate().toLocalDate().isBefore(dueDateFromFilter.getValue()));

            boolean matchesDueDateTo = dueDateToFilter.getValue() == null || 
                    (ticket.getDueDate() != null && 
                    !ticket.getDueDate().toLocalDate().isAfter(dueDateToFilter.getValue()));

            return matchesId && matchesTicketNumber && matchesSubject && matchesStatus && 
                   matchesPriority && matchesCategory && matchesServiceType && matchesCustomerId && 
                   matchesCustomerName && matchesAssignedTo && matchesDepartment && 
                   matchesCreatedDateFrom && matchesCreatedDateTo && matchesDueDateFrom && matchesDueDateTo;
        });
    }

    private void clearFilters() {
        idFilter.clear();
        ticketNumberFilter.clear();
        subjectFilter.clear();
        statusFilter.clear();
        priorityFilter.clear();
        categoryFilter.clear();
        serviceTypeFilter.clear();
        customerIdFilter.clear();
        customerNameFilter.clear();
        assignedToFilter.clear();
        departmentFilter.clear();
        createdDateFromFilter.clear();
        createdDateToFilter.clear();
        dueDateFromFilter.clear();
        dueDateToFilter.clear();
        dataProvider.clearFilters();
    }

    private String[] getTicketStatuses() {
        return java.util.Arrays.stream(ServiceTicket.Status.values())
                .map(ServiceTicket.Status::getName)
                .toArray(String[]::new);
    }

    private String[] getTicketPriorities() {
        return java.util.Arrays.stream(ServiceTicket.Priority.values())
                .map(ServiceTicket.Priority::getName)
                .toArray(String[]::new);
    }

    private String[] getTicketCategories() {
        return java.util.Arrays.stream(ServiceTicket.Category.values())
                .map(ServiceTicket.Category::getName)
                .toArray(String[]::new);
    }

    private String[] getServiceTypes() {
        return java.util.Arrays.stream(ServiceTicket.ServiceType.values())
                .map(ServiceTicket.ServiceType::getName)
                .toArray(String[]::new);
    }

    private String[] getDepartments() {
        return java.util.Arrays.stream(ServiceTicket.Department.values())
                .map(ServiceTicket.Department::getName)
                .toArray(String[]::new);
    }
}
