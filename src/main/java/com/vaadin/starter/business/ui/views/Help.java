package com.vaadin.starter.business.ui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.components.FlexBoxLayout;
import com.vaadin.starter.business.ui.layout.size.Horizontal;
import com.vaadin.starter.business.ui.layout.size.Uniform;

@PageTitle("Help")
@Route(value = "help", layout = MainLayout.class)
public class Help extends ViewFrame {

    public Help() {
        setId("help");
        setViewContent(createContent());
    }

    private Component createContent() {
        FlexBoxLayout content = new FlexBoxLayout();
        content.setFlexDirection(FlexDirection.COLUMN);
        content.setMargin(Horizontal.AUTO);
        content.setMaxWidth("840px");
        content.setPadding(Uniform.RESPONSIVE_L);

        // Main title
        Html title = new Html("<h1>Help Center</h1>");
        content.add(title);

        // Introduction
        Html intro = new Html("<p>Welcome to the Firefly Operations Backoffice Help Center. This guide provides information about all currently active sections and what you can do in each of them.</p>");
        content.add(intro);

        // Home section
        H2 homeTitle = new H2("Home");
        Paragraph homeDesc = new Paragraph("The home page provides a welcome screen and overview of the Operations Backoffice application. It's your starting point for navigating to different operational modules.");
        content.add(homeTitle, homeDesc);

        // Dashboard section
        H2 dashboardTitle = new H2("Dashboard");
        Paragraph dashboardDesc = new Paragraph("The Dashboard section provides real-time insights and metrics about operational performance. It helps you monitor the overall health of banking operations.");
        content.add(dashboardTitle, dashboardDesc);

        // Dashboard subsections
        H3 operationsOverviewTitle = new H3("Operations Overview");
        Paragraph operationsOverviewDesc = new Paragraph("Provides a high-level summary of all operational activities across different departments. It displays key metrics, pending tasks, and critical alerts that require attention.");
        
        H3 dailyPerformanceTitle = new H3("Daily Performance Metrics");
        Paragraph dailyPerformanceDesc = new Paragraph("Shows detailed performance indicators for the current day, including transaction volumes, processing times, and service level achievements. It helps track daily operational efficiency.");
        
        H3 serviceLevelTitle = new H3("Service Level Indicators");
        Paragraph serviceLevelDesc = new Paragraph("Displays metrics related to service level agreements (SLAs), including response times, resolution rates, and compliance with operational standards. It helps ensure that services meet defined quality levels.");
        
        H3 operationalAlertsTitle = new H3("Operational Alerts");
        Paragraph operationalAlertsDesc = new Paragraph("Shows real-time alerts about operational issues that require immediate attention. These may include system outages, processing delays, or threshold breaches in key operational parameters.");
        
        content.add(operationsOverviewTitle, operationsOverviewDesc, 
                   dailyPerformanceTitle, dailyPerformanceDesc,
                   serviceLevelTitle, serviceLevelDesc,
                   operationalAlertsTitle, operationalAlertsDesc);

        // Distributors section
        H2 distributorsTitle = new H2("Distributors");
        Paragraph distributorsDesc = new Paragraph("The Distributors section allows you to manage distributor relationships, products, and users. It provides tools for overseeing the distribution network.");
        content.add(distributorsTitle, distributorsDesc);

        // Distributors subsections
        H3 distributorManagementTitle = new H3("Distributor Management");
        Paragraph distributorManagementDesc = new Paragraph("Allows you to add, edit, and manage distributor profiles. You can view distributor details, performance metrics, and manage contractual relationships with distributors.");
        
        H3 itemsTitle = new H3("Items");
        Paragraph itemsDesc = new Paragraph("Provides tools to manage products and services that are distributed through the network. You can update product information, pricing, and availability for different distributors.");
        
        H3 distributorUsersTitle = new H3("Distributor Users");
        Paragraph distributorUsersDesc = new Paragraph("Allows you to manage user accounts associated with distributors. You can create new users, assign roles and permissions, and monitor user activities within the distributor network.");
        
        content.add(distributorManagementTitle, distributorManagementDesc,
                   itemsTitle, itemsDesc,
                   distributorUsersTitle, distributorUsersDesc);

        // Clients section
        H2 clientsTitle = new H2("Clients");
        Paragraph clientsDesc = new Paragraph("The Clients section provides tools for managing client relationships, contracts, and compliance requirements. It helps ensure effective client service and regulatory compliance.");
        content.add(clientsTitle, clientsDesc);

        // Clients subsections
        H3 clientManagementTitle = new H3("Client Management");
        Paragraph clientManagementDesc = new Paragraph("Allows you to view and manage client profiles, including personal information, account details, and service preferences. You can update client information and track client interactions.");
        
        H3 contractsTitle = new H3("Contracts");
        Paragraph contractsDesc = new Paragraph("Provides tools to manage client contracts, including creation, modification, and termination of contractual agreements. You can track contract status, renewal dates, and compliance with terms.");
        
        H3 onboardingTitle = new H3("Onboarding Processes");
        Paragraph onboardingDesc = new Paragraph("Allows you to manage the client onboarding workflow, from application to account activation. You can track the progress of onboarding tasks, verify documentation, and ensure compliance with onboarding requirements.");
        
        H3 amlKycTitle = new H3("AML/KYC Cases");
        Paragraph amlKycDesc = new Paragraph("Provides tools for managing Anti-Money Laundering (AML) and Know Your Customer (KYC) compliance cases. You can review suspicious activities, verify client identities, and ensure regulatory compliance.");
        
        content.add(clientManagementTitle, clientManagementDesc,
                   contractsTitle, contractsDesc,
                   onboardingTitle, onboardingDesc,
                   amlKycTitle, amlKycDesc);

        return content;
    }
}