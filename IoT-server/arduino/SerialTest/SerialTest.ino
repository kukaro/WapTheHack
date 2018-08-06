const int WATERPIN = 0;
int val = 0;

void setup() {
  Serial.begin(9600);
  pinMode(WATERPIN, OUTPUT);
}

void loop() {
  val = analogRead(WATERPIN);
  Serial.println(val);
  delay(500);
}
