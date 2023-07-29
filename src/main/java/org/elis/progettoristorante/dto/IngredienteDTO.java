package org.elis.progettoristorante.dto;

import java.util.List;

import org.elis.progettoristorante.model.Piatto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class IngredienteDTO {

	@NotBlank(message = "il campo ingrediente non può essere vuoto")
	@Size(min = 3, message = "inserire almeno tre caratteri per l'ingrediente")
	private String nome;
	@JsonIgnore
	private List<Piatto> piattiConIngrediente;
	
	public IngredienteDTO(String nome, List<Piatto> piattiConIngrediente) {
		super();
		this.nome = nome;
		this.piattiConIngrediente = piattiConIngrediente;
	}

	public IngredienteDTO(String nome) {
		this.nome = nome;
	}

	public IngredienteDTO() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Piatto> getPiattiConIngrediente() {
		return piattiConIngrediente;
	}

	public void setPiattiConIngrediente(List<Piatto> piattiConIngrediente) {
		this.piattiConIngrediente = piattiConIngrediente;
	}
	
	

}
