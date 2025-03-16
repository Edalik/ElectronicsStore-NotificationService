package ru.edalik.electronics.store.notification.service.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Schema(description = "DTO для представления уведомления")
public record NotificationDto(
    @Schema(
        description = "UUID уведомления",
        example = "550e8400-e29b-41d4-a716-446655440000"
    )
    UUID id,

    @Schema(
        description = "UUID пользователя",
        example = "550e8400-e29b-41d4-a716-446655440000"
    )
    UUID userId,

    @Schema(
        description = "Текст уведомления",
        example = "Текст"
    )
    String text,

    @Schema(
        description = "Время создания уведомления",
        example = "2025-03-16T16:22:00.138Z"
    )
    LocalDateTime createdTime
) {

}