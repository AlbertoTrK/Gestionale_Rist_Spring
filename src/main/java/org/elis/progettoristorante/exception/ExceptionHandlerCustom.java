package org.elis.progettoristorante.exception;

import org.elis.progettoristorante.dto.ErrorMsgDTO;
import org.elis.progettoristorante.exception.model.DatoNonInseritoException;
import org.elis.progettoristorante.exception.model.ListaVuotaException;
import org.elis.progettoristorante.exception.model.OggettoGiàEsistenteException;
import org.elis.progettoristorante.exception.model.OggettoNonTrovatoException;
import org.elis.progettoristorante.exception.model.OperazioneNonPossibileException;
import org.elis.progettoristorante.exception.model.UtenteGiàEsistenteException;
import org.elis.progettoristorante.exception.model.UtenteNonTrovatoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerCustom {
	
	@ExceptionHandler(UtenteNonTrovatoException.class)
	public ResponseEntity<ErrorMsgDTO> getUtenteNonTrovato(UtenteNonTrovatoException e){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMsgDTO(e.getMessage()));
	}
	
	@ExceptionHandler(DatoNonInseritoException.class)
	public ResponseEntity<ErrorMsgDTO> getDatoNonInserito(DatoNonInseritoException e){
		ErrorMsgDTO emDTO=new ErrorMsgDTO("campo \""+e.getNomeCampo()+"\" non correttamente inserito");
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(emDTO);
	}
	
	@ExceptionHandler(UtenteGiàEsistenteException.class)
	public ResponseEntity<ErrorMsgDTO> getUtenteGiàEsistente(UtenteGiàEsistenteException e){
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorMsgDTO(e.getMessage()));
	}
	
	@ExceptionHandler(ListaVuotaException.class)
	public ResponseEntity<ErrorMsgDTO> getListaVuotaExc(ListaVuotaException e){
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ErrorMsgDTO(e.getMessage()));
	}
	
	@ExceptionHandler(OggettoNonTrovatoException.class)
	public ResponseEntity<ErrorMsgDTO> getOggettoNonTrovato(OggettoNonTrovatoException e){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMsgDTO(e.getMessage()));
	}
	
	@ExceptionHandler(OggettoGiàEsistenteException.class)
	public ResponseEntity<ErrorMsgDTO>  getOggettoGiàEsistente(OggettoGiàEsistenteException e){
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ErrorMsgDTO(e.getMessage()));
	}
	
	@ExceptionHandler(OperazioneNonPossibileException.class)
	public ResponseEntity<ErrorMsgDTO>  getOperazioneNonPosssibile(OperazioneNonPossibileException e){
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ErrorMsgDTO(e.getMessage()));
	}
}
