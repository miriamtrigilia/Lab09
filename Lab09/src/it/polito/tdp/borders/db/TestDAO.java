package it.polito.tdp.borders.db;

import java.util.List;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Border;

public class TestDAO {

	public static void main(String[] args) {

		BordersDAO dao = new BordersDAO();

		System.out.println("Lista di tutte le nazioni fino all'anno specificato:");
		List<Country> countries = dao.getCountryByYear(1816);
		System.out.println(countries+"\n"+countries.size());
		
		System.out.println();
		
		System.out.println("Lista di tutti i confini fino all'anno:");
		List<Border> confini = dao.getCountryPairs(1816);
		System.out.println(confini+"\n"+confini.size());
		
		dao.loadAllCountries();
	}
}
