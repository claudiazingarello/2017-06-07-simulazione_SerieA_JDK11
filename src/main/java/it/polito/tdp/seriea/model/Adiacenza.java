package it.polito.tdp.seriea.model;

public class Adiacenza {
	private Team t1;
	private Team t2;
	private String ris;
	
	public Adiacenza(Team t1, Team t2, String ris) {
		super();
		this.t1 = t1;
		this.t2 = t2;
		this.ris = ris;
	}

	public Team getT1() {
		return t1;
	}

	public void setT1(Team t1) {
		this.t1 = t1;
	}

	public Team getT2() {
		return t2;
	}

	public void setT2(Team t2) {
		this.t2 = t2;
	}

	public String getRis() {
		return ris;
	}

	public void setRis(String ris) {
		this.ris = ris;
	}
	
}
