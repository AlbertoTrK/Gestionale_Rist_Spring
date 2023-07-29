package org.elis.progettoristorante.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Ordine {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false)
	private LocalDateTime dataInvio;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Ristorante ristorante;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Utente cliente;
	
	private Stato stato;
	@OneToMany(mappedBy = "ordine", cascade = CascadeType.PERSIST)
	private List<RigaDOrdine> righeDOrdine;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public LocalDateTime getDataInvio() {
		return dataInvio;
	}
	public void setDataInvio(LocalDateTime dataInvio) {
		this.dataInvio = dataInvio;
	}
	public Ristorante getRistorante() {
		return ristorante;
	}
	public void setRistorante(Ristorante ristorante) {
		this.ristorante = ristorante;
	}
	public Utente getCliente() {
		return cliente;
	}
	public void setCliente(Utente cliente) {
		this.cliente = cliente;
	}
	public Stato getStato() {
		return stato;
	}
	public void setStato(Stato stato) {
		this.stato = stato;
	}
	public List<RigaDOrdine> getRigheDOrdine() {
		return righeDOrdine;
	}
	public void setRigheDOrdine(List<RigaDOrdine> righeDOrdine) {
		this.righeDOrdine = righeDOrdine;
	}
}
