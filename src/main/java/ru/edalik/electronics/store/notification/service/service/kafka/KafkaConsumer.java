package ru.edalik.electronics.store.notification.service.service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.edalik.electronics.store.notification.service.model.dto.NotificationRequest;
import ru.edalik.electronics.store.notification.service.service.interfaces.NotificationService;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final NotificationService notificationService;

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${spring.kafka.topic.notification}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(String message) {
        log.info("Received message {}", message);
        try {
            NotificationRequest notificationRequest = objectMapper.readValue(message, NotificationRequest.class);
            notificationService.createNotification(notificationRequest.userId(), notificationRequest.text());
        } catch (Exception e) {
            log.error("Error processing message: {}", message, e);
        }
    }

}