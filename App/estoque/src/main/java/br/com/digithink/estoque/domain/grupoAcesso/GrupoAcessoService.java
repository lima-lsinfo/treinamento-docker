package br.com.digithink.estoque.domain.grupoAcesso;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.digithink.estoque.custom.Auxiliar;
import br.com.digithink.estoque.custom.DataTableParams;
import br.com.digithink.estoque.custom.DataTableResult;

@Service
public class GrupoAcessoService {
	
	@Autowired
	private GrupoAcessoRepository grupoAcessoRepository;
	
	@Autowired
	private GrupoAcessoCustomRepository customGrupoAcessoRepository;

	public DataTableResult dataTableGrupoAcessos(DataTableParams params) {

		// colunas a serem consultadas conforme modelos relacionais
		String colunas[]={"id","situacao","grupo"};
		
		// varre a lista de registros no banco de dados e adiciona na lista de informações
		List<GrupoAcesso> gruposList = customGrupoAcessoRepository.listEntitiesToDataTable(colunas, params, GrupoAcesso.class); 
		
		// gera o DataTable e popula com as informações da lista de objetos
		DataTableResult dataTable = new DataTableResult();
		dataTable.setSEcho(String.valueOf(System.currentTimeMillis()));
		dataTable.setITotalRecords(gruposList.size());
		dataTable.setITotalDisplayRecords(customGrupoAcessoRepository.totalEntitiesToDataTable(colunas, Auxiliar.removeAcentos(params.sSearch()), GrupoAcesso.class));
		dataTable.setAaData(gruposList.toArray());
		return dataTable;
	}

	public Optional<GrupoAcesso> findById(Long idGrupoAcesso) {
		return grupoAcessoRepository.findById(idGrupoAcesso);
	}

	public void save(GrupoAcesso grupoAcesso) {
		grupoAcessoRepository.save(grupoAcesso);
		
	}
	
	public List<GrupoAcesso> findAll(boolean apenasAtivos){
		
		if(apenasAtivos) {
			return grupoAcessoRepository.findAllAtivos();
		}
		return grupoAcessoRepository.findAll(); 
	}

}
