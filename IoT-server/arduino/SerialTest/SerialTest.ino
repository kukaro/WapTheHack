const int inWaterPin = A0;
const int outWaterPin = A1;
const int gasPin = A2;
const int lampLedPin = 6;
const int fireLedPin = 7;

int inWater = 0;
int outWater = 0;
int gas = 0;
bool lampLed = true;
bool fireLed = true;

char income = 'x';

bool sw = true; 

void setup() {
  Serial.begin(9600);
  pinMode(lampLedPin, OUTPUT);
  pinMode(fireLedPin, OUTPUT);  
  digitalWrite(lampLedPin, HIGH);
  digitalWrite(fireLedPin, HIGH);  
}

void loop() {
  if(Serial.available()){
    income = Serial.read();
    if(income=='o'){
    Serial.println(income);
    income = 'x';
    digitalWrite(lampLedPin, LOW);
    digitalWrite(fireLedPin, LOW);
    }
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
  Serial.print("lamp : ");
  Serial.println(lampLed);
  Serial.print("fire : ");
  Serial.println(fireLed);
  delay(1000);
}
