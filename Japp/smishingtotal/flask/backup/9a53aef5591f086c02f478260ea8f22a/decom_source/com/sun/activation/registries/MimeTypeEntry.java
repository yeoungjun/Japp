// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.activation.registries;


public class MimeTypeEntry
{

    public MimeTypeEntry(String s, String s1)
    {
        type = s;
        extension = s1;
    }

    public String getFileExtension()
    {
        return extension;
    }

    public String getMIMEType()
    {
        return type;
    }

    public String toString()
    {
        return (new StringBuilder("MIMETypeEntry: ")).append(type).append(", ").append(extension).toString();
    }

    private String extension;
    private String type;
}
