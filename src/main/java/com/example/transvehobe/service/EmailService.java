package com.example.transvehobe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;

    public void sendNewAccountEmail(
        String to,String username, String firstName, String password) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("parola");
        message.setText(
            "Buna " + firstName + ",\n\nAcestea sunt credentialele tale:\n"
                +" username: "+username+"\n"
                +" parola: "+ password + "\n\n" + "Multumim,\nEchipa Transveho");
        emailSender.send(message);
    }
}
