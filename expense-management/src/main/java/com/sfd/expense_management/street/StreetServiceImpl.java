package com.sfd.expense_management.street;

import com.sfd.expense_management.house.House;
import com.sfd.expense_management.house.HouseService;
import com.sfd.expense_management.street.dtos.StreetCreatePayload;
import com.sfd.expense_management.user.User;
import com.sfd.expense_management.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StreetServiceImpl implements StreetService{
    private final StreetRepository streetRepository;
    private final HouseService houseService;
    private final UserService userService;

    @Override
    public Street create(StreetCreatePayload streetCreatePayload) {
        User admin = userService.getUserById(streetCreatePayload.getAdminId());
        Street street = new Street();
        street.setName(streetCreatePayload.getName());
        street.setAdmin(admin);
        if(Objects.nonNull(streetCreatePayload.getHouses()) && !streetCreatePayload.getHouses().isEmpty()){
            street.setHouses(streetCreatePayload.getHouses());
        }
        return streetRepository.save(street);
    }

    @Override
    public List<Street> getAll() {
        return streetRepository.findAll();
    }

    @Override
    public Street getById(Long id) {
        return streetRepository.findById(id)
                .orElseThrow(()->new StreetException("Street doesn't exist!", HttpStatus.NOT_FOUND.value()));
    }

    @Override
    public Street updateName(Long id, String name) {
        Street street = getById(id);
        street.setName(name);
        return streetRepository.save(street);
    }

    @Override
    public Street assignAdmin(Long id, User admin) {
        Street street = getById(id);
        street.setAdmin(admin);
        return streetRepository.save(street);
    }

    @Override
    public Street addHouses(Long id, Set<House> houseList) {
        Street street = getById(id);
        street.setHouses(houseList);
        return streetRepository.save(street);
    }

    @Override
    public Street removeHouse(Long streetId, Long houseId) {
        Street street = getById(streetId);
        House house = houseService.getById(houseId);
        street.getHouses().remove(house);
        return streetRepository.save(street);
    }

    @Override
    public String delete(Long id) {
        getById(id);
        streetRepository.deleteById(id);
        return "Street deleted successfully!";
    }
}
