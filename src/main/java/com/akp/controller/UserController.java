package com.akp.controller;

import com.akp.model.User;
import com.akp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

/**
 * @author Aashish Patel
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/api/rest/user/{id}}")
    public @ResponseBody
    ResponseEntity<User> getUser(Principal principal) throws Exception {

        if (!principal.getName().isEmpty()) {
            Optional<User> user = userService.findByUsername(principal.getName());
            return new ResponseEntity<User>(user.get(), HttpStatus.OK);
        } else
            throw new AuthenticationCredentialsNotFoundException("No user present, please provide authentication to authenticate!");
    }

    @PostMapping("/api/rest/user/signup")
    User newEmployee(@RequestBody User user) {
        return userService.saveUser(user);
    }


}
