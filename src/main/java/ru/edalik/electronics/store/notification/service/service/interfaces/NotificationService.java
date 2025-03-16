package ru.edalik.electronics.store.notification.service.service.interfaces;

import ru.edalik.electronics.store.notification.service.model.entity.Notification;

import java.util.List;
import java.util.UUID;

public interface NotificationService {

    Notification createNotification(UUID userId, String text);

    List<Notification> getNotifications(UUID userId);

    Notification getNotificationById(UUID userId, UUID notificationId);

}