package com.cjt.svc4.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.cjt.svc4.domain.CrmCode;
import com.cjt.svc4.service.eduapp.EduAppCodeService;
import com.cjt.svc4.service.isherpa.CrmCodeService;

import java.util.Date;

@RequiredArgsConstructor
@Controller
@RequestMapping(value="/crm/*")
public class CrmController {
    
    
    private final static Logger LOGGER = LoggerFactory.getLogger(CrmController.class);
    @Autowired
    private final CrmCodeService crmCodeService;
    @Autowired
    private final EduAppCodeService eduAppCodeService;
    
    @GetMapping("test")
    public String test(Model model, CrmCode crmCodeRequest) throws Exception {
        model.addAttribute("data", new Date());
        model.addAttribute("codes", crmCodeService.getCrmCodeList(crmCodeRequest));
        return "crm/test";
    }

    // crmCodeService 호출
    @GetMapping(value="test1")
    public String test1(Model model) throws Exception {
        model.addAttribute("data", crmCodeService.getTest());
        return "crm/test";
    }

    @RequestMapping(value = "codes", method={RequestMethod.GET, RequestMethod.POST})
    public String getCodeList(Model model, CrmCode crmCodeRequest) throws Exception {
        model.addAttribute("codes", crmCodeService.getCrmCodeList(crmCodeRequest));
        return "crm/codes";
    }

    
    @RequestMapping(value = "codes2", method={RequestMethod.GET, RequestMethod.POST})
    public String getCodeList2(Model model, CrmCode crmCodeRequest) throws Exception {
        model.addAttribute("codes", eduAppCodeService.getCrmCodeList(crmCodeRequest));
        return "crm/codes";
    }

    @RequestMapping(value="code/{id}", method={RequestMethod.GET, RequestMethod.POST})
    public String getCrmCodeById(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable String id) throws Exception {
        model.addAttribute("codes", crmCodeService.getCrmCodeById(id));
        return "crm/codes";
    }


    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}
