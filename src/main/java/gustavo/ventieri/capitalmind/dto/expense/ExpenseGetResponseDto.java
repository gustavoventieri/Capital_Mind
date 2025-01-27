package gustavo.ventieri.capitalmind.dto.expense;

public record ExpenseGetResponseDto(Long expenseId, String name, String description, String category, Double price) {
} 
