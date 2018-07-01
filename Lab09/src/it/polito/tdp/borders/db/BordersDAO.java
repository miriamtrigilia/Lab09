package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public List<Country> loadAllCountries() {

		String sql = "SELECT distinct ccode, StateAbb, StateNme from contiguity, country where contiguity.state1no= country.ccode and conttype=1 and state1no>state2no ";
		List<Country> result = new ArrayList<Country>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				//System.out.format("%d %s %s\n", rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
				result.add(new Country(rs.getInt("ccode"),rs.getString("statenme"),rs.getString("stateabb")));
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Border> getCountryPairs(int anno) {
		String sql = "select StateNme, state1no,state2no,state1ab,state2ab,dyad,year\n" + 
				"from contiguity, country\n" + 
				"where contiguity.state1no= country.ccode and year<=? and conttype=1 and state1no>state2no";
		List<Border> result = new ArrayList<Border>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Border(rs.getInt("dyad"),new Country(rs.getInt("state1no"),rs.getString("statenme"),rs.getString("state1ab")),new Country(rs.getInt("state2no"),rs.getString("statenme"),rs.getString("state2ab")),rs.getInt("year")));
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	public List<Country> getCountryByYear (int anno) {
		
		String sql = "SELECT  distinct state1no, state1ab,  statenme\n" + 
				"from contiguity, country\n" + 
				"where contiguity.state1no= country.ccode and year<=?\n" + 
				"ORDER BY state1no";
		
		List<Country> result = new ArrayList<Country>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Country(rs.getInt("state1no"),rs.getString("statenme"),rs.getString("state1ab")));
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
}
