package com.test.BookStore.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.test.BookStore.entities.Student;
import com.test.BookStore.repo.StudentRepository;
import com.test.BookStore.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;

public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	 public JwtService jwtService;
	
	@Autowired 
	private  UserDetailsService userDetailsService;

	@Autowired
	 public StudentRepository studentRepository;
	
	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain)
			throws ServletException, IOException {
		
		String authHeader ;
		String token;
		String username;
		authHeader = request.getHeader("Authorization");
		if(authHeader == null ||!authHeader.startsWith("Bearer ")) {
			System.err.println("Authorization failed in doFilterInternal");
			filterChain.doFilter(request, response);
		}
		token = authHeader.substring(7);
		username = jwtService.extractUsername(token);
		
		if(username ==null || SecurityContextHolder.getContext().getAuthentication()!=null) {
			return;
		}
		
		System.err.println("(Student) userDetailsService.loadUserByUsername(username)");
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		System.err.println("Successfully Done");

		if(!jwtService.isTokenValid(token, userDetails)) {
			System.err.println("Token is not valid jwtService.isTokenValid(token, student)");
		return;
		}
		else {
UsernamePasswordAuthenticationToken authentication = 
new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));	
SecurityContextHolder.getContext().setAuthentication(authentication);
		}
	    filterChain.doFilter(request, response);

	}

}
