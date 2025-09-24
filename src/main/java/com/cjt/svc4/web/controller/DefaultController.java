package com.cjt.svc4.web.controller;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class DefaultController {
    private final static Logger LOGGER = LoggerFactory.getLogger(VueAppController.class);

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("serverTime", new Date());
        // String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        // Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        // GrantedAuthority auth = iter.next();
        // String role = auth.getAuthority();
        
        return "index";
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}
