package com.etraveli.cardcost;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;


import com.etraveli.cardcost.config.JwtService;
import com.etraveli.cardcost.domain.dbo.Role;
import com.etraveli.cardcost.domain.dbo.Token;
import com.etraveli.cardcost.enums.TokenType;
import com.etraveli.cardcost.domain.dbo.User;
import com.etraveli.cardcost.domain.dto.AuthRequest;
import com.etraveli.cardcost.domain.dto.AuthResponse;
import com.etraveli.cardcost.service.internal.AuthenticationService;
import com.etraveli.cardcost.service.repository.TokenRepository;
import com.etraveli.cardcost.service.repository.UserRepository;
import jakarta.transaction.Transactional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Objects;
import java.util.UUID;

@Tag("AuthenticationServiceTest")

@SpringBootTest
@Transactional
public class AuthenticationServiceTest {

  @Autowired
  AuthenticationService authenticationService;
  @Autowired
  TokenRepository tokenRepository;
  @Autowired
  UserRepository repository;
  @Autowired
  PasswordEncoder passwordEncoder;
  @Autowired
  JwtService jwtService;

 
  private String username;
  @BeforeEach
  public void before() {
    tokenRepository.deleteAll();
    repository.deleteAll();
    username = UUID.randomUUID().toString();
    var user = User.builder()
        .username(username)
        .password(passwordEncoder.encode("password"))
        .role(Role.USER)
        .build();
    var savedUser = repository.save(user);
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    var token = Token.builder()
        .user(user)
        .token(jwtToken).
        tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }
  
  
  @Test
  void authenticate() {
    AuthResponse response = authenticationService
        .authenticate(AuthRequest.builder().username(username).password("password").build());
    assertTrue(Objects.nonNull(response));
    assertTrue(Objects.nonNull(response.getAccessToken()));
    assertThrows(BadCredentialsException.class, 
        () -> authenticationService
        .authenticate(AuthRequest.builder().username(username+"2").password("password").build()));
  }
  

}
