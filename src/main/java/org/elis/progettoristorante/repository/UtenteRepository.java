package org.elis.progettoristorante.repository;

import java.util.Optional;

import org.elis.progettoristorante.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UtenteRepository extends JpaRepository<Utente, Long>{
	
	@Query("select u from Utente u where u.username = :username and u.password = :password")
	public Optional<Utente> login(String username, String password);
	
	//metodo per login senza query
	public Optional<Utente> findUtenteByUsernameAndPassword(String username, String password);
	
	public Optional<Utente> findUtenteByUsername(String username);
}
