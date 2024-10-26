package com.treaps.common.models;

import com.treaps.common.enums.NotificationChannel;
import com.treaps.common.enums.NotificationGroup;
import com.treaps.common.enums.RecipientType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    private RecipientType recipientType;
    private PhoneNumber recipientPhoneNumber;
    private String recipientEmail;
    private List<NotificationChannel> channel;
    private String title;
    private String body;
    private NotificationGroup group;
    private Instant createdAt = Instant.now();
    private Instant expiryAt;
    private Map<String, Object> data;
}