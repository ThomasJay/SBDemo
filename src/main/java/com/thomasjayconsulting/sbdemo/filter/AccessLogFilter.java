package com.thomasjayconsulting.sbdemo.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AccessLogFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getRequestURI().toLowerCase().contains("/api")) {

            long time = System.currentTimeMillis();

            try {
                filterChain.doFilter(request, response);
            }
            finally {
                time = System.currentTimeMillis() - time;

                String remoteIPAddress = request.getHeader("X-FORWARDED-FOR");

                if (remoteIPAddress == null || remoteIPAddress.isEmpty()) {
                    remoteIPAddress = request.getRemoteAddr();
                }

                log.info("{} {} {} {} {} {}ms", remoteIPAddress, request.getMethod(), request.getRequestURI(), response.getContentType(),
                        response.getStatus(), time);

            }

        }
        else {
            filterChain.doFilter(request, response);
        }
    }
}
