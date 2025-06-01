package com.sfd.expense_management.expenseCategory;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenseCategory")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseCategoryService expenseCategoryService;

    @PostMapping
    public ResponseEntity<ExpenseCategory> create(@RequestBody ExpenseCategory expenseCategory){
        return ResponseEntity.ok(expenseCategoryService.create(expenseCategory));
    }

    @GetMapping
    public ResponseEntity<List<ExpenseCategory>> getAll(){
        return ResponseEntity.ok(expenseCategoryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseCategory> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(expenseCategoryService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseCategory> update(@PathVariable("id") Long id,
                                                  @RequestBody ExpenseCategory expenseCategory){
        return ResponseEntity.ok(expenseCategoryService.update(id, expenseCategory));
    }
}
