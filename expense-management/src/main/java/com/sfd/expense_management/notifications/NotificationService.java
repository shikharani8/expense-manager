package com.sfd.expense_management.notifications;

import com.sfd.expense_management.notifications.dto.NotificationDto;

import java.util.Map;

public interface NotificationService {

    void sendNotification(NotificationDto notificationDto, NotificationType notificationType, Map<String, String> model);

}
