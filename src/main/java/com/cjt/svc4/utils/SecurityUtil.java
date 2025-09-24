package com.cjt.svc4.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.servlet.http.HttpServletRequest;

public class SecurityUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityUtil.class);

    public static final String X_FORWARDED_FOR = "X-FORWARDED-FOR";

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader(X_FORWARDED_FOR);

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if (ip.equals("0:0:0:0:0:0:0:1") || ip.equals("127:0:0:1")) {
            try {
                InetAddress hostAddress = InetAddress.getLocalHost();
                ip = hostAddress.getHostAddress();
            } catch (UnknownHostException e) {
                LOGGER.info("got unknown host");
                ip = "unknown";
            }
        }
        return ip;
    }

    public static String getClientIP(final HttpServletRequest request) {
        final String xfHeader = request.getHeader(X_FORWARDED_FOR);
        if (xfHeader == null || xfHeader.isEmpty() || !xfHeader.contains(request.getRemoteAddr())) {
            return request.getRemoteAddr();
        }
        String ip = xfHeader.split(",")[0];
        if (ip.equals("0:0:0:0:0:0:0:1") || ip.equals("127:0:0:1")) {
            try {
                InetAddress hostAddress = InetAddress.getLocalHost();
                ip = hostAddress.getHostAddress();
            } catch (UnknownHostException e) {
                LOGGER.info("got unknown host");
                ip = "unknown";
            }
        }
        return ip;
        // return "128.101.101.101"; // for testing 미국
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}
