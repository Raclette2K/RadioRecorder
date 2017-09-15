/*
======================================================================
RecordDirectSound.java - Get the sound input into a stream
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

import com.raclette_it.radiorecorder.System.AudioLineException;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;
import java.util.Timer;
import java.util.TimerTask;

public class RecordDirectSound {

    // Length of a record in seconds
    private int S_TimeToRecord;

    // Stop the timer when set to true
    private boolean S_StopRecord = false;

    // Sample rate
    float S_sampleRate = 16000;
    // Bit depth
    int S_sampleSizeInBits = 8;
    // Mono = 1; Stereo = 2
    int S_channels = 1;
    // Signed mode
    boolean S_signed = true;
    // Big endian mode
    boolean S_bigEndian = true;


    // Constructor
    public RecordDirectSound(int TimeToRecord, boolean write, AudioFileFormat.Type audioFileFormat,
                             float sampleRate, int sampleSizeInBits, int channels, boolean signed, boolean bigEndian, String fileName) throws AudioLineException {

        S_sampleRate = sampleRate;
        S_bigEndian = bigEndian;
        S_channels = channels;
        S_sampleSizeInBits = sampleSizeInBits;
        S_signed = signed;

        // Convert time from minutes to seconds
        S_TimeToRecord = TimeToRecord*60;


        // Call setup() to get the AudioLine and to define recording parameters
        TargetDataLine line = setup(S_sampleRate,S_sampleSizeInBits,S_channels,S_signed,S_bigEndian);

        // Call readStream() and get the audio stream
        ByteArrayOutputStream out = readStream(line);

        // If we want the stream to be saved in a file
        if(write) {
            // Calls WriteSound()
            new WriteSound(audioFileFormat, out, fileName);

        }
        // Close the line
        line.close();


    }


    // Get the line input and check if available
    private TargetDataLine setup(float sampleRate, int sampleSizeInBits, int channels, boolean signed, boolean bigEndian) throws AudioLineException {

        // Define the audio format
        AudioFormat audioFormat =  new AudioFormat(sampleRate,
                sampleSizeInBits, channels, signed, bigEndian);

        TargetDataLine line;

        DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);

        // Check if the input line supports the audio format
        if (!AudioSystem.isLineSupported(info)) {
            System.err.println("Audio format not supported !");
            System.err.println("Please check your configuration");
            throw new AudioLineException("Audio format not supported");
        }

        // Check if the input line is available
        try {
            // If available, opens it
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(audioFormat);
            return line;

        } catch (LineUnavailableException ex) {
            //If unavailable throw an error
            System.err.println("AudioLine is unavailable !");
            System.err.println("Please check if another process is using the line !");
            ex.printStackTrace();
            return null;
        }

    }


    // Get the audio into a stream while S_StopRecord = false
    private ByteArrayOutputStream readStream(TargetDataLine line)
    {
        // Start the time for S_TimeToRecord seconds
        sessionTimer();

        // The audio stream where data are put into
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        // Number of bytes read
        int numByteRead;

        // Store data of the stream
        byte[] data = new byte[line.getBufferSize() /5];

        // Launch the recording
        line.start();

        // Read the input and write it in the audio stream
        while(!S_StopRecord)
        {
            numByteRead = line.read(data,0,data.length);
            out.write(data, 0, numByteRead);
        }

        return out;
    }

    // Timer that stops the record after S_TimeToRecord seconds
    private void sessionTimer()
    {
        S_StopRecord = false;

        final Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Stop the record
                S_StopRecord = true;
                // Reset the timer
                timer.cancel();
                timer.purge();
            }
        }, S_TimeToRecord * 1000);

    }

}
