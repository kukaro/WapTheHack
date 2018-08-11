const int inWaterPin = A2;
const int outWaterPin = A1;
const int gasPin = A0;
const int lampLedPin = 6;
const int fireLedPin = 7;

int inWater = 0;
int outWater = 0;
int gas = 0;

char income = 'x';

void setup() {
  Serial.begin(9600);
  pinMode(inWaterPin, INPUT);
  pinMode(outWaterPin, INPUT);
  pinMode(gasPin, INPUT);
  pinMode(lampLedPin, OUTPUT);
  pinMode(fireLedPin, OUTPUT);  
  digitalWrite(lampLedPin, HIGH);
  digitalWrite(fireLedPin, HIGH);  
}

void loop() {
  if(Serial.available()){
    income = Serial.read();
    if(income=='g'){  //가스 잠그기
      income = 'x';
      digitalWrite(fireLedPin, LOW);
    }
    if(income=='l'){
      income = 'x';
      digitalWrite(lampLedPin, LOW);
    }
  }
  
  inWater = analogRead(inWaterPin);
  outWater = analogRead(outWaterPin);
  gas = analogRead(gasPin);
  
  Serial.print("iwr");
  Serial.print(inWater);
  Serial.print(" ");
  Serial.print("owr");
  Serial.print(outWater);
  Serial.print(" ");
  Serial.print("gas");
  Serial.print(gas);
  Serial.print("\n");
  delay(1000);
}
