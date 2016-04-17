// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.util;

import java.net.*;

// Referenced classes of package org.apache.http.util:
//            Args

public final class NetUtils
{

    public NetUtils()
    {
    }

    public static void formatAddress(StringBuilder stringbuilder, SocketAddress socketaddress)
    {
        Args.notNull(stringbuilder, "Buffer");
        Args.notNull(socketaddress, "Socket address");
        if(socketaddress instanceof InetSocketAddress)
        {
            InetSocketAddress inetsocketaddress = (InetSocketAddress)socketaddress;
            Object obj = inetsocketaddress.getAddress();
            if(obj != null)
                obj = ((InetAddress) (obj)).getHostAddress();
            stringbuilder.append(obj).append(':').append(inetsocketaddress.getPort());
            return;
        } else
        {
            stringbuilder.append(socketaddress);
            return;
        }
    }
}
