package com.sabit.rabbitmqmqtt.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	public static final String QUEUE = "my-queue";
	public static final String FROM = "amq.topic";
	public static final String ROUTING_KEY_IN = "esp32.publish.+";
	public static final String ROUTING_KEY_OUT = "esp32.subscribe";

	@Bean
	public Queue createQueue() {
		return new Queue(QUEUE);
	}

	@Bean
	public Binding createBindingBetweenQueueAndMqttTopic() {
		return new Binding(QUEUE, Binding.DestinationType.QUEUE, FROM, ROUTING_KEY_IN, null);
	}
}