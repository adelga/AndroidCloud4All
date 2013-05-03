#SystemSettingHandler for Android

## Description
The SystemSettingHanderl is a module which main function is configurate diferentes system params
SystemSettingHandler receive the params from the Orchestrator though a Intent.
This module has been made as a system service . It is compatible from Android 4.0 (ICS) or more.
SystemSettingHandler is designed to be included in Cloud4ALL enviroment.
This SystemSettingHandler was developed using Eclipse. 

## License
This project has been developed by Vodafone Spain Foundation.
SystemSettingHandler is shared under New BSD license. This project folder includes a license file.
You can share and distribute this project and built software using New BSD license.
Please, send any feedback to http://www.cloud4all.info

## How to use the service
SystemSettingHandler run as an  Android service. You should send an BroadCastIntent with an special action and some extras to communicate with its, Check the documentation about communication on http://wiki.gpii.net/index.php/Android_Overview.
The device reporter need some system permissions to access to some system params.
You can check all permissions in AndroidManifest.xml file in the project.

### Input
The input its the Intent Extras with the params that should be modified on device.

### Output
SystemSettingHandler only return an message about the configuration status, it all field have been change properly, this message will be "OK".

## Modules
There are java files in the project. Here is a list of files with all classes. 
-SettingHandlerReceiver.java
This class extends from BroadcastReceiver, its main role is listen broadcast intens with an especial action, in this case SystemSettingHandler action.	

-SystemSettingService.java
This class extends from Service, the intent is reading and invoke diferent util class to configurate settings

-SystemFontUtil.java
This class contains methods to configurate font settings. 

-SystemSoundUtil.java
This class contains methods to configurate sound settings. 

-SystemSettingUtil.java
This class contains methods to configurate system settings. 

-DummyActivity.java
This class extends from Activity, it's an Activity without UI which function is change the screen brightness instanly.

-IntentCloud.java
This class extends from Intent, The action provide the information about who should listen this Intent. Adds some specific information, the fields of this class are:
	--idEvent: What should do
	--idModule: It's an int with the current module
	--Params: It's a  JSON format String with a dinamic number of key/value. To set and get an specific param, the class implements get and set methods

- CommunicationPersistence.java
This class provide the specifics codes for each event, modules.

## Elements in the results
Curently, SystemSettingParams can modify this params:
- system settings.
- Default sounds.
- Volumes.

(see the complete parameters-list on http://wiki.gpii.net/index.php/Android_SystemSetting_Handler )


## History log
- 0.1 - basic functionality on SystemSettingHandler
0.2 - Implements Cloud4All Communication
0.3 -

## Bugs and decisions
- Bug in notification Sound and Rigntone, sometimes don't get insert to media ContentProvider database the new file.




