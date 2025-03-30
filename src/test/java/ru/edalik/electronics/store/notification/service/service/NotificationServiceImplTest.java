package ru.edalik.electronics.store.notification.service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.edalik.electronics.store.notification.service.mapper.NotificationMapperImpl;
import ru.edalik.electronics.store.notification.service.model.dto.NotificationRequest;
import ru.edalik.electronics.store.notification.service.model.entity.Notification;
import ru.edalik.electronics.store.notification.service.model.exception.NotFoundException;
import ru.edalik.electronics.store.notification.service.repository.NotificationRepository;
import ru.edalik.electronics.store.notification.service.service.mail.EmailSenderImpl;
import ru.edalik.electronics.store.notification.service.service.security.UserContextService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    static final UUID USER_ID = UUID.randomUUID();
    static final UUID NOTIFICATION_ID = UUID.randomUUID();
    static final String NOTIFICATION_TEXT = "Test notification";
    static final String SUBJECT = "subject";
    static final String EMAIL = "email";

    @Mock
    NotificationRepository notificationRepository;

    @Mock
    UserContextService userContextService;

    @Spy
    NotificationMapperImpl mapper;

    @Mock
    EmailSenderImpl mailSender;

    @InjectMocks
    NotificationServiceImpl notificationService;

    @BeforeEach
    void setUp() {
        lenient().when(userContextService.getUserGuid()).thenReturn(USER_ID);
    }

    @Test
    void createNotification_ShouldSaveWithCorrectParameters() {
        Notification expected = Notification.builder()
            .userId(USER_ID)
            .text(NOTIFICATION_TEXT)
            .subject(SUBJECT)
            .email(EMAIL)
            .build();
        NotificationRequest notificationRequest = new NotificationRequest(USER_ID, NOTIFICATION_TEXT, SUBJECT, EMAIL);

        when(notificationRepository.save(any(Notification.class))).thenReturn(expected);

        Notification result = notificationService.createNotification(notificationRequest);

        assertEquals(USER_ID, result.getUserId());
        assertEquals(NOTIFICATION_TEXT, result.getText());
        assertEquals(SUBJECT, result.getSubject());
        assertEquals(EMAIL, result.getEmail());
    }

    @Test
    void getNotifications_ShouldReturnListForUser() {
        List<Notification> expected = List.of(mock(Notification.class));
        when(notificationRepository.findByUserId(USER_ID)).thenReturn(expected);

        List<Notification> result = notificationService.getNotifications();

        assertEquals(expected, result);
    }

    @Test
    void getNotificationById_ShouldReturnNotification_WhenExists() {
        Notification expected = mock(Notification.class);
        when(notificationRepository.findByIdAndUserId(NOTIFICATION_ID, USER_ID)).thenReturn(Optional.of(expected));

        Notification result = notificationService.getNotificationById(NOTIFICATION_ID);

        assertEquals(expected, result);
    }

    @Test
    void getNotificationById_ShouldThrowException_WhenNotExists() {
        when(notificationRepository.findByIdAndUserId(NOTIFICATION_ID, USER_ID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> notificationService.getNotificationById(NOTIFICATION_ID));
    }

}