package gustavo.ventieri.capitalmind.infrastructure.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import gustavo.ventieri.capitalmind.application.dto.expense.ExpenseRequestDto;
import gustavo.ventieri.capitalmind.application.service.ExpenseServiceInterface;
import gustavo.ventieri.capitalmind.domain.expense.Expense;
import gustavo.ventieri.capitalmind.domain.user.User;
import gustavo.ventieri.capitalmind.infrastructure.exception.InvalidDataException;
import gustavo.ventieri.capitalmind.infrastructure.exception.NotFoundException;
import gustavo.ventieri.capitalmind.infrastructure.persistence.ExpenseRepository;
import gustavo.ventieri.capitalmind.infrastructure.persistence.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseService implements ExpenseServiceInterface{

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    @Override
    public void create(ExpenseRequestDto expenseRequestDto) {

        String userId = expenseRequestDto.userId();

        if (userId == null || userId.isEmpty()) throw new InvalidDataException("Data is Blank or Null");
        
        User user = this.userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new NotFoundException("User Not Found"));
       
        Expense newExpense = new Expense(
            null,
            expenseRequestDto.name(),
            expenseRequestDto.description(),
            expenseRequestDto.category(),
            expenseRequestDto.price(),
            user,
            Instant.now(),
            Instant.now()
        ); 

        this.expenseRepository.save(newExpense);      
    }

    @Override
    public void update(Long expenseId, ExpenseRequestDto expenseRequestDto) {

        String userId = expenseRequestDto.userId();

        if (userId == null || userId.isEmpty()) throw new InvalidDataException("Data is Blank or Null");

        this.userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new NotFoundException("User Not Found"));
        
        Expense expense = this.expenseRepository.findById(expenseId).orElseThrow(() -> new NotFoundException("Expense Not Found"));;

        expense.setName(expenseRequestDto.name());
        expense.setDescription(expenseRequestDto.description());
        expense.setCategory(expenseRequestDto.category());
        expense.setPrice(expenseRequestDto.price());
       

        this.expenseRepository.save(expense);

    }

    @Override
    public List<Expense> getAll(String userId) {

        if (userId == null || userId.isEmpty()) throw new InvalidDataException("Data is Blank or Null");
        
        User user = this.userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new NotFoundException("User Not Found"));;
        
        return this.expenseRepository.findAllByUserData(user);
    }

    @Override
    public Expense getById(Long expenseId) {

        if (expenseId == null) throw new InvalidDataException("Data is Blank or Null");

        return this.expenseRepository.findById(expenseId).orElseThrow(() -> new NotFoundException("Expense Not Found"));
        
    }

    @Override
    public void deleteById(Long expenseId) {

        if (!this.expenseRepository.existsById(expenseId)) throw new NotFoundException("Expense Not Found");

        this.expenseRepository.deleteById(expenseId);

    }
}
