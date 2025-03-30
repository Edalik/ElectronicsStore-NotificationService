package ru.edalik.electronics.store.notification.service.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.edalik.electronics.store.notification.service.mapper.NotificationMapper;
import ru.edalik.electronics.store.notification.service.model.dto.NotificationRequest;
import ru.edalik.electronics.store.notification.service.model.entity.Notification;
import ru.edalik.electronics.store.notification.service.model.exception.NotFoundException;
import ru.edalik.electronics.store.notification.service.repository.NotificationRepository;
import ru.edalik.electronics.store.notification.service.service.interfaces.NotificationService;
import ru.edalik.electronics.store.notification.service.service.mail.EmailSenderImpl;
import ru.edalik.electronics.store.notification.service.service.security.UserContextService;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    private final UserContextService userContextService;

    private final NotificationMapper mapper;

    private final EmailSenderImpl mailSender;

    @Override
    public Notification createNotification(NotificationRequest notificationRequest) {
        Notification notification = mapper.toEntity(notificationRequest);

        boolean isMailSent = mailSender.sendMail(
            notificationRequest.email(),
            notificationRequest.subject(),
            notificationRequest.text()
        );
        if (isMailSent) {
            notification.setMailSentTime(LocalDateTime.now());
        }

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