package com.sfd.expense_management.street;

import com.sfd.expense_management.house.House;
import com.sfd.expense_management.street.dtos.StreetCreatePayload;
import com.sfd.expense_management.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/street")
public class StreetController {
    private final StreetService streetService;
    @PostMapping
    public ResponseEntity<Street> create(@RequestBody StreetCreatePayload streetCreatePayload){
        return ResponseEntity.ok(streetService.create(streetCreatePayload));
    }

    @GetMapping
    public ResponseEntity<List<Street>> getAll(){
        return ResponseEntity.ok(streetService.getAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Street> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(streetService.getById(id));
    }
    @PutMapping("/{id}/{name}")
    public ResponseEntity<Street> updateName(@PathVariable("id") Long id,@PathVariable("name") String name){
        return ResponseEntity.ok(streetService.updateName(id, name));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Street> assignAdmin(@PathVariable("id") Long id, @RequestBody User admin){
        return ResponseEntity.ok(streetService.assignAdmin(id, admin));
    }

    @PutMapping("/addHouse/{id}")
    public ResponseEntity<Street> addHouses(@PathVariable("id") Long id, @RequestBody Set<House> houseList){
        return ResponseEntity.ok(streetService.addHouses(id,houseList));
    }

    @PutMapping("/{streetId}/{houseId}")
    public ResponseEntity<Street> removeHouse(@PathVariable("streetId") Long streetId,
                                              @PathVariable("houseId") Long houseId){
        return ResponseEntity.ok(streetService.removeHouse(streetId, houseId));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        return ResponseEntity.ok(streetService.delete(id));
    }
}
