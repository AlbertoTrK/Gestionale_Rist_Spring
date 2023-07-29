package org.elis.progettoristorante.dto;

import jakarta.validation.constraints.Min;

public class RigaDOrdineDTO {
	@Min(value = 1, message = "L'id piatto deve essere almeno 1")
	private long idPiatto;
	@Min(value = 1, message = "L'id ordine deve essere almeno 1")
	private long idOrdine;
	@Min(value = 1, message = "La quantità deve essere almeno 1")
	private int quantità;

	public RigaDOrdineDTO() {

	}

	public long getIdPiatto() {
		return idPiatto;
	}

	public void setIdPiatto(long idPiatto) {
		this.idPiatto = idPiatto;
	}

	public long getIdOrdine() {
		return idOrdine;
	}

	public void setIdOrdine(long idOrdine) {
		this.idOrdine = idOrdine;
	}

	public int getQuantità() {
		return quantità;
	}

	public void setQuantità(int quantità) {
		this.quantità = quantità;
	}

}
