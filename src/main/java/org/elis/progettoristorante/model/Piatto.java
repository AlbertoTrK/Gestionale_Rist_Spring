package org.elis.progettoristorante.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Piatto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false)
	private String nome;
	@Column
	private String descrizione;
	@Column(nullable = false)
	private double prezzo;
	@ManyToMany
	@JoinTable(name = "ingredienti_piatto", joinColumns = @JoinColumn(name = "id_piatto"), inverseJoinColumns = @JoinColumn(name = "id_ingrediente"))
	private List<Ingrediente> ingredienti_piatto;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Categoria categoria;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Ristorante ristorante;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public double getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}
	public List<Ingrediente> getIngredienti_piatto() {
		return ingredienti_piatto;
	}
	public void setIngredienti_piatto(List<Ingrediente> ingredienti_piatto) {
		this.ingredienti_piatto = ingredienti_piatto;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public Ristorante getRistorante() {
		return ristorante;
	}
	public void setRistorante(Ristorante ristorante) {
		this.ristorante = ristorante;
	}
}
