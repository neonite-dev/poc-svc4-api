package com.cjt.svc4.web.interceptor;

// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Slf4j
public class CrmInterceptor implements HandlerInterceptor {

    @SuppressWarnings("null")
    @Override
    public boolean preHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler
    ) throws Exception {
      long startTime = System.currentTimeMillis();
      log.debug("Request URL::" + request.getRequestURL().toString() + ":: "
          + "Start Time=" + System.currentTimeMillis());
      request.setAttribute("startTime", startTime);
      return true;
    }
  
    @SuppressWarnings("null")
    @Override
    public void postHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler,
        ModelAndView modelAndView
    ) throws Exception {
      log.debug("Request URL::" + request.getRequestURL().toString() + " Sent to Handler :: "
          + "Current Time=" + System.currentTimeMillis());
    }
  
    @SuppressWarnings("null")
    @Override
    public void afterCompletion(
        HttpServletRequest request,
        HttpServletResponse response,
        Object object,
        Exception exception
    ) throws Exception {
      long startTime = (Long) request.getAttribute("startTime");
      log.debug("Request URL::" + request.getRequestURL().toString() + ":: "
          + "End Time=" + System.currentTimeMillis());
      log.debug("Request URL::" + request.getRequestURL().toString() + ":: "
          + "Time Taken=" + (System.currentTimeMillis() - startTime));
    }
  }