package ru.edalik.electronics.store.notification.service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.edalik.electronics.store.notification.service.model.entity.Notification;
import ru.edalik.electronics.store.notification.service.model.exception.NotFoundException;
import ru.edalik.electronics.store.notification.service.repository.NotificationRepository;
import ru.edalik.electronics.store.notification.service.service.interfaces.NotificationService;
import ru.edalik.electronics.store.notification.service.service.security.UserContextService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    private final UserContextService userContextService;

    @Override
    public Notification createNotification(UUID userId, String text) {
        Notification notification = Notification.builder()
            .userId(userId)
            .text(text)
            .build();

        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getNotifications() {
        UUID userId = userContextService.getUserGuid();
        return notificationRepository.findByUserId(userId);
    }

    @Override
    public Notification getNotificationById(UUID notificationId) {
        UUID userId = userContextService.getUserGuid();
        return notificationRepository.findByIdAndUserId(notificationId, userId)
            .orElseThrow(
                () -> new NotFoundException("Notification with id %s was not found".formatted(notificationId))
            );
    }

}