package com.sfd.expense_management.society;

import com.sfd.expense_management.society.dto.SocietyUpdateRequest;
import com.sfd.expense_management.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/society")
public class SocietyController {
    private final SocietyService societyService;
    @PostMapping
    public ResponseEntity<Society> create(@RequestBody Society society){
        return ResponseEntity.ok(societyService.create(society));
    }

    @GetMapping
    public ResponseEntity<List<Society>> getAll(){
        return ResponseEntity.ok(societyService.getAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Society> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(societyService.getById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Society> update(@PathVariable("id") Long id,
                                          @RequestBody SocietyUpdateRequest societyUpdateRequest){
        return ResponseEntity.ok(societyService.update(id, societyUpdateRequest));
    }

    @PutMapping("/assignSuperAdmin/{id}")
    public ResponseEntity<Society> assignSuperAdmin(@PathVariable("id") Long id
            ,@RequestBody User superAdmin){
        return ResponseEntity.ok(societyService.assignSuperAdmin(id, superAdmin));
    }
    @PutMapping("/{societyId}/{streetId}")
    public ResponseEntity<Society> removeStreet(@PathVariable("societyId") Long societyId, @PathVariable("streetId") Long streetId){
        return ResponseEntity.ok(societyService.removeStreet(societyId, streetId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        return ResponseEntity.ok(societyService.delete(id));
    }
}
