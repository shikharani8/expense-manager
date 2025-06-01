package com.sfd.expense_management.house;

import com.sfd.expense_management.house.dtos.HouseCreatePayload;
import com.sfd.expense_management.user.User;
import com.sfd.expense_management.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService{
    private final HouseRepository houseRepository;
    private final UserService userService;

    @Override
    public House create(HouseCreatePayload houseCreatePayload) {
        User owner = userService.getUserById(houseCreatePayload.getOwnerId());
        House house = new House();
        house.setAddress(house.getAddress());
        house.setOwner(owner);
        house.setStreetId(houseCreatePayload.getStreetId());
        return houseRepository.save(house);
    }

    @Override
    public List<House> getAll() {
        return houseRepository.findAll();
    }

    @Override
    public House getById(Long id) {
        return houseRepository.findById(id)
                .orElseThrow(()-> new HouseException("House doesn't exist", HttpStatus.NOT_FOUND.value()));
    }

    @Override
    public House assignOwner(Long id, User owner) {
        House house = getById(id);
        house.setOwner(owner);
        return houseRepository.save(house);
    }

    @Override
    public String delete(Long id) {
        getById(id);
        houseRepository.deleteById(id);
        return "House deleted successfully!";
    }
}
