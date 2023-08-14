package com.cg.backend.shoppingcart.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cg.backend.shoppingcart.dto.JwtRequest;
import com.cg.backend.shoppingcart.dto.JwtResponse;
import com.cg.backend.shoppingcart.repository.UserRepository;
import com.cg.backend.shoppingcart.service.UserDetailsServiceImpl;
import com.cg.backend.shoppingcart.utils.JwtUtility;
import com.cg.backend.shoppingcart.entities.*;


@RestController
public class HomeController {
	@Autowired
	private AuthenticationManager authenticationManager;
	 @Autowired
	 private UserDetailsServiceImpl userDetailsService;
	 @Autowired
	 private JwtUtility jwtUtil;
	 @Autowired
	 private UserRepository userRepo;
	 public static final String TOKEN_PREFIX = "Bearer ";
	 public static final String HEADER_STRING = "Authorization";

	 
	@GetMapping("/home")
	public String home() {
		return "this is home page";
	}
	@PostMapping({"/authenticate"})
    public JwtResponse createAuthenticationToken(@RequestBody JwtRequest authenticationRequest, HttpServletResponse response) throws BadCredentialsException, DisabledException, UsernameNotFoundException, IOException, JSONException, ServletException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password", e);
        }
        catch (DisabledException disabledException){
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "User Is Not Activated");
            return null;
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        User user = userRepo.findFirstByEmail(authenticationRequest.getUsername());


        final String jwt = jwtUtil.generateToken(userDetails);

        return new JwtResponse(jwt);

    }
	
}
