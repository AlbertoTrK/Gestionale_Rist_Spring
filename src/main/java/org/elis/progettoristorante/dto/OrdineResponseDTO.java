package org.elis.progettoristorante.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.elis.progettoristorante.model.Stato;

public class OrdineResponseDTO {
	private String username;
	private String nomeRistorante;
	private long idOrdine;
	private LocalDateTime dataOrdine;
	private Stato stato;
	private List<RigaDOrdineDTOResponse> righeDOrdine;

	public OrdineResponseDTO() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNomeRistorante() {
		return nomeRistorante;
	}

	public void setNomeRistorante(String nomeRistorante) {
		this.nomeRistorante = nomeRistorante;
	}

	public long getIdOrdine() {
		return idOrdine;
	}

	public void setIdOrdine(long idOrdine) {
		this.idOrdine = idOrdine;
	}

	public LocalDateTime getDataOrdine() {
		return dataOrdine;
	}

	public void setDataOrdine(LocalDateTime dataOrdine) {
		this.dataOrdine = dataOrdine;
	}

	public List<RigaDOrdineDTOResponse> getRigheDOrdine() {
		return righeDOrdine;
	}

	public void setRigheDOrdine(List<RigaDOrdineDTOResponse> righeDOrdine) {
		this.righeDOrdine = righeDOrdine;
	}

	public Stato getStato() {
		return stato;
	}

	public void setStato(Stato stato) {
		this.stato = stato;
	}
}
