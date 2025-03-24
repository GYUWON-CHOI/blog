package org.example.blog.repository;

import org.example.blog.entity.User;

import java.util.Optional;

public interface UserRepository {
    void create(User user);
    Optional<User> findById(String id);
    void delete(String id);
}
