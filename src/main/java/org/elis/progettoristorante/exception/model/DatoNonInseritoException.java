package org.elis.progettoristorante.exception.model;

public class DatoNonInseritoException extends RuntimeException{
	
	private String nomeCampo;
	
	public DatoNonInseritoException(String nomeCampo) {
		this.nomeCampo=nomeCampo;
	}

	public String getNomeCampo() {
		return nomeCampo;
	}

	public void setNomeCampo(String nomeCampo) {
		this.nomeCampo = nomeCampo;
	}
}
