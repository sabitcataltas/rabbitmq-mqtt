#include <WiFi.h>
#include <PubSubClient.h>
#include <Wire.h>

// Replace the next variables with your SSID/Password combination
const char* ssid = "SSID"; //SSID
const char* password = "Password"; //Password

// Must be a unique clientID
const char* clientId = "esp123";

// Replace the next variables with your RabitMQ inforamtions
const char* mqtt_server = "192.168.1.1"; //rabbitmq ip
const long mqtt_server_port = 1883; //rabbitmq port
const char* mqtt_topic_pub = "esp32/publish/esp123"; //clientId must be added 
const char* mqtt_topic_sub = "esp32/subscribe/#";


WiFiClient espClient;
PubSubClient client(espClient);
long lastMsg = 0;
long loopCount = 0;
long delayTime = 0;


void setup() {
  Serial.begin(115200);

  setup_wifi();

  //strcat(mqtt_topic_pub, clientId);
  client.setServer(mqtt_server, mqtt_server_port);
  client.setCallback(callback);

}

void setup_wifi() {
  delay(10);
  // We start by connecting to a WiFi network
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);

  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
}

void callback(char* topic, byte* message, unsigned int length) {
  Serial.print("Message arrived on topic: ");
  Serial.print(topic);
  Serial.print(". Message: ");
  String messageTemp;
  
  for (int i = 0; i < length; i++) {
    Serial.print((char)message[i]);
    messageTemp += (char)message[i];
  }
  Serial.println();

  if(String(topic).startsWith("esp32/subscribe/esp123/")) {
    Serial.println("DEVICE OVERLOADED!!");
  }
}

void reconnect() {
  // Loop until we're reconnected
  while (!client.connected()) {
    Serial.println("Attempting MQTT connection...");
    // Attempt to connect
    if (client.connect(clientId)) {
      Serial.println("connected");
      // Subscribe
      client.subscribe(mqtt_topic_sub);
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");
      // Wait 5 seconds before retrying
      delay(5000);
    }
  }
}
void loop() {
  if (!client.connected()) {
    reconnect();
  }
  client.loop();

  long now = millis();

  if(loopCount > 99)
    loopCount = 0;

  if (loopCount % 4 == 0) {
    delayTime = 2000;
  } else {
    delayTime = 0;
  }
   

  if (now - lastMsg > (1000 + delayTime)) {
    lastMsg = now;
    loopCount = loopCount + 1;
    
    // Convert the value to a char array
    char tempString[8];
    dtostrf(random(100), 1, 2, tempString);
    Serial.print("Sending value : ");
    Serial.println(tempString);
    client.publish(mqtt_topic_pub, tempString);

  }
}