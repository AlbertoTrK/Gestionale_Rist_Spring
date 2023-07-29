package org.elis.progettoristorante.exception.model;

public class OggettoNonTrovatoException extends RuntimeException{
	private String msg;

	public OggettoNonTrovatoException(String msg) {
		super(msg);
	}
}
