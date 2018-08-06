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
  Serial.print("inWater : ");
  Serial.println(inWater);
  Serial.print("outWater : ");
  Serial.println(outWater);
  delay(500);
}
