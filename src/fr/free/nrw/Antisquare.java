package fr.free.nrw;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;

/**
 * Returns a list of suitable fonts for a given character or string.
 *
 * Copyright (c) 2012 Nicolas Raoul
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see http://www.gnu.org/licenses/
 */
public class Antisquare {
    
    /**
     * List of suitable fonts for a given character.
     * Data has to be generated beforehand, see DatabaseGenerator.
     */
    public String getSuitableFonts(char character) {
        int zone = Arrays.binarySearch(AntisquareData.zones, character);
        if (zone >= 0) {
            return AntisquareData.fontsSets[AntisquareData.mappings[zone]];
        }
        else {
            // character is not the first of its zone, so look into the previous zone
            // binarySearch helpfully tells us where the character would be inserted.
            // See http://docs.oracle.com/javase/7/docs/api/java/util/Arrays.html#binarySearch(char[], char)
            zone = -zone -1 -1; // Because binarySearch returns (-(insertion point) - 1)
            return AntisquareData.fontsSets[AntisquareData.mappings[zone]];
        }
    }
    
    /**
     * List of suitable fonts for a given string.
     * Data has to be generated beforehand, see DatabaseGenerator.
     * If no font is totally suitable, the "most" suitable is returned.
     */
    public String getSuitableFonts(String string) {
        return "TODO";
    }
    
    /**
     * Launch search from system.
     */
    public static void main(String[] args) {
        Antisquare antisquare = new Antisquare();
        System.out.println("suitable:" + antisquare.getSuitableFonts('\u0000')); // 
        System.out.println("suitable:" + antisquare.getSuitableFonts('t')); // KhmerOS.ttf DroidSans-Regular.ttf OpenSans-Regular.ttf
        System.out.println("suitable:" + antisquare.getSuitableFonts('Ĕ')); // DroidSans-Regular.ttf OpenSans-Regular.ttf
        System.out.println("suitable:" + antisquare.getSuitableFonts('œ')); // KhmerOS.ttf DroidSans-Regular.ttf OpenSans-Regular.ttf
        
        // 2.10^8 in 8 seconds on my laptop.
        int NB = 100000000;
        char[] characters = new char[NB];
        Random random = new Random();
        for (int i=0; i<NB; i++) {
            characters[i] = (char)random.nextInt(65535);
        }
        System.out.println(new Date());
        for (char character : characters) {
            antisquare.getSuitableFonts(character);
        }
        for (char character : characters) {
            antisquare.getSuitableFonts((char)(65535 - character));
        }
        System.out.println(new Date());
    }
}