package org.elis.progettoristorante.service.impl;

import java.util.List;

import org.elis.progettoristorante.model.Ingrediente;
import org.elis.progettoristorante.repository.IngredienteRepository;
import org.elis.progettoristorante.service.model.IngredienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredienteServiceImpl implements IngredienteService{
	
	@Autowired
	IngredienteRepository repo;

	@Override
	public void save(String nome) {
		Ingrediente ing = new Ingrediente();
		ing.setNome(nome);
		repo.save(ing);
	}

	@Override
	public List<Ingrediente> findAll() {
		return repo.findAll();
	}

	@Override
	public List<Ingrediente> findAllByIds(List<Long> ids) {
		return repo.findAllById(ids);
	}

	@Override
	public Ingrediente findById(long id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public List<Ingrediente> findAllIngredienteByPiattoId(long idPiatto) {
		return repo.findAllIngredienteByPiattoId(idPiatto);
	}

	@Override
	public Ingrediente findIngredienteByNome(String nome) {
		return repo.findIngredienteByNome(nome);
	}

	@Override
	public void delete(Ingrediente i) {
		repo.delete(i);
	}

}
