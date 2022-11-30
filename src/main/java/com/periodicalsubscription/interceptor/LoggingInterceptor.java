package com.periodicalsubscription.interceptor;

import com.periodicalsubscription.aspect.logging.annotation.LogInvocation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * interceptor for logging requests
 */
@Log4j2
public class LoggingInterceptor implements HandlerInterceptor {
    @Override
    @LogInvocation
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("Try to send request: " + request.getRequestURI() + " method: " + request.getMethod());
        return true;
    }

    @Override
    @LogInvocation
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.debug("Handle request successfully: " + request.getRequestURI() + " method: " + request.getMethod());
    }

    @Override
    @LogInvocation
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.debug("Complete view rendering for: " + request.getRequestURI() + " method: " + request.getMethod());
    }
}
