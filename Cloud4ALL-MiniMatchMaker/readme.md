#Mini match maker for Android

## Description
The Mini match maker is a module which checks data from the device, the enviroment and the user and it makes decisions in cloud4ALL enviroment.
This module has been made as a system service to be linked by any application or service. It is compatible from Android 2.3.3 to 4.2.
Mini match maker is designed to be included in Cloud4ALL enviroment.
This Mini match maker was developed using Eclipse. This Eclipse project includes a main activity witch shows all data from the Mini match maker. You can use this project to know how to use Mini match maker.
This project was designed as a demo for Mini Match maker. it includes a simulator for brightness and noise.

## License
This project has been developed by Technosite R&D department.
Mini match maker is shared under New BSD license. This project folder includes a license file.
You can share and distribute this project and built software using New BSD license.
Please, send any feedback to info@cloud4all.info

## Demo
The simulator let you change brightness and noise level.
Firstly, when you try to send environment data to the Mini Match Maker you receive a message about there is no stored rule in Mini Match maker. There is a simulated connection to Match maker in the Cloud for getting rules.
All operations for this demo are only messages.

This project includes two rules:
### close the blind
This rule will be executed when brightness level will be between 500 and 1000 and noise level will be between 500 and 1000.
This rule includes two operations.
#### JSON String: 
{"conditions":2,"condition2":"{\"maximum value\":1000,\"minimum value\":500,\"type\":\"range\",\"property\":\"noise\"}","condition1":"{\"maximum value\":1000,\"minimum value\":500,\"type\":\"range\",\"property\":\"brightness\"}","name":"close the blind","operation2":"{\"value\":\"Rule close the blind.\\nMove the device to other place.\",\"type\":\"message\"}","operation1":"{\"value\":\"Rule: close the blind.\\nIs noisy and bright here.\",\"type\":\"message\"}","operations":2,"type":"rule"}

### Go to bed
This rule will be executed when brightness level will be less than 40.
This rule includes one operation.
#### JSON String: 
{"conditions":1,"operation1":"{\"value\":\"Rule Go to bed.\\nYou are on darkness. I think it's time to go to bed.\",\"type\":\"message\"}","condition1":"{\"maximum value\":40,\"minimum value\":0,\"type\":\"range\",\"property\":\"brightness\"}","operations":1,"type":"rule","name":"Go to bed."}

## Bugs and decisions
There is a list of found bugs and topics about Mini match makerdevelopment:

### Singleton for kernel
The kernel of Mini match maker should manage all data between Match maker in the cloud and Flow manager module. If there is more than one instance of Mini match maker, synchronization problems can appear in runtime.
A solution for this possible bug is a singleton pattern for the kernel of Mini match maker.
### Communications with MatchMaker
The communication between Mini match maker and the Cloud will be a demo.
The first communication will reply a JSON object with rules. Mini match maker will load these rules in its rules table.
### Rules for MatchMaker
Match maker and Mini match maker manage data using rules. These rules have to store in a data table. To do this we have to design a class which represents each rule.
A rule includes a list of conditions and a list of operations.
A condition can be a boolean or a range.

