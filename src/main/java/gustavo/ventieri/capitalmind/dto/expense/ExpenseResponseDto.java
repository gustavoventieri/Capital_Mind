package gustavo.ventieri.capitalmind.dto.expense;

public record ExpenseResponseDto(Long expenseId, String name, String description, String category, Double price) {
} 
