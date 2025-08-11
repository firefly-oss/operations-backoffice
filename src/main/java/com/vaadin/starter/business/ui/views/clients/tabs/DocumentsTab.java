package com.vaadin.starter.business.ui.views.clients.tabs;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.views.clients.ClientManagement.Client;
import com.vaadin.starter.business.ui.views.clients.tabs.models.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Tab for displaying client documents information.
 */
public class DocumentsTab extends AbstractTab {

    /**
     * Constructor for DocumentsTab.
     *
     * @param client The client whose documents are displayed
     */
    public DocumentsTab(Client client) {
        super(client);
    }

    @Override
    protected void initContent() {
        // Add summary cards
        HorizontalLayout summaryCards = new HorizontalLayout();
        summaryCards.setWidthFull();
        summaryCards.setSpacing(true);

        summaryCards.add(createSummaryCard("Total Documents", "8", VaadinIcon.FOLDER));
        summaryCards.add(createSummaryCard("Pending Review", "2", VaadinIcon.CLOCK));
        summaryCards.add(createSummaryCard("Recently Added", "3", VaadinIcon.CALENDAR));

        add(summaryCards);

        // Add documents grid
        Grid<Document> documentsGrid = createDocumentsGrid();
        add(documentsGrid);
        expand(documentsGrid);

        // Add action buttons
        HorizontalLayout actionButtons = new HorizontalLayout();
        Button uploadButton = UIUtils.createPrimaryButton("Upload Document");
        Button downloadAllButton = UIUtils.createTertiaryButton("Download All");
        Button archiveButton = UIUtils.createTertiaryButton("Archive Selected");
        actionButtons.add(uploadButton, downloadAllButton, archiveButton);
        actionButtons.setSpacing(true);
        add(actionButtons);
    }

    @Override
    public String getTabName() {
        return "Documents";
    }

    /**
     * Creates the documents grid with all columns and data.
     *
     * @return The configured grid component
     */
    private Grid<Document> createDocumentsGrid() {
        Grid<Document> grid = new Grid<>();
        grid.setItems(generateMockDocuments());
        grid.setSizeFull();
        grid.setMinHeight("400px");

        grid.addColumn(Document::getDocumentId)
                .setHeader("Document ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(Document::getDocumentName)
                .setHeader("Document Name")
                .setSortable(true)
                .setWidth("200px");
        grid.addColumn(Document::getDocumentType)
                .setHeader("Type")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(Document::getSize)
                .setHeader("Size")
                .setSortable(true)
                .setWidth("100px");
        grid.addColumn(new ComponentRenderer<>(document -> createStatusBadge(document.getStatus())))
                .setHeader("Status")
                .setSortable(true)
                .setComparator(Document::getStatus)
                .setWidth("120px");
        grid.addColumn(document -> UIUtils.formatDate(document.getUploadDate()))
                .setHeader("Upload Date")
                .setSortable(true)
                .setComparator(Document::getUploadDate)
                .setWidth("120px");
        grid.addColumn(Document::getUploadedBy)
                .setHeader("Uploaded By")
                .setSortable(true)
                .setWidth("150px");

        // Add item click listener to show document details dialog
        grid.addItemClickListener(event -> {
            Document document = event.getItem();
            showDocumentDetails(document);
        });

        // Add style to indicate rows are clickable
        grid.addClassName("clickable-grid");
        grid.getElement().getStyle().set("cursor", "pointer");

        return grid;
    }

    /**
     * Shows a dialog with detailed information about a document.
     *
     * @param document The document to show details for
     */
    private void showDocumentDetails(Document document) {
        // Create dialog for document details
        Dialog dialog = new Dialog();
        dialog.setWidth("600px");

        // Create dialog title
        H3 title = new H3("Document Details: " + document.getDocumentName());

        // Create form layout for document details
        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("500px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP)
        );

        // Create fields for document details
        TextField documentIdField = new TextField("Document ID");
        documentIdField.setValue(document.getDocumentId());
        documentIdField.setReadOnly(true);

        TextField documentNameField = new TextField("Document Name");
        documentNameField.setValue(document.getDocumentName());
        documentNameField.setReadOnly(true);

        TextField documentTypeField = new TextField("Document Type");
        documentTypeField.setValue(document.getDocumentType());
        documentTypeField.setReadOnly(true);

        TextField sizeField = new TextField("Size");
        sizeField.setValue(document.getSize());
        sizeField.setReadOnly(true);

        TextField statusField = new TextField("Status");
        statusField.setValue(document.getStatus());
        statusField.setReadOnly(true);

        DatePicker uploadDateField = new DatePicker("Upload Date");
        uploadDateField.setValue(document.getUploadDate());
        uploadDateField.setReadOnly(true);

        TextField uploadedByField = new TextField("Uploaded By");
        uploadedByField.setValue(document.getUploadedBy());
        uploadedByField.setReadOnly(true);

        TextField descriptionField = new TextField("Description");
        descriptionField.setValue(document.getDescription());
        descriptionField.setReadOnly(true);

        // Add fields to form layout
        formLayout.add(documentIdField, documentNameField, documentTypeField, sizeField, 
                      statusField, uploadDateField, uploadedByField, descriptionField);

        // Create close button
        Button closeButton = UIUtils.createTertiaryButton("Close");
        closeButton.addClickListener(e -> dialog.close());

        // Create download button
        Button downloadButton = UIUtils.createPrimaryButton("Download");
        downloadButton.addClickListener(e -> {
            // In a real application, this would download the document
            dialog.close();
        });

        // Create layout for buttons
        HorizontalLayout buttonLayout = new HorizontalLayout(downloadButton, closeButton);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(true);

        // Create layout for dialog content
        VerticalLayout dialogLayout = new VerticalLayout(title, formLayout, buttonLayout);
        dialogLayout.setPadding(true);
        dialogLayout.setSpacing(true);

        dialog.add(dialogLayout);
        dialog.open();
    }

    /**
     * Generates mock document data for testing.
     *
     * @return A list of mock documents
     */
    private List<Document> generateMockDocuments() {
        List<Document> documents = new ArrayList<>();

        documents.add(new Document(
            "DOC001", 
            "ID_Verification.pdf", 
            "PDF", 
            "1.2 MB", 
            "Verified", 
            LocalDate.now().minusMonths(6), 
            "System Admin", 
            "Client ID verification document"
        ));

        documents.add(new Document(
            "DOC002", 
            "Address_Proof.pdf", 
            "PDF", 
            "2.5 MB", 
            "Verified", 
            LocalDate.now().minusMonths(6), 
            "System Admin", 
            "Proof of address document"
        ));

        documents.add(new Document(
            "DOC003", 
            "Income_Statement_2023.xlsx", 
            "Excel", 
            "3.7 MB", 
            "Verified", 
            LocalDate.now().minusMonths(3), 
            "Financial Advisor", 
            "Annual income statement"
        ));

        documents.add(new Document(
            "DOC004", 
            "Contract_CNT001.pdf", 
            "PDF", 
            "4.1 MB", 
            "Active", 
            LocalDate.now().minusMonths(6), 
            "Account Manager", 
            "Standard contract document"
        ));

        documents.add(new Document(
            "DOC005", 
            "Contract_CNT002.pdf", 
            "PDF", 
            "4.3 MB", 
            "Active", 
            LocalDate.now().minusMonths(2), 
            "Account Manager", 
            "Premium contract document"
        ));

        documents.add(new Document(
            "DOC006", 
            "Tax_Form_2023.pdf", 
            "PDF", 
            "1.8 MB", 
            "Pending Review", 
            LocalDate.now().minusDays(5), 
            client.getName(), 
            "Annual tax form"
        ));

        documents.add(new Document(
            "DOC007", 
            "Investment_Portfolio.pdf", 
            "PDF", 
            "5.2 MB", 
            "Pending Review", 
            LocalDate.now().minusDays(3), 
            "Financial Advisor", 
            "Investment portfolio analysis"
        ));

        documents.add(new Document(
            "DOC008", 
            "Meeting_Notes_2023-06-15.docx", 
            "Word", 
            "0.8 MB", 
            "Internal", 
            LocalDate.now().minusDays(15), 
            "Account Manager", 
            "Client meeting notes"
        ));

        return documents;
    }
}