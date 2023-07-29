package org.elis.progettoristorante.db;

import java.util.ArrayList;
import java.util.List;

import org.elis.progettoristorante.model.Utente;

public class DatabaseSingleton {
	
	private static final DatabaseSingleton instance = new DatabaseSingleton();
	
	private DatabaseSingleton(){
		utentiAmmessi = new ArrayList<>();
	}
	
	public static DatabaseSingleton getInstance() {
		return instance;
	}
	
	private List<Utente> utentiAmmessi;
	private long idUtentiAmmessi = 1;
	
	public void aggiungiUtente (Utente u) {
		u.setId(idUtentiAmmessi++);
		u.setNome(u.getNome());
		u.setCognome(u.getCognome());
		u.setUsername(u.getUsername());
		u.setPassword(u.getPassword());
		u.setRistorante(u.getRistorante());
		u.setRuolo(u.getRuolo());
		utentiAmmessi.add(u);
	}
	
	public Utente trovaUtente(String username, String password) {
		for(Utente u :utentiAmmessi) {
			if(u.getUsername().equals(username) && u.getPassword().equals(password)){
				return u;
			}
		}
		return null;
		
	}
	
	public Utente trovaUtentePerUsername(String username) {
		for(Utente u :utentiAmmessi) {
			if(u.getUsername().equals(username)){
				return u;
			}
		}
		return null;
		
	}
}
