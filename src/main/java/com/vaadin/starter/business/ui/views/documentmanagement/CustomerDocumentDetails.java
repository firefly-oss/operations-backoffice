package com.vaadin.starter.business.ui.views.documentmanagement;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.business.backend.dto.documentmanagement.DocumentDTO;
import com.vaadin.starter.business.backend.mapper.DocumentMapper;
import com.vaadin.starter.business.backend.service.DocumentService;
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.components.Badge;
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
import com.vaadin.starter.business.ui.util.css.lumo.BadgeColor;
import com.vaadin.starter.business.ui.util.css.lumo.BadgeShape;
import com.vaadin.starter.business.ui.util.css.lumo.BadgeSize;
import com.vaadin.starter.business.ui.views.ViewFrame;

import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Customer Document Details")
@Route(value = "document-management/customer-document-details", layout = MainLayout.class)
public class CustomerDocumentDetails extends ViewFrame implements HasUrlParameter<String> {

    private ListItem documentId;
    private ListItem customerId;
    private ListItem customerName;
    private ListItem documentType;
    private ListItem status;
    private ListItem filename;
    private ListItem uploadDate;
    private ListItem fileSize;
    private ListItem uploadedBy;
    private ListItem description;
    private Div documentPreview;

    private CustomerDocumentation.Document document;

    private final DocumentService documentService;
    private final DocumentMapper documentMapper;

    @Autowired
    public CustomerDocumentDetails(DocumentService documentService, DocumentMapper documentMapper) {
        this.documentService = documentService;
        this.documentMapper = documentMapper;
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String documentId) {
        // Fetch the document from the service
        DocumentDTO documentDTO = documentService.getDocumentById(documentId);
        if (documentDTO != null) {
            document = documentMapper.toEntity(documentDTO);
            setViewContent(createContent());
        } else {
            // Handle case where document is not found
            setViewContent(createDocumentNotFoundContent(documentId));
        }
    }

    private Component createDocumentNotFoundContent(String documentId) {
        FlexBoxLayout content = new FlexBoxLayout();
        content.setAlignItems(FlexComponent.Alignment.CENTER);
        content.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        content.setHeightFull();

        Span message = new Span("Document with ID " + documentId + " not found");
        message.getStyle().set("font-size", "var(--lumo-font-size-xl)");
        message.getStyle().set("color", "var(--lumo-error-text-color)");

        content.add(message);
        return content;
    }

    private Component createContent() {
        FlexBoxLayout content = new FlexBoxLayout(
                createDetailsSection(),
                createDocumentPreviewSection(),
                createActionsSection()
        );
        content.setFlexDirection(FlexDirection.COLUMN);
        content.setMargin(Horizontal.AUTO, Vertical.RESPONSIVE_L);
        content.setMaxWidth("840px");
        return content;
    }

    private FlexBoxLayout createDetailsSection() {
        documentId = new ListItem(VaadinIcon.FILE_TEXT.create(), "", "Document ID");
        documentId.getPrimary().addClassName(LumoStyles.Heading.H2);
        documentId.setDividerVisible(true);
        documentId.setId("documentId");
        documentId.setReverse(true);

        customerId = new ListItem(VaadinIcon.USER.create(), "", "Customer ID");
        customerId.setDividerVisible(true);
        customerId.setId("customerId");
        customerId.setReverse(true);

        customerName = new ListItem(VaadinIcon.USER.create(), "", "Customer Name");
        customerName.setDividerVisible(true);
        customerName.setId("customerName");
        customerName.setReverse(true);

        documentType = new ListItem(VaadinIcon.FOLDER.create(), "", "Document Type");
        documentType.setDividerVisible(true);
        documentType.setId("documentType");
        documentType.setReverse(true);

        status = new ListItem(VaadinIcon.FLAG.create(), "", "Status");
        status.setDividerVisible(true);
        status.setId("status");
        status.setReverse(true);

        filename = new ListItem(VaadinIcon.FILE.create(), "", "Filename");
        filename.setDividerVisible(true);
        filename.setId("filename");
        filename.setReverse(true);

        uploadDate = new ListItem(VaadinIcon.CALENDAR.create(), "", "Upload Date");
        uploadDate.setDividerVisible(true);
        uploadDate.setId("uploadDate");
        uploadDate.setReverse(true);

        fileSize = new ListItem(VaadinIcon.SCALE.create(), "", "File Size");
        fileSize.setDividerVisible(true);
        fileSize.setId("fileSize");
        fileSize.setReverse(true);

        uploadedBy = new ListItem(VaadinIcon.USER_CARD.create(), "", "Uploaded By");
        uploadedBy.setDividerVisible(true);
        uploadedBy.setId("uploadedBy");
        uploadedBy.setReverse(true);

        description = new ListItem(VaadinIcon.CLIPBOARD_TEXT.create(), "", "Description");
        description.setDividerVisible(true);
        description.setId("description");
        description.setReverse(true);
        description.setWhiteSpace(WhiteSpace.PRE_LINE);

        // Populate fields with data
        documentId.setPrimaryText(document.getDocumentId());
        customerId.setPrimaryText(document.getCustomerId());
        customerName.setPrimaryText(document.getCustomerName());
        documentType.setPrimaryText(document.getDocumentType());

        // For status, create a badge
        Badge statusBadge = createStatusBadge(document.getStatus());
        status.setPrimaryText(document.getStatus());

        filename.setPrimaryText(document.getFilename());

        if (document.getUploadDate() != null) {
            uploadDate.setPrimaryText(document.getUploadDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        }

        fileSize.setPrimaryText(document.getFileSize());
        uploadedBy.setPrimaryText(document.getUploadedBy());
        description.setPrimaryText(document.getDescription());

        FlexBoxLayout listItems = new FlexBoxLayout(
                documentId,
                customerId,
                customerName,
                documentType,
                status,
                filename,
                uploadDate,
                fileSize,
                uploadedBy,
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

    private Component createDocumentPreviewSection() {
        Span title = new Span("Document Preview");
        title.addClassName(LumoStyles.Heading.H3);

        documentPreview = new Div();
        documentPreview.setWidth("100%");
        documentPreview.setHeight("400px");
        documentPreview.getStyle().set("border", "1px solid var(--lumo-contrast-20pct)");
        documentPreview.getStyle().set("border-radius", "var(--lumo-border-radius)");
        documentPreview.getStyle().set("background-color", "var(--lumo-base-color)");
        documentPreview.getStyle().set("display", "flex");
        documentPreview.getStyle().set("align-items", "center");
        documentPreview.getStyle().set("justify-content", "center");

        // In a real application, this would display the actual document
        // For this example, we'll just show a placeholder
        if (document.getFilename().endsWith(".pdf")) {
            Span placeholder = new Span("PDF Document Preview");
            placeholder.getStyle().set("font-size", "var(--lumo-font-size-xl)");
            placeholder.getStyle().set("color", "var(--lumo-secondary-text-color)");
            documentPreview.add(placeholder);
        } else if (document.getFilename().endsWith(".jpg") || document.getFilename().endsWith(".png")) {
            // For image files, we could display a placeholder image
            Image image = new Image("images/placeholder-image.png", "Document Preview");
            image.setMaxWidth("100%");
            image.setMaxHeight("100%");
            documentPreview.add(image);
        } else {
            Span placeholder = new Span("Document Preview Not Available");
            placeholder.getStyle().set("font-size", "var(--lumo-font-size-xl)");
            placeholder.getStyle().set("color", "var(--lumo-secondary-text-color)");
            documentPreview.add(placeholder);
        }

        FlexBoxLayout previewSection = new FlexBoxLayout(title, documentPreview);
        previewSection.setFlexDirection(FlexDirection.COLUMN);
        previewSection.setMargin(Top.L);
        previewSection.addClassName(BoxShadowBorders.BOTTOM);
        previewSection.setPadding(Bottom.L);

        return previewSection;
    }

    private Component createActionsSection() {
        Span title = new Span("Actions");
        title.addClassName(LumoStyles.Heading.H3);

        Button downloadButton = new Button("Download", VaadinIcon.DOWNLOAD.create());
        downloadButton.addClickListener(e -> {
            // In a real application, this would download the document
            UI.getCurrent().getPage().executeJs("alert('Download functionality would be implemented here.');");
        });

        Button printButton = new Button("Print", VaadinIcon.PRINT.create());
        printButton.addClickListener(e -> {
            // In a real application, this would print the document
            UI.getCurrent().getPage().executeJs("alert('Print functionality would be implemented here.');");
        });

        Button shareButton = new Button("Share", VaadinIcon.SHARE.create());
        shareButton.addClickListener(e -> {
            // In a real application, this would share the document
            UI.getCurrent().getPage().executeJs("alert('Share functionality would be implemented here.');");
        });

        Button updateStatusButton = new Button("Update Status", VaadinIcon.EDIT.create());
        updateStatusButton.addClickListener(e -> {
            // In a real application, this would open a dialog to update the status
            UI.getCurrent().getPage().executeJs("alert('Status update functionality would be implemented here.');");
        });

        HorizontalLayout actions = new HorizontalLayout();
        actions.add(downloadButton, printButton, shareButton, updateStatusButton);
        actions.setSpacing(true);

        FlexBoxLayout actionsSection = new FlexBoxLayout();
        actionsSection.add(title);
        actionsSection.add(actions);
        actionsSection.setFlexDirection(FlexDirection.COLUMN);
        actionsSection.setMargin(Top.L);
        actionsSection.setPadding(Bottom.L);

        return actionsSection;
    }

    private Badge createStatusBadge(String status) {
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

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        initAppBar();
    }

    private AppBar initAppBar() {
        AppBar appBar = MainLayout.get().getAppBar();
        appBar.setNaviMode(AppBar.NaviMode.CONTEXTUAL);
        appBar.getContextIcon().addClickListener(e -> UI.getCurrent().navigate("document-management/customer-documentation"));
        appBar.setTitle("Document: " + document.getDocumentId());
        return appBar;
    }

}
