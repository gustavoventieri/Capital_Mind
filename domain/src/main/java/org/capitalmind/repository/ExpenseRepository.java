package org.capitalmind.repository;

import java.util.List;
import java.util.Optional;

import org.capitalmind.entity.Expense;
import org.capitalmind.entity.User;


public interface ExpenseRepository {
    Expense save(Expense expense);

    Expense update(Expense expense);

    Optional<Expense> findById(Long expenseId);

    List<Expense> findAllByUserData(User user);

    void delete(String expenseId);
}
