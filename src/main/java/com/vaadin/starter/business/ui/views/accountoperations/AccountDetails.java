package com.vaadin.starter.business.ui.views.accountoperations;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.business.backend.Account;
import com.vaadin.starter.business.backend.service.AccountService;
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.components.FlexBoxLayout;
import com.vaadin.starter.business.ui.components.ListItem;
import com.vaadin.starter.business.ui.constants.NavigationConstants;
import com.vaadin.starter.business.ui.layout.size.Horizontal;
import com.vaadin.starter.business.ui.layout.size.Top;
import com.vaadin.starter.business.ui.util.LumoStyles;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.util.css.BoxSizing;
import com.vaadin.starter.business.ui.views.ViewFrame;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.format.DateTimeFormatter;

@PageTitle(NavigationConstants.ACCOUNT_MAINTENANCE)
@Route(value = "account-operations/details", layout = MainLayout.class)
public class AccountDetails extends ViewFrame implements HasUrlParameter<String> {

    private final AccountService accountService;
    private Account account;

    @Autowired
    public AccountDetails(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String accountNumber) {
        account = accountService.getAccountByNumber(accountNumber);
        setViewContent(createContent());
    }

    private Component createContent() {
        FlexBoxLayout content = new FlexBoxLayout(createDetails());
        content.setBoxSizing(BoxSizing.BORDER_BOX);
        content.setHeightFull();
        content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);
        return content;
    }

    private Component createDetails() {
        if (account == null) {
            return new Span("Account not found");
        }

        // Header section with account number and name
        H3 header = new H3(account.getAccountName() + " (" + account.getAccountNumber() + ")");
        header.addClassName(LumoStyles.Heading.H3);

        // Account type and status
        ListItem accountTypeItem = new ListItem(
                VaadinIcon.CREDIT_CARD.create(),
                "Account Type",
                account.getAccountType()
        );

        ListItem statusItem = new ListItem(
                VaadinIcon.INFO_CIRCLE.create(),
                "Status",
                account.getStatus()
        );

        // Balance
        ListItem balanceItem = new ListItem(
                VaadinIcon.MONEY.create(),
                "Balance",
                UIUtils.formatAmount(account.getBalance())
        );

        // Customer ID
        ListItem customerItem = new ListItem(
                VaadinIcon.USER.create(),
                "Customer ID",
                account.getCustomerId()
        );

        // Open date
        ListItem openDateItem = new ListItem(
                VaadinIcon.CALENDAR.create(),
                "Open Date",
                account.getOpenDate().format(DateTimeFormatter.ofPattern("MMMM d, yyyy"))
        );

        // Action buttons
        Button editButton = UIUtils.createPrimaryButton("Edit");
        editButton.setIcon(VaadinIcon.EDIT.create());
        editButton.addClickListener(e -> {
            // Edit logic would go here
        });

        Button blockButton = account.getStatus().equals("Blocked") ?
                UIUtils.createSuccessButton("Unblock") :
                UIUtils.createErrorButton("Block");
        blockButton.setIcon(account.getStatus().equals("Blocked") ?
                VaadinIcon.UNLOCK.create() :
                VaadinIcon.LOCK.create());
        blockButton.addClickListener(e -> {
            if (account.getStatus().equals("Blocked")) {
                account = accountService.unblockAccount(account.getAccountNumber());
            } else {
                account = accountService.blockAccount(account.getAccountNumber());
            }
            setViewContent(createContent());
        });

        Button backButton = UIUtils.createTertiaryButton("Back");
        backButton.setIcon(VaadinIcon.ARROW_LEFT.create());
        backButton.addClickListener(e -> {
            getUI().ifPresent(ui -> ui.navigate(AccountMaintenance.class));
        });

        HorizontalLayout actions = new HorizontalLayout(backButton, editButton, blockButton);
        actions.setSpacing(true);

        // Create a card-like container for the details
        Div card = new Div();
        card.getStyle().set("background-color", "var(--lumo-base-color)");
        card.getStyle().set("border-radius", "var(--lumo-border-radius)");
        card.getStyle().set("box-shadow", "0 0 0 1px var(--lumo-contrast-10pct)");
        card.getStyle().set("padding", "var(--lumo-space-m)");
        card.getStyle().set("margin-bottom", "var(--lumo-space-m)");

        VerticalLayout details = new VerticalLayout(
                header,
                accountTypeItem,
                statusItem,
                balanceItem,
                customerItem,
                openDateItem,
                actions
        );
        details.setPadding(false);
        details.setSpacing(true);

        card.add(details);
        return card;
    }
}
