## #User Listener for Android

## Description
The User Listener is a module which handles the login interactions of the user and provides the credentials to the rest of the system.
The different modules, when installed on a device, are offering the user the ability to retrieve and generate unique credential IDs and
store them into a physical NFC tag or a QR code that can be shared and printed.

## License
This project has been developed by the Life Supporting Technologies lab at the UPM.
Device reporter is shared under New BSD license.
You can share and distribute this project and built software using New BSD license.
Please, send any feedback to info@cloud4all.info

## How to use the modules
The User Listener are a set of Android modules sorted by different packages.
To use them, the developer can either use the test Activity for Android that is included or create their own solutions as well.
The NFC modules can both create and scan NFC tags.
When a NFC tag is NDEF formated and has been generated with the NFC writing tool of the same set, then the device is going to
trigger the default action.
If the user wants to create a new tag, he just have to modify the String field on the test/Main.java class.
The same concept is applied to any driver or stub of the User Listener modules, including the QR generation.
These modules do require just one permission in order to work propertly.
This permission will able the application to use the NFC hardware as stated on the included AndroidManifest.xml file in the project.

### Input
The User Listener receives a physical user interaction, either a QR scan or a NFC scan.
The QR code or the NFC may have the credential in an appropiate forma so the actual input to the system would be that credential in 
String format.

### Output
The User Listener modules will use the sharing mechanism between the different Cloud4all modules so the information reaches
the system orquestator.
In the other use cases, the output will be a credential stored on a NFc tag or a new QR code that can be shared in many ways.

## Modules
There are 8 different packages containing from one to a bunch of different classes each one.
The public interfaces that may be reusable and many of the relevant information on the classes is described following
the javadoc metalanguage.

## History log
- 1.0 - Basic NFC and QR functionality with simple test driver GUI.


## Bugs and decisions
Not found (yet).