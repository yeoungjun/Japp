// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.conn;

import java.io.IOException;
import java.net.Socket;
import javax.net.ssl.SSLSession;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpInetConnection;

public interface ManagedHttpClientConnection
    extends HttpClientConnection, HttpInetConnection
{

    public abstract void bind(Socket socket)
        throws IOException;

    public abstract String getId();

    public abstract SSLSession getSSLSession();

    public abstract Socket getSocket();
}
