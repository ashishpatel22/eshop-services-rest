package com.akp.service.impl;

import java.util.Collections;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akp.model.User;
import com.akp.repository.CustomerRepository;
import com.akp.repository.RoleRepository;
import com.akp.repository.UserRepository;
import com.akp.service.UserService;

/**
 * @author Aashish Patel
 * User serives implementation
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String USER_ROLE = "ROLE_USER";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, CustomerRepository customerRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.customerRepository = customerRepository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        logger.info("Fetching details for the user id=%s", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        logger.info("Fetching details for the user by email id=%s", email);
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public User saveUser(User user) {
        logger.debug(String.format("Saving user...., details are=%s", user));
        logger.info("Encode plaintext password");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(1);
        logger.info("Setting Role to ROLE_USER, which is to be set by default for every user");
        user.setRoles(Collections.singletonList(roleRepository.findByRole(USER_ROLE)));
        customerRepository.save(user.getCustomer());
        return userRepository.saveAndFlush(user);
    }
}
