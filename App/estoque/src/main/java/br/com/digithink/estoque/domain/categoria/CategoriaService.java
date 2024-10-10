package br.com.digithink.estoque.domain.categoria;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.digithink.estoque.custom.Auxiliar;
import br.com.digithink.estoque.custom.DataTableParams;
import br.com.digithink.estoque.custom.DataTableResult;

/**
 * Classe de serviço para a entidade Categoria.
 */
@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private CategoriaCustomRepository categoriaCustomRepository;

	/**
	 * Salva uma categoria.
	 * 
	 * @param categoria a categoria a ser salva
	 */
	public void salvar(Categoria categoria) {
		categoriaRepository.save(categoria);
	}

	/**
	 * Retorna uma lista de todas as categorias.
	 * 
	 * @return a lista de categorias
	 */
	public List<Categoria> buscarTodos() {
		return categoriaRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
	}

	/**
	 * Retorna uma categoria pelo seu ID.
	 * 
	 * @param idCategoria o ID da categoria
	 * @return a categoria encontrada, ou vazio se não encontrada
	 */
	public Optional<Categoria> buscarPorId(Long idCategoria) {
		return categoriaRepository.findById(idCategoria);
	}

	/**
	 * Exclui uma categoria pelo seu ID.
	 * 
	 * @param idCategoria o ID da categoria a ser excluída
	 */
	public void excluir(Long idCategoria) {
		categoriaRepository.deleteById(idCategoria);
	}

    public DataTableResult dataTableCategorias(DataTableParams params) {
        
		// colunas a serem consultadas conforme modelos relacionais
		String[] colunas={"id","nome","situacao"};
				
		// varre a lista de registros no banco de dados e adiciona na lista de informações
		var categoriasList = categoriaCustomRepository.listEntitiesToDataTable(colunas, params, Categoria.class);
		
		// gera o DataTable e popula com as informações da lista de objetos
		DataTableResult dataTable = new DataTableResult();
		dataTable.setSEcho(String.valueOf(System.currentTimeMillis()));
		dataTable.setITotalRecords(categoriasList.size());
		dataTable.setITotalDisplayRecords(categoriaCustomRepository.totalEntitiesToDataTable(colunas, Auxiliar.removeAcentos(params.sSearch()), Categoria.class));
		dataTable.setAaData(categoriasList.toArray());
		return dataTable;
    }
}
