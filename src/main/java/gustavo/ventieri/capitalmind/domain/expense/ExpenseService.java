package gustavo.ventieri.capitalmind.domain.expense;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import gustavo.ventieri.capitalmind.application.core.resources.expense.dto.ExpenseRequestDto;
import gustavo.ventieri.capitalmind.domain.user.UserRepository;
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
            return true;
        }).orElseGet(() -> {

           
            return false;
        });
    }

    public Boolean update(Long expenseId, ExpenseRequestDto expenseRequestDto) {
        var userId = expenseRequestDto.userId();
        if (userId == null || userId.isEmpty()) {
            return false;
        }
    
        var userOptional = this.userRepository.findById(UUID.fromString(userId));
        if (userOptional.isEmpty()) {
            return false;
        }
        var user = userOptional.get();
    
        var expenseOptional = this.expenseRepository.findById(expenseId);
        if (expenseOptional.isEmpty()) {
            return false;
        }
        var expense = expenseOptional.get();
    
        // Atualizando os campos da instância existente
        expense.setName(expenseRequestDto.name());
        expense.setDescription(expenseRequestDto.description());
        expense.setCategory(expenseRequestDto.category());
        expense.setPrice(expenseRequestDto.price());
        expense.setUserData(user);
    
        // O @UpdateTimestamp se encarregará de atualizar o campo updateAt automaticamente
        this.expenseRepository.save(expense);
    
        return true;
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
    
    public Boolean deleteById(Long expenseId) {
        
        this.expenseRepository.deleteById(expenseId);

        return true;
    }

}
