/* NoiseApp script for the noise sensor realized with ArduinoUno/GenuinoUno
 * This script is written to achieve an approximation of the noise level(DB) in the ambience
 * We do not use any external library, just send the data throw the web using NodeRED
 * It can be easily adapted for an usage with a esp8266 shield
 * Created by Federico Boarelli for the Pervasive System class at Sapienza - Univesità di Roma
 */


int sensorValue = 0;
int bias_level = 505;
String SENSOR_NAME = "Insert your sensor name";
String SENSOR_LATITUDE = "Insert the sensor latitude";
String SENSOR_LONGITUDE = "Insert the sensor longitude";

void setup() {
  Serial.begin(9600);
}

void loop() {
  //Reading the value from the sensor
  sensorValue = analogRead(A0);
  float adc_ac = abs(sensorValue - bias_level);
  //Convert the ADC reading to voltage
  float voltage = (adc_ac / 1024.0) * 5.0;
  float volts_db = -20.0 * log10(voltage);
  //The +6 is a parameter for a better accuracy depending of the type of sensor used
  float spl_db = volts_db + 6;
  //The json sent to the server
  Serial.print("{\"sensorName\":\"" + SENSOR_NAME + "\"");
  Serial.print(", \"latitude\":\"" + SENSOR_LATITUDE + "\"");
  Serial.print(", \"longitude\":\"" + SENSOR_LONGITUDE + "\"");
  Serial.print(", \"noiseValue\": ");
  Serial.println("\""+String(spl_db)+"\"}");
  //Rilevation every minute
  delay(60000);
}
