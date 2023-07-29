package org.elis.progettoristorante.facade;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.elis.progettoristorante.dto.AggiungiAdOrdineDTO;
import org.elis.progettoristorante.dto.AggiungiOrdineDTO;
import org.elis.progettoristorante.dto.OrdineResponseDTO;
import org.elis.progettoristorante.dto.IngredienteDTO;
import org.elis.progettoristorante.dto.LoginDTO;
import org.elis.progettoristorante.dto.ModificaUtenteDTO;
import org.elis.progettoristorante.dto.OrdineDTO;
import org.elis.progettoristorante.dto.PiattoDTO;
import org.elis.progettoristorante.dto.PiattoDTOResponse;
import org.elis.progettoristorante.dto.RegistraUtenteDTO;
import org.elis.progettoristorante.dto.RistoranteDto;
import org.elis.progettoristorante.dto.UtenteDTO;
import org.elis.progettoristorante.exception.model.ListaVuotaException;
import org.elis.progettoristorante.exception.model.OggettoNonTrovatoException;
import org.elis.progettoristorante.exception.model.OperazioneNonPossibileException;
import org.elis.progettoristorante.exception.model.UtenteGiàEsistenteException;
import org.elis.progettoristorante.exception.model.UtenteNonTrovatoException;
import org.elis.progettoristorante.filtri.FiltraUtentiRequest;
import org.elis.progettoristorante.filtri.FiltraPiattoRequest;
import org.elis.progettoristorante.mapper.IngredienteMapper;
import org.elis.progettoristorante.mapper.OrdineMapper;
import org.elis.progettoristorante.mapper.PiattoMapper;
import org.elis.progettoristorante.mapper.RistoranteMapper;
import org.elis.progettoristorante.mapper.UtenteMapper;
import org.elis.progettoristorante.model.Ingrediente;
import org.elis.progettoristorante.model.Ordine;
import org.elis.progettoristorante.model.Piatto;
import org.elis.progettoristorante.model.RigaDOrdine;
import org.elis.progettoristorante.model.Ristorante;
import org.elis.progettoristorante.model.Stato;
import org.elis.progettoristorante.model.Utente;
import org.elis.progettoristorante.repository.RigaDOrdineRepository;
import org.elis.progettoristorante.service.model.IngredienteService;
import org.elis.progettoristorante.service.model.OrdineService;
import org.elis.progettoristorante.service.model.PiattoService;
import org.elis.progettoristorante.service.model.RistoranteService;
import org.elis.progettoristorante.service.model.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

@Service
public class UtenteFacade {
	
	@Autowired
	UtenteService serv;
	
	@Autowired
	RistoranteService servRis;
	
	@Autowired
	PiattoService servPiatto;
	
	@Autowired
	IngredienteService servIng;
	
	@Autowired
	OrdineService servOrd;
	
	@Autowired
	RigaDOrdineRepository repoRiga;
	
	@Autowired
	UtenteMapper mapperUt;
	
	@Autowired
	RistoranteMapper mapperRist;
	
	@Autowired
	PiattoMapper mapperPiatto;
	
	@Autowired
	IngredienteMapper mapperIng;
	
	@Autowired
	OrdineMapper mapperOrd;
	
	public void registraUtente(RegistraUtenteDTO request) {
		Utente u = serv.findUtenteByUsername(request.getUsername()); 
		if (u != null) {
			throw new UtenteGiàEsistenteException("Username già esistente");
		}
		else {
			serv.registra(mapperUt.toUtente(request));
		}
	}
	
	public UtenteDTO login(LoginDTO request) {
		Utente u = serv.login(request.getUsername(), request.getPassword());
		if (u == null) {
			throw new UtenteNonTrovatoException("Nessun utente con queste credenziali");
		} else {
			return mapperUt.toUtenteDTO(serv.login(request.getUsername(), request.getPassword()));
		}
	}
	
	public Utente loginToken(LoginDTO request) {
		Utente u = serv.login(request.getUsername(), request.getPassword());
		if (u == null) {
			throw new UtenteNonTrovatoException("Nessun utente con queste credenziali");
		} else {
			return serv.login(request.getUsername(), request.getPassword());
		}
	}
	
	public List<UtenteDTO> filtraUtenti(FiltraUtentiRequest request){
		if(serv.getUtentiFiltrati(request).isEmpty()) {
			throw new ListaVuotaException("lista utenti filtrati con questi criteri vuota");
		}
		else {
			return mapperUt.toUtenteDTOList(serv.getUtentiFiltrati(request));
		}
	}
	
	public List<PiattoDTOResponse> filtraPiatti(FiltraPiattoRequest request) {
		if (servPiatto.getPiattiFiltrati(request).isEmpty()) {
			throw new ListaVuotaException("lista piatti filtrati con questi criteri vuota");
		} else {
			return mapperPiatto.toPiattoDTOResponseList(servPiatto.getPiattiFiltrati(request));
		}
	}
	
	public List<RistoranteDto> findAllRistoranti() {
		List<Ristorante> ristoranti = servRis.findAll();
		if (ristoranti.isEmpty()) {
			throw new ListaVuotaException("nessun ristorante in database");
		} else {
			return mapperRist.toRistoranteDTOList(ristoranti);
		}
	}
	
	public List<PiattoDTOResponse> visualizzaPiattiRistorante(long idRistorante){
		Ristorante r = servRis.findById(idRistorante);
		if(r == null) {
			throw new OggettoNonTrovatoException("nessun ristorante trovato");
		}
		
		List<Piatto> piattiRistorante = servPiatto.findAllByRistoranteId(idRistorante);
		List<PiattoDTOResponse> resultPiattiRistorante = mapperPiatto.toPiattoDTOResponseList(piattiRistorante);
		
		if (resultPiattiRistorante.isEmpty()) {
			throw new ListaVuotaException("nessun piatto esistente per questo ristorante");
		} else {
			return resultPiattiRistorante;
		}
	}
	
	public List<PiattoDTOResponse> visualizzaPiattiConIngrediente(long idIngrediente){
		Ingrediente i = servIng.findById(idIngrediente);
		if(i == null) {
			throw new OggettoNonTrovatoException("nessun ingrediente trovato");
		}
		List<Piatto> piattiConIngr = servPiatto.findAllPiattoByIngredienteId(idIngrediente);
		List<PiattoDTOResponse> resultPiattiConIng = mapperPiatto.toPiattoDTOResponseList(piattiConIngr);
		
		if (resultPiattiConIng.isEmpty()) {
			throw new ListaVuotaException("nessun piatto presente con questo ingrediente");
		} else {
			return resultPiattiConIng;
		}
	}
	
	public List<IngredienteDTO> visualizzaIngredientiPiatto(long idPiatto){
		List<Ingrediente> ingredientiPiatto = servIng.findAllIngredienteByPiattoId(idPiatto);
		List<IngredienteDTO> resultIngPiatto = mapperIng.toIngredienteDTOList(ingredientiPiatto);
		
		if (resultIngPiatto.isEmpty()) {
			throw new ListaVuotaException("nessun piatto esistente");
		} else {
			return resultIngPiatto;
		}
	}
	
	public List<OrdineResponseDTO> visualizzaOrdiniEvasiERif(long idUtente) {
		List<Ordine> ordiniEvasiERif = servOrd.findAllByClienteId(idUtente);
		List<OrdineResponseDTO> ordiniDTO = mapperOrd.toOrdineResponseDTOListEvaERif(ordiniEvasiERif);
		Utente u = serv.findById(idUtente);
		if (u == null) {
			throw new OggettoNonTrovatoException("nessun utente esistente");
		}

		else if (ordiniDTO.isEmpty()) {
			throw new ListaVuotaException("nessun ordine di questo utente");
		} else {
			return ordiniDTO;
		}
	}
	
	public List<OrdineResponseDTO> storicoOrdiniNonEvaNonRif(long idUtente) {
		List<Ordine> ordiniUtente = servOrd.findAllByClienteId(idUtente);
		List<OrdineResponseDTO> resultOrdiniUtente = mapperOrd.toOrdineResponseDTOListNonEvaNonRif(ordiniUtente);
		Utente u = serv.findById(idUtente);
		if (u == null) {
			throw new OggettoNonTrovatoException("nessun utente esistente");
		}
		if (resultOrdiniUtente.isEmpty()) {
			throw new ListaVuotaException("nessun ordine di questo utente");
		} else {
			return resultOrdiniUtente;
		}
	}
	
	
	
//	public AggiungiOrdineResponseDTO aggiungiOrdine(AggiungiOrdineDTO request){
//		Utente u = serv.findById(request.getIdUtente());
//		Ristorante r = servRis.findById(request.getIdRistorante());
//		if (u == null) {
//			throw new OggettoNonTrovatoException("nessun utente trovato");
//		}
//		else if(r == null) {
//			throw new OggettoNonTrovatoException("nessun ristorante trovato");
//		}
//		else {
//			servOrd.aggiungiOrdine(request.getIdUtente(), request.getIdRistorante(), request.getIdProdottoQuantità());
//			AggiungiOrdineResponseDTO aorDTO = new AggiungiOrdineResponseDTO();
//			// incompleto
//			return aorDTO;
//		}
//	}
	
	public OrdineResponseDTO aggiungiOrdine(AggiungiOrdineDTO request) {
		Utente u = serv.findById(request.getIdUtente());
		Ristorante r = servRis.findById(request.getIdRistorante());
		if (u == null) {
			throw new OggettoNonTrovatoException("nessun utente trovato");
		}
		else if(r == null) {
			throw new OggettoNonTrovatoException("nessun ristorante trovato");
		}
		else { //findPiattoByRistoranteId
			for (Long idProdotto : request.getIdProdottoQuantità().keySet()) {
				Piatto p = servPiatto.findById(idProdotto);
				if(p == null) {
					throw new OggettoNonTrovatoException("piatto non trovato");
				}
				else if(p.getRistorante().getId() != r.getId()) {
					throw new OperazioneNonPossibileException("Piatto non di questo ristorante");
				}
			}
//			servOrd.aggiungiOrdine(request.getIdUtente(), request.getIdRistorante(), request.getIdProdottoQuantità());
			Ordine o = new Ordine();
			o.setCliente(u);
			o.setRistorante(r);
			o.setDataInvio(LocalDateTime.now());
			o.setStato(Stato.RICEVUTO);
			List<RigaDOrdine> righeDOrdine = new ArrayList<>();
			for (Long idProdotto : request.getIdProdottoQuantità().keySet()) {
				int quantità = request.getIdProdottoQuantità().get(idProdotto);
				RigaDOrdine rigaOrdine = new RigaDOrdine();
				//aggiunge anche piatto non associato a ristorante //non più con else if e throw operazione non possibile exc
				rigaOrdine.setPiatto(servPiatto.findById(idProdotto));
				rigaOrdine.setOrdine(o);
				rigaOrdine.setQuantità(quantità);
				//id righe ordine torna o??
				righeDOrdine.add(rigaOrdine);
			}
			o.setRigheDOrdine(righeDOrdine);
			servOrd.save(o);
			return mapperOrd.toOrdineResponseDTO(o);
		}
	}
	
	public OrdineResponseDTO aggiungiAdOrdine(AggiungiAdOrdineDTO request) {
		Ordine o = servOrd.findById(request.getIdOrdine());
		Ristorante r = servRis.findById(request.getIdRistorante());
		if (o == null) {
			throw new OggettoNonTrovatoException("ordine inesistente");
		} else {
			for (Long idProdotto : request.getIdProdottoQuantità().keySet()) {
				Piatto p = servPiatto.findById(idProdotto);
				if (p == null) {
					throw new OggettoNonTrovatoException("piatto non trovato");
				}
				else if(p.getRistorante().getId() != r.getId()) {
					throw new OperazioneNonPossibileException("Piatto non di questo ristorante");
				}
			}
			List<RigaDOrdine> righeDOrdine = new ArrayList<>();
			for (Long idProdotto : request.getIdProdottoQuantità().keySet()) {
				int quantità = request.getIdProdottoQuantità().get(idProdotto);
				RigaDOrdine rigaOrdine = new RigaDOrdine();
				// aggiunge anche piatto non associato a ristorante //non più con else if e throw operazione non possibile exc
				rigaOrdine.setPiatto(servPiatto.findById(idProdotto));
				rigaOrdine.setOrdine(o);
				rigaOrdine.setQuantità(quantità);
				righeDOrdine.add(rigaOrdine);
			}
//			o.setRigheDOrdine(righeDOrdine);
			repoRiga.saveAll(righeDOrdine);
//			o.getRigheDOrdine().addAll(righeDOrdine); 
			return mapperOrd.toOrdineResponseDTO(o);
		}
	}
	
	public ModificaUtenteDTO modificaUsernameEPassword(ModificaUtenteDTO request) {
		Utente u = serv.findUtenteByUsername(request.getUsername());
		ModificaUtenteDTO result = new ModificaUtenteDTO();
		if (u == null) {
			throw new UtenteNonTrovatoException("nessun utente trovato");
		}

		if (request.getPassword().equals(u.getPassword())) {
			u.setUsername(request.getNuovaUsername());
			u.setPassword(request.getNuovaPassword());
			serv.save(u);
			result.setUsername(request.getNuovaUsername());
			result.setPassword(request.getPassword());
			result.setNuovaPassword(request.getNuovaPassword());
			result.setNuovaUsername(request.getNuovaUsername());
			return result;
		} else {
			throw new OperazioneNonPossibileException("impossibile effettuare questa operazione");
		}

	}
	
}
