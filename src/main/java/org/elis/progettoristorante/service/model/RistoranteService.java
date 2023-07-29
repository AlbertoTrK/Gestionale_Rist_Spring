package org.elis.progettoristorante.service.model;

import java.util.List;

import org.elis.progettoristorante.model.Ristorante;

public interface RistoranteService {
	public List<Ristorante> findAll();
	public Ristorante findById(long id);
	public Ristorante findByUtenteId(long idUtente);
	public void delete(Ristorante r);
	public List<Ristorante> findAllByUtenteId(long idUtente);
}
