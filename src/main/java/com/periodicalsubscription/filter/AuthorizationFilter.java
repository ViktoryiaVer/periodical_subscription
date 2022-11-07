package com.periodicalsubscription.filter;

import com.periodicalsubscription.aspect.logging.annotation.LogInvocation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {
    private final MessageSource messageSource;
    @Override
    @LogInvocation
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return path.startsWith("/user/create") || path.startsWith("/subscription/create");
    }

    @Override
    @LogInvocation
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            request.setAttribute("message", messageSource.getMessage("msg.error.login.required", null, LocaleContextHolder.getLocale()));

            ServletContext servletContext = request.getServletContext();
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/login");
            requestDispatcher.forward(request, response);
        }
        filterChain.doFilter(request, response);
    }
}
