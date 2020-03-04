package com.akp.controller;

import com.akp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.security.Principal;

/**
 * @author Aashish Patel
 */
@RestController
public class LogoutController {

    private static final Logger logger = LoggerFactory.getLogger(LogoutController.class);

    @Autowired
    UserService userService;

    @GetMapping("/api/rest/logout")
    public @ResponseBody
    ResponseEntity<String> getUser(Principal principal, HttpSession session) throws Exception {

        logger.info(String.format("User session is going to get invalidated for the username=%s", principal.getName()));
        session.invalidate();
        return new ResponseEntity<>("{logout:success}", HttpStatus.OK);
    }
}
