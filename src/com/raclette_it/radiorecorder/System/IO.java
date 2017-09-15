/*
======================================================================
IO.java - Does some IO things
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
import com.raclette_it.radiorecorder.UI.Configure;

import java.io.File;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;


@SuppressWarnings("Since15")
public class IO {


    private static File dataFolder = new File("data");

    // Run through all the records and delete those that are out of date
    public static void LimitData() {

        // Get the current date
        Date dateInstance = new Date();
        // Create a calendar
        Calendar cal = Calendar.getInstance();
        // Set the calendar to the current date
        cal.setTime(dateInstance);
        // Remove X days from the calendar
        cal.add(Calendar.DATE, -GlobalVars.S_TimeKeepRecord);
        // Create a date that is X days from now
        Date dateOld = cal.getTime();

        // List all files (records) in the data folders
        File[] listOfFiles = dataFolder.listFiles();

        Date dateFile;

        // Iterate through all the files
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                try {
                    // Parse the name of the record into a Date
                    dateFile = GlobalVars.formatter.parse(listOfFiles[i].getName());
                    // Compare the parsed date to the date from X days
                    if(dateFile.before(dateOld) || dateFile.equals(dateOld))
                    {
                        // Delete if it's out of the date range
                        if(!listOfFiles[i].delete())
                            System.err.println("Can't delete "+listOfFiles[i].getName());
                    }
                } catch (ParseException e) {
                    LanguageLoader.getString("E_parseError");
                }
            }
        }

    }

    // Check if the data folder exists
    public static void DataFolderCheck() {
        // If the data folder doesn't exist
        if (!dataFolder.exists()) {
            // Inform the user it'll be created
            System.out.println(LanguageLoader.getString("E_dataFolderNotExist"));
            // Create the folder
            if (dataFolder.mkdir()) {
                // Tell if it went well
                System.out.println(LanguageLoader.getString("I_dataFolderCreated"));
            } else {
                // Went bad, tell the user to check its permissions
                System.err.println(LanguageLoader.getString("E_cantCreateDataFolder"));
                System.out.println(LanguageLoader.getString("I_checkFolderPermissions"));
            }
        }
    }

    // Check if the configuration file exists
    public static void ConfigurationCheck() {
        // Get the file's path
        File rspProperties = new File(Configure.confFileName);
        // If it doesn't exists
        if (!rspProperties.exists()) {
            // Create it and start the configurator for the user
            System.out.println(LanguageLoader.getString("I_noConfigurationDetected")+"\n");
            LanguageLoader.firstLaunchAutoLanguage();
            new Configure();
        }
    }

    // Clean the data folders from EVERYTHING in it
    public static void CleanDataFolder(){

        // List all the files in the data folder
        File[] listOfFiles = dataFolder.listFiles();

        // Iterate through each file and delete it if it's a file.
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                if(!listOfFiles[i].delete())
                    System.err.println("Can't delete "+listOfFiles[i].getName());
            }
        }
    }

}
