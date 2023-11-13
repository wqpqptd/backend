package com.example.backend.services;

import com.example.backend.entities.Profile;

import java.time.LocalDate;


public interface EmailService {
    void sendEmail(String to, String subject, String message);
    void sendReminderEmailsCreateProfile(Profile profile, LocalDate eventDate);
    void sendReminderEmails(Profile profile);
}
