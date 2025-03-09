package com.example.Expense_Track.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
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
        if (expense.getDescription() == null || expense.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
    
        if (expense.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
    
        if (expense.getDate() == null) {
            expense.setDate(LocalDate.now());
        }
    
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

    public void deleteExpense(String id) {
        expenseRepository.deleteById(id);
    }

    public BigDecimal getTotalExpense() {
        return expenseRepository.findAll().stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getMonthlyExpense(int month) {
        return expenseRepository.findAll().stream()
                .filter(expense -> YearMonth.from(expense.getDate()).getMonthValue() == month)
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Expense> getExpensesByCategory(String category) {
        return expenseRepository.findByCategory(category);
    }
    
}
