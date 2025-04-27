package org.capitalmind.driver.repository;

import java.util.List;
import java.util.Optional;

import org.capitalmind.driver.repository.client.ExpenseRepositoryOrm;
import org.capitalmind.entity.Expense;
import org.capitalmind.entity.User;
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
            return this.expenseRepositoryOrm.save(expense);
        } catch (Exception exc) {
            throw new InternalServerError(exc);
        }
    }

    @Override
    public Expense update(Expense expense) {
          try {
            return this.expenseRepositoryOrm.save(expense);
        } catch (Exception exc) {
            throw new InternalServerError(exc);
        }
    }

    @Override
    public Optional<Expense> findById(Long expenseId) {
    try {
            return this.expenseRepositoryOrm.findById(expenseId);
       } catch (NotFound exc) {
            throw new NotFound(exc);
       } catch (Exception exc){
            throw new InternalServerError(exc);
       }
    }

    @Override
    public void delete(Long expenseId) {
       try {
            expenseRepositoryOrm.deleteById(expenseId);
       } catch (NotFound exc) {
           throw new NotFound(exc);
       } catch (Exception exc){
            throw new InternalServerError(exc);
       }
    }

    @Override
    public List<Expense> findAllByUserData(User user) {
        try {
            return this.expenseRepositoryOrm.findAllByUserData(user); 
       } catch (NotFound exc) {
            throw new NotFound(exc);
       } catch (Exception exc){
            throw new InternalServerError(exc);
       }
    }
    
}
