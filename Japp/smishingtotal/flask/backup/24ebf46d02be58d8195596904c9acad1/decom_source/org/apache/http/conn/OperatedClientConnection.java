// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.conn;

import java.io.IOException;
import java.net.Socket;
import org.apache.http.*;
import org.apache.http.params.HttpParams;

public interface OperatedClientConnection
    extends HttpClientConnection, HttpInetConnection
{

    public abstract Socket getSocket();

    public abstract HttpHost getTargetHost();

    public abstract boolean isSecure();

    public abstract void openCompleted(boolean flag, HttpParams httpparams)
        throws IOException;

    public abstract void opening(Socket socket, HttpHost httphost)
        throws IOException;

    public abstract void update(Socket socket, HttpHost httphost, boolean flag, HttpParams httpparams)
        throws IOException;
}
