package com.chaitanya.auth.security;
import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.chaitanya.auth.entity.ApplicationUser;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
private AuthenticationManager authenticationManager;
	
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		try {
			ApplicationUser user = new ObjectMapper().readValue(request.getInputStream(), ApplicationUser.class);
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));
		} 
		catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				return null;
		
	}
	
	@Override
	public void successfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain, Authentication auth) {
		
		String token = Jwts.builder()
                .setSubject(((ApplicationUser) auth.getPrincipal()).getUserName())
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET.getBytes())
                .compact();
		response.addHeader(SecurityConstants.HEADER, SecurityConstants.TOKEN_PREFIX + token);
		
	}
}
