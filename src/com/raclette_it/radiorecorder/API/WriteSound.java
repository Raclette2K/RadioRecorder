/*
======================================================================
WriteSound.java - Write the audio file to the system
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
package com.raclette_it.radiorecorder.API;

import javax.sound.sampled.AudioFileFormat;
import java.io.*;


class WriteSound {

    // Audio format
    private AudioFileFormat.Type S_AudioFormat;
    // Raw audio stream
    private ByteArrayOutputStream S_rawAudio;

    // Constructor
    public WriteSound(AudioFileFormat.Type audioFileFormat, ByteArrayOutputStream toWrite, String fileName)
    {
        // Set the audio format
        S_AudioFormat = audioFileFormat;
        // Get the audio stream
        S_rawAudio = toWrite;
        // Call the write method with the name file
        WriteFile(fileName);
    }

    // Write the audio stream into a file
    private void WriteFile(String fileName){

        byte[] bytes = S_rawAudio.toByteArray();
        File out = new File(fileName+"."+S_AudioFormat);
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(out);
            outputStream.write(bytes);
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
