package com.vaadin.starter.business.ui.views.taskmanagement;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.components.FlexBoxLayout;
import com.vaadin.starter.business.ui.constants.NavigationConstants;
import com.vaadin.starter.business.ui.layout.size.Horizontal;
import com.vaadin.starter.business.ui.layout.size.Top;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.util.css.BoxSizing;
import com.vaadin.starter.business.ui.views.ViewFrame;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@PageTitle(NavigationConstants.WORK_QUEUE)
@Route(value = "task-management/work-queue", layout = MainLayout.class)
public class WorkQueue extends ViewFrame {

    private Grid<Task> grid;
    private TextField searchField;
    private ComboBox<String> statusFilter;
    private ComboBox<String> priorityFilter;
    private Random random = new Random(42); // Fixed seed for reproducible data

    public WorkQueue() {
        setViewContent(createContent());
    }

    private Component createContent() {
        FlexBoxLayout content = new FlexBoxLayout(createHeader(), createFilters(), createGrid());
        content.setBoxSizing(BoxSizing.BORDER_BOX);
        content.setHeightFull();
        content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);
        content.setFlexDirection(FlexDirection.COLUMN);
        return content;
    }

    private Component createHeader() {
        H3 header = new H3("Work Queue");
        header.getStyle().set("margin", "0");
        header.getStyle().set("padding", "1rem");
        return header;
    }

    private Component createFilters() {
        HorizontalLayout filtersLayout = new HorizontalLayout();
        filtersLayout.setWidthFull();
        filtersLayout.setPadding(true);
        filtersLayout.setSpacing(true);
        filtersLayout.getStyle().set("background-color", "var(--lumo-base-color)");
        filtersLayout.getStyle().set("border-radius", "var(--lumo-border-radius)");
        filtersLayout.getStyle().set("box-shadow", "0 0 0 1px var(--lumo-contrast-10pct)");

        // Search field
        searchField = new TextField();
        searchField.setPlaceholder("Search tasks...");
        searchField.setClearButtonVisible(true);
        searchField.setValueChangeMode(ValueChangeMode.LAZY);
        searchField.addValueChangeListener(e -> filterGrid());
        searchField.setPrefixComponent(VaadinIcon.SEARCH.create());

        // Status filter
        statusFilter = new ComboBox<>();
        statusFilter.setPlaceholder("Status");
        statusFilter.setItems("All", "New", "In Progress", "Waiting", "Completed", "Cancelled");
        statusFilter.setValue("All");
        statusFilter.addValueChangeListener(e -> filterGrid());

        // Priority filter
        priorityFilter = new ComboBox<>();
        priorityFilter.setPlaceholder("Priority");
        priorityFilter.setItems("All", "High", "Medium", "Low");
        priorityFilter.setValue("All");
        priorityFilter.addValueChangeListener(e -> filterGrid());

        // Create buttons
        Button refreshButton = UIUtils.createPrimaryButton("Refresh");
        refreshButton.setIcon(VaadinIcon.REFRESH.create());
        refreshButton.addClickListener(e -> refreshGrid());

        Button assignButton = UIUtils.createTertiaryButton("Assign Selected");
        assignButton.setIcon(VaadinIcon.USER_CHECK.create());

        Button exportButton = UIUtils.createTertiaryButton("Export");
        exportButton.setIcon(VaadinIcon.DOWNLOAD.create());

        filtersLayout.add(searchField, statusFilter, priorityFilter, refreshButton, assignButton, exportButton);
        return filtersLayout;
    }

    private Component createGrid() {
        grid = new Grid<>();
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.setHeightFull();

        // Add columns
        grid.addColumn(Task::getId).setHeader("ID").setAutoWidth(true).setFlexGrow(0);
        grid.addColumn(Task::getSubject).setHeader("Subject").setAutoWidth(true).setFlexGrow(1);
        grid.addColumn(new ComponentRenderer<>(task -> {
            Span span = new Span(task.getStatus());
            span.getElement().getThemeList().add("badge");

            switch (task.getStatus()) {
                case "New":
                    span.getElement().getThemeList().add("success");
                    break;
                case "In Progress":
                    span.getElement().getThemeList().add("primary");
                    break;
                case "Waiting":
                    span.getElement().getThemeList().add("contrast");
                    break;
                case "Completed":
                    span.getElement().getThemeList().add("success");
                    break;
                case "Cancelled":
                    span.getElement().getThemeList().add("error");
                    break;
            }

            return span;
        })).setHeader("Status").setAutoWidth(true).setFlexGrow(0);

        grid.addColumn(new ComponentRenderer<>(task -> {
            Span span = new Span(task.getPriority());
            span.getElement().getThemeList().add("badge");

            switch (task.getPriority()) {
                case "High":
                    span.getElement().getThemeList().add("error");
                    break;
                case "Medium":
                    span.getElement().getThemeList().add("contrast");
                    break;
                case "Low":
                    span.getElement().getThemeList().add("success");
                    break;
            }

            return span;
        })).setHeader("Priority").setAutoWidth(true).setFlexGrow(0);

        grid.addColumn(Task::getType).setHeader("Type").setAutoWidth(true).setFlexGrow(0);
        grid.addColumn(Task::getAssignee).setHeader("Assignee").setAutoWidth(true).setFlexGrow(0);
        grid.addColumn(new LocalDateRenderer<>(Task::getDueDate, "MMM dd, yyyy"))
            .setHeader("Due Date").setAutoWidth(true).setFlexGrow(0);
        grid.addColumn(Task::getCustomer).setHeader("Customer").setAutoWidth(true).setFlexGrow(0);

        grid.addColumn(new ComponentRenderer<>(task -> {
            HorizontalLayout actions = new HorizontalLayout();
            actions.setSpacing(true);

            Button viewButton = new Button(VaadinIcon.EYE.create());
            viewButton.getElement().setAttribute("title", "View task details");
            viewButton.addThemeVariants(com.vaadin.flow.component.button.ButtonVariant.LUMO_SMALL, com.vaadin.flow.component.button.ButtonVariant.LUMO_TERTIARY);

            Button editButton = new Button(VaadinIcon.EDIT.create());
            editButton.getElement().setAttribute("title", "Edit task");
            editButton.addThemeVariants(com.vaadin.flow.component.button.ButtonVariant.LUMO_SMALL, com.vaadin.flow.component.button.ButtonVariant.LUMO_TERTIARY);

            Button assignButton = new Button(VaadinIcon.USER_CHECK.create());
            assignButton.getElement().setAttribute("title", "Assign task");
            assignButton.addThemeVariants(com.vaadin.flow.component.button.ButtonVariant.LUMO_SMALL, com.vaadin.flow.component.button.ButtonVariant.LUMO_TERTIARY);

            actions.add(viewButton, editButton, assignButton);
            return actions;
        })).setHeader("Actions").setAutoWidth(true).setFlexGrow(0);

        // Load data
        refreshGrid();

        return grid;
    }

    private void refreshGrid() {
        grid.setItems(generateTasks(100));
    }

    private void filterGrid() {
        List<Task> filteredTasks = new ArrayList<>();
        List<Task> allTasks = generateTasks(100);

        String searchTerm = searchField.getValue().toLowerCase();
        String statusValue = statusFilter.getValue();
        String priorityValue = priorityFilter.getValue();

        for (Task task : allTasks) {
            boolean matchesSearch = searchTerm.isEmpty() || 
                                   task.getSubject().toLowerCase().contains(searchTerm) ||
                                   task.getCustomer().toLowerCase().contains(searchTerm) ||
                                   task.getAssignee().toLowerCase().contains(searchTerm);

            boolean matchesStatus = "All".equals(statusValue) || task.getStatus().equals(statusValue);
            boolean matchesPriority = "All".equals(priorityValue) || task.getPriority().equals(priorityValue);

            if (matchesSearch && matchesStatus && matchesPriority) {
                filteredTasks.add(task);
            }
        }

        grid.setItems(filteredTasks);
    }

    private List<Task> generateTasks(int count) {
        List<Task> tasks = new ArrayList<>();
        String[] subjects = {
            "Customer account verification", "Payment processing issue", "Document review", 
            "Loan application review", "Credit limit increase request", "Fraud alert investigation",
            "Customer complaint", "Account closure request", "Address change verification",
            "Transaction dispute", "Card replacement request", "Statement discrepancy"
        };

        String[] statuses = {"New", "In Progress", "Waiting", "Completed", "Cancelled"};
        String[] priorities = {"High", "Medium", "Low"};
        String[] types = {"Customer Service", "Account Management", "Loan Processing", "Fraud Investigation", "Document Review"};
        String[] assignees = {"John Smith", "Maria Garcia", "Ahmed Khan", "Sarah Johnson", "Unassigned"};
        String[] customers = {"ABC Corp", "Jane Doe", "Global Enterprises", "Local Business LLC", "First Time Customer"};

        for (int i = 1; i <= count; i++) {
            Task task = new Task();
            task.setId("TASK-" + (1000 + i));
            task.setSubject(subjects[random.nextInt(subjects.length)]);
            task.setStatus(statuses[random.nextInt(statuses.length)]);
            task.setPriority(priorities[random.nextInt(priorities.length)]);
            task.setType(types[random.nextInt(types.length)]);
            task.setAssignee(assignees[random.nextInt(assignees.length)]);
            task.setDueDate(LocalDate.now().plusDays(random.nextInt(14)));
            task.setCustomer(customers[random.nextInt(customers.length)]);
            tasks.add(task);
        }

        return tasks;
    }

    // Task data class
    public static class Task {
        private String id;
        private String subject;
        private String status;
        private String priority;
        private String type;
        private String assignee;
        private LocalDate dueDate;
        private String customer;

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public String getSubject() { return subject; }
        public void setSubject(String subject) { this.subject = subject; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public String getPriority() { return priority; }
        public void setPriority(String priority) { this.priority = priority; }

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }

        public String getAssignee() { return assignee; }
        public void setAssignee(String assignee) { this.assignee = assignee; }

        public LocalDate getDueDate() { return dueDate; }
        public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

        public String getCustomer() { return customer; }
        public void setCustomer(String customer) { this.customer = customer; }
    }
}
