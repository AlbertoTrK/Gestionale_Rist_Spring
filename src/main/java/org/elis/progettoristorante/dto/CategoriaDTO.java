package org.elis.progettoristorante.dto;

import java.util.List;

import org.elis.progettoristorante.model.Piatto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoriaDTO {
	
	@NotBlank(message = "il campo categoria non può essere vuoto")
	@Size(min = 3, message = "inserire almeno tre caratteri per la categoria")
	private String nome;
	@JsonIgnore //senza non funziona
	private List<Piatto> piattiInCategoria;
	
	public CategoriaDTO(String nome, List<Piatto> piattiInCategoria) {
		this.nome = nome;
		this.piattiInCategoria = piattiInCategoria;
	}
	
	public CategoriaDTO() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Piatto> getPiattiInCategoria() {
		return piattiInCategoria;
	}

	public void setPiattiInCategoria(List<Piatto> piattiInCategoria) {
		this.piattiInCategoria = piattiInCategoria;
	}
	
	

}
