#!/bin/sh
# Generates a Java class that can tell which font(s) to use for any given character
#
java -cp bin:lib/sfntly_cvs20120313.jar fr.free.nrw.DatabaseGenerator > output.log
echo "Generated: gen/fr/free/nrw/AntisquareData.java"
