package br.com.digithink.estoque.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {                    
            
        //String encodedMessage = URLEncoder.encode(exception.getMessage(), StandardCharsets.UTF_8.toString());
        //response.sendRedirect(request.getContextPath()+"/login?error="+ encodedMessage);
        response.sendRedirect(request.getContextPath()+"/login?error");
        
    }
    
}
