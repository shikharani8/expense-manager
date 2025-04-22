package com.sfd.expense_management.notifications.dto;

import lombok.Data;

@Data
public class NotificationDto {
    private String sentFrom;
    private String  sentTo;
    private String subject;
    private String body;

}
