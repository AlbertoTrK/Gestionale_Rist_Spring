package org.elis.progettoristorante.controllertest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.ArrayList;
import java.util.List;

import org.elis.progettoristorante.ProgettoRistoranteApplication;
import org.elis.progettoristorante.dto.CategoriaDTO;
import org.elis.progettoristorante.dto.IngredienteDTO;
import org.elis.progettoristorante.dto.PiattoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ContextConfiguration(classes = ProgettoRistoranteApplication.class)
@TestMethodOrder(OrderAnnotation.class)
class RistoratoreControllerTest {
	
	private final WebApplicationContext wac;
	private MockMvc mock;
	
	private RistoratoreControllerTest (WebApplicationContext w) {
		wac = w;
	}
	
	@BeforeEach
	public void creaMock() {
		mock = MockMvcBuilders.webAppContextSetup(wac).apply(SecurityMockMvcConfigurers.springSecurity()).build();
	}
	
	@Test @Order(1) @WithMockUser(roles = "RISTORATORE")
	void aggiungiIngrediente() throws Exception {
		IngredienteDTO iDTO = new IngredienteDTO();
		iDTO.setNome("Pesto");
		String json = new ObjectMapper().writeValueAsString(iDTO);
		mock.perform(MockMvcRequestBuilders.post("/ristoratore/aggiungiIngrediente").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(MockMvcResultMatchers.status().isAccepted()).andReturn();
	}
	
	@Test @Order(2) @WithMockUser(roles = "RISTORATORE")
	void aggiungiIngredienteGiàEsist() throws Exception {
		IngredienteDTO iDTO = new IngredienteDTO();
		iDTO.setNome("Pesto");
		String json = new ObjectMapper().writeValueAsString(iDTO);
		mock.perform(MockMvcRequestBuilders.post("/ristoratore/aggiungiIngrediente").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(MockMvcResultMatchers.status().isNotAcceptable()).andReturn();
	}
	
	@Test @WithMockUser(roles = "RISTORATORE")
	void aggiungiPiatto() throws Exception {
		PiattoDTO pDTO = new PiattoDTO();
		pDTO.setNome("Linguine al pesto");
		pDTO.setId_categoria(1);
		pDTO.setId_ristorante(1);
		pDTO.setPrezzo(7.90);
		List<Long> idIngredienti = new ArrayList<>();
		idIngredienti.add((long) 7);
		pDTO.setId_ingredienti(idIngredienti);
		
		String json = new ObjectMapper().writeValueAsString(pDTO);
		mock.perform(MockMvcRequestBuilders.post("/ristoratore/aggiungiPiatto").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test @WithMockUser(roles = "RISTORATORE")
	void visualizzaIngredienti() throws Exception {
		mock.perform(MockMvcRequestBuilders.get("/ristoratore/visualizzaIngredienti").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isAccepted()).andReturn();
	}
	
	@Test @WithMockUser(roles = "RISTORATORE") //non funziona
	void verificaRisultatoVisualizzaIngredienti() throws Exception {
		mock.perform(MockMvcRequestBuilders.get("/ristoratore/visualizzaIngredienti").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isAccepted())
		.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].nome").exists())
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].nome").value("pasta"))
		.andDo(MockMvcResultHandlers.print());
	}
	
	@Test @WithMockUser(roles = "RISTORATORE")
	void aggiungiCategoria() throws Exception {
		CategoriaDTO cDTO = new CategoriaDTO();
		cDTO.setNome("Antipasti");
		String json = new ObjectMapper().writeValueAsString(cDTO);
		mock.perform(MockMvcRequestBuilders.post("/ristoratore/aggiungiCategoria").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test @WithMockUser(roles = "RISTORATORE")
	void aggiungiCategoriaGiàEsist() throws Exception {
		CategoriaDTO cDTO = new CategoriaDTO();
		cDTO.setNome("primi");
		String json = new ObjectMapper().writeValueAsString(cDTO);
		mock.perform(MockMvcRequestBuilders.post("/ristoratore/aggiungiCategoria").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(MockMvcResultMatchers.status().isNotAcceptable()).andReturn();
	}
	
	@Test @WithMockUser(roles = "RISTORATORE")
	void visualizzaCategorie() throws Exception {
		mock.perform(MockMvcRequestBuilders.get("/ristoratore/visualizzaCategorie").accept(MediaType.APPLICATION_JSON)).
		andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test @WithMockUser(roles = "RISTORATORE")
	void cambiaStatoOrdine() throws Exception {
		long idOrdine = 1;
		
		mock.perform(MockMvcRequestBuilders.get("/ristoratore/cambiastatoordine/{idOrdine}", idOrdine).accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test @WithMockUser(roles = "RISTORATORE")
	void rifiutaOrdine() throws Exception {
		long idOrdine = 1;
		
		mock.perform(MockMvcRequestBuilders.get("/ristoratore/rifiutaOrdine/{idOrdine}", idOrdine).accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test @WithMockUser(roles = "RISTORATORE")
	void trovaOrdiniRicEinElab() throws Exception {
		long idRistorante = 2;

		mock.perform(MockMvcRequestBuilders
				.get("/ristoratore/visualizzaOrdiniRicevutiEinElaborazione/{idRistorante}", idRistorante)
				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}

}
