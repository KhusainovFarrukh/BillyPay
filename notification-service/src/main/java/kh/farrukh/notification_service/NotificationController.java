package kh.farrukh.notification_service;

import kh.farrukh.feign_clients.notification.payloads.NotificationRequestDTO;
import kh.farrukh.feign_clients.notification.payloads.NotificationResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static kh.farrukh.feign_clients.notification.NotificationConstants.ENDPOINT_NOTIFICATION;

@RequiredArgsConstructor
@RestController
@RequestMapping(ENDPOINT_NOTIFICATION)
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<NotificationResponseDTO> sendNotification(
            @Valid @RequestBody NotificationRequestDTO notificationRequestDTO
    ) {
        return ResponseEntity.ok(notificationService.sendNotification(notificationRequestDTO));
    }
}
