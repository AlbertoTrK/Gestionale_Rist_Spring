package org.elis.progettoristorante.security;

import java.io.IOException;

import org.elis.progettoristorante.model.Utente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterAutenticazione extends OncePerRequestFilter{
	
	@Autowired
	GestoreToken gestoreToken;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");
		
		if(authHeader != null && authHeader.startsWith("Bearer ")) {
			String tokenJWT = authHeader.substring(7);
			if(gestoreToken.tokenAncoraValido(tokenJWT) && SecurityContextHolder.getContext().getAuthentication() == null) {
				Utente u = gestoreToken.getUtenteDaToken(tokenJWT);
				UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(u, null, u.getAuthorities());
				upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(upat);
			}
		}
		filterChain.doFilter(request, response);
	}

}
