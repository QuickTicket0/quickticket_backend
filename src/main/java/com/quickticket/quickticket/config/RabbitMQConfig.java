package com.quickticket.quickticket.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public TopicExchange ticketExchange() {
        return new TopicExchange("ticket.exchange");
    }

    @Bean
    public Queue bulkinsertQueue() {
        return new Queue("worker.queue.bulk-insert");
    }

    @Bean
    public Binding binding(Queue bulkinsertQueue, TopicExchange ticketExchange) {
        return BindingBuilder.bind(bulkinsertQueue)
                .to(ticketExchange)
                .with("ticket.ticket-issue.#");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
