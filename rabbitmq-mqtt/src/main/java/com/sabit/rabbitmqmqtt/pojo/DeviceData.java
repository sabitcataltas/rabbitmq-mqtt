package com.sabit.rabbitmqmqtt.pojo;

import java.io.Serializable;

public class DeviceData implements Serializable {

	private Long id;
	private String clientId;
	private Long time;
	private Double value;

	public DeviceData(String clientId, Long time, Double value) {
		super();
		this.clientId = clientId;
		this.time = time;
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

}
