package gustavo.ventieri.capitalmind.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gustavo.ventieri.capitalmind.entity.Expense;
import gustavo.ventieri.capitalmind.entity.User;

import java.util.List;


@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

 
    List<Expense> findAllByUserData(User user);

   
}
    
