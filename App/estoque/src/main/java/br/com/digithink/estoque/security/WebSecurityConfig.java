package br.com.digithink.estoque.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig{

	@Autowired
    private ApplicationContext applicationContext;
	
	@Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	
    	http.authorizeHttpRequests( auth -> {
    		
            auth.requestMatchers("/assets/**").permitAll();
			auth.requestMatchers("/login/**").permitAll();
			
			/*
			auth.requestMatchers("/fornecedores**").hasRole("FORNECEDORES");
			auth.requestMatchers("/materiais**").hasRole("MATERIAIS");
			auth.requestMatchers("/categorias**").hasRole("CATEGORIAS");
			auth.requestMatchers("/movimentos**").hasRole("MOVIMENTACAO");
			*/


			//auth.requestMatchers("/movimentos**").hasAnyRole("MOVIMENTACAO", "DEL_MOVIMENTACAO");			
			//auth.requestMatchers(HttpMethod.DELETE, "/movimentos**").hasRole("DEL_MOVIMENTACAO");
			
            
		    auth.anyRequest().authenticated();
		    
		});
    	
    	http.csrf(AbstractHttpConfigurer::disable);
		
        http.formLogin( form -> {
            form.loginPage("/login"); 
            form.successHandler(new CustomAuthenticationSuccessHandler(applicationContext));
            form.failureHandler(new CustomAuthenticationFailureHandler());
            form.permitAll();
        });

        http.logout( logout -> {
            logout.logoutUrl("/logout");
            logout.invalidateHttpSession(true);
        });

		http.exceptionHandling( exception -> {
			exception.accessDeniedPage("/acessoNegado");
		});

        return http.build();
	}
	
	/**
     * Configura o método de autenticação
     */
    @Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	@Bean
	/**
	 * Configura o tipo de criptografia utilizado para o banco de dados
	 */
	PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}
