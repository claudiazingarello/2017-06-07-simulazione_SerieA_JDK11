package it.polito.tdp.seriea.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.seriea.model.Adiacenza;
import it.polito.tdp.seriea.model.Season;
import it.polito.tdp.seriea.model.Team;

public class SerieADAO {
	
	public List<Season> listSeasons() {
		String sql = "SELECT DISTINCT s.season AS season, s.description " + 
				"FROM seasons AS s " + 
				"ORDER BY season ASC" ;
		
		List<Season> result = new ArrayList<>() ;
		
		Connection conn = DBConnect.getConnection() ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				result.add( new Season(res.getInt("season"), res.getString("description"))) ;
			}
			
			conn.close();
			return result ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Team> listTeams(Season stagione) {
		String sql = "SELECT DISTINCT t.team " + 
				"FROM seasons AS s, matches AS m, teams AS t " + 
				"WHERE s.season = ? AND s.season = m.Season " + 
				"AND (m.HomeTeam = t.team OR m.AwayTeam = t.team)" ;
		
		List<Team> result = new ArrayList<>() ;
		
		Connection conn = DBConnect.getConnection() ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, stagione.getSeason());
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				result.add( new Team(res.getString("team"))) ;
			}
			
			conn.close();
			return result ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public List<Adiacenza> getAdiacenze(Season stagione) {
		String sql = "SELECT t1.team AS te1, t2.team AS te2, m.FTR AS ris " + 
				"FROM seasons AS s, matches AS m, teams AS t1, teams AS t2 " + 
				"WHERE s.season = ? AND s.season = m.Season " + 
				"AND m.HomeTeam = t1.team AND m.AwayTeam = t2.team " + 
				"AND t1.team <> t2.team" ;
		
		List<Adiacenza> result = new ArrayList<>() ;
		
		Connection conn = DBConnect.getConnection() ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, stagione.getSeason());
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				Team t1 = new Team(res.getString("te1"));
				Team t2 = new Team(res.getString("te2"));
				
				result.add( new Adiacenza(t1, t2, res.getString("ris"))) ;
			}
			
			conn.close();
			return result ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

}
