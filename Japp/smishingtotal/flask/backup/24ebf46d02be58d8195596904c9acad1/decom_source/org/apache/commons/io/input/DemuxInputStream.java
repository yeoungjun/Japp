// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;

public class DemuxInputStream extends InputStream
{

    public DemuxInputStream()
    {
    }

    public InputStream bindStream(InputStream inputstream)
    {
        InputStream inputstream1 = (InputStream)m_streams.get();
        m_streams.set(inputstream);
        return inputstream1;
    }

    public void close()
        throws IOException
    {
        InputStream inputstream = (InputStream)m_streams.get();
        if(inputstream != null)
            inputstream.close();
    }

    public int read()
        throws IOException
    {
        InputStream inputstream = (InputStream)m_streams.get();
        if(inputstream != null)
            return inputstream.read();
        else
            return -1;
    }

    private final InheritableThreadLocal m_streams = new InheritableThreadLocal();
}
