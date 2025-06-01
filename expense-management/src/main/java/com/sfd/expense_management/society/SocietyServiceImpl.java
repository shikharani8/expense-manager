package com.sfd.expense_management.society;

import com.sfd.expense_management.society.dto.SocietyUpdateRequest;
import com.sfd.expense_management.street.Street;
import com.sfd.expense_management.street.StreetService;
import com.sfd.expense_management.user.User;
import com.sfd.expense_management.user.UserException;
import com.sfd.expense_management.user.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SocietyServiceImpl implements SocietyService{
    private final SocietyRepository societyRepository;
    private final StreetService streetService;
    private final UserService userService;

    @Value("${superAdminRole}")
    private String roleName;

    @Override
    public Society create(Society society) {
        return societyRepository.save(society);
    }

    @Override
    public List<Society> getAll() {
        return societyRepository.findAll();
    }

    @Override
    public Society getById(Long id) {
        return societyRepository.findById(id)
                .orElseThrow(()-> new SocietyException("Society doesn't exist", HttpStatus.NOT_FOUND.value()));
    }

    @Override
    public Society update(Long id, SocietyUpdateRequest societyUpdateRequest) {
        Society society = getById(id);
        if(!StringUtils.isEmpty(societyUpdateRequest.getName())){
            society.setName(societyUpdateRequest.getName());
        }
        if(Objects.nonNull(societyUpdateRequest.getStreets()) && !societyUpdateRequest.getStreets().isEmpty()){
            society.setStreets(societyUpdateRequest.getStreets());
        }
        return societyRepository.save(society);
    }

    @Override
    public Society assignSuperAdmin(Long id, User superAdmin) {
        Society society = getById(id);
        User superAdminUser = userService.getUserById(superAdmin.getId());
        if(!superAdminUser.getRoles().contains(roleName)){
            throw new UserException("User is not Superadmin, please assign correct Superadmin", HttpStatus.BAD_REQUEST.value());
        }
        society.setSuperAdmin(superAdmin);
        return societyRepository.save(society);
    }

    @Override
    public Society removeStreet(Long societyId, Long streetId) {
        Society society = getById(societyId);
        Street street = streetService.getById(streetId);
        society.getStreets().remove(street);
        return societyRepository.save(society);
    }

    @Override
    public String delete(Long id) {
        Society society = getById(id);
        societyRepository.deleteById(id);
        return "Society is deleted successfully!";
    }
}
