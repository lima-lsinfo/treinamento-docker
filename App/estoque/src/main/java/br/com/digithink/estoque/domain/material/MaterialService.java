package br.com.digithink.estoque.domain.material;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.digithink.estoque.custom.Auxiliar;
import br.com.digithink.estoque.custom.DataTableParams;
import br.com.digithink.estoque.custom.DataTableResult;

/**
 * Classe de serviço para manipulação de materiais.
 */
@Service
public class MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private MaterialCustomRepository materialCustomRepository;

    /**
     * Salva um material.
     * 
     * @param material O material a ser salvo.
     */
    public void salvar(Material material) {
        materialRepository.save(material);
    }

    /**
     * Retorna uma lista de todos os materiais ordenados pelo nome.
     * 
     * @return Uma lista de materiais.
     */
    public List<Material> buscarTodos() {
        return materialRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
    }

    /**
     * Busca um material pelo seu ID.
     * 
     * @param idCategoria O ID do material a ser buscado.
     * @return Um Optional contendo o material, se encontrado.
     */
    public Optional<Material> buscarPorId(Long idCategoria) {
        return materialRepository.findById(idCategoria);
    }

    /**
     * Exclui um material pelo seu ID.
     * 
     * @param idCategoria O ID do material a ser excluído.
     */
    public void excluir(Long idCategoria) {
        materialRepository.deleteById(idCategoria);
    }

    public DataTableResult dataTableMateriais(DataTableParams params)  {

		// colunas a serem consultadas conforme modelos relacionais
		String[] colunas={"id","nome","categoria.nome","fabricante","fornecedor.nome","valor","saldo","situacao"};

		// varre a lista de registros no banco de dados e adiciona na lista de informações
		var materiaisList = materialCustomRepository.listEntitiesToDataTable(colunas, params, Material.class);
        
        // Cria a lista de informações vazia
        List<Object[]> listaObjetos = new ArrayList<Object[]>();

        materiaisList.forEach(material -> {

            Object[] linha = {
                material.getId(),
                material.getNome(),
                material.getCategoria().getNome(),
                material.getFabricante(),
                material.getFornecedor().getNome(),
                material.getValor(),
                material.getSaldo(),
                material.getSituacao(),
                material.getId()
            };
            listaObjetos.add(linha);
        });
		
		// gera o DataTable e popula com as informações da lista de objetos
		DataTableResult dataTable = new DataTableResult();
		dataTable.setSEcho(String.valueOf(System.currentTimeMillis()));
		dataTable.setITotalRecords(materiaisList.size());
		dataTable.setITotalDisplayRecords(materialCustomRepository.totalEntitiesToDataTable(colunas, Auxiliar.removeAcentos(params.sSearch()), Material.class));
		dataTable.setAaData(listaObjetos.toArray());
		return dataTable;
	}
}
