package com.vaadin.starter.business.ui.views.customerservice;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
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
import com.vaadin.starter.business.backend.CustomerRequest;
import com.vaadin.starter.business.backend.service.CustomerRequestService;
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.components.FlexBoxLayout;
import com.vaadin.starter.business.ui.constants.NavigationConstants;
import com.vaadin.starter.business.ui.layout.size.Horizontal;
import com.vaadin.starter.business.ui.layout.size.Top;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.util.css.BoxSizing;
import com.vaadin.starter.business.ui.views.ViewFrame;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.format.DateTimeFormatter;
import java.util.Collection;

@PageTitle(NavigationConstants.CUSTOMER_REQUESTS)
@Route(value = "customer-service/requests", layout = MainLayout.class)
public class CustomerRequests extends ViewFrame {

    private Grid<CustomerRequest> grid;
    private ListDataProvider<CustomerRequest> dataProvider;

    // Filter form fields
    private TextField idFilter;
    private TextField requestNumberFilter;
    private TextField subjectFilter;
    private ComboBox<String> statusFilter;
    private ComboBox<String> priorityFilter;
    private ComboBox<String> typeFilter;
    private TextField customerIdFilter;
    private TextField customerNameFilter;
    private TextField assignedToFilter;
    private DatePicker createdDateFromFilter;
    private DatePicker createdDateToFilter;
    private ComboBox<String> channelFilter;

    private final CustomerRequestService customerRequestService;

    @Autowired
    public CustomerRequests(CustomerRequestService customerRequestService) {
        this.customerRequestService = customerRequestService;
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

    private void viewDetails(CustomerRequest request) {
        UI.getCurrent().navigate(CustomerRequestDetails.class, request.getId());
    }

    private Component createFilterForm() {
        // Initialize filter fields
        idFilter = new TextField();
        idFilter.setValueChangeMode(ValueChangeMode.EAGER);
        idFilter.setClearButtonVisible(true);

        requestNumberFilter = new TextField();
        requestNumberFilter.setValueChangeMode(ValueChangeMode.EAGER);
        requestNumberFilter.setClearButtonVisible(true);

        subjectFilter = new TextField();
        subjectFilter.setValueChangeMode(ValueChangeMode.EAGER);
        subjectFilter.setClearButtonVisible(true);

        statusFilter = new ComboBox<>();
        statusFilter.setItems(getRequestStatuses());
        statusFilter.setClearButtonVisible(true);

        priorityFilter = new ComboBox<>();
        priorityFilter.setItems(getRequestPriorities());
        priorityFilter.setClearButtonVisible(true);

        typeFilter = new ComboBox<>();
        typeFilter.setItems(getRequestTypes());
        typeFilter.setClearButtonVisible(true);

        customerIdFilter = new TextField();
        customerIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        customerIdFilter.setClearButtonVisible(true);

        customerNameFilter = new TextField();
        customerNameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        customerNameFilter.setClearButtonVisible(true);

        assignedToFilter = new TextField();
        assignedToFilter.setValueChangeMode(ValueChangeMode.EAGER);
        assignedToFilter.setClearButtonVisible(true);

        createdDateFromFilter = new DatePicker();
        createdDateFromFilter.setClearButtonVisible(true);

        createdDateToFilter = new DatePicker();
        createdDateToFilter.setClearButtonVisible(true);

        channelFilter = new ComboBox<>();
        channelFilter.setItems(getRequestChannels());
        channelFilter.setClearButtonVisible(true);

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
        formLayout.addFormItem(requestNumberFilter, "Request Number");
        formLayout.addFormItem(subjectFilter, "Subject");
        formLayout.addFormItem(statusFilter, "Status");
        formLayout.addFormItem(priorityFilter, "Priority");
        formLayout.addFormItem(typeFilter, "Type");
        formLayout.addFormItem(customerIdFilter, "Customer ID");
        formLayout.addFormItem(customerNameFilter, "Customer Name");
        formLayout.addFormItem(assignedToFilter, "Assigned To");
        formLayout.addFormItem(createdDateFromFilter, "Created From");
        formLayout.addFormItem(createdDateToFilter, "Created To");
        formLayout.addFormItem(channelFilter, "Channel");

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

    private Grid<CustomerRequest> createGrid() {
        grid = new Grid<>();
        grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(this::viewDetails));

        // Initialize data provider
        Collection<CustomerRequest> requests = customerRequestService.getCustomerRequests();
        dataProvider = new ListDataProvider<>(requests);
        grid.setDataProvider(dataProvider);

        grid.setId("customerRequests");
        grid.setSizeFull();

        // Add columns
        grid.addColumn(CustomerRequest::getId)
                .setHeader("ID")
                .setSortable(true)
                .setWidth("100px");
        grid.addColumn(CustomerRequest::getRequestNumber)
                .setHeader("Request Number")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(CustomerRequest::getSubject)
                .setHeader("Subject")
                .setSortable(true)
                .setWidth("250px");
        grid.addColumn(CustomerRequest::getStatus)
                .setHeader("Status")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(CustomerRequest::getPriority)
                .setHeader("Priority")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(CustomerRequest::getType)
                .setHeader("Type")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(CustomerRequest::getCustomerName)
                .setHeader("Customer")
                .setSortable(true)
                .setWidth("200px");
        grid.addColumn(CustomerRequest::getAssignedTo)
                .setHeader("Assigned To")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(request -> {
                    if (request.getCreatedDate() != null) {
                        return request.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    }
                    return "";
                })
                .setHeader("Created Date")
                .setSortable(true)
                .setComparator(CustomerRequest::getCreatedDate)
                .setWidth("150px");
        grid.addColumn(request -> {
                    if (request.getCompletionDate() != null) {
                        return request.getCompletionDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    }
                    return "";
                })
                .setHeader("Completion Date")
                .setSortable(true)
                .setComparator(CustomerRequest::getCompletionDate)
                .setWidth("150px");
        grid.addColumn(CustomerRequest::getChannel)
                .setHeader("Channel")
                .setSortable(true)
                .setWidth("120px");

        return grid;
    }

    private void applyFilters() {
        dataProvider.clearFilters();

        // Apply ID filter
        if (idFilter.getValue() != null && !idFilter.getValue().isEmpty()) {
            String idValue = idFilter.getValue();
            dataProvider.addFilter(request -> 
                request.getId() != null && 
                request.getId().contains(idValue));
        }

        // Apply request number filter
        if (requestNumberFilter.getValue() != null && !requestNumberFilter.getValue().isEmpty()) {
            String requestNumberValue = requestNumberFilter.getValue().toLowerCase();
            dataProvider.addFilter(request -> 
                request.getRequestNumber() != null && 
                request.getRequestNumber().toLowerCase().contains(requestNumberValue));
        }

        // Apply subject filter
        if (subjectFilter.getValue() != null && !subjectFilter.getValue().isEmpty()) {
            String subjectValue = subjectFilter.getValue().toLowerCase();
            dataProvider.addFilter(request -> 
                request.getSubject() != null && 
                request.getSubject().toLowerCase().contains(subjectValue));
        }

        // Apply status filter
        if (statusFilter.getValue() != null) {
            String statusValue = statusFilter.getValue();
            dataProvider.addFilter(request -> 
                request.getStatus() != null && 
                request.getStatus().equals(statusValue));
        }

        // Apply priority filter
        if (priorityFilter.getValue() != null) {
            String priorityValue = priorityFilter.getValue();
            dataProvider.addFilter(request -> 
                request.getPriority() != null && 
                request.getPriority().equals(priorityValue));
        }

        // Apply type filter
        if (typeFilter.getValue() != null) {
            String typeValue = typeFilter.getValue();
            dataProvider.addFilter(request -> 
                request.getType() != null && 
                request.getType().equals(typeValue));
        }

        // Apply customer ID filter
        if (customerIdFilter.getValue() != null && !customerIdFilter.getValue().isEmpty()) {
            String customerIdValue = customerIdFilter.getValue().toLowerCase();
            dataProvider.addFilter(request -> 
                request.getCustomerId() != null && 
                request.getCustomerId().toLowerCase().contains(customerIdValue));
        }

        // Apply customer name filter
        if (customerNameFilter.getValue() != null && !customerNameFilter.getValue().isEmpty()) {
            String customerNameValue = customerNameFilter.getValue().toLowerCase();
            dataProvider.addFilter(request -> 
                request.getCustomerName() != null && 
                request.getCustomerName().toLowerCase().contains(customerNameValue));
        }

        // Apply assigned to filter
        if (assignedToFilter.getValue() != null && !assignedToFilter.getValue().isEmpty()) {
            String assignedToValue = assignedToFilter.getValue().toLowerCase();
            dataProvider.addFilter(request -> 
                request.getAssignedTo() != null && 
                request.getAssignedTo().toLowerCase().contains(assignedToValue));
        }

        // Apply created date from filter
        if (createdDateFromFilter.getValue() != null) {
            java.time.LocalDate fromDate = createdDateFromFilter.getValue();
            dataProvider.addFilter(request -> 
                request.getCreatedDate() != null && 
                !request.getCreatedDate().toLocalDate().isBefore(fromDate));
        }

        // Apply created date to filter
        if (createdDateToFilter.getValue() != null) {
            java.time.LocalDate toDate = createdDateToFilter.getValue();
            dataProvider.addFilter(request -> 
                request.getCreatedDate() != null && 
                !request.getCreatedDate().toLocalDate().isAfter(toDate));
        }

        // Apply channel filter
        if (channelFilter.getValue() != null) {
            String channelValue = channelFilter.getValue();
            dataProvider.addFilter(request -> 
                request.getChannel() != null && 
                request.getChannel().equals(channelValue));
        }
    }

    private void clearFilters() {
        // Clear all filter fields
        idFilter.clear();
        requestNumberFilter.clear();
        subjectFilter.clear();
        statusFilter.clear();
        priorityFilter.clear();
        typeFilter.clear();
        customerIdFilter.clear();
        customerNameFilter.clear();
        assignedToFilter.clear();
        createdDateFromFilter.clear();
        createdDateToFilter.clear();
        channelFilter.clear();

        // Clear all filters from data provider
        dataProvider.clearFilters();
    }

    private String[] getRequestStatuses() {
        CustomerRequest.Status[] statuses = CustomerRequest.Status.values();
        String[] statusNames = new String[statuses.length];
        for (int i = 0; i < statuses.length; i++) {
            statusNames[i] = statuses[i].getName();
        }
        return statusNames;
    }

    private String[] getRequestPriorities() {
        CustomerRequest.Priority[] priorities = CustomerRequest.Priority.values();
        String[] priorityNames = new String[priorities.length];
        for (int i = 0; i < priorities.length; i++) {
            priorityNames[i] = priorities[i].getName();
        }
        return priorityNames;
    }

    private String[] getRequestTypes() {
        CustomerRequest.Type[] types = CustomerRequest.Type.values();
        String[] typeNames = new String[types.length];
        for (int i = 0; i < types.length; i++) {
            typeNames[i] = types[i].getName();
        }
        return typeNames;
    }

    private String[] getRequestChannels() {
        CustomerRequest.Channel[] channels = CustomerRequest.Channel.values();
        String[] channelNames = new String[channels.length];
        for (int i = 0; i < channels.length; i++) {
            channelNames[i] = channels[i].getName();
        }
        return channelNames;
    }
}