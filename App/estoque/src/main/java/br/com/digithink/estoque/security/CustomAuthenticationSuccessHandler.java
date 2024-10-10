package br.com.digithink.estoque.security;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import br.com.digithink.estoque.domain.usuario.Usuario;
import br.com.digithink.estoque.session.SessionParameters;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
  
    private final ApplicationContext applicationContext;
    
    public CustomAuthenticationSuccessHandler(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        
        Usuario usuario = (Usuario) authentication.getPrincipal();

        if(usuario.isContaResetada() || usuario.isNovaConta()){
            response.sendRedirect(request.getContextPath()+"/login/formReset");
            return;
        }

        SessionParameters sessionParameters = applicationContext.getBean(SessionParameters.class);
        LocalDate now = LocalDate.now();
        sessionParameters.setAno(now.getYear());
        response.sendRedirect(request.getContextPath());
                
    }
    
}
