# RadioRecorder
For more informations : https://radiorec.raclette-it.com
## What is RadioRecorder ?
RadioRecorder is a simple java app that continuously record an audio input and divides it in X minutes sessions. It also deletes records past a certain date that is user defined.
## How to use it ?
### Setup
- First, get the latest build of RadioRecorder fomr the `builds/` folder and put the jar together with the two xml files `lang.en.xml` and `lang.fr.xml` in a folder.
- Make sure the folder has the right permissions allowing RadioRecorder to write, modify and be executed. 
### First Launch
- Open a terminal and get in the same directory as RadioRecorder. 
- Execute the command `java -jar RadioRecorder-github.X.X.jar`.
- If you have trouble see the [Troubleshooting] sectiion "Launch"
- If you want to have a launch script as well as to know what are the launch options available, check the [Advance Use] section "Launch"
### Configuration
- When you first launch RadioRecorder, it creates the `data/` folder which contains all recordings and also a `properties.rsp` file.
- You will also have a configuration assistant started

Beware ! Double check what you are writing because as for the moment (github.1.0) there isn't any kind of protection and it might result in parsing errors, forcing you to delete the `properties.rsp` file and to relaunch.
~~Also, by default RadioRecorder uses stereo, you can't change it for the moment (github.1.0) except if you modify the code.~~
You can choose the number of channels since version github.1.1, `1` for mono and `2` for stereo.
- Choose a language between English `EN` and French `FR`
- Enter a number of days where recordings past them will be deleted
- Choose the length of a sing record (session) in minutes
- Enter the sample rate, using a high one will increase quality (*depending on the sound card*) but will also increase size on disk
- Enter the bit depth, using a high one will increase quality (*depending on the sound card*) but will also increase size on disk
- Choose if you want to used signed value 
- Choose if you want to use the big-endian mode 
### Commands
- `help` displays all the commands available
- `exit` quit RadioRecorder
- `start` start recording
- `stop` stop the recording
- `stope` stop the recording and exit
- `clean` delete all records in the data folder (and every file in it to be fair)
- `conf` start the configuration assistant

