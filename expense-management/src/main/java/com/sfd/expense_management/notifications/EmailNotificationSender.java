package com.sfd.expense_management.notifications;

import com.sfd.expense_management.notifications.dto.NotificationDto;
import com.sfd.expense_management.user.User;
import com.sfd.expense_management.user.UserHelper;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service("email_sender")
@Slf4j
@RequiredArgsConstructor
public class EmailNotificationSender implements NotificationSender {
    private final JavaMailSender mailSender;
    private final VelocityEngine velocityEngine;

    @Override
    public void send(NotificationDto notificationDto, Map<String, String> model) {
        try {
            User loggedInUser = UserHelper.getLoggedInUser();
            if(!loggedInUser.isEmailNotificationEnabled()){
                throw new NotificationException("User email preference is Off, please On it for sending Emails", HttpStatus.BAD_REQUEST.value());
            }
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message);
            Map<String, Object> valueMap = new HashMap<>();
            valueMap.put("username", model.get("username"));
            valueMap.put("otp", model.get("otp"));
            VelocityContext velocityContext = new VelocityContext(valueMap);
            StringWriter stringWriter = new StringWriter();
            velocityEngine.mergeTemplate("templates/" + model.get("emailTemplate"), "UTF-8", velocityContext, stringWriter);
            mimeMessageHelper.setFrom(notificationDto.getSentFrom());
            mimeMessageHelper.setTo(notificationDto.getSentTo());
            mimeMessageHelper.setSubject(notificationDto.getSubject());
            mimeMessageHelper.setText(stringWriter.toString(), true);
            mailSender.send(message);
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
