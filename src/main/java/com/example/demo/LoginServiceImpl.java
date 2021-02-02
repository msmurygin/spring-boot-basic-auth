package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoginServiceImpl implements LoginService{

  @Autowired
  AuthenticationManager authenticationManager;

  @Override
  public LoginResponse login(LoginRequest request) {
    UsernamePasswordAuthenticationToken authenticationTokenRequest = new
        UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
    try {
      Authentication authentication = this.authenticationManager.authenticate(authenticationTokenRequest);
      SecurityContext securityContext = SecurityContextHolder.getContext();
      securityContext.setAuthentication(authentication);

     String user =  (String) authentication.getPrincipal();
      List<String> roles = authentication.getAuthorities().stream()
          .map(item -> item.getAuthority())
          .collect(Collectors.toList());
     return new LoginResponse(user, user, roles);
    } catch (BadCredentialsException ex) {
      // handle User not found exception
      //...
      throw  ex;
    }
  }
}
