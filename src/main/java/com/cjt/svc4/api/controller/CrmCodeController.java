package com.cjt.svc4.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.cjt.svc4.api.controller.CrmCodeController;
import com.cjt.svc4.domain.CrmCode;
import com.cjt.svc4.service.eduapp.EduAppCodeService;
import com.cjt.svc4.service.isherpa.CrmCodeService;



@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api/crm")
public class CrmCodeController {
    
    
    private final static Logger LOGGER = LoggerFactory.getLogger(CrmCodeController.class);
    @Autowired
    private final CrmCodeService crmCodeService;
    @Autowired
    private final EduAppCodeService eduAppCodeService;
    
    @GetMapping("test")
    public String test() {
        return "test";
    }

    // crmCodeService 호출
    @GetMapping(value="test1")
    public String test1() throws Exception {
        return crmCodeService.getTest();
    }

    @PostMapping(value = "codes")
    public List<CrmCode> getCodeList(HttpServletRequest request, HttpServletResponse response, CrmCode crmCodeRequest) throws Exception {
        return crmCodeService.getCrmCodeList(crmCodeRequest);
    }

    @GetMapping(value = "codes")
    public List<CrmCode> getCodeList(CrmCode crmCodeRequest) throws Exception {
        return crmCodeService.getCrmCodeList(crmCodeRequest);
    }
    
    @GetMapping(value="code/{id}")
    public CrmCode getCrmCodeById(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws Exception {
        return crmCodeService.getCrmCodeById(id);
    }

    // eduAppCodeService
    @GetMapping(value="test2")
    public String test2() throws Exception {
        return eduAppCodeService.getTest();
    }

    
    @GetMapping(value = "test3")
    public List<CrmCode> getTest3() throws Exception {
        return eduAppCodeService.getTest3();
    }

    @PostMapping(value = "codes2")
    public List<CrmCode> getCodeList2(HttpServletRequest request, HttpServletResponse response, CrmCode crmCodeRequest) throws Exception {
        return eduAppCodeService.getCrmCodeList(crmCodeRequest);
    }

    @GetMapping(value = "codes2")
    public List<CrmCode> getCodeList2(CrmCode crmCodeRequest) throws Exception {
        return eduAppCodeService.getCrmCodeList(crmCodeRequest);
    }

    
    
    @GetMapping(value="code2/{id}")
    public CrmCode getCrmCodeById2(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws Exception {
        return eduAppCodeService.getCrmCodeById(id);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}
