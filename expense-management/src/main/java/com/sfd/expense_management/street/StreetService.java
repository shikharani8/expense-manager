package com.sfd.expense_management.street;

import com.sfd.expense_management.house.House;
import com.sfd.expense_management.street.dtos.StreetCreatePayload;
import com.sfd.expense_management.user.User;

import java.util.List;
import java.util.Set;

public interface StreetService {
    Street create(StreetCreatePayload streetCreatePayload);
    List<Street> getAll();
    Street getById(Long id);
    Street updateName(Long id, String name);
    Street assignAdmin(Long id, User admin);
    Street addHouses(Long id, Set<House> houseList);
    Street removeHouse(Long streetId, Long houseId);
    String delete(Long id);


}
