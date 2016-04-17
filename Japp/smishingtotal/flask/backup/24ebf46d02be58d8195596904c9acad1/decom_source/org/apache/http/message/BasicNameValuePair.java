// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.message;

import java.io.Serializable;
import org.apache.http.NameValuePair;
import org.apache.http.util.Args;
import org.apache.http.util.LangUtils;

public class BasicNameValuePair
    implements NameValuePair, Cloneable, Serializable
{

    public BasicNameValuePair(String s, String s1)
    {
        name = (String)Args.notNull(s, "Name");
        value = s1;
    }

    public Object clone()
        throws CloneNotSupportedException
    {
        return super.clone();
    }

    public boolean equals(Object obj)
    {
        BasicNameValuePair basicnamevaluepair;
        if(this != obj)
            if(obj instanceof NameValuePair)
            {
                if(!name.equals((basicnamevaluepair = (BasicNameValuePair)obj).name) || !LangUtils.equals(value, basicnamevaluepair.value))
                    return false;
            } else
            {
                return false;
            }
        return true;
    }

    public String getName()
    {
        return name;
    }

    public String getValue()
    {
        return value;
    }

    public int hashCode()
    {
        return LangUtils.hashCode(LangUtils.hashCode(17, name), value);
    }

    public String toString()
    {
        if(value == null)
        {
            return name;
        } else
        {
            StringBuilder stringbuilder = new StringBuilder(1 + name.length() + value.length());
            stringbuilder.append(name);
            stringbuilder.append("=");
            stringbuilder.append(value);
            return stringbuilder.toString();
        }
    }

    private static final long serialVersionUID = 0xa6a85653cc9535f8L;
    private final String name;
    private final String value;
}
