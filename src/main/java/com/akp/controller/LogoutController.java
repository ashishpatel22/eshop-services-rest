package com.akp.controller;

import com.akp.model.User;
import com.akp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Optional;

/**
 * @author Aashish Patel
 */
@RestController
public class LogoutController {

    @Autowired
    UserService userService;

    @GetMapping("/api/rest/logout")
    public @ResponseBody
    ResponseEntity<String> getUser(Principal principal, HttpSession session) throws Exception {

        session.invalidate();
        return new ResponseEntity<>("{logout:success}", HttpStatus.OK);
    }
}
