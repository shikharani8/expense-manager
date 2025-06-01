package com.sfd.expense_management.street.dtos;

import com.sfd.expense_management.house.House;
import lombok.Data;

import java.util.Set;

@Data
public class StreetCreatePayload {
    private String name;
    private Long adminId;
    private Set<House> houses;
}
