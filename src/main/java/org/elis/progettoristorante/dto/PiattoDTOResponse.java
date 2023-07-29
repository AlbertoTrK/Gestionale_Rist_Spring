package org.elis.progettoristorante.dto;

import java.util.List;

import jakarta.validation.constraints.Min;

public class PiattoDTOResponse {
	
	private String nome;
	@Min(value = 1, message = "il campo prezzo del piatto non può essere vuoto o minore di uno")
	private double prezzo;
	private String descrizione;
	private List<String> nomiIngredienti;
	private String nomeCategoria;
	private String nomeRistorante;
	
	public PiattoDTOResponse() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}



	public List<String> getNomiIngredienti() {
		return nomiIngredienti;
	}

	public void setNomiIngredienti(List<String> nomiIngredienti) {
		this.nomiIngredienti = nomiIngredienti;
	}

	public String getNomeCategoria() {
		return nomeCategoria;
	}

	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}

	public String getNomeRistorante() {
		return nomeRistorante;
	}

	public void setNomeRistorante(String nomeRistorante) {
		this.nomeRistorante = nomeRistorante;
	}
	
	

}
