package com.akp.controller;

import com.akp.model.User;
import com.akp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

/**
 * @author Aashish Patel
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/api/rest/user/login")
    public @ResponseBody
    ResponseEntity<User> login(Principal principal) throws Exception {
        if (principal != null && !principal.getName().isEmpty()) {
            Optional<User> user = userService.findByUsername(principal.getName());
            if (user.isPresent()) {
                return new ResponseEntity<User>(user.get(), HttpStatus.OK);
            } else
                throw new Exception("No user present, try logout and login agani!");
        } else
            throw new Exception("Credentials not valid");
    }
}
