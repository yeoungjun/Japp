// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.io;

import java.io.IOException;
import java.net.Socket;
import org.apache.http.io.EofSensor;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.impl.io:
//            AbstractSessionInputBuffer

public class SocketInputBuffer extends AbstractSessionInputBuffer
    implements EofSensor
{

    public SocketInputBuffer(Socket socket1, int i, HttpParams httpparams)
        throws IOException
    {
        Args.notNull(socket1, "Socket");
        socket = socket1;
        eof = false;
        int j = i;
        if(j < 0)
            j = socket1.getReceiveBufferSize();
        if(j < 1024)
            j = 1024;
        init(socket1.getInputStream(), j, httpparams);
    }

    protected int fillBuffer()
        throws IOException
    {
        int i = super.fillBuffer();
        boolean flag;
        if(i == -1)
            flag = true;
        else
            flag = false;
        eof = flag;
        return i;
    }

    public boolean isDataAvailable(int i)
        throws IOException
    {
        boolean flag;
        int j;
        flag = hasBufferedData();
        if(flag)
            break MISSING_BLOCK_LABEL_47;
        j = socket.getSoTimeout();
        boolean flag1;
        socket.setSoTimeout(i);
        fillBuffer();
        flag1 = hasBufferedData();
        flag = flag1;
        socket.setSoTimeout(j);
        return flag;
        Exception exception;
        exception;
        socket.setSoTimeout(j);
        throw exception;
    }

    public boolean isEof()
    {
        return eof;
    }

    private boolean eof;
    private final Socket socket;
}
