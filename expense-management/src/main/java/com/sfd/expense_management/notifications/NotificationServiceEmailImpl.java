package com.sfd.expense_management.notifications;

import com.sfd.expense_management.notifications.dto.NotificationDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NotificationServiceEmailImpl implements NotificationService{
    private final NotificationSender emailSender;
    private final NotificationSender smsSender;
    @Value("${notification.email.enabled}")
    private boolean emailEnabled;
    @Value("${notification.sms.enabled}")
    private boolean smsEnabled;
    @Value("${notification.whatsapp.enabled}")
    private boolean whatsappEnabled;
    @Value("${notification.push.enabled}")
    private boolean pushEnabled;

    public NotificationServiceEmailImpl(@Qualifier("email_sender") NotificationSender emailSender,
                                        @Qualifier("sms_sender") NotificationSender smsSender){
        this.emailSender = emailSender;
        this.smsSender = smsSender;
    }

    @Override
    public void sendNotification(NotificationDto notificationDto, NotificationType notificationType, Map<String, String> model) {

        if(emailEnabled){
            emailSender.send(notificationDto, model);
        }
       if(smsEnabled){
           smsSender.send(notificationDto, model);
       }
    }

}
