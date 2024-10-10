package br.com.digithink.estoque.domain.fornecedor;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import br.com.digithink.estoque.custom.Auxiliar;
import br.com.digithink.estoque.custom.DataTableParams;
import br.com.digithink.estoque.custom.DataTableResult;

/**
 * Classe de serviço para manipulação de fornecedores.
 */
@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private FornecedorCustomRepository fornecedorCustomRepository;

    /**
     * Salva um fornecedor.
     * 
     * @param fornecedor O fornecedor a ser salvo.
     */
    public void salvar(Fornecedor fornecedor) {
        fornecedorRepository.save(fornecedor);
    }

    /**
     * Busca um fornecedor pelo ID.
     * 
     * @param id O ID do fornecedor.
     * @return Um Optional contendo o fornecedor, se encontrado.
     */
    public Optional<Fornecedor> buscarPorId(Long id) {
        return fornecedorRepository.findById(id);
    }

    /**
     * Busca todos os fornecedores.
     * 
     * @return Uma lista contendo todos os fornecedores.
     */
    public List<Fornecedor> buscarTodos() {
        return fornecedorRepository.findAll();
    }

    /**
     * Busca um fornecedor pelo CNPJ.
     * 
     * @param cnpj O CNPJ do fornecedor.
     * @return Um Optional contendo o fornecedor, se encontrado.
     */
    public Optional<Fornecedor> buscaPorCnpj(String cnpj) {
        return fornecedorRepository.findByCnpj(cnpj);
    }

    /**
     * Busca um fornecedor pelo CNPJ, excluindo o fornecedor com o ID especificado.
     * 
     * @param cnpj O CNPJ do fornecedor.
     * @param id O ID do fornecedor a ser excluído.
     * @return Um Optional contendo o fornecedor, se encontrado.
     */
    public Optional<Fornecedor> buscarPorCnpjEIdDiferenteDoMeu(String cnpj, Long id) {
        return fornecedorRepository.findByCnpjAndIdNot(cnpj, id);
    }

    /**
     * Exclui um fornecedor pelo ID.
     * 
     * @param idFornecedor O ID do fornecedor a ser excluído.
     */
    public void excluir(Long idFornecedor) {
        fornecedorRepository.deleteById(idFornecedor);
    }

    /**
     * Valida a alteração de um fornecedor.
     * 
     * @param fornecedor O fornecedor a ser validado.
     * @param result O BindingResult contendo os erros de validação.
     */
    public void validaAlteracao(Fornecedor fornecedor, BindingResult result) {
        // Validação de registros existentes (editar)
        if (fornecedor.getId() != null) {
            if (buscarPorCnpjEIdDiferenteDoMeu(fornecedor.getCnpj(), fornecedor.getId()).isPresent()) {
                result.rejectValue("cnpj", "", "CNPJ já cadastrado");
            }
        }
    }

    /**
     * Valida a inclusão de um fornecedor.
     * 
     * @param fornecedor O fornecedor a ser validado.
     * @param result O BindingResult contendo os erros de validação.
     */
    public void validaInclusao(Fornecedor fornecedor, BindingResult result) {
        // Validação de registros existentes (novos)
        if (fornecedor.getId() == null) {
            if (buscaPorCnpj(fornecedor.getCnpj()).isPresent()) {
                result.rejectValue("cnpj", "null", "CNPJ já cadastrado");
            }
        }
    }

    public DataTableResult dataTableFornecedores(DataTableParams params)  {

		// colunas a serem consultadas conforme modelos relacionais
		String[] colunas={"id","nome","telefone","email","contato","situacao"};
				
		// varre a lista de registros no banco de dados e adiciona na lista de informações
		var fornecedoresList = fornecedorCustomRepository.listEntitiesToDataTable(colunas, params, Fornecedor.class);
		
		// gera o DataTable e popula com as informações da lista de objetos
		DataTableResult dataTable = new DataTableResult();
		dataTable.setSEcho(String.valueOf(System.currentTimeMillis()));
		dataTable.setITotalRecords(fornecedoresList.size());
		dataTable.setITotalDisplayRecords(fornecedorCustomRepository.totalEntitiesToDataTable(colunas, Auxiliar.removeAcentos(params.sSearch()), Fornecedor.class));
		dataTable.setAaData(fornecedoresList.toArray());
		return dataTable;
	}
}
