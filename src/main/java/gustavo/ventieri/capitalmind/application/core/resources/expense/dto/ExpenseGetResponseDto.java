package gustavo.ventieri.capitalmind.application.core.resources.expense.dto;

public record ExpenseGetResponseDto(Long expenseId, String name, String description, String category, Double price) {
} 
