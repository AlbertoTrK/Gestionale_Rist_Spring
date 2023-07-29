package org.elis.progettoristorante.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elis.progettoristorante.filtri.FiltraOrdiniRequest;
import org.elis.progettoristorante.model.Ordine;
import org.elis.progettoristorante.model.RigaDOrdine;
import org.elis.progettoristorante.model.Ristorante;
import org.elis.progettoristorante.model.Stato;
import org.elis.progettoristorante.model.Utente;
import org.elis.progettoristorante.repository.OrdineRepository;
import org.elis.progettoristorante.repository.RistoranteRepository;
import org.elis.progettoristorante.repository.custom.OrdineCustomRepository;
import org.elis.progettoristorante.service.model.OrdineService;
import org.elis.progettoristorante.service.model.PiattoService;
import org.elis.progettoristorante.service.model.RigaDOrdineService;
import org.elis.progettoristorante.service.model.RistoranteService;
import org.elis.progettoristorante.service.model.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdineServiceImpl implements OrdineService{
	
	@Autowired
	private OrdineRepository repo;
	
	@Autowired
	private RistoranteService servRis;
	
	@Autowired
	private UtenteService servUt;
	
//	@Autowired
//	private RigaDOrdineService rigaOrdServ;
	
	@Autowired
	private PiattoService piattoService;
	
	@Autowired
	private OrdineCustomRepository customOrdineRepo;

	@Override
	public List<Ordine> findAll() {
		return repo.findAll();
	}

	@Override
	public Ordine findById(long id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public void save(Stato statoOrdine, long idUtente, LocalDateTime dataInvio, long idRistorante) {
		Ordine o = new Ordine();
		Ristorante r = servRis.findById(idRistorante);
		Utente u = servUt.findById(idUtente);
		o.setCliente(u);
		o.setRistorante(r);
		o.setStato(statoOrdine);
		o.setDataInvio(dataInvio);
		repo.save(o);
	}

	@Override
	public void save(Ordine o) {
		repo.save(o);
	}

	@Override
	public List<Ordine> findAllByClienteId(long idUtente) {
		return repo.findAllByClienteId(idUtente);
	}

	@Override
	public void aggiungiOrdine(long idUtente, long idRistorante, Map<Long, Integer> idProdottoQuantità) {
		Ordine o = new Ordine();
		Ristorante r = servRis.findById(idRistorante);
		Utente u = servUt.findById(idUtente);
		o.setCliente(u);
		o.setRistorante(r);

		List<RigaDOrdine> righeDOrdine = new ArrayList<>();
		for (Long idProdotto : idProdottoQuantità.keySet()) {
			int quantità = idProdottoQuantità.get(idProdotto);
			RigaDOrdine rigaOrdine = new RigaDOrdine();
			rigaOrdine.setPiatto(piattoService.findById(idProdotto));
			rigaOrdine.setOrdine(o);
			rigaOrdine.setQuantità(quantità);
//			rigaOrdine.setId();
			righeDOrdine.add(rigaOrdine);
		}
		o.setStato(Stato.RICEVUTO);
		o.setDataInvio(LocalDateTime.now());
		o.setRigheDOrdine(righeDOrdine);
		repo.save(o);
	}

	@Override
	public void save(long idUtente, long idRistorante) {
		Ordine o = new Ordine();
		Ristorante r = servRis.findById(idRistorante);
		Utente u = servUt.findById(idUtente);
		o.setCliente(u);
		o.setRistorante(r);
		o.setStato(Stato.RICEVUTO);
		o.setDataInvio(LocalDateTime.now());
		repo.save(o);
	}

	@Override
	public List<Ordine> findAllByRistoranteId(long idRistorante) {
		return repo.findAllByRistoranteId(idRistorante);
	}

	@Override
	public Ordine findByRistoranteId(long idRistorante) {
		return repo.findByRistoranteId(idRistorante);
	}

	@Override
	public void delete(Ordine o) {
		repo.delete(o);
	}

//	@Override
//	public List<Ordine> getOrdiniFiltrati(FiltraOrdiniRequest request) {
//		return customOrdineRepo.filtraOrdini(request);
//	}

	@Override
	public void aggiungiOrdine(Ordine o) {
		Ristorante r = servRis.findById(o.getRistorante().getId());
		Utente u = servUt.findById(o.getCliente().getId());
		o.setCliente(u);
		o.setRistorante(r);
		o.setStato(Stato.RICEVUTO);
		o.setDataInvio(LocalDateTime.now());
		o.setRigheDOrdine(o.getRigheDOrdine()); //??
		repo.save(o);
	}

}
