# Androbot
An android app which allows you to control any Robot or Embedded Sys. interfaced bluetooth using Serial Port Profile(SPP).
Download from playstore https://play.google.com/store/apps/details?id=com.at.androbot

This is a simple app for beginners learning to use Arduino/Any Development using Serial communication and control their project from their Android Smartphone/Tablet.
Understanding how the app sends data:

The app uses separate Thread with a loop to send data continuously after every 100ms (this would be user configurable in further versions of the app) in form of a byte*.
*(Which could be type-casted to int as well char as per convenience)

Which key/button would send will send which byte can be seen in the table:

<style type="text/css">
.tg  {border-collapse:collapse;border-spacing:0;border-color:#999;border:none;}
.tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:0px;overflow:hidden;word-break:normal;border-color:#999;color:#444;background-color:#F7FDFA;}
.tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:0px;overflow:hidden;word-break:normal;border-color:#999;color:#fff;background-color:#26ADE4;}
.tg .tg-2dkq{background-color:#3166ff;color:#ffffff;vertical-align:top}
.tg .tg-ufe5{background-color:#34cdf9;vertical-align:top}
.tg .tg-to9j{font-family:Georgia, serif !important;;background-color:#3166ff;color:#ffffff;vertical-align:top}
</style>
<table class="tg">
  <tr>
    <th class="tg-2dkq">Key</th>
    <th class="tg-ufe5"> 1</th>
    <th class="tg-ufe5">2</th>
    <th class="tg-ufe5">3</th>
    <th class="tg-ufe5">4</th>
    <th class="tg-ufe5">5</th>
    <th class="tg-ufe5">6</th>
    <th class="tg-ufe5">7</th>
  </tr>
  <tr>
    <td class="tg-2dkq">Data(char)</td>
    <td class="tg-ufe5">“1”</td>
    <td class="tg-ufe5">“2”</td>
    <td class="tg-ufe5">“3”</td>
    <td class="tg-ufe5">“4”</td>
    <td class="tg-ufe5">“5”</td>
    <td class="tg-ufe5">“6”</td>
    <td class="tg-ufe5">“7”</td>
  </tr>
  <tr>
    <td class="tg-2dkq">Data(int )</td>
    <td class="tg-ufe5">48</td>
    <td class="tg-ufe5">49</td>
    <td class="tg-ufe5">50</td>
    <td class="tg-ufe5">51</td>
    <td class="tg-ufe5">52</td>
    <td class="tg-ufe5">53</td>
    <td class="tg-ufe5">54</td>
  </tr>
  <tr>
    <td class="tg-to9j">Data(byte)</td>
    <td class="tg-ufe5">48</td>
    <td class="tg-ufe5">49</td>
    <td class="tg-ufe5">50</td>
    <td class="tg-ufe5">51</td>
    <td class="tg-ufe5">52</td>
    <td class="tg-ufe5">53</td>
    <td class="tg-ufe5">54</td>
  </tr>
</table>

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

