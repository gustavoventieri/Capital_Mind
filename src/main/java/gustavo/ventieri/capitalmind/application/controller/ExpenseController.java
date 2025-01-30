package gustavo.ventieri.capitalmind.application.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gustavo.ventieri.capitalmind.application.dto.expense.ExpenseRequestDto;
import gustavo.ventieri.capitalmind.application.dto.expense.ExpenseResponseDto;
import gustavo.ventieri.capitalmind.domain.expense.Expense;
import gustavo.ventieri.capitalmind.infrastructure.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/expense")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;


    @PostMapping("/create")
    public ResponseEntity<?> createExpense(@RequestBody @Valid ExpenseRequestDto expenseRequestDto) {
        
        Boolean userSaved = this.expenseService.create(expenseRequestDto);
        if (userSaved) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Expense Created");
        }
    
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Expense Not Created");
    }

    @GetMapping("/{expenseId}")
    public ResponseEntity<?> getExpenseById(@PathVariable("expenseId") Long expenseId){
        Optional<Expense> expenseOptional = expenseService.getById(expenseId);
        
        if(expenseOptional.isPresent()){
            Expense expense = expenseOptional.get();

            return ResponseEntity.ok(new ExpenseResponseDto(
                expense.getExpenseId(),
                expense.getName(),
                expense.getDescription(),
                expense.getCategory(),
                expense.getPrice())
            );
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource ID not found.");
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<?> getAllExpenses(@PathVariable("userId") String userId) {

        Optional<List<Expense>> expensesOptional = expenseService.getAll(userId);

        if (expensesOptional.isPresent()) {

            List<ExpenseResponseDto> expenses = expensesOptional.get().stream()
                .map(expense -> new ExpenseResponseDto(
                    expense.getExpenseId(),
                    expense.getName(),
                    expense.getDescription(),
                    expense.getCategory(),
                    expense.getPrice()
                ))
                .collect(Collectors.toList());

            return ResponseEntity.ok(expenses);
        } 
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource ID not found.");
        
    }

    @PutMapping("/update/{expenseId}")
    public ResponseEntity<?> updateExpenseById(@PathVariable("expenseId") Long expenseId, @RequestBody @Valid ExpenseRequestDto expenseRequestDto){
        Boolean expenseUpdated = this.expenseService.update(expenseId, expenseRequestDto);
        
        if(expenseUpdated){
            return ResponseEntity.ok().body("Expense Updated");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource ID not found.");
    }


    @DeleteMapping("/delete/{expenseId}")
    public ResponseEntity<String> deleteExpenseById(@PathVariable("expenseId") Long expenseId) {
       

        Boolean expenseDeleted = expenseService.deleteById(expenseId);
        if (expenseDeleted) {
            return ResponseEntity.ok("Expense Deleted");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource ID not found.");
    }
    


}
