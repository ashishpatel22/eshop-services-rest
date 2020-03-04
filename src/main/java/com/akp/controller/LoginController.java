package com.akp.controller;

import com.akp.model.User;
import com.akp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

/**
 * @author Aashish Patel
 */
@RestController
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    @GetMapping("/api/rest/login")
    public @ResponseBody
    ResponseEntity<User> login(Principal principal) throws Exception {

        logger.info("Inside login method", LoginController.class);
        logger.info(String.format("Fetching user details using username= %s from the session", principal.getName()), LoginController.class);
        Optional<User> user = userService.findByUsername(principal.getName());
        logger.debug(String.format("The user details are %s =", user.toString()), LoginController.class);
        return new ResponseEntity<User>(user.get(), HttpStatus.OK);
    }
}
