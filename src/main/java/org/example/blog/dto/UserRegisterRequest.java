package org.example.blog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRegisterRequest(@NotBlank @Size(min = 6, max = 30) String id,
                                  @NotBlank @Size(min = 12, max = 50)
                                  @Pattern(regexp = "^(?=(.*[A-Za-z]){2,})(?=(.*\\d){2,})(?=(.*[!@#$%^&*]){2,}).*$") String password,
                                  @NotBlank @Email @Size(max = 100) String email,
                                  @NotBlank @Size(min = 3, max = 50) String nickname) {
}
