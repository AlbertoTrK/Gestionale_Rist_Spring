package org.elis.progettoristorante.dto;

import org.elis.progettoristorante.model.Ruolo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegistraRistoratoreDTO {
	@NotBlank(message = "l'username non può essere vuota")
	@Size(min = 5, message = "inserire almeno cinque caratteri per l'username")
	private String username;
	@NotBlank(message = "la password non può essere vuota")
	@Size(min = 6, message = "inserire almeno sei caratteri per la password")
	@Pattern(regexp = "^(?=.*[!@#&()–_[{}]:;',?/*~$^+=<>]).*$", message = "La password deve contenere almeno un simbolo")
	@Pattern(regexp = "^(?=.*[0-9]).*$", message = "La password deve contenere almeno un numero")
	@Pattern(regexp = "^(?=.*[a-z]).*$", message = "La password deve contenere almeno una lettera minuscola")
	@Pattern(regexp = "^(?=.*[A-Z]).*$", message = "La password deve contenere almeno una lettera maiuscola")
	private String password;
	@NotBlank(message = "il campo nome non può essere vuoto")
	@Size(min = 2, message = "inserire almeno due caratteri per il nome")
	private String nome;
	@NotBlank(message = "il campo cognome non può essere vuoto")
	@Size(min = 2, message = "inserire almeno due caratteri per il cognome")
	private String cognome;
	private Ruolo ruolo;
	@NotBlank(message = "il campo ristorante non può essere vuoto")
	@Size(min = 2, message = "inserire almeno due caratteri per il nome del ristorante")
	private String nomeRistorante;
//	private long id;
	
	public RegistraRistoratoreDTO(String username, String password, String nome, String cognome, Ruolo ruolo,
			String nomeRistorante, long id) {
		this.username = username;
		this.password = password;
		this.nome = nome;
		this.cognome = cognome;
		this.ruolo = ruolo;
		this.nomeRistorante = nomeRistorante;
//		this.id = id;
	}
	
	public RegistraRistoratoreDTO() {
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Ruolo getRuolo() {
		return ruolo;
	}

	public void setRuolo(Ruolo ruolo) {
		this.ruolo = ruolo;
	}

	public String getNomeRistorante() {
		return nomeRistorante;
	}

	public void setNomeRistorante(String nomeRistorante) {
		this.nomeRistorante = nomeRistorante;
	}
}