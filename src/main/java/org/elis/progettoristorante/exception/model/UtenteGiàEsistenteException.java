package org.elis.progettoristorante.exception.model;

public class UtenteGiàEsistenteException extends RuntimeException{
	
	private String msg;

	public UtenteGiàEsistenteException(String msg) {
		super(msg);
	}
}
