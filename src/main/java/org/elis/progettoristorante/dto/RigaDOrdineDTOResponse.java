package org.elis.progettoristorante.dto;

public class RigaDOrdineDTOResponse {
	private String nomePiatto;
	private long idOrdine;
	private int quantità;
	
	public RigaDOrdineDTOResponse() {
		
	}

	public String getNomePiatto() {
		return nomePiatto;
	}

	public void setNomePiatto(String nomePiatto) {
		this.nomePiatto = nomePiatto;
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
