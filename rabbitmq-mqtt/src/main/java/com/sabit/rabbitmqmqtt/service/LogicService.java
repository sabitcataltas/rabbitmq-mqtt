package com.sabit.rabbitmqmqtt.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sabit.rabbitmqmqtt.config.RabbitMQConfig;

@Service
public class LogicService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void deviceOverloaded(String clientId, String value) {
		String topic = RabbitMQConfig.ROUTING_KEY_OUT + "." + clientId + ".overloaded";
		rabbitTemplate.send(RabbitMQConfig.FROM, topic, new Message(value.getBytes()));
	}

}
