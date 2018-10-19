/*
  Capacitive-Touch Arduino Keyboard Piano

  Plays piano tones through a buzzer when the user taps touch-sensitive piano "keys"

  Created  18 May 2013
  Modified 23 May 2013
  by Tyler Crumpton and Nicholas Jones
  Modified 19 Nov 2018 by Szymon Bobek

  This code is released to the public domain. For information about the circuit,
  visit the Instructable tutorial at http://www.instructables.com/id/Capacitive-Touch-Arduino-Keyboard-Piano/
*/

#include <CapacitiveSensor.h>

#define COMMON_PIN      10    // The common 'send' pin for all keys
#define NUM_OF_SAMPLES  10   // Higher number whens more delay but more consistent readings
#define CAP_THRESHOLD   150  // Capactive reading that triggers a note (adjust to fit your needs)
#define NUM_OF_KEYS     8    // Number of keys that are on the keyboard

// This macro creates a capacitance "key" sensor object for each key on the piano keyboard:
#define CS(Y) CapacitiveSensor(COMMON_PIN, Y)


// Defines the pins that the keys are connected to:
CapacitiveSensor keys[] = {CS(2),CS(3), CS(4), CS(5), CS(6), CS(7), CS(8), CS(9)};
char sounds[] = {'C','D','E','F','G','A','H','c'};
bool pressed[] = {false, false, false,false,false,false,false,false};

void setup() {
  // Turn off autocalibrate on all channels:
  Serial.begin(9600);
  for(int i=0; i<8; ++i) {
    keys[i].set_CS_AutocaL_Millis(0xFFFFFFFF);
  }

}

void loop() {
  // Loop through each key:
  for (int i = 0; i < 8; ++i) {
    // If the capacitance reading is greater than the threshold, play a note:
    if(keys[i].capacitiveSensor(NUM_OF_SAMPLES) > CAP_THRESHOLD) {
      if(pressed[i] == false){
        Serial.println(String("")+sounds[i]+String(":P"));
        pressed[i] = true;
      }
    }else if(pressed[i] == true){
      Serial.println(String("")+sounds[i]+String(":R"));
      pressed[i] = false;
    }


  }
}