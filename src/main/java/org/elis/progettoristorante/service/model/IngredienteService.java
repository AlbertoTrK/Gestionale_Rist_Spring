package org.elis.progettoristorante.service.model;

import java.util.List;

import org.elis.progettoristorante.model.Ingrediente;
import org.elis.progettoristorante.model.Ristorante;

public interface IngredienteService {
	public void save(String nome);
	public List<Ingrediente> findAll();
	public List<Ingrediente> findAllByIds(List <Long> ids);
	public Ingrediente findById(long id);

	public List<Ingrediente> findAllIngredienteByPiattoId(long idPiatto);
	public Ingrediente findIngredienteByNome(String nome);
	public void delete(Ingrediente i);
}
