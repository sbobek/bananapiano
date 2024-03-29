int numKeys = 8;
int digInput[] = { 2, 3, 4, 5, 6, 7, 8, 9 };
String sounds[] = {"C","D","E","F","G","A","H","CC"};
bool pressed[] = {false, false, false,false,false,false,false,false};

void setup() {
  // Start serial connection
  Serial.begin(9600);
  // Configure digital input pins
  for (int i = 0; i < numKeys; i++) {
    pinMode(digInput[i], INPUT);
  }
}

void loop() {
  //
  for (int j = 0; j < numKeys; j++) {
    int sensorVal = digitalRead(digInput[j]);
    String outStr = String("");
    if (sensorVal == LOW) {
      if(pressed[j] == false){
              Serial.println(sounds[j]+String(":P"));
              pressed[j] = true;
       }
     }else if(pressed[j] == true){
        Serial.println(sounds[j]+String(":R"));
        pressed[j] = false;
     }
    }

  delay(250);
}