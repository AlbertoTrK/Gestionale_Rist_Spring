package org.elis.progettoristorante.service.impl;

import org.elis.progettoristorante.model.RigaDOrdine;
import org.elis.progettoristorante.repository.OrdineRepository;
import org.elis.progettoristorante.repository.RigaDOrdineRepository;
import org.elis.progettoristorante.service.model.OrdineService;
import org.elis.progettoristorante.service.model.PiattoService;
import org.elis.progettoristorante.service.model.RigaDOrdineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RigaDOrdineImpl implements RigaDOrdineService{
	
	@Autowired
	RigaDOrdineRepository repoRiga;
	
//	@Autowired
//	OrdineService ordineService; //se decommentato causa cycle error
	
	@Autowired
	OrdineRepository ordrepo; //inserito dopo 
	
	@Autowired
	PiattoService piattoService;
	

	@Override
	public void save(long idPiatto, long idOrdine, int quantità) {
		RigaDOrdine riga = new RigaDOrdine();
		riga.setOrdine(ordrepo.findById(idOrdine).orElse(null));
		riga.setPiatto(piattoService.findById(idPiatto));
		riga.setQuantità(quantità);
		repoRiga.save(riga);
	}
	
	//commentare da ordineservice a qui per far avviare

	@Override
	public RigaDOrdine findById(Long id) {
		return repoRiga.findById(id).orElse(null);
	}

}
