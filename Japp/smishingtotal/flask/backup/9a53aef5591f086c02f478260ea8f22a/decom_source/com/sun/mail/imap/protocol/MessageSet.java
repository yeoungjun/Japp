// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.imap.protocol;

import java.util.Vector;

public class MessageSet
{

    public MessageSet()
    {
    }

    public MessageSet(int i, int j)
    {
        start = i;
        end = j;
    }

    public static MessageSet[] createMessageSets(int ai[])
    {
        Vector vector = new Vector();
        int i = 0;
        do
        {
            if(i >= ai.length)
            {
                MessageSet amessageset[] = new MessageSet[vector.size()];
                vector.copyInto(amessageset);
                return amessageset;
            }
            MessageSet messageset = new MessageSet();
            messageset.start = ai[i];
            int j = i + 1;
            while(false) 
            {
                while(j < ai.length && ai[j] == 1 + ai[j - 1]) 
                    j++;
                messageset.end = ai[j - 1];
                vector.addElement(messageset);
                i = 1 + (j - 1);
            }
        } while(true);
    }

    public static int size(MessageSet amessageset[])
    {
        int i = 0;
        if(amessageset == null)
            return 0;
        int j = 0;
        do
        {
            if(j >= amessageset.length)
                return i;
            i += amessageset[j].size();
            j++;
        } while(true);
    }

    public static String toString(MessageSet amessageset[])
    {
        if(amessageset == null || amessageset.length == 0)
            return null;
        int i = 0;
        StringBuffer stringbuffer = new StringBuffer();
        int j = amessageset.length;
        do
        {
            int k = amessageset[i].start;
            int l = amessageset[i].end;
            if(l > k)
                stringbuffer.append(k).append(':').append(l);
            else
                stringbuffer.append(k);
            if(++i >= j)
                return stringbuffer.toString();
            stringbuffer.append(',');
        } while(true);
    }

    public int size()
    {
        return 1 + (end - start);
    }

    public int end;
    public int start;
}
