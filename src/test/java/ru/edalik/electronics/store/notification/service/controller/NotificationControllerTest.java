package ru.edalik.electronics.store.notification.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.edalik.electronics.store.notification.service.mapper.NotificationMapper;
import ru.edalik.electronics.store.notification.service.model.dto.NotificationDto;
import ru.edalik.electronics.store.notification.service.model.entity.Notification;
import ru.edalik.electronics.store.notification.service.model.exception.NotFoundException;
import ru.edalik.electronics.store.notification.service.service.interfaces.NotificationService;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotificationController.class)
@AutoConfigureMockMvc(addFilters = false)
class NotificationControllerTest {

    static final String BASE_URL = "/api/v1/notifications";
    static final UUID NOTIFICATION_ID = UUID.randomUUID();

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    NotificationService notificationService;

    @MockitoBean
    NotificationMapper notificationMapper;

    @Test
    @SneakyThrows
    void getNotificationById_ExistingNotification_ReturnsNotificationDto() {
        Notification notification = mock(Notification.class);
        NotificationDto dto = mock(NotificationDto.class);

        when(notificationService.getNotificationById(NOTIFICATION_ID)).thenReturn(notification);
        when(notificationMapper.toDto(notification)).thenReturn(dto);

        mockMvc.perform(get(BASE_URL + "/{id}", NOTIFICATION_ID))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(dto)));
    }

    @Test
    @SneakyThrows
    void getNotificationById_NotExistingNotification_ReturnsNotFound() {
        String errorMessage = "Notification not found";
        when(notificationService.getNotificationById(NOTIFICATION_ID))
            .thenThrow(new NotFoundException(errorMessage));

        mockMvc.perform(get(BASE_URL + "/{id}", NOTIFICATION_ID))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.error").value("Not Found"))
            .andExpect(jsonPath("$.message").value(errorMessage));
    }

    @Test
    @SneakyThrows
    void getNotifications_ValidRequest_ReturnsNotificationList() {
        List<Notification> notifications = List.of(mock(Notification.class));
        List<NotificationDto> dtos = List.of(mock(NotificationDto.class));

        when(notificationService.getNotifications()).thenReturn(notifications);
        when(notificationMapper.toDto(notifications)).thenReturn(dtos);

        mockMvc.perform(get(BASE_URL))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(dtos)));
    }

}