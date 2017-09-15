/*
======================================================================
Commands.java - Read user input and execute appropriate commands
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

import com.raclette_it.radiorecorder.Language.LanguageLoader;
import com.raclette_it.radiorecorder.Main;
import com.raclette_it.radiorecorder.UI.Configure;

import java.io.RandomAccessFile;
import java.util.Scanner;


public class Commands {


    // Constructor that receives a string
    public Commands(String cmd)
    {
        // Compare the received string to known commands

        if(cmd.equalsIgnoreCase("help"))
            helpCmd();
        else if(cmd.equalsIgnoreCase("exit"))
            exitCmd();
        else if(cmd.equalsIgnoreCase("start"))
            startCmd();
        else if(cmd.equalsIgnoreCase("stop"))
            stopCmd(false);
        else if(cmd.equalsIgnoreCase("conf"))
            confCmd();
        else if(cmd.equalsIgnoreCase("testfile"))
            generateTestFile();
        else if(cmd.equalsIgnoreCase("stope"))
            stopCmd(true);
        else if(cmd.equalsIgnoreCase("clean"))
            cleanCmd();
        else
            unknownCmd();
    }

    // UI: List all the commands available
    private void helpCmd()
    {
        System.out.println(LanguageLoader.getString("CMD_title")+"\n");
        System.out.println(LanguageLoader.getString("CMD_help"));
        System.out.println(LanguageLoader.getString("CMD_exit"));
        System.out.println(LanguageLoader.getString("CMD_start"));
        System.out.println(LanguageLoader.getString("CMD_stop"));
        System.out.println(LanguageLoader.getString("CMD_stope"));
        System.out.println(LanguageLoader.getString("CMD_clean"));
        System.out.println(LanguageLoader.getString("conf"));
        System.out.println("--------------------------\n");
    }

    // Start the recording
    private void startCmd()
    {
        // Start the recording
        Main.StartRecord();
    }

    // Stop the recording at the end of the session
    private void stopCmd(boolean exit)
    {
        if (Record.C_IsRecordRunning) {
            System.out.println(LanguageLoader.getString("stopConfirm"));
            // Check the will of the user
            if (confirmCmd()) {
                // Stop the recording by preventing the loop to continue
                Record.C_StopInfiniteLoop = true;
                if(exit) {
                    Record.C_StopInfiniteLoop = true;
                    // Inform the user that the recording stops after the end of the current session and the app exit
                    System.out.println(LanguageLoader.getString("stopeConfirmSuccess"));
                }else {
                    // Inform the user that the recording stops after the end of the current session
                    System.out.println(LanguageLoader.getString("stop"));
                    System.out.println(LanguageLoader.getString("stop2"));
                }
            } else
                System.out.println(LanguageLoader.getString("stopConfirmFail")); // Inform the user that he didn't accept
        }
        else
            System.out.println(LanguageLoader.getString("noRecordRunning")); // Inform the user that the recording is running
    }

    // Exit the app
    private void exitCmd()
    {
        // If the recording is going on tell the user
        if(Record.C_IsRecordRunning)
            System.out.println(LanguageLoader.getString("sessionRunning"));
        // Inform the user that he's gonna quit
        System.out.println(LanguageLoader.getString("exit"));
        // Confirm that the user wants to
        if(confirmCmd())
        {
            // Inform the user the app is going to exit
            System.out.println(LanguageLoader.getString("exitConfirm"));
            // Close the app
            Main.ExitApp();
        }

    }

    // UI: Start the configurator
    private void confCmd()
    {
        new Configure();
    }

    // UI: Informs the user that the command is unknown
    private void unknownCmd()
    {
        System.out.println(LanguageLoader.getString("unknown"));
    }

    // Generates test files
    private void generateTestFile() {
        int i = 10;
        do {
            try {
                RandomAccessFile f = new RandomAccessFile("data/01-01-2017;05-"+i+"-30.AU", "rw");
                f.setLength(1024 * 1024);
                i++;
            } catch (Exception e) {
                System.err.println(e);
            }
        } while (i < 60);
    }

    // Check the will of the user before cleaning the data folder
    private void cleanCmd()
    {
        // If the recording is going on tell the user
        if(Record.C_IsRecordRunning)
            System.out.println(LanguageLoader.getString("sessionRunning"));

        // Make sure the user wants to delete all the records
        System.out.println(LanguageLoader.getString("cleanConfirm"));
        if(confirmCmd())
        {
            // Tell that the job is going to be done
            System.out.println(LanguageLoader.getString("clean"));
            // Call the cleaning method
            IO.CleanDataFolder();
            // Tell that the job is done
            System.out.println(LanguageLoader.getString("clean2"));
        }
        else
            System.out.println(LanguageLoader.getString("cleanAborted")); // The user has aborted

    }

    // Doing a Y/N confirmation from the user
    private boolean confirmCmd()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println(LanguageLoader.getString("TypeYN"));
        String s = sc.nextLine();
        while(true)
        {
            if(s.equalsIgnoreCase("y"))
                return true;
            else if(s.equalsIgnoreCase("n"))
                return false;
            else
                System.out.println(LanguageLoader.getString("TypeYN"));
            sc.reset();
            s = sc.nextLine();
        }

    }

}

