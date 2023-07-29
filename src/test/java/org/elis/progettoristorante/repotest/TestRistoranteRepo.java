package org.elis.progettoristorante.repotest;

import org.elis.progettoristorante.ProgettoRistoranteApplication;
import org.elis.progettoristorante.model.Ristorante;
import org.elis.progettoristorante.model.Utente;
import org.elis.progettoristorante.repository.RistoranteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = ProgettoRistoranteApplication.class)
public class TestRistoranteRepo {
	
	@Autowired
	private RistoranteRepository ristoRepo;
	
	@Test
	public void findByUtenteId() {
		long idUtente = 3;
		Ristorante rist = ristoRepo.findByUtenteId(idUtente);
		assertEquals(idUtente, rist.getUtente().getId());
	}
	
	@Test
	public void findAllByUtenteId() {
		long idUtente = 3;
		List<Ristorante> ristoranti = ristoRepo.findAllByUtenteId(idUtente);
		for(Ristorante r : ristoranti) {
			assertEquals(idUtente, r.getUtente().getId());
		}
	}

}
