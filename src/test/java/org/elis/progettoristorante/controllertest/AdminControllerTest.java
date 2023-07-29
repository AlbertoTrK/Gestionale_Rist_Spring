package org.elis.progettoristorante.controllertest;

import org.elis.progettoristorante.ProgettoRistoranteApplication;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ContextConfiguration(classes = ProgettoRistoranteApplication.class)
@TestMethodOrder(OrderAnnotation.class)
class AdminControllerTest {
	
	private final WebApplicationContext wac;
	private MockMvc mock;
	
	private AdminControllerTest (WebApplicationContext w) {
		wac = w;
	}
	
	@BeforeEach
	public void creaMock() {
		mock = MockMvcBuilders.webAppContextSetup(wac).apply(SecurityMockMvcConfigurers.springSecurity()).build();
	}
	
	@Test @WithMockUser(roles = "ADMIN")
	void findAllRistorantiAdmin() throws Exception {
		mock.perform(MockMvcRequestBuilders.get("/admin/ristoranti").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test @WithMockUser(roles = "ADMIN")
	void storicoOrdiniUtente() throws Exception {
		long idUtente = 1;
		
		mock.perform(MockMvcRequestBuilders.get("/admin/storicoOrdini/{idUtente}", idUtente).accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test @WithMockUser(roles = "ADMIN")
	void vediFatturato() throws Exception {
		long idRistorante = 1;
		
		mock.perform(MockMvcRequestBuilders.get("/admin/fatturato/{idRistorante}", idRistorante).accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test @WithMockUser(roles = "ADMIN")
	void vediFatturatoPerMese() throws Exception {
		long idRistorante = 1;
		
		mock.perform(MockMvcRequestBuilders.get("/admin/fatturatoPerMese/{idRistorante}", idRistorante).accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test @WithMockUser(roles = "ADMIN")
	void trovaTuttiGliOrdini() throws Exception {
		mock.perform(MockMvcRequestBuilders.get("/admin/visualizzaTuttiOrdini").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	
}
