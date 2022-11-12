package kh.farrukh.notification_service;

import feign.FeignException;
import kh.farrukh.common.exceptions.exceptions.ResourceNotFoundException;
import kh.farrukh.feign_clients.notification.payloads.NotificationRequestDTO;
import kh.farrukh.feign_clients.notification.payloads.NotificationResponseDTO;
import kh.farrukh.feign_clients.user.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserClient userClient;

    @Override
    public NotificationResponseDTO sendNotification(NotificationRequestDTO notificationRequestDTO) {
        try {
            userClient.getUserById(notificationRequestDTO.getUserId());
        } catch (FeignException.NotFound | FeignException.ServiceUnavailable e) {
            throw new ResourceNotFoundException("User", "id", notificationRequestDTO.getUserId());
        }

        // TODO: 11/12/22 implement real notification sending
        Notification notification = notificationRepository.save(NotificationMappers.toNotification(notificationRequestDTO));
        notification.setSentAt(LocalDateTime.now());
        return NotificationMappers.toNotificationResponseDTO(notification);
    }
}
