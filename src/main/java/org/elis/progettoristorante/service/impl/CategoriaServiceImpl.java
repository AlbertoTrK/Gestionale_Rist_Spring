package org.elis.progettoristorante.service.impl;

import java.util.List;

import org.elis.progettoristorante.model.Categoria;
import org.elis.progettoristorante.repository.CategoriaRepository;
import org.elis.progettoristorante.service.model.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServiceImpl implements CategoriaService{
	
	@Autowired
	CategoriaRepository repo;

	@Override
	public void save(String nome) {
		Categoria cat = new Categoria();
		cat.setNome(nome);
		repo.save(cat);
	}

	@Override
	public List<Categoria> findAll() {
		return repo.findAll();
	}

	@Override
	public Categoria findCategoriaById(long id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public Categoria findCategoriaByNome(String nome) {
		return repo.findCategoriaByNome(nome);
	}
}
