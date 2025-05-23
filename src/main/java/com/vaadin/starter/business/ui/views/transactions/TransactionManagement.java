package com.vaadin.starter.business.ui.views.transactions;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.components.FlexBoxLayout;
import com.vaadin.starter.business.ui.layout.size.Horizontal;
import com.vaadin.starter.business.ui.layout.size.Uniform;
import com.vaadin.starter.business.ui.views.ViewFrame;

@PageTitle("Transaction Management")
@Route(value = "transactions", layout = MainLayout.class)
public class TransactionManagement extends ViewFrame {

    public TransactionManagement() {
        setId("transaction-management");
        setViewContent(createContent());
    }

    private Component createContent() {
        Html welcome = new Html("<h1>Transaction Management</h1>");

        Html intro = new Html("<p>Welcome to the Transaction Management section. This area provides tools for searching, monitoring, and managing all transaction-related operations.</p>");

        Html instructions = new Html("<p>Use the navigation menu to access different transaction management features including Transaction Search & Monitoring, Payment Processing, Transaction Reconciliation, and Batch Operations.</p>");

        FlexBoxLayout content = new FlexBoxLayout(welcome, intro, instructions);
        content.setFlexDirection(FlexDirection.COLUMN);
        content.setMargin(Horizontal.AUTO);
        content.setMaxWidth("840px");
        content.setPadding(Uniform.RESPONSIVE_L);
        return content;
    }
}