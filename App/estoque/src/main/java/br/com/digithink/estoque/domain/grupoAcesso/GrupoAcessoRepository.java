package br.com.digithink.estoque.domain.grupoAcesso;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GrupoAcessoRepository extends JpaRepository<GrupoAcesso, Long> {
	
	@Query("from GrupoAcesso g WHERE g.situacao = 'Ativo'")
	List<GrupoAcesso> findAllAtivos();
}
