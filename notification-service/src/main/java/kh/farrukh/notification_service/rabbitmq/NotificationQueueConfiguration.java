package kh.farrukh.notification_service.rabbitmq;

import kh.farrukh.amqp.MessagingConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationQueueConfiguration {

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(MessagingConstants.EXCHANGE_NAME);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(MessagingConstants.QUEUE_NOTIFICATION);
    }

    @Bean
    public Binding notificationToInternalBinding(TopicExchange topicExchange, Queue queue) {
        return BindingBuilder
                .bind(queue)
                .to(topicExchange)
                .with(MessagingConstants.ROUTING_KEY_INTERNAL_TO_NOTIFICATION);
    }
}
