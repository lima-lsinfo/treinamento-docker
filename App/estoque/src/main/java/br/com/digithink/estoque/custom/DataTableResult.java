package br.com.digithink.estoque.custom;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe responsável por popular o as informações que serão exibidas no json de acordo
 * com a estrutura padrão da biblioteca jQuery DataTable  
 * @author Ailton
 * @version 2.0.1
 */
@JsonSerialize
public class DataTableResult {
	
	@Getter @Setter
	@JsonProperty("sEcho")
	private String sEcho;
    
	@Getter @Setter
	@JsonProperty("iTotalRecords")
	private Integer iTotalRecords;
    
	@Getter @Setter
	@JsonProperty("iTotalDisplayRecords")
	private Long iTotalDisplayRecords;
    
	@Getter @Setter
	@JsonProperty("aaData")
	private Object[] aaData;

}
