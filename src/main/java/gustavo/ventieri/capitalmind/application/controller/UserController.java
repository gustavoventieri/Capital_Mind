package gustavo.ventieri.capitalmind.application.controller;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gustavo.ventieri.capitalmind.application.dto.user.GetUserResponseDto;
import gustavo.ventieri.capitalmind.application.dto.user.UpdateUserRequestDto;
import gustavo.ventieri.capitalmind.domain.user.User;
import gustavo.ventieri.capitalmind.infrastructure.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable("userId") String userId) {
       

        Optional<User> userOptional = this.userService.getById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            return ResponseEntity.ok(new GetUserResponseDto(
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getSalary())
            );
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource ID not found.");
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable("userId") String userId) {
       

        Boolean userDeleted = this.userService.deleteById(userId);
        if (userDeleted) {
            return ResponseEntity.ok("User Deleted");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource ID not found.");
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<String> updateUserById(@PathVariable("userId") String userId, @RequestBody @Valid UpdateUserRequestDto updateUserRequestDto) {
        
        Boolean userUpdated = this.userService.update(userId, updateUserRequestDto);

        if (userUpdated) {
            return ResponseEntity.ok("User Updated");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource ID not found.");
    }
}