package gustavo.ventieri.capitalmind.infrastructure.persistence;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gustavo.ventieri.capitalmind.application.repository.ExpenseRepositoryInterface;
import gustavo.ventieri.capitalmind.domain.expense.Expense;


@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long>, ExpenseRepositoryInterface {

    
} 
