package org.elis.progettoristorante.security;

import org.elis.progettoristorante.exception.model.UtenteNonTrovatoException;
import org.elis.progettoristorante.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfigurazione {
	
	@Autowired
	UtenteRepository repo;
	
	@Bean
	public UserDetailsService userDetailsService() {
		return u->repo.findUtenteByUsername(u).orElseThrow(() -> new UtenteNonTrovatoException("nessun utente"));
	}
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	public AuthenticationManager authenticationManager(AuthenticationConfiguration conf) throws Exception{
		return conf.getAuthenticationManager();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAP = new DaoAuthenticationProvider();
		daoAP.setUserDetailsService(userDetailsService());
		daoAP.setPasswordEncoder(encoder());
		return daoAP;
	}
}
