package org.elis.progettoristorante.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ModificaUtenteDTO {
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
	@NotBlank(message = "la nuova username non può essere vuota")
	@Size(min = 5, message = "inserire almeno cinque caratteri per la nuova username")
	private String nuovaUsername;
	@NotBlank(message = "la nuova password non può essere vuota")
	@Size(min = 6, message = "inserire almeno sei caratteri per la nuova password")
	@Pattern(regexp = "^(?=.*[!@#&()–_[{}]:;',?/*~$^+=<>]).*$", message = "La password deve contenere almeno un simbolo")
	@Pattern(regexp = "^(?=.*[0-9]).*$", message = "La nuova password deve contenere almeno un numero")
	@Pattern(regexp = "^(?=.*[a-z]).*$", message = "La password nuova deve contenere almeno una lettera minuscola")
	@Pattern(regexp = "^(?=.*[A-Z]).*$", message = "La password nuova deve contenere almeno una lettera maiuscola")
	private String nuovaPassword;
	
	public ModificaUtenteDTO() {
		super();
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

	public String getNuovaUsername() {
		return nuovaUsername;
	}

	public void setNuovaUsername(String nuovaUsername) {
		this.nuovaUsername = nuovaUsername;
	}

	public String getNuovaPassword() {
		return nuovaPassword;
	}

	public void setNuovaPassword(String nuovaPassword) {
		this.nuovaPassword = nuovaPassword;
	}
	
	
}
