package com.periodicalsubscription.aspect.logging;

import com.periodicalsubscription.exceptions.ImageUploadException;
import com.periodicalsubscription.exceptions.LoginException;
import com.periodicalsubscription.exceptions.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * logging aspect for application
 */
@Aspect
@Component
@Log4j2
public class LoggingAspect {

    /**
     * logs the invocation of controller layer method
     *
     * @param jp method to be logged
     */
    @Before("@annotation(com.periodicalsubscription.aspect.logging.annotation.LogInvocation)")
    public void logMethodCallControllerLayer(JoinPoint jp) {
        String className = jp.getSignature().getDeclaringTypeName();
        String methodName = jp.getSignature().getName();
        log.debug("Calling a controller layer method " + methodName + " of " + className);
    }

    /**
     * logs the invocation of service layer method
     *
     * @param jp method to be logged
     */
    @Before("@annotation(com.periodicalsubscription.aspect.logging.annotation.LogInvocationService)")
    public void logMethodCallService(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        Object[] args = jp.getArgs();
        log.debug("Calling a service method " + methodName + " with " + Arrays.toString(args));
    }

    /**
     * logs the exception thrown while method execution
     *
     * @param jp method executed while an exception occurred
     * @param e  LoginException to be logged
     */
    @AfterThrowing(value = "@annotation(com.periodicalsubscription.aspect.logging.annotation.LoginEx)", throwing = "e")
    public void afterThrowingLoginException(JoinPoint jp, LoginException e) {
        String className = jp.getSignature().getDeclaringTypeName();
        String methodName = jp.getSignature().getName();
        log.error("Class " + className + " method " + methodName + " error. Exception is " + e);
    }

    /**
     * logs the exception thrown while method execution
     *
     * @param jp method executed while an exception occurred
     * @param e  ServiceException to be logged
     */
    @AfterThrowing(value = "@annotation(com.periodicalsubscription.aspect.logging.annotation.ServiceEx)", throwing = "e")
    public void afterThrowingServiceException(JoinPoint jp, ServiceException e) {
        String className = jp.getSignature().getDeclaringTypeName();
        String methodName = jp.getSignature().getName();
        Object[] args = jp.getArgs();
        log.error("Class " + className + " method " + methodName + " error with args " + Arrays.toString(args) + ". Exception is " + e);
    }

    /**
     * logs the exception thrown while method execution
     *
     * @param jp method executed while an exception occurred
     * @param e  ImageUploadException to be logged
     */
    @AfterThrowing(value = "@annotation(com.periodicalsubscription.aspect.logging.annotation.ImageUploadEx)", throwing = "e")
    public void afterThrowingImageUploadException(JoinPoint jp, ImageUploadException e) {
        String className = jp.getSignature().getDeclaringTypeName();
        String methodName = jp.getSignature().getName();
        Object[] args = jp.getArgs();
        log.error("Class " + className + " method " + methodName + " error with args " + Arrays.toString(args) + ". Exception is " + e);
    }
}
