#define p1 2
#define p2 3
#define p3 4
#define p4 5
#include <SoftwareSerial.h>
#include <Servo.h>
SoftwareSerial mySerial(10, 11); // RX, TX
Servo myservo,myspeed;  // create servo object to control a servo
int pos=50;
int spd=50;
int ctr=48;
int i=1;
void setup() {
  Serial.begin(9600);
  Serial.println("Ready");
  mySerial.begin(9600);
  mySerial.println("Setup Started");
  myservo.attach(9);      
  myservo.write(pos);
  myspeed.attach(8);
  myservo.write(spd); 
  pinMode(p1, OUTPUT);      
  pinMode(p2, OUTPUT);      
  pinMode(p3, OUTPUT);      
  pinMode(p4, OUTPUT);
  digitalWrite(p1, LOW);  
  digitalWrite(p2, LOW);  
  digitalWrite(p3, LOW);  
  digitalWrite(p4, LOW);
  mySerial.println("Start loop...");
  Serial.flush();  
}
void loop() {   
  if(mySerial.available() > 0) {
  int incomingByte = mySerial.read();
  switch(i){
    case 1:
      if(incomingByte==127){
        i+=1;
      }
      break;
    case 2:
      ctr=incomingByte;
      i+=1;
      break;
    case 3:
      spd=incomingByte;
      i+=1;
      break;
    case 4:
      pos=incomingByte;
      i+=1;
      break;
    default:
      i=1;
      break;
  }
}


 if(i==5){
    //Serial.print("Control:");
    //Serial.println(ctr);
    //Serial.print("Speed:");      //Commentout these lines of code when you are not debugging
    //Serial.println(spd); 
    //Serial.print("Angle:");
    //Serial.println(pos);
    myservo.write(map(pos, 0, 100, 45, 105));
    myspeed.write(map(spd, 0, 100, 0, 180));
    switch(ctr){
      case 49:
       digitalWrite(p1, LOW);  
       digitalWrite(p2, HIGH);  
       digitalWrite(p3, LOW);  
       digitalWrite(p4, HIGH); 
       break;
      case 50:
        digitalWrite(p1, HIGH);  
        digitalWrite(p2, LOW);  
        digitalWrite(p3, HIGH);  
        digitalWrite(p4, LOW); 
       break;
      default:
       digitalWrite(p1, LOW);  
       digitalWrite(p2, LOW);  
       digitalWrite(p3, LOW);  
       digitalWrite(p4, LOW); 
      }

     i=1;
     mySerial.flush();
     Serial.flush();
 }
 /*else {
     digitalWrite(p1, LOW);  
     digitalWrite(p2, LOW);  
     digitalWrite(p3, LOW);  
     digitalWrite(p4, LOW); 
 }*/
 delay(10);
}

