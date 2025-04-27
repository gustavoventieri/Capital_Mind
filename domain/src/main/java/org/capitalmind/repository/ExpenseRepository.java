package org.capitalmind.repository;

import java.util.Optional;

import org.capitalmind.entity.Expense;


public interface ExpenseRepository {
    Expense save(Expense expense);

    Expense update(Expense expense);

    Optional<Expense> findById(Long expenseId);

    void delete(Long expenseId);
}
