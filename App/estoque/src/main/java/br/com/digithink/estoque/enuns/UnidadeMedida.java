package br.com.digithink.estoque.enuns;

public enum UnidadeMedida {

    // Comprimento
    METRO("m", "Metro"),
    CENTIMETRO("cm", "Centímetro"),
    MILIMETRO("mm", "Milímetro"),
    QUILOMETRO("km", "Quilômetro"),

    // Massa
    QUILOGRAMA("kg", "Quilograma"),
    GRAMA("g", "Grama"),
    MILIGRAMA("mg", "Miligrama"),
    TONELADA("t", "Tonelada"),

    // Volume
    LITRO("L", "Litro"),
    MILILITRO("mL", "Mililitro"),
    METRO_CUBICO("m³", "Metro Cúbico"),

    // Área
    METRO_QUADRADO("m²", "Metro Quadrado"),    

    // Peças
    UNIDADE("un", "Unidade"),
    CAIXA("cx", "Caixa"),
    PACOTE("pct", "Pacote"),
    LOTE("lt", "Lote");

    private final String simbolo;
    private final String descricao;

    UnidadeMedida(String simbolo, String descricao) {
        this.simbolo = simbolo;
        this.descricao = descricao;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao + " (" + simbolo + ")";
    }
}
