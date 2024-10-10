package br.com.digithink.estoque.domain.usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import br.com.digithink.estoque.custom.Auxiliar;
import br.com.digithink.estoque.custom.DataTableParams;
import br.com.digithink.estoque.custom.DataTableResult;

@Service
public class UsuarioService {
		
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioCustomRepository customUsuarioRepository;
	
	
	public Usuario save(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	
	/**
	 * Método responsável por receber os parâmetros do jQuery Data Table e passar para o service
	 * filtrar as informações no banco de dados
	 */
	public DataTableResult dataTableUsuarios(DataTableParams params) {

		// colunas a serem consultadas conforme modelos relacionais
		String colunas[]={"nome","email","login","situacaoUsuario","id"};
		
		// varre a lista de registros no banco de dados e adiciona na lista de informações
		List<Usuario> usuariosList = customUsuarioRepository.listEntitiesToDataTable(colunas, params, Usuario.class); 
				
		// gera o DataTable e popula com as informações da lista de objetos
		DataTableResult dataTable = new DataTableResult();
		dataTable.setSEcho(String.valueOf(System.currentTimeMillis()));
		dataTable.setITotalRecords(usuariosList.size());
		dataTable.setITotalDisplayRecords(customUsuarioRepository.totalEntitiesToDataTable(colunas, Auxiliar.removeAcentos(params.sSearch()), Usuario.class));
		dataTable.setAaData(usuariosList.toArray());
		return dataTable;
	}

	
	public Optional<Usuario> findById(Long idPasta) {
		return usuarioRepository.findById(idPasta);
	}

	
	/*
	 * Valida a inclusão de um novo usuário verificando se já existe um usuário com o mesmo e-mail
	 */ 
	public void validaInclusao(Usuario usuario, BindingResult result) {
		if(usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
			result.rejectValue("email", "erroDescricao", "* Já existe um usuário cadastrado com este e-mail");
		}
	}
	

	/**
	 * Valida a edição de um usuário verificando se já existe um usuário com o mesmo e-mail
	 */
	public void validaEdicao(Usuario usuario, BindingResult result) {
		if(usuarioRepository.findByEmailAndIdIsNot(usuario.getEmail(), usuario.getId()).isPresent()) {
			result.rejectValue("email", "erroDescricao", "* Já existe um usuário cadastrado com este e-mail");
		}
	}

    /**
	 * Busca um usuário pelo login
	 */
    public Optional<UserDetails> findByLogin(String login) {
        return usuarioRepository.findByLoginIgnoreCase(login);
    }


	 /**
	 * Busca um usuário pelo e-mail
	 */
	public Optional<Usuario> findByEmail(String email) {
		return usuarioRepository.findByEmail(email);
	}

}
