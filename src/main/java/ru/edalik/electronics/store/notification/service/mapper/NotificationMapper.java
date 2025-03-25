package ru.edalik.electronics.store.notification.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.edalik.electronics.store.notification.service.model.dto.NotificationDto;
import ru.edalik.electronics.store.notification.service.model.dto.NotificationRequest;
import ru.edalik.electronics.store.notification.service.model.entity.Notification;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface NotificationMapper {

    Notification toEntity(NotificationDto notificationDto);

    Notification toEntity(NotificationRequest notificationRequest);

    List<Notification> toEntity(List<NotificationDto> notificationDtoList);

    NotificationDto toDto(Notification notification);

    List<NotificationDto> toDto(List<Notification> notifications);

}