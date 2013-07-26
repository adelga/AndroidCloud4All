## #AccessibilityServiceNotify for Android

## Description
AccessibilityServiceNotify is an AccessibilityService which listen notification events an provide feedback, this feedback is a special vibration pattern and/or a dialog in front which can turn on the screen.
## License
This project has been developed by Vodafone Spain Foundation.

## How to use the service
 You should enable the accessibility service in Settings->Accessibility->AccessibilityServiceNotify, and them configure it thought a broadcastIntent.
### Input

### Input by broadcast receiver
The broadcast receiver receive configuration params to configure the notification feedback.

### Output
If the Service can configure succesfully all features, the app send a BroadcastIntent with "OK"


## History log
0.1 Haptic feedback
0.2 Screen dialog feedback

## Bugs and decisions
-No turn on the screen in all device.
-Not all notifications provide enought information to show properly the dialog.




