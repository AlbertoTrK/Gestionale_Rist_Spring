package org.elis.progettoristorante.service.model;

import java.util.List;

import org.elis.progettoristorante.dto.RegistraRistoratoreDTO;
import org.elis.progettoristorante.dto.RistoranteDto;
import org.elis.progettoristorante.filtri.FiltraUtentiRequest;
import org.elis.progettoristorante.model.Ristorante;
import org.elis.progettoristorante.model.Ruolo;
import org.elis.progettoristorante.model.Utente;

public interface UtenteService {
	public Utente login(String username, String password);
//	public void registrazione(String username, String password, String nome, String cognome, Ruolo ruolo, List<Ristorante> ris, long id);
//	public void registrazioneRistoratore(String username, String password, String nome, String cognome, Ruolo ruolo, String nomeRistorante);
	public Utente findUtenteByUsername(String username);
	public Utente findById(long id);
//	void registrazioneRistoratore2(RegistraRistoratoreDTO request);
	public void delete(Utente u);
	public void save(Utente u);
	public void registra(Utente u);
	List<Utente> getUtentiFiltrati(FiltraUtentiRequest request);
}
