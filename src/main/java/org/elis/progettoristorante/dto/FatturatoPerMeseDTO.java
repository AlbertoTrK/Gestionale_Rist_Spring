package org.elis.progettoristorante.dto;

import java.util.List;
import java.util.Map;

public class FatturatoPerMeseDTO {
	
	private List<FatturatoDiMeseDTO> fatturatoPerMese;
	String nomeRistorante;
	
	public List<FatturatoDiMeseDTO> getFatturatoPerMese() {
		return fatturatoPerMese;
	}
	public void setFatturatoPerMese(List<FatturatoDiMeseDTO> fatturatoPerMese) {
		this.fatturatoPerMese = fatturatoPerMese;
	}
	public String getNomeRistorante() {
		return nomeRistorante;
	}
	public void setNomeRistorante(String nomeRistorante) {
		this.nomeRistorante = nomeRistorante;
	}
	
	

}
