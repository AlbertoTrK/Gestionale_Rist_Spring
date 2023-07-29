package org.elis.progettoristorante.dto;

import java.util.Map;

import jakarta.validation.constraints.Min;

public class AggiungiOrdineDTO {
	
	@Min(value = 1, message = "L'id utente deve essere almeno 1")
	private long idUtente;
	@Min(value = 1, message = "L'id ristorante deve essere almeno 1")
	private long idRistorante;
	private Map<Long, Integer> idProdottoQuantità;

	public AggiungiOrdineDTO() {
	}

	public long getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(long idUtente) {
		this.idUtente = idUtente;
	}

	public long getIdRistorante() {
		return idRistorante;
	}

	public void setIdRistorante(long idRistorante) {
		this.idRistorante = idRistorante;
	}

	public Map<Long, Integer> getIdProdottoQuantità() {
		return idProdottoQuantità;
	}

	public void setIdProdottoQuantità(Map<Long, Integer> idProdottoQuantità) {
		this.idProdottoQuantità = idProdottoQuantità;
	}

	public void metodoMap() {
		for (long l : idProdottoQuantità.keySet()) {
			long idProdotto = l;
			int quantità = idProdottoQuantità.get(l);
		}
	}

}
