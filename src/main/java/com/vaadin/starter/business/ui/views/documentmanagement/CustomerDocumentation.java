package com.vaadin.starter.business.ui.views.documentmanagement;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.business.backend.dto.documentmanagement.DocumentDTO;
import com.vaadin.starter.business.backend.mapper.DocumentMapper;
import com.vaadin.starter.business.backend.service.DocumentService;
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.components.Badge;
import com.vaadin.starter.business.ui.components.FlexBoxLayout;
import com.vaadin.starter.business.ui.constants.NavigationConstants;
import com.vaadin.starter.business.ui.layout.size.Horizontal;
import com.vaadin.starter.business.ui.layout.size.Top;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.util.css.BoxSizing;
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

@PageTitle(NavigationConstants.CUSTOMER_DOCUMENTATION)
@Route(value = "document-management/customer-documentation", layout = MainLayout.class)
public class CustomerDocumentation extends ViewFrame {

    private Grid<Document> grid;
    private ListDataProvider<Document> dataProvider;

    // Filter form fields
    private TextField documentIdFilter;
    private TextField customerIdFilter;
    private TextField customerNameFilter;
    private ComboBox<String> documentTypeFilter;
    private ComboBox<String> statusFilter;
    private DatePicker uploadDateFromFilter;
    private DatePicker uploadDateToFilter;
    private TextField filenameFilter;

    private final DocumentService documentService;
    private final DocumentMapper documentMapper;

    @Autowired
    public CustomerDocumentation(DocumentService documentService, DocumentMapper documentMapper) {
        this.documentService = documentService;
        this.documentMapper = documentMapper;
        setViewContent(createContent());
    }

    private Component createContent() {
        FlexBoxLayout content = new FlexBoxLayout(createHeader(), createFilterForm(), createGrid());
        content.setBoxSizing(BoxSizing.BORDER_BOX);
        content.setHeightFull();
        content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);
        content.setFlexDirection(FlexDirection.COLUMN);
        return content;
    }

    private Component createHeader() {
        H3 header = new H3("Customer Documentation");
        header.getStyle().set("margin", "0");
        header.getStyle().set("padding", "1rem");
        return header;
    }

    private Component createFilterForm() {
        // Initialize filter fields
        documentIdFilter = new TextField();
        documentIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        documentIdFilter.setClearButtonVisible(true);

        customerIdFilter = new TextField();
        customerIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        customerIdFilter.setClearButtonVisible(true);

        customerNameFilter = new TextField();
        customerNameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        customerNameFilter.setClearButtonVisible(true);

        documentTypeFilter = new ComboBox<>();
        documentTypeFilter.setItems(getDocumentTypes());
        documentTypeFilter.setClearButtonVisible(true);

        statusFilter = new ComboBox<>();
        statusFilter.setItems(getStatuses());
        statusFilter.setClearButtonVisible(true);

        uploadDateFromFilter = new DatePicker();
        uploadDateFromFilter.setClearButtonVisible(true);

        uploadDateToFilter = new DatePicker();
        uploadDateToFilter.setClearButtonVisible(true);

        filenameFilter = new TextField();
        filenameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        filenameFilter.setClearButtonVisible(true);

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
        formLayout.addFormItem(documentIdFilter, "Document ID");
        formLayout.addFormItem(customerIdFilter, "Customer ID");
        formLayout.addFormItem(customerNameFilter, "Customer Name");
        formLayout.addFormItem(documentTypeFilter, "Document Type");
        formLayout.addFormItem(statusFilter, "Status");
        formLayout.addFormItem(uploadDateFromFilter, "Upload Date From");
        formLayout.addFormItem(uploadDateToFilter, "Upload Date To");
        formLayout.addFormItem(filenameFilter, "Filename");

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

    private Grid<Document> createGrid() {
        grid = new Grid<>();
        grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(this::viewDetails));

        // Initialize data provider with data from service
        Collection<DocumentDTO> documentDTOs = documentService.getDocuments();
        Collection<Document> documents = documentMapper.toEntityList(documentDTOs);
        dataProvider = new ListDataProvider<>(documents);
        grid.setDataProvider(dataProvider);

        grid.setId("customerDocuments");
        grid.setSizeFull();

        // Add columns
        grid.addColumn(Document::getDocumentId)
                .setHeader("Document ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(Document::getCustomerId)
                .setHeader("Customer ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(Document::getCustomerName)
                .setHeader("Customer Name")
                .setSortable(true)
                .setWidth("180px");
        grid.addColumn(Document::getDocumentType)
                .setHeader("Document Type")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(document -> createStatusBadge(document.getStatus()))
                .setHeader("Status")
                .setSortable(true)
                .setComparator(Document::getStatus)
                .setWidth("120px");
        grid.addColumn(Document::getFilename)
                .setHeader("Filename")
                .setSortable(true)
                .setWidth("200px");
        grid.addColumn(document -> {
                    if (document.getUploadDate() != null) {
                        return document.getUploadDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    }
                    return "";
                })
                .setHeader("Upload Date")
                .setSortable(true)
                .setComparator(Document::getUploadDate)
                .setWidth("150px");
        grid.addColumn(Document::getFileSize)
                .setHeader("File Size")
                .setSortable(true)
                .setWidth("100px");
        grid.addColumn(Document::getUploadedBy)
                .setHeader("Uploaded By")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(Document::getDescription)
                .setHeader("Description")
                .setSortable(true)
                .setWidth("250px");

        return grid;
    }

    private void viewDetails(Document document) {
        // Navigate to the details view
        UI.getCurrent().navigate(CustomerDocumentDetails.class, document.getDocumentId());
    }

    private void applyFilters() {
        dataProvider.clearFilters();

        // Apply document ID filter
        if (documentIdFilter.getValue() != null && !documentIdFilter.getValue().isEmpty()) {
            String documentIdValue = documentIdFilter.getValue().toLowerCase();
            dataProvider.addFilter(document -> 
                document.getDocumentId() != null && 
                document.getDocumentId().toLowerCase().contains(documentIdValue));
        }

        // Apply customer ID filter
        if (customerIdFilter.getValue() != null && !customerIdFilter.getValue().isEmpty()) {
            String customerIdValue = customerIdFilter.getValue().toLowerCase();
            dataProvider.addFilter(document -> 
                document.getCustomerId() != null && 
                document.getCustomerId().toLowerCase().contains(customerIdValue));
        }

        // Apply customer name filter
        if (customerNameFilter.getValue() != null && !customerNameFilter.getValue().isEmpty()) {
            String customerNameValue = customerNameFilter.getValue().toLowerCase();
            dataProvider.addFilter(document -> 
                document.getCustomerName() != null && 
                document.getCustomerName().toLowerCase().contains(customerNameValue));
        }

        // Apply document type filter
        if (documentTypeFilter.getValue() != null) {
            String documentTypeValue = documentTypeFilter.getValue();
            dataProvider.addFilter(document -> 
                document.getDocumentType() != null && 
                document.getDocumentType().equals(documentTypeValue));
        }

        // Apply status filter
        if (statusFilter.getValue() != null) {
            String statusValue = statusFilter.getValue();
            dataProvider.addFilter(document -> 
                document.getStatus() != null && 
                document.getStatus().equals(statusValue));
        }

        // Apply upload date from filter
        if (uploadDateFromFilter.getValue() != null) {
            java.time.LocalDate fromDate = uploadDateFromFilter.getValue();
            dataProvider.addFilter(document -> 
                document.getUploadDate() != null && 
                !document.getUploadDate().toLocalDate().isBefore(fromDate));
        }

        // Apply upload date to filter
        if (uploadDateToFilter.getValue() != null) {
            java.time.LocalDate toDate = uploadDateToFilter.getValue();
            dataProvider.addFilter(document -> 
                document.getUploadDate() != null && 
                !document.getUploadDate().toLocalDate().isAfter(toDate));
        }

        // Apply filename filter
        if (filenameFilter.getValue() != null && !filenameFilter.getValue().isEmpty()) {
            String filenameValue = filenameFilter.getValue().toLowerCase();
            dataProvider.addFilter(document -> 
                document.getFilename() != null && 
                document.getFilename().toLowerCase().contains(filenameValue));
        }
    }

    private void clearFilters() {
        // Clear all filter fields
        documentIdFilter.clear();
        customerIdFilter.clear();
        customerNameFilter.clear();
        documentTypeFilter.clear();
        statusFilter.clear();
        uploadDateFromFilter.clear();
        uploadDateToFilter.clear();
        filenameFilter.clear();

        // Clear all filters from data provider
        dataProvider.clearFilters();
    }

    private String[] getDocumentTypes() {
        DocumentDTO.DocumentType[] types = DocumentDTO.DocumentType.values();
        String[] result = new String[types.length];
        for (int i = 0; i < types.length; i++) {
            result[i] = types[i].getName();
        }
        return result;
    }

    private String[] getStatuses() {
        DocumentDTO.Status[] statuses = DocumentDTO.Status.values();
        String[] result = new String[statuses.length];
        for (int i = 0; i < statuses.length; i++) {
            result[i] = statuses[i].getName();
        }
        return result;
    }

    private Component createStatusBadge(String status) {
        BadgeColor color;
        switch (status) {
            case "Approved":
                color = BadgeColor.SUCCESS;
                break;
            case "Rejected":
                color = BadgeColor.ERROR;
                break;
            case "Pending Review":
                color = BadgeColor.CONTRAST;
                break;
            case "Expired":
                color = BadgeColor.ERROR_PRIMARY;
                break;
            case "Needs Update":
                color = BadgeColor.CONTRAST_PRIMARY;
                break;
            default:
                color = BadgeColor.NORMAL;
        }

        return new Badge(status, color, BadgeSize.S, BadgeShape.PILL);
    }


    // Inner class to represent a document
    public static class Document {
        private String documentId;
        private String customerId;
        private String customerName;
        private String documentType;
        private String status;
        private String filename;
        private LocalDateTime uploadDate;
        private String fileSize;
        private String uploadedBy;
        private String description;

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

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
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

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public LocalDateTime getUploadDate() {
            return uploadDate;
        }

        public void setUploadDate(LocalDateTime uploadDate) {
            this.uploadDate = uploadDate;
        }

        public String getFileSize() {
            return fileSize;
        }

        public void setFileSize(String fileSize) {
            this.fileSize = fileSize;
        }

        public String getUploadedBy() {
            return uploadedBy;
        }

        public void setUploadedBy(String uploadedBy) {
            this.uploadedBy = uploadedBy;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
