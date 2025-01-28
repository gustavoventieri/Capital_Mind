package gustavo.ventieri.capitalmind.application.core.resources.expense;

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
import gustavo.ventieri.capitalmind.application.service.expense.ExpenseServiceImpl;
import gustavo.ventieri.capitalmind.domain.expense.Expense;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/expense")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseServiceImpl expenseService;

    @PostMapping("/create")
    public ResponseEntity<?> createExpense(@RequestBody @Valid ExpenseRequestDto expenseRequestDto) {
        
        Boolean userSaved = this.expenseService.create(expenseRequestDto);
        if (userSaved) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Expense Created");
        }
    
        return ResponseEntity.badRequest().body("Expense Not Created");
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

        return ResponseEntity.badRequest().body("Invalid User Id or Expensive Id");
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<?> getAllExpense(@PathVariable("userId") String userId) {

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
        return ResponseEntity.badRequest().body("Invalid User Id");
        
    }

    @PutMapping("/update/{expenseId}")
    public ResponseEntity<?> updateExpenseById(@PathVariable("expenseId") Long expenseId, @RequestBody @Valid ExpenseRequestDto expenseRequestDto){
        Boolean expenseDeleted = this.expenseService.update(expenseId, expenseRequestDto);
        
        if(expenseDeleted){
            return ResponseEntity.ok().body("Expense Updated");
        }

        return ResponseEntity.badRequest().body("Expense Not Updated");
    }


    @DeleteMapping("/delete/{expenseId}")
    public ResponseEntity<?> deleteExpenseById(@PathVariable("expenseId") Long expenseId){
        Boolean expenseDeleted = this.expenseService.deleteById(expenseId);
        
        if(expenseDeleted){
            return ResponseEntity.ok().body("Expense Deleted");
        }

        return ResponseEntity.badRequest().body("Expense Not Deleted");
    }
    


}
