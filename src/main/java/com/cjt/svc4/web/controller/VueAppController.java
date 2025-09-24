package com.cjt.svc4.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.servlet.http.HttpSession;

@Controller
public class VueAppController {
    
     private final static Logger LOGGER = LoggerFactory.getLogger(VueAppController.class);

    @GetMapping(value = {"/vueapp", "/vueapp/*"})
    public String vueApp(Model model, HttpSession session) {
        return "vue/index";
    }

    @GetMapping({"/vueapp2", "/vueapp2/*"})
    public String vueApp2(Model model, HttpSession session) {
        return "vue/dashboard";
    }

    @GetMapping(value = {"/vueapp2/board/*", "/vueapp2/boardview/*", "/vueapp2/utils/*", "/vueapp2/icons/*", "/vueapp2/auth/*"})
    public String vueAppSub(Model model, HttpSession session) {
        return "vue/dashboard";
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}
