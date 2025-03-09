package com.example.Expense_Track.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Expense_Track.Model.Expense;
import com.example.Expense_Track.Repository.ExpenseRepository;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Optional<Expense> getExpenseById(String id) {
        return expenseRepository.findById(id);
    }

    public Expense addExpense(Expense expense) {
        expense.setDate(LocalDate.now());
        return expenseRepository.save(expense);
    }

    public Expense updateExpense(String id, Expense newExpense) {
        return expenseRepository.findById(id)
            .map(expense -> {
                expense.setDescription(newExpense.getDescription());
                expense.setAmount(newExpense.getAmount());
                expense.setCategory(id);
                return expenseRepository.save(expense);
            }).orElseThrow(() -> new RuntimeException("Expense not found"));
    }

    
}
