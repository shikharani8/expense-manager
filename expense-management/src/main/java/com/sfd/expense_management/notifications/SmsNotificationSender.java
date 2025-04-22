package com.sfd.expense_management.notifications;

import com.sfd.expense_management.notifications.dto.NotificationDto;
import jakarta.websocket.OnError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("sms_sender")
@Slf4j
@RequiredArgsConstructor
public class SmsNotificationSender implements NotificationSender {
    @Override
    public void send(NotificationDto notificationDto, Map<String, String> model) {
        //TODO
    }
}
