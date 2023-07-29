package org.elis.progettoristorante.repotest;

import org.elis.progettoristorante.ProgettoRistoranteApplication;
import org.elis.progettoristorante.model.Utente;
import org.elis.progettoristorante.repository.UtenteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(classes = ProgettoRistoranteApplication.class)
public class TestUtenteRepo {
	
	@Autowired
	UtenteRepository repo;
	
	@Test
	public void login() {
		assertThat(repo.login("PaoloPac", "Dr_pacello97")).get().extracting(Utente::getNome).isEqualTo("Paolo");
	}
	
	@Test
	public void findUtenteByUsernameAndPassword() {
		assertThat(repo.findUtenteByUsernameAndPassword("PaoloPac", "Dr_pacello97")).get().extracting(Utente::getNome).isEqualTo("Paolo");
	}
	
	@Test
	public void findUtenteByUsername() {
		assertThat(repo.findUtenteByUsername("PaoloPac")).get().extracting(Utente::getNome).isEqualTo("Paolo");
	}

}
