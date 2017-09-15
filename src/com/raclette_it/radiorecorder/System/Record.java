/*
======================================================================
Record.java - Handles the recording
Copyright (c) 2017 Pierre Boisselier. All rights reserved.
**********************************************************************
This file is part of RadioRecorder.
RadioRecorder is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.
RadioRecorder is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
You should have received a copy of the GNU General Public License
along with RadioRecorder.  If not, see <http://www.gnu.org/licenses/>.
======================================================================
 */
package com.raclette_it.radiorecorder.System;

import com.raclette_it.radiorecorder.API.RecordDirectSound;
import com.raclette_it.radiorecorder.Language.LanguageLoader;
import com.raclette_it.radiorecorder.Main;

import javax.sound.sampled.AudioFileFormat;
import java.io.File;
import java.util.Date;


public class Record implements Runnable{

    // To know if the recording is active
    public static boolean C_IsRecordRunning = false;

    // To kill the recording loop
    static boolean C_StopInfiniteLoop = false;
    // States if the app should exit at the end of the current record
    static boolean C_ExitAtStop = false;

    // Get values from the GlobalVars [the configuration]
    private final int sessionRecord = GlobalVars.S_SessionRecord;
    private final AudioFileFormat.Type audioFormat = GlobalVars.S_AudioFormat;
    private final float sampleHz = GlobalVars.SA_SampleHz;
    private final int sampleInBits =  GlobalVars.SA_SampleInBits;
    private final boolean  signed = GlobalVars.SA_Signed;
    private final boolean  bigEndian = GlobalVars.SA_BigEndian;

    // Constructor
    public Record()
    {
        // Ensure that we don't block the main loop
        C_StopInfiniteLoop = false;
        // States that the recording is active
        C_IsRecordRunning = true;
    }

    public void run()
    {
        // Represents the current record number
        int i = 0;
        // The main loop
        while(!C_StopInfiniteLoop)
        {
            // Add 1 to each iteration
            i++;
            // Inform that the session i is launched
            System.out.println(LanguageLoader.getString("launchSession")+i);

            try{
                // Calls the RecordDirectSound class with the configuration
                new RecordDirectSound(this.sessionRecord ,true, this.audioFormat,this.sampleHz, this.sampleInBits,1,this.signed,this.bigEndian, FileNameGenerator());
            }catch (AudioLineException e){LanguageLoader.getString("E_noAudioIn");System.exit(-1);}
            // Inform the user that the current session i is over
            System.out.println("Session "+i+LanguageLoader.getString("sessionOver"));
            // Call the checkForLimitDate() to make sure we delete old records
            checkForLimitDate();
        }
        // When the recording is over
        C_IsRecordRunning = false;
        // Tells how many records were made
        System.out.println(LanguageLoader.getString("recordStopped")+i+" sessions !");
        // If the app must exit after recording
        if(C_ExitAtStop)
            System.out.println(LanguageLoader.getString("exitConfirm"));Main.ExitApp();// Inform and exit
    }

    // Generate the file name as the current date
    private String FileNameGenerator()
    {
        Date date = new Date();
        return "data"+ File.separator+GlobalVars.formatter.format(date);
    }
    // Run the IO.LimitData that checks for old records that need to be deleted
    private void checkForLimitDate()
    {
        new Thread(new Runnable() {
            public void run() {
                IO.LimitData();
            }
        }).start();
    }
}
