package org.elis.progettoristorante;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.elis.progettoristorante.dto.AggiungiAdOrdineDTO;
import org.elis.progettoristorante.dto.AggiungiOrdineDTO;
import org.elis.progettoristorante.dto.CategoriaDTO;
import org.elis.progettoristorante.dto.IngredienteDTO;
import org.elis.progettoristorante.dto.LoginDTO;
import org.elis.progettoristorante.dto.PiattoDTO;
import org.elis.progettoristorante.dto.RegistraRistoratoreDTO;
import org.elis.progettoristorante.dto.RegistraUtenteDTO;
import org.elis.progettoristorante.filtri.FiltraPiattoRequest;
import org.elis.progettoristorante.filtri.FiltraUtentiRequest;
import org.elis.progettoristorante.model.Ruolo;
import org.elis.progettoristorante.model.Utente;
import org.elis.progettoristorante.security.GestoreToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
class ProgettoRistoranteApplicationTests {
	
	private final WebApplicationContext wac;
	private MockMvc mock;
	
	private ProgettoRistoranteApplicationTests (WebApplicationContext w) {
		wac = w;
	}
	
	@BeforeEach
	public void creaMock() {
		mock = MockMvcBuilders.webAppContextSetup(wac).apply(SecurityMockMvcConfigurers.springSecurity()).build();
	}
	
	@Autowired
	private GestoreToken gestoreT;
	
	@Test @Order(1)
	void registraUtenteOk() throws Exception {
		RegistraUtenteDTO ruDTO = new RegistraUtenteDTO();
		ruDTO.setNome("Luca");
		ruDTO.setCognome("DePaoli");
		ruDTO.setUsername("Luca90_!");
		ruDTO.setPassword("Password123!_");
		ruDTO.setRuolo(Ruolo.CLIENTE);
		String json = new ObjectMapper().writeValueAsString(ruDTO);
		mock.perform(MockMvcRequestBuilders.post("/all/utente/registrazione").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(MockMvcResultMatchers.status().isAccepted()).andReturn();
	}
	
	@Test @Order(2)
	void registraUtenteErrore() throws Exception {
		RegistraUtenteDTO ruDTO = new RegistraUtenteDTO();
		ruDTO.setNome("Luca");
		ruDTO.setCognome("DePaoli");
		ruDTO.setUsername("Luca90_!");
		ruDTO.setPassword("Password123!_");
		ruDTO.setRuolo(Ruolo.CLIENTE);
		String json = new ObjectMapper().writeValueAsString(ruDTO);
		mock.perform(MockMvcRequestBuilders.post("/all/utente/registrazione").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(MockMvcResultMatchers.status().isForbidden()).andReturn();
	}
	
	@Test @Order(3)
	void registraUtenteNonValid() throws Exception {
		RegistraUtenteDTO ruDTO = new RegistraUtenteDTO();
		ruDTO.setNome("Marco");
		ruDTO.setCognome("Razzi");
		ruDTO.setUsername("Mark90_!");
		ruDTO.setPassword("Password!_");
		ruDTO.setRuolo(Ruolo.CLIENTE);
		String json = new ObjectMapper().writeValueAsString(ruDTO);
		mock.perform(MockMvcRequestBuilders.post("/all/utente/registrazione").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
	}
	
	@Test @Order(1)
	void registraRistoratoreOk() throws Exception {
		RegistraRistoratoreDTO rrDTO = new RegistraRistoratoreDTO();
		rrDTO.setNome("Piero");
		rrDTO.setCognome("Foglia");
		rrDTO.setUsername("PieroFo87!");
		rrDTO.setPassword("KFogli87_!");
		rrDTO.setNomeRistorante("Il molo");
		rrDTO.setRuolo(Ruolo.RISTORATORE);
		String json = new ObjectMapper().writeValueAsString(rrDTO);
		mock.perform(MockMvcRequestBuilders.post("/all/ristoratore/registrazione").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(MockMvcResultMatchers.status().isAccepted()).andReturn();
	}
	
	@Test @Order(3)
	void loginSenzaDati() throws Exception {
		mock.perform(MockMvcRequestBuilders.post("/all/login").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
	}

	@Test @Order(3)
	void loginOk() throws Exception {
		LoginDTO lDTO = new LoginDTO();
		lDTO.setUsername("PaoloPac");
		lDTO.setPassword("Dr_pacello97");
		String json = new ObjectMapper().writeValueAsString(lDTO);
		mock.perform(MockMvcRequestBuilders.post("/all/login").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(MockMvcResultMatchers.status().isAccepted()).andReturn();
	}
	
	@Test @Order(4)
	void loginErrato() throws Exception {
		LoginDTO lDTO = new LoginDTO();
		lDTO.setUsername("Lor234tre!");
		lDTO.setPassword("Pass1231_!");
		String json = new ObjectMapper().writeValueAsString(lDTO);
		mock.perform(MockMvcRequestBuilders.post("/all/login").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
	}
	
	@Test @Order(4) @WithMockUser(username = "PaoloPac", roles = "CLIENTE")
	void findAllRistoranti() throws Exception {
		mock.perform(MockMvcRequestBuilders.get("/utente/ristoranti").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test @Order(4) @WithMockUser(roles = "ADMIN")
	void findAllRistorantiAdmin() throws Exception {
		mock.perform(MockMvcRequestBuilders.get("/admin/ristoranti").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test @Order(6) @WithMockUser(username = "PaoloPac", roles = "CLIENTE")
	void visualizzaPiattiRistorante() throws Exception {
		long idRistorante = 1;
		
		mock.perform(MockMvcRequestBuilders.get("/utente/visualizzaPiattiRistorante/{idRistorante}", idRistorante).accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test @Order(4) @WithMockUser(roles = "RISTORATORE")
	void aggiungiIngrediente() throws Exception {
		IngredienteDTO iDTO = new IngredienteDTO();
		iDTO.setNome("Pasta");
		String json = new ObjectMapper().writeValueAsString(iDTO);
		mock.perform(MockMvcRequestBuilders.post("/ristoratore/aggiungiIngrediente").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(MockMvcResultMatchers.status().isAccepted()).andReturn();
	}
	
	@Test @Order(5) @WithMockUser(roles = "RISTORATORE")
	void aggiungiPiatto() throws Exception {
		PiattoDTO pDTO = new PiattoDTO();
		pDTO.setNome("Carbonara");
		pDTO.setId_categoria(1);
		pDTO.setId_ristorante(1);
		pDTO.setPrezzo(9.90);
		List<Long> idIngredienti = new ArrayList<>();
		idIngredienti.add((long) 1);
		pDTO.setId_ingredienti(idIngredienti);
		
		String json = new ObjectMapper().writeValueAsString(pDTO);
		mock.perform(MockMvcRequestBuilders.post("/ristoratore/aggiungiPiatto").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test @Order(5) @WithMockUser(roles = "RISTORATORE")
	void aggiungiIngredienteGiàEsist() throws Exception {
		IngredienteDTO iDTO = new IngredienteDTO();
		iDTO.setNome("Pasta");
		String json = new ObjectMapper().writeValueAsString(iDTO);
		mock.perform(MockMvcRequestBuilders.post("/ristoratore/aggiungiIngrediente").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(MockMvcResultMatchers.status().isNotAcceptable()).andReturn();
	}
	
	@Test @Order(6) @WithMockUser(roles = "RISTORATORE")
	void visualizzaIngredienti() throws Exception {
		mock.perform(MockMvcRequestBuilders.get("/ristoratore/visualizzaIngredienti").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isAccepted()).andReturn();
	}
	
	@Test @Order(4) @WithMockUser(roles = "RISTORATORE")
	void aggiungiCategoria() throws Exception {
		CategoriaDTO cDTO = new CategoriaDTO();
		cDTO.setNome("Primi");
		String json = new ObjectMapper().writeValueAsString(cDTO);
		mock.perform(MockMvcRequestBuilders.post("/ristoratore/aggiungiCategoria").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test @Order(5) @WithMockUser(roles = "RISTORATORE")
	void aggiungiCategoriaGiàEsist() throws Exception {
		CategoriaDTO cDTO = new CategoriaDTO();
		cDTO.setNome("Primi");
		String json = new ObjectMapper().writeValueAsString(cDTO);
		mock.perform(MockMvcRequestBuilders.post("/ristoratore/aggiungiCategoria").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(MockMvcResultMatchers.status().isNotAcceptable()).andReturn();
	}
	
	@Test @Order(6) @WithMockUser(roles = "RISTORATORE")
	void visualizzaCategorie() throws Exception {
		mock.perform(MockMvcRequestBuilders.get("/ristoratore/visualizzaCategorie").accept(MediaType.APPLICATION_JSON)).
		andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test @Order(6) @WithMockUser(roles = "CLIENTE")
	void visualizzaPiattiConIng() throws Exception {
		long idIngrediente = 1;
		mock.perform(MockMvcRequestBuilders.get("/utente/piattiConIngrediente/{idIngrediente}", idIngrediente).accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test @Order(6) @WithMockUser(roles = "CLIENTE")
	void ingredientiDiPiatto() throws Exception {
		long idPiatto = 1;
		
		mock.perform(MockMvcRequestBuilders.get("/utente/piattiConIngrediente/{idIngrediente}", idPiatto).accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test @Order(6) @WithMockUser(roles = "CLIENTE")
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
	
	@Test @Order(6) @WithMockUser(roles = "CLIENTE")
	void aggiungiOrdine2() throws Exception {
		AggiungiOrdineDTO aoDTO = new AggiungiOrdineDTO();
		Map<Long, Integer> prodottoQuantitàMap = new HashMap<>();
		prodottoQuantitàMap.put(1L, 4);
		aoDTO.setIdProdottoQuantità(prodottoQuantitàMap);
		aoDTO.setIdRistorante(1);
		aoDTO.setIdUtente(1);
		String json = new ObjectMapper().writeValueAsString(aoDTO);
		mock.perform(MockMvcRequestBuilders.post("/utente/aggiungiOrdine").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test @Order(7)
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
	
	@Test @Order(7) @WithMockUser(roles = "RISTORATORE")
	void cambiaStatoOrdine() throws Exception {
		long idOrdine = 1;
		
		mock.perform(MockMvcRequestBuilders.get("/ristoratore/cambiastatoordine/{idOrdine}", idOrdine).accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test @Order(8) @WithMockUser(roles = "RISTORATORE")
	void rifiutaOrdine() throws Exception {
		long idOrdine = 1;
		
		mock.perform(MockMvcRequestBuilders.get("/ristoratore/rifiutaOrdine/{idOrdine}", idOrdine).accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test @Order(9) @WithMockUser(roles = "CLIENTE")
	void storicoOrdiniRifEEvasi() throws Exception {
		long idUtente = 1;
		
		mock.perform(MockMvcRequestBuilders.get("/utente/storicoOrdiniEvasiERif/{idUtente}", idUtente).accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test @Order(9) @WithMockUser(roles = "CLIENTE")
	void storicoOrdiniNonEvaNonRif() throws Exception {
		long idUtente = 1;
		
		mock.perform(MockMvcRequestBuilders.get("/utente/storicoOrdiniNonEvasiNonRif/{idUtente}", idUtente).accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test @Order(9) @WithMockUser(roles = "RISTORATORE")
	void trovaOrdiniRicEinElab() throws Exception {
		long idRistorante = 1;

		mock.perform(MockMvcRequestBuilders
				.get("/ristoratore/visualizzaOrdiniRicevutiEinElaborazione/{idRistorante}", idRistorante)
				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test @Order(9) @WithMockUser(roles = "ADMIN")
	void storicoOrdiniUtente() throws Exception {
		long idUtente = 1;
		
		mock.perform(MockMvcRequestBuilders.get("/admin/storicoOrdini/{idUtente}", idUtente).accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test @Order(9) @WithMockUser(roles = "ADMIN")
	void vediFatturato() throws Exception {
		long idRistorante = 1;
		
		mock.perform(MockMvcRequestBuilders.get("/admin/fatturato/{idRistorante}", idRistorante).accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test @Order(9) @WithMockUser(roles = "ADMIN")
	void trovaTuttiGliOrdini() throws Exception {
		mock.perform(MockMvcRequestBuilders.get("/admin/visualizzaTuttiOrdini").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test @Order(9) @WithMockUser(roles = "CLIENTE")
	void filtraUtenti() throws Exception {
		FiltraUtentiRequest fuReq = new FiltraUtentiRequest();
		fuReq.setCognome("DeP");
		
		String json = new ObjectMapper().writeValueAsString(fuReq);
		mock.perform(MockMvcRequestBuilders.post("/utente/filtraUtenti").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test @Order(9) @WithMockUser(roles = "CLIENTE")
	void filtraPiatti() throws Exception {
		FiltraPiattoRequest fpReq = new FiltraPiattoRequest();
		fpReq.setNome("Carb");
		
		String json = new ObjectMapper().writeValueAsString(fpReq);
		mock.perform(MockMvcRequestBuilders.post("/utente/filtraPiatti").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}


	@Test
	void contextLoads() {
	}

}
