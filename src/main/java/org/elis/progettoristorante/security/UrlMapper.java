package org.elis.progettoristorante.security;

import org.elis.progettoristorante.model.Ruolo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class UrlMapper {
	
	@Autowired
	FilterAutenticazione filter;
	
	@Autowired
	AuthenticationProvider provider;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.csrf(AbstractHttpConfigurer::disable)
		.headers().frameOptions().disable().and() //riga che serve per usare h2
		.authorizeHttpRequests(auth -> auth.requestMatchers("/all/**").permitAll()
				.requestMatchers("/utente/**").hasRole(Ruolo.CLIENTE.getRuolo())
				.requestMatchers("/ristoratore/**").hasRole(Ruolo.RISTORATORE.getRuolo())
				.requestMatchers("/admin/**").hasRole(Ruolo.ADMIN.getRuolo())
				.anyRequest().permitAll()
				)
		.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.cors(AbstractHttpConfigurer::disable)
		.authenticationProvider(provider)
		.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

}
