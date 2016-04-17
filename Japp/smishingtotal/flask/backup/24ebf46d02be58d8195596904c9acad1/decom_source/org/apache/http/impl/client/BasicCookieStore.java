// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import java.io.Serializable;
import java.util.*;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieIdentityComparator;

public class BasicCookieStore
    implements CookieStore, Serializable
{

    public BasicCookieStore()
    {
    }

    public void addCookie(Cookie cookie)
    {
        this;
        JVM INSTR monitorenter ;
        if(cookie == null)
            break MISSING_BLOCK_LABEL_40;
        cookies.remove(cookie);
        if(!cookie.isExpired(new Date()))
            cookies.add(cookie);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void addCookies(Cookie acookie[])
    {
        this;
        JVM INSTR monitorenter ;
        if(acookie == null) goto _L2; else goto _L1
_L1:
        int i = acookie.length;
        int j = 0;
_L3:
        if(j >= i)
            break; /* Loop/switch isn't completed */
        addCookie(acookie[j]);
        j++;
        if(true) goto _L3; else goto _L2
_L2:
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void clear()
    {
        this;
        JVM INSTR monitorenter ;
        cookies.clear();
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public boolean clearExpired(Date date)
    {
        this;
        JVM INSTR monitorenter ;
        if(date != null) goto _L2; else goto _L1
_L1:
        boolean flag = false;
_L6:
        this;
        JVM INSTR monitorexit ;
        return flag;
_L2:
        flag = false;
        Iterator iterator = cookies.iterator();
_L4:
        do
            if(!iterator.hasNext())
                break; /* Loop/switch isn't completed */
        while(!((Cookie)iterator.next()).isExpired(date));
        iterator.remove();
        flag = true;
        if(true) goto _L4; else goto _L3
_L3:
        if(true) goto _L6; else goto _L5
_L5:
        Exception exception;
        exception;
        throw exception;
    }

    public List getCookies()
    {
        this;
        JVM INSTR monitorenter ;
        ArrayList arraylist = new ArrayList(cookies);
        this;
        JVM INSTR monitorexit ;
        return arraylist;
        Exception exception;
        exception;
        throw exception;
    }

    public String toString()
    {
        this;
        JVM INSTR monitorenter ;
        String s = cookies.toString();
        this;
        JVM INSTR monitorexit ;
        return s;
        Exception exception;
        exception;
        throw exception;
    }

    private static final long serialVersionUID = 0x96ca8bce24c77aa7L;
    private final TreeSet cookies = new TreeSet(new CookieIdentityComparator());
}
