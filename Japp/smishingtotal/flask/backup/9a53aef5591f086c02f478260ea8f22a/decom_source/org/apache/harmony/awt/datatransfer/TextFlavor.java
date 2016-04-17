// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.harmony.awt.datatransfer;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.SystemFlavorMap;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

public class TextFlavor
{

    public TextFlavor()
    {
    }

    public static void addCharsetClasses(SystemFlavorMap systemflavormap, String s, String s1, String s2)
    {
        int i = 0;
        do
        {
            if(i >= charsetTextClasses.length)
                return;
            String s3 = (new StringBuilder("text/")).append(s1).toString();
            String s4 = (new StringBuilder(";class=\"")).append(charsetTextClasses[i].getName()).append("\"").append(";charset=\"").append(s2).append("\"").toString();
            DataFlavor dataflavor = new DataFlavor((new StringBuilder(String.valueOf(s3))).append(s4).toString(), s3);
            systemflavormap.addFlavorForUnencodedNative(s, dataflavor);
            systemflavormap.addUnencodedNativeForFlavor(dataflavor, s);
            i++;
        } while(true);
    }

    public static void addUnicodeClasses(SystemFlavorMap systemflavormap, String s, String s1)
    {
        int i = 0;
        do
        {
            if(i >= unicodeTextClasses.length)
                return;
            String s2 = (new StringBuilder("text/")).append(s1).toString();
            String s3 = (new StringBuilder(";class=\"")).append(unicodeTextClasses[i].getName()).append("\"").toString();
            DataFlavor dataflavor = new DataFlavor((new StringBuilder(String.valueOf(s2))).append(s3).toString(), s2);
            systemflavormap.addFlavorForUnencodedNative(s, dataflavor);
            systemflavormap.addUnencodedNativeForFlavor(dataflavor, s);
            i++;
        } while(true);
    }

    public static final Class charsetTextClasses[] = {
        java/io/InputStream, java/nio/ByteBuffer, [B
    };
    public static final Class unicodeTextClasses[] = {
        java/lang/String, java/io/Reader, java/nio/CharBuffer, [C
    };

}
