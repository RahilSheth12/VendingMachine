package edu.vhhs.demo.vendingmachine.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.vhhs.demo.vendingmachine.entity.UserProfile;

@RestController
@RequestMapping("/whoami")
public class WhoAmIController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhoAmIController.class);

    @GetMapping
    public UserProfile whoAmIGet(Authentication authentication) {
        LOGGER.debug("Authentication for GET: {}", authentication);
        return (UserProfile) authentication.getPrincipal();
    }

    @PostMapping
    public UserProfile whoAmIPost() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LOGGER.debug("Authentication from POST: {}", authentication);
        UserProfile userDetails = (UserProfile) authentication.getPrincipal();
        return userDetails;
    }

}
