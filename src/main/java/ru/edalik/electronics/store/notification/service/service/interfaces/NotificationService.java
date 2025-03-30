package ru.edalik.electronics.store.notification.service.service.interfaces;

import ru.edalik.electronics.store.notification.service.model.dto.NotificationRequest;
import ru.edalik.electronics.store.notification.service.model.entity.Notification;

import java.util.List;
import java.util.UUID;

public interface NotificationService {

    Notification createNotification(NotificationRequest notificationRequest);

    List<Notification> getNotifications();

    Notification getNotificationById(UUID notificationId);

}