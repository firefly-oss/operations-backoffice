package com.vaadin.starter.business.ui.views.transactions;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.business.backend.Transaction;
import com.vaadin.starter.business.backend.service.TransactionService;
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
import com.vaadin.starter.business.ui.util.TextColor;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.util.css.WhiteSpace;
import com.vaadin.starter.business.ui.views.ViewFrame;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.format.DateTimeFormatter;

@PageTitle("Transaction Details")
@Route(value = "transaction-details", layout = MainLayout.class)
public class TransactionDetails extends ViewFrame implements HasUrlParameter<String> {

    private ListItem transactionId;
    private ListItem type;
    private ListItem status;
    private ListItem amount;
    private ListItem accounts;
    private ListItem reference;
    private ListItem timestamp;
    private ListItem description;

    private Transaction transaction;
    private final TransactionService transactionService;

    @Autowired
    public TransactionDetails(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String id) {
        transaction = transactionService.getTransactionById(id);
        setViewContent(createContent());
    }

    private Component createContent() {
        FlexBoxLayout content = new FlexBoxLayout(
                createDetailsSection(),
                createRelatedTransactionsHeader(),
                createRelatedTransactionsList()
        );
        content.setFlexDirection(FlexDirection.COLUMN);
        content.setMargin(Horizontal.AUTO, Vertical.RESPONSIVE_L);
        content.setMaxWidth("840px");
        return content;
    }

    private FlexBoxLayout createDetailsSection() {
        transactionId = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.HASH), "", "Transaction ID");
        transactionId.getPrimary().addClassName(LumoStyles.Heading.H2);
        transactionId.setDividerVisible(true);
        transactionId.setId("transactionId");
        transactionId.setReverse(true);

        type = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.EXCHANGE), "", "Type");
        type.setDividerVisible(true);
        type.setId("type");
        type.setReverse(true);

        status = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.INFO_CIRCLE), "", "Status");
        status.setDividerVisible(true);
        status.setId("status");
        status.setReverse(true);

        amount = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.DOLLAR), "", "Amount");
        amount.setDividerVisible(true);
        amount.setId("amount");
        amount.setReverse(true);

        accounts = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.CREDIT_CARD), "", "Accounts");
        accounts.setDividerVisible(true);
        accounts.setId("accounts");
        accounts.setReverse(true);
        accounts.setWhiteSpace(WhiteSpace.PRE_LINE);

        reference = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.CLIPBOARD), "", "Reference");
        reference.setDividerVisible(true);
        reference.setId("reference");
        reference.setReverse(true);

        timestamp = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.CALENDAR), "", "Timestamp");
        timestamp.setDividerVisible(true);
        timestamp.setId("timestamp");
        timestamp.setReverse(true);

        description = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.CLIPBOARD_TEXT), "", "Description");
        description.setReverse(true);
        description.setId("description");

        FlexBoxLayout listItems = new FlexBoxLayout(transactionId, type, status, amount, accounts, reference, timestamp, description);
        listItems.setFlexDirection(FlexDirection.COLUMN);

        FlexBoxLayout section = new FlexBoxLayout(listItems);
        section.addClassName(BoxShadowBorders.BOTTOM);
        section.setAlignItems(FlexComponent.Alignment.CENTER);
        section.setFlex("1", listItems);
        section.setFlexWrap(FlexWrap.WRAP);
        section.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        section.setPadding(Bottom.L);
        return section;
    }

    private Component createRelatedTransactionsHeader() {
        Span title = new Span("Related Transactions");
        title.addClassName(LumoStyles.Heading.H3);

        Button viewAll = UIUtils.createSmallButton("View All");
        viewAll.addClickListener(
                e -> UIUtils.showNotification("Not implemented yet."));
        viewAll.addClassName(LumoStyles.Margin.Left.AUTO);

        FlexBoxLayout header = new FlexBoxLayout(title, viewAll);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setMargin(Bottom.M, Horizontal.RESPONSIVE_L, Top.L);
        return header;
    }

    private Component createRelatedTransactionsList() {
        Div items = new Div();
        items.addClassNames(BoxShadowBorders.BOTTOM, LumoStyles.Padding.Bottom.L);

        // Add a message if there are no related transactions
        Span message = new Span("No related transactions found.");
        message.getStyle().set("display", "block");
        message.getStyle().set("padding", "1em");
        message.getStyle().set("text-align", "center");
        items.add(message);

        return items;
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        initAppBar();
        UI.getCurrent().getPage().setTitle("Transaction " + transaction.getId());

        // Set values for the transaction details
        transactionId.setPrimaryText(transaction.getId());
        type.setPrimaryText(transaction.getType());
        status.setPrimaryText(transaction.getStatus());

        // Format amount with currency
        String formattedAmount = UIUtils.formatAmount(transaction.getAmount()) + " " + transaction.getCurrency();
        amount.setPrimaryText(formattedAmount);

        // Set text color based on amount
        if (transaction.getAmount() >= 0) {
            UIUtils.setTextColor(TextColor.SUCCESS, amount.getPrimary());
        } else {
            UIUtils.setTextColor(TextColor.ERROR, amount.getPrimary());
        }

        // Format accounts information
        String accountsText = "From: " + transaction.getSourceAccount() + "\nTo: " + transaction.getDestinationAccount();
        accounts.setPrimaryText(accountsText);

        reference.setPrimaryText(transaction.getReference());

        // Format timestamp
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        timestamp.setPrimaryText(transaction.getTimestamp().format(formatter));

        description.setPrimaryText(transaction.getDescription());
    }

    private AppBar initAppBar() {
        AppBar appBar = MainLayout.get().getAppBar();
        appBar.setNaviMode(AppBar.NaviMode.CONTEXTUAL);
        appBar.getContextIcon().addClickListener(e -> UI.getCurrent().navigate(TransactionSearchMonitoring.class));
        appBar.setTitle("Transaction " + transaction.getId());
        return appBar;
    }
}
