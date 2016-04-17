// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.conn.scheme;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import org.apache.http.params.HttpParams;

// Referenced classes of package org.apache.http.conn.scheme:
//            SchemeSocketFactoryAdaptor, SchemeLayeredSocketFactory, LayeredSocketFactory

class SchemeLayeredSocketFactoryAdaptor extends SchemeSocketFactoryAdaptor
    implements SchemeLayeredSocketFactory
{

    SchemeLayeredSocketFactoryAdaptor(LayeredSocketFactory layeredsocketfactory)
    {
        super(layeredsocketfactory);
        factory = layeredsocketfactory;
    }

    public Socket createLayeredSocket(Socket socket, String s, int i, HttpParams httpparams)
        throws IOException, UnknownHostException
    {
        return factory.createSocket(socket, s, i, true);
    }

    private final LayeredSocketFactory factory;
}
