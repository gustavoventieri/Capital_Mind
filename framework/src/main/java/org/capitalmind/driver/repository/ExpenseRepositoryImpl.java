package org.capitalmind.driver.repository;

import java.util.Optional;

import org.capitalmind.driver.repository.client.ExpenseRepositoryOrm;
import org.capitalmind.entity.Expense;
import org.capitalmind.exception.InternalServerError;
import org.capitalmind.exception.NotFound;
import org.capitalmind.repository.ExpenseRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Repository
public class ExpenseRepositoryImpl implements ExpenseRepository {

    private final ExpenseRepositoryOrm expenseRepositoryOrm;

    @Override
    public Expense save(Expense expense) {
        try {
            Expense newExpense = expenseRepositoryOrm.save(expense);
            return newExpense;
        } catch (Exception exc) {
           throw new InternalServerError(exc);
        }
    }

    @Override
    public Expense update(Expense expense) {
          try {
            Expense expenseUpdated = expenseRepositoryOrm.save(expense);
            return expenseUpdated;
        } catch (Exception exc) {
           throw new InternalServerError(exc);
        }
    }

    @Override
    public Optional<Expense> findById(Long expenseId) {
    try {
        Optional<Expense> expense = expenseRepositoryOrm.findById(expenseId);
        return expense;
       } catch (NotFound exc) {
            throw exc;
       } catch (Exception exc){
            throw new InternalServerError(exc);
       }
    }

    @Override
    public void delete(Long expenseId) {
       try {
            expenseRepositoryOrm.deleteById(expenseId);
       } catch (NotFound exc) {
            throw exc;
       } catch (Exception exc){
            throw new InternalServerError(exc);
       }
    }
    
}
