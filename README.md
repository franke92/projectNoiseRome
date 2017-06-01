## NoiseApp - Welcome:  <img src="https://github.com/projectNoiseRome/projectNoiseRome/blob/master/MockUp/icon.png" alt="Drawing" width="50" height="50"/>
NoiseApp is an accademic project developed for the Pervasive System class a Univerity of Rome - La Sapienza, Master Degree in Computer Engineering<br />
The goal of the project is to have a noise map of the city, in order to achieve better booking for tourist based on their preferences: for example, young tourist may like to be near a zone with high nightlife activities(so with a higher noise of level) and viceversa a family maybe want to avoid such places. On the other side, having a noise map of the city give us the possibility to study the noise pollution in a city like Rome, that make the project really intresting in term of utilization: acquiring the noise level data allow the user to find the best place for any eventuality and allow the researchers to study the noise phenomenon<br />

## System Composition:

We have a three components in our system:
- The noise sensors
- The web server
- The android app <br />
<img src="https://github.com/projectNoiseRome/projectNoiseRome/blob/master/MockUp/SystemDesign.png" alt="Drawing" width="475" height="350" description="Login"/><br />
> The system design<br />
#### Sensor:

The first component let us to retrieve the noise level data, and(we will see later in detail) with NodeRED we send this data to our web server. <br />
<img src="https://github.com/projectNoiseRome/projectNoiseRome/blob/master/NoiseAppSensor/SensorWorking.jpg" alt="Drawing" width="300" height="250" description="Login"/><br />
> A configuration with ArduinoUno wired to RaspberryPi, which is running NodeRED<br />

Sensor code(ArduinoUno and Genuino101): https://github.com/projectNoiseRome/projectNoiseRome/tree/master/NoiseAppSensor <br />
#### Server:

Then we have our Server node: this is a REST server, deployed on Tomcat and linked with a MySql dbms, in order to collect the data from our sensors and to share the data with the app. This is the core of the system: the server is the bridge between the user and the sensor. Using the REST paradigm make really us for us to manage the HTTP call, both on the sensor and on the smartphone. 

Server(Tomcat+Mysql, hosted on Microsoft Azure) : https://github.com/projectNoiseRome/NoiseAppServer <br />

#### App:
This is how the user can view in real time the noise level of the city and is able to make rilevations too, helping us to build the most accurate possible map. We discuss the technical details later

Android App : https://github.com/projectNoiseRome/NoiseApp  <br />

## Road to NoiseApp:
### Composition of the team - first half of March 2017:
In the first day of march we met and start to think about what kind of project we could realize in three months, considering the time of the develop plus the testing phase, and of course the theme for the Pervasive System class. After studying a little bit the main challenge for the smart city, we decide that the best field to approach was the sound one, in particular the monitoring of the noise level. 


### Presentantion of the idea and Mockup - second half of March 2017:
We explain in a first short presentation what was our idea, to build a noise map with some static sensor always transmitting data to a server, and then an Android app to merge and show the data. We needed some days to choose any of the technical aspects, and in the end we decide to develop a Java REST server and to use a MySql database in order to host the final product on Microsoft Azure cloud platform. Then we focus on the Android mock up, to show to the user in order have an idea if we were going in the right way. Here we show some images of our initial design(made with Balsamiq Mockups):

<img src="https://github.com/projectNoiseRome/projectNoiseRome/blob/master/MockUp/1%20-%20Login.png" alt="Drawing" width="100" height="200" description="Login"/><br />
> 1 - The login activity base on Google

<img src="https://github.com/projectNoiseRome/projectNoiseRome/blob/master/MockUp/2%20-%20NoiseMap(Home).png" alt="Drawing" width="100" height="200" description="Login"/><br />
> 2 - The main screen with the static sensor(circle) and the user rilevations(squares)

<img src="https://github.com/projectNoiseRome/projectNoiseRome/blob/master/MockUp/4%20-%20Calculate%20Result.png" alt="Drawing" width="100" height="200" description="Login"/><br />
> 3 - The user's calculation page

<img src="https://github.com/projectNoiseRome/projectNoiseRome/blob/master/MockUp/6%20-%20Stats.png" alt="Drawing" width="100" height="200" description="Login"/><br />
> 4 - The statistics page for the sensor

Here you can find the slides of our first presentation: https://www.slideshare.net/MarcoNigro6/noise-app <br />



### Users interview and presentation of the Mvp - second half of April 2017:
After many breefings to decide what kind and how many questions make to our users, we define our system design and start to develop, considering the first interviews collected. <br />

We divide our tasks into two main work: the Android app and the Server side(including the sensor). For the first one, we start developing with Android Studio, studying how to use the Google Maps apis to achieve our aims. On the other side, while for the server we setup an Apache Tomcat REST server with MySql as dmbs, we begin to work on the sensors to: we decide that ArduinoUno was the best solution. To retrieve the information from the controller, we test first a WiFi shield, then we choose to use NodeRED because it make more easier to share the data in a fast and secure way throw the web. So we arrive at the presentation with a live demo that was running our Android app prototype and the Server running live time.<br />

Here you can find the slides of the Mvp presentation: https://www.slideshare.net/MarcoNigro6/noise-app-mvp <br />

### Presentation of the final Android application and System - second half of May 2017:
After the feedbacks received at the MVP presentation, we begin the heavy develop of the app in order to let our user try the app and make some deep testing. At the second week of may we have implemented all the main functions in the app, having a lot of users feedbacks and hours of debug, we concentrate on the visual aspect, improving the general UI. Here there are some screenshots from our app:<br />

<img src="https://github.com/projectNoiseRome/projectNoiseRome/blob/master/Screenshots/Login.png" alt="Drawing" width="100" height="200" description="Login"/><br />
> 1 - Login<br />

<img src="https://github.com/projectNoiseRome/projectNoiseRome/blob/master/Screenshots/NoiseMap.PNG" alt="Drawing" width="100" height="200" description="Login"/><br />
> 2 - Home with the map<br />

<img src="https://github.com/projectNoiseRome/projectNoiseRome/blob/master/Screenshots/NavigationView.png" alt="Drawing" width="100" height="200" description="Login"/><br />
> 3 - NavigationView menu<br />

<img src="https://github.com/projectNoiseRome/projectNoiseRome/blob/master/Screenshots/SensorList.png" alt="Drawing" width="100" height="200" description="Login"/><br />
> 4 - Sensors list<br />

<img src="https://github.com/projectNoiseRome/projectNoiseRome/blob/master/Screenshots/Stats1.png" alt="Drawing" width="100" height="200" description="Login"/><br />
> 5 - Sensor Stats per day<br />

<img src="https://github.com/projectNoiseRome/projectNoiseRome/blob/master/Screenshots/Stats3.png" alt="Drawing" width="100" height="200" description="Login"/><br />
> 6 - Sensor Stats per hour<br />

<img src="https://github.com/projectNoiseRome/projectNoiseRome/blob/master/Screenshots/Calculate1.png" alt="Drawing" width="100" height="200" description="Login"/><br />
> 7 - Calculate Noise activity<br />

And here are our final presentation slide: https://www.slideshare.net/MarcoNigro6/noiseapp-final-presentation

### Next(possible) steps:
Of course this is an academic project, but there is a lot of possible work to do to improve our app. First of all, build a deep network of sensor to obtain a deep noise level monitoring. This is a very intresting point, because this could involve the community: we could create a NoiseBox(a plug and play box that need only a WiFi network and a power source) and give it to the peole, making this a shared project for the city. On other side, we still have a lot of work to improve our app, and we are studying some ways to enable the share on the social networks: As we said, there are a lot of ways to improve the project, but we built in three months a solid system, stable and with few minor bugs. <br />

We will post here all the news about our project, and we will be glad if someone from the GitHub community wants to join us!
Thanks for reading!


## Links:
Slides initial concept: https://www.slideshare.net/MarcoNigro6/noise-app<br />
MVP: https://www.slideshare.net/MarcoNigro6/noise-app-mvp<br />
Final Presentation: https://www.slideshare.net/MarcoNigro6/noiseapp-final-presentation<br/>
Android rep: https://github.com/projectNoiseRome/NoiseApp<br />
Server rep: https://github.com/projectNoiseRome/NoiseAppServer<br />
Website: http://noiseappproject.azurewebsites.net/   (work in progress)<br />
Video presentation:https://www.youtube.com/watch?v=pT_vTS6CXI8&feature=youtu.be<br/>

## Developed by:<br />
Marco Nigro       : https://www.linkedin.com/in/marco-nigro-283024140/<br />
Alessio Tirabasso : https://www.linkedin.com/in/alessio-tirabasso-44a023140/<br />
Federico Boarelli : https://www.linkedin.com/in/federico-boarelli-a4885311b/<br />
