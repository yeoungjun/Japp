// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.io;

import java.io.IOException;
import java.net.Socket;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.impl.io:
//            AbstractSessionOutputBuffer

public class SocketOutputBuffer extends AbstractSessionOutputBuffer
{

    public SocketOutputBuffer(Socket socket, int i, HttpParams httpparams)
        throws IOException
    {
        Args.notNull(socket, "Socket");
        int j = i;
        if(j < 0)
            j = socket.getSendBufferSize();
        if(j < 1024)
            j = 1024;
        init(socket.getOutputStream(), j, httpparams);
    }
}
