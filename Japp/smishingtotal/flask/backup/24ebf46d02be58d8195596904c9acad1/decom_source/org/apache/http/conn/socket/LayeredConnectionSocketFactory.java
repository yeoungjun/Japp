// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.conn.socket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import org.apache.http.protocol.HttpContext;

// Referenced classes of package org.apache.http.conn.socket:
//            ConnectionSocketFactory

public interface LayeredConnectionSocketFactory
    extends ConnectionSocketFactory
{

    public abstract Socket createLayeredSocket(Socket socket, String s, int i, HttpContext httpcontext)
        throws IOException, UnknownHostException;
}
