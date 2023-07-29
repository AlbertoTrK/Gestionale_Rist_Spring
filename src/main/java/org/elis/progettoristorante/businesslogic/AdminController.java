package org.elis.progettoristorante.businesslogic;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.elis.progettoristorante.dto.OrdineResponseDTO;
import org.elis.progettoristorante.dto.ErrorMsgDTO;
import org.elis.progettoristorante.dto.FatturatoDTO;
import org.elis.progettoristorante.dto.FatturatoPerMeseDTO;
import org.elis.progettoristorante.dto.OrdineDTO;
import org.elis.progettoristorante.dto.RistoranteDto;
import org.elis.progettoristorante.dto.UtenteDTO;
import org.elis.progettoristorante.facade.AdminFacade;
import org.elis.progettoristorante.filtri.FiltraOrdiniRequest;
import org.elis.progettoristorante.model.Ordine;
import org.elis.progettoristorante.model.RigaDOrdine;
import org.elis.progettoristorante.model.Ristorante;
import org.elis.progettoristorante.model.Stato;
import org.elis.progettoristorante.model.Utente;
import org.elis.progettoristorante.service.model.OrdineService;
import org.elis.progettoristorante.service.model.RistoranteService;
import org.elis.progettoristorante.service.model.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@Tag(name = "Admin Controller: metodi accessibili solo all'admin", 
description = "metodi accessibili solo a chi supera l'autenticazione come admin")
public class AdminController {
	
	@Autowired
	RistoranteService servRis;
	
	@Autowired
	OrdineService serviceOrd;
	
	@Autowired
	UtenteService serv;
	
	@Autowired
	AdminFacade admFacade;
	
	@Operation(summary = "visualizzazione di ogni ristorante esistente in database")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "lista ristoranti recuperata con successo", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RistoranteDto.class))),
			@ApiResponse(responseCode = "204", description = "nessun ristorante presente in database", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class)))	
	})
	@GetMapping("/admin/ristoranti")
	public ResponseEntity<List<RistoranteDto>> findAllRistoranti() {
		return ResponseEntity.status(HttpStatus.OK).body(admFacade.vediTuttiRistoranti());
	}
	
	@Operation(summary = "visualizzazione ordini di utente specifico")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "lista ordini utente recuperata con successo", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = OrdineResponseDTO.class))),
			@ApiResponse(responseCode = "204", description = "nessun ordine associato all'utente specifico", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class))),
			@ApiResponse(responseCode = "404", description = "nessun utente trovato ", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class)))
			
	})
	@GetMapping("/admin/storicoOrdini/{idUtente}")
	public ResponseEntity<List<OrdineResponseDTO>> storicoOrdiniUtente(@PathVariable("idUtente") long idUtente) {
		return ResponseEntity.status(HttpStatus.OK).body(admFacade.storicoOrdini(idUtente));
	}
	
	@Operation(summary = "visualizzazione fatturato degli ultimi trenta giorni del ristorante specificato")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "fatturato recuperato con successo", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = FatturatoDTO.class))),
			@ApiResponse(responseCode = "404", description = "ristorante specificato non trovato ", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class)))
			
	})
	@GetMapping("/admin/fatturato/{idRistorante}")
	public ResponseEntity<FatturatoDTO> vediFatturato(@PathVariable("idRistorante") long idRistorante) {
		return ResponseEntity.status(HttpStatus.OK).body(admFacade.vediFatturato(idRistorante));
	}
	
	@Operation(summary = "visualizzazione fatturato, diviso per mesi, del ristorante specificato")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "fatturato recuperato con successo", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = FatturatoPerMeseDTO.class))),
			@ApiResponse(responseCode = "404", description = "ristorante specificato non trovato ", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class)))
			
	})
	@GetMapping("/admin/fatturatoPerMese/{idRistorante}")
	public ResponseEntity<FatturatoPerMeseDTO> vediFatturatoPerMese(@PathVariable("idRistorante") long idRistorante) {
		return ResponseEntity.status(HttpStatus.OK).body(admFacade.fatturatoPerMese(idRistorante));
	}
	
	@Operation(summary = "visualizzazione di ogni ordine esistente in database")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "lista ordini recuperati con successo", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = OrdineResponseDTO.class))),
			@ApiResponse(responseCode = "204", description = "nessun ordine presente in database", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class)))	
	})
	@GetMapping("/admin/visualizzaTuttiOrdini")
	public ResponseEntity<List<OrdineResponseDTO>> trovaTuttiGliOrdini() {
		return ResponseEntity.status(HttpStatus.OK).body(admFacade.findAllOrdini());
	}
	
	@Operation(summary = "eliminazione di un utente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "utente recuperato con successo", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = String.class))),
			@ApiResponse(responseCode = "404", description = "utente non trovato", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class)))	
	})
	@PostMapping("/admin/eliminaUtente")
	public ResponseEntity<String> eliminaUtente(@Valid @RequestBody UtenteDTO request) {
		return ResponseEntity.status(HttpStatus.OK).body("Utente " + request.getUsername() + " cancellato.");
	}
	
//	@Operation(summary = "filtro ordini in base a uno o più dei seguenti criteri: data invio ordine, nome del ristorante, cognome, username dell'utente che lo ha effettuato")
//	@ApiResponses(value = {
//			@ApiResponse(responseCode = "200", description = "utenti filtrati correttamente in base ai criteri scelti", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = OrdineResponseDTO.class))),
//			@ApiResponse(responseCode = "204", description = "nessun utente trovato in base a questi criteri", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class)))
//			
//	})
//	@PostMapping("/admin/filtraOrdini")
//	public ResponseEntity<List<OrdineResponseDTO>> filtraOrdini(FiltraOrdiniRequest request){
//		List<OrdineResponseDTO> ordiniFiltrati =  admFacade.filtraOrdini(request);
//		return ResponseEntity.status(HttpStatus.OK).body(ordiniFiltrati);
//	}
}
	
//	@GetMapping("/admin/ristoranti") //pre-facade
//	public ResponseEntity<List<RistoranteDto>> findAll() {
//		List<Ristorante> ristoranti = servRis.findAll();
//		List<RistoranteDto> resultRist = new ArrayList<>();
//		for (Ristorante r : ristoranti) {
//			resultRist.add(new RistoranteDto(r.getNome(), r.getPiatti()));
//		}
//		return ResponseEntity.status(HttpStatus.OK).body(resultRist);
//	}
	
//	@GetMapping("/admin/visualizzaTuttiOrdini") //versione senza righe d'ordine
//	public ResponseEntity<List<OrdineDTO>> findAllOrdini() {
//		List<Ordine> ordini = serviceOrd.findAll();
//		List<OrdineDTO> resultOrd = new ArrayList<>();
//		// findRistorante e findUtente by id?
//		for (Ordine o : ordini) {
//			resultOrd.add(
//					new OrdineDTO(o.getStato(), o.getRistorante().getId(), o.getDataInvio(), o.getCliente().getId()));
//		}
//		if (resultOrd.size() == 0) {
//			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//		} else {
//			return ResponseEntity.status(HttpStatus.OK).body(resultOrd);
//		}
//
//	}
	
//	@GetMapping("/admin/visualizzaTuttiOrdini")
//	public ResponseEntity<List<AggiungiOrdineResponseDTO>> findAllOrdini() {
//		List<Ordine> ordini = serviceOrd.findAll();
//		List<AggiungiOrdineResponseDTO> resultOrd = new ArrayList<>();
//		// findRistorante e findUtente by id?
//		for (Ordine o : ordini) {
//			resultOrd.add(
//					new AggiungiOrdineResponseDTO(o.getCliente().getUsername(), o.getRistorante().getNome(), o.getId(), o.getRigheDOrdine().get(0).getId());
//		}
//		if (resultOrd.size() == 0) {
//			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//		} else {
//			return ResponseEntity.status(HttpStatus.OK).body(resultOrd);
//		}
//
//	}
	 
//	@GetMapping("admin/ordini/{idUtente}") //pre-facade
//	public ResponseEntity<List<OrdineDTO>> storicoOrdine(@PathVariable("idUtente") long idUtente) {
//		List<Ordine> ordiniUtente = serviceOrd.findAllByClienteId(idUtente);
//		List<OrdineDTO> resultOrdiniUtente = new ArrayList<>();
//		Utente u = serv.findById(idUtente);
//		if (u == null) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//		} else {
//			for (Ordine o : ordiniUtente) {
//				List<Long> idRigheOrdine = o.getRigheDOrdine().stream()
//						.map(RigaDOrdine::getId)
//						.collect(Collectors.toList());
//				
//				resultOrdiniUtente.add(new OrdineDTO(o.getStato(), o.getCliente().getId(), o.getDataInvio(),
//						o.getRistorante().getId(), idRigheOrdine));
//			}
//			if (resultOrdiniUtente.isEmpty()) {
//				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//			} else {
//				return ResponseEntity.status(HttpStatus.OK).body(resultOrdiniUtente);
//			}
//		}
//	}
	
//	@GetMapping("admin/fatturato/{idRistorante}")
//	public ResponseEntity<FatturatoDTO> vediFatturato(@PathVariable("idRistorante") long idRistorante) {
//		Ristorante ristorante = servRis.findById(idRistorante);
//		List<Ordine> ordiniRistorante = ristorante.getOrdine();
//		FatturatoDTO resultFatturato = new FatturatoDTO();
//		double fatturato = 0;
//		for (int i = 0; i < ordiniRistorante.size(); i++) {
//			List<RigaDOrdine> righe = ordiniRistorante.get(i).getRigheDOrdine();
//			for (RigaDOrdine r : righe) {
//				double conto = r.getPiatto().getPrezzo() * r.getQuantità();
//				fatturato += conto;
//			}
//
//		}
//		resultFatturato.setFatturato(fatturato);
//		resultFatturato.setOrdini(ordiniRistorante);
//		resultFatturato.setRistorante(ristorante);
//		if (resultFatturato == null) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//		} else {
//			return ResponseEntity.status(HttpStatus.OK).body(resultFatturato);
//		}
//
//	}
	
//	@GetMapping("/admin/fatturato/{idRistorante}") //pre-facade
//	public ResponseEntity<Double> vediFatturato(@PathVariable("idRistorante") long idRistorante) {
//		Ristorante ristorante = servRis.findById(idRistorante);
//
//		if (ristorante == null) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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
//		return ResponseEntity.status(HttpStatus.OK).body(fatturato);
//	}
	
//	@GetMapping("/admin/visualizzaTuttiOrdini") //pre-facade
//	public ResponseEntity<List<OrdineDTO>> findAllOrdini() {
//		List<Ordine> ordini = serviceOrd.findAll();
//		List<OrdineDTO> resultOrd = new ArrayList<>();
//
//		for (Ordine o : ordini) {
//
//			List<Long> idRigheOrdine = o.getRigheDOrdine().stream().map(RigaDOrdine::getId)
//					.collect(Collectors.toList());
//
//			resultOrd.add(new OrdineDTO(o.getStato(), o.getRistorante().getId(), o.getDataInvio(),
//					o.getCliente().getId(), idRigheOrdine));
//		}
//
//		if (resultOrd.size() == 0) {
//			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//		} else {
//			return ResponseEntity.status(HttpStatus.OK).body(resultOrd);
//		}
//	}

//}
