package org.elis.progettoristorante.service.model;

import java.util.List;

import org.elis.progettoristorante.model.Categoria;

public interface CategoriaService {
	public void save(String nome);
	public List<Categoria> findAll();
	public Categoria findCategoriaById(long id);
	public Categoria findCategoriaByNome(String nome);
}
