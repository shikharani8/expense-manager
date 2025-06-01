package com.sfd.expense_management.expenseCategory;

import java.util.List;

public interface ExpenseCategoryService {
    ExpenseCategory create(ExpenseCategory expenseCategory);
    List<ExpenseCategory> getAll();
    ExpenseCategory getById(Long id);
    ExpenseCategory update(Long id, ExpenseCategory expenseCategory);
}