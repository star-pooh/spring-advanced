package org.example.expert.domain.common.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AdminApiLogging {

    private final ObjectMapper objectMapper;

    @Around("changeUserRoleMethod() || deleteCommentMethod()")
    public Object adminApiLogging(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();

        String requestBody = getRequestBody((ContentCachingRequestWrapper) request);
        Long userId = (Long) request.getAttribute("userId");

        log.info("API Request Log - User ID : {}, Request Time : {}, URL : {}, RequestBody : {}",
                userId, LocalDateTime.now(), request.getRequestURL(), requestBody);

        Object result = joinPoint.proceed();

        String responseBody = getResponseBody((ContentCachingResponseWrapper) response);
        log.info("API Response Log - User ID : {}, ResponseBody : {}", userId, responseBody);

        return result;
    }

    private String getRequestBody(ContentCachingRequestWrapper request) throws IOException {
        byte[] content = request.getContentAsByteArray();

        if (content.length > 0) {
            Object requestBody = objectMapper.readValue(content, Object.class);

            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestBody);
        }

        return "{}";
    }

    private String getResponseBody(ContentCachingResponseWrapper response) throws IOException {
        byte[] content = response.getContentAsByteArray();

        if (content.length > 0) {
            Object responseBody = objectMapper.readValue(content, Object.class);

            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseBody);
        }

        return "{}";
    }

    @Pointcut("execution(* org.example.expert.domain.user.controller.UserAdminController.changeUserRole(..))")
    private void changeUserRoleMethod() {
    }

    @Pointcut("execution(* org.example.expert.domain.comment.controller.CommentAdminController.deleteComment(..))")
    private void deleteCommentMethod() {
    }
}
