package kh.farrukh.feign_clients.notification;

import kh.farrukh.feign_clients.notification.payloads.NotificationRequestDTO;
import kh.farrukh.feign_clients.notification.payloads.NotificationResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

import static kh.farrukh.feign_clients.notification.NotificationConstants.ENDPOINT_NOTIFICATION;

@FeignClient(name = "notification-service", path = ENDPOINT_NOTIFICATION)
public interface NotificationClient {

    @PostMapping
    NotificationResponseDTO sendNotification(
            @Valid @RequestBody NotificationRequestDTO notificationRequestDTO
    );
}
