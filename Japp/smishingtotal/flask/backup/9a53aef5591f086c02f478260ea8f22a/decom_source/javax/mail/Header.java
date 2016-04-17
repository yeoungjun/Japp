// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail;


public class Header
{

    public Header(String s, String s1)
    {
        name = s;
        value = s1;
    }

    public String getName()
    {
        return name;
    }

    public String getValue()
    {
        return value;
    }

    protected String name;
    protected String value;
}
