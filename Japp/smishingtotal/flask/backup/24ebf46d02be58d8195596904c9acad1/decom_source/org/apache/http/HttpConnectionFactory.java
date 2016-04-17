// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http;

import java.io.IOException;
import java.net.Socket;

// Referenced classes of package org.apache.http:
//            HttpConnection

public interface HttpConnectionFactory
{

    public abstract HttpConnection createConnection(Socket socket)
        throws IOException;
}
