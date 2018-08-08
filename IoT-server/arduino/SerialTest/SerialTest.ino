const int inWaterPin = 0;
const int outWaterPin = 2;
const int gasPin = 4;
int inWater = 0;
int outWater = 0;
int gas = 0;
String income = "";

void setup() {
  Serial.begin(9600);
  pinMode(inWaterPin, OUTPUT);
  pinMode(outWaterPin, OUTPUT);
}

void loop() {
  if(Serial.available()){
    income += (char)Serial.read();
  }
  if(income!=0){
    Serial.println(income);
    income = "";
  }
  inWater = analogRead(inWaterPin);
  outWater = analogRead(outWaterPin);
  gas = analogRead(gasPin);
  Serial.print("inWater : ");
  Serial.println(inWater);
  Serial.print("outWater : ");
  Serial.println(outWater);
  Serial.print("gas : ");
  Serial.println(gas);
  delay(1000);
}
