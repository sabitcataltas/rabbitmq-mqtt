# RabbitMQ MQTT Plugin
Basic project for reading data from Esp32 MQTT clients via RabbitMQ with Springboot.

### RabbitMQ MQTT
- First run RabbitMQ. ```docker run -d --hostname my-rabbit --name my-rabbit -p 15672:15672 -p 5672:5672 -p 1883:1883 rabbitmq:management```
- Then, rabbitmq-mqtt plugin must be enabled via container cli ```rabbitmq-plugins enable rabbitmq_mqtt```

### Esp32
- WiFi SSID/Pass needs to be replaced in EspMqttClient.ino
- Random numbers will be published to ```esp32/publish/{clientId}```
- ```esp32/subscribe/#``` will be consumed

### Springboot
- ```http://localhost:8080/```

![mqttClients](https://github.com/sabitcataltas/rabbitmq-mqtt/assets/105239474/cf852128-dfd9-48f5-a609-59583df6714f)
![clientLogs](https://github.com/sabitcataltas/rabbitmq-mqtt/assets/105239474/96c70b3d-bf10-49b5-922c-2b983f1cc766)
