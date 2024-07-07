package com.telemedicine.payment.external;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
@Component
public class TokenInterceptor implements RequestInterceptor {
    private final HttpServletRequest httpServletRequest;

    public TokenInterceptor(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Authorization",httpServletRequest.getHeader("Authorization"));

    }
}

