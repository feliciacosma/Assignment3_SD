package com.example.demo.business.observer;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class ObserverSend implements IObserverSend{
    @Override
    public void sendEmail(int i, String s) {

        System.out.println(s);

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("ass.sd366244@gmail.com");
        mailSender.setPassword("gyprhgsyilshkcps");

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth","true");
        properties.setProperty("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        mailSender.setJavaMailProperties(properties);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ass.sd366244@gmail.com");
        message.setTo("ass.sd366244@gmail.com");

        if(i==1){
            message.setSubject("REGISTERED SUCCESSFULLY");
        }

        message.setText("Student: " + s + " registered successfully.");
        mailSender.send(message);


    }
}
