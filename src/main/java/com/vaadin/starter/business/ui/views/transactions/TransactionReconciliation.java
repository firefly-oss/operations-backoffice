package com.vaadin.starter.business.ui.views.transactions;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.board.Row;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.components.FlexBoxLayout;
import com.vaadin.starter.business.ui.constants.NavigationConstants;
import com.vaadin.starter.business.ui.layout.size.Horizontal;
import com.vaadin.starter.business.ui.layout.size.Right;
import com.vaadin.starter.business.ui.layout.size.Uniform;
import com.vaadin.starter.business.ui.layout.size.Vertical;
import com.vaadin.starter.business.ui.util.IconSize;
import com.vaadin.starter.business.ui.util.LumoStyles;
import com.vaadin.starter.business.ui.util.TextColor;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.util.css.BorderRadius;
import com.vaadin.starter.business.ui.util.css.BoxSizing;
import com.vaadin.starter.business.ui.util.css.Display;
import com.vaadin.starter.business.ui.util.css.Shadow;
import com.vaadin.starter.business.ui.views.ViewFrame;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@PageTitle(NavigationConstants.TRANSACTION_RECONCILIATION)
@Route(value = "transactions/reconciliation", layout = MainLayout.class)
public class TransactionReconciliation extends ViewFrame {

    private static final String CLASS_NAME = "transaction-reconciliation";
    public static final String MAX_WIDTH = "1024px";

    public TransactionReconciliation() {
        setViewContent(createContent());
    }

    private Component createContent() {
        Component reconciliationSummary = createReconciliationSummary();
        Component discrepancyAnalysis = createDiscrepancyAnalysis();
        Component recentReconciliations = createRecentReconciliations();

        FlexBoxLayout content = new FlexBoxLayout(reconciliationSummary, discrepancyAnalysis, recentReconciliations);
        content.setAlignItems(FlexComponent.Alignment.CENTER);
        content.setFlexDirection(FlexDirection.COLUMN);
        return content;
    }

    private Component createReconciliationSummary() {
        FlexBoxLayout reconciliationSummary = new FlexBoxLayout(
                createHeader(VaadinIcon.CHECK, "Reconciliation Summary"),
                createReconciliationSummaryCards());
        reconciliationSummary.setBoxSizing(BoxSizing.BORDER_BOX);
        reconciliationSummary.setDisplay(Display.BLOCK);
        reconciliationSummary.setMargin(com.vaadin.starter.business.ui.layout.size.Top.L);
        reconciliationSummary.setMaxWidth(MAX_WIDTH);
        reconciliationSummary.setPadding(Horizontal.RESPONSIVE_L);
        reconciliationSummary.setWidthFull();
        return reconciliationSummary;
    }

    private FlexBoxLayout createHeader(VaadinIcon icon, String title) {
        FlexBoxLayout header = new FlexBoxLayout(
                UIUtils.createIcon(IconSize.M, TextColor.TERTIARY, icon),
                UIUtils.createH3Label(title));
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setMargin(com.vaadin.starter.business.ui.layout.size.Bottom.L, Horizontal.RESPONSIVE_L);
        header.setSpacing(Right.L);
        return header;
    }

    private Component createReconciliationSummaryCards() {
        Row cards = new Row();
        UIUtils.setBackgroundColor(LumoStyles.Color.BASE_COLOR, cards);
        UIUtils.setBorderRadius(BorderRadius.S, cards);
        UIUtils.setShadow(Shadow.XS, cards);

        cards.add(createSummaryCard("Total Transactions", "1,245", ""));
        cards.add(createSummaryCard("Reconciled", "1,230", "green"));
        cards.add(createSummaryCard("Pending", "10", "orange"));
        cards.add(createSummaryCard("Discrepancies", "5", "red"));

        return cards;
    }

    private Component createSummaryCard(String label, String value, String color) {
        Span labelSpan = new Span(label);
        labelSpan.getStyle().set("font-weight", "bold");

        H3 valueH3 = new H3(value);
        valueH3.getStyle().set("margin", "0.2em 0");
        if (!color.isEmpty()) {
            valueH3.getStyle().set("color", color);
        }

        FlexBoxLayout card = new FlexBoxLayout(labelSpan, valueH3);
        card.setFlexDirection(FlexDirection.COLUMN);
        card.setAlignItems(FlexComponent.Alignment.CENTER);
        card.setPadding(Uniform.M);
        card.setSpacing(Vertical.S);
        card.setHeight("120px");

        return card;
    }

    private Component createDiscrepancyAnalysis() {
        FlexBoxLayout discrepancyAnalysis = new FlexBoxLayout(
                createHeader(VaadinIcon.CHART, "Discrepancy Analysis"),
                createDiscrepancyChart());
        discrepancyAnalysis.setBoxSizing(BoxSizing.BORDER_BOX);
        discrepancyAnalysis.setDisplay(Display.BLOCK);
        discrepancyAnalysis.setMargin(com.vaadin.starter.business.ui.layout.size.Top.XL);
        discrepancyAnalysis.setMaxWidth(MAX_WIDTH);
        discrepancyAnalysis.setPadding(Horizontal.RESPONSIVE_L);
        discrepancyAnalysis.setWidthFull();
        return discrepancyAnalysis;
    }

    private Component createDiscrepancyChart() {
        Chart chart = new Chart(ChartType.COLUMN);

        Configuration conf = chart.getConfiguration();
        conf.getChart().setStyledMode(true);
        conf.setTitle("Discrepancies by Category");

        XAxis xAxis = new XAxis();
        xAxis.setCategories("Amount Mismatch", "Missing Transaction", "Duplicate Transaction", 
                           "Incorrect Status", "Other");
        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Number of Discrepancies");
        conf.addyAxis(yAxis);

        ListSeries series = new ListSeries("Current Month");
        series.setData(3, 1, 0, 1, 0);
        
        ListSeries previousSeries = new ListSeries("Previous Month");
        previousSeries.setData(2, 2, 1, 0, 1);

        conf.addSeries(series);
        conf.addSeries(previousSeries);

        FlexBoxLayout card = new FlexBoxLayout(chart);
        card.setBackgroundColor(LumoStyles.Color.BASE_COLOR);
        card.setBorderRadius(BorderRadius.S);
        card.setBoxSizing(BoxSizing.BORDER_BOX);
        card.setHeight("400px");
        card.setPadding(Uniform.M);
        card.setShadow(Shadow.XS);
        return card;
    }

    private Component createRecentReconciliations() {
        FlexBoxLayout recentReconciliations = new FlexBoxLayout(
                createHeader(VaadinIcon.TABLE, "Recent Reconciliations"),
                createRecentReconciliationsGrid());
        recentReconciliations.setBoxSizing(BoxSizing.BORDER_BOX);
        recentReconciliations.setDisplay(Display.BLOCK);
        recentReconciliations.setMargin(com.vaadin.starter.business.ui.layout.size.Top.XL);
        recentReconciliations.setMaxWidth(MAX_WIDTH);
        recentReconciliations.setPadding(Horizontal.RESPONSIVE_L);
        recentReconciliations.setWidthFull();
        return recentReconciliations;
    }

    private Component createRecentReconciliationsGrid() {
        Grid<ReconciliationRecord> grid = new Grid<>();
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeight("300px");

        grid.addColumn(ReconciliationRecord::getDate).setHeader("Date").setWidth("150px");
        grid.addColumn(ReconciliationRecord::getSystem).setHeader("System").setWidth("150px");
        grid.addColumn(ReconciliationRecord::getTransactions).setHeader("Transactions").setWidth("120px");
        grid.addColumn(ReconciliationRecord::getReconciled).setHeader("Reconciled").setWidth("120px");
        grid.addColumn(ReconciliationRecord::getDiscrepancies).setHeader("Discrepancies").setWidth("120px");
        grid.addColumn(ReconciliationRecord::getStatus).setHeader("Status").setWidth("120px");

        List<ReconciliationRecord> records = createMockReconciliationRecords();
        grid.setItems(records);

        FlexBoxLayout card = new FlexBoxLayout(grid);
        card.setBackgroundColor(LumoStyles.Color.BASE_COLOR);
        card.setBorderRadius(BorderRadius.S);
        card.setBoxSizing(BoxSizing.BORDER_BOX);
        card.setPadding(Uniform.M);
        card.setShadow(Shadow.XS);
        return card;
    }

    private List<ReconciliationRecord> createMockReconciliationRecords() {
        List<ReconciliationRecord> records = new ArrayList<>();
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        records.add(new ReconciliationRecord(
                today.format(formatter),
                "Core Banking",
                "245",
                "245",
                "0",
                "Completed"
        ));
        
        records.add(new ReconciliationRecord(
                today.minusDays(1).format(formatter),
                "Payment Gateway",
                "312",
                "310",
                "2",
                "Completed with Issues"
        ));
        
        records.add(new ReconciliationRecord(
                today.minusDays(2).format(formatter),
                "Core Banking",
                "278",
                "275",
                "3",
                "Completed with Issues"
        ));
        
        records.add(new ReconciliationRecord(
                today.minusDays(3).format(formatter),
                "Payment Gateway",
                "298",
                "298",
                "0",
                "Completed"
        ));
        
        records.add(new ReconciliationRecord(
                today.minusDays(4).format(formatter),
                "Core Banking",
                "256",
                "256",
                "0",
                "Completed"
        ));
        
        return records;
    }

    // Data class for reconciliation records
    public static class ReconciliationRecord {
        private String date;
        private String system;
        private String transactions;
        private String reconciled;
        private String discrepancies;
        private String status;

        public ReconciliationRecord(String date, String system, String transactions, 
                                   String reconciled, String discrepancies, String status) {
            this.date = date;
            this.system = system;
            this.transactions = transactions;
            this.reconciled = reconciled;
            this.discrepancies = discrepancies;
            this.status = status;
        }

        public String getDate() {
            return date;
        }

        public String getSystem() {
            return system;
        }

        public String getTransactions() {
            return transactions;
        }

        public String getReconciled() {
            return reconciled;
        }

        public String getDiscrepancies() {
            return discrepancies;
        }

        public String getStatus() {
            return status;
        }
    }
}