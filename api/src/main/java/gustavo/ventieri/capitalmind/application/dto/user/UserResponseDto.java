package gustavo.ventieri.capitalmind.application.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UserResponseDto( 

    @NotBlank(message = "Name cannot be empty.")
    @Size(min = 2, message = "Name cannot be less than 2 characters.")
    @Size(max = 100, message = "Name cannot be more than 100 characters.")
    String name,

    @NotBlank(message = "Email cannot be empty.")
    @Email
    String email, 

    @NotBlank(message = "Password cannot be empty.")
    @Size(min = 8, message = "Password cannot be less than 2 characters.")
    @Size(max = 32, message = "Password cannot exceed 50 characters.")
    String password,


    @NotNull(message = "Salary cannot be empty.")
    @Positive(message = "Salary must be a positive value.")
    Double salary
) {
    
}
