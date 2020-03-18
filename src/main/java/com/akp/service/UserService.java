package com.akp.service;

import java.util.Optional;

import com.akp.model.User;

/**
 * @author Aashish Patel
 * Contract for user services
 */
public interface UserService {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    User saveUser(User user);
}
