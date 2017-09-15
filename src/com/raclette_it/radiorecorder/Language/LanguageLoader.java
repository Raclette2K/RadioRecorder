/*
======================================================================
LanguageLoader.java - Get the string from the lang file
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
package com.raclette_it.radiorecorder.Language;

import org.xml.sax.SAXException;
import com.raclette_it.radiorecorder.System.GlobalVars;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import java.text.Normalizer;

import java.io.IOException;

public class LanguageLoader {

    // Get the string from the xml lang file
    public static String getString( String item) {
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc;

            // Get the right xml file
            if (GlobalVars.language == LanguageList.FR)
                doc = docBuilder.parse("lang.fr.xml");
            else
                doc = docBuilder.parse("lang.en.xml");

            doc.getDocumentElement().normalize();


            NodeList nodeList = doc.getElementsByTagName("*");
            String toReturn = "**Error while loading "+item+" text !**";
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    if(node.getNodeName().equalsIgnoreCase(item)) {
                        toReturn = node.getTextContent();
                    }
                }
            }
            // If the system is windows based, remove accents
            if(System.getProperty("os.name").toLowerCase().indexOf("win")>0)
                return stripAccents(toReturn);
            else
                return toReturn;


        }catch (SAXException e){e.printStackTrace();}
        catch(ParserConfigurationException e){e.printStackTrace();}
        catch(IOException e){e.printStackTrace();}

        return "**Error while loading language information !**";
    }

    // Detects what language the system is configured as
    public static void firstLaunchAutoLanguage()
    {
        if(System.getProperty("user.language").equalsIgnoreCase("fr"))
            GlobalVars.language = LanguageList.FR;
        else
            GlobalVars.language = LanguageList.EN;
    }

    // Preventing windows based system from displaying accents
    private static String stripAccents(String s)
    {
        s = Normalizer.normalize(s, java.text.Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }

}
