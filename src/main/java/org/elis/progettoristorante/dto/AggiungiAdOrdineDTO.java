package org.elis.progettoristorante.dto;

import java.util.Map;

import org.elis.progettoristorante.model.Ordine;

import jakarta.validation.constraints.Min;

public class AggiungiAdOrdineDTO {
	@Min(value = 1, message = "L'id ordine deve essere almeno 1")
	private long idOrdine;
	@Min(value = 1, message = "L'id ristorante deve essere almeno 1")
	private long idRistorante;
	private Map<Long, Integer> idProdottoQuantità;

	public AggiungiAdOrdineDTO() {
	}

	public long getIdOrdine() {
		return idOrdine;
	}

	public void setIdOrdine(long idOrdine) {
		this.idOrdine = idOrdine;
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

}
