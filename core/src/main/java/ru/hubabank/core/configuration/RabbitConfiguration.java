package ru.hubabank.core.configuration;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static ru.hubabank.core.constant.RabbitConstants.*;

@Configuration
public class RabbitConfiguration {

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    Queue depositQueue() {
        return newQuorumQueue(DEPOSIT_QUEUE);
    }

    @Bean
    Queue withdrawalQueue() {
        return newQuorumQueue(WITHDRAWAL_QUEUE);
    }

    @Bean
    Queue transferToBillQueue() {
        return newQuorumQueue(TRANSFER_TO_BILL_QUEUE);
    }

    @Bean
    Queue transferToUserQueue() {
        return newQuorumQueue(TRANSFER_TO_USER_QUEUE);
    }

    @Bean
    DirectExchange transferResponseExchange() {
        return new DirectExchange(TRANSFER_RESPONSE_EXCHANGE);
    }

    private Queue newQuorumQueue(String name) {
        Map<String, Object> args = new HashMap<>();
        args.put("x-queue-type", "quorum");
        return new Queue(name, true, false, false, args);
    }
}
