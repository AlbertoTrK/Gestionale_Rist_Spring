package org.elis.progettoristorante.dto;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PiattoDTO {
	@NotBlank(message = "il campo nome del piatto non può essere vuoto")
	@Size(min = 2, message = "inserire almeno due caratteri per il piatto")
	private String nome;
	@Min(value = 1, message = "il campo prezzo del piatto non può essere vuoto o minore di uno")
	private double prezzo;
	private String descrizione;
	@NotNull
	@Size(min = 1, message = "L'id ingrediente deve essere almeno 1")
	private List<Long> id_ingredienti;
	@Min(value = 1, message = "L'id categoria deve essere almeno 1")
	private long id_categoria;
	@NotNull
	@Min(value = 1, message = "L'id ristorante deve essere almeno 1")
	private long id_ristorante;

	public PiattoDTO(String nome, double prezzo, String descrizione, List<Long> id_ingredienti, long id_ristorante, long id_categoria) {
		this.nome = nome;
		this.prezzo = prezzo;
		this.descrizione = descrizione;
		this.id_ingredienti = id_ingredienti;
		this.id_categoria = id_categoria;
		this.id_ristorante = id_ristorante;
	}

	public PiattoDTO() {
		
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

	public List<Long> getId_ingredienti() {
		return id_ingredienti;
	}

	public void setId_ingredienti(List<Long> id_ingredienti) {
		this.id_ingredienti = id_ingredienti;
	}

	public long getId_categoria() {
		return id_categoria;
	}

	public void setId_categoria(long id_categoria) {
		this.id_categoria = id_categoria;
	}

	public long getId_ristorante() {
		return id_ristorante;
	}

	public void setId_ristorante(long id_ristorante) {
		this.id_ristorante = id_ristorante;
	}
}
