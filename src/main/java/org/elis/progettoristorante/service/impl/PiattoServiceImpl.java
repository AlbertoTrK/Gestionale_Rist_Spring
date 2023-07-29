package org.elis.progettoristorante.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.elis.progettoristorante.service.model.CategoriaService;
import org.elis.progettoristorante.service.model.IngredienteService;
import org.elis.progettoristorante.service.model.PiattoService;
import org.elis.progettoristorante.service.model.RistoranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.elis.progettoristorante.filtri.FiltraPiattoRequest;
import org.elis.progettoristorante.model.Categoria;
import org.elis.progettoristorante.model.Ingrediente;
import org.elis.progettoristorante.model.Piatto;
import org.elis.progettoristorante.model.Ristorante;
import org.elis.progettoristorante.repository.PiattoRepository;
import org.elis.progettoristorante.repository.custom.PiattoCustomRepository;

@Service
public class PiattoServiceImpl implements PiattoService{
	
	@Autowired
	PiattoRepository repo;
	
	@Autowired
	CategoriaService serviceCat;
	
	@Autowired
	IngredienteService serviceIng;
	
	@Autowired
	RistoranteService serviceRis;
	
	@Autowired
	PiattoCustomRepository piattoCustRepo;

	@Override
	public List<Piatto> findAllByRistoranteId(long idRistorante) {
		return repo.findAllPiattoByRistorante_id(idRistorante);
	}

	@Override
	public void save(String nomePiatto, double prezzo, List<Long> idIngredienti, long idCategoria, long idRistorante, String descrizione) {
		Piatto p = new Piatto();
		p.setNome(nomePiatto);
		p.setPrezzo(prezzo);
		Categoria c = serviceCat.findCategoriaById(idCategoria);
//		List<Ingrediente> ingredienti = serviceIng.findAllByIds(idIngredienti);
		
		List<Ingrediente> ingredienti = new ArrayList<>();
		for(Long idIngrediente : idIngredienti) {
			Ingrediente i = serviceIng.findById(idIngrediente);
			ingredienti.add(i);
		}
		
		p.setIngredienti_piatto(ingredienti);
		p.setCategoria(c);
		p.setDescrizione(descrizione);
		Ristorante r = serviceRis.findById(idRistorante);
		p.setRistorante(r);	//associare ristorante altrimenti errore
		repo.save(p);
	}

	@Override
	public List<Piatto> findAllPiattoByIngredienteId(long idIngrediente) {
		return repo.findAllPiattoByIngredienteId(idIngrediente);
	}

	@Override
	public Piatto findPiattoByNome(String nome) {
		return repo.findPiattoByNome(nome);
	}

	@Override
	public Piatto findById(long idPiatto) {
		return repo.findById(idPiatto).orElse(null);
	}

	@Override
	public List<Piatto> getPiattiFiltrati(FiltraPiattoRequest request) {
		// TODO Auto-generated method stub
		return piattoCustRepo.getPiattiFiltrati(request);
	}

	@Override
	public Piatto findPiattoByRistorante_id(long idRistorante) {
		return repo.findPiattoByRistorante_id(idRistorante);
	}

}
