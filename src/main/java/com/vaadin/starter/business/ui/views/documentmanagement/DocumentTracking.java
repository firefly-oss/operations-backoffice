package com.vaadin.starter.business.ui.views.documentmanagement;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.business.backend.dto.documentmanagement.DocumentTrackingEntryDTO;
import com.vaadin.starter.business.backend.mapper.DocumentTrackingEntryMapper;
import com.vaadin.starter.business.backend.service.DocumentService;
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.components.Badge;
import com.vaadin.starter.business.ui.constants.NavigationConstants;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.util.css.lumo.BadgeColor;
import com.vaadin.starter.business.ui.util.css.lumo.BadgeShape;
import com.vaadin.starter.business.ui.util.css.lumo.BadgeSize;
import com.vaadin.starter.business.ui.views.ViewFrame;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle(NavigationConstants.DOCUMENT_TRACKING)
@Route(value = "document-management/document-tracking", layout = MainLayout.class)
public class DocumentTracking extends ViewFrame {

    private Grid<DocumentTrackingEntry> grid;
    private ListDataProvider<DocumentTrackingEntry> dataProvider;

    private final DocumentService documentService;
    private final DocumentTrackingEntryMapper documentTrackingEntryMapper;

    @Autowired
    public DocumentTracking(DocumentService documentService, DocumentTrackingEntryMapper documentTrackingEntryMapper) {
        this.documentService = documentService;
        this.documentTrackingEntryMapper = documentTrackingEntryMapper;
        setViewContent(createContent());
    }

    private Component createContent() {
        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setPadding(false);
        content.setSpacing(false);

        content.add(createHeader());
        content.add(createDashboardStats());
        content.add(createCharts());
        content.add(createFilterForm());
        content.add(createGrid());

        return content;
    }

    private Component createHeader() {
        H3 header = new H3("Document Tracking Dashboard");
        header.getStyle().set("margin", "0");
        header.getStyle().set("padding", "1rem");
        return header;
    }

    private Component createDashboardStats() {
        HorizontalLayout statsLayout = new HorizontalLayout();
        statsLayout.setWidthFull();
        statsLayout.setSpacing(true);
        statsLayout.setPadding(true);

        // Add stat cards
        statsLayout.add(createStatCard("Pending Documents", "24", BadgeColor.CONTRAST));
        statsLayout.add(createStatCard("Processing Documents", "47", BadgeColor.CONTRAST_PRIMARY));
        statsLayout.add(createStatCard("Completed Documents", "83", BadgeColor.SUCCESS));
        statsLayout.add(createStatCard("Rejected Documents", "18", BadgeColor.ERROR));

        return statsLayout;
    }

    private Component createStatCard(String title, String value, BadgeColor color) {
        Div card = new Div();
        card.getStyle().set("background-color", "var(--lumo-base-color)");
        card.getStyle().set("border-radius", "var(--lumo-border-radius)");
        card.getStyle().set("box-shadow", "0 0 0 1px var(--lumo-contrast-10pct)");
        card.getStyle().set("padding", "1rem");
        card.getStyle().set("text-align", "center");
        card.getStyle().set("flex", "1");

        Span titleSpan = new Span(title);
        titleSpan.getStyle().set("font-size", "var(--lumo-font-size-s)");
        titleSpan.getStyle().set("color", "var(--lumo-secondary-text-color)");
        titleSpan.getStyle().set("display", "block");

        Span valueSpan = new Span(value);
        valueSpan.getStyle().set("font-size", "var(--lumo-font-size-xxl)");
        valueSpan.getStyle().set("font-weight", "bold");
        valueSpan.getStyle().set("color", "var(--lumo-primary-text-color)");
        valueSpan.getStyle().set("display", "block");
        valueSpan.getStyle().set("margin-top", "0.5rem");

        Badge badge = new Badge(title, color, BadgeSize.S, BadgeShape.PILL);

        card.add(titleSpan, valueSpan);

        return card;
    }

    private Component createCharts() {
        HorizontalLayout chartsLayout = new HorizontalLayout();
        chartsLayout.setWidthFull();
        chartsLayout.setSpacing(true);
        chartsLayout.setPadding(true);

        // Add charts
        chartsLayout.add(createDocumentStatusChart());
        chartsLayout.add(createDocumentTypeChart());
        chartsLayout.add(createProcessingTimeChart());

        return chartsLayout;
    }

    private Component createDocumentStatusChart() {
        Chart chart = new Chart(ChartType.PIE);
        chart.setWidth("33%");
        chart.setHeight("300px");

        Configuration configuration = chart.getConfiguration();
        configuration.setTitle("Documents by Status");
        configuration.getTitle().setMargin(20);

        Tooltip tooltip = new Tooltip();
        tooltip.setValueDecimals(1);
        tooltip.setPointFormat("{point.name}: <b>{point.percentage:.1f}%</b>");
        configuration.setTooltip(tooltip);

        PlotOptionsPie plotOptions = new PlotOptionsPie();
        plotOptions.setAllowPointSelect(true);
        plotOptions.setCursor(com.vaadin.flow.component.charts.model.Cursor.POINTER);
        plotOptions.setShowInLegend(true);
        configuration.setPlotOptions(plotOptions);

        DataSeries series = new DataSeries();
        series.add(new DataSeriesItem("Pending", 24));
        series.add(new DataSeriesItem("Processing", 47));
        series.add(new DataSeriesItem("Completed", 83));
        series.add(new DataSeriesItem("Rejected", 18));
        configuration.addSeries(series);

        return chart;
    }

    private Component createDocumentTypeChart() {
        Chart chart = new Chart(ChartType.PIE);
        chart.setWidth("33%");
        chart.setHeight("300px");

        Configuration configuration = chart.getConfiguration();
        configuration.setTitle("Documents by Type");
        configuration.getTitle().setMargin(20);

        Tooltip tooltip = new Tooltip();
        tooltip.setValueDecimals(1);
        tooltip.setPointFormat("{point.name}: <b>{point.percentage:.1f}%</b>");
        configuration.setTooltip(tooltip);

        PlotOptionsPie plotOptions = new PlotOptionsPie();
        plotOptions.setAllowPointSelect(true);
        plotOptions.setCursor(com.vaadin.flow.component.charts.model.Cursor.POINTER);
        plotOptions.setShowInLegend(true);
        configuration.setPlotOptions(plotOptions);

        DataSeries series = new DataSeries();
        series.add(new DataSeriesItem("ID Documents", 35));
        series.add(new DataSeriesItem("Contracts", 28));
        series.add(new DataSeriesItem("Invoices", 17));
        series.add(new DataSeriesItem("Statements", 42));
        series.add(new DataSeriesItem("Other", 32));
        configuration.addSeries(series);

        return chart;
    }

    private Component createProcessingTimeChart() {
        Chart chart = new Chart(ChartType.COLUMN);
        chart.setWidth("33%");
        chart.setHeight("300px");

        Configuration configuration = chart.getConfiguration();
        configuration.setTitle("Average Processing Time (Days)");
        configuration.getTitle().setMargin(20);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("ID Documents", "Contracts", "Invoices", "Statements", "Other");
        configuration.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Days");
        configuration.addyAxis(yAxis);

        PlotOptionsColumn plotOptions = new PlotOptionsColumn();
        plotOptions.setColorByPoint(true);
        configuration.setPlotOptions(plotOptions);

        DataSeries series = new DataSeries();
        series.add(new DataSeriesItem("ID Documents", 2.5));
        series.add(new DataSeriesItem("Contracts", 4.2));
        series.add(new DataSeriesItem("Invoices", 1.8));
        series.add(new DataSeriesItem("Statements", 3.1));
        series.add(new DataSeriesItem("Other", 2.9));
        configuration.addSeries(series);

        return chart;
    }

    private Component createFilterForm() {
        // Initialize filter fields
        TextField documentIdFilter = new TextField();
        documentIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        documentIdFilter.setClearButtonVisible(true);

        TextField customerIdFilter = new TextField();
        customerIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        customerIdFilter.setClearButtonVisible(true);

        ComboBox<String> documentTypeFilter = new ComboBox<>();
        documentTypeFilter.setItems("ID Document", "Contract", "Invoice", "Statement", "Other");
        documentTypeFilter.setClearButtonVisible(true);

        ComboBox<String> statusFilter = new ComboBox<>();
        statusFilter.setItems("Pending", "Processing", "Completed", "Rejected");
        statusFilter.setClearButtonVisible(true);

        DatePicker submissionDateFromFilter = new DatePicker();
        submissionDateFromFilter.setClearButtonVisible(true);

        DatePicker submissionDateToFilter = new DatePicker();
        submissionDateToFilter.setClearButtonVisible(true);

        // Create buttons
        Button searchButton = UIUtils.createPrimaryButton("Search");
        Button clearButton = UIUtils.createTertiaryButton("Clear");

        // Create button layout
        HorizontalLayout buttonLayout = new HorizontalLayout(searchButton, clearButton);
        buttonLayout.setSpacing(true);

        // Create form layout
        FormLayout formLayout = new FormLayout();
        formLayout.addFormItem(documentIdFilter, "Document ID");
        formLayout.addFormItem(customerIdFilter, "Customer ID");
        formLayout.addFormItem(documentTypeFilter, "Document Type");
        formLayout.addFormItem(statusFilter, "Status");
        formLayout.addFormItem(submissionDateFromFilter, "Submission Date From");
        formLayout.addFormItem(submissionDateToFilter, "Submission Date To");

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
        formContainer.getStyle().set("margin", "1em");

        return formContainer;
    }

    private Grid<DocumentTrackingEntry> createGrid() {
        grid = new Grid<>();

        // Initialize data provider with data from service
        Collection<DocumentTrackingEntryDTO> entriesDTO = documentService.getDocumentTrackingEntries();
        Collection<DocumentTrackingEntry> entries = documentTrackingEntryMapper.toEntityList(entriesDTO);
        dataProvider = new ListDataProvider<>(entries);
        grid.setDataProvider(dataProvider);

        grid.setId("documentTracking");
        grid.setHeight("400px");
        grid.getStyle().set("margin", "0 1em 1em 1em");

        // Add columns
        grid.addColumn(DocumentTrackingEntry::getDocumentId)
                .setHeader("Document ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(DocumentTrackingEntry::getCustomerId)
                .setHeader("Customer ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(DocumentTrackingEntry::getDocumentType)
                .setHeader("Document Type")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(entry -> createStatusBadge(entry.getStatus()))
                .setHeader("Status")
                .setSortable(true)
                .setComparator(DocumentTrackingEntry::getStatus)
                .setWidth("120px");
        grid.addColumn(entry -> {
                    if (entry.getSubmissionDate() != null) {
                        return entry.getSubmissionDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    }
                    return "";
                })
                .setHeader("Submission Date")
                .setSortable(true)
                .setComparator(DocumentTrackingEntry::getSubmissionDate)
                .setWidth("150px");
        grid.addColumn(entry -> {
                    if (entry.getLastUpdated() != null) {
                        return entry.getLastUpdated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    }
                    return "";
                })
                .setHeader("Last Updated")
                .setSortable(true)
                .setComparator(DocumentTrackingEntry::getLastUpdated)
                .setWidth("150px");
        grid.addColumn(DocumentTrackingEntry::getCurrentStage)
                .setHeader("Current Stage")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(DocumentTrackingEntry::getAssignedTo)
                .setHeader("Assigned To")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(DocumentTrackingEntry::getEstimatedCompletion)
                .setHeader("Estimated Completion")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(DocumentTrackingEntry::getNotes)
                .setHeader("Notes")
                .setSortable(true)
                .setWidth("250px");

        // Add action column with buttons
        grid.addComponentColumn(entry -> {
            HorizontalLayout actions = new HorizontalLayout();

            Button viewButton = UIUtils.createSmallButton("");
            viewButton.setIcon(VaadinIcon.EYE.create());
            viewButton.getElement().setAttribute("title", "View Details");

            Button trackButton = UIUtils.createSmallButton("");
            trackButton.setIcon(VaadinIcon.CONNECT.create());
            trackButton.getElement().setAttribute("title", "Track Progress");

            Button historyButton = UIUtils.createSmallButton("");
            historyButton.setIcon(VaadinIcon.CLOCK.create());
            historyButton.getElement().setAttribute("title", "View History");

            actions.add(viewButton, trackButton, historyButton);
            actions.setSpacing(true);
            return actions;
        }).setHeader("Actions").setWidth("120px").setFlexGrow(0);

        return grid;
    }

    private Component createStatusBadge(String status) {
        BadgeColor color;
        switch (status) {
            case "Completed":
                color = BadgeColor.SUCCESS;
                break;
            case "Rejected":
                color = BadgeColor.ERROR;
                break;
            case "Processing":
                color = BadgeColor.CONTRAST_PRIMARY;
                break;
            case "Pending":
                color = BadgeColor.CONTRAST;
                break;
            default:
                color = BadgeColor.NORMAL;
        }

        return new Badge(status, color, BadgeSize.S, BadgeShape.PILL);
    }

    private Collection<DocumentTrackingEntry> generateMockData() {
        List<DocumentTrackingEntry> entries = new ArrayList<>();
        Random random = new Random(42); // Fixed seed for reproducible data

        String[] customerIds = {"C10045", "C20056", "C30078", "C40023", "C50091", "C60112", "C70134", "C80156"};
        String[] documentTypes = {"ID Document", "Contract", "Invoice", "Statement", "Other"};
        String[] statuses = {"Pending", "Processing", "Completed", "Rejected"};
        String[] stages = {
            "Document Submission", 
            "Initial Verification", 
            "Content Validation", 
            "Approval Process", 
            "Final Review",
            "Archiving"
        };

        String[] assignedTo = {
            "John Smith", 
            "Maria Rodriguez", 
            "Wei Zhang", 
            "Sarah Johnson", 
            "Ahmed Hassan",
            "Olivia Wilson",
            "Michael Brown",
            "Fatima Al-Farsi"
        };

        String[] notes = {
            "Document submitted via online portal",
            "Waiting for customer verification",
            "Document requires additional information",
            "Processing delayed due to high volume",
            "Document approved by compliance team",
            "Rejected due to missing information",
            "Customer notified of document status",
            "Document archived in system"
        };

        for (int i = 1; i <= 50; i++) {
            DocumentTrackingEntry entry = new DocumentTrackingEntry();
            entry.setDocumentId("DOC" + String.format("%06d", i));
            entry.setCustomerId(customerIds[random.nextInt(customerIds.length)]);
            entry.setDocumentType(documentTypes[random.nextInt(documentTypes.length)]);

            String status = statuses[random.nextInt(statuses.length)];
            entry.setStatus(status);

            // Generate submission date within the last 30 days
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime submissionDate = now.minusDays(random.nextInt(30)).minusHours(random.nextInt(24));
            entry.setSubmissionDate(submissionDate);

            // Generate last updated date after submission date
            LocalDateTime lastUpdated = submissionDate.plusHours(random.nextInt(24 * 30));
            if (lastUpdated.isAfter(now)) {
                lastUpdated = now;
            }
            entry.setLastUpdated(lastUpdated);

            // Set current stage based on status
            if (status.equals("Pending")) {
                entry.setCurrentStage(stages[0]);
            } else if (status.equals("Processing")) {
                entry.setCurrentStage(stages[1 + random.nextInt(3)]);
            } else if (status.equals("Completed")) {
                entry.setCurrentStage(stages[5]);
            } else {
                entry.setCurrentStage("Rejected");
            }

            entry.setAssignedTo(assignedTo[random.nextInt(assignedTo.length)]);

            // Set estimated completion date
            if (status.equals("Pending") || status.equals("Processing")) {
                int daysToAdd = 1 + random.nextInt(14);
                entry.setEstimatedCompletion(now.plusDays(daysToAdd).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            } else {
                entry.setEstimatedCompletion("N/A");
            }

            entry.setNotes(notes[random.nextInt(notes.length)]);

            entries.add(entry);
        }

        return entries;
    }

    // Inner class to represent a document tracking entry
    public static class DocumentTrackingEntry {
        private String documentId;
        private String customerId;
        private String documentType;
        private String status;
        private LocalDateTime submissionDate;
        private LocalDateTime lastUpdated;
        private String currentStage;
        private String assignedTo;
        private String estimatedCompletion;
        private String notes;

        public String getDocumentId() {
            return documentId;
        }

        public void setDocumentId(String documentId) {
            this.documentId = documentId;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getDocumentType() {
            return documentType;
        }

        public void setDocumentType(String documentType) {
            this.documentType = documentType;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public LocalDateTime getSubmissionDate() {
            return submissionDate;
        }

        public void setSubmissionDate(LocalDateTime submissionDate) {
            this.submissionDate = submissionDate;
        }

        public LocalDateTime getLastUpdated() {
            return lastUpdated;
        }

        public void setLastUpdated(LocalDateTime lastUpdated) {
            this.lastUpdated = lastUpdated;
        }

        public String getCurrentStage() {
            return currentStage;
        }

        public void setCurrentStage(String currentStage) {
            this.currentStage = currentStage;
        }

        public String getAssignedTo() {
            return assignedTo;
        }

        public void setAssignedTo(String assignedTo) {
            this.assignedTo = assignedTo;
        }

        public String getEstimatedCompletion() {
            return estimatedCompletion;
        }

        public void setEstimatedCompletion(String estimatedCompletion) {
            this.estimatedCompletion = estimatedCompletion;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }
    }
}
