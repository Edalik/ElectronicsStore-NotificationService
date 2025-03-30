package ru.edalik.electronics.store.notification.service.service.mail;

public interface EmailSender {

    boolean sendMail(String to, String subject, String body);

}