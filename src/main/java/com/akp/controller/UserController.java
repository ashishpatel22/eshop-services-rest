package com.akp.controller;

import com.akp.model.User;
import com.akp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Optional;

/**
 * @author Aashish Patel
 */
@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @GetMapping("/api/rest/user}")
    public @ResponseBody
    ResponseEntity<User> getUser(Principal principal) throws Exception {

        logger.info("Inside getUser method", UserController.class);
        Optional<User> user = userService.findByUsername(principal.getName());
        logger.debug("The user id is % and custoemr id is=%s", user.get().getId(), user.get().getCustomer().getId());
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @PostMapping("/api/rest/user/signup")
    public @ResponseBody
    ResponseEntity<User> createUser(@RequestBody User user) {
        logger.info("Inside createUser method", UserController.class);
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.OK);
    }


}
