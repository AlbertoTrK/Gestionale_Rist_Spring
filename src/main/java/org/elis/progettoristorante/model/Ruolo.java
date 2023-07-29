package org.elis.progettoristorante.model;

public enum Ruolo {
	CLIENTE("CLIENTE"),
	ADMIN("ADMIN"),
	RISTORATORE("RISTORATORE");
	
	private String ruolo;

	private Ruolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
}
