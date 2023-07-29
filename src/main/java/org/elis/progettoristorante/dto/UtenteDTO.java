package org.elis.progettoristorante.dto;

import org.elis.progettoristorante.model.Utente;

public class UtenteDTO {
	private String username;
//	private String password;
	private String nome;
	private String cognome;
	private long id;

	public UtenteDTO(Utente u) {
		username = u.getUsername();
//		password = u.getPassword();
		nome = u.getNome();
		cognome = u.getCognome();
		id = u.getId();
	}

	public UtenteDTO() {

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

//	public String getPassword() {
//		return password;
//	}
//	public void setPassword(String password) {
//		this.password = password;
//	}
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
