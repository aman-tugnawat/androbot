//Code for controlling a bot with 4 motors
void setup() {
  Serial.begin(9600);
  Serial.println("Setup Started");
  pinMode(4, OUTPUT);      
  pinMode(5, OUTPUT);      
  pinMode(2, OUTPUT);      
  pinMode(3, OUTPUT);      
  pinMode(8, OUTPUT);      
  pinMode(9, OUTPUT);      
  pinMode(12, OUTPUT);      
  pinMode(13, OUTPUT);      
  Serial.println("Start loop...");
}


void loop() { 
        
  if(Serial.available() > 0) {
    int incomingByte = Serial.read();
    //Serial.println(incomingByte);
    
   if(incomingByte==48){

          digitalWrite(4, LOW);
          digitalWrite(5, LOW);
          digitalWrite(2, LOW);
          digitalWrite(3, LOW);
          digitalWrite(8, LOW);
          digitalWrite(9, LOW);
          digitalWrite(12, LOW);
          digitalWrite(13, LOW);
          
    }
    if(incomingByte==49){
          digitalWrite(4, LOW);
          digitalWrite(5, HIGH);
          digitalWrite(2, LOW);
          digitalWrite(3, HIGH);
          
      
    }
    if(incomingByte==50){
          digitalWrite(4, HIGH);
          digitalWrite(5, LOW);
          digitalWrite(2, HIGH);
          digitalWrite(3, LOW);
         
    }
    if(incomingByte==51){
          digitalWrite(4, HIGH);
          digitalWrite(5, LOW);
          digitalWrite(2, LOW);
          digitalWrite(3, HIGH);
          
    }
    if(incomingByte==52){
          digitalWrite(4, LOW);
          digitalWrite(5, HIGH);
          digitalWrite(2, HIGH);
          digitalWrite(3, LOW);
          
    }
    if(incomingByte==53){
       digitalWrite(8, LOW);
       digitalWrite(9, HIGH);
    }
    if(incomingByte==54){
       digitalWrite(8, HIGH);
       digitalWrite(9, LOW);
    }
    if(incomingByte==55){
      digitalWrite(12, LOW);
      digitalWrite(13, HIGH);
      
    }
  
  }
}
