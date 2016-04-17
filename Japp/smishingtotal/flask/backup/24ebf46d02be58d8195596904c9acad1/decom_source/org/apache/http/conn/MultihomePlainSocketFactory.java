// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.conn;

import java.io.IOException;
import java.net.*;
import java.util.*;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

// Referenced classes of package org.apache.http.conn:
//            ConnectTimeoutException

public final class MultihomePlainSocketFactory
    implements SocketFactory
{

    private MultihomePlainSocketFactory()
    {
    }

    public static MultihomePlainSocketFactory getSocketFactory()
    {
        return DEFAULT_FACTORY;
    }

    public Socket connectSocket(Socket socket, String s, int i, InetAddress inetaddress, int j, HttpParams httpparams)
        throws IOException
    {
        Socket socket1;
        IOException ioexception;
        InetAddress inetaddress1;
        Args.notNull(s, "Target host");
        Args.notNull(httpparams, "HTTP parameters");
        socket1 = socket;
        if(socket1 == null)
            socket1 = createSocket();
        if(inetaddress != null || j > 0)
        {
            int k;
            InetAddress ainetaddress[];
            ArrayList arraylist;
            Iterator iterator;
            if(j <= 0)
                j = 0;
            socket1.bind(new InetSocketAddress(inetaddress, j));
        }
        k = HttpConnectionParams.getConnectionTimeout(httpparams);
        ainetaddress = InetAddress.getAllByName(s);
        arraylist = new ArrayList(ainetaddress.length);
        arraylist.addAll(Arrays.asList(ainetaddress));
        Collections.shuffle(arraylist);
        ioexception = null;
        iterator = arraylist.iterator();
_L2:
        if(!iterator.hasNext())
            break MISSING_BLOCK_LABEL_154;
        inetaddress1 = (InetAddress)iterator.next();
        socket1.connect(new InetSocketAddress(inetaddress1, i), k);
        IOException ioexception1;
        SocketTimeoutException sockettimeoutexception;
        if(ioexception != null)
            throw ioexception;
        else
            return socket1;
        sockettimeoutexception;
        throw new ConnectTimeoutException((new StringBuilder()).append("Connect to ").append(inetaddress1).append(" timed out").toString());
        ioexception1;
        socket1 = new Socket();
        ioexception = ioexception1;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public Socket createSocket()
    {
        return new Socket();
    }

    public final boolean isSecure(Socket socket)
        throws IllegalArgumentException
    {
        Args.notNull(socket, "Socket");
        boolean flag;
        if(!socket.isClosed())
            flag = true;
        else
            flag = false;
        Asserts.check(flag, "Socket is closed");
        return false;
    }

    private static final MultihomePlainSocketFactory DEFAULT_FACTORY = new MultihomePlainSocketFactory();

}
