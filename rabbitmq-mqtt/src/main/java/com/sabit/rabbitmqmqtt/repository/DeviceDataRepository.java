package com.sabit.rabbitmqmqtt.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.springframework.stereotype.Service;

import com.sabit.rabbitmqmqtt.pojo.DeviceData;

@Service
public class DeviceDataRepository {

	private Map<String, Queue<DeviceData>> map = new HashMap<String, Queue<DeviceData>>();

	public void add(String clientId, DeviceData data) {
		map.computeIfAbsent(clientId, k -> new CircularFifoQueue<DeviceData>(30)).add(data);
	}

	public void add(String clientId, Double value) {
		map.computeIfAbsent(clientId, k -> new CircularFifoQueue<DeviceData>(30))
				.add(new DeviceData(clientId, System.currentTimeMillis(), Double.valueOf(value)));
	}

	public Queue<DeviceData> getQueue(String clientId) {
		return this.map.get(clientId);
	}
	
	public Map<String, Queue<DeviceData>> getMap() {
		return this.map;
	}

}
