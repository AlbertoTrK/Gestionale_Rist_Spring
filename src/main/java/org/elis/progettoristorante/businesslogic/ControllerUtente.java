//package org.elis.progettoristorante.businesslogic;
//
//import java.util.List;
//
//import org.elis.progettoristorante.db.DatabaseRistorante;
//import org.elis.progettoristorante.db.DatabaseSingleton;
//import org.elis.progettoristorante.dto.AggiungiUtenteDto;
//import org.elis.progettoristorante.dto.LoginResponseDto;
//import org.elis.progettoristorante.dto.LoginUtenteDto;
//import org.elis.progettoristorante.model.Ristorante;
//import org.elis.progettoristorante.model.Utente;
//import org.elis.progettoristorante.model.Piatto;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class ControllerUtente {

//	@PostMapping("/registrazione")
//	public ResponseEntity<String> registrazione(@RequestBody AggiungiUtenteDto request) {
//		
//		if (request.getUsername().isBlank() || request.getPassword().isBlank() || request.getNome().isBlank()
//				|| request.getCognome().isBlank()) {
//			String errore = "errore";
//			return new ResponseEntity<>(errore, HttpStatus.FORBIDDEN);
//		} else {
//			Utente u = new Utente();
//			u.setUsername(request.getUsername());
//			u.setPassword(request.getPassword());
//			u.setNome(request.getNome());
//			u.setCognome(request.getCognome());
//			u.setRuolo(request.getRuolo());
//
//			DatabaseSingleton.getInstance().aggiungiUtente(u);
//			String s = "Benvenuto " + u.getNome() + " " + u.getCognome();
//			return new ResponseEntity<>(s, HttpStatus.ACCEPTED);
//		}
//
//	}
	
//	@PostMapping("/registrazioneRist")
//	public ResponseEntity<String> registrazioneRist(@RequestBody AggiungiUtenteDto request) {
//		
//		if (request.getUsername().isBlank() || request.getPassword().isBlank() || request.getNome().isBlank()
//				|| request.getCognome().isBlank() || request.getRistorante().getNome().isBlank()) {
//			String errore = "errore, compilare tutti i campi";
//			return new ResponseEntity<>(errore, HttpStatus.FORBIDDEN);
//		} else {
//			Utente ristoratore = new Utente();
//			ristoratore.setUsername(request.getUsername());
//			ristoratore.setPassword(request.getPassword());
//			ristoratore.setNome(request.getNome());
//			ristoratore.setCognome(request.getCognome());
//			ristoratore.setRistorante(request.getRistorante());
//			ristoratore.setRuolo(request.getRuolo());
//			
//			DatabaseRistorante.getInstance().aggiungiRistorante(request.getRistorante());
//			
//			DatabaseSingleton.getInstance().aggiungiUtente(ristoratore);
//			String s = "Benvenuto ristoratore " + ristoratore.getNome() + " " + ristoratore.getCognome() + ", ristorante: " + ristoratore.getRistorante().getNome();
//			return new ResponseEntity<>(s, HttpStatus.ACCEPTED);
//		}
//
//	}
	
//	@PostMapping("/login")
//	public ResponseEntity<LoginResponseDto> login(@RequestBody LoginUtenteDto request) {
//		if (request.getUsername().isBlank() || request.getPassword().isBlank()) {
//			String err = "riempi entrambi i campi";
//			LoginResponseDto response = new LoginResponseDto();
//			response.setMessaggio(err);
//			return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
//		}
//		
//		Utente ut = DatabaseSingleton.getInstance().trovaUtente(request.getUsername(), request.getPassword());
//		if(ut != null) {
//			String ok = "Login ok";
//			LoginResponseDto response = new LoginResponseDto();
//			response.setUsername(ut.getUsername());
//			response.setPassword(ut.getPassword());
//			response.setNome(ut.getNome());
//			response.setCognome(ut.getCognome());
//			response.setRuolo(ut.getRuolo());
//			response.setMessaggio(ok);
//			return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
//		}
//		else {
//			String err = "Credenziali non valide";
//			LoginResponseDto response = new LoginResponseDto();
//			response.setMessaggio(err);
//			return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
//		}
//	}
//	
//	@PostMapping("/visualizzaRistoranti")
//	public ResponseEntity<List<Ristorante>> visualizzaRistoranti(){
//		
//		List<Ristorante> ristoranti = DatabaseRistorante.getInstance().getListaRistoranti();
//		//da piatti null, trovare piatti per ristorante
//		
//		if(!ristoranti.isEmpty()) {
//			return new ResponseEntity<>(ristoranti, HttpStatus.OK);
//		}
//		else {
//			return new ResponseEntity<List<Ristorante>>(HttpStatus.NO_CONTENT);
//		}
//	}
//	
//	@PostMapping("/visualizzaPiattiRistorante")
//	public ResponseEntity<List<Piatto>> getPiattiRistorante(@RequestBody Ristorante request) {
//
//		// trova ristorante per id
////		List<Piatto> piattiRistorante = DatabaseRistorante.getInstance().getPiattiRistorantePerId(request.getId());
//		List<Piatto> piattiRistorante = request.getPiatti();
//
//		if (!piattiRistorante.isEmpty()) {
//			return new ResponseEntity<>(piattiRistorante, HttpStatus.OK);
//		}
//		
//		else {
//			return ResponseEntity.noContent().build();
//		}
//	}
	
	
//	@PostMapping("/visualizzaRistoranti")
//	public ResponseEntity<List<Ristorante>> visualizzaRistoranti(){
//		
//		List<Ristorante> ristoranti = DatabaseRistorante.getInstance().getListaRistoranti();
//		
//		if(!ristoranti.isEmpty()) {
//			VisualizzaRistorantiResponseDto response = new VisualizzaRistorantiResponseDto();
//			response.setRistoranti(ristoranti);
//			return new ResponseEntity<List<Ristorante>>((List<Ristorante>) response, HttpStatus.OK);
//		}
//		else {
//			return new ResponseEntity<List<Ristorante>>(HttpStatus.NO_CONTENT);
//		}
//	}

//}
