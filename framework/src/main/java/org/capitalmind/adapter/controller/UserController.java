package org.capitalmind.adapter.controller;

import org.capitalmind.adapter.dto.request.UserRequestImpl;
import org.capitalmind.adapter.mapper.UserMapper;
import org.capitalmind.dto.request.UserRequest;
import org.capitalmind.dto.response.UserResponse;
import org.capitalmind.useCase.service.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userServiceImpl;
    private final UserMapper userMapper;

    // Recupera um usuário pelo ID
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("userId") String userId) {

        return ResponseEntity.status(HttpStatus.OK).body(this.userServiceImpl.getById(userId));
    }

    // Exclui um usuário pelo ID
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable("userId") String userId) {
       
        this.userServiceImpl.deleteById(userId);
        
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User Deleted");
    }

    // Atualiza os dados de um usuário pelo ID
    @PutMapping("/update/{userId}")
    public ResponseEntity<String> updateUserById(@PathVariable("userId") String userId, @RequestBody @Valid UserRequestImpl userRequestImpl) {
        UserRequest userData = this.userMapper.toDomainUserRequest(userRequestImpl);
        
        this.userServiceImpl.update(userId, userData);

        return ResponseEntity.status(HttpStatus.OK).body("User Updated");
    }
}