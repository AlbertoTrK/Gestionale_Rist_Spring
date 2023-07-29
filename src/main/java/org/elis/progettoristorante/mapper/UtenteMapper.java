package org.elis.progettoristorante.mapper;

import java.util.ArrayList;
import java.util.List;

import org.elis.progettoristorante.dto.LoginDTO;
import org.elis.progettoristorante.dto.RegistraRistoratoreDTO;
import org.elis.progettoristorante.dto.RegistraUtenteDTO;
import org.elis.progettoristorante.dto.UtenteDTO;
import org.elis.progettoristorante.model.Utente;
import org.elis.progettoristorante.model.Ristorante;
import org.springframework.stereotype.Component;

@Component
public class UtenteMapper {
	
	public UtenteDTO toUtenteDTO(Utente u) {
		UtenteDTO utDTO = new UtenteDTO();
		utDTO.setNome(u.getNome());
		utDTO.setCognome(u.getCognome());
		utDTO.setUsername(u.getUsername());
		utDTO.setId(u.getId());
		return utDTO;
	}
	
	public LoginDTO toLoginDTO(Utente u) {
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setUsername(u.getUsername());
		loginDTO.setPassword(u.getPassword());
		return loginDTO;
	}
	
	public Utente toUtente(RegistraUtenteDTO dto) {
		Utente u=new Utente();
		u.setNome(dto.getNome());
		u.setCognome(dto.getCognome());
		u.setUsername(dto.getUsername());
		u.setPassword(dto.getPassword());
		u.setRuolo(dto.getRuolo());
		return u;
	}
	
	public List<UtenteDTO> toUtenteDTOList(List<Utente> u){
		return u.stream().map(this::toUtenteDTO).toList();
	}
	
	public Utente toRistoratore(RegistraRistoratoreDTO dto) {
		Utente u=new Utente();
		u.setNome(dto.getNome());
		u.setCognome(dto.getCognome());
		u.setUsername(dto.getUsername());
		u.setPassword(dto.getPassword());
		u.setRuolo(dto.getRuolo());
		Ristorante r = new Ristorante();
		r.setUtente(u);
		r.setNome(dto.getNomeRistorante());
		List<Ristorante> ristorantiRistoratore = new ArrayList<>();
		ristorantiRistoratore.add(r);
		u.setRistorante(ristorantiRistoratore);
		return u;
	}
	
//	public Utente toRistoratore(RegistraRistoratoreDTO dto) {
//		Utente u=new Utente();
//		u.setNome(dto.getNome());
//		u.setCognome(dto.getCognome());
//		u.setUsername(dto.getUsername());
//		u.setPassword(dto.getPassword());
//		u.setRuolo(dto.getRuolo());
//		List<Ristorante> ristorantiRistoratore = new ArrayList<>();
//		for(String s : dto.getRistoranti) {
//			Ristorante r = new Ristorante();
//			r.setUtente(u);
//			r.setNome(s);
//			ristorantiRistoratore.add(r);
//			u.setRistorante(ristorantiRistoratore);
//		}
//		return u;
//	}

}
