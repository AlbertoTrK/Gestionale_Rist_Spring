package org.elis.progettoristorante.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.elis.progettoristorante.dto.RegistraRistoratoreDTO;
import org.elis.progettoristorante.dto.RistoranteDto;
import org.elis.progettoristorante.exception.model.InvalidConstraintException;
import org.elis.progettoristorante.filtri.FiltraUtentiRequest;
import org.elis.progettoristorante.model.Ristorante;
import org.elis.progettoristorante.model.Ruolo;
import org.elis.progettoristorante.model.Utente;
import org.elis.progettoristorante.repository.UtenteRepository;
import org.elis.progettoristorante.repository.custom.UtenteCustomRepository;
import org.elis.progettoristorante.service.model.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Service
public class UtenteServiceImpl implements UtenteService {

	@Autowired
	private UtenteRepository repo;
	@Autowired
	private Validator validator;
	@Autowired
	private UtenteCustomRepository customRepoUt;

	@Override
	public Utente login(String username, String password) {
		return repo.findUtenteByUsernameAndPassword(username, password).orElse(null);
	}

//	@Override
//	public void registrazione(String username, String password, String nome, String cognome, Ruolo ruolo,
//			List<Ristorante> ris, long id) {
//		Utente u = new Utente();
//		u.setUsername(username);
//		u.setPassword(password);
//		u.setNome(nome);
//		u.setCognome(cognome);
//		u.setRuolo(ruolo);
//		u.setRistorante(ris);
//		u.setId(id);
//		repo.save(u);
//	}

	@Override
	public Utente findUtenteByUsername(String username) {
		return repo.findUtenteByUsername(username).orElse(null);
	}

//	@Override
//	public void registrazioneRistoratore(String username, String password, String nome, String cognome, Ruolo ruolo,
//			String nomeRistorante) {
//		Utente u = new Utente();
//		u.setUsername(username);
//		u.setPassword(password);
//		u.setNome(nome);
//		u.setCognome(cognome);
//		u.setRuolo(ruolo);
//		Ristorante r = new Ristorante();
//		r.setNome(nomeRistorante);
//		r.setUtente(u);
//		List<Ristorante> ristorantiRistoratore = new ArrayList<>();
//		ristorantiRistoratore.add(r);
//		u.setRistorante(ristorantiRistoratore); // setRistorante setta lista di Ristorante
//		repo.save(u);
//	}
//
//	@Override
//	public void registrazioneRistoratore2(RegistraRistoratoreDTO request) {
//		Utente u = new Utente();
//		Set<ConstraintViolation<RegistraRistoratoreDTO>> set = validator.validate(request);
//		if (!set.isEmpty()) {
//			throw new InvalidConstraintException(set.toString());
//		} else {
//			u.setUsername(request.getUsername());
//			u.setPassword(request.getPassword());
//			u.setNome(request.getNome());
//			u.setCognome(request.getCognome());
//			u.setRuolo(request.getRuolo());
//			Ristorante r = new Ristorante();
//			r.setNome(request.getNomeRistorante());
//			r.setUtente(u);
//			List<Ristorante> ristorantiRistoratore = new ArrayList<>();
//			ristorantiRistoratore.add(r);
//			u.setRistorante(ristorantiRistoratore); // setRistorante setta lista di Ristorante
//			repo.save(u);
//		}
//	}

	@Override
	public Utente findById(long id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public void delete(Utente u) {
		repo.delete(u);
	}

	@Override
	public void save(Utente u) {
		repo.save(u);
	}

	@Override
	public void registra(Utente u) {
		repo.save(u);
	}

	@Override
	public List<Utente> getUtentiFiltrati(FiltraUtentiRequest request) {
		return customRepoUt.getUtentiFiltrati(request);
	}

}
