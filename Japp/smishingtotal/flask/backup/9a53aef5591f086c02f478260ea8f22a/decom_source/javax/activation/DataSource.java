// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.activation;

import java.io.*;

public interface DataSource
{

    public abstract String getContentType();

    public abstract InputStream getInputStream()
        throws IOException;

    public abstract String getName();

    public abstract OutputStream getOutputStream()
        throws IOException;
}
