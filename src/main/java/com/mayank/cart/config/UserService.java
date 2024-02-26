package com.mayank.cart.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
@Service
@RequiredArgsConstructor
public class UserService {
    private final JwtService jwtService;
    private final LogManager logManager = LogManager.getLogManager();
    private final Logger logger = logManager.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public String extractEmailFromRequest(HttpServletRequest request) throws Exception {
        try {
            final String authHeader = request.getHeader("Authorization");
            final String userEmail;
            final String jwt;
            jwt = authHeader.substring(7);
            userEmail = jwtService.extractUsername(jwt);
            return userEmail;
        } catch(Exception e) {
            logger.log(Level.WARNING, e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
}
