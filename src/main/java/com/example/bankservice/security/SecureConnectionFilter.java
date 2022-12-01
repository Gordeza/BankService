package com.example.bankservice.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class SecureConnectionFilter implements Filter {
    @Value("${bank-service-key.header}")
    private String principalRequestHeader;

    @Value("${bank-service-key.token}")
    private String principalRequestToken;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String secureToken = ((HttpServletRequest)servletRequest).getHeader(principalRequestHeader);
        if(secureToken == null || !secureToken.equals(principalRequestToken)) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Bank can only be accessed via ATM");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
