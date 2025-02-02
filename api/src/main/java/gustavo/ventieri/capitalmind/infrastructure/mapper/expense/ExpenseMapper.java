package gustavo.ventieri.capitalmind.infrastructure.mapper.expense;

import org.springframework.stereotype.Component;

import gustavo.ventieri.capitalmind.application.dto.expense.ExpenseResponseDto;
import gustavo.ventieri.capitalmind.domain.expense.Expense;



@Component
public class ExpenseMapper {
    
    public ExpenseResponseDto toDto(Expense expense){
        return new ExpenseResponseDto(
            expense.getExpenseId(),
            expense.getName(),
            expense.getDescription(),
            expense.getCategory(),
            expense.getPrice());
    }

}
