package gustavo.ventieri.capitalmind.application.controller;

import java.util.List;

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
import gustavo.ventieri.capitalmind.infrastructure.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/expense")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;


    @PostMapping("/create")
    public ResponseEntity<String> createExpense(@RequestBody @Valid ExpenseRequestDto expenseRequestDto) {
        
        this.expenseService.create(expenseRequestDto);
        
        return ResponseEntity.status(HttpStatus.CREATED).body("Expense Created");
    }

    @PutMapping("/update/{expenseId}")
    public ResponseEntity<String> updateExpenseById(@PathVariable("expenseId") Long expenseId, @RequestBody @Valid ExpenseRequestDto expenseRequestDto){
        
        this.expenseService.update(expenseId, expenseRequestDto);
    
        return ResponseEntity.ok().body("Expense Updated");
    }

    @DeleteMapping("/delete/{expenseId}")
    public ResponseEntity<String> deleteExpenseById(@PathVariable("expenseId") Long expenseId) {
       
        this.expenseService.deleteById(expenseId);
        
        return ResponseEntity.ok("Expense Deleted");
        
    }

    @GetMapping("/{expenseId}")
    public ResponseEntity<ExpenseResponseDto> getExpenseById(@PathVariable("expenseId") Long expenseId){

     return ResponseEntity.ok(expenseService.getById(expenseId));          
       
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<List<ExpenseResponseDto>> getAllExpenses(@PathVariable("userId") String userId) {

        return ResponseEntity.ok(expenseService.getAll(userId));
        
    }
}
