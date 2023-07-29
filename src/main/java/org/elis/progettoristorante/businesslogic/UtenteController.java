package org.elis.progettoristorante.businesslogic;

import org.elis.progettoristorante.dto.UtenteDTO;
import org.elis.progettoristorante.dto.swagger.BadRequestDTO;
import org.elis.progettoristorante.exception.model.DatoNonInseritoException;
import org.elis.progettoristorante.exception.model.UtenteNonTrovatoException;
import org.elis.progettoristorante.facade.UtenteFacade;
import org.elis.progettoristorante.filtri.FiltraPiattoRequest;
import org.elis.progettoristorante.filtri.FiltraUtentiRequest;
import org.elis.progettoristorante.mapper.UtenteMapper;
import org.elis.progettoristorante.model.Ingrediente;
import org.elis.progettoristorante.model.Ordine;
import org.elis.progettoristorante.model.Piatto;
import org.elis.progettoristorante.model.RigaDOrdine;
import org.elis.progettoristorante.model.Ristorante;
import org.elis.progettoristorante.model.Stato;
import org.elis.progettoristorante.model.Utente;
import org.elis.progettoristorante.security.GestoreToken;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.elis.progettoristorante.dto.AggiungiAdOrdineDTO;
import org.elis.progettoristorante.dto.AggiungiOrdineDTO;
import org.elis.progettoristorante.dto.OrdineResponseDTO;
import org.elis.progettoristorante.dto.ErrorMsgDTO;
import org.elis.progettoristorante.dto.IngredienteDTO;
import org.elis.progettoristorante.dto.LoginDTO;
import org.elis.progettoristorante.dto.ModificaUtenteDTO;
import org.elis.progettoristorante.dto.OrdineDTO;
import org.elis.progettoristorante.dto.PiattoDTO;
import org.elis.progettoristorante.dto.PiattoDTOResponse;
import org.elis.progettoristorante.dto.RegistraUtenteDTO;
import org.elis.progettoristorante.dto.RistoranteDto;
import org.elis.progettoristorante.service.model.IngredienteService;
import org.elis.progettoristorante.service.model.OrdineService;
import org.elis.progettoristorante.service.model.PiattoService;
import org.elis.progettoristorante.service.model.RistoranteService;
import org.elis.progettoristorante.service.model.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;

@RestController
@Tag(name = "Utente Controller: metodi accessibili a cliente e ristoratore", 
description = "metodi accessibili a chi supera l'autenticazione come cliente o ristoratore")
public class UtenteController {
	
	@Autowired
	private UtenteService serv;
	
	@Autowired
	private RistoranteService servRis;
	
	@Autowired
	private OrdineService servOrd;
	
	@Autowired
	private UtenteFacade facadeUt;
	
	@Operation(summary = "filtro utenti in base a uno o più dei seguenti criteri: username, nome, cognome, nome del ristorante che possiede (se ristoratore)")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "utenti filtrati correttamente in base ai criteri scelti", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UtenteDTO.class))),
			@ApiResponse(responseCode = "204", description = "nessun utente trovato in base a questi criteri", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class)))
			
	})
	@PostMapping("/utente/filtraUtenti")
	public ResponseEntity<List<UtenteDTO>> filtraUtenti(@RequestBody FiltraUtentiRequest request){
		List<UtenteDTO> utentiFiltrati = facadeUt.filtraUtenti(request);
		return ResponseEntity.status(HttpStatus.OK).body(utentiFiltrati);
	}
	
	@Operation(summary = "filtro piatti in base a uno o più dei seguenti criteri: nome, categoria, ristorante, ingrediente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "piatti filtrati correttamente in base ai criteri scelti", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PiattoDTOResponse.class))),
			@ApiResponse(responseCode = "204", description = "nessun piatto trovato in base a questi criteri", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class)))
			
	})
	@PostMapping("/utente/filtraPiatti")
	public ResponseEntity<List<PiattoDTOResponse>> filtraPiatti(@RequestBody FiltraPiattoRequest request){
		List<PiattoDTOResponse> piattiFiltrati = facadeUt.filtraPiatti(request);
		return ResponseEntity.status(HttpStatus.OK).body(piattiFiltrati);
	}
	
	@Operation(summary = "recupero lista ristoranti presente in database")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "lista ristoranti recuperata con successo", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RistoranteDto.class))),
			@ApiResponse(responseCode = "204", description = "nessun ristorante esistente in database", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class)))
			
	})
	@GetMapping("/utente/ristoranti")
	public ResponseEntity <List<RistoranteDto>> findAllRistoranti(){
		return ResponseEntity.status(HttpStatus.OK).body(facadeUt.findAllRistoranti());
	}
	
	@Operation(summary = "visualizzazione piatti di ristorante specifico")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "lista piatti ristorante", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PiattoDTOResponse.class))),
			@ApiResponse(responseCode = "204", description = "nessun piatto esistente per questo ristorante", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class))),
			@ApiResponse(responseCode = "404", description = "nessun ristorante trovato ", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class)))
			
	})
	@GetMapping("/utente/visualizzaPiattiRistorante/{idRistorante}")
	public ResponseEntity<List<PiattoDTOResponse>> visualizzaPiattiRistorante(@PathVariable("idRistorante") long idRistorante) {
		return ResponseEntity.status(HttpStatus.OK).body(facadeUt.visualizzaPiattiRistorante(idRistorante));
	}
	
	@Operation(summary = "recupero lista piatti che contengono un determinato ingrediente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "lista piatti con ingrediente recuperata con successo", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PiattoDTOResponse.class))),
			@ApiResponse(responseCode = "204", description = "nessun piatto con l'ingrediente cercato presente in database", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class))),
			@ApiResponse(responseCode = "404", description = "nessun ingrediente trovato ", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class)))
			
	})
	@GetMapping("/utente/piattiConIngrediente/{idIngrediente}")
	public ResponseEntity<List<PiattoDTOResponse>> visualizzaPiattiConIng(@PathVariable("idIngrediente") long idIngrediente) {
		return ResponseEntity.status(HttpStatus.OK).body(facadeUt.visualizzaPiattiConIngrediente(idIngrediente));
	}
	
	@Operation(summary = "visualizzazione ingredienti del piatto specificato")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "lista ingredienti del piatto recuperata con successo", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = IngredienteDTO.class))),
			@ApiResponse(responseCode = "204", description = "piatto non trovato in database", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class)))
			
	})
	@GetMapping("/utente/ingredientiPiatto/{idPiatto}")
	public ResponseEntity<List<IngredienteDTO>> ingredientiDiPiatto(@PathVariable ("idPiatto") long idPiatto){
		return ResponseEntity.status(HttpStatus.OK).body(facadeUt.visualizzaIngredientiPiatto(idPiatto));
	}
	
	@Operation(summary = "invio ordine da parte del cliente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ordine inviato con successo", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = OrdineResponseDTO.class))),
			@ApiResponse(responseCode = "404", description = "nessun piatto, ristorante o utente trovato ", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class))),
			@ApiResponse(responseCode = "406", description = "impossibile aggiungere a ordine piatto non di ristorante associato al piatto ", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class))),
			@ApiResponse(responseCode = "400", description = "i dati inseriti non sono conformi ai pattern richiesti ", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BadRequestDTO.class)))
	})
	@PostMapping("/utente/aggiungiOrdine") 
	public ResponseEntity<OrdineResponseDTO> aggiungiOrdine(@Valid @RequestBody AggiungiOrdineDTO request) {
		return ResponseEntity.status(HttpStatus.OK).body(facadeUt.aggiungiOrdine(request));
	}
	
	@Operation(summary = "aggiunta di ulteriori piatti a ordine da parte del cliente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ordine aggiornato con successo", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = OrdineResponseDTO.class))),
			@ApiResponse(responseCode = "404", description = "nessun ordine o piatto trovato ", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class))),
			@ApiResponse(responseCode = "406", description = "impossibile aggiungere a ordine piatto non di ristorante associato al piatto ", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class))),
			@ApiResponse(responseCode = "400", description = "i dati inseriti non sono conformi ai pattern richiesti ", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BadRequestDTO.class)))
			
	})
	@PostMapping("/utente/aggiungiAdOrdine") 
	public ResponseEntity<OrdineResponseDTO> aggiungiAdOrdine(@Valid @RequestBody AggiungiAdOrdineDTO request) {
		return ResponseEntity.status(HttpStatus.OK).body(facadeUt.aggiungiAdOrdine(request));
	}
	
	@Operation(summary = "recupero lista ordini evasi e rifiutati dell'utente specificato")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "lista ordini richiesta recuperata con successo", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = OrdineResponseDTO.class))),
			@ApiResponse(responseCode = "404", description = "nessun utente trovato ", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class))),
			@ApiResponse(responseCode = "204", description = "nessun ordine per questo utente o nessun ordine evaso o rifiutato presente ", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class))),
			@ApiResponse(responseCode = "400", description = "i dati inseriti non sono conformi ai pattern richiesti ", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BadRequestDTO.class)))
	})
	@GetMapping("/utente/storicoOrdiniEvasiERif/{idUtente}") //UsernameAuthentication da aggiungere per utente loggato
	public ResponseEntity<List<OrdineResponseDTO>> storicoOrdiniRifEEvasi(@PathVariable("idUtente") long idUtente) {
		return ResponseEntity.status(HttpStatus.OK).body(facadeUt.visualizzaOrdiniEvasiERif(idUtente));
	}
	
	@Operation(summary = "recupero lista ordini ricevuti e in elaborazione dell'utente specificato")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "lista ordini richiesta recuperata con successo", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = OrdineResponseDTO.class))),
			@ApiResponse(responseCode = "404", description = "nessun utente trovato ", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class))),
			@ApiResponse(responseCode = "204", description = "nessun ordine per questo utente o nessun ordine ricevuto o in elaborazione presente ", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class)))
			
	})
	@GetMapping("/utente/storicoOrdiniNonEvasiNonRif/{idUtente}")
	public ResponseEntity<List<OrdineResponseDTO>> storicoOrdiniNonEvaNonRif(@PathVariable("idUtente") long idUtente) {
		return ResponseEntity.status(HttpStatus.OK).body(facadeUt.storicoOrdiniNonEvaNonRif(idUtente));
	}
	
	@Operation(summary = "modifica dell'username e/o della password da parte dell'utente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "202", description = "modifica effettuata con successo", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ModificaUtenteDTO.class))),
			@ApiResponse(responseCode = "404", description = "nessun utente trovato ", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class))),
			@ApiResponse(responseCode = "204", description = "dati utente errati: impossibile eseguire l'operazione ", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class)))
			
	})
	@PostMapping("/utente/modificaUsernameEPassword")
	public ResponseEntity<ModificaUtenteDTO> modificaUsernameEPassword(@Valid @RequestBody ModificaUtenteDTO request) {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(facadeUt.modificaUsernameEPassword(request));
	}
	
//	@PostMapping("/utente/registrazione") //versione pre-facade
//	public ResponseEntity<Void> registraUtente(@Valid @RequestBody RegistraUtenteDTO request){
//			Utente u = serv.findUtenteByUsername(request.getUsername()); //gestione caso inserimento username già esistente che causa eccezione
//			if(u != null) {
//				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//			}
//			serv.registrazione(request.getUsername(), request.getPassword(), request.getNome(), request.getCognome(), request.getRuolo(), request.getRistorante(),
//					request.getId());
//			return ResponseEntity.status(HttpStatus.ACCEPTED).build();
//		}
	
//	@PostMapping("/utente/registrazione")
//	public ResponseEntity<Void> registraUtente(@Valid @RequestBody RegistraUtenteDTO request){
//			facadeUt.registraUtente(request);
//			return ResponseEntity.status(HttpStatus.ACCEPTED).build();
//		}

//	@PostMapping("/utente/login") //versione pre-facade
//	public ResponseEntity<UtenteDTO> login(@Valid @RequestBody LoginDTO request) {
//			Utente u = serv.login(request.getUsername(), request.getPassword());
//			if (u == null) { //throw new UtenteNonTrovatoException ?
//				throw new UtenteNonTrovatoException("Nessun utente con queste credenziali");
////				return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
//			} else {
//				UtenteDTO result = new UtenteDTO(u);
//				return ResponseEntity.status(HttpStatus.OK).body(result);
//			}
//		}
	
//	@PostMapping("/utente/login") //login senza token
//	public ResponseEntity<UtenteDTO> login(@Valid @RequestBody LoginDTO request){
//		return ResponseEntity.status(HttpStatus.ACCEPTED).body(facadeUt.login(request));
//	}
	
	
//	@GetMapping("/utente/ristoranti") //versione pre-facade
//	public ResponseEntity <List<RistoranteDto>> findAll(){
//		List<Ristorante> ristoranti = servRis.findAll();
//		List<RistoranteDto> resultRist = new ArrayList<>();
//		for(Ristorante r : ristoranti) {
//			resultRist.add(new RistoranteDto(r.getNome()));
//		}
//		if(resultRist.isEmpty()) {
//			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//		}
//		else {
//			return ResponseEntity.status(HttpStatus.OK).body(resultRist);
//		}
//	}
	
//	@GetMapping("/visualizzaPiatti/{idRistorante}") //versione pre-facade
//	public ResponseEntity<List<PiattoDTO>> visualizzaPiatti(@PathVariable("idRistorante") long idRistorante) {
//		Ristorante r = servRis.findById(idRistorante);
//		if(r == null) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//		}
//		List<Piatto> piattiRistorante = servPiatto.findAllByRistoranteId(idRistorante);
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
	
//	@GetMapping("utente/piattiConIngrediente/{idIngrediente}") //versione pre-facade
//	public ResponseEntity<List<PiattoDTO>> findAllByIngredienteId(@PathVariable("idIngrediente") long idIngrediente) {
//		List<Piatto> listaPiattiConIngrediente = servPiatto.findAllPiattoByIngredienteId(idIngrediente);
//		List<PiattoDTO> resultPiatti = new ArrayList<>();
//		for (Piatto p : listaPiattiConIngrediente) {
//			List<Long> idIngredienti = p.getIngredienti_piatto().stream()
//					.map(Ingrediente::getId)
//					.collect(Collectors.toList());
//			
//			resultPiatti.add(new PiattoDTO(p.getNome(), p.getPrezzo(), p.getDescrizione(), idIngredienti,
//					p.getRistorante().getId(), p.getCategoria().getId()));
//		}
//		if (resultPiatti.isEmpty()) {
//			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//		} else {
//			return ResponseEntity.status(HttpStatus.OK).body(resultPiatti);
//		}
//	}
	
//	@GetMapping("utente/ingredientiPiatto/{idPiatto}") //versione pre-facade
//	public ResponseEntity<List<IngredienteDTO>> ingredientiDiPiatto(@PathVariable ("idPiatto") long idPiatto){
//		List<Ingrediente> ingredientiDelPiatto = servIng.findAllIngredienteByPiattoId(idPiatto);
//		List<IngredienteDTO> resultIngredientiDiPiatto = new ArrayList<>();
//		for(Ingrediente i : ingredientiDelPiatto) {
//			resultIngredientiDiPiatto.add(new IngredienteDTO(i.getNome()));
//		}
//		if(resultIngredientiDiPiatto.isEmpty()){
//			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//		}
//		else {
//			return ResponseEntity.status(HttpStatus.OK).body(resultIngredientiDiPiatto);
//		}
//	}
	
//	@GetMapping("utente/ingredientiPiatto")
//	public ResponseEntity<List<IngredienteDTO>> ingredientiDiPiatto(@RequestBody Piatto request){
//		List<Ingrediente> ingredientiDelPiatto = servIng.findAllIngredienteByPiattoId(request.getId());
//		List<IngredienteDTO> resultIngredientiDiPiatto = new ArrayList<>();
//		for(Ingrediente i : ingredientiDelPiatto) {
//			resultIngredientiDiPiatto.add(new IngredienteDTO(i.getNome()));
//		}
//		if(resultIngredientiDiPiatto.isEmpty()){
//			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//		}
//		else {
//			return ResponseEntity.status(HttpStatus.OK).body(resultIngredientiDiPiatto);
//		}
//	}
	
	//incompleto
//	@PostMapping("/utente/inviaOrdine") // gestire anche righe d'ordine //da migliorare
//	public ResponseEntity<Void> inviaOrdine(@Valid @RequestBody AggiungiOrdineDTO request) {
//		Utente u = serv.findById(request.getIdUtente());
//		Ristorante r = servRis.findById(request.getIdRistorante());
//		if (u == null || r == null) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//		} else {
//			servOrd.save(request.getIdUtente(), request.getIdRistorante());
//			return ResponseEntity.status(HttpStatus.ACCEPTED).build();
//		}
//	}
	
//	@PostMapping("utente/aggiungiOrdine") //versione 2 pre-facade
//	public ResponseEntity<AggiungiOrdineResponseDTO> aggiungiOrdine(@Valid @RequestBody AggiungiOrdineDTO request) {
//		Utente u = serv.findById(request.getIdUtente());
//		Ristorante r = servRis.findById(request.getIdRistorante());
//		if (u == null || r == null) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//		} else {
//		servOrd.aggiungiOrdine(request.getIdUtente(), request.getIdRistorante(), request.getIdProdottoQuantità());
//		return ResponseEntity.status(HttpStatus.OK).build();
//		}
//	}
	
//	@PostMapping("utente/aggiungiOrdine") 
//	public ResponseEntity<AggiungiOrdineResponseDTO> aggiungiOrdine(@Valid @RequestBody AggiungiOrdineDTO request) {
//		return ResponseEntity.status(HttpStatus.OK).body(facadeUt.aggiungiOrdine(request));
//	}

	// cambiare return type con aggiungiordineresponsedto?
//	@GetMapping("utente/storicoOrdiniEvasiERif/{idUtente}")
//	public ResponseEntity<List<OrdineDTO>> storicoOrdineRifEEvasi(@PathVariable("idUtente") long idUtente) {
//		List<Ordine> ordiniUtente = servOrd.findAllByClienteId(idUtente);
//		List<OrdineDTO> resultOrdiniUtente = new ArrayList<>();
//		Utente u = serv.findById(idUtente);
//		if (u == null) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//		} else {
//			for (Ordine o : ordiniUtente) {
//				if (o.getStato() == Stato.RIFIUTATO || o.getStato() == Stato.EVASO) {
//					List<Long> idRigheOrdine = o.getRigheDOrdine().stream().map(RigaDOrdine::getId)
//							.collect(Collectors.toList());
//
//					resultOrdiniUtente.add(new OrdineDTO(o.getStato(), o.getCliente().getId(), o.getDataInvio(),
//							o.getRistorante().getId(), idRigheOrdine));
//				}
//
//			}
//			if (resultOrdiniUtente.isEmpty()) {
//				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//			} else {
//				return ResponseEntity.status(HttpStatus.OK).body(resultOrdiniUtente);
//			}
//		}
//	}

	// cambiare return type con aggiungiordineresponsedto?
//	@GetMapping("utente/storicoOrdiniNonEvasiNonRif/{idUtente}")
//	public ResponseEntity<List<OrdineDTO>> storicoOrdineNonEvNonRif(@PathVariable("idUtente") long idUtente) {
//		List<Ordine> ordiniUtente = servOrd.findAllByClienteId(idUtente);
//		List<OrdineDTO> resultOrdiniUtente = new ArrayList<>();
//		Utente u = serv.findById(idUtente);
//		if (u == null) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//		} else {
//			for (Ordine o : ordiniUtente) {
//				if (o.getStato() != Stato.RIFIUTATO && o.getStato() != Stato.EVASO) {
//					List<Long> idRigheOrdine = o.getRigheDOrdine().stream()
//							.map(RigaDOrdine::getId)
//							.collect(Collectors.toList());
//					
//					resultOrdiniUtente.add(new OrdineDTO(o.getStato(), o.getCliente().getId(), o.getDataInvio(),
//							o.getRistorante().getId(), idRigheOrdine));
//				}
//			}
//			if (resultOrdiniUtente.isEmpty()) {
//				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//			} else {
//				return ResponseEntity.status(HttpStatus.OK).body(resultOrdiniUtente);
//			}
//		}
//	}
	
//	@PostMapping("utente/aggiungi")
//	public ResponseEntity<Void> aggiungiAdOrdine(long idPiatto, int quantità){
//		Ordine o = new Ordine();
//		RigaDOrdine rOd = new RigaDOrdine();
////		o.setRigheDOrdine(rOd);
//		Piatto p = servPiatto.findById(idPiatto);
//		rOd.setPiatto(p);
//		rOd.setQuantità(quantità);
////		servOrd.aggiungi(rOd);
//	}

}
