package ru.edalik.electronics.store.notification.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.edalik.electronics.store.notification.service.model.entity.Notification;

import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {

}