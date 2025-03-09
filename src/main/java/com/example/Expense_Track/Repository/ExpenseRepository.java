package com.example.Expense_Track.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.Expense_Track.Model.Expense;


public interface ExpenseRepository extends MongoRepository<Expense, String>{
    List<Expense> findByCategory(String category);
}
