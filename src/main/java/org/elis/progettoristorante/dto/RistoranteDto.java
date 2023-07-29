package org.elis.progettoristorante.dto;

import java.util.List;

import org.elis.progettoristorante.model.Piatto;
import org.elis.progettoristorante.model.Ristorante;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RistoranteDto {
	@NotBlank(message = "il nome del ristorante non può essere vuota")
	@Size(min = 2, message = "inserire almeno cinque caratteri per il nome del ristorante")
	private String nome;
	@JsonIgnore
	private List<Piatto> piattiRistorante;

	public RistoranteDto(String nome, List<Piatto> piattiRistorante) {
		this.nome = nome;
		this.piattiRistorante = piattiRistorante;
	}

	public RistoranteDto(String nome) {
		this.nome = nome;
	}

	public RistoranteDto() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Piatto> getPiattiRistorante() {
		return piattiRistorante;
	}

	public void setPiattiRistorante(List<Piatto> piattiRistorante) {
		this.piattiRistorante = piattiRistorante;
	}
}
