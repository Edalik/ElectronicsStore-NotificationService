package ru.edalik.electronics.store.notification.service.service.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSenderImpl implements EmailSender {

    private final JavaMailSender mailSender;

    @Override
    public boolean sendMail(String to, String subject, String body) {
        try {
            MimeMessage message = buildMessage(to, subject, body);
            mailSender.send(message);
            log.info("Successfully sent email to {} with subject: \"{}\" and body: \"{}\"", to, subject, body);
            return true;
        } catch (Exception e) {
            log.error("Error sending email message: {}", e.getMessage(), e);
            return false;
        }
    }

    private MimeMessage buildMessage(String to, String subject, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);

        return message;
    }

}