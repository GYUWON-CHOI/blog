package org.example.blog.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.blog.dto.LoginRequest;
import org.example.blog.dto.LoginResponse;
import org.example.blog.dto.UserRegisterRequest;
import org.example.blog.dto.UserRegisterResponse;
import org.example.blog.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> register(@RequestBody @Valid UserRegisterRequest request) {
        try {
            userService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new UserRegisterResponse(true, "회원 가입이 완료되었습니다."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new UserRegisterResponse(false, e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        try {
            String token = userService.login(request);
            return ResponseEntity.ok(new LoginResponse(true, token));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(false, null));
        }
    }
}
