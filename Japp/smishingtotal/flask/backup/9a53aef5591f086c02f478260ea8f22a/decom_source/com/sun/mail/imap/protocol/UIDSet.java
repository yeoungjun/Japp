// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.imap.protocol;

import java.util.Vector;

public class UIDSet
{

    public UIDSet()
    {
    }

    public UIDSet(long l, long l1)
    {
        start = l;
        end = l1;
    }

    public static UIDSet[] createUIDSets(long al[])
    {
        Vector vector = new Vector();
        int i = 0;
        do
        {
            if(i >= al.length)
            {
                UIDSet auidset[] = new UIDSet[vector.size()];
                vector.copyInto(auidset);
                return auidset;
            }
            UIDSet uidset = new UIDSet();
            uidset.start = al[i];
            int j = i + 1;
            while(false) 
            {
                while(j < al.length && al[j] == 1L + al[j - 1]) 
                    j++;
                uidset.end = al[j - 1];
                vector.addElement(uidset);
                i = 1 + (j - 1);
            }
        } while(true);
    }

    public static long size(UIDSet auidset[])
    {
        long l = 0L;
        if(auidset == null)
            return 0L;
        int i = 0;
        do
        {
            if(i >= auidset.length)
                return l;
            l += auidset[i].size();
            i++;
        } while(true);
    }

    public static String toString(UIDSet auidset[])
    {
        if(auidset == null || auidset.length == 0)
            return null;
        int i = 0;
        StringBuffer stringbuffer = new StringBuffer();
        int j = auidset.length;
        do
        {
            long l = auidset[i].start;
            long l1 = auidset[i].end;
            if(l1 > l)
                stringbuffer.append(l).append(':').append(l1);
            else
                stringbuffer.append(l);
            if(++i >= j)
                return stringbuffer.toString();
            stringbuffer.append(',');
        } while(true);
    }

    public long size()
    {
        return 1L + (end - start);
    }

    public long end;
    public long start;
}
