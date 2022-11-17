package kh.farrukh.notification_service.rabbitmq;

import kh.farrukh.amqp.MessagingConstants;
import kh.farrukh.feign_clients.notification.payloads.NotificationRequestDTO;
import kh.farrukh.notification_service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationService notificationService;

    @RabbitListener(queues = MessagingConstants.QUEUE_NOTIFICATION)
    public void consume(NotificationRequestDTO notificationRequestDTO) {
        log.info("Received notification request: {}", notificationRequestDTO);
        notificationService.sendNotification(notificationRequestDTO);
    }
}
