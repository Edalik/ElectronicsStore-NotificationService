package ru.edalik.electronics.store.notification.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.edalik.electronics.store.notification.service.model.entity.Notification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    List<Notification> findByUserId(UUID userId);

    Optional<Notification> findByIdAndUserId(UUID id, UUID userId);

}