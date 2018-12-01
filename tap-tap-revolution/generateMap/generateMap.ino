/*
 * MAP GENERATOR
 * This is version 1 of the map generator that seeks manual
 * user input to generate notes for a particular song. Not used 
 * as part of the main game.
 */

#include <LiquidCrystal.h>
#include <FastLED.h>

// INITIALIZE LCD
LiquidCrystal LCD(5, 6, 7, 8, 9, 10);

// BUTTON PINS
#define BUTTON_1 2
#define BUTTON_2 3
#define BUTTON_3 4

// LED STRIP PINS
#define STRIP_1 11
#define STRIP_2 12
#define STRIP_3 13

// LED AMOUNT ON EACH STRIP
#define NUM_LEDS 10 

// Button variables
int pressed1 = 0;
int pressed2 = 0;
int pressed3 = 0;

// List of notes
int strip1[200];
int strip2[200];
int strip3[200];

// Current 10 notes to focus on and display initialized as blank
int strip1Focus[10];
int strip2Focus[10];
int strip3Focus[10];

// LED strip arrays
CRGB strip1LED[NUM_LEDS];
CRGB strip2LED[NUM_LEDS];
CRGB strip3LED[NUM_LEDS];


void setup() {
  Serial.begin(9600);
  
  // Initialize buttons
  pinMode(BUTTON_1, INPUT);
  pinMode(BUTTON_2, INPUT);
  pinMode(BUTTON_3, INPUT);

  // Fill arrays
  for (int i = 0; i < 200; i++) {
    if (i % 2 == 0) {
      strip1[i] = 0;
      strip2[i] = 0;
      strip3[i] = 0;
    } else {
      strip1[i] = i;
      strip2[i] = i;
      strip3[i] = i;
    }
  }

  // Initialize LCD
  LCD.begin(16, 2);
  LCD.setCursor(0, 0);

  // Initialize LED strips
  FastLED.addLeds<WS2812, STRIP_1, GRB>(strip1LED, NUM_LEDS);
  FastLED.addLeds<WS2812, STRIP_2, GRB>(strip2LED, NUM_LEDS);
  FastLED.addLeds<WS2812, STRIP_3, GRB>(strip3LED, NUM_LEDS);
}


void startPanel() {
  // Display start message
  LCD.setCursor(0, 0);
  LCD.print("                ");
  LCD.setCursor(0, 1);
  LCD.print("                ");
  LCD.setCursor(0,0);
  LCD.print("PUSH TO START:");

  // Reset button presses
  pressed1 = 0;
  pressed2 = 0;
  pressed3 = 0;

  // Exit starting screen when any button is pressed
  while (!(pressed1 || pressed2 || pressed3)) {
    pressed1 = digitalRead(BUTTON_1);
    pressed2 = digitalRead(BUTTON_2);
    pressed3 = digitalRead(BUTTON_3);
    delay(50);
  }
}


void displayNotes() {
  // Displaying the notes for timing consistency
  for (int i = 0; i < NUM_LEDS; i++) {
    if (strip1Focus[i]) {
      strip1LED[i] = CRGB(0,0,255); 
    } else {
      strip1LED[i] = CRGB(0,0,0);
    }
    
    if (strip2Focus[i]) {
      strip2LED[i] = CRGB(0,0,255); 
    } else {
      strip2LED[i] = CRGB(0,0,0);
    }
    
    if (strip3Focus[i]) {
      strip3LED[i] = CRGB(0,0,255); 
    } else {
      strip3LED[i] = CRGB(0,0,0);
    }
  }

  // Show notes
  FastLED.show();
}


void game() {
  // Reset button presses
  pressed1 = 0;
  pressed2 = 0;
  pressed3 = 0;
  
  // Main game loop
  for (int i = 0; i< 200; i++){
    // Refill focus arrays (used for timing consistency)
    for (int j = 0; j < 10; j++) {
      strip1Focus[j] = strip1[i];
      strip2Focus[j] = strip2[i];
      strip3Focus[j] = strip3[i];
    }
    
    // Reset button presses
    pressed1 = 0;
    pressed2 = 0;
    pressed3 = 0;

    // Read user button input
    for (int n = 0; n < 30; n++){
      pressed1 = pressed1 || digitalRead(BUTTON_1);
      pressed2 = pressed2 || digitalRead(BUTTON_2);
      pressed3 = pressed3 || digitalRead(BUTTON_3);
      delay(7);
    }

    // Enter values into the strip to be used in game
    if (pressed1 == HIGH) {
      strip1[i] = 1;
    }else{
      strip1[i] = 0;
    }
    if (pressed2 == HIGH) {
      strip2[i] = 2;
    }else{
      strip2[i] = 0;
    }
    if (pressed3 == HIGH) {
      strip3[i] = 3;
    }else{
      strip3[i] = 0;
    }
    
    delay(10);
  }
}


void loop() {
  startPanel();

  // Run main game loop to manually input generated notes
  game();

  // Display all generated notes
  Serial.println();
  for (int i = 0; i < 200; i++) {
    Serial.print(strip1[i]);
    Serial.print(",");
  }
  Serial.println();
  for (int i = 0; i < 200; i++) {
    Serial.print(strip2[i]);
    Serial.print(",");
  }
  Serial.println();
  for (int i = 0; i < 200; i++) {
    Serial.print(strip3[i]);
    Serial.print(",");
  }

  delay(100000);
}
