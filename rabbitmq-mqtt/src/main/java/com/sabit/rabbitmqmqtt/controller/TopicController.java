package com.sabit.rabbitmqmqtt.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sabit.rabbitmqmqtt.config.RabbitMQConfig;
import com.sabit.rabbitmqmqtt.repository.DeviceDataRepository;

@RestController
public class TopicController {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private DeviceDataRepository deviceDataRepository;

	@GetMapping("/topic/{topic}/publish/{message}")
	public ResponseEntity publish(@PathVariable String topic, @PathVariable String message) {
		rabbitTemplate.send(RabbitMQConfig.FROM, topic, new Message(message.getBytes()));
		return ResponseEntity.ok(null);
	}

	@GetMapping("/main")
	public ResponseEntity<Map<String, List<List>>> chart() {

		Map<String, List<List>> result = new HashMap<String, List<List>>();

		deviceDataRepository.getMap().forEach((clientId, queue) -> {
			result.put(clientId, queue.stream().map(data -> Arrays.asList(data.getTime(), data.getValue()))
					.collect(Collectors.toList()));
		});

		return ResponseEntity.ok(result);

	}
}
