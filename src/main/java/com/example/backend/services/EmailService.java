package com.example.backend.services;

import com.example.backend.entities.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


public interface EmailService {
    void sendEmail(String to, String subject, String message);
    void sendReminderEmailsCreateProfile(Profile profile, LocalDate eventDate);
    void sendReminderEmails(Profile profile);
}
