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
import com.vaadin.starter.business.backend.dto.transactions.BatchJobDTO;
import com.vaadin.starter.business.backend.service.TransactionOperationsService;
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
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@PageTitle(NavigationConstants.BATCH_OPERATIONS)
@Route(value = "transactions/batch-operations", layout = MainLayout.class)
public class BatchOperations extends ViewFrame {

    private static final String CLASS_NAME = "batch-operations";
    public static final String MAX_WIDTH = "1024px";

    private final TransactionOperationsService transactionOperationsService;

    @Autowired
    public BatchOperations(TransactionOperationsService transactionOperationsService) {
        this.transactionOperationsService = transactionOperationsService;
        setViewContent(createContent());
    }

    private Component createContent() {
        Component batchSummary = createBatchSummary();
        Component batchPerformance = createBatchPerformance();
        Component recentBatches = createRecentBatches();

        FlexBoxLayout content = new FlexBoxLayout(batchSummary, batchPerformance, recentBatches);
        content.setAlignItems(FlexComponent.Alignment.CENTER);
        content.setFlexDirection(FlexDirection.COLUMN);
        return content;
    }

    private Component createBatchSummary() {
        FlexBoxLayout batchSummary = new FlexBoxLayout(
                createHeader(VaadinIcon.PACKAGE, "Batch Summary"),
                createBatchSummaryCards());
        batchSummary.setBoxSizing(BoxSizing.BORDER_BOX);
        batchSummary.setDisplay(Display.BLOCK);
        batchSummary.setMargin(com.vaadin.starter.business.ui.layout.size.Top.L);
        batchSummary.setMaxWidth(MAX_WIDTH);
        batchSummary.setPadding(Horizontal.RESPONSIVE_L);
        batchSummary.setWidthFull();
        return batchSummary;
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

    private Component createBatchSummaryCards() {
        Row cards = new Row();
        UIUtils.setBackgroundColor(LumoStyles.Color.BASE_COLOR, cards);
        UIUtils.setBorderRadius(BorderRadius.S, cards);
        UIUtils.setShadow(Shadow.XS, cards);

        cards.add(createSummaryCard("Total Batches", "24", ""));
        cards.add(createSummaryCard("Completed", "18", "green"));
        cards.add(createSummaryCard("In Progress", "4", "blue"));
        cards.add(createSummaryCard("Failed", "2", "red"));

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

    private Component createBatchPerformance() {
        FlexBoxLayout batchPerformance = new FlexBoxLayout(
                createHeader(VaadinIcon.CHART, "Batch Performance"),
                createBatchPerformanceChart());
        batchPerformance.setBoxSizing(BoxSizing.BORDER_BOX);
        batchPerformance.setDisplay(Display.BLOCK);
        batchPerformance.setMargin(com.vaadin.starter.business.ui.layout.size.Top.XL);
        batchPerformance.setMaxWidth(MAX_WIDTH);
        batchPerformance.setPadding(Horizontal.RESPONSIVE_L);
        batchPerformance.setWidthFull();
        return batchPerformance;
    }

    private Component createBatchPerformanceChart() {
        Chart chart = new Chart(ChartType.COLUMN);

        Configuration conf = chart.getConfiguration();
        conf.getChart().setStyledMode(true);
        conf.setTitle("Batch Processing Time (minutes)");

        XAxis xAxis = new XAxis();
        xAxis.setCategories("Payment Batch", "Statement Batch", "Interest Calculation", 
                           "Fee Processing", "End of Day");
        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Processing Time (minutes)");
        conf.addyAxis(yAxis);

        ListSeries currentSeries = new ListSeries("Current Week");
        currentSeries.setData(12, 18, 25, 8, 35);

        ListSeries previousSeries = new ListSeries("Previous Week");
        previousSeries.setData(15, 20, 28, 10, 40);

        conf.addSeries(currentSeries);
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

    private Component createRecentBatches() {
        FlexBoxLayout recentBatches = new FlexBoxLayout(
                createHeader(VaadinIcon.TABLE, "Recent Batch Jobs"),
                createRecentBatchesGrid());
        recentBatches.setBoxSizing(BoxSizing.BORDER_BOX);
        recentBatches.setDisplay(Display.BLOCK);
        recentBatches.setMargin(com.vaadin.starter.business.ui.layout.size.Top.XL);
        recentBatches.setMaxWidth(MAX_WIDTH);
        recentBatches.setPadding(Horizontal.RESPONSIVE_L);
        recentBatches.setWidthFull();
        return recentBatches;
    }

    private Component createRecentBatchesGrid() {
        Grid<BatchJobDTO> grid = new Grid<>();
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeight("300px");

        grid.addColumn(BatchJobDTO::getId).setHeader("ID").setWidth("80px");
        grid.addColumn(BatchJobDTO::getName).setHeader("Batch Name").setWidth("200px");
        grid.addColumn(BatchJobDTO::getStartTime).setHeader("Start Time").setWidth("150px");
        grid.addColumn(BatchJobDTO::getEndTime).setHeader("End Time").setWidth("150px");
        grid.addColumn(BatchJobDTO::getDuration).setHeader("Duration").setWidth("100px");
        grid.addColumn(BatchJobDTO::getRecords).setHeader("Records").setWidth("100px");
        grid.addColumn(BatchJobDTO::getStatus).setHeader("Status").setWidth("120px");

        List<BatchJobDTO> batchJobs = transactionOperationsService.getBatchJobs();
        grid.setItems(batchJobs);

        FlexBoxLayout card = new FlexBoxLayout(grid);
        card.setBackgroundColor(LumoStyles.Color.BASE_COLOR);
        card.setBorderRadius(BorderRadius.S);
        card.setBoxSizing(BoxSizing.BORDER_BOX);
        card.setPadding(Uniform.M);
        card.setShadow(Shadow.XS);
        return card;
    }

}
