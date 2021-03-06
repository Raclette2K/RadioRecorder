/*
======================================================================
AudioLineException.java - An exception for the audio line input/output
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

public class AudioLineException extends Exception {

    public AudioLineException() { super(); }
    public AudioLineException(String msg) { super(msg); }
    public AudioLineException(String msg, Throwable cause) { super(msg, cause); }
    public AudioLineException(Throwable cause) { super(cause); }



}
