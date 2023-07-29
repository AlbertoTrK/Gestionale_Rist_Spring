package org.elis.progettoristorante.security;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.elis.progettoristorante.exception.model.UtenteNonTrovatoException;
import org.elis.progettoristorante.model.Utente;
import org.elis.progettoristorante.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class GestoreToken {
	
	@Autowired
	UtenteRepository repo;
	
	private final static String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
	
	private Key getKey() {
		byte [] keyInByte = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyInByte);
	}
	
	private Claims creaClaim(Utente u) {
		Claims cl = Jwts.claims().setSubject(u.getUsername());
		cl.put("ruolo", u.getRuolo());
		cl.put("nome", u.getNome());
		cl.put("cognome", u.getCognome());
		return cl;
	}
	
	public String creaToken(Utente u) {
		long durataSessione = 1000 * 60 * 60 * 24 * 30 * 2;
		return Jwts.builder().setClaims(creaClaim(u)).
				setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + durataSessione))
				.signWith(getKey(), SignatureAlgorithm.HS256).compact();
	}
	
	//decodifica token
	
	private Claims estraiClaim(String token) {
		return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
	}
	
	public Utente getUtenteDaToken(String token) {
		Claims cl = estraiClaim(token);
		String username = cl.getSubject();
		return repo.findUtenteByUsername(username).orElseThrow(() -> new UtenteNonTrovatoException("nessun utente trovato con questa username"));
	}
	
	public LocalDateTime dataScadenza(String token) {
		Claims cl = estraiClaim(token);
		Date d = cl.getExpiration();
		return d.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}
	
	public String getNome(String token) {
		Claims cl = estraiClaim(token);
		String nome = (String) cl.get("nome");
		return nome;
	}
	
	public boolean tokenAncoraValido(String token) {
		return dataScadenza(token).isAfter(LocalDateTime.now());
	}

}
