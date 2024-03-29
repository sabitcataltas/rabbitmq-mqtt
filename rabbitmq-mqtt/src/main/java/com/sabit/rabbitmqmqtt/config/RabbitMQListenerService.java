package com.sabit.rabbitmqmqtt.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.sabit.rabbitmqmqtt.repository.DeviceDataRepository;
import com.sabit.rabbitmqmqtt.service.LogicService;

import io.micrometer.common.util.StringUtils;

@Service
public class RabbitMQListenerService {

	private LogicService logicService;
	private DeviceDataRepository deviceDataRepository;

	public RabbitMQListenerService(LogicService logicService, DeviceDataRepository deviceDataRepository) {
		this.logicService = logicService;
		this.deviceDataRepository = deviceDataRepository;
	}

	@RabbitListener(queues = RabbitMQConfig.QUEUE)
	public void listenMessage(String messageStr, Message message) {
		String topic = message.getMessageProperties().getReceivedRoutingKey();
		String clientId = topic.substring(topic.lastIndexOf(".") + 1);

		System.out.println("ClientId : " + clientId);
		System.out.println("Message : " + messageStr);

		if (StringUtils.isEmpty(messageStr))
			return;

		deviceDataRepository.add(clientId, Double.valueOf(messageStr));

		if (Double.valueOf(messageStr).compareTo(80.0) > 0) {
			logicService.deviceOverloaded(clientId, messageStr);
		}
	}
}