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
import com.vaadin.starter.business.backend.TreasuryOperation;
import com.vaadin.starter.business.backend.service.TreasuryOperationService;
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

@PageTitle("Treasury Operation Details")
@Route(value = "treasury-operation-details", layout = MainLayout.class)
public class TreasuryOperationDetails extends ViewFrame implements HasUrlParameter<String> {

    private ListItem id;
    private ListItem operationType;
    private ListItem status;
    private ListItem amount;
    private ListItem currency;
    private ListItem counterparty;
    private ListItem executionDate;
    private ListItem settlementDate;
    private ListItem trader;
    private ListItem instrument;
    private ListItem rate;
    private ListItem reference;
    private ListItem description;

    private TreasuryOperation treasuryOperation;
    private final TreasuryOperationService treasuryOperationService;

    @Autowired
    public TreasuryOperationDetails(TreasuryOperationService treasuryOperationService) {
        this.treasuryOperationService = treasuryOperationService;
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String id) {
        treasuryOperation = treasuryOperationService.getTreasuryOperationById(id);
        setViewContent(createContent());
    }

    private Component createContent() {
        FlexBoxLayout content = new FlexBoxLayout(
                createDetailsSection(),
                createRelatedOperationsHeader(),
                createRelatedOperationsList()
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

        operationType = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.EXCHANGE), "", "Operation Type");
        operationType.setDividerVisible(true);
        operationType.setId("operationType");
        operationType.setReverse(true);

        status = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.INFO_CIRCLE), "", "Status");
        status.setDividerVisible(true);
        status.setId("status");
        status.setReverse(true);

        amount = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.DOLLAR), "", "Amount");
        amount.setDividerVisible(true);
        amount.setId("amount");
        amount.setReverse(true);

        currency = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.MONEY), "", "Currency");
        currency.setDividerVisible(true);
        currency.setId("currency");
        currency.setReverse(true);

        counterparty = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.INSTITUTION), "", "Counterparty");
        counterparty.setDividerVisible(true);
        counterparty.setId("counterparty");
        counterparty.setReverse(true);

        executionDate = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.CALENDAR), "", "Execution Date");
        executionDate.setDividerVisible(true);
        executionDate.setId("executionDate");
        executionDate.setReverse(true);

        settlementDate = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.CALENDAR_CLOCK), "", "Settlement Date");
        settlementDate.setDividerVisible(true);
        settlementDate.setId("settlementDate");
        settlementDate.setReverse(true);

        trader = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.USER), "", "Trader");
        trader.setDividerVisible(true);
        trader.setId("trader");
        trader.setReverse(true);

        instrument = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.CHART), "", "Instrument");
        instrument.setDividerVisible(true);
        instrument.setId("instrument");
        instrument.setReverse(true);

        rate = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.TRENDING_UP), "", "Rate");
        rate.setDividerVisible(true);
        rate.setId("rate");
        rate.setReverse(true);

        reference = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.CLIPBOARD), "", "Reference");
        reference.setDividerVisible(true);
        reference.setId("reference");
        reference.setReverse(true);

        description = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.CLIPBOARD_TEXT), "", "Description");
        description.setReverse(true);
        description.setId("description");

        FlexBoxLayout listItems = new FlexBoxLayout(id, operationType, status, amount, currency, counterparty, 
                                                  executionDate, settlementDate, trader, instrument, rate, 
                                                  reference, description);
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

    private Component createRelatedOperationsHeader() {
        Span title = new Span("Related Operations");
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

    private Component createRelatedOperationsList() {
        Div items = new Div();
        items.addClassNames(BoxShadowBorders.BOTTOM, LumoStyles.Padding.Bottom.L);

        // Add a message if there are no related operations
        Span message = new Span("No related operations found.");
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
        UI.getCurrent().getPage().setTitle("Treasury Operation " + treasuryOperation.getId());

        // Set values for the treasury operation details
        id.setPrimaryText(treasuryOperation.getId());
        operationType.setPrimaryText(treasuryOperation.getOperationType());
        status.setPrimaryText(treasuryOperation.getStatus());

        // Format amount with currency
        String formattedAmount = UIUtils.formatAmount(treasuryOperation.getAmount()) + " " + treasuryOperation.getCurrency();
        amount.setPrimaryText(formattedAmount);

        // Set text color based on amount
        if (treasuryOperation.getAmount() >= 0) {
            UIUtils.setTextColor(TextColor.SUCCESS, amount.getPrimary());
        } else {
            UIUtils.setTextColor(TextColor.ERROR, amount.getPrimary());
        }

        currency.setPrimaryText(treasuryOperation.getCurrency());
        counterparty.setPrimaryText(treasuryOperation.getCounterparty());

        // Format execution date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        executionDate.setPrimaryText(treasuryOperation.getExecutionDate().format(formatter));

        // Format settlement date
        settlementDate.setPrimaryText(treasuryOperation.getSettlementDate().format(formatter));

        trader.setPrimaryText(treasuryOperation.getTrader());
        instrument.setPrimaryText(treasuryOperation.getInstrument());
        rate.setPrimaryText(treasuryOperation.getRate().toString() + "%");
        reference.setPrimaryText(treasuryOperation.getReference());
        description.setPrimaryText(treasuryOperation.getDescription());
    }

    private AppBar initAppBar() {
        AppBar appBar = MainLayout.get().getAppBar();
        appBar.setNaviMode(AppBar.NaviMode.CONTEXTUAL);
        appBar.getContextIcon().addClickListener(e -> UI.getCurrent().navigate(TreasuryOperations.class));
        appBar.setTitle("Treasury Operation " + treasuryOperation.getId());
        return appBar;
    }
}
