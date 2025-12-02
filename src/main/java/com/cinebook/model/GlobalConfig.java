package com.cinebook.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "global_config")
public class GlobalConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long configId;

    @Column(nullable = false, unique = true)
    private String configKey;  // e.g., "TAX_PERCENTAGE", "LOYALTY_FACTOR"

    @Column(nullable = false)
    private String configValue; // store as string, parse to float/int as needed

    @Column
    private String description; // optional, for admin reference

    // --- Getters & Setters ---
    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
