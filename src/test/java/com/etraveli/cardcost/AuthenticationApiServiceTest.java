package com.etraveli.cardcost;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;
import com.etraveli.cardcost.domain.dto.AuthRequest;
import com.etraveli.cardcost.domain.dto.AuthResponse;
import com.etraveli.cardcost.service.internal.AuthenticationService;
import com.etraveli.cardcost.utils.TestUtils;

@Tag("AuthenticationApiServiceTest")

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationApiServiceTest {


  @Autowired
  protected MockMvc mockMvc;

  @MockBean
  protected AuthenticationService authenticationService;

  private String username;
  private static AuthRequest request =
      AuthRequest.builder().username("user").password("pass").build();
  private static AuthRequest bad_request =
      AuthRequest.builder().username("user1").password("pass1").build();
  private static AuthResponse response =
      AuthResponse.builder().accessToken("acctoken").refreshToken("refToken").build();

  


  @Test
  void authenticate() throws Exception {
    when(authenticationService.authenticate(request)).thenReturn(response);
    mockMvc.perform(post("/api/v1/auth/authenticate")
        .content(TestUtils.toJson(request))
        .contentType(MediaType.APPLICATION_JSON))
    .andExpect(status().isOk());
  }
  
  @Test
  void authenticateBadRequest() throws Exception {
    when(authenticationService.authenticate(bad_request))
      .thenThrow(new BadCredentialsException(bad_request.getUsername()));
    mockMvc.perform(post("/api/v1/auth/authenticate")
        .content(TestUtils.toJson(bad_request))
        .contentType(MediaType.APPLICATION_JSON))
    .andExpect(status().isUnauthorized());
  }


}
