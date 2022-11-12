package kh.farrukh.notification_service;

import kh.farrukh.feign_clients.notification.payloads.NotificationRequestDTO;
import kh.farrukh.feign_clients.notification.payloads.NotificationResponseDTO;

public interface NotificationService {

    NotificationResponseDTO sendNotification(NotificationRequestDTO notificationRequestDTO);
}
