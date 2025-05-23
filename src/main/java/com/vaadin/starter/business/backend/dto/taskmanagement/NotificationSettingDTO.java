package com.vaadin.starter.business.backend.dto.taskmanagement;

/**
 * Data Transfer Object for SLA Notification Setting.
 */
public class NotificationSettingDTO {

    private String event;
    private String recipients;
    private String channel;
    private String enabled;

    /**
     * Default constructor.
     */
    public NotificationSettingDTO() {
    }

    /**
     * Constructor with all fields.
     *
     * @param event      the notification event
     * @param recipients the notification recipients
     * @param channel    the notification channel
     * @param enabled    whether the notification is enabled
     */
    public NotificationSettingDTO(String event, String recipients, String channel, String enabled) {
        this.event = event;
        this.recipients = recipients;
        this.channel = channel;
        this.enabled = enabled;
    }

    // Getters and setters
    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getRecipients() {
        return recipients;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }
}