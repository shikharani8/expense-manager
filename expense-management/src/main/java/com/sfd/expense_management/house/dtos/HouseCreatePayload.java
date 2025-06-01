package com.sfd.expense_management.house.dtos;

import com.sfd.expense_management.user.User;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
public class HouseCreatePayload {
    private String address;
    private Long ownerId;
    private Long streetId;
}
