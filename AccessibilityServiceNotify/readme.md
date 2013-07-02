## #Device reporter for Android

## Description
The Device reporter is a module which examines hardware and software features of an Android device.
Device reporter manages static and dinamic data about the hardware and software of the device which is running this service.
This module has been made as a system service to be linked by any application or service. It is compatible from Android 2.3.3 to 4.2.
Device reporter is designed to be included in Cloud4ALL enviroment.
This device reporter was developed using Eclipse. This Eclipse project includes a main activity witch shows all data from the Device reporter. You can use this project to know how to use Device reporter. 

## License
This project has been developed by Technosite R&D department.
Device reporter is shared under New BSD license. This project folder includes a license file.
You can share and distribute this project and built software using New BSD license.
Please, send any feedback to info@cloud4all.info

## How to use the service
Device reporter is an Android service. You have to develop an activity or service which will connect to Device porter service using binding service connection.
You can read sample code in Viewer.java file to do this.
Device reporter supports broadcast communication for Android. Device reporter includes a broadcast receiver to accept petitions from other applications.
The device reporter need some system permissions to access to information of the device.
You can check all permissions in AndroidManifest.xml file in the project.

### Input
Device reporter gets all data from the device using the running context. Running context is an Java concept about an execution process.
You can get device data using a custom context or the current context. You can use these methods:

- public HashMap getResultsWithContext(Context context);
- Public HashMap getResults();

### Input by broadcast receiver
Device reporter uses a broadcast receiver to get petitions from other applications.
The broadcast notification name uses this string: "cloudDeviceReporter".
You can check the constants for persistence in CommunicationPersistence.java file.
If you use broadcast communication, Device reporter supports two ways:
- All data in Device reporter: Send a broadcast notification with this key-value: ReporterType, ROOT.
- A specific value in Device reporter: send a broadcast with this key-value: ReporterValue, NAME_OF_THE_PARAMETER_IN_DEVICE_REPORTER_LIST

### Output
Device reporter uses a collection(HashMap<String,String>) for replying the connection.
There is no duplicate key using a HashMap. Keys and values are stored as strings. You have to convert values to numbers if you will manage that data.

For broadcast communications Device reporter offers two types of results:
- All data in Device reporter: Device reporter answers a string value with the path where the JSON result file is saved.
- A specific value in Device reporter: Device reporter answers a broadcast with the key-value that you need. If the key was not in the hasmap data the value will be "ERROR".

## Elements in the results
The device reporter examine these groups of information:
- Audio
- Battery
- Bluetooth
- Wifi
- NFC
- Camera
- Keyboard
- Installed applications 
- Installed services
- Screen
- Sensors in the device
- Telephony 
Some of these results are dynamic data. These dynamic values can be modified by the operating system, the user or the enviroment. The other results are static. They are about model of the device, Brand, screen size, etc.
Here are the static results:
- device identifier (Model, brand)
- Operating system (Version, userID, SDK version)
- Cameras (numbers, lent side, resolution)
- JoyPad support

## History log
- 0.1 - basic template for Device reporter engine
0.2 - Functions for operating system information and device identification added in the Device reporter engine
0.3 - Functions for screen and battery added in the Device reporter engine
0.4 - functions for audio management added in the Device reporter engine
0.5 - Functions for sensors added in the Device reporter engine
0.6 - functions for applications and services added in the Device reporter engine
0.7 - functions for Wifi, Bluetooth and NFC added in the Device reporter engine
0.8 - Tests about Device reporter's performance running as an application, service, IntentService and a broadCast.
0.9 - Device reporter developed as a service.
1.0 - Stable version of Device reporter. Viewer class added as a demo
1.1 - Broadcast support added.
1.2 - JSON file for results saved in external memory.
1.3 - Ask for a specific value function added.
2.0 - Stable version of Device reporter with broadcast support.

## Bugs and decisions
There is a list of found bugs and topics about Device reporter development:

### Hardware access problem
There are problems to access to the hardware. The Device reporter service needs system permissions to solve these problems.

### Software access problem
There are problems to identify if a system service is available in the device. In many cases the problem is because of the Device reporter has not enough permissions to do it.
In other cases the problem is the service is busy. For example, Text to speech service could be busy because there is a screen reader running on the device.

### TTS always busy
The screen reader doesn't release the TTS engine after if is stopped. It would be a bug of the screen reader. It was posted to Talk back developer team for a response.

### Screen size management
The functions to get information about screen appears in SDK17. In previous versions of the Android SDK there was not real way to get this information. 
To solve this problem Device reporter uses two solutions: getting information about context view and getting information about software screen. 
Each solution is used checking the Android version of the device.

### wired headphones detection
The function to detect wired headphoned is marked as deprecated in last versions of the Android SDK.
The function is still working in SDK 17 but it would be unsupported in next versions.
There is no alternative for this function.

### Time to solve the task
The device reporter needs 1,7 seconds in version 1.0 to get all information about the device. We used a Galaxy Nexus with Android 4.2 for the tests.
This time is too much for completing a login process in Cloud4All project. The Device reporter's performance should be improved.
There are some solutions for this problem.
A solution for this problem is add a new method to get the information about the device including a filter.
A piece of the results of Device reporter is static data. This static data can be stored in a file to spend less time in the Device reporter task.
This file with static data would be created when the system starts. The main module of Cloud4ALL enviroment should run this first loading process for Device reporter.
These solutions will be included in next versions of the Device reporter

### Broadcast communication
Device reporter includes a broadcast receiver and a broadcast sender module to add external communication with other Cloud4All 302.2 task modules.
All constants for identifications and notifications are in CommunicationPersistence.java file. This file has to be shared and updated with all modules.

### Ask for a specific value
Device reporter can answer a specific value of its results. This feature is available only for broadcast communication. This feature will be added as a common function for other communication methods with Device reporter.
