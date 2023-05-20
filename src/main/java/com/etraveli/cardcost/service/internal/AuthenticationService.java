package com.etraveli.cardcost.service.internal;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import com.etraveli.cardcost.config.JwtService;
import com.etraveli.cardcost.domain.dbo.Token;
import com.etraveli.cardcost.domain.dbo.User;
import com.etraveli.cardcost.domain.dto.AuthRequest;
import com.etraveli.cardcost.domain.dto.AuthResponse;
import com.etraveli.cardcost.enums.TokenType;
import com.etraveli.cardcost.service.repository.TokenRepository;
import com.etraveli.cardcost.service.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final TokenRepository tokenRepository;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthResponse authenticate(AuthRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
    var user = repository.findByUsername(request.getUsername()).orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    return AuthResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
  }

  public void refreshToken(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    try {
      final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
      final String refreshToken;
      final String username;
      if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        return;
      }
      refreshToken = authHeader.substring(7);
      username = jwtService.extractUsername(refreshToken);
      if (username != null) {
        var user = this.repository.findByUsername(username).orElseThrow();
        if (jwtService.isTokenValid(refreshToken, user)) {
          var accessToken = jwtService.generateToken(user);
          revokeAllUserTokens(user);
          saveUserToken(user, accessToken);
          var authResponse =
              AuthResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
          new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

  private void saveUserToken(User user, String jwtToken) {
    var token = Token.builder().user(user).token(jwtToken).tokenType(TokenType.BEARER)
        .expired(false).revoked(false).build();
    tokenRepository.save(token);
  }



}
