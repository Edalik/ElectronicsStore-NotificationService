package ru.edalik.electronics.store.notification.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.edalik.electronics.store.notification.service.mapper.NotificationMapper;
import ru.edalik.electronics.store.notification.service.model.dto.NotificationDto;
import ru.edalik.electronics.store.notification.service.model.dto.exception.ErrorDto;
import ru.edalik.electronics.store.notification.service.service.interfaces.NotificationService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
@Tag(name = "Notification Controller", description = "API для взаимодействия с уведомлениями")
public class NotificationController {

    private final NotificationService notificationService;

    private final NotificationMapper notificationMapper;

    @Operation(
        summary = "Получение уведомления по идентификатору",
        description = "Возвращает уведомление по идентификатору"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Уведомление найдено",
        content = @Content(schema = @Schema(implementation = NotificationDto.class))
    )
    @ApiResponse(
        responseCode = "404",
        description = "Уведомление не найдено",
        content = @Content(schema = @Schema(implementation = ErrorDto.class))
    )
    @GetMapping("/{id}")
    public NotificationDto getNotificationById(
        @Parameter(
            description = "UUID уведомления",
            required = true,
            example = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
        )
        @PathVariable UUID id
    ) {
        return notificationMapper.toDto(notificationService.getNotificationById(id));
    }

    @Operation(
        summary = "Получение списка уведомлений",
        description = "Возвращает список уведомлений"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Уведомления найдены",
        content = @Content(schema = @Schema(implementation = NotificationDto.class))
    )
    @GetMapping
    public List<NotificationDto> getNotifications() {
        return notificationMapper.toDto(notificationService.getNotifications());
    }

}