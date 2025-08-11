package com.vaadin.starter.business.ui.views.clients.tabs;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.starter.business.ui.components.Badge;
import com.vaadin.starter.business.ui.util.css.lumo.BadgeColor;
import com.vaadin.starter.business.ui.util.css.lumo.BadgeShape;
import com.vaadin.starter.business.ui.util.css.lumo.BadgeSize;
import com.vaadin.starter.business.ui.views.clients.ClientManagement.Client;

/**
 * Abstract base class for all client dashboard tabs.
 * This class defines the common structure and methods that all tabs will share.
 */
public abstract class AbstractTab extends VerticalLayout {

    protected Client client;

    /**
     * Constructor for AbstractTab.
     * 
     * @param client The client whose data is displayed in this tab
     */
    public AbstractTab(Client client) {
        this.client = client;

        // Common setup for all tabs
        setPadding(false);
        setSpacing(true);
        setSizeFull();

        // Initialize the tab content
        initContent();
    }

    /**
     * Initialize the content of the tab.
     * This method should be implemented by each concrete tab class.
     */
    protected abstract void initContent();

    /**
     * Get the tab name.
     * 
     * @return The name of the tab
     */
    public abstract String getTabName();

    /**
     * Creates a summary card with an icon, title, and value.
     * 
     * @param title The title of the card
     * @param value The value to display
     * @param icon The icon to display
     * @return The created card component
     */
    protected Component createSummaryCard(String title, String value, VaadinIcon icon) {
        Div card = new Div();
        card.getStyle().set("background-color", "var(--lumo-base-color)");
        card.getStyle().set("border-radius", "var(--lumo-border-radius)");
        card.getStyle().set("box-shadow", "0 0 0 1px var(--lumo-contrast-10pct)");
        card.getStyle().set("padding", "1rem");
        card.getStyle().set("width", "250px");
        card.getStyle().set("height", "120px");
        card.getStyle().set("display", "flex");
        card.getStyle().set("flex-direction", "column");
        card.getStyle().set("justify-content", "center");
        card.getStyle().set("align-items", "center");

        Span iconElement = new Span(icon.create());
        iconElement.getStyle().set("font-size", "24px");
        iconElement.getStyle().set("color", "var(--lumo-primary-color)");
        iconElement.getStyle().set("margin-bottom", "0.5rem");

        Span titleElement = new Span(title);
        titleElement.getStyle().set("color", "var(--lumo-secondary-text-color)");
        titleElement.getStyle().set("margin-bottom", "0.5rem");

        Span valueElement = new Span(value);
        valueElement.getStyle().set("font-size", "24px");
        valueElement.getStyle().set("font-weight", "bold");

        card.add(iconElement, titleElement, valueElement);
        return card;
    }

    /**
     * Creates a status badge with a color based on the status.
     * 
     * @param status The status text
     * @return The created badge component
     */
    protected Component createStatusBadge(String status) {
        BadgeColor color;
        switch (status) {
            case "Active":
            case "Completed":
            case "Approved":
                color = BadgeColor.SUCCESS;
                break;
            case "Inactive":
            case "Expired":
            case "Closed":
                color = BadgeColor.ERROR_PRIMARY;
                break;
            case "Blocked":
            case "Rejected":
            case "Failed":
                color = BadgeColor.ERROR;
                break;
            case "Pending":
            case "Processing":
                color = BadgeColor.CONTRAST;
                break;
            default:
                color = BadgeColor.NORMAL;
        }

        return new Badge(status, color, BadgeSize.S, BadgeShape.PILL);
    }
}
