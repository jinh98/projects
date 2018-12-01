#include <Servo.h>

//defining servo stuff
#define S1 9
#define S2 10
Servo topServo;
Servo bottomServo;


int pos = 0; 

//defining color sensor stuff

int redled = 2;
int greenled = 4;
int blueled = 3;

int color = 0 ;

//adjustment values
int raj= 6;
int gaj = -20;
int baj = -10;
int nocolor = 70;

int value = A0;
int red;
int blue;
int green;

int redvalue;
int bluevalue;
int greenvalue;

int redout = 5;
int greenout=7;
int blueout = 6;

void setup() {
  pinMode (redled, OUTPUT);
  pinMode (greenled, OUTPUT);
  pinMode (blueled, OUTPUT);

  pinMode (value, INPUT);
  
  pinMode (redout, OUTPUT);
  pinMode (greenout, OUTPUT);
  pinMode (blueout, OUTPUT);
  
  topServo.attach(9);
  bottomServo.attach(10);
  
  Serial.begin (9600);
}

// ************************************************************** Main LOOP
void loop(){
  
   //Top servo turns to the spot and get color
  topServo.write(25); // this is subject to change
  delay(500);
  
  
  
    for(int i = 0; i<85; i++){
    topServo.write(i);
    delay(10);
  }
  
  color = readColor(); // get color
  
  //Bottom servo turns as color is being detected
  if (color == 1){
    bottomServo.write(135);
  }else if (color == 2){
    bottomServo.write(120);
   
  }else if (color == 3){  
    bottomServo.write(95);
  }else{

  }
  delay (2000);//wait for bottom servo to turn to position

  
  //Make the top servo spin to dropping position
  topServo.write(170); // this is subject to change
  delay(1000);

  topServo.write(25);
  delay(1000);

  color = 0;
   
   

   
}///// ************************************************************ End of main loop

//custom function
int readColor(){

  // use a for loop to sense the color: time needed
  for (int i = 0; i< 20; i ++){
   color = 0;

   // detects red
  digitalWrite(redled,HIGH);
   delay (40);
   red=analogRead(value)+raj;
   delay(10);
   Serial.print("R=");
   Serial.println(red);
   digitalWrite(redled,LOW);

   //green
   digitalWrite(greenled,HIGH);
   delay (40);
   green=analogRead(value)+gaj;
   delay(10);
   Serial.print("G=");
   Serial.println(green);
   digitalWrite(greenled,LOW);

   //blue
   digitalWrite(blueled,HIGH);
   delay (40);
   blue=analogRead(value)+baj;
   delay(10);
   Serial.print("B=");
   Serial.println(blue);
   digitalWrite(blueled,LOW);
   
  
   if (red > green && red > blue && red < nocolor)
   {
    Serial.println("Red");
      redvalue =HIGH;
      color = 1;
   }else{
    
    redvalue = LOW;
   }


   if (blue > red && blue > green && blue < nocolor)
   {
      bluevalue =HIGH;
      Serial.println("Blue");
      color = 2;
   }else{
    bluevalue = LOW;
   }
   
   
   if (green > red  && green > blue && green < nocolor)
   {
      greenvalue =HIGH;
      Serial.println("Green");
      color = 3;
   }else{
    greenvalue = LOW;
   }

   
  if (greenvalue == bluevalue == redvalue == LOW)
  color =0;
  
   digitalWrite(redout, redvalue);
   digitalWrite(greenout, greenvalue);
   digitalWrite(blueout, bluevalue);

  }
  return color;
}


