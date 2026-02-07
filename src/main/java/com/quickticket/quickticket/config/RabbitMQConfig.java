@Configuration
public class RabbitMQConfig {
    @Bean
    public TopicExchange domainExchange() {
        return new TopicExchange("worker.bulkinsert.exchange");
    }

    @Bean
    public Queue bulkinsertQueue() {
        return new Queue("worker.bulkinsert.queue");
    }

    @Bean
    public Binding binding(Queue bulkinsertQueue, TopicExchange domainExchange) {
        return new BindingBuilder.bind(bulkinsertQueue)
                .to(domainExchange)
                .with("worker.bulkinsert.#");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
