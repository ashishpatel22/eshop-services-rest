package com.akp.service;

import com.akp.model.User;

import java.util.Optional;

/**
 * @author Aashish Patel
 * Contract for user services
 */
public interface UserService {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    User saveUser(User user);
}
