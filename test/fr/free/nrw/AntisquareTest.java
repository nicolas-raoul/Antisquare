package fr.free.nrw;

import junit.framework.TestCase;

import java.util.Date;
import java.util.Random;

/**
 * Unit tests for Antisquare.
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
public class AntisquareTest extends TestCase {

    /**
     * Get suitable font for various characters.
     */
    public final void testVariousCharacters() {
        assertEquals(new String[]{}, Antisquare.getSuitableFonts('\u0000'));
        assertEquals(new String[]{"KhmerOS.ttf","DroidSans-Regular.ttf","OpenSans-Regular.ttf"}, Antisquare.getSuitableFonts('t'));
        assertEquals(new String[]{"DroidSans-Regular.ttf","OpenSans-Regular.ttf"}, Antisquare.getSuitableFonts('Ĕ'));
        assertEquals(new String[]{"KhmerOS.ttf","DroidSans-Regular.ttf","OpenSans-Regular.ttf"}, Antisquare.getSuitableFonts('œ'));
    }
    
    /**
     * Get suitable font for all characters.
     */
    public final void testAllCharacters() {
        for (char character=0; character<65535; character++) {
            Antisquare.getSuitableFonts(character);
        }
    }
    
    /**
     * Get suitable font for various strings.
     */
    public final void testVariousStrings() {
        assertEquals(new String[]{}, Antisquare.getSuitableFonts(""));
        assertEquals(new String[]{"OpenSans-Regular.ttf"}, Antisquare.getSuitableFonts('\u0000' + "tĔœȚ"));
    }
    
    /**
     * Get suitable font for long string.
     */
    public final void testLongString() {
        int LENGTH = 10000000;
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();
        for (int i=0; i<LENGTH; i++) {
            buffer.append((char)random.nextInt(65535));
        }
        Antisquare.getSuitableFonts(buffer.toString());
    }
    
    /**
     * Performance test.
     */
    public static void main(String[] args) {
        int NB = 100000000;
        char[] characters = new char[NB];
        Random random = new Random();
        for (int i=0; i<NB; i++) {
            characters[i] = (char)random.nextInt(65535);
        }
        System.out.println(new Date());
        for (char character : characters) {
            Antisquare.getSuitableFonts(character);
        }
        for (char character : characters) {
            Antisquare.getSuitableFonts((char)(65535 - character));
        }
        System.out.println(new Date());
        // 2.10^8 in 8 seconds on my laptop.
    }
}
