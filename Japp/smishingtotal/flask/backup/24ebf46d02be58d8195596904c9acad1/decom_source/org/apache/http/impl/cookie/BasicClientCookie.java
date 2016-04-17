// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.cookie;

import java.io.Serializable;
import java.util.*;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.SetCookie;
import org.apache.http.util.Args;

public class BasicClientCookie
    implements SetCookie, ClientCookie, Cloneable, Serializable
{

    public BasicClientCookie(String s, String s1)
    {
        Args.notNull(s, "Name");
        name = s;
        attribs = new HashMap();
        value = s1;
    }

    public Object clone()
        throws CloneNotSupportedException
    {
        BasicClientCookie basicclientcookie = (BasicClientCookie)super.clone();
        basicclientcookie.attribs = new HashMap(attribs);
        return basicclientcookie;
    }

    public boolean containsAttribute(String s)
    {
        return attribs.get(s) != null;
    }

    public String getAttribute(String s)
    {
        return (String)attribs.get(s);
    }

    public String getComment()
    {
        return cookieComment;
    }

    public String getCommentURL()
    {
        return null;
    }

    public String getDomain()
    {
        return cookieDomain;
    }

    public Date getExpiryDate()
    {
        return cookieExpiryDate;
    }

    public String getName()
    {
        return name;
    }

    public String getPath()
    {
        return cookiePath;
    }

    public int[] getPorts()
    {
        return null;
    }

    public String getValue()
    {
        return value;
    }

    public int getVersion()
    {
        return cookieVersion;
    }

    public boolean isExpired(Date date)
    {
        Args.notNull(date, "Date");
        return cookieExpiryDate != null && cookieExpiryDate.getTime() <= date.getTime();
    }

    public boolean isPersistent()
    {
        return cookieExpiryDate != null;
    }

    public boolean isSecure()
    {
        return isSecure;
    }

    public void setAttribute(String s, String s1)
    {
        attribs.put(s, s1);
    }

    public void setComment(String s)
    {
        cookieComment = s;
    }

    public void setDomain(String s)
    {
        if(s != null)
        {
            cookieDomain = s.toLowerCase(Locale.ENGLISH);
            return;
        } else
        {
            cookieDomain = null;
            return;
        }
    }

    public void setExpiryDate(Date date)
    {
        cookieExpiryDate = date;
    }

    public void setPath(String s)
    {
        cookiePath = s;
    }

    public void setSecure(boolean flag)
    {
        isSecure = flag;
    }

    public void setValue(String s)
    {
        value = s;
    }

    public void setVersion(int i)
    {
        cookieVersion = i;
    }

    public String toString()
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("[version: ");
        stringbuilder.append(Integer.toString(cookieVersion));
        stringbuilder.append("]");
        stringbuilder.append("[name: ");
        stringbuilder.append(name);
        stringbuilder.append("]");
        stringbuilder.append("[value: ");
        stringbuilder.append(value);
        stringbuilder.append("]");
        stringbuilder.append("[domain: ");
        stringbuilder.append(cookieDomain);
        stringbuilder.append("]");
        stringbuilder.append("[path: ");
        stringbuilder.append(cookiePath);
        stringbuilder.append("]");
        stringbuilder.append("[expiry: ");
        stringbuilder.append(cookieExpiryDate);
        stringbuilder.append("]");
        return stringbuilder.toString();
    }

    private static final long serialVersionUID = 0xca4bb969ba98b1ceL;
    private Map attribs;
    private String cookieComment;
    private String cookieDomain;
    private Date cookieExpiryDate;
    private String cookiePath;
    private int cookieVersion;
    private boolean isSecure;
    private final String name;
    private String value;
}
