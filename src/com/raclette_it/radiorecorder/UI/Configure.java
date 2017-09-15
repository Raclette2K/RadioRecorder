/*
======================================================================
Configure.java - Handles the configuration stored in properties.rsp
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
package com.raclette_it.radiorecorder.UI;

import com.raclette_it.radiorecorder.Language.LanguageList;
import com.raclette_it.radiorecorder.Language.LanguageLoader;
import com.raclette_it.radiorecorder.Main;
import com.raclette_it.radiorecorder.System.GlobalVars;

import java.io.*;
import java.util.*;

public class Configure {

    // How long records are kept
    private String keepData;
    // The duration of a record
    private String sessionDuration;
    // The sampling frequency
    private String sampleHz;
    // The sampling bit depth
    private String sampleBits;
    // Signed mode
    private String signed;
    // Big Endian mode
    private String bigEndian;
    // Language
    private String language;

    public static String confFileName = "properties.rsp";
    // Constructor
    public Configure()
    {
        // Start the configuration UI
        configuration();
    }

    private void configuration()
    {

        Scanner sc = new Scanner(System.in);
        System.out.println(LanguageLoader.getString("A_title"));
        System.out.println(LanguageLoader.getString("language"));
        language = sc.nextLine();
        System.out.println(LanguageLoader.getString("dataDuration"));
        keepData = sc.nextLine();
        System.out.println(LanguageLoader.getString("sessionDuration"));
        sessionDuration = sc.nextLine();
        System.out.println(LanguageLoader.getString("advancedConfiguration"));
        System.out.println(LanguageLoader.getString("sampleRate"));
        sampleHz = sc.nextLine();
        System.out.println(LanguageLoader.getString("audioBitDepth"));
        sampleBits = sc.nextLine();
        System.out.println(LanguageLoader.getString("signed"));
        signed = sc.nextLine();
        System.out.println(LanguageLoader.getString("bigEndian"));
        bigEndian = sc.nextLine();

        // Write the values in the configuration file
        createFile();

    }

    // Check if the file exists and creates it or just overwrite it
    private void createFile()
    {
        File f = new File(confFileName);
        try {
            if (!f.exists()) {
                f.createNewFile();
                saveInFile();
            }
            else
            {
                if(f.delete()) {
                    createFile();
                }
                else{
                    System.out.println(LanguageLoader.getString("E_cantDeleteOldConf"));
                    System.exit(-1);
                }

            }
        }catch(IOException e) {
            System.out.println(LanguageLoader.getString("E_cantCreateConf"));
        }
        // "Restart" the app by recalling the main to get the configuration to reload
        Main.main(null);
    }

    // Save the configuration in the newly created file
    private void saveInFile()
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(confFileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(createHashMap()); // Write down the hashmap from the createHashMap method that stores the configuration
            oos.close();
            fos.close();
            System.out.println("\n"+LanguageLoader.getString("I_configurationSaved"));
        }catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    // Create a hashmap containing the configuration
    private HashMap<String, String> createHashMap()
    {

        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("language", language);
        hm.put("keepData", keepData);
        hm.put("sessionDuration",sessionDuration);
        hm.put("sampleHz", sampleHz);
        hm.put("sampleBits", sampleBits);
        hm.put("signed",signed);
        hm.put("bigEndian",bigEndian);
        GlobalVars.S_SessionRecord = Integer.parseInt(hm.get("sessionDuration"));
        GlobalVars.S_TimeKeepRecord = Integer.parseInt(hm.get("keepData"));
        GlobalVars.SA_SampleHz = Float.parseFloat(sampleHz);
        GlobalVars.SA_SampleInBits = Integer.parseInt(sampleBits);
        GlobalVars.SA_Signed = Boolean.parseBoolean(signed);
        GlobalVars.SA_Signed = Boolean.parseBoolean(bigEndian);
        if(hm.get("language").equalsIgnoreCase("FR"))
            GlobalVars.language = LanguageList.FR;
        else
            GlobalVars.language = LanguageList.EN;
        return hm;
    }

    // Load the configuration from the file
    public static void loadConfiguration()
    {
        HashMap<Integer, String> map = null;
        try
        {
            FileInputStream fis = new FileInputStream(confFileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            map = (HashMap) ois.readObject();
            ois.close();
            fis.close();
        }catch(IOException ioe)
        {
            System.out.println(LanguageLoader.getString("E_cantLoadConf"));
            return;
        }catch(ClassNotFoundException c)
        {
            System.out.println("Class not found !");
            return;
        }
        System.out.println(LanguageLoader.getString("I_loadingConfiguration"));
        Set set = map.entrySet();
        Iterator iterator = set.iterator();
        HashMap<String, String> hashMap = new HashMap<String, String>();
        while(iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterator.next();

            String k = mentry.getKey().toString();
            String v = mentry.getValue().toString();
            hashMap.put(k, v);
        }
        System.out.println(LanguageLoader.getString("I_configurationLoaded"));
        GlobalVars.S_SessionRecord = Integer.parseInt(hashMap.get("sessionDuration"));
        GlobalVars.S_TimeKeepRecord = Integer.parseInt(hashMap.get("keepData"));
        GlobalVars.SA_SampleHz = Float.parseFloat(hashMap.get("sampleHz"));
        GlobalVars.SA_SampleInBits = Integer.parseInt(hashMap.get("sampleBits"));
        GlobalVars.SA_Signed = Boolean.parseBoolean(hashMap.get("signed"));
        GlobalVars.SA_Signed = Boolean.parseBoolean(hashMap.get("bigEndian"));
        if(hashMap.get("language").equalsIgnoreCase("FR"))
            GlobalVars.language = LanguageList.FR;
        else
            GlobalVars.language = LanguageList.EN;
    }

}
