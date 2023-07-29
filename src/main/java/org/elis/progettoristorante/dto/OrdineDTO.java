package org.elis.progettoristorante.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.elis.progettoristorante.model.RigaDOrdine;
import org.elis.progettoristorante.model.Stato;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class OrdineDTO {
	@NotNull
	private Stato statoOrdine;
	@Min(value = 1, message = "L'id utente deve essere almeno 1")
	private long idUtente;
	private String username;
	@NotNull
	private LocalDateTime dataInvio;
	@Min(value = 1, message = "L'id ristorante deve essere almeno 1")
	private long idRistorante;
	private String nomeRistorante;
	@Size(min = 1, message = "L'id ingrediente deve essere almeno 1")
	private List<Long> idRigheOrdine;
	
	public OrdineDTO(Stato statoOrdine, long idUtente, LocalDateTime dataInvio, long idRistorante) {
		this.statoOrdine = statoOrdine;
		this.idUtente = idUtente;
		this.dataInvio = dataInvio;
		this.idRistorante = idRistorante;
	}
	
	public OrdineDTO(Stato statoOrdine, long idUtente, LocalDateTime dataInvio, long idRistorante, List<Long> idRigheOrdine) {
		this.statoOrdine = statoOrdine;
		this.idUtente = idUtente;
		this.dataInvio = dataInvio;
		this.idRistorante = idRistorante;
		this.idRigheOrdine = idRigheOrdine;
	}

	public OrdineDTO() {
	
	}

	public Stato getStatoOrdine() {
		return statoOrdine;
	}

	public void setStatoOrdine(Stato statoOrdine) {
		this.statoOrdine = statoOrdine;
	}

	public long getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(long idUtente) {
		this.idUtente = idUtente;
	}

	public LocalDateTime getDataInvio() {
		return dataInvio;
	}

	public void setDataInvio(LocalDateTime dataInvio) {
		this.dataInvio = dataInvio;
	}

	public long getIdRistorante() {
		return idRistorante;
	}

	public void setIdRistorante(long idRistorante) {
		this.idRistorante = idRistorante;
	}

	public List<Long> getIdRigheOrdine() {
		return idRigheOrdine;
	}

	public void setIdRigheOrdine(List<Long> idRigheOrdine) {
		this.idRigheOrdine = idRigheOrdine;
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
	
}
