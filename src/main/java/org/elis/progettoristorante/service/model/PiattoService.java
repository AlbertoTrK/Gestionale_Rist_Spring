package org.elis.progettoristorante.service.model;

import java.util.List;

import org.elis.progettoristorante.filtri.FiltraPiattoRequest;
import org.elis.progettoristorante.model.Ingrediente;
import org.elis.progettoristorante.model.Piatto;

public interface PiattoService {
	public void save(String nomePiatto, double prezzo, List<Long> idIngredienti, long idCategoria, long idRistorante, String descrizione);
	public List<Piatto> findAllByRistoranteId(long idRistorante);
	public List<Piatto> findAllPiattoByIngredienteId(long idIngrediente);
	public Piatto findPiattoByNome(String nome);
	public Piatto findById(long idPiatto);
	List<Piatto> getPiattiFiltrati(FiltraPiattoRequest request);
	public Piatto findPiattoByRistorante_id(long idRistorante);
}
