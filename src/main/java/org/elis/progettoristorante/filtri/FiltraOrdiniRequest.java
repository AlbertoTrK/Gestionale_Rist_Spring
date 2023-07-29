package org.elis.progettoristorante.filtri;

import java.time.LocalDateTime;

public class FiltraOrdiniRequest {
	
	private LocalDateTime dataInvio;
	private String nomeRistorante;
	private String username;
	
	public LocalDateTime getDataInvio() {
		return dataInvio;
	}
	public void setDataInvio(LocalDateTime dataInvio) {
		this.dataInvio = dataInvio;
	}
	public String getNomeRistorante() {
		return nomeRistorante;
	}
	public void setNomeRistorante(String nomeRistorante) {
		this.nomeRistorante = nomeRistorante;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
