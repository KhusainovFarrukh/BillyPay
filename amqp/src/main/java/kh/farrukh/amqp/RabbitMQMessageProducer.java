package kh.farrukh.amqp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMQMessageProducer {

    private final AmqpTemplate amqpTemplate;

    public void sendNotificationMessage(Object message) {
        send(MessagingConstants.EXCHANGE_NAME, MessagingConstants.ROUTING_KEY_INTERNAL_TO_NOTIFICATION, message);
    }

    public void send(String exchange, String routingKey, Object message) {
        amqpTemplate.convertAndSend(exchange, routingKey, message);
        log.info("Sent message to exchange: {}, routingKey: {}, message: {}", exchange, routingKey, message);
    }
}
