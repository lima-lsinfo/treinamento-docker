package br.com.digithink.estoque.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.digithink.estoque.session.SessionParameters;

@Controller
public class HomeController {

    @Autowired
    private SessionParameters sessionParameters;

    @GetMapping("/start")
    public ModelAndView start() {
        return new ModelAndView("home/pages-starter");
    }

    @GetMapping("/")
    public ModelAndView index() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Principal: " + auth.getPrincipal());
        auth.getAuthorities().forEach(System.out::println);

        return new ModelAndView("home/index");
    }

    @GetMapping("/funcaoMenu")
    public ResponseEntity<?> funcaoMenu() {
        sessionParameters.setMenuAberto(!sessionParameters.isMenuAberto());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/acessoNegado")
    public ModelAndView acessoNegado() {
        return new ModelAndView("home/acesso-negado");
    }


}
