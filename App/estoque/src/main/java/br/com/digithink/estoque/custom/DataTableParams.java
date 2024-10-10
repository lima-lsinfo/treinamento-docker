package br.com.digithink.estoque.custom;

public record DataTableParams(
    Integer iDisplayStart, 
    Integer iDisplayLength, 
    String sSortDir_0, 
    Integer iSortCol_0, 
    String sSearch) {
}
