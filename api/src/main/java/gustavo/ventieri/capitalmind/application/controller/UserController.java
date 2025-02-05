package gustavo.ventieri.capitalmind.application.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gustavo.ventieri.capitalmind.application.dto.user.UserResponseDto;
import gustavo.ventieri.capitalmind.application.dto.user.UserRequestDto;
import gustavo.ventieri.capitalmind.infrastructure.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("userId") String userId) {

        return ResponseEntity.ok(this.userService.getById(userId));
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable("userId") String userId) {
       
        this.userService.deleteById(userId);
        
        return ResponseEntity.ok("User Deleted");
      
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<String> updateUserById(@PathVariable("userId") String userId, @RequestBody @Valid UserRequestDto updateUserRequestDto) {
        
        this.userService.update(userId, updateUserRequestDto);

        return ResponseEntity.ok("User Updated");

    }
}