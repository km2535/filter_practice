package com.example.filter.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.stream.Collectors;

@Slf4j
//@Component
public class LoggerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //진입전
        log.info(">>>>진입 ");

        var request = new ContentCachingRequestWrapper((HttpServletRequest) servletRequest);
        var response = new ContentCachingResponseWrapper((HttpServletResponse) servletResponse);

        filterChain.doFilter(request, response);

        var reqJson = new String(request.getContentAsByteArray());
        log.info("req : {}", reqJson);

        var resJson = new String(response.getContentAsByteArray());
        log.info("res : {}", resJson);

        log.info("<<<<<  리턴");
        //진입 후

        response.copyBodyToResponse();
    }
}
