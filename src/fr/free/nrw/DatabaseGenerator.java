package fr.free.nrw;

import java.io.FileInputStream;
import java.io.File;
import com.google.typography.font.sfntly.table.core.CMap;
import com.google.typography.font.sfntly.table.core.CMapTable;
import com.google.typography.font.sfntly.Tag;
import com.google.typography.font.sfntly.Font;
import com.google.typography.font.sfntly.FontFactory;

/**
 * Generate a database of mappings {character→suitable fonts for that character}
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
public class DatabaseGenerator {

    /**
     * Directory where font files are stored.
     */
    private String fontsDirectory;

    /**
     * Generate database.
     * TODO: Instead of outputing to console, generate efficient data structure.
     */
    public void generate() throws Exception {
        String[] fonts = new File(fontsDirectory).list();
        // Test all fonts for all characters
        for (char c=0; c<65535; c++) {
            System.out.print(c + ": ");
            for (int j=0; j<fonts.length; j++) {
                if(fontHasCharacter(fonts[j], c)) {
                    System.out.print(fonts[j] + " ");
                }
            }
            System.out.print("\n");
        }
    }

    /**
     * Check whether the given font has a particular character.
     */
    private boolean fontHasCharacter(String fontFilename, char charId) throws Exception {
        // TODO: cache cmap tables instead of reloading every time.
        Font[] srcFontarray = FontFactory.getInstance().loadFonts(new FileInputStream(
		fontsDirectory + System.getProperty("file.separator") + fontFilename));
        Font font = srcFontarray[0];
        CMapTable cmapTable = font.getTable(Tag.cmap);
        // Use the bigger cmap table if available.
        CMap cmap = cmapTable.cmap(Font.PlatformId.Windows.value(), Font.WindowsEncodingId.UnicodeUCS4.value());
        if (cmap == null) 
            cmap = cmapTable.cmap(Font.PlatformId.Windows.value(), Font.WindowsEncodingId.UnicodeUCS2.value());
 
        if (cmap.glyphId(charId) != 0)
            return true;
        return false;
    }

    /**
     * Constructor.
     */
    public DatabaseGenerator(String fontsDirectory) {
        this.fontsDirectory = fontsDirectory;
    }

    /**
     * Launch database generation from system.
     */
    public static void main(String[] args) {
        try {
            DatabaseGenerator generator = new DatabaseGenerator("freely-distributable-fonts");
            generator.generate();
        }
        catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
