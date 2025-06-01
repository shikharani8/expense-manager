package com.sfd.expense_management.expenseCategory;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ExpenseCategoryServiceImpl implements ExpenseCategoryService {
    private final ExpenseCategoryRepository expenseCategoryRepository;

    @Override
    public ExpenseCategory create(ExpenseCategory expenseCategory) {
        ExpenseCategory existingCategory = expenseCategoryRepository.findByName(expenseCategory.getName());
        if(Objects.isNull(existingCategory)){
            return expenseCategoryRepository.save(expenseCategory);
        }
        return existingCategory;
    }

    @Override
    public List<ExpenseCategory> getAll() {
        return expenseCategoryRepository.findAll();
    }

    @Override
    public ExpenseCategory getById(Long id) {
        return expenseCategoryRepository.findById(id)
                .orElseThrow(()-> new ExpenseCategoryException("Expense Category doesn't exist", HttpStatus.NOT_FOUND.value()));
    }

    @Override
    public ExpenseCategory update(Long id, ExpenseCategory expenseCategory) {
        ExpenseCategory existingExpenseCategory = getById(id);
        if(StringUtils.isEmpty(expenseCategory.getName())) {
            throw new ExpenseCategoryException("Expense Category cannot be null!", HttpStatus.BAD_REQUEST.value());
        }
        existingExpenseCategory.setName(expenseCategory.getName());
        existingExpenseCategory.setActive(expenseCategory.isActive());
        return expenseCategoryRepository.save(existingExpenseCategory);
    }
}
