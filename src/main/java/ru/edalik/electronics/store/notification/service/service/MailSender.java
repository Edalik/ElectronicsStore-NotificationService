package ru.edalik.electronics.store.notification.service.service;

public interface MailSender {

    boolean sendMail(String to, String subject, String body);

}