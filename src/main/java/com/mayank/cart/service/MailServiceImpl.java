package com.mayank.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService{
    private final JavaMailSender mailSender;
    LogManager logManager = LogManager.getLogManager();
    Logger logger = logManager.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @Value("${spring.mail.username}") private String sender;
    @Override
    public void sendMail(String to) throws Exception {
        try {
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(to);
            mailMessage.setText("Thank you! Order placed successfully.");
            mailMessage.setSubject("Order placed successfully");
            mailSender.send(mailMessage);
            logger.log(Level.INFO, "Mail sent successfully.");
        }
        catch (Exception e) {
            logger.log(Level.WARNING, "Encountered a problem while sending mail -- sendMail in MailServiceImpl. - " + e.getMessage());
            throw new Exception("Error while sending mail.");
        }
    }
}
