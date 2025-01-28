package gustavo.ventieri.capitalmind.application.core.resources.expense.dto;

public record ExpenseResponseDto(Long expenseId, String name, String description, String category, Double price) {
} 
