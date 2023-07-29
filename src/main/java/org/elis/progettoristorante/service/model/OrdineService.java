package org.elis.progettoristorante.service.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.elis.progettoristorante.filtri.FiltraOrdiniRequest;
import org.elis.progettoristorante.model.Ordine;
import org.elis.progettoristorante.model.Stato;

public interface OrdineService {
	public List<Ordine> findAll();
	public Ordine findById(long id);
	public void save(Stato statoOrdine, long idUtente, LocalDateTime dataInvio, long idRistorante);
	public void save(long idUtente, long idRistorante);
	public void aggiungiOrdine(long idUtente, long idRistorante, Map<Long, Integer> idProdottoQuantità);
	public void save(Ordine o);
	public List<Ordine> findAllByClienteId(long idUtente);
	public List<Ordine> findAllByRistoranteId(long idRistorante);
	public Ordine findByRistoranteId(long idRistorante);
	public void delete(Ordine o);
//	List<Ordine> getOrdiniFiltrati(FiltraOrdiniRequest request);
	public void aggiungiOrdine(Ordine o); //per aggiungi con facade
}
