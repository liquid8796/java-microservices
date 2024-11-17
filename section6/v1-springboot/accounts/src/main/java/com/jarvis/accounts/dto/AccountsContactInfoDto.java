package com.jarvis.accounts.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "accounts")
public record AccountsContactInfoDto(String message, Map<String, String> contactDetails, List<String> onCallSupport) {
    @Override
    public String toString() {
        return "AccountsContactInfoDto{" +
            "message='" + message + '\'' +
            ", contactDetails=" + contactDetails +
            ", onCallSupport=" + onCallSupport +
            '}';
    }
}
