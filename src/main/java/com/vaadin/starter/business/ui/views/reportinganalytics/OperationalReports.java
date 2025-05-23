package com.vaadin.starter.business.ui.views.reportinganalytics;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.business.backend.dto.reportinganalytics.OperationalReportDTO;
import com.vaadin.starter.business.backend.service.ReportingAnalyticsService;
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.components.FlexBoxLayout;
import com.vaadin.starter.business.ui.constants.NavigationConstants;
import com.vaadin.starter.business.ui.layout.size.Horizontal;
import com.vaadin.starter.business.ui.layout.size.Top;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.util.css.BoxSizing;
import com.vaadin.starter.business.ui.views.ViewFrame;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@PageTitle(NavigationConstants.OPERATIONAL_REPORTS)
@Route(value = "reporting-analytics/operational-reports", layout = MainLayout.class)
public class OperationalReports extends ViewFrame {

    private final ReportingAnalyticsService reportingAnalyticsService;
    private Grid<Report> reportsGrid;
    private ListDataProvider<Report> reportsDataProvider;
    private Div reportDetailContainer;
    private Report selectedReport;

    @Autowired
    public OperationalReports(ReportingAnalyticsService reportingAnalyticsService) {
        this.reportingAnalyticsService = reportingAnalyticsService;
        setViewContent(createContent());
    }

    private Component createContent() {
        FlexBoxLayout content = new FlexBoxLayout(createHeader(), createMainContent());
        content.setBoxSizing(BoxSizing.BORDER_BOX);
        content.setHeightFull();
        content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);
        content.setFlexDirection(FlexDirection.COLUMN);
        return content;
    }

    private Component createHeader() {
        H3 header = new H3("Operational Reports");
        header.getStyle().set("margin", "0");
        header.getStyle().set("padding", "1rem");
        return header;
    }

    private Component createMainContent() {
        // Create a split layout for the reports list and detail view
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();
        splitLayout.setOrientation(SplitLayout.Orientation.HORIZONTAL);

        // Left side - Reports list with filter
        VerticalLayout leftLayout = new VerticalLayout();
        leftLayout.setPadding(false);
        leftLayout.setSpacing(true);
        leftLayout.setSizeFull();

        // Add filter form
        leftLayout.add(createReportsFilterForm());

        // Add reports grid
        reportsGrid = createReportsGrid();
        leftLayout.add(reportsGrid);
        leftLayout.expand(reportsGrid);

        // Right side - Report detail view
        VerticalLayout rightLayout = new VerticalLayout();
        rightLayout.setPadding(true);
        rightLayout.setSpacing(true);
        rightLayout.setSizeFull();

        reportDetailContainer = new Div();
        reportDetailContainer.setSizeFull();
        reportDetailContainer.getStyle().set("overflow", "auto");

        // Initially show a placeholder
        H4 placeholderTitle = new H4("Select a report to view details");
        placeholderTitle.getStyle().set("color", "var(--lumo-secondary-text-color)");
        placeholderTitle.getStyle().set("text-align", "center");
        placeholderTitle.getStyle().set("margin-top", "40%");

        reportDetailContainer.add(placeholderTitle);
        rightLayout.add(reportDetailContainer);

        // Set the split layout components
        splitLayout.addToPrimary(leftLayout);
        splitLayout.addToSecondary(rightLayout);
        splitLayout.setSplitterPosition(40);

        return splitLayout;
    }

    private Component createReportsFilterForm() {
        // Initialize filter fields
        TextField reportNameFilter = new TextField();
        reportNameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        reportNameFilter.setClearButtonVisible(true);
        reportNameFilter.setPlaceholder("Search by name...");

        ComboBox<String> categoryFilter = new ComboBox<>();
        categoryFilter.setItems("Transactions", "Customer Activity", "Account Operations", "Loan Operations", "Card Operations", "System Performance");
        categoryFilter.setClearButtonVisible(true);
        categoryFilter.setPlaceholder("All Categories");

        ComboBox<String> frequencyFilter = new ComboBox<>();
        frequencyFilter.setItems("Daily", "Weekly", "Monthly", "Quarterly", "Yearly", "On-demand");
        frequencyFilter.setClearButtonVisible(true);
        frequencyFilter.setPlaceholder("All Frequencies");

        DatePicker dateFromFilter = new DatePicker();
        dateFromFilter.setClearButtonVisible(true);
        dateFromFilter.setPlaceholder("From");

        DatePicker dateToFilter = new DatePicker();
        dateToFilter.setClearButtonVisible(true);
        dateToFilter.setPlaceholder("To");

        // Add filter change listeners
        reportNameFilter.addValueChangeListener(e -> {
            filterReports(reportNameFilter.getValue(), 
                          categoryFilter.getValue(), 
                          frequencyFilter.getValue(),
                          dateFromFilter.getValue(),
                          dateToFilter.getValue());
        });

        categoryFilter.addValueChangeListener(e -> {
            filterReports(reportNameFilter.getValue(), 
                          categoryFilter.getValue(), 
                          frequencyFilter.getValue(),
                          dateFromFilter.getValue(),
                          dateToFilter.getValue());
        });

        frequencyFilter.addValueChangeListener(e -> {
            filterReports(reportNameFilter.getValue(), 
                          categoryFilter.getValue(), 
                          frequencyFilter.getValue(),
                          dateFromFilter.getValue(),
                          dateToFilter.getValue());
        });

        dateFromFilter.addValueChangeListener(e -> {
            filterReports(reportNameFilter.getValue(), 
                          categoryFilter.getValue(), 
                          frequencyFilter.getValue(),
                          dateFromFilter.getValue(),
                          dateToFilter.getValue());
        });

        dateToFilter.addValueChangeListener(e -> {
            filterReports(reportNameFilter.getValue(), 
                          categoryFilter.getValue(), 
                          frequencyFilter.getValue(),
                          dateFromFilter.getValue(),
                          dateToFilter.getValue());
        });

        // Create buttons
        Button searchButton = UIUtils.createPrimaryButton("Search");
        searchButton.addClickListener(e -> {
            filterReports(reportNameFilter.getValue(), 
                          categoryFilter.getValue(), 
                          frequencyFilter.getValue(),
                          dateFromFilter.getValue(),
                          dateToFilter.getValue());
        });

        Button clearButton = UIUtils.createTertiaryButton("Clear");
        clearButton.addClickListener(e -> {
            reportNameFilter.clear();
            categoryFilter.clear();
            frequencyFilter.clear();
            dateFromFilter.clear();
            dateToFilter.clear();
            filterReports(null, null, null, null, null);
        });

        // Create button layout
        HorizontalLayout buttonLayout = new HorizontalLayout(searchButton, clearButton);
        buttonLayout.setSpacing(true);

        // Create form layout
        FormLayout formLayout = new FormLayout();
        formLayout.addFormItem(reportNameFilter, "Report Name");
        formLayout.addFormItem(categoryFilter, "Category");
        formLayout.addFormItem(frequencyFilter, "Frequency");
        formLayout.addFormItem(dateFromFilter, "Date From");
        formLayout.addFormItem(dateToFilter, "Date To");

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

    private Grid<Report> createReportsGrid() {
        Grid<Report> grid = new Grid<>();

        // Initialize data provider with data from service
        Collection<Report> reports = getOperationalReportsFromService();
        reportsDataProvider = new ListDataProvider<>(reports);
        grid.setDataProvider(reportsDataProvider);

        grid.setId("reports");
        grid.setSizeFull();

        // Add columns
        grid.addColumn(Report::getReportId)
                .setHeader("Report ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(Report::getReportName)
                .setHeader("Report Name")
                .setSortable(true)
                .setWidth("250px");
        grid.addColumn(Report::getCategory)
                .setHeader("Category")
                .setSortable(true)
                .setWidth("180px");
        grid.addColumn(Report::getFrequency)
                .setHeader("Frequency")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(report -> {
                    if (report.getLastGenerated() != null) {
                        return report.getLastGenerated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    }
                    return "Never";
                })
                .setHeader("Last Generated")
                .setSortable(true)
                .setComparator(Report::getLastGenerated)
                .setWidth("180px");

        // Add row click listener to show report details
        grid.addItemClickListener(event -> {
            selectedReport = event.getItem();
            showReportDetails(selectedReport);
        });

        return grid;
    }

    private void filterReports(String reportName, String category, String frequency, LocalDate dateFrom, LocalDate dateTo) {
        if (reportsDataProvider != null) {
            reportsDataProvider.setFilter(report -> {
                boolean matchesReportName = reportName == null || reportName.isEmpty() || 
                    report.getReportName().toLowerCase().contains(reportName.toLowerCase());

                boolean matchesCategory = category == null || category.isEmpty() || 
                    report.getCategory().equals(category);

                boolean matchesFrequency = frequency == null || frequency.isEmpty() || 
                    report.getFrequency().equals(frequency);

                boolean matchesDateFrom = dateFrom == null || 
                    (report.getLastGenerated() != null && 
                     !report.getLastGenerated().toLocalDate().isBefore(dateFrom));

                boolean matchesDateTo = dateTo == null || 
                    (report.getLastGenerated() != null && 
                     !report.getLastGenerated().toLocalDate().isAfter(dateTo));

                return matchesReportName && matchesCategory && matchesFrequency && 
                       matchesDateFrom && matchesDateTo;
            });
        }
    }

    private void showReportDetails(Report report) {
        reportDetailContainer.removeAll();

        // Create report detail view
        VerticalLayout detailLayout = new VerticalLayout();
        detailLayout.setPadding(false);
        detailLayout.setSpacing(true);

        // Report header
        H3 reportTitle = new H3(report.getReportName());
        reportTitle.getStyle().set("margin-top", "0");

        // Report metadata
        FormLayout metadataLayout = new FormLayout();
        metadataLayout.setResponsiveSteps(
            new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
            new FormLayout.ResponsiveStep("600px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP)
        );

        Span reportIdValue = new Span(report.getReportId());
        Span categoryValue = new Span(report.getCategory());
        Span frequencyValue = new Span(report.getFrequency());
        Span lastGeneratedValue = new Span(report.getLastGenerated() != null ? 
                report.getLastGenerated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : "Never");
        Span createdByValue = new Span(report.getCreatedBy());

        metadataLayout.addFormItem(reportIdValue, "Report ID");
        metadataLayout.addFormItem(categoryValue, "Category");
        metadataLayout.addFormItem(frequencyValue, "Frequency");
        metadataLayout.addFormItem(lastGeneratedValue, "Last Generated");
        metadataLayout.addFormItem(createdByValue, "Created By");

        // Report description
        H4 descriptionTitle = new H4("Description");
        descriptionTitle.getStyle().set("margin-bottom", "0.5em");

        TextArea descriptionArea = new TextArea();
        descriptionArea.setValue(report.getDescription());
        descriptionArea.setReadOnly(true);
        descriptionArea.setWidthFull();
        descriptionArea.setMinHeight("100px");

        // Report parameters
        H4 parametersTitle = new H4("Parameters");
        parametersTitle.getStyle().set("margin-bottom", "0.5em");

        FormLayout parametersLayout = new FormLayout();
        parametersLayout.setResponsiveSteps(
            new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
            new FormLayout.ResponsiveStep("600px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP)
        );

        DatePicker startDate = new DatePicker("Start Date");
        startDate.setValue(LocalDate.now().minusMonths(1));

        DatePicker endDate = new DatePicker("End Date");
        endDate.setValue(LocalDate.now());

        ComboBox<String> formatSelector = new ComboBox<>("Output Format");
        formatSelector.setItems("PDF", "Excel", "CSV", "HTML");
        formatSelector.setValue("PDF");

        parametersLayout.add(startDate, endDate, formatSelector);

        // Generate report button
        Button generateButton = UIUtils.createPrimaryButton("Generate Report");
        generateButton.setIcon(VaadinIcon.DOWNLOAD.create());
        generateButton.addClickListener(e -> {
            Notification notification = new Notification(
                "Report '" + report.getReportName() + "' is being generated. It will be available shortly.",
                5000,
                Notification.Position.MIDDLE
            );
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            notification.open();
        });

        // Add all components to the detail layout
        detailLayout.add(
            reportTitle,
            metadataLayout,
            descriptionTitle,
            descriptionArea,
            parametersTitle,
            parametersLayout,
            generateButton
        );

        reportDetailContainer.add(detailLayout);
    }

    private Collection<Report> getOperationalReportsFromService() {
        // Get operational reports from the service
        Collection<OperationalReportDTO> reportDTOs = reportingAnalyticsService.getOperationalReports();

        // Convert DTOs to view model objects
        return reportDTOs.stream()
                .map(this::convertToViewModel)
                .collect(Collectors.toList());
    }

    private Report convertToViewModel(OperationalReportDTO dto) {
        Report report = new Report();
        report.setReportId(dto.getReportId());
        report.setReportName(dto.getReportName());
        report.setCategory(dto.getCategory());
        report.setFrequency(dto.getFrequency());
        report.setLastGenerated(dto.getLastGenerated());
        report.setCreatedBy(dto.getCreatedBy());
        report.setDescription(dto.getDescription());
        return report;
    }

    // Inner class to represent a report
    public static class Report {
        private String reportId;
        private String reportName;
        private String category;
        private String frequency;
        private LocalDateTime lastGenerated;
        private String createdBy;
        private String description;

        public String getReportId() {
            return reportId;
        }

        public void setReportId(String reportId) {
            this.reportId = reportId;
        }

        public String getReportName() {
            return reportName;
        }

        public void setReportName(String reportName) {
            this.reportName = reportName;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getFrequency() {
            return frequency;
        }

        public void setFrequency(String frequency) {
            this.frequency = frequency;
        }

        public LocalDateTime getLastGenerated() {
            return lastGenerated;
        }

        public void setLastGenerated(LocalDateTime lastGenerated) {
            this.lastGenerated = lastGenerated;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
