// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn;

import java.io.IOException;
import java.nio.charset.Charset;
import org.apache.http.Consts;
import org.apache.http.io.HttpTransportMetrics;
import org.apache.http.io.SessionOutputBuffer;
import org.apache.http.util.CharArrayBuffer;

// Referenced classes of package org.apache.http.impl.conn:
//            Wire

public class LoggingSessionOutputBuffer
    implements SessionOutputBuffer
{

    public LoggingSessionOutputBuffer(SessionOutputBuffer sessionoutputbuffer, Wire wire1)
    {
        this(sessionoutputbuffer, wire1, null);
    }

    public LoggingSessionOutputBuffer(SessionOutputBuffer sessionoutputbuffer, Wire wire1, String s)
    {
        out = sessionoutputbuffer;
        wire = wire1;
        if(s == null)
            s = Consts.ASCII.name();
        charset = s;
    }

    public void flush()
        throws IOException
    {
        out.flush();
    }

    public HttpTransportMetrics getMetrics()
    {
        return out.getMetrics();
    }

    public void write(int i)
        throws IOException
    {
        out.write(i);
        if(wire.enabled())
            wire.output(i);
    }

    public void write(byte abyte0[])
        throws IOException
    {
        out.write(abyte0);
        if(wire.enabled())
            wire.output(abyte0);
    }

    public void write(byte abyte0[], int i, int j)
        throws IOException
    {
        out.write(abyte0, i, j);
        if(wire.enabled())
            wire.output(abyte0, i, j);
    }

    public void writeLine(String s)
        throws IOException
    {
        out.writeLine(s);
        if(wire.enabled())
        {
            String s1 = (new StringBuilder()).append(s).append("\r\n").toString();
            wire.output(s1.getBytes(charset));
        }
    }

    public void writeLine(CharArrayBuffer chararraybuffer)
        throws IOException
    {
        out.writeLine(chararraybuffer);
        if(wire.enabled())
        {
            String s = new String(chararraybuffer.buffer(), 0, chararraybuffer.length());
            String s1 = (new StringBuilder()).append(s).append("\r\n").toString();
            wire.output(s1.getBytes(charset));
        }
    }

    private final String charset;
    private final SessionOutputBuffer out;
    private final Wire wire;
}
