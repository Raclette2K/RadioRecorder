/*
======================================================================
GlobalVars.java - Contains global variables (maybe not the right thing
                - to do)
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

import com.raclette_it.radiorecorder.Language.LanguageList;

import javax.sound.sampled.AudioFileFormat;
import java.text.SimpleDateFormat;

public class GlobalVars {


     // Length in minute of a record (or session)
    public static int S_SessionRecord = 1;


    // How long, in days, the app keeps the records
    public static int S_TimeKeepRecord = 40;


    // Records audio format [AU]
    public static AudioFileFormat.Type S_AudioFormat = AudioFileFormat.Type.AU;
    // Sample Rate [16kHz]
    public static float SA_SampleHz = 16000;
    // Bit depth [8bits]
    public static int SA_SampleInBits = 8;
    // Signed mode [true]
    public static boolean SA_Signed = true;
    // Big endian mode [true]
    public static boolean SA_BigEndian = true;

    // Version of RadioRecord
    public static String VERSION = "github.1.0";

    // Date format [24h is useful to prevent problems with the data cleaning]
    public static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy;HH-mm-ss");

    // Language of the app
    public static LanguageList language = LanguageList.EN;
}
