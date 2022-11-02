package com.periodicalsubscription.filter;

import com.periodicalsubscription.aspect.logging.annotation.LogInvocation;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthorizationFilter extends OncePerRequestFilter {

    @Override
    @LogInvocation
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return path.startsWith("/user/create");
    }

    @Override
    @LogInvocation
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("user") == null) {
            request.setAttribute("message", "You need to login to see this content");
            response.sendRedirect("/login");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
