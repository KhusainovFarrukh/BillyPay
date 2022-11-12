package kh.farrukh.notification_service;

import kh.farrukh.feign_clients.notification.payloads.NotificationRequestDTO;
import kh.farrukh.feign_clients.notification.payloads.NotificationResponseDTO;
import org.springframework.beans.BeanUtils;

public class NotificationMappers {

    public static NotificationResponseDTO toNotificationResponseDTO(Notification notification) {
        NotificationResponseDTO notificationResponseDTO = new NotificationResponseDTO();
        BeanUtils.copyProperties(notification, notificationResponseDTO);
        return notificationResponseDTO;
    }

    public static Notification toNotification(NotificationRequestDTO notificationRequestDTO) {
        Notification notification = new Notification();
        BeanUtils.copyProperties(notificationRequestDTO, notification);
        return notification;
    }
}
