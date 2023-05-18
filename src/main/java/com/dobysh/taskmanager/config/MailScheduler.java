package com.dobysh.taskmanager.config;

import com.dobysh.taskmanager.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class MailScheduler {
    private UserService userService;
    private JavaMailSender javaMailSender;

    @Autowired
    public void getUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void getEmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Scheduled(cron = "0 0 6 * ?")
    public void sendMailsToDebtors() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        List<String> emails = userService.getUserEmailsWithDelayedExpirationDate();
        if (emails.size() > 0) {
            mailMessage.setTo(emails.toArray(new String[0]));
            mailMessage.setSubject("Напоминание о просрочке задач(и)");
            mailMessage.setText("Добрый день. Вы получили это письмо, так как одна или несколько" +
                    " из ваших задач просрочена.");
            javaMailSender.send(mailMessage);
        }
    }
}
