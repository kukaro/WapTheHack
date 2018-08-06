const int inWaterPin = 0;
const int outWaterPin = 2;
int inWater = 0;
int outWater = 0;

void setup() {
  Serial.begin(9600);
  pinMode(inWaterPin, OUTPUT);
  pinMode(outWaterPin, OUTPUT);
}

void loop() {
  inWater = analogRead(inWaterPin);
  outWater = analogRead(outWaterPin);
  Serial.println(inWater);
  Serial.println(outWater);
  delay(500);
}
