package com.etraveli.cardcost.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.etraveli.cardcost.domain.dto.AuthRequest;
import com.etraveli.cardcost.domain.dto.AuthResponse;
import com.etraveli.cardcost.service.internal.AuthenticationService;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationApi {

  @Autowired
  private AuthenticationService authenticationService;

  @PostMapping("/authenticate")
  public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {
    return ResponseEntity.ok(authenticationService.authenticate(request));
  }

  @PostMapping("/refresh-token")
  public void refreshToken(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    authenticationService.refreshToken(request, response);
  }

}
