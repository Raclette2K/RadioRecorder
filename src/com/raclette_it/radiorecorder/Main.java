/*
============================ DISCLAIMER ==============================
This project is not a "definitive" product and will never be one.
It was created out of boredom so don't expect too much of it.
In any case, as the GPL says, this project/software is distributed
without any warranty and I'm not liable for anything that can happen
with the use of it.
Be also aware that the main purpose of this project was to train me
in Java, so there will be quite a lot of optimization issues and bug
handling not really done.
======================================================================


======================================================================
Main.java - Entry point of the program.
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

package com.raclette_it.radiorecorder;

import com.raclette_it.radiorecorder.Language.LanguageLoader;
import com.raclette_it.radiorecorder.System.Commands;
import com.raclette_it.radiorecorder.System.GlobalVars;
import com.raclette_it.radiorecorder.System.IO;
import com.raclette_it.radiorecorder.System.Record;
import com.raclette_it.radiorecorder.UI.Configure;

import java.util.Scanner;

public class Main {

    // Manages the infinite loop for user input
    private static boolean appRunning = true;

    // Main class and entry point
    public static void main(String[] args)
    {
        // Checking the configuration file
        IO.ConfigurationCheck();
        // Checking if the data folder exists
        IO.DataFolderCheck();
        // Loading the configuration from the file
        Configure.loadConfiguration();
        // Run through all the records and remove those that are past the date limit
        IO.LimitData();


        // ---- UI : Home Screen ----
        System.out.println("\n\n------ RADIO RECORDER ------");
        System.out.println("Current Version : " + GlobalVars.VERSION);
        System.out.println("----------------------------");
        System.out.println(LanguageLoader.getString("copyrightNotice"));
        System.out.println("----------------------------");
        System.out.println(LanguageLoader.getString("helpNotice"));
        System.out.println("----------------------------");

        // Checking if there are arguments when launched
        // If "auto" is mentioned as argument, send the start command which launch the record
        if(args != null)
            if(args.length > 0)
                if (args[0].equalsIgnoreCase("auto"))
                    new Commands("start");

        // Call the waitForInput function that basically wait for a user input
        waitForInput();

    }
    // Simple loop that allows the user to execute commands
    private static void waitForInput()
    {
        // Infinite loop until the exit command is executed
        Scanner sc = new Scanner(System.in);
        while(appRunning) {
            // Read input
            String cmd = sc.nextLine();
            sc.reset();
            // Send the input to the command resolver
            new Commands(cmd);
        }
    }
    // Start a new thread that handles the recording
    public static void StartRecord()
    {
        // Creating a thread
        Thread t = new Thread(new Record());
        // Starting it
        t.start();
    }
    // Does it need explanation ?
    public static void ExitApp()
    {
        // Stopping the infinite loop that handles user input
        appRunning = false;
        // Kindly tell the app to close.
        System.exit(0);
    }
}
