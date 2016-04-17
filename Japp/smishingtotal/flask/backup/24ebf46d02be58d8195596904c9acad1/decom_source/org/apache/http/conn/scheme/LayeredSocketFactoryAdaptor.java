// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.conn.scheme;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

// Referenced classes of package org.apache.http.conn.scheme:
//            SocketFactoryAdaptor, LayeredSocketFactory, LayeredSchemeSocketFactory

class LayeredSocketFactoryAdaptor extends SocketFactoryAdaptor
    implements LayeredSocketFactory
{

    LayeredSocketFactoryAdaptor(LayeredSchemeSocketFactory layeredschemesocketfactory)
    {
        super(layeredschemesocketfactory);
        factory = layeredschemesocketfactory;
    }

    public Socket createSocket(Socket socket, String s, int i, boolean flag)
        throws IOException, UnknownHostException
    {
        return factory.createLayeredSocket(socket, s, i, flag);
    }

    private final LayeredSchemeSocketFactory factory;
}
