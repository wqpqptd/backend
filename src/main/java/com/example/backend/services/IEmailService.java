package com.example.backend.services;

import com.example.backend.entities.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class IEmailService implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;


    public void sendReminderEmailsCreateProfile(Profile profile, LocalDate date) {
        String subject = "Notification: Your register exam driver license success";
        String text = String.format(
                "You can follow the website to know information about the examination.%n <br/>" +
                        "email: %s%n <br/>" +
                        "name: %s%n <br/>" +
                        "dateofbirth: %s%n <br/>" +
                        "sex: %s%n <br/>" +
                        "idcard: %s%n <br/>" +
                        "phone: %s%n <br/>" +
                        "image: <a href=\"%s\">Click send image</a>%n <br/>" +
                        "file: <a href=\"%s\">Click send file</a>%n <br/>" +
                        "note: %s%n <br/>" +
                        "nation: %s%n <br/>" +
                        "religion: %s%n <br/>" +
                        "province: %s%n <br/>" +
                        "district: %s%n <br/>" +
                        "wards: %s%n <br/>" +
                        "examinationsName: %s%n <br/>" +
                        "examinationsDate: %s <br/>",
                profile.getEmail(),
                profile.getName(),
                profile.getDateofbirth(),
                profile.getSex(),
                profile.getIdcard(),
                profile.getPhone(),
                profile.getImage(),
                profile.getFile(),
                profile.getNote(),
                profile.getNationId().getNationName(),
                profile.getReligionId().getReligionName(),
                profile.getProvince(),
                profile.getDistrict(),
                profile.getWards(),
                profile.getExaminationsId().getExaminationsName(),
                profile.getExaminationsId().getExaminationsDate()
        );

        sendEmail(profile.getEmail(), subject, text);
    }
    @Scheduled(cron = "0 52 17 * * ?")
    public void sendReminderEmails(Profile profile) {
        LocalDate reminderDate = profile.getExaminationsId().getExaminationsDate().minusDays(1);

        if (isTodayOneDayBefore(reminderDate)) {
                String subject = "Reminder: You have an exam tomorrow!";
                String text = String.format(
                        "Don't forget about your exam tomorrow!%n <br/> " +
                                "Information Examinations: %n <br/>" +
                                "Examination Name: %s%n <br/>" +
                                "Examination Date: %s%n <br/>" +
                                "Examination Description: %s%n <br/>",
                        profile.getExaminationsId().getExaminationsName(),
                        profile.getExaminationsId().getExaminationsDate(),
                        profile.getExaminationsId().getExaminationsDescription()
                );
                sendEmail(profile.getEmail(), subject, text);
        }
    }

    private boolean isTodayOneDayBefore(LocalDate reminderDate) {
        return LocalDate.now().isEqual(reminderDate);
    }



    @Override
    public void sendEmail(String to, String subject, String message) {
        MimeMessagePreparator result = mimeMessage -> {
            MimeMessageHelper msg = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            msg.setTo(to);
            msg.setSubject(subject);
            msg.setText(message, true);
        };
        javaMailSender.send(result);
    }
}

