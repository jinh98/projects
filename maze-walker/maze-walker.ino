
  
int trigPin = 3;  //Sensor Trip pin connected to Arduino pin 13
int echoPin = 2;  //Sensor Echo pin connected to Arduino pin 11
int myCounter = 0;  //declare your variable myCounter and set to 0
int servoControlPin = 6;  //Servo control line is connected to pin 6 but we don't have one
float pingTime;  //time for ping to travel from sensor to target and return
float targetDistance;  //Distance to Target in inches
float speedOfSound = 1250;  //Speed of sound in Km per hour when temp is 77 degrees.
float distanceLeft;  //distance to the left
float distanceRight;  //distance to the right

#define E1 10       // motors have analog pins
#define E2 11     

#define I1 8      
#define I2 9     
#define I3 12     
#define I4 13     

void setup() {

Serial.begin(9600);
pinMode(E1, OUTPUT);
pinMode(E2, OUTPUT);

pinMode(I1, OUTPUT);
pinMode(I2, OUTPUT);
pinMode(I3, OUTPUT);
pinMode(I4, OUTPUT);


pinMode(trigPin,  OUTPUT);
pinMode(echoPin,  INPUT);






}
  
void  loop() {
    digitalWrite(I1, LOW);
    digitalWrite(I2, HIGH);
    digitalWrite(I3, HIGH);
    digitalWrite(I4, LOW);

    delay(100);

    //code for the ultrasonic
    digitalWrite(trigPin, LOW);  //Set trigger pin low
    delayMicroseconds(2000);  //Let signal settle
    digitalWrite(trigPin, HIGH);  //Set trigPin high
    
    delayMicroseconds(15);  //Delay in high state
    digitalWrite(trigPin, LOW);  //ping has now been sent
    delayMicroseconds(10);  //Delay in high state
    
    pingTime = pulseIn(echoPin,  HIGH);  //pingTime is presented in microceconds
    pingTime=pingTime / 1000000;  //convert pingTime to seconds by dividing by 1000000 (microseconds in a second)
    pingTime=pingTime / 3600;  //convert pingtime to hours by dividing by 3600 (seconds in an hour)
    targetDistance = speedOfSound * pingTime;  //This will be in Km, since speed of sound was Km per hour
    targetDistance = targetDistance/2;  //Remember ping travels to target and back from target, so you must divide by 2 for actual target distance.
    targetDistance = targetDistance*100000;  //Convert Km to cm by multipling by 100000 (cm per Km)
    Serial.println(targetDistance);
    
//    targetDistance = getDistance();
    //end of code for ultrasonic

    //code for cart
    if(targetDistance >= 12){  // if the distance is greater than 12 cm, do nothing
      forward();
    } else {
      backUp();
      turnRight90();  // if not, back up then turn right
      //distanceRight = getDistance();
      turnLeft90();
      turnLeft90();
      //distanceLeft = getDistance();

     /* if(distanceRight > distanceLeft){
        turnRight90();
        turnRight90();
      }else{
      }
      */
    }  
    
    delay(100);  //pause to let things settle

}



void backUp(){

  digitalWrite(I1, HIGH);
  digitalWrite(I2, LOW);
  digitalWrite(I3, LOW);
  digitalWrite(I4, HIGH);
  
  analogWrite(E1, 230);
  analogWrite(E2, 180);
  delay(500);
}

void forward() {
  
    digitalWrite(I1, LOW);
    digitalWrite(I2, HIGH);
    digitalWrite(I3, HIGH);
    digitalWrite(I4, LOW);

    analogWrite(E2, 230);
    analogWrite(E1, 230);
    delay(20);
}

void turnRight90() {
  
  digitalWrite(I1, HIGH);
d  digitalWrite(I2, LOW);
  digitalWrite(I3, HIGH);
  digitalWrite(I4, LOW);

  analogWrite(E1, 180);
  analogWrite(E2, 230);
  delay(450);
}

void turnLeft90() {
  
  digitalWrite(I1, LOW);
  digitalWrite(I2, HIGH);
  digitalWrite(I3, LOW);
  digitalWrite(I4, HIGH);

  analogWrite(E1, 180);
  analogWrite(E2, 230);
  delay(450);
}

/*float getDistance(){
  //code for the ultrasonic
    digitalWrite(trigPin, LOW);  //Set trigger pin low
    delayMicroseconds(2000);  //Let signal settle
    digitalWrite(trigPin, HIGH);  //Set trigPin high
    
    delayMicroseconds(15);  //Delay in high state
    digitalWrite(trigPin, LOW);  //ping has now been sent
    delayMicroseconds(10);  //Delay in high state
    
    pingTime = pulseIn(echoPin,  HIGH);  //pingTime is presented in microceconds
    pingTime=pingTime / 1000000;  //convert pingTime to seconds by dividing by 1000000 (microseconds in a second)
    pingTime=pingTime / 3600;  //convert pingtime to hours by dividing by 3600 (seconds in an hour)
    targetDistance = speedOfSound * pingTime;  //This will be in Km, since speed of sound was Km per hour
    targetDistance = targetDistance/2;  //Remember ping travels to target and back from target, so you must divide by 2 for actual target distance.
    targetDistance = targetDistance*100000;  //Convert Km to cm by multipling by 100000 (cm per Km)
    Serial.println(targetDistance);
    
  }*/
