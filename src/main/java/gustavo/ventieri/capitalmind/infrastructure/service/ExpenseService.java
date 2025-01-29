package gustavo.ventieri.capitalmind.infrastructure.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import gustavo.ventieri.capitalmind.application.dto.expense.ExpenseRequestDto;
import gustavo.ventieri.capitalmind.application.service.ExpenseServiceInterface;
import gustavo.ventieri.capitalmind.domain.expense.Expense;
import gustavo.ventieri.capitalmind.domain.user.User;
import gustavo.ventieri.capitalmind.infrastructure.persistence.ExpenseRepository;
import gustavo.ventieri.capitalmind.infrastructure.persistence.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseService implements ExpenseServiceInterface{

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    @Override
    public Boolean create(ExpenseRequestDto expenseRequestDto) {
        String userId = expenseRequestDto.userId();

        if (userId == null || userId.isEmpty()) {
            return false;
        }

        // Tenta buscar o usuário
        Optional<User> userOptional = this.userRepository.findById(UUID.fromString(userId));
        
        return userOptional.map(user -> {
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

            return true;
        }).orElseGet(() -> {
            return false;
        });
    }

    @Override
    public Boolean update(Long expenseId, ExpenseRequestDto expenseRequestDto) {
        String userId = expenseRequestDto.userId();
        if (userId == null || userId.isEmpty()) {
            return false;
        }

        Optional<User> userOptional = this.userRepository.findById(UUID.fromString(userId));
        if (userOptional.isEmpty()) {
            return false;
        }
        User user = userOptional.get();

        Optional<Expense> expenseOptional = this.expenseRepository.findById(expenseId);
        if (expenseOptional.isEmpty()) {
            return false;
        }
        Expense expense = expenseOptional.get();

        expense.setName(expenseRequestDto.name());
        expense.setDescription(expenseRequestDto.description());
        expense.setCategory(expenseRequestDto.category());
        expense.setPrice(expenseRequestDto.price());
        expense.setUserData(user);

        this.expenseRepository.save(expense);

        return true;
    }

    @Override
    public Optional<List<Expense>> getAll(String userId) {
        if (userId == null || userId.isEmpty()) {
            return Optional.empty();
        }

        Optional<User> user = this.userRepository.findById(UUID.fromString(userId));
        
        if (user.isEmpty()) {
            return Optional.empty();
        }

        List<Expense> expenses = this.expenseRepository.findAllByUserData(user.get());
        return Optional.of(expenses);
    }

    @Override
    public Optional<Expense> getById(Long expenseId) {
        if (expenseId == null) {
            return Optional.empty();
        }

        Optional<Expense> expense = this.expenseRepository.findById(expenseId);
        
        return expense;
    }

    @Override
    public Boolean deleteById(Long expenseId) {
        this.expenseRepository.deleteById(expenseId);

        Optional<Expense> userDeleted = this.expenseRepository.findById(expenseId);

        if (userDeleted.isEmpty()) {
            return false;
        }
        return true;
    }
}
