# androbot
An android app which allows you to control any Robot or Embedded Sys. interfaced bluetooth using Serial Port Profile(SPP).

This is a simple app for beginners learning to use Arduino/Any Development using Serial communication and control their project from their Android Smartphone/Tablet.
Understanding how the app sends data:

The app uses separate Thread with a loop to send data continuously after every 100ms (this would be user configurable in further versions of the app) in form of a byte*.
*(Which could be type-casted to int as well char as per convenience)

Which key/button would send will send which byte can be seen in the table:

Key         1  	2	  3  	4  	5 	6  	7
Data(char)	“1”	“2”	“3”	“4”	“5”	“6”	“7”
Data(int )	48	49	50	51	52	53	54
Data(byte)	48	49	50	51	52	53	54

The key/button no. can be seen in image shown on top i.e. the promo image.
For example:
If the key 1 is pressed on the screen. The data send would be single char i.e.“1” which could be type casted and used to establish control over Arduino as follows:

Int read;
read=Serial.Read();
if(read==48){
//Write your Code for what to do
}

About the App Documentation can be found at this link:
http://goo.gl/gPr1CE

Sample Arduino Source Code can be downloaded from this link:
http://goo.gl/WpakUp

I will be adding adding further functionality based on user reviews like accelerometer,GPS , PWM ,Serial terminal etc so if you have any specific need please do write an review.

