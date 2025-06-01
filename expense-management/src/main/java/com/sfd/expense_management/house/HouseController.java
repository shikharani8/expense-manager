package com.sfd.expense_management.house;

import com.sfd.expense_management.house.dtos.HouseCreatePayload;
import com.sfd.expense_management.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/house")
public class HouseController {
    private final HouseService houseService;

    @PostMapping
    public ResponseEntity<House> create(@RequestBody HouseCreatePayload houseCreatePayload){
        return ResponseEntity.ok(houseService.create(houseCreatePayload));
    }

    @GetMapping
    public ResponseEntity<List<House>> getAll(){
        return ResponseEntity.ok(houseService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<House> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(houseService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<House> assignOwner(@PathVariable("id") Long id, @RequestBody User owner){
        return ResponseEntity.ok(houseService.assignOwner(id, owner));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        return ResponseEntity.ok(houseService.delete(id));
    }
}
