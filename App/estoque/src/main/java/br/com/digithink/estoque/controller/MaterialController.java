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
import br.com.digithink.estoque.domain.categoria.CategoriaService;
import br.com.digithink.estoque.domain.fornecedor.FornecedorService;
import br.com.digithink.estoque.domain.material.Material;
import br.com.digithink.estoque.domain.material.MaterialService;
import br.com.digithink.estoque.enuns.UnidadeMedida;
import jakarta.validation.Valid;

/**
 * Controlador responsável por gerenciar as requisições relacionadas aos materiais.
 */
@Controller
@RequestMapping("/materiais")
@PreAuthorize("hasAuthority('ROLE_MATERIAIS')")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private FornecedorService fornecedorService;

    /**
	 * Gera json dinâmico para Data Table (CRUD)
	 */
	@GetMapping("jsonDataTable")
    @ResponseBody
	public DataTableResult jsonDataTable(DataTableParams params) {
		return materialService.dataTableMateriais(params);
	}

    /**
     * Retorna a página inicial dos materiais, exibindo a lista de materiais cadastrados.
     * @return O ModelAndView contendo a página inicial dos materiais.
     */
    @GetMapping("")
    public ModelAndView index(){
        ModelAndView model = new ModelAndView("materiais/index");
        return model;
    }

    /**
     * Retorna a página de criação de um novo material.
     * @param material O objeto Material a ser preenchido no formulário.
     * @return O ModelAndView contendo a página de criação de um novo material.
     */
    @GetMapping("/novo")
    public ModelAndView novo(@ModelAttribute("material") Material material){
        ModelAndView model = new ModelAndView("materiais/modal-form");
        model.addObject("unidadeMedidaList", UnidadeMedida.values());
        model.addObject("categoriaList", categoriaService.buscarTodos());
        model.addObject("fornecedorList", fornecedorService.buscarTodos());
        return model;
    }

    /**
     * Retorna a página de edição de um material existente.
     * @param idMaterial O ID do material a ser editado.
     * @return O ModelAndView contendo a página de edição de um material existente.
     */
    @GetMapping("/{idMaterial}/editar")
    public ModelAndView editar(@PathVariable Long idMaterial){
        ModelAndView model = new ModelAndView("materiais/modal-form");
        model.addObject("material", materialService.buscarPorId(idMaterial).get());
        model.addObject("unidadeMedidaList", UnidadeMedida.values());
        model.addObject("categoriaList", categoriaService.buscarTodos());
        model.addObject("fornecedorList", fornecedorService.buscarTodos());
        return model;
    }

    /**
     * Salva um novo material ou atualiza um material existente.
     * @param material O objeto Material a ser salvo ou atualizado.
     * @param result O BindingResult contendo os erros de validação.
     * @return O ModelAndView contendo a página de criação de um novo material em caso de erros de validação,
     *         ou o ModelAndView para fechar o modal em caso de sucesso.
     */
    @PostMapping("/salvar")
    public ModelAndView salvar(@Valid Material material, BindingResult result){
        if(result.hasErrors()){
            return novo(material);
        }
        materialService.salvar(material);        
        return new ModelAndView("materiais/close-modal");
    }

    /**
     * Exclui um material existente.
     * @param idMaterial O ID do material a ser excluído.
     * @return A ResponseEntity contendo a resposta da exclusão.
     */
    @DeleteMapping("/{idMaterial}/excluir")
    public ResponseEntity<?> excluir(@PathVariable Long idMaterial){
        if(materialService.buscarPorId(idMaterial).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        materialService.excluir(idMaterial);
        return ResponseEntity.ok("OK");
    }

}