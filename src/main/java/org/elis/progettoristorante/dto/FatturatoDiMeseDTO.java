package org.elis.progettoristorante.dto;

import java.time.Month;

public class FatturatoDiMeseDTO {
	
	private Month mese;
	private double fatturato;
	
	public Month getMese() {
		return mese;
	}
	public void setMese(Month mese) {
		this.mese = mese;
	}
	public double getFatturato() {
		return fatturato;
	}
	public void setFatturato(double fatturato) {
		this.fatturato = fatturato;
	}
	
	
}
