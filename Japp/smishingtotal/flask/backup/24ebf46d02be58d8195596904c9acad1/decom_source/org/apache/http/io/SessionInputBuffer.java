// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.io;

import java.io.IOException;
import org.apache.http.util.CharArrayBuffer;

// Referenced classes of package org.apache.http.io:
//            HttpTransportMetrics

public interface SessionInputBuffer
{

    public abstract HttpTransportMetrics getMetrics();

    public abstract boolean isDataAvailable(int i)
        throws IOException;

    public abstract int read()
        throws IOException;

    public abstract int read(byte abyte0[])
        throws IOException;

    public abstract int read(byte abyte0[], int i, int j)
        throws IOException;

    public abstract int readLine(CharArrayBuffer chararraybuffer)
        throws IOException;

    public abstract String readLine()
        throws IOException;
}
