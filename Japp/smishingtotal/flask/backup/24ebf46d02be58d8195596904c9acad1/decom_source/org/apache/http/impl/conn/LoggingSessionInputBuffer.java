// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn;

import java.io.IOException;
import java.nio.charset.Charset;
import org.apache.http.Consts;
import org.apache.http.io.*;
import org.apache.http.util.CharArrayBuffer;

// Referenced classes of package org.apache.http.impl.conn:
//            Wire

public class LoggingSessionInputBuffer
    implements SessionInputBuffer, EofSensor
{

    public LoggingSessionInputBuffer(SessionInputBuffer sessioninputbuffer, Wire wire1)
    {
        this(sessioninputbuffer, wire1, null);
    }

    public LoggingSessionInputBuffer(SessionInputBuffer sessioninputbuffer, Wire wire1, String s)
    {
        in = sessioninputbuffer;
        EofSensor eofsensor;
        if(sessioninputbuffer instanceof EofSensor)
            eofsensor = (EofSensor)sessioninputbuffer;
        else
            eofsensor = null;
        eofSensor = eofsensor;
        wire = wire1;
        if(s == null)
            s = Consts.ASCII.name();
        charset = s;
    }

    public HttpTransportMetrics getMetrics()
    {
        return in.getMetrics();
    }

    public boolean isDataAvailable(int i)
        throws IOException
    {
        return in.isDataAvailable(i);
    }

    public boolean isEof()
    {
        if(eofSensor != null)
            return eofSensor.isEof();
        else
            return false;
    }

    public int read()
        throws IOException
    {
        int i = in.read();
        if(wire.enabled() && i != -1)
            wire.input(i);
        return i;
    }

    public int read(byte abyte0[])
        throws IOException
    {
        int i = in.read(abyte0);
        if(wire.enabled() && i > 0)
            wire.input(abyte0, 0, i);
        return i;
    }

    public int read(byte abyte0[], int i, int j)
        throws IOException
    {
        int k = in.read(abyte0, i, j);
        if(wire.enabled() && k > 0)
            wire.input(abyte0, i, k);
        return k;
    }

    public int readLine(CharArrayBuffer chararraybuffer)
        throws IOException
    {
        int i = in.readLine(chararraybuffer);
        if(wire.enabled() && i >= 0)
        {
            int j = chararraybuffer.length() - i;
            String s = new String(chararraybuffer.buffer(), j, i);
            String s1 = (new StringBuilder()).append(s).append("\r\n").toString();
            wire.input(s1.getBytes(charset));
        }
        return i;
    }

    public String readLine()
        throws IOException
    {
        String s = in.readLine();
        if(wire.enabled() && s != null)
        {
            String s1 = (new StringBuilder()).append(s).append("\r\n").toString();
            wire.input(s1.getBytes(charset));
        }
        return s;
    }

    private final String charset;
    private final EofSensor eofSensor;
    private final SessionInputBuffer in;
    private final Wire wire;
}
