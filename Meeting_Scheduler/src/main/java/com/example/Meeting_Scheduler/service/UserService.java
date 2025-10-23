package com.example.Meeting_Scheduler.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.Meeting_Scheduler.entity.User;
import com.example.Meeting_Scheduler.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    // Constructor
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Adding new User
    public User createUser(User user) {
        return userRepository.save(user);
        // This method will call userRepository's member function
        // which extends Jpa that has builtin methods
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Finding an User by userId
    public Optional<User> getUserById(Long id) {
        // Option User because if Id is NULL , an exception will be thrown
        return userRepository.findById(id);
    }

    // Finding an User by email
    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    // Delete an User by Id
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
