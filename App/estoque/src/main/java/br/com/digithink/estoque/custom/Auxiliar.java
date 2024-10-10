package br.com.digithink.estoque.custom;

import java.text.Normalizer;

public class Auxiliar {

    /**
	 * Remove os acentos de uma String utilizando Normalizer
	 */
	public static String removeAcentos(String string) {
		if (string != null) {
			string = Normalizer.normalize(string, Normalizer.Form.NFD);
			string = string.replaceAll("[^\\p{ASCII}]", "");
		}
		return string;
	}
    
}
