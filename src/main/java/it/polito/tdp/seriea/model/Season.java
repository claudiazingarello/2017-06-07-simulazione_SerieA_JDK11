package it.polito.tdp.seriea.model;

public class Season {

	private int season;
	private String description;
	
	public Season(int season, String description) {
		super();
		this.season = season;
		this.description = description;
	}
	public int getSeason() {
		return season;
	}
	public void setSeason(int season) {
		this.season = season;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Season [" + season + "]";
	}
	
	
	
	
}

