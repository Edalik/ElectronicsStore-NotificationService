package ru.edalik.electronics.store.notification.service.model.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record NotificationRequest(
    UUID userId,
    String subject,
    String text,
    String email
) {

}