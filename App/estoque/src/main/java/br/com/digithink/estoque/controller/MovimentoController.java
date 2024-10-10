package br.com.digithink.estoque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.digithink.estoque.domain.fornecedor.FornecedorService;
import br.com.digithink.estoque.domain.material.MaterialService;
import br.com.digithink.estoque.domain.movimento.Movimento;
import br.com.digithink.estoque.domain.movimento.MovimentoService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/movimentos")
@PreAuthorize("hasAuthority('ROLE_MOVIMENTACAO')")
public class MovimentoController {

    @Autowired
    private MovimentoService movimentoService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private FornecedorService fornecedorService;

    @GetMapping("")
    public ModelAndView listarAllMovimentos() {
        ModelAndView model = new ModelAndView("movimentos/index");
        List<Movimento> movimentos = movimentoService.buscarTodos();
        model.addObject("movimentosList", movimentos);
        return model;
    }

    @GetMapping("/novo")
    public ModelAndView novo(Movimento movimento, Boolean erro) {
        ModelAndView model = new ModelAndView("movimentos/form");
        model.addObject("materiaisList", materialService.buscarTodos());
        model.addObject("fornecedoresList", fornecedorService.buscarTodos());
        model.addObject("erro", erro);
        return model;
    }

    @PostMapping("/salvar")
    public ModelAndView salvar(@Valid @ModelAttribute("movimento") Movimento movimento, BindingResult result,
            RedirectAttributes attributes) {

        if (movimento.getTipo().equals("Entrada")) {
            movimentoService.validaEntradaMaterial(movimento, result);
        }

        if (result.hasErrors()) {
            return novo(movimento, true);
        }

        movimentoService.salvarMovimento(movimento);
        attributes.addFlashAttribute("mensagem", "Movimento salvo com sucesso!");
        return new ModelAndView("redirect:/movimentos/novo");
    }

    @GetMapping("/{id}")
    public String getMovimentoById(@PathVariable Long id, Model model) {
        Movimento movimento = movimentoService.buscarPorId(id);
        if (movimento != null) {
            model.addAttribute("movimento", movimento);
            return "movimento/view";
        }
        return "redirect:/movimentos";
    }

    @DeleteMapping("/{idMovimento}/excluir")
    public ResponseEntity<?> excluir(@PathVariable Long idMovimento) {
        try {

            movimentoService.excluir(idMovimento);

            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro durante a exclusão do registro");
        }
    }
}