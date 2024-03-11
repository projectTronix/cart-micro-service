package com.mayank.cart.service;

import org.springframework.stereotype.Service;

@Service
public interface MailService {
    void sendMail(String to);
}
