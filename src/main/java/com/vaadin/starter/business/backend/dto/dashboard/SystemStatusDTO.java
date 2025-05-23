package com.vaadin.starter.business.backend.dto.dashboard;

/**
 * Data Transfer Object for System Status information.
 */
public class SystemStatusDTO {
    private String system;
    private String status;
    private String uptime;

    public SystemStatusDTO() {
    }

    public SystemStatusDTO(String system, String status, String uptime) {
        this.system = system;
        this.status = status;
        this.uptime = uptime;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }
}