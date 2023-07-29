package org.elis.progettoristorante.facade;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.elis.progettoristorante.dto.OrdineDTO;
import org.elis.progettoristorante.dto.OrdineResponseDTO;
import org.elis.progettoristorante.dto.RistoranteDto;
import org.elis.progettoristorante.dto.UtenteDTO;
import org.elis.progettoristorante.dto.FatturatoDTO;
import org.elis.progettoristorante.dto.FatturatoDiMeseDTO;
import org.elis.progettoristorante.dto.FatturatoPerMeseDTO;
import org.elis.progettoristorante.exception.model.ListaVuotaException;
import org.elis.progettoristorante.exception.model.OggettoNonTrovatoException;
import org.elis.progettoristorante.exception.model.UtenteNonTrovatoException;
import org.elis.progettoristorante.filtri.FiltraOrdiniRequest;
import org.elis.progettoristorante.mapper.OrdineMapper;
import org.elis.progettoristorante.mapper.RistoranteMapper;
import org.elis.progettoristorante.model.Ordine;
import org.elis.progettoristorante.model.RigaDOrdine;
import org.elis.progettoristorante.model.Ristorante;
import org.elis.progettoristorante.model.Stato;
import org.elis.progettoristorante.model.Utente;
import java.util.Map;
import java.util.HashMap;
import org.elis.progettoristorante.service.model.OrdineService;
import org.elis.progettoristorante.service.model.RistoranteService;
import org.elis.progettoristorante.service.model.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

@Service
public class AdminFacade {
	
	@Autowired
	OrdineMapper mapperOrd;
	
	@Autowired
	RistoranteMapper mapperRis;
	
	@Autowired
	OrdineService serviceOrd;
	
	@Autowired
	RistoranteService servRis;
	
	@Autowired
	UtenteService serv;
	
//	public List<OrdineResponseDTO> filtraOrdini(FiltraOrdiniRequest request) {
//		if (serviceOrd.getOrdiniFiltrati(request).isEmpty()) {
//			throw new ListaVuotaException("nessun risultato con questi criteri");
//		} else {
//			return mapperOrd.toOrdineResponseDTOList(serviceOrd.getOrdiniFiltrati(request));
//		}
//	}
	
	public List<RistoranteDto> vediTuttiRistoranti() {
		List<Ristorante> ristoranti = servRis.findAll();
		List<RistoranteDto> resultRist = mapperRis.toRistoranteDTOList(ristoranti);
		
		if(resultRist.isEmpty()) {
			throw new ListaVuotaException("nessun ristorante in database");
		}
		else {
			return resultRist;
		}
	}
	
	public List<OrdineResponseDTO> storicoOrdini(long idUtente) {
		List<Ordine> ordiniUtente = serviceOrd.findAllByClienteId(idUtente);
		List<OrdineResponseDTO> resultOrdiniUtente = mapperOrd.toOrdineResponseDTOList(ordiniUtente);
		Utente u = serv.findById(idUtente);
		if (u == null) {
			throw new UtenteNonTrovatoException("nessun utente trovato");
		} else {
			if (resultOrdiniUtente.isEmpty()) {
				throw new ListaVuotaException("nessun ordine per questo utente");
			} else {
				return resultOrdiniUtente;
			}
		}
	}
	
//	public double vediFatturato(long idRistorante) {
//		Ristorante ristorante = servRis.findById(idRistorante);
//
//		if (ristorante == null) {
//			throw new OggettoNonTrovatoException("nessun ristorante trovato");
//		}
//		LocalDateTime ultimiTrentaGiorni = LocalDateTime.now().minusDays(30);
//		double fatturato = 0;
//
//		for (Ordine ordine : ristorante.getOrdine()) {
//			if (ordine.getDataInvio().isAfter(ultimiTrentaGiorni)) {
//				for (RigaDOrdine riga : ordine.getRigheDOrdine()) {
//					double conto = riga.getPiatto().getPrezzo() * riga.getQuantità();
//					fatturato += conto;
//				}
//			}
//		}
//		return fatturato;
//	}
	
	public FatturatoDTO vediFatturato(long idRistorante) {
		Ristorante ristorante = servRis.findById(idRistorante);

		if (ristorante == null) {
			throw new OggettoNonTrovatoException("nessun ristorante trovato");
		}
		LocalDateTime ultimiTrentaGiorni = LocalDateTime.now().minusDays(30);
		double fatturato = 0;

		for (Ordine ordine : ristorante.getOrdine()) {
			if (ordine.getDataInvio().isAfter(ultimiTrentaGiorni)) {
				for (RigaDOrdine riga : ordine.getRigheDOrdine()) {
					double conto = riga.getPiatto().getPrezzo() * riga.getQuantità();
					fatturato += conto;
				}
			}
		}
		FatturatoDTO fDTO = new FatturatoDTO();
		fDTO.setFatturato(fatturato);
		fDTO.setNomeRistorante(ristorante.getNome());
		return fDTO;
	}
	
	public FatturatoPerMeseDTO fatturatoPerMese(long idRistorante) {
		Ristorante ristorante = servRis.findById(idRistorante);

		if (ristorante == null) {
			throw new OggettoNonTrovatoException("nessun ristorante trovato");
		}
		
		List<Ordine> ordini = serviceOrd.findAllByRistoranteId(idRistorante);
		List<Ordine> [] ordiniPerMese = new List[12];
		
		LocalDateTime ultimoAnno = LocalDateTime.now().minusDays(365);
		List<FatturatoDiMeseDTO> fatturatoDiMese = new ArrayList<>();
		
		for(int i = 0; i < 12; i++) {
			Month mese = Month.of(i + 1);
			ordiniPerMese[i] = ordini.stream().filter(ordine -> ordine.getDataInvio().getMonth() == mese).toList();
			double fatturatoMese = 0;
			
			for(Ordine o : ordiniPerMese[i]) {
				if(o.getDataInvio().isAfter(ultimoAnno) && o.getStato() == Stato.EVASO) {
					for(RigaDOrdine riga : o.getRigheDOrdine()) {
						double conto = riga.getPiatto().getPrezzo() * riga.getQuantità();
						fatturatoMese += conto;
					}
				}
			}
			FatturatoDiMeseDTO fDTO = new FatturatoDiMeseDTO();
			fDTO.setFatturato(fatturatoMese);
			fDTO.setMese(mese);
			fatturatoDiMese.add(fDTO);
		}
		
		FatturatoPerMeseDTO fpmDTO = new FatturatoPerMeseDTO();
		fpmDTO.setNomeRistorante(ristorante.getNome());
		fpmDTO.setFatturatoPerMese(fatturatoDiMese);
		return fpmDTO;
	}
	
	public List<OrdineResponseDTO> findAllOrdini() {
		List<Ordine> ordini = serviceOrd.findAll();
		List<OrdineResponseDTO> resultOrd = mapperOrd.toOrdineResponseDTOList(ordini);

		if (resultOrd.size() == 0) {
			throw new ListaVuotaException("nessun ordine esistente");
		} else {
			return resultOrd;
		}
	}
	
	public String eliminaUtente(UtenteDTO request){
		Utente u = serv.findById(request.getId());
		List<Ristorante> ristorantiUtente = servRis.findAllByUtenteId(request.getId());
		List<Ordine> ordiniUtente = serviceOrd.findAllByClienteId(request.getId());
		if(u == null) {
			throw new UtenteNonTrovatoException("Utente o ristorante associato non esistente");
		}
		else {
			for(Ordine o : ordiniUtente) {
				serviceOrd.delete(o); //fare con set cancellato
			}
			for(Ristorante r : ristorantiUtente) {
				servRis.delete(r); //fare con set cancellato
			}
			serv.delete(u); //fare con set cancellato
			return "Utente cancellato con successo";
		}
	}
}
