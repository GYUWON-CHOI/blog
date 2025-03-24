package org.example.blog.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.blog.entity.User;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class FileUserRepository implements UserRepository {
    private final String FILE_NAME = "user.ser";
    private Map<String, User> users = new HashMap<>();

    public FileUserRepository() {
        load();
    }

    @Override
    public void create(User user) {
        users.put(user.getId(), user);
        write();
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public void delete(String id) {
        users.remove(id);
        write();
    }

    private void load() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            users = (Map<String, User>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void write() {
        File file = new File(FILE_NAME);
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
