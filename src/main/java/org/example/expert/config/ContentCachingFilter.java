package org.example.expert.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Component
public class ContentCachingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        // HttpServletRequest/Response는 한 번 읽으면 더 이상 사용할 수 없기 때문에 캐싱 객체를 사용해서 래핑함
        // 래핑된 이후에는 반복해서 사용 가능하기 때문에 로깅 및 서비스 로직에서 문제 없이 사용 가능
        HttpServletRequest wrappedRequest = new ContentCachingRequestWrapper((HttpServletRequest) servletRequest);
        HttpServletResponse wrappedResponse = new ContentCachingResponseWrapper((HttpServletResponse) servletResponse);

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            ((ContentCachingResponseWrapper) wrappedResponse).copyBodyToResponse();
        }
    }
}
