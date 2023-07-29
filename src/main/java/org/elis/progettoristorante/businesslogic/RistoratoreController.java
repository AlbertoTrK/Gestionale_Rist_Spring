package org.elis.progettoristorante.businesslogic;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.elis.progettoristorante.dto.CategoriaDTO;
import org.elis.progettoristorante.dto.ErrorMsgDTO;
import org.elis.progettoristorante.dto.IngredienteDTO;
import org.elis.progettoristorante.dto.OrdineDTO;
import org.elis.progettoristorante.dto.OrdineResponseDTO;
import org.elis.progettoristorante.dto.PiattoDTO;
import org.elis.progettoristorante.dto.PiattoDTOResponse;
import org.elis.progettoristorante.dto.RegistraRistoratoreDTO;
import org.elis.progettoristorante.dto.RegistraUtenteDTO;
import org.elis.progettoristorante.dto.swagger.BadRequestDTO;
import org.elis.progettoristorante.facade.RistoratoreFacade;
import org.elis.progettoristorante.facade.UtenteFacade;
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
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Ristoratore Controller: metodi accessibili solo all'utente che ha il ruolo di ristoratore", 
description = "metodi accessibili solo a chi supera l'autenticazione come ristoratore")
public class RistoratoreController {

	@Autowired
	IngredienteService serviceIng;

	@Autowired
	CategoriaService serviceCat;

	@Autowired
	PiattoService servicePiatto;

	@Autowired
	UtenteService serv;

	@Autowired
	OrdineService serviceOrd;
	
	@Autowired
	RistoranteService serviceRis;
	
	@Autowired
	RistoratoreFacade facadeRist;
	
	@Operation(summary = "inserimento nuovo ingrediente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "202", description = "ingrediente aggiunto con successo", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = IngredienteDTO.class))),
			@ApiResponse(responseCode = "400", description = "i dati inseriti non sono conformi ai pattern richiesti ", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BadRequestDTO.class))),
			@ApiResponse(responseCode = "406", description = "ingrediente con questo nome già esistente ", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class)))
			
	})
	@PostMapping("/ristoratore/aggiungiIngrediente")
	public ResponseEntity<IngredienteDTO> aggiungiIngrediente(@Valid @RequestBody IngredienteDTO request) {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(facadeRist.aggiungiIngrediente(request));
	}
	
	@Operation(summary = "recupero lista ingredienti presenti in database")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "202", description = "lista ingredienti richiesta recuperata con successo", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = IngredienteDTO.class))),
			@ApiResponse(responseCode = "204", description = "nessun ingrediente presente", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class)))
			
	})
	@GetMapping("/ristoratore/visualizzaIngredienti")
	public ResponseEntity<List<IngredienteDTO>> findAllIngredienti() {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(facadeRist.trovaTuttiIngredienti());
	}
	
	@Operation(summary = "inserimento nuova categoria")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "categoria aggiunta con successo", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CategoriaDTO.class))),
			@ApiResponse(responseCode = "400", description = "i dati inseriti non sono conformi ai pattern richiesti ", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BadRequestDTO.class))),
			@ApiResponse(responseCode = "406", description = "categoria con questo nome già esistente ", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class)))
			
	})
	@PostMapping("/ristoratore/aggiungiCategoria")
	public ResponseEntity<CategoriaDTO> aggiungiCategoria(@Valid @RequestBody CategoriaDTO request) {
		return ResponseEntity.status(HttpStatus.OK).body(facadeRist.aggiungiCategoria(request));
	}
	
	@Operation(summary = "inserimento nuovo piatto")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "piatto aggiunto con successo", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PiattoDTOResponse.class))),
			@ApiResponse(responseCode = "400", description = "i dati inseriti non sono conformi ai pattern richiesti ", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BadRequestDTO.class))),
			@ApiResponse(responseCode = "406", description = "piatto con questo nome già esistente ", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class))),
			@ApiResponse(responseCode = "404", description = "ingrediente, ristorante o categoria nella richiesta inesistente", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class)))
			
	})
	@PostMapping("/ristoratore/aggiungiPiatto")
	public ResponseEntity<PiattoDTOResponse> aggiungiPiatto(@Valid @RequestBody PiattoDTO request) {
		return ResponseEntity.status(HttpStatus.OK).body(facadeRist.aggiungiPiatto(request));
	}
	
	@Operation(summary = "recupero lista categorie presenti in database")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "lista categoria richiesta recuperata con successo", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = IngredienteDTO.class))),
			@ApiResponse(responseCode = "204", description = "nessuna categoria presente", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class)))
			
	})
	@GetMapping("/ristoratore/visualizzaCategorie")
	public ResponseEntity<List<CategoriaDTO>> findAllCategorie() {
		return ResponseEntity.status(HttpStatus.OK).body(facadeRist.vediTutteCategorie());
	}
	
	@Operation(summary = "recupero ordini ricevuti e in elaborazione del ristorante specificato")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "lista recuperata con successo", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = OrdineResponseDTO.class))),
			@ApiResponse(responseCode = "204", description = "nessun ordine di queste due tipologie esistente per questo ristorante", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BadRequestDTO.class))),
			@ApiResponse(responseCode = "404", description = "ristorante non esistente ", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class)))
			
	})
	@GetMapping("/ristoratore/visualizzaOrdiniRicevutiEinElaborazione/{idRistorante}")
	public ResponseEntity<List<OrdineResponseDTO>> trovaOrdiniRicEinElab(@PathVariable("idRistorante") long idRistorante) {
		return ResponseEntity.status(HttpStatus.OK).body(facadeRist.trovaOrdiniRicEinElab(idRistorante)); 
	}
	
	@Operation(summary = "imposta ordine come rifiutato")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ordine rifiutato con successo", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = OrdineResponseDTO.class))),
			@ApiResponse(responseCode = "406", description = "impossibile eseguire l'operazione: ordine già rifiutato ", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class))),
			@ApiResponse(responseCode = "404", description = "ordine specificato non esistente ", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class)))
			
	})
	@GetMapping("/ristoratore/rifiutaOrdine/{idOrdine}")
	public ResponseEntity<OrdineResponseDTO> rifiutaOrdine(@PathVariable("idOrdine") long idOrdine) {
		return ResponseEntity.status(HttpStatus.OK).body(facadeRist.rifiutaOrdine(idOrdine));
	}
	
	@Operation(summary = "cambia stato ordine da ricevuto a in elaborazione o da quest'ultimo a evaso")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "stato ordine cambiato con successo", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = OrdineResponseDTO.class))),
			@ApiResponse(responseCode = "406", description = "impossibile eseguire l'operazione: ordine già evaso o rifiutato ", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class))),
			@ApiResponse(responseCode = "404", description = "ordine specificato non esistente ", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class)))
			
	})
	@GetMapping("/ristoratore/cambiastatoordine/{idOrdine}")
	public ResponseEntity<OrdineResponseDTO> cambiaStatoOrdine(@PathVariable("idOrdine") long idOrdine) {
		return ResponseEntity.status(HttpStatus.OK).body(facadeRist.cambiaStatoOrdine(idOrdine));
	}
	
//	@PostMapping("/ristoratore/registrazione") //versione pre-facade
//	public ResponseEntity<Void> registraRistoratore(@Valid @RequestBody RegistraRistoratoreDTO request) {
//		Utente u = serv.findUtenteByUsername(request.getUsername()); // gestione caso inserimento username già esistente
//																		// che causa eccezione
//		if (u != null) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//		}
//		serv.registrazioneRistoratore(request.getUsername(), request.getPassword(), request.getNome(),
//				request.getCognome(), request.getRuolo(), request.getNomeRistorante());
//		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
//	}
	
//	@PostMapping("/ristoratore/registrazione")
//	public ResponseEntity<Void> registraRistoratore(@Valid @RequestBody RegistraRistoratoreDTO request) {
//		facadeRist.registraRistoratore(request);
//		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
//	}
	
	//non funziona
//	@PostMapping("/ristoratore/registrazione2validator") 
//	public ResponseEntity<Void> registraRistoratore2(@RequestBody RegistraRistoratoreDTO request) {
//		Utente u = serv.findUtenteByUsername(request.getUsername());
//		if (u != null) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//		}
//		serv.registrazioneRistoratore(request.getUsername(), request.getPassword(), request.getNome(),
//				request.getCognome(), request.getRuolo(), request.getNomeRistorante());
//		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
//	}

//	@PostMapping("/aggiungiIngrediente")
//	public ResponseEntity<Void> aggiungiIngrediente(@Valid @RequestBody IngredienteDTO request) {
//		Ingrediente i = serviceIng.findIngredienteByNome(request.getNome());
//		if (i == null) {
//			serviceIng.save(request.getNome());
//			return ResponseEntity.status(HttpStatus.OK).build();
//		} else {
//			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
//		}
//	}

//	@GetMapping("/visualizzaIngredienti")
//	public ResponseEntity<List<IngredienteDTO>> findAllIngredienti() {
//		List<Ingrediente> ingredienti = serviceIng.findAll();
//		List<IngredienteDTO> resultIngredienti = new ArrayList<>();
//		for (Ingrediente i : ingredienti) {
//			resultIngredienti.add(new IngredienteDTO(i.getNome(), i.getPiatto())); // rimuovere getPiatto?
//		}
//		if (resultIngredienti.size() == 0) {
//			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//		} else {
//			return ResponseEntity.status(HttpStatus.OK).body(resultIngredienti);
//		}
//	}

//	@PostMapping("/aggiungiCategoria")
//	public ResponseEntity<Void> aggiungiCategoria(@Valid @RequestBody CategoriaDTO request) {
//		// verifica che non esista già una categoria con quel nome, findCategoriaByNome
//		Categoria c = serviceCat.findCategoriaByNome(request.getNome());
//		if (c == null) {
//			serviceCat.save(request.getNome());
//			return ResponseEntity.status(HttpStatus.OK).build();
//		} else {
//			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
//		}
//	}

//	@GetMapping("/visualizzaCategorie")
//	public ResponseEntity<List<CategoriaDTO>> findAllCategorie() {
//		List<Categoria> categorie = serviceCat.findAll();
//		List<CategoriaDTO> resultCategorie = new ArrayList<>();
//		for (Categoria c : categorie) {
//			resultCategorie.add(new CategoriaDTO(c.getNome(), c.getPiatti()));
//		}
//		if (resultCategorie.size() == 0) {
//			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//		} else {
//			return ResponseEntity.status(HttpStatus.OK).body(resultCategorie);
//		}
//	}

//	@PostMapping("/aggiungiPiatto")
//	public ResponseEntity<PiattoDTO> aggiungiPiatto(@Valid @RequestBody PiattoDTO request) {
//		Piatto p = servicePiatto.findPiattoByNome(request.getNome());
//		Ristorante r = serviceRis.findById(request.getId_ristorante());
//		Categoria c = serviceCat.findCategoriaById(request.getId_categoria());
//		List<Long> idIngredienti = request.getId_ingredienti();
//		for(Long id : idIngredienti) {
//			Ingrediente ingrediente = serviceIng.findById(id);
//			if(ingrediente == null) {
//				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//			}
//		}
//		PiattoDTO resultPiatto = new PiattoDTO();
//		if (r == null || c == null) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
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
//				return ResponseEntity.status(HttpStatus.OK).body(resultPiatto);
//			} else {
//				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
//			}
//		}
//	}
	
//	@GetMapping("/visualizzaPiatti/{idRistorante}")
//	public ResponseEntity<List<PiattoDTO>> visualizzaPiatti(@PathVariable("idRistorante") long idRistorante) {
//		List<Piatto> piattiRistorante = servicePiatto.findAllByRistoranteId(idRistorante);
//		List<PiattoDTO> resultPiattiRist = new ArrayList<>();
//		for(Piatto p : piattiRistorante) {
//			List<Long> idIngredienti = p.getIngredienti_piatto().stream()
//	                .map(Ingrediente::getId)
//	                .collect(Collectors.toList());
//			
//			resultPiattiRist.add(new PiattoDTO(p.getNome(), p.getPrezzo(), p.getDescrizione(), 
//					idIngredienti, p.getRistorante().getId(), p.getCategoria().getId()));
//		}
//		if(resultPiattiRist.isEmpty()) {
//			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//		}
//		else {
//			return ResponseEntity.status(HttpStatus.OK).body(resultPiattiRist);
//		}
//	}

//	@GetMapping("/visualizzaOrdini")
//	public ResponseEntity<List<OrdineDTO>> findAllOrdini(){
//		List<Ordine> ordini = serviceOrd.findAll();
//		List<OrdineDTO> resultOrd = new ArrayList<>();
//		//findRistorante e findUtente by id?
//		for(Ordine o : ordini) {
////			if(o.getCliente().getId() == )
//			resultOrd.add(new OrdineDTO(o.getStato(), o.getRistorante().getId(), o.getDataInvio(), o.getCliente().getId()));
//		}
//		if(resultOrd.size() == 0) {
//			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//		}
//		else {
//			return ResponseEntity.status(HttpStatus.OK).body(resultOrd);
//		}
//		
//	}

//	@GetMapping("/ristoratore/visualizzaOrdiniRicevutiEinElaborazione/{idRistorante}")
//	public ResponseEntity<List<OrdineDTO>> findAllOrdiniRicEinElab(@PathVariable("idRistorante") long idRistorante) {
////		List<Ordine> ordini = serviceOrd.findAll();
//		List<Ordine> ordiniRistorante = serviceOrd.findAllByRistoranteId(idRistorante);
//		List<OrdineDTO> resultOrd = new ArrayList<>();
//		// findRistorante e findUtente by id?
//		for (Ordine o : ordiniRistorante) {
//			if (o.getStato() == Stato.EVASO || o.getStato() == Stato.RIFIUTATO) {
//				continue;
//			} else {
//				List<Long> idRigheOrdine = o.getRigheDOrdine().stream()
//						.map(RigaDOrdine::getId)
//						.collect(Collectors.toList());
//				
//				resultOrd.add(new OrdineDTO(o.getStato(), o.getRistorante().getId(), o.getDataInvio(),
//						o.getCliente().getId(), idRigheOrdine));
//			}
//		}
//		if (resultOrd.size() == 0) {
//			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//		} else {
//			return ResponseEntity.status(HttpStatus.OK).body(resultOrd);
//		}
//
//	}

//	@GetMapping("/rifiutaOrdine/{idOrdine}/{idRistorante}")
//	public ResponseEntity<Void> rifiutaOrdine(@PathVariable("idOrdine") long idOrdine, @PathVariable("idRistorante") long idRistorante){
//		Ordine o = serviceOrd.findById(idOrdine);
//		if(idOrdine < 1) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//		}
//		else if (o == null) {
//			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//		}
//		else if(o.getStato() == Stato.RIFIUTATO || o.getStato() == Stato.EVASO) {
//			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//		}
//		else {
//			o.setStato(Stato.RIFIUTATO);
//			serviceOrd.save(o);
//			return ResponseEntity.status(HttpStatus.OK).build();
//		}
//		
//		else if(o.getStato() != Stato.RIFIUTATO && o.getRistorante().getId() == idRistorante 
//				|| o.getStato() != Stato.IN_ELABORAZIONE  && o.getRistorante().getId() == idRistorante) {
//			o.setStato(Stato.RIFIUTATO);
//			serviceOrd.save(o);
//			return ResponseEntity.status(HttpStatus.OK).build();
//		}
//	}
	
	// versione 2 con findbyidristorante, 
	// crea jakarta.persistence.NonUniqueResultException: query did not return a unique result: 3
	
//	@GetMapping("/rifiutaOrdine/{idOrdine}/{idRistorante}") 
//	public ResponseEntity<Void> rifiutaOrdine(@PathVariable("idOrdine") long idOrdine,
//			@PathVariable("idRistorante") long idRistorante) {
//		Ordine o = serviceOrd.findByRistoranteId(idRistorante);
//		if (idOrdine < 1) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//		} else if (o == null) {
//			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//		} else if (o.getStato() == Stato.RIFIUTATO || o.getStato() == Stato.EVASO) {
//			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//		} else {
//			o.setStato(Stato.RIFIUTATO);
//			serviceOrd.save(o);
//			return ResponseEntity.status(HttpStatus.OK).build();
//		}
//	}

//	@GetMapping("/rifiutaOrdine/{idOrdine}/{idRistorante}") //versione 1 con findbyid ordine
//	public ResponseEntity<Void> rifiutaOrdine(@PathVariable("idOrdine") long idOrdine,
//			@PathVariable("idRistorante") long idRistorante) {
//		Ordine o = serviceOrd.findById(idOrdine);
//		if (idOrdine < 1) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//		} else if (o == null) {
//			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//		} else if (o.getStato() == Stato.RIFIUTATO || o.getStato() == Stato.EVASO) {
//			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//		} else if (o.getStato() != Stato.RIFIUTATO && o.getRistorante().getId() == idRistorante
//				|| o.getStato() != Stato.EVASO && o.getRistorante().getId() == idRistorante) {
//			o.setStato(Stato.RIFIUTATO);
//			serviceOrd.save(o);
//			return ResponseEntity.status(HttpStatus.OK).build();
//		} else {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//		}
//	}

//	@GetMapping("/cambiastatoordine/{idOrdine}/{idRistorante}")
//	public ResponseEntity<Void> cambiaStatoOrdine(@PathVariable("idOrdine") long idOrdine,
//			@PathVariable("idRistorante") long idRistorante) {
//		Ordine o = serviceOrd.findById(idOrdine);
//		if (o == null) {
//			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//		} else if (o.getStato() == Stato.RICEVUTO && o.getRistorante().getId() == idRistorante) {
//			o.setStato(Stato.IN_ELABORAZIONE);
//			serviceOrd.save(o);
//			return ResponseEntity.status(HttpStatus.ACCEPTED).build();
//		} else if (o.getStato() == Stato.IN_ELABORAZIONE && o.getRistorante().getId() == idRistorante) {
//			o.setStato(Stato.EVASO);
//			serviceOrd.save(o);
//			return ResponseEntity.status(HttpStatus.ACCEPTED).build();
//		} else {
//			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//		}
//	}
}
