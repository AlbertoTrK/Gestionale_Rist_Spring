package org.elis.progettoristorante.controllertest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.elis.progettoristorante.ProgettoRistoranteApplication;
import org.elis.progettoristorante.dto.LoginDTO;
import org.elis.progettoristorante.dto.RegistraRistoratoreDTO;
import org.elis.progettoristorante.dto.RegistraUtenteDTO;
import org.elis.progettoristorante.model.Ruolo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
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
class AllControllerTest {
	
	private final WebApplicationContext wac;
	private MockMvc mock;
	
	private AllControllerTest (WebApplicationContext w) {
		wac = w;
	}
	
	@BeforeEach
	public void creaMock() {
		mock = MockMvcBuilders.webAppContextSetup(wac).apply(SecurityMockMvcConfigurers.springSecurity()).build();
	}
	
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
	void registraUtenteNonValido() throws Exception {
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
		mock.perform(MockMvcRequestBuilders.post("/all/loginToken").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
	}

	@Test @Order(3)
	void loginOk() throws Exception {
		LoginDTO lDTO = new LoginDTO();
		lDTO.setUsername("GiujoTwitchDB");
		lDTO.setPassword("Giujone3000_1");
		String json = new ObjectMapper().writeValueAsString(lDTO);
		mock.perform(MockMvcRequestBuilders.post("/all/loginToken").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(MockMvcResultMatchers.status().isAccepted()).andReturn();
	}
	
	@Test
	@Order(4)
	void verificaLogin() throws Exception { // verificare
		LoginDTO lDTO = new LoginDTO();
		lDTO.setUsername("GiujoTwitchDB");
		lDTO.setPassword("Giujone3000_1");
		String json = new ObjectMapper().writeValueAsString(lDTO);
		mock.perform(MockMvcRequestBuilders.post("/all/loginToken").contentType(MediaType.APPLICATION_JSON)
				.content(json).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isAccepted())

				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.username").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.username").value("GiujoTwitchDB")).andDo(print());
	}
	
	@Test @Order(4)
	void loginErrato() throws Exception {
		LoginDTO lDTO = new LoginDTO();
		lDTO.setUsername("Lor234tre!");
		lDTO.setPassword("Pass1231_!");
		String json = new ObjectMapper().writeValueAsString(lDTO);
		mock.perform(MockMvcRequestBuilders.post("/all/loginToken").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
	}
}
