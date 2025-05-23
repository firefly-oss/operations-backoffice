package com.vaadin.starter.business.ui.views.cashmanagement;

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
import com.vaadin.starter.business.backend.CashPosition;
import com.vaadin.starter.business.backend.service.CashPositionService;
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
import com.vaadin.starter.business.ui.views.ViewFrame;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.format.DateTimeFormatter;

@PageTitle("Cash Position Details")
@Route(value = "cash-position-details", layout = MainLayout.class)
public class CashPositionDetails extends ViewFrame implements HasUrlParameter<String> {

    private ListItem id;
    private ListItem accountNumber;
    private ListItem accountName;
    private ListItem accountType;
    private ListItem balance;
    private ListItem currency;
    private ListItem lastUpdated;
    private ListItem status;
    private ListItem bankName;
    private ListItem branchCode;
    private ListItem availableForTrading;

    private CashPosition cashPosition;
    private final CashPositionService cashPositionService;

    @Autowired
    public CashPositionDetails(CashPositionService cashPositionService) {
        this.cashPositionService = cashPositionService;
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String id) {
        cashPosition = cashPositionService.getCashPositionById(id);
        setViewContent(createContent());
    }

    private Component createContent() {
        FlexBoxLayout content = new FlexBoxLayout(
                createDetailsSection(),
                createRelatedAccountsHeader(),
                createRelatedAccountsList()
        );
        content.setFlexDirection(FlexDirection.COLUMN);
        content.setMargin(Horizontal.AUTO, Vertical.RESPONSIVE_L);
        content.setMaxWidth("840px");
        return content;
    }

    private FlexBoxLayout createDetailsSection() {
        id = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.HASH), "", "ID");
        id.getPrimary().addClassName(LumoStyles.Heading.H2);
        id.setDividerVisible(true);
        id.setId("id");
        id.setReverse(true);

        accountNumber = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.USER_CARD), "", "Account Number");
        accountNumber.setDividerVisible(true);
        accountNumber.setId("accountNumber");
        accountNumber.setReverse(true);

        accountName = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.USER), "", "Account Name");
        accountName.setDividerVisible(true);
        accountName.setId("accountName");
        accountName.setReverse(true);

        accountType = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.BRIEFCASE), "", "Account Type");
        accountType.setDividerVisible(true);
        accountType.setId("accountType");
        accountType.setReverse(true);

        balance = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.DOLLAR), "", "Balance");
        balance.setDividerVisible(true);
        balance.setId("balance");
        balance.setReverse(true);

        currency = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.MONEY), "", "Currency");
        currency.setDividerVisible(true);
        currency.setId("currency");
        currency.setReverse(true);

        lastUpdated = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.CALENDAR), "", "Last Updated");
        lastUpdated.setDividerVisible(true);
        lastUpdated.setId("lastUpdated");
        lastUpdated.setReverse(true);

        status = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.INFO_CIRCLE), "", "Status");
        status.setDividerVisible(true);
        status.setId("status");
        status.setReverse(true);

        bankName = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.INSTITUTION), "", "Bank Name");
        bankName.setDividerVisible(true);
        bankName.setId("bankName");
        bankName.setReverse(true);

        branchCode = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.OFFICE), "", "Branch Code");
        branchCode.setDividerVisible(true);
        branchCode.setId("branchCode");
        branchCode.setReverse(true);

        availableForTrading = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.CHART), "", "Available for Trading");
        availableForTrading.setReverse(true);
        availableForTrading.setId("availableForTrading");

        FlexBoxLayout listItems = new FlexBoxLayout(id, accountNumber, accountName, accountType, balance, currency, 
                                                   lastUpdated, status, bankName, branchCode, availableForTrading);
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

    private Component createRelatedAccountsHeader() {
        Span title = new Span("Related Accounts");
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

    private Component createRelatedAccountsList() {
        Div items = new Div();
        items.addClassNames(BoxShadowBorders.BOTTOM, LumoStyles.Padding.Bottom.L);

        // Add a message if there are no related accounts
        Span message = new Span("No related accounts found.");
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
        UI.getCurrent().getPage().setTitle("Cash Position " + cashPosition.getId());

        // Set values for the cash position details
        id.setPrimaryText(cashPosition.getId());
        accountNumber.setPrimaryText(cashPosition.getAccountNumber());
        accountName.setPrimaryText(cashPosition.getAccountName());
        accountType.setPrimaryText(cashPosition.getAccountType());

        // Format balance with currency
        String formattedBalance = UIUtils.formatAmount(cashPosition.getBalance()) + " " + cashPosition.getCurrency();
        balance.setPrimaryText(formattedBalance);

        // Set text color based on balance
        if (cashPosition.getBalance() >= 0) {
            UIUtils.setTextColor(TextColor.SUCCESS, balance.getPrimary());
        } else {
            UIUtils.setTextColor(TextColor.ERROR, balance.getPrimary());
        }

        currency.setPrimaryText(cashPosition.getCurrency());

        // Format last updated timestamp
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        lastUpdated.setPrimaryText(cashPosition.getLastUpdated().format(formatter));

        status.setPrimaryText(cashPosition.getStatus());
        bankName.setPrimaryText(cashPosition.getBankName());
        branchCode.setPrimaryText(cashPosition.getBranchCode());
        availableForTrading.setPrimaryText(cashPosition.getAvailableForTrading());
    }

    private AppBar initAppBar() {
        AppBar appBar = MainLayout.get().getAppBar();
        appBar.setNaviMode(AppBar.NaviMode.CONTEXTUAL);
        appBar.getContextIcon().addClickListener(e -> UI.getCurrent().navigate(CashPositions.class));
        appBar.setTitle("Cash Position " + cashPosition.getId());
        return appBar;
    }
}