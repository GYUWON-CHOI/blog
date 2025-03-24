package org.example.blog.service;

import lombok.RequiredArgsConstructor;
import org.example.blog.util.JwtUtil;
import org.example.blog.dto.LoginRequest;
import org.example.blog.dto.UserRegisterRequest;
import org.example.blog.entity.User;
import org.example.blog.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public void register(UserRegisterRequest request) {
        String hashedPassword = BCrypt.hashpw(request.password(), BCrypt.gensalt());

        User user = new User(request.id(), hashedPassword, request.email(), request.nickname());
        userRepository.create(user);
    }

    public String login(LoginRequest request) {
        User user = userRepository.findById(request.id()).orElseThrow(() -> new RuntimeException("User not found"));

        if (!BCrypt.checkpw(request.password(), user.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }

        return jwtUtil.generateToken(user.getId());
    }

    public boolean existsById(String userId) {
        return userRepository.findById(userId).isPresent();
    }

    private void validate(UserRegisterRequest request) {
        if (userRepository.findById(request.id()).isPresent()) {
            throw new IllegalArgumentException("ID is already in use");
        }
    }
}
