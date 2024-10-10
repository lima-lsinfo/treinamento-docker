package br.com.digithink.estoque.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.digithink.estoque.custom.DataTableParams;
import br.com.digithink.estoque.custom.DataTableResult;
import br.com.digithink.estoque.domain.categoria.Categoria;
import br.com.digithink.estoque.domain.categoria.CategoriaService;
import jakarta.validation.Valid;

/**
 * Controlador responsável por gerenciar as requisições relacionadas às categorias.
 */
@Controller
@PreAuthorize("hasAuthority('ROLE_CATEGORIAS')")
@RequestMapping("/categorias")

public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    /**
	 * Gera json dinâmico para Data Table (CRUD)
	 */
	@GetMapping("jsonDataTable")
    @ResponseBody
	public DataTableResult jsonDataTable(DataTableParams params) {
		return categoriaService.dataTableCategorias(params);
	}

    /**
     * Retorna a página inicial das categorias.
     * @return o modelo e a visualização da página inicial das categorias.
     */
    @GetMapping("")
    public ModelAndView index(){
        ModelAndView model = new ModelAndView("categorias/index");
        return model;
    }

    /**
     * Retorna a página de criação de uma nova categoria.
     * @param categoria a categoria a ser criada.
     * @return o modelo e a visualização da página de criação de uma nova categoria.
     */
    @GetMapping("/novo")
    public ModelAndView novo(@ModelAttribute("categoria") Categoria categoria){
        ModelAndView model = new ModelAndView("categorias/modal-form");
        return model;
    }

    /**
     * Retorna a página de edição de uma categoria existente.
     * @param idCategoria o ID da categoria a ser editada.
     * @return o modelo e a visualização da página de edição de uma categoria existente.
     */
    @GetMapping("/{idCategoria}/editar")
    public ModelAndView editar(@PathVariable Long idCategoria){
        ModelAndView model = new ModelAndView("categorias/modal-form");
        model.addObject("categoria", categoriaService.buscarPorId(idCategoria).get());
        return model;
    }

    /**
     * Salva uma categoria.
     * @param categoria a categoria a ser salva.
     * @param result o resultado da validação da categoria.
     * @return o modelo e a visualização da página de fechamento do modal.
     */
    @PostMapping("/salvar")
    public ModelAndView salvar(@Valid Categoria categoria, BindingResult result){
        if(result.hasErrors()){
            return novo(categoria);
        }
        categoriaService.salvar(categoria);
        ModelAndView model = new ModelAndView("categorias/close-modal");
        return model;
    }

    /**
     * Exclui uma categoria.
     * @param idCategoria o ID da categoria a ser excluída.
     * @return uma resposta HTTP indicando o sucesso ou a falha da exclusão.
     */
    @DeleteMapping("/{idCategoria}/excluir")
    public ResponseEntity<?> excluir(@PathVariable Long idCategoria){
        if(categoriaService.buscarPorId(idCategoria).isEmpty()){
            return ResponseEntity.notFound().build();            
        }
        categoriaService.excluir(idCategoria);
        return ResponseEntity.ok("OK");
    }
}
