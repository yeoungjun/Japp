// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.entity.mime;


public final class HttpMultipartMode extends Enum
{

    private HttpMultipartMode(String s, int i)
    {
        super(s, i);
    }

    public static HttpMultipartMode valueOf(String s)
    {
        return (HttpMultipartMode)Enum.valueOf(org/apache/http/entity/mime/HttpMultipartMode, s);
    }

    public static HttpMultipartMode[] values()
    {
        return (HttpMultipartMode[])$VALUES.clone();
    }

    private static final HttpMultipartMode $VALUES[];
    public static final HttpMultipartMode BROWSER_COMPATIBLE;
    public static final HttpMultipartMode RFC6532;
    public static final HttpMultipartMode STRICT;

    static 
    {
        STRICT = new HttpMultipartMode("STRICT", 0);
        BROWSER_COMPATIBLE = new HttpMultipartMode("BROWSER_COMPATIBLE", 1);
        RFC6532 = new HttpMultipartMode("RFC6532", 2);
        HttpMultipartMode ahttpmultipartmode[] = new HttpMultipartMode[3];
        ahttpmultipartmode[0] = STRICT;
        ahttpmultipartmode[1] = BROWSER_COMPATIBLE;
        ahttpmultipartmode[2] = RFC6532;
        $VALUES = ahttpmultipartmode;
    }
}
