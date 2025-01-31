package gustavo.ventieri.capitalmind.infrastructure.service;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import gustavo.ventieri.capitalmind.application.dto.expense.ExpenseRequestDto;
import gustavo.ventieri.capitalmind.application.service.ExpenseServiceInterface;
import gustavo.ventieri.capitalmind.domain.expense.Expense;
import gustavo.ventieri.capitalmind.domain.user.User;
import gustavo.ventieri.capitalmind.infrastructure.exception.NotFoundException;
import gustavo.ventieri.capitalmind.infrastructure.persistence.ExpenseRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseService implements ExpenseServiceInterface{

    private final ExpenseRepository expenseRepository;
    private final UserService userService;


    @Override
    public void create(ExpenseRequestDto expenseRequestDto) {

        User user =  this.userService.validateAndGetUser(expenseRequestDto.userId());
       
        this.expenseRepository.save(
        new Expense(
            null,
            expenseRequestDto.name(),
            expenseRequestDto.description(),
            expenseRequestDto.category(),
            expenseRequestDto.price(),
            user,
            Instant.now(),
            Instant.now()
        ));      
    }

    @Override
    public void update(Long expenseId, ExpenseRequestDto expenseRequestDto) {
        
        Expense expense = this.expenseRepository.findById(expenseId).orElseThrow(() -> new NotFoundException("Expense Not Found"));;

        expense.setName(expenseRequestDto.name());
        expense.setDescription(expenseRequestDto.description());
        expense.setCategory(expenseRequestDto.category());
        expense.setPrice(expenseRequestDto.price());
       
        this.expenseRepository.save(expense);

    }

    @Override
    public List<Expense> getAll(String userId) {

        User user =  this.userService.validateAndGetUser(userId);

        return this.expenseRepository.findAllByUserData(user);
    }

    @Override
    public Expense getById(Long expenseId) {

        return this.expenseRepository.findById(expenseId).orElseThrow(() -> new NotFoundException("Expense Not Found"));
        
    }

    @Override
    public void deleteById(Long expenseId) {

        if (!this.expenseRepository.existsById(expenseId)) throw new NotFoundException("Expense Not Found");

        this.expenseRepository.deleteById(expenseId);

    }
}
