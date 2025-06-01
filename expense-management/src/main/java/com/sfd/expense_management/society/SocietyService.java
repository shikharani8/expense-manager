package com.sfd.expense_management.society;

import com.sfd.expense_management.society.dto.SocietyUpdateRequest;
import com.sfd.expense_management.user.User;

import java.util.List;

public interface SocietyService {
    Society create(Society society);
    List<Society> getAll();
    Society getById(Long id);
    Society update(Long id, SocietyUpdateRequest societyUpdateRequest);
    Society assignSuperAdmin(Long id, User superAdmin);
    Society removeStreet(Long societyId, Long streetId);
    String delete(Long id);

}
