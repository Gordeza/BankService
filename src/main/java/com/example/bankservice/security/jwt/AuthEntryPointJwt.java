package com.example.bankservice.security.jwt;

import com.example.bankservice.DAO.ExceptionMessage;
import com.example.bankservice.exception.InvalidCardException;
import com.example.bankservice.model.Card;
import com.example.bankservice.service.CardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

  private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

  private final CardService cardService;

  private final int MAX_FAILED_ATTEMPTS = 3;

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException, ServletException {
    try {
      Card card = cardService.getCard(request.getParameter("cardNumber"));
      int failedAttempts = card.getFailedAttempts();
      if (failedAttempts < MAX_FAILED_ATTEMPTS) {
        failedAttempts++;
        card.setFailedAttempts(failedAttempts);
        if (failedAttempts == MAX_FAILED_ATTEMPTS)
          card.setIsNotLocked(false);
        cardService.save(card);
      }
    } catch (Exception ignored) {
    }
    logger.error("Unauthorized error: {}", authException.getMessage());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    final ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(response.getOutputStream(), new ExceptionMessage(
            HttpServletResponse.SC_UNAUTHORIZED,
            authException.getMessage(),
            request.getServletPath()));
  }

}
