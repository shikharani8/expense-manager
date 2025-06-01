package com.sfd.expense_management.society.dto;

import com.sfd.expense_management.street.Street;
import lombok.Data;

import java.util.Set;

@Data
public class SocietyUpdateRequest {
    private String name;
    private Set<Street> streets;
}
