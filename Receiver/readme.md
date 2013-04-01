## #Client for Android

## Description
The Receiver is an example code for Android Communication between Android Cloud4All Modules.
The Receiver listen a special Intent. It is compatible from Android 2.3.3 to 4.2.
This Receiver was developed using Eclipse. This Eclipse project includes a main activity witch shows all data from the Device reporter. You can use this project to know how to use Device reporter. 

## License
This project has been developed by Vodafone Spain Foundation.
Client is shared under New BSD license. This project folder includes a license file.
You can share and distribute this project and built software using New BSD license.
Please, send any feedback to info@cloud4all.info

## How to use the service
Receiver is a example app, you just run the app after launch the CLient Example app. The Receiver listen BroadcastIntent, and then they launch a example Service to show the params

### Input
No input

### Output
Client launch a Broadcast Intent with a specific acction.

## Modules
There are three java files in the project. Here is a list of files with all classes. 
- MainActivity.java
This class extends from Activity and show a plain text on the screen	
-SettingHandlerReceiver.java
This class extends from BroadcastReceiver, its main role is listen broadcast intens with an especial action, in this case SystemSettingHandler action.	

-SystemSettingService.java
This class extends from Service, the intent is reading and invoke diferent util class to configurate settings

-SystemFontUtil.java
This class contains 	

-IntentCloud.java
This class extends from Intent, The action provide the information about who should listen this Intent. Adds some specific information, the fields of this class are:
	--idEvent: What should do
	--idModule: It's an int with the current module
	--Params: It's a  JSON format String with a dinamic number of key/value. To set and get an specific param, the class implements get and set methods

- CommunicationPersistence.java
This class provide the specifics codes for each event, modules.





## Bugs and decisions
There is a list of found bugs and topics about Device reporter development:
