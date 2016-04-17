// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn;

import java.io.*;
import org.apache.commons.logging.Log;
import org.apache.http.util.Args;

public class Wire
{

    public Wire(Log log1)
    {
        this(log1, "");
    }

    public Wire(Log log1, String s)
    {
        log = log1;
        id = s;
    }

    private void wire(String s, InputStream inputstream)
        throws IOException
    {
        StringBuilder stringbuilder = new StringBuilder();
        do
        {
            int i = inputstream.read();
            if(i == -1)
                break;
            if(i == 13)
                stringbuilder.append("[\\r]");
            else
            if(i == 10)
            {
                stringbuilder.append("[\\n]\"");
                stringbuilder.insert(0, "\"");
                stringbuilder.insert(0, s);
                log.debug((new StringBuilder()).append(id).append(" ").append(stringbuilder.toString()).toString());
                stringbuilder.setLength(0);
            } else
            if(i < 32 || i > 127)
            {
                stringbuilder.append("[0x");
                stringbuilder.append(Integer.toHexString(i));
                stringbuilder.append("]");
            } else
            {
                stringbuilder.append((char)i);
            }
        } while(true);
        if(stringbuilder.length() > 0)
        {
            stringbuilder.append('"');
            stringbuilder.insert(0, '"');
            stringbuilder.insert(0, s);
            log.debug((new StringBuilder()).append(id).append(" ").append(stringbuilder.toString()).toString());
        }
    }

    public boolean enabled()
    {
        return log.isDebugEnabled();
    }

    public void input(int i)
        throws IOException
    {
        byte abyte0[] = new byte[1];
        abyte0[0] = (byte)i;
        input(abyte0);
    }

    public void input(InputStream inputstream)
        throws IOException
    {
        Args.notNull(inputstream, "Input");
        wire("<< ", inputstream);
    }

    public void input(String s)
        throws IOException
    {
        Args.notNull(s, "Input");
        input(s.getBytes());
    }

    public void input(byte abyte0[])
        throws IOException
    {
        Args.notNull(abyte0, "Input");
        wire("<< ", new ByteArrayInputStream(abyte0));
    }

    public void input(byte abyte0[], int i, int j)
        throws IOException
    {
        Args.notNull(abyte0, "Input");
        wire("<< ", new ByteArrayInputStream(abyte0, i, j));
    }

    public void output(int i)
        throws IOException
    {
        byte abyte0[] = new byte[1];
        abyte0[0] = (byte)i;
        output(abyte0);
    }

    public void output(InputStream inputstream)
        throws IOException
    {
        Args.notNull(inputstream, "Output");
        wire(">> ", inputstream);
    }

    public void output(String s)
        throws IOException
    {
        Args.notNull(s, "Output");
        output(s.getBytes());
    }

    public void output(byte abyte0[])
        throws IOException
    {
        Args.notNull(abyte0, "Output");
        wire(">> ", new ByteArrayInputStream(abyte0));
    }

    public void output(byte abyte0[], int i, int j)
        throws IOException
    {
        Args.notNull(abyte0, "Output");
        wire(">> ", new ByteArrayInputStream(abyte0, i, j));
    }

    private final String id;
    private final Log log;
}
