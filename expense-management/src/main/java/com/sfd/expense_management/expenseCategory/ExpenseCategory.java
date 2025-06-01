package com.sfd.expense_management.expenseCategory;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="tb_expense_categories")
@Data
public class ExpenseCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private boolean active = true;

    public ExpenseCategory(){
    }

    public ExpenseCategory(String name, boolean active){
        this.name = name;
        this.active = active;
    }
}
