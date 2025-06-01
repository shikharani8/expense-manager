package com.sfd.expense_management.house;

import com.sfd.expense_management.house.dtos.HouseCreatePayload;
import com.sfd.expense_management.user.User;

import java.util.List;

public interface HouseService {
    House create(HouseCreatePayload houseCreatePayload);
    List<House> getAll();
    House getById(Long id);
    House assignOwner(Long id, User owner);
    String delete(Long id);
}
