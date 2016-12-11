# GOALProject
Project for CS449 that is meant to gamify civic participation. 
Note that the emulator/tablet HAS to be connected to the internet in order to work - it has to pull information from the internet. 
In order for people testing this to see if the event code is added properly, the event codes are listed in the descriptions. 
So you should see the event codes listed in the fields, which will allow you to test it properly. 
Also: the program doesn't load all of the data in at first because everything is in the ASyncTask and it doesn't load it dynamically
You have to open the app, and then exit or go to another app so that the onCreate function can get the events the second time around. 
This is a feature that will be updated later. 

Once you see events, the codes will be listed in them. You can add to calendar by simply clicking the button and confirming. 
You can get points by clicking "Get points" and type in the event code afterwards. It will then take you to a survey to fill out
It doesn't really matter if you complete the form or not

If the app has issues, please contact me so I can demo it - the Jsoup module has had weird issues before but hopefully it doesn't with your emulator. 
