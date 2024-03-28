package org.ahmedukamel.mulham.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.ahmedukamel.mulham.dto.response.ApiResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException ex) throws IOException {

        if (response.isCommitted()) {
            return;
        }
        ApiResponse apiResponse = new ApiResponse(false, ex.getMessage(), ex.getClass());
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        new ObjectMapper().writeValue(response.getWriter(), apiResponse);

    }
}
