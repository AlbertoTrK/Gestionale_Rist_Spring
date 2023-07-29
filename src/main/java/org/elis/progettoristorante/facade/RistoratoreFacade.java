package org.elis.progettoristorante.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.elis.progettoristorante.dto.CategoriaDTO;
import org.elis.progettoristorante.dto.IngredienteDTO;
import org.elis.progettoristorante.dto.OrdineDTO;
import org.elis.progettoristorante.dto.OrdineResponseDTO;
import org.elis.progettoristorante.dto.PiattoDTO;
import org.elis.progettoristorante.dto.PiattoDTOResponse;
import org.elis.progettoristorante.dto.RegistraRistoratoreDTO;
import org.elis.progettoristorante.exception.model.ListaVuotaException;
import org.elis.progettoristorante.exception.model.OggettoGiàEsistenteException;
import org.elis.progettoristorante.exception.model.OggettoNonTrovatoException;
import org.elis.progettoristorante.exception.model.OperazioneNonPossibileException;
import org.elis.progettoristorante.exception.model.UtenteGiàEsistenteException;
import org.elis.progettoristorante.mapper.CategoriaMapper;
import org.elis.progettoristorante.mapper.IngredienteMapper;
import org.elis.progettoristorante.mapper.OrdineMapper;
import org.elis.progettoristorante.mapper.PiattoMapper;
import org.elis.progettoristorante.mapper.UtenteMapper;
import org.elis.progettoristorante.model.Categoria;
import org.elis.progettoristorante.model.Ingrediente;
import org.elis.progettoristorante.model.Ordine;
import org.elis.progettoristorante.model.Piatto;
import org.elis.progettoristorante.model.RigaDOrdine;
import org.elis.progettoristorante.model.Ristorante;
import org.elis.progettoristorante.model.Stato;
import org.elis.progettoristorante.model.Utente;
import org.elis.progettoristorante.service.model.CategoriaService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RistoratoreFacade {
	
	@Autowired
	UtenteService serv;
	
	@Autowired
	RistoranteService serviceRis;
	
	@Autowired
	IngredienteService serviceIng;
	
	@Autowired
	CategoriaService serviceCat;
	
	@Autowired
	PiattoService servicePiatto;
	
	@Autowired
	OrdineService serviceOrd;
	
	@Autowired
	UtenteMapper mapperUt;
	
	@Autowired
	IngredienteMapper mapperIng;
	
	@Autowired
	CategoriaMapper mapperCat;
	
	@Autowired
	PiattoMapper mapperPiatto;
	
	@Autowired
	OrdineMapper mapperOrd;
	
	public void registraRistoratore(RegistraRistoratoreDTO request) {
		Utente u = serv.findUtenteByUsername(request.getUsername()); 
		if (u != null) {
			throw new UtenteGiàEsistenteException("Ristoratore già esistente con questa username");
		}
		else {
			serv.registra(mapperUt.toRistoratore(request));
		}
	}
	
	public IngredienteDTO aggiungiIngrediente(IngredienteDTO request) {
		Ingrediente i = serviceIng.findIngredienteByNome(request.getNome());
		if (i == null) {
			serviceIng.save(request.getNome());
			IngredienteDTO iDTO = new IngredienteDTO();
			iDTO.setNome(request.getNome());
			return iDTO;
		} else {
			throw new OggettoGiàEsistenteException("ingrediente già esistente con questo nome");
		}
	}
	
	public List<IngredienteDTO> trovaTuttiIngredienti() {
		List<Ingrediente> ingredienti = serviceIng.findAll();
		List<IngredienteDTO> resultIngredienti = mapperIng.toIngredienteDTOList(ingredienti);

		if (resultIngredienti.size() == 0) {
			throw new ListaVuotaException("nessun ingrediente trovato in database");
		} else {
			return resultIngredienti;
		}
	}
	
	public CategoriaDTO aggiungiCategoria(CategoriaDTO request) {
		Categoria c = serviceCat.findCategoriaByNome(request.getNome());
		if (c == null) {
			serviceCat.save(request.getNome());
			CategoriaDTO cDTO = new CategoriaDTO();
			cDTO.setNome(request.getNome());
			return cDTO;
		} else {
			throw new OggettoGiàEsistenteException("categoria già esistente con questo nome");
		}
	}
	
	public List<CategoriaDTO> vediTutteCategorie() {
		List<Categoria> categorie = serviceCat.findAll();
		List<CategoriaDTO> resultCategorie = mapperCat.toCategoriaDTOList(categorie);
	
		if (resultCategorie.size() == 0) {
			throw new ListaVuotaException("nessuna categoria presente in database");
		} else {
			return resultCategorie;
		}
	}
	
//	public PiattoDTO aggiungiPiatto(PiattoDTO request) {
//		Piatto p = servicePiatto.findPiattoByNome(request.getNome());
//		Ristorante r = serviceRis.findById(request.getId_ristorante());
//		Categoria c = serviceCat.findCategoriaById(request.getId_categoria());
//		List<Long> idIngredienti = request.getId_ingredienti();
//		for(Long id : idIngredienti) {
//			Ingrediente ingrediente = serviceIng.findById(id);
//			if(ingrediente == null) {
//				throw new OggettoNonTrovatoException("nessun ingrediente trovato");
//			}
//		}
//		PiattoDTO resultPiatto = new PiattoDTO();
//		if (r == null || c == null) {
//			throw new OggettoNonTrovatoException("nessun ristorante o categoria trovata");
//		} else {
//			if (p == null) {
//				resultPiatto.setNome(request.getNome());
//				resultPiatto.setDescrizione(request.getDescrizione());
//				resultPiatto.setPrezzo(request.getPrezzo());
//				resultPiatto.setId_categoria(request.getId_categoria());
//				resultPiatto.setId_ristorante(request.getId_ristorante());
//				resultPiatto.setId_ingredienti(request.getId_ingredienti());
//
//				servicePiatto.save(request.getNome(), request.getPrezzo(), request.getId_ingredienti(),
//						request.getId_categoria(), request.getId_ristorante(), request.getDescrizione());
//
//				return resultPiatto;
//			} else {
//				throw new OggettoGiàEsistenteException("piatto già esistente");
//			}
//	}
//}
	
	public PiattoDTOResponse aggiungiPiatto(PiattoDTO request) {
		Piatto p = servicePiatto.findPiattoByNome(request.getNome());
		Ristorante r = serviceRis.findById(request.getId_ristorante());
		Categoria c = serviceCat.findCategoriaById(request.getId_categoria());
		List<Long> idIngredienti = request.getId_ingredienti();
		for(Long id : idIngredienti) {
			Ingrediente ingrediente = serviceIng.findById(id);
			if(ingrediente == null) {
				throw new OggettoNonTrovatoException("nessun ingrediente trovato"); //con eccezione custom
//				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ingrediente non trovato"); //con response status
			}
		}
		PiattoDTOResponse resultPiatto = new PiattoDTOResponse();
		if (r == null || c == null) {
			throw new OggettoNonTrovatoException("nessun ristorante o categoria trovata");
		} else {
			if (p == null) {
				servicePiatto.save(request.getNome(), request.getPrezzo(), request.getId_ingredienti(),
						request.getId_categoria(), request.getId_ristorante(), request.getDescrizione());
				
				resultPiatto.setNome(request.getNome());
				resultPiatto.setDescrizione(request.getDescrizione());
				resultPiatto.setPrezzo(request.getPrezzo());
				resultPiatto.setNomeCategoria(c.getNome());
				resultPiatto.setNomeRistorante(r.getNome());
				
//				List<String> nomiIng = new ArrayList<>();
//				for(Long i : request.getId_ingredienti()) {
//					Ingrediente ing = serviceIng.findById(i);
//					String nome = ing.getNome();
//					nomiIng.add(nome);
//					
//				}
				
				List<String> nomiIng = request.getId_ingredienti().stream().map(serviceIng::findById).map(Ingrediente::getNome).toList();
				resultPiatto.setNomiIngredienti(nomiIng);

				return resultPiatto;
			} else {
				throw new OggettoGiàEsistenteException("piatto già esistente");
			}
	}
}
	
	public List<OrdineResponseDTO> trovaOrdiniRicEinElab(@PathVariable("idRistorante") long idRistorante) {
		Ristorante r = serviceRis.findById(idRistorante);
		List<Ordine> ordiniRistorante = serviceOrd.findAllByRistoranteId(idRistorante);
		List<OrdineResponseDTO> resultOrd = mapperOrd.toOrdineResponseDTOListNonEvaNonRif(ordiniRistorante);
		if(r == null) {
			throw new OggettoNonTrovatoException("nessun ristorante trovato");
		}
		if (resultOrd.size() == 0) {
			throw new ListaVuotaException("nessun ordine trovato");
		} else {
			return resultOrd;
		}
	}
	
	public OrdineResponseDTO rifiutaOrdine(long idOrdine) {
		Ordine o = serviceOrd.findById(idOrdine);
		if (idOrdine < 1) {
			throw new OggettoNonTrovatoException("id ordine errato");
		} else if (o == null) {
			throw new OggettoNonTrovatoException("nessun ordine trovato");
		} else if (o.getStato() == Stato.RIFIUTATO || o.getStato() == Stato.EVASO) {
			throw new OperazioneNonPossibileException("ordine già evaso o rifiutato");
		} else if (o.getStato() != Stato.RIFIUTATO || o.getStato() != Stato.EVASO) {
			o.setStato(Stato.RIFIUTATO);
			serviceOrd.save(o);
			OrdineResponseDTO oDTO = mapperOrd.toOrdineResponseDTO(o);
			return oDTO;
		} else {
			throw new OperazioneNonPossibileException("errore/ ristorante non esistente");
		}
	}
	
//	public OrdineDTO cambiaStatoOrdine(long idOrdine, long idRistorante) {
//		Ordine o = serviceOrd.findById(idOrdine);
//		if (o == null) {
//			throw new OggettoNonTrovatoException("nessun ordine trovato");
//		} else if (o.getStato() == Stato.RICEVUTO && o.getRistorante().getId() == idRistorante) {
//			o.setStato(Stato.IN_ELABORAZIONE);
//			serviceOrd.save(o);
//			OrdineDTO oDTO = mapperOrd.toOrdineDTO(o);
//			return oDTO;
//		} else if (o.getStato() == Stato.IN_ELABORAZIONE && o.getRistorante().getId() == idRistorante) {
//			o.setStato(Stato.EVASO);
//			serviceOrd.save(o);
//			OrdineDTO oDTO = mapperOrd.toOrdineDTO(o);
//			return oDTO;
//		} else {
//			throw new OperazioneNonPossibileException("ordine già evaso o rifiutato/ id ristorante non corretto/ ristorante non esistente");
//		}
//	}
	
	public OrdineResponseDTO cambiaStatoOrdine(long idOrdine) {
		Ordine o = serviceOrd.findById(idOrdine);
		if (o == null) {
			throw new OggettoNonTrovatoException("nessun ordine trovato");
		} else if (o.getStato() == Stato.RICEVUTO) {
			o.setStato(Stato.IN_ELABORAZIONE);
			serviceOrd.save(o);
			OrdineResponseDTO oDTO = mapperOrd.toOrdineResponseDTO(o);
			return oDTO;
		} else if (o.getStato() == Stato.IN_ELABORAZIONE) {
			o.setStato(Stato.EVASO);
			serviceOrd.save(o);
			OrdineResponseDTO oDTO = mapperOrd.toOrdineResponseDTO(o);
			return oDTO;
		} else {
			throw new OperazioneNonPossibileException("ordine già evaso o rifiutato");
		}
	}
	
}
