package br.com.digithink.estoque.repository;

import java.util.List;

import br.com.digithink.estoque.custom.Auxiliar;
import br.com.digithink.estoque.custom.DataTableParams;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * Classe abstrata que representa um repositório personalizado.
 * 
 * @param <T> O tipo de entidade manipulada pelo repositório.
 */
public abstract class CustomRepository<T> {

    @PersistenceContext
    protected EntityManager em;

    /**
	 * lista os contatos para o CRUD dinâmico com Json do Data Table com base na Classe selecionada
	 */
    public List<T> listEntitiesToDataTable(String[] colunas, DataTableParams params, Class<T> type) {
        StringBuilder jpql = new StringBuilder();
        jpql.append(" FROM ").append(type.getSimpleName()).append(" t WHERE t.id > 0");
        jpql.append(" AND ( 1 = 0 ");

        for (String coluna : colunas) {
            jpql.append(" OR UNACCENT(LOWER(CAST(t.").append(coluna).append(" AS text))) LIKE :sSearch ");
        }

        jpql.append(" ) ");
		jpql.append(" ORDER BY %s %s".formatted(colunas[params.iSortCol_0()], params.sSortDir_0()));

        var query = em.createQuery(jpql.toString(), type);
        query.setParameter("sSearch", "%"+Auxiliar.removeAcentos(params.sSearch().toLowerCase())+"%");
		query.setFirstResult(params.iDisplayStart());
		query.setMaxResults(params.iDisplayLength());

        return query.getResultList();
    }

    /**
     * Retorna o número total de entidades que correspondem aos critérios de pesquisa especificados.
     * 
     * @param colunas As colunas nas quais a pesquisa será realizada.
     * @param sSearch O termo de pesquisa.
     * @param type O tipo de entidade.
     * @return O número total de entidades que correspondem aos critérios de pesquisa especificados.
     */
    public Long totalEntitiesToDataTable(String[] colunas, String sSearch, Class<T> type) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT COUNT(*) FROM ").append(type.getSimpleName()).append(" t WHERE t.id > 0");
        jpql.append(" AND ( 1 = 0 ");

        for (String coluna : colunas) {
            jpql.append(" OR UNACCENT(LOWER(CAST(t.").append(coluna).append(" AS text))) LIKE :sSearch ");
        }

        jpql.append(" ) ");

        var query = em.createQuery(jpql.toString(), Long.class);
        query.setParameter("sSearch", "%" + sSearch.toLowerCase() + "%");

        return (Long) query.getSingleResult();
    }
}
