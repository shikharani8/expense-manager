package com.sfd.expense_management.role;

import com.sfd.expense_management.role.dtos.RoleRequestPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<Role> create(@RequestBody RoleRequestPayload  roleRequestPayload){
        return  ResponseEntity.ok(roleService.create(roleRequestPayload));
    }

    @GetMapping
    public ResponseEntity<List<Role>> getRoles(){
        return ResponseEntity.ok(roleService.getRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable("id") Long id){
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @PutMapping("/{id}")
    public  ResponseEntity<Role> update(@PathVariable("id") Long id, @RequestBody RoleRequestPayload  roleRequestPayload){
        return ResponseEntity.ok(roleService.update(id, roleRequestPayload));
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam Long id){
        return ResponseEntity.ok(roleService.delete(id));
    }

}
