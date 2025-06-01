package com.sfd.expense_management.notifications;

import com.sfd.expense_management.notifications.dto.NotificationDto;
import jakarta.mail.MessagingException;

import java.util.Map;

public interface NotificationSender {
    public void send(NotificationDto notificationDto, Map<String, String> model);
}
