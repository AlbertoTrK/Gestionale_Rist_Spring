package org.elis.progettoristorante.controllertest;

import java.util.HashMap;
import java.util.Map;

import org.elis.progettoristorante.ProgettoRistoranteApplication;
import org.elis.progettoristorante.dto.AggiungiAdOrdineDTO;
import org.elis.progettoristorante.dto.AggiungiOrdineDTO;
import org.elis.progettoristorante.dto.ModificaUtenteDTO;
import org.elis.progettoristorante.filtri.FiltraPiattoRequest;
import org.elis.progettoristorante.filtri.FiltraUtentiRequest;
import org.elis.progettoristorante.model.Utente;
import org.elis.progettoristorante.security.GestoreToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ContextConfiguration(classes = ProgettoRistoranteApplication.class)
@TestMethodOrder(OrderAnnotation.class)
class UtenteControllerTest {
	
	private final WebApplicationContext wac;
	private MockMvc mock;
	
	private UtenteControllerTest (WebApplicationContext w) {
		wac = w;
	}
	
	@BeforeEach
	public void creaMock() {
		mock = MockMvcBuilders.webAppContextSetup(wac).apply(SecurityMockMvcConfigurers.springSecurity()).build();
	}
	
	@Autowired
	private GestoreToken gestoreT;
	
	@Test @WithMockUser(username = "PaoloPac", roles = "CLIENTE")
	void findAllRistoranti() throws Exception {
		mock.perform(MockMvcRequestBuilders.get("/utente/ristoranti").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test @WithMockUser(username = "PaoloPac", roles = "CLIENTE")
	void visualizzaPiattiRistorante() throws Exception {
		long idRistorante = 1;
		
		mock.perform(MockMvcRequestBuilders.get("/utente/visualizzaPiattiRistorante/{idRistorante}", idRistorante).accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test @WithMockUser(roles = "CLIENTE")
	void visualizzaPiattiConIng() throws Exception {
		long idIngrediente = 1;
		mock.perform(MockMvcRequestBuilders.get("/utente/piattiConIngrediente/{idIngrediente}", idIngrediente).accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test @WithMockUser(roles = "CLIENTE")
	void ingredientiDiPiatto() throws Exception {
		long idPiatto = 1;
		
		mock.perform(MockMvcRequestBuilders.get("/utente/piattiConIngrediente/{idIngrediente}", idPiatto).accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test @WithMockUser(roles = "CLIENTE")
	void aggiungiOrdine() throws Exception {
		AggiungiOrdineDTO aoDTO = new AggiungiOrdineDTO();
		Map<Long, Integer> prodottoQuantitàMap = new HashMap<>();
		prodottoQuantitàMap.put(1L, 2);
		aoDTO.setIdProdottoQuantità(prodottoQuantitàMap);
		aoDTO.setIdRistorante(1);
		aoDTO.setIdUtente(1);
		String json = new ObjectMapper().writeValueAsString(aoDTO);
		mock.perform(MockMvcRequestBuilders.post("/utente/aggiungiOrdine").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test
	void aggiungiAdOrdine() throws Exception {
		Utente u = new Utente();
		u.setNome("Paolo");
		u.setCognome("Pacello");
		u.setUsername("PaoloPac");
		u.setPassword("Dr_pacello97");
		String token = gestoreT.creaToken(u);
		AggiungiAdOrdineDTO aaoDTO = new AggiungiAdOrdineDTO();
		aaoDTO.setIdOrdine(1);
		aaoDTO.setIdRistorante(1);
		Map<Long, Integer> prodottoQuantitàMap = new HashMap<>();
		prodottoQuantitàMap.put(1L, 2);
		aaoDTO.setIdProdottoQuantità(prodottoQuantitàMap);
		String json = new ObjectMapper().writeValueAsString(aaoDTO);
		mock.perform(MockMvcRequestBuilders.post("/utente/aggiungiAdOrdine").contentType(MediaType.APPLICATION_JSON).content(json)
			.header("Authorization", "Bearer " + token))
			.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test @WithMockUser(roles = "CLIENTE")
	void storicoOrdiniRifEEvasi() throws Exception {
		long idUtente = 1;
		
		mock.perform(MockMvcRequestBuilders.get("/utente/storicoOrdiniEvasiERif/{idUtente}", idUtente).accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test @WithMockUser(roles = "CLIENTE")
	void storicoOrdiniNonEvaNonRif() throws Exception {
		long idUtente = 1;
		
		mock.perform(MockMvcRequestBuilders.get("/utente/storicoOrdiniNonEvasiNonRif/{idUtente}", idUtente).accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test @WithMockUser(roles = "CLIENTE")
	void filtraUtenti() throws Exception {
		FiltraUtentiRequest fuReq = new FiltraUtentiRequest();
		fuReq.setCognome("Emi");
		
		String json = new ObjectMapper().writeValueAsString(fuReq);
		mock.perform(MockMvcRequestBuilders.post("/utente/filtraUtenti").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test @WithMockUser(roles = "CLIENTE")
	void filtraPiatti() throws Exception {
		FiltraPiattoRequest fpReq = new FiltraPiattoRequest();
		fpReq.setNome("Carb");
		
		String json = new ObjectMapper().writeValueAsString(fpReq);
		mock.perform(MockMvcRequestBuilders.post("/utente/filtraPiatti").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test @WithMockUser(roles = "CLIENTE")
	void modificaUsernameAndPassword() throws Exception {
		ModificaUtenteDTO muDTO = new ModificaUtenteDTO();
		muDTO.setUsername("PaoloPac");
		muDTO.setPassword("Dr_pacello97");
		muDTO.setNuovaUsername("PaoloPacello98");
		muDTO.setNuovaPassword("Mr_pacello98!");
		
		String json = new ObjectMapper().writeValueAsString(muDTO);
		mock.perform(MockMvcRequestBuilders.post("/utente/modificaUsernameEPassword").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(MockMvcResultMatchers.status().isAccepted()).andReturn();
	}
}
