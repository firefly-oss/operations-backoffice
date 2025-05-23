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

@PageTitle(NavigationConstants.TRANSACTION_DOCUMENTS)
@Route(value = "document-management/transaction-documents", layout = MainLayout.class)
public class TransactionDocuments extends ViewFrame {

    private Grid<Document> grid;
    private ListDataProvider<Document> dataProvider;

    // Filter form fields
    private TextField documentIdFilter;
    private TextField transactionIdFilter;
    private ComboBox<String> documentTypeFilter;
    private ComboBox<String> statusFilter;
    private DatePicker uploadDateFromFilter;
    private DatePicker uploadDateToFilter;
    private TextField filenameFilter;
    private ComboBox<String> transactionTypeFilter;

    public TransactionDocuments() {
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
        H3 header = new H3("Transaction Documents");
        header.getStyle().set("margin", "0");
        header.getStyle().set("padding", "1rem");
        return header;
    }

    private Component createFilterForm() {
        // Initialize filter fields
        documentIdFilter = new TextField();
        documentIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        documentIdFilter.setClearButtonVisible(true);

        transactionIdFilter = new TextField();
        transactionIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        transactionIdFilter.setClearButtonVisible(true);

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

        transactionTypeFilter = new ComboBox<>();
        transactionTypeFilter.setItems(getTransactionTypes());
        transactionTypeFilter.setClearButtonVisible(true);

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
        formLayout.addFormItem(transactionIdFilter, "Transaction ID");
        formLayout.addFormItem(documentTypeFilter, "Document Type");
        formLayout.addFormItem(statusFilter, "Status");
        formLayout.addFormItem(uploadDateFromFilter, "Upload Date From");
        formLayout.addFormItem(uploadDateToFilter, "Upload Date To");
        formLayout.addFormItem(filenameFilter, "Filename");
        formLayout.addFormItem(transactionTypeFilter, "Transaction Type");

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

        // Initialize data provider with mock data
        Collection<Document> documents = generateMockData();
        dataProvider = new ListDataProvider<>(documents);
        grid.setDataProvider(dataProvider);

        grid.setId("transactionDocuments");
        grid.setSizeFull();

        // Add columns
        grid.addColumn(Document::getDocumentId)
                .setHeader("Document ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(Document::getTransactionId)
                .setHeader("Transaction ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(Document::getTransactionType)
                .setHeader("Transaction Type")
                .setSortable(true)
                .setWidth("150px");
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
        UI.getCurrent().navigate(TransactionDocumentDetails.class, document.getDocumentId());
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

        // Apply transaction ID filter
        if (transactionIdFilter.getValue() != null && !transactionIdFilter.getValue().isEmpty()) {
            String transactionIdValue = transactionIdFilter.getValue().toLowerCase();
            dataProvider.addFilter(document -> 
                document.getTransactionId() != null && 
                document.getTransactionId().toLowerCase().contains(transactionIdValue));
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

        // Apply transaction type filter
        if (transactionTypeFilter.getValue() != null) {
            String transactionTypeValue = transactionTypeFilter.getValue();
            dataProvider.addFilter(document -> 
                document.getTransactionType() != null && 
                document.getTransactionType().equals(transactionTypeValue));
        }
    }

    private void clearFilters() {
        // Clear all filter fields
        documentIdFilter.clear();
        transactionIdFilter.clear();
        documentTypeFilter.clear();
        statusFilter.clear();
        uploadDateFromFilter.clear();
        uploadDateToFilter.clear();
        filenameFilter.clear();
        transactionTypeFilter.clear();

        // Clear all filters from data provider
        dataProvider.clearFilters();
    }

    private String[] getDocumentTypes() {
        return new String[] {
            "Invoice", 
            "Receipt", 
            "Contract", 
            "Payment Confirmation", 
            "Wire Transfer Form",
            "Check Image",
            "Transaction Statement",
            "Authorization Form",
            "Dispute Documentation",
            "Settlement Agreement"
        };
    }

    private String[] getStatuses() {
        return new String[] {
            "Pending Review", 
            "Approved", 
            "Rejected", 
            "Expired", 
            "Needs Update"
        };
    }

    private String[] getTransactionTypes() {
        return new String[] {
            "Payment", 
            "Deposit", 
            "Withdrawal", 
            "Transfer", 
            "Loan Disbursement",
            "Fee Collection",
            "Interest Payment",
            "Currency Exchange",
            "Refund",
            "Chargeback"
        };
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

    private Collection<Document> generateMockData() {
        List<Document> documents = new ArrayList<>();
        Random random = new Random(42); // Fixed seed for reproducible data

        String[] transactionIds = {"T10045", "T20056", "T30078", "T40023", "T50091", "T60112", "T70134", "T80156"};
        String[] transactionTypes = getTransactionTypes();

        String[] documentTypes = getDocumentTypes();
        String[] statuses = getStatuses();
        String[] fileExtensions = {".pdf", ".jpg", ".png", ".docx", ".xlsx"};

        String[] uploadedBy = {
            "John Smith", 
            "Maria Rodriguez", 
            "Wei Zhang", 
            "Sarah Johnson", 
            "Ahmed Hassan",
            "Olivia Wilson",
            "Michael Brown",
            "Fatima Al-Farsi"
        };

        String[] descriptions = {
            "Transaction receipt document",
            "Invoice for payment",
            "Contract for financial transaction",
            "Payment confirmation document",
            "Wire transfer authorization form",
            "Check image for deposit",
            "Transaction statement",
            "Dispute documentation"
        };

        for (int i = 1; i <= 50; i++) {
            Document document = new Document();
            document.setDocumentId("TDOC" + String.format("%06d", i));

            int transactionIndex = random.nextInt(transactionIds.length);
            document.setTransactionId(transactionIds[transactionIndex]);
            document.setTransactionType(transactionTypes[random.nextInt(transactionTypes.length)]);

            String documentType = documentTypes[random.nextInt(documentTypes.length)];
            document.setDocumentType(documentType);

            document.setStatus(statuses[random.nextInt(statuses.length)]);

            // Generate a filename based on document type
            String fileExtension = fileExtensions[random.nextInt(fileExtensions.length)];
            document.setFilename(documentType.replace(" ", "_").toLowerCase() + "_" + 
                                transactionIds[transactionIndex].toLowerCase() + "_" + 
                                (random.nextInt(900) + 100) + fileExtension);

            // Generate a random upload date within the last 180 days
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime uploadDate = now.minusDays(random.nextInt(180)).minusHours(random.nextInt(24));
            document.setUploadDate(uploadDate);

            // Generate a random file size between 100KB and 10MB
            document.setFileSize(formatFileSize(100 + random.nextInt(9900)));

            document.setUploadedBy(uploadedBy[random.nextInt(uploadedBy.length)]);
            document.setDescription(descriptions[random.nextInt(descriptions.length)]);

            documents.add(document);
        }

        return documents;
    }

    private String formatFileSize(int sizeInKB) {
        if (sizeInKB < 1024) {
            return sizeInKB + " KB";
        } else {
            double sizeInMB = sizeInKB / 1024.0;
            return String.format("%.2f MB", sizeInMB);
        }
    }

    // Inner class to represent a document
    public static class Document {
        private String documentId;
        private String transactionId;
        private String transactionType;
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

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public String getTransactionType() {
            return transactionType;
        }

        public void setTransactionType(String transactionType) {
            this.transactionType = transactionType;
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
