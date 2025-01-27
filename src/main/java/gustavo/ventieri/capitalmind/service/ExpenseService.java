package gustavo.ventieri.capitalmind.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import gustavo.ventieri.capitalmind.dto.expense.ExpenseRequestDto;
import gustavo.ventieri.capitalmind.entity.Expense;
import gustavo.ventieri.capitalmind.repository.ExpenseRepository;
import gustavo.ventieri.capitalmind.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

  

    public Boolean create(ExpenseRequestDto expenseRequestDto) {
      
        var userId = expenseRequestDto.userId();
    
        if (userId == null || userId.isEmpty()) {
            return false; 
        }
    
        // Tenta buscar o usuário
        var userOptional = this.userRepository.findById(UUID.fromString(userId));
        
        return userOptional.map(user -> {
            
            
            var newExpense = new Expense(
                null,
                expenseRequestDto.name(),
                expenseRequestDto.description(),
                expenseRequestDto.category(),
                expenseRequestDto.price(),
                user,
                Instant.now(),
                Instant.now()
            );


    
            @SuppressWarnings("unused")
            var expenseSaved = this.expenseRepository.save(newExpense);
            System.out.println(expenseSaved.getExpenseId());
            return true;
        }).orElseGet(() -> {

           
            return false;
        });
    }
    

    public Optional<List<Expense>> getAll(String userId) {
        
        if (userId == null || userId.isEmpty()) {
            return Optional.empty();
        }
    
       
        var user = this.userRepository.findById(UUID.fromString(userId));
        
        
        if (user.isEmpty()) {
            return Optional.empty();
        }
    
        
        List<Expense> expenses = this.expenseRepository.findAllByUserData(user.get());
        return Optional.of(expenses);
    }
    
    public Optional<Expense> getById(Long expenseId) {
        if (expenseId == null) {
            return Optional.empty();
        }
        
        

        var expense = this.expenseRepository.findByExpenseId(expenseId);
        
        return expense;
    }
    

}
