package org.elis.progettoristorante.db;

import java.util.ArrayList;
import java.util.List;

import org.elis.progettoristorante.model.Ristorante;
import org.elis.progettoristorante.model.Utente;

public class DatabaseRistorante {
	
private static final DatabaseRistorante instance = new DatabaseRistorante();
	
	private DatabaseRistorante(){
		ristoranti = new ArrayList<>();
	}
	
	public static DatabaseRistorante getInstance() {
		return instance;
	}
	
	private List<Ristorante> ristoranti;
	private long idRistoranti = 1;
	
	public void aggiungiRistorante (Ristorante r) {
		r.setId(idRistoranti++);
		r.setNome(r.getNome());
		ristoranti.add(r);
	}
	
	public List<Ristorante> getListaRistoranti() {
		return ristoranti;
	}

}
