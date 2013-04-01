#Enviromental reporter for Android

## Description
The enviromentalreporter is a module which examines data from sensors in the device.
This module has been made as a system service to be linked by any application or service. It is compatible from Android 2.3.3 to 4.2.
Enviromental reporter is designed to be included in Cloud4ALL enviroment.
This enviromental reporter was developed using Eclipse. This Eclipse project includes a main activity witch shows all data from the Enviromental reporter. You can use this project to know how to use Enviromental reporter. 

## License
This project has been developed by Technosite R&D department.
Enviromental reporter is shared under New BSD license. This project folder includes a license file.
You can share and distribute this project and built software using New BSD license.
Please, send any feedback to info@cloud4all.info

## How to use the service
Enviromental reporter is an Android service. You have to develop an activity or service which will connect to Enviromental reporter service using binding service connection.
You can read sample code in Viewer.java file to do this.
The enviromental reporter need some system permissions to access to information of sensors in the device.
You can check all permissions in AndroidManifest.xml file in the project.

### Input
Enviromental reporter gets all data from the device using the running context. Running context is an Java concept about an execution process.
You can get device data using a custom context or the current context. You can use these methods:

- public String getResultsWithContext(Context context, int typeOfData);
- Public String getResults(, int typeOfData);

The typeOfData parameter is a int constant. You can verify the possible values in EnviromentalReporter.java file.

### Output
Enviromental reporter uses a String value to return data of a sensor. 
You have to choose a sensor using the second parameter in getResult method.

## Modules
There are four java files in the project. Here is a list of files with all classes. 
- EnviromentalReporter.java
Here is a list of constants for enviromental reporter.
- EnviromentalReporterService.java
Here is the service class for Enviromental reporter. it manages the connection with the reporting engine.
- EnviromentalReporterEngine.java
This class examine sensors in the device and fill the collection for the reply of the service.
- Viewer.java
this class is a sample code to link to the Enviromental reporter service. Thiss class makes a connection with the Enviromental reporter service and it shows the result with the data of sensors.

## Elements in the results
The device reporter examine these groups of information:
- Brightness sensor

In future versions Enviromental reporter will examine these groups of information:
- Microphone
- Temperature
- Compas
- GPS position

## History log
- 0.1 - basic template for Enviromental reporter engine
- 0.2 - Brightness sensor support added to the project.


## Bugs and decisions
There is a list of found bugs and topics about Enviromental reporter development:

### Hardware access problem
There are problems to access to the hardware. The Enviromental reporter service needs system permissions to solve these problems.

### Observer pattern for the engine
Enviromental reporter should send notifications for an observer object. This observer could be Flow manager or Orquestator object in Cloud4All.
The observer has to register as observer and implements the interface of Enviromental reporter class.

### Noise detection
There is no direct access to microphone stream in Android. We have to use a Media recorder instance to get data from the microphone and get the amplitude of the record.
We have to decide the time, quality and size for the record.
The maximum amplitude of the record is the highest volume of the enviroment.