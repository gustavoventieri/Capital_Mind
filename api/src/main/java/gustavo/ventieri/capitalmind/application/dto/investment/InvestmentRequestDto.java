package gustavo.ventieri.capitalmind.application.dto.investment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

// Valida a requisição feita
public record InvestmentRequestDto( 
    @NotBlank(message = "Name cannot be empty.")
    @Size(min = 2, message = "Name cannot be less than 2 characters.")
    @Size(max = 100, message = "Name cannot be more than 100 characters.")
    String name,

    @NotBlank(message = "Description cannot be empty.")
    @Size(min = 1, message = "Description cannot be less than 2 characters.")
    @Size(max = 255, message = "Description cannot be more than 255 characters.")
    String description,

    @NotNull(message = "Price cannot be null.")
    @Positive(message = "Price must be a positive value.")
    Double price,

    @NotNull(message = "User ID cannot be null.")
    String userId
) 
{
    
}
