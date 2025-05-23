package com.vaadin.starter.business.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.ErrorHandler;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.starter.business.ui.components.FlexBoxLayout;
import com.vaadin.starter.business.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.business.ui.components.navigation.bar.TabBar;
import com.vaadin.starter.business.ui.components.navigation.drawer.NaviDrawer;
import com.vaadin.starter.business.ui.components.navigation.drawer.NaviItem;
import com.vaadin.starter.business.ui.components.navigation.drawer.NaviMenu;
import com.vaadin.starter.business.ui.constants.NavigationConstants;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.util.css.Overflow;
import com.vaadin.starter.business.ui.views.Home;
import com.vaadin.starter.business.ui.views.cashmanagement.CashPositions;
import com.vaadin.starter.business.ui.views.cashmanagement.CurrencyExchange;
import com.vaadin.starter.business.ui.views.cashmanagement.LiquidityManagement;
import com.vaadin.starter.business.ui.views.cashmanagement.TreasuryOperations;
import com.vaadin.starter.business.ui.views.customerservice.CaseManagement;
import com.vaadin.starter.business.ui.views.customerservice.CustomerRequests;
import com.vaadin.starter.business.ui.views.customerservice.IncidentManagement;
import com.vaadin.starter.business.ui.views.customerservice.ServiceTickets;
import com.vaadin.starter.business.ui.views.dashboard.DailyPerformanceMetrics;
import com.vaadin.starter.business.ui.views.dashboard.OperationalAlerts;
import com.vaadin.starter.business.ui.views.dashboard.OperationsOverview;
import com.vaadin.starter.business.ui.views.dashboard.ServiceLevelIndicators;
import com.vaadin.starter.business.ui.views.transactions.BatchOperations;
import com.vaadin.starter.business.ui.views.transactions.PaymentProcessing;
import com.vaadin.starter.business.ui.views.transactions.TransactionReconciliation;
import com.vaadin.starter.business.ui.views.transactions.TransactionSearchMonitoring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CssImport(value = "./styles/components/charts.css", themeFor = "vaadin-chart")
@CssImport(value = "./styles/components/floating-action-button.css", themeFor = "vaadin-button")
@CssImport(value = "./styles/components/grid.css", themeFor = "vaadin-grid")
@CssImport("./styles/lumo/border-radius.css")
@CssImport("./styles/lumo/icon-size.css")
@CssImport("./styles/lumo/margin.css")
@CssImport("./styles/lumo/padding.css")
@CssImport("./styles/lumo/shadow.css")
@CssImport("./styles/lumo/spacing.css")
@CssImport("./styles/lumo/typography.css")
@CssImport("./styles/misc/box-shadow-borders.css")
@CssImport(value = "./styles/styles.css", include = "lumo-badge")
@JsModule("@vaadin/vaadin-lumo-styles/badge")
public class MainLayout extends FlexBoxLayout
		implements RouterLayout, AfterNavigationObserver {

	private static final Logger log = LoggerFactory.getLogger(MainLayout.class);
	private static final String CLASS_NAME = "root";

	private Div appHeaderOuter;

	private FlexBoxLayout row;
	private NaviDrawer naviDrawer;
	private FlexBoxLayout column;

	private Div appHeaderInner;
	private FlexBoxLayout viewContainer;
	private Div appFooterInner;

	private Div appFooterOuter;

	private TabBar tabBar;
	private boolean navigationTabs = false;
	private AppBar appBar;

	public MainLayout() {
		VaadinSession.getCurrent()
				.setErrorHandler((ErrorHandler) errorEvent -> {
					log.error("Uncaught UI exception",
							errorEvent.getThrowable());
					Notification.show(
							"We are sorry, but an internal error occurred");
				});

		addClassName(CLASS_NAME);
		setFlexDirection(FlexDirection.COLUMN);
		setSizeFull();

		// Initialise the UI building blocks
		initStructure();

		// Populate the navigation drawer
		initNaviItems();

		// Configure the headers and footers (optional)
		initHeadersAndFooters();
	}

	/**
	 * Initialise the required components and containers.
	 */
	private void initStructure() {
		naviDrawer = new NaviDrawer();

		viewContainer = new FlexBoxLayout();
		viewContainer.addClassName(CLASS_NAME + "__view-container");
		viewContainer.setOverflow(Overflow.HIDDEN);

		column = new FlexBoxLayout(viewContainer);
		column.addClassName(CLASS_NAME + "__column");
		column.setFlexDirection(FlexDirection.COLUMN);
		column.setFlexGrow(1, viewContainer);
		column.setOverflow(Overflow.HIDDEN);

		row = new FlexBoxLayout(naviDrawer, column);
		row.addClassName(CLASS_NAME + "__row");
		row.setFlexGrow(1, column);
		row.setOverflow(Overflow.HIDDEN);
		add(row);
		setFlexGrow(1, row);
	}

	/**
	 * Initialise the navigation items.
	 */
	private void initNaviItems() {
		NaviMenu menu = naviDrawer.getMenu();
		NaviItem home = menu.addNaviItem(VaadinIcon.HOME, NavigationConstants.HOME, Home.class);
		home.setTitle(NavigationConstants.HOME);

		NaviItem dashboard = menu.addNaviItem(VaadinIcon.DASHBOARD, NavigationConstants.DASHBOARD,
				null);
		dashboard.setTitle(NavigationConstants.DASHBOARD);
		menu.addNaviItem(dashboard, NavigationConstants.OPERATIONS_OVERVIEW, OperationsOverview.class);
		menu.addNaviItem(dashboard, NavigationConstants.DAILY_PERFORMANCE_METRICS, DailyPerformanceMetrics.class);
		menu.addNaviItem(dashboard, NavigationConstants.SERVICE_LEVEL_INDICATORS, ServiceLevelIndicators.class);
		menu.addNaviItem(dashboard, NavigationConstants.OPERATIONAL_ALERTS, OperationalAlerts.class);
		dashboard.setSubItemsVisible(false);

		NaviItem transactions = menu.addNaviItem(VaadinIcon.EXCHANGE, NavigationConstants.TRANSACTION_MANAGEMENT,
				null);
		transactions.setTitle(NavigationConstants.TRANSACTION_MANAGEMENT);
		menu.addNaviItem(transactions, NavigationConstants.TRANSACTION_SEARCH_MONITORING, TransactionSearchMonitoring.class);
		menu.addNaviItem(transactions, NavigationConstants.PAYMENT_PROCESSING, PaymentProcessing.class);
		menu.addNaviItem(transactions, NavigationConstants.TRANSACTION_RECONCILIATION, TransactionReconciliation.class);
		menu.addNaviItem(transactions, NavigationConstants.BATCH_OPERATIONS, BatchOperations.class);
		transactions.setSubItemsVisible(false);

		NaviItem cashManagement = menu.addNaviItem(VaadinIcon.WALLET, NavigationConstants.CASH_MANAGEMENT,
				null);
		cashManagement.setTitle(NavigationConstants.CASH_MANAGEMENT);
		menu.addNaviItem(cashManagement, NavigationConstants.CASH_POSITIONS, CashPositions.class);
		menu.addNaviItem(cashManagement, NavigationConstants.LIQUIDITY_MANAGEMENT, LiquidityManagement.class);
		menu.addNaviItem(cashManagement, NavigationConstants.TREASURY_OPERATIONS, TreasuryOperations.class);
		menu.addNaviItem(cashManagement, NavigationConstants.CURRENCY_EXCHANGE, CurrencyExchange.class);
		cashManagement.setSubItemsVisible(false);

		NaviItem accountOperations = menu.addNaviItem(VaadinIcon.PIGGY_BANK, NavigationConstants.ACCOUNT_OPERATIONS,
				null);
		accountOperations.setTitle(NavigationConstants.ACCOUNT_OPERATIONS);
		menu.addNaviItem(accountOperations, NavigationConstants.ACCOUNT_MAINTENANCE, 
				com.vaadin.starter.business.ui.views.accountoperations.AccountMaintenance.class);
		menu.addNaviItem(accountOperations, NavigationConstants.STANDING_ORDERS, 
				com.vaadin.starter.business.ui.views.accountoperations.StandingOrders.class);
		menu.addNaviItem(accountOperations, NavigationConstants.DIRECT_DEBITS, 
				com.vaadin.starter.business.ui.views.accountoperations.DirectDebits.class);
		menu.addNaviItem(accountOperations, NavigationConstants.ACCOUNT_BLOCKING, 
				com.vaadin.starter.business.ui.views.accountoperations.AccountBlocking.class);
		accountOperations.setSubItemsVisible(false);

		NaviItem cardOperations = menu.addNaviItem(VaadinIcon.CREDIT_CARD, NavigationConstants.CARD_OPERATIONS,
				null);
		cardOperations.setTitle(NavigationConstants.CARD_OPERATIONS);
		menu.addNaviItem(cardOperations, NavigationConstants.CARD_ISSUANCE, 
				com.vaadin.starter.business.ui.views.cardoperations.CardIssuance.class);
		menu.addNaviItem(cardOperations, NavigationConstants.CARD_ACTIVATION, 
				com.vaadin.starter.business.ui.views.cardoperations.CardActivation.class);
		menu.addNaviItem(cardOperations, NavigationConstants.DISPUTES_MANAGEMENT, 
				com.vaadin.starter.business.ui.views.cardoperations.DisputesManagement.class);
		menu.addNaviItem(cardOperations, NavigationConstants.REPLACEMENT_REQUESTS, 
				com.vaadin.starter.business.ui.views.cardoperations.ReplacementRequests.class);
		cardOperations.setSubItemsVisible(false);

		NaviItem loanOperations = menu.addNaviItem(VaadinIcon.MONEY_EXCHANGE, NavigationConstants.LOAN_OPERATIONS,
				null);
		loanOperations.setTitle(NavigationConstants.LOAN_OPERATIONS);
		menu.addNaviItem(loanOperations, NavigationConstants.APPLICATION_PROCESSING, 
				com.vaadin.starter.business.ui.views.loanoperations.ApplicationProcessing.class);
		menu.addNaviItem(loanOperations, NavigationConstants.DISBURSEMENTS, 
				com.vaadin.starter.business.ui.views.loanoperations.Disbursements.class);
		menu.addNaviItem(loanOperations, NavigationConstants.COLLECTIONS, 
				com.vaadin.starter.business.ui.views.loanoperations.Collections.class);
		menu.addNaviItem(loanOperations, NavigationConstants.RESTRUCTURING, 
				com.vaadin.starter.business.ui.views.loanoperations.Restructuring.class);
		loanOperations.setSubItemsVisible(false);

		NaviItem customerService = menu.addNaviItem(VaadinIcon.USER_HEART, NavigationConstants.CUSTOMER_SERVICE,
				null);
		customerService.setTitle(NavigationConstants.CUSTOMER_SERVICE);
		menu.addNaviItem(customerService, NavigationConstants.CASE_MANAGEMENT, CaseManagement.class);
		menu.addNaviItem(customerService, NavigationConstants.CUSTOMER_REQUESTS, CustomerRequests.class);
		menu.addNaviItem(customerService, NavigationConstants.INCIDENT_MANAGEMENT, IncidentManagement.class);
		menu.addNaviItem(customerService, NavigationConstants.SERVICE_TICKETS, ServiceTickets.class);
		customerService.setSubItemsVisible(false);

		NaviItem fraudRiskOperations = menu.addNaviItem(VaadinIcon.SHIELD, NavigationConstants.FRAUD_RISK_OPERATIONS,
				null);
		fraudRiskOperations.setTitle(NavigationConstants.FRAUD_RISK_OPERATIONS);
		menu.addNaviItem(fraudRiskOperations, NavigationConstants.SUSPICIOUS_ACTIVITY_MONITORING, 
				com.vaadin.starter.business.ui.views.fraudriskoperations.SuspiciousActivityMonitoring.class);
		menu.addNaviItem(fraudRiskOperations, NavigationConstants.INVESTIGATIONS, 
				com.vaadin.starter.business.ui.views.fraudriskoperations.Investigations.class);
		menu.addNaviItem(fraudRiskOperations, NavigationConstants.AML_KYC_CASES, 
				com.vaadin.starter.business.ui.views.fraudriskoperations.AmlKycCases.class);
		menu.addNaviItem(fraudRiskOperations, NavigationConstants.RISK_ALERTS_MANAGEMENT, 
				com.vaadin.starter.business.ui.views.fraudriskoperations.RiskAlertsManagement.class);
		fraudRiskOperations.setSubItemsVisible(false);

		NaviItem documentManagement = menu.addNaviItem(VaadinIcon.FILE_TEXT, NavigationConstants.DOCUMENT_MANAGEMENT,
				null);
		documentManagement.setTitle(NavigationConstants.DOCUMENT_MANAGEMENT);
		menu.addNaviItem(documentManagement, NavigationConstants.CUSTOMER_DOCUMENTATION, 
				com.vaadin.starter.business.ui.views.documentmanagement.CustomerDocumentation.class);
		menu.addNaviItem(documentManagement, NavigationConstants.TRANSACTION_DOCUMENTS, 
				com.vaadin.starter.business.ui.views.documentmanagement.TransactionDocuments.class);
		menu.addNaviItem(documentManagement, NavigationConstants.SIGNATURES_AUTHORIZATIONS, 
				com.vaadin.starter.business.ui.views.documentmanagement.SignaturesAuthorizations.class);
		menu.addNaviItem(documentManagement, NavigationConstants.DOCUMENT_TRACKING, 
				com.vaadin.starter.business.ui.views.documentmanagement.DocumentTracking.class);
		documentManagement.setSubItemsVisible(false);

		NaviItem reportingAnalytics = menu.addNaviItem(VaadinIcon.CHART, NavigationConstants.REPORTING_ANALYTICS,
				null);
		reportingAnalytics.setTitle(NavigationConstants.REPORTING_ANALYTICS);
		menu.addNaviItem(reportingAnalytics, NavigationConstants.OPERATIONAL_REPORTS, 
				com.vaadin.starter.business.ui.views.reportinganalytics.OperationalReports.class);
		menu.addNaviItem(reportingAnalytics, NavigationConstants.COMPLIANCE_REPORTING, 
				com.vaadin.starter.business.ui.views.reportinganalytics.ComplianceReporting.class);
		menu.addNaviItem(reportingAnalytics, NavigationConstants.AUDIT_TRAILS, 
				com.vaadin.starter.business.ui.views.reportinganalytics.AuditTrails.class);
		menu.addNaviItem(reportingAnalytics, NavigationConstants.PERFORMANCE_ANALYTICS, 
				com.vaadin.starter.business.ui.views.reportinganalytics.PerformanceAnalytics.class);
		reportingAnalytics.setSubItemsVisible(false);

		NaviItem taskManagement = menu.addNaviItem(VaadinIcon.TASKS, NavigationConstants.TASK_MANAGEMENT,
				null);
		taskManagement.setTitle(NavigationConstants.TASK_MANAGEMENT);
		menu.addNaviItem(taskManagement, NavigationConstants.WORK_QUEUE, 
				com.vaadin.starter.business.ui.views.taskmanagement.WorkQueue.class);
		menu.addNaviItem(taskManagement, NavigationConstants.SLA_TRACKING,
				com.vaadin.starter.business.ui.views.taskmanagement.SLATracking.class);
		menu.addNaviItem(taskManagement, NavigationConstants.TEAM_PERFORMANCE, 
				com.vaadin.starter.business.ui.views.taskmanagement.TeamPerformance.class);
		taskManagement.setSubItemsVisible(false);
	}

	/**
	 * Configure the app's inner and outer headers and footers.
	 */
	private void initHeadersAndFooters() {
		// setAppHeaderOuter();
		// setAppFooterInner();
		// setAppFooterOuter();

		// Default inner header setup:
		// - When using tabbed navigation the view title, user avatar and main menu button will appear in the TabBar.
		// - When tabbed navigation is turned off they appear in the AppBar.

		appBar = new AppBar("");

		// Tabbed navigation
		if (navigationTabs) {
			tabBar = new TabBar();
			UIUtils.setTheme(Lumo.DARK, tabBar);

			// Shift-click to add a new tab
			for (NaviItem item : naviDrawer.getMenu().getNaviItems()) {
				item.addClickListener(e -> {
					if (e.getButton() == 0 && e.isShiftKey()) {
						tabBar.setSelectedTab(tabBar.addClosableTab(item.getText(), item.getNavigationTarget()));
					}
				});
			}
			appBar.getAvatar().setVisible(false);
			setAppHeaderInner(tabBar, appBar);

			// Default navigation
		} else {
			UIUtils.setTheme(Lumo.DARK, appBar);
			setAppHeaderInner(appBar);
		}
	}

	private void setAppHeaderOuter(Component... components) {
		if (appHeaderOuter == null) {
			appHeaderOuter = new Div();
			appHeaderOuter.addClassName("app-header-outer");
			getElement().insertChild(0, appHeaderOuter.getElement());
		}
		appHeaderOuter.removeAll();
		appHeaderOuter.add(components);
	}

	private void setAppHeaderInner(Component... components) {
		if (appHeaderInner == null) {
			appHeaderInner = new Div();
			appHeaderInner.addClassName("app-header-inner");
			column.getElement().insertChild(0, appHeaderInner.getElement());
		}
		appHeaderInner.removeAll();
		appHeaderInner.add(components);
	}

	private void setAppFooterInner(Component... components) {
		if (appFooterInner == null) {
			appFooterInner = new Div();
			appFooterInner.addClassName("app-footer-inner");
			column.getElement().insertChild(column.getElement().getChildCount(),
					appFooterInner.getElement());
		}
		appFooterInner.removeAll();
		appFooterInner.add(components);
	}

	private void setAppFooterOuter(Component... components) {
		if (appFooterOuter == null) {
			appFooterOuter = new Div();
			appFooterOuter.addClassName("app-footer-outer");
			getElement().insertChild(getElement().getChildCount(),
					appFooterOuter.getElement());
		}
		appFooterOuter.removeAll();
		appFooterOuter.add(components);
	}

	@Override
	public void showRouterLayoutContent(HasElement content) {
		this.viewContainer.getElement().appendChild(content.getElement());
	}

	public NaviDrawer getNaviDrawer() {
		return naviDrawer;
	}

	public static MainLayout get() {
		return (MainLayout) UI.getCurrent().getChildren()
				.filter(component -> component.getClass() == MainLayout.class)
				.findFirst().get();
	}

	public AppBar getAppBar() {
		return appBar;
	}

	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		if (navigationTabs) {
			afterNavigationWithTabs(event);
		} else {
			afterNavigationWithoutTabs(event);
		}
	}

	private void afterNavigationWithTabs(AfterNavigationEvent e) {
		NaviItem active = getActiveItem(e);
		if (active == null) {
			if (tabBar.getTabCount() == 0) {
				tabBar.addClosableTab("", Home.class);
			}
		} else {
			if (tabBar.getTabCount() > 0) {
				tabBar.updateSelectedTab(active.getText(),
						active.getNavigationTarget());
			} else {
				tabBar.addClosableTab(active.getText(),
						active.getNavigationTarget());
			}
		}
		appBar.getMenuIcon().setVisible(false);
	}

	private NaviItem getActiveItem(AfterNavigationEvent e) {
		for (NaviItem item : naviDrawer.getMenu().getNaviItems()) {
			if (item.isHighlighted(e)) {
				return item;
			}
		}
		return null;
	}

	private void afterNavigationWithoutTabs(AfterNavigationEvent e) {
		NaviItem active = getActiveItem(e);
		if (active != null) {
			getAppBar().setTitle(active.getText());
		}
	}

}
