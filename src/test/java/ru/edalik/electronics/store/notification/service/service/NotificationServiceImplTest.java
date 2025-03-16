package ru.edalik.electronics.store.notification.service.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.edalik.electronics.store.notification.service.model.entity.Notification;
import ru.edalik.electronics.store.notification.service.model.exception.NotFoundException;
import ru.edalik.electronics.store.notification.service.repository.NotificationRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    static final UUID USER_ID = UUID.randomUUID();
    static final UUID NOTIFICATION_ID = UUID.randomUUID();
    static final String NOTIFICATION_TEXT = "Test notification";

    @Mock
    NotificationRepository notificationRepository;

    @InjectMocks
    NotificationServiceImpl notificationService;

    @Test
    void createNotification_ShouldSaveWithCorrectParameters() {
        Notification expected = Notification.builder()
            .userId(USER_ID)
            .text(NOTIFICATION_TEXT)
            .build();

        when(notificationRepository.save(any(Notification.class))).thenReturn(expected);

        Notification result = notificationService.createNotification(USER_ID, NOTIFICATION_TEXT);

        assertEquals(USER_ID, result.getUserId());
        assertEquals(NOTIFICATION_TEXT, result.getText());
    }

    @Test
    void getNotifications_ShouldReturnListForUser() {
        List<Notification> expected = List.of(mock(Notification.class));
        when(notificationRepository.findByUserId(USER_ID)).thenReturn(expected);

        List<Notification> result = notificationService.getNotifications(USER_ID);

        assertEquals(expected, result);
    }

    @Test
    void getNotificationById_ShouldReturnNotification_WhenExists() {
        Notification expected = mock(Notification.class);
        when(notificationRepository.findById(NOTIFICATION_ID)).thenReturn(Optional.of(expected));

        Notification result = notificationService.getNotificationById(USER_ID, NOTIFICATION_ID);

        assertEquals(expected, result);
    }

    @Test
    void getNotificationById_ShouldThrowException_WhenNotExists() {
        when(notificationRepository.findById(NOTIFICATION_ID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> notificationService.getNotificationById(USER_ID, NOTIFICATION_ID));
    }
}