package org.elis.progettoristorante.businesslogic;

import org.elis.progettoristorante.dto.LoginDTO;
import org.elis.progettoristorante.dto.RegistraRistoratoreDTO;
import org.elis.progettoristorante.dto.RegistraUtenteDTO;
import org.elis.progettoristorante.dto.UtenteDTO;
import org.elis.progettoristorante.dto.swagger.BadRequestDTO;
import org.elis.progettoristorante.exception.model.UtenteGiàEsistenteException;
import org.elis.progettoristorante.facade.RistoratoreFacade;
import org.elis.progettoristorante.facade.UtenteFacade;
import org.elis.progettoristorante.mapper.UtenteMapper;
import org.elis.progettoristorante.model.Utente;
import org.elis.progettoristorante.dto.ErrorMsgDTO;
import org.elis.progettoristorante.security.GestoreToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/all")
@Tag(name = "AllController: metodi di registrazione e login", description = "metodi senza autorizzazioni, accessibili da ogni utente")
public class AllController {
	
	@Autowired
	private UtenteFacade facadeUt;
	
	@Autowired
	private RistoratoreFacade facadeRist;
	
	@Autowired
	private GestoreToken gestoreT;
	
	@Autowired
	private UtenteMapper mapperUt;

	@Operation(summary = "registrazione cliente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "202", description = "cliente registrato con successo", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "403", description = "utente con questo username già esistente", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class))),
			@ApiResponse(responseCode = "400", description = "i dati inseriti non sono conformi ai pattern richiesti ", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BadRequestDTO.class)))
			
	})
	@PostMapping("/utente/registrazione")
	public ResponseEntity<Void> registraUtente(@Valid @RequestBody RegistraUtenteDTO request){
			facadeUt.registraUtente(request);
			return ResponseEntity.status(HttpStatus.ACCEPTED).build();
		}
	
	@Operation(summary = "registrazione ristoratore")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "202", description = "ristoratore registrato con successo", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "403", description = "ristoratore con questo username già esistente", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class))),
			@ApiResponse(responseCode = "400", description = "i dati inseriti non sono conformi ai pattern richiesti ", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BadRequestDTO.class)))
			
	})
	@PostMapping("/ristoratore/registrazione")
	public ResponseEntity<Void> registraRistoratore(@Valid @RequestBody RegistraRistoratoreDTO request) {
		facadeRist.registraRistoratore(request);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}
	
	@Operation(summary = "registrazione cliente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "202", description = "login effettuato con successo", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "404", description = "nessun utente trovato con queste credenziali", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class))),
			@ApiResponse(responseCode = "400", description = "i dati inseriti non sono conformi ai pattern richiesti ", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BadRequestDTO.class)))
			
	})
	@PostMapping("/loginToken")
	public ResponseEntity<UtenteDTO> loginToken(@Valid @RequestBody LoginDTO request){
		Utente u = facadeUt.loginToken(request);
		String token = gestoreT.creaToken(u);
		return ResponseEntity.status(HttpStatus.ACCEPTED).header("Authorization", token).body(mapperUt.toUtenteDTO(u));
	}
	
	@Operation(summary = "registrazione cliente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "202", description = "login effettuato con successo", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "404", description = "nessun utente trovato con queste credenziali", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMsgDTO.class))),
			@ApiResponse(responseCode = "400", description = "i dati inseriti non sono conformi ai pattern richiesti ", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BadRequestDTO.class)))
			
	})
	@PostMapping("/utente/login") //login senza token
	public ResponseEntity<UtenteDTO> login(@Valid @RequestBody LoginDTO request){
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(facadeUt.login(request));
	}
}
