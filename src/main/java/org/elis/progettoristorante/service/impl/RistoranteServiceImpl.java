package org.elis.progettoristorante.service.impl;

import java.util.List;

import org.elis.progettoristorante.model.Ristorante;
import org.elis.progettoristorante.repository.RistoranteRepository;
import org.elis.progettoristorante.service.model.RistoranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RistoranteServiceImpl implements RistoranteService{
	
	@Autowired
	RistoranteRepository repo;

	@Override
	public List<Ristorante> findAll() {
		return repo.findAll();
	}

	@Override
	public Ristorante findById(long id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public Ristorante findByUtenteId(long idUtente) {
		// TODO Auto-generated method stub
		return repo.findByUtenteId(idUtente);
	}

	@Override
	public void delete(Ristorante r) {
		repo.delete(r);
	}

	@Override
	public List<Ristorante> findAllByUtenteId(long idUtente) {
		return repo.findAllByUtenteId(idUtente);
	}

}
