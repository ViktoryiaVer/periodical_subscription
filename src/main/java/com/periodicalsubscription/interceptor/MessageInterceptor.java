package com.periodicalsubscription.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MessageInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String message = (String) session.getAttribute("message");
        if(message != null) {
            request.setAttribute("message", message);
            session.removeAttribute("message");
        }
        return true;
    }
}
