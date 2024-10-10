package br.com.digithink.estoque.domain.fornecedor;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long>{

    Optional<Fornecedor> findByCnpj(String cnpj);

    Optional<Fornecedor> findByCnpjAndIdNot(String cnpj, Long id);
    
}
