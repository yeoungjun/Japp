// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.activation;

import java.io.*;

// Referenced classes of package javax.activation:
//            DataSource, DataHandler

class DataHandlerDataSource
    implements DataSource
{

    public DataHandlerDataSource(DataHandler datahandler)
    {
        dataHandler = null;
        dataHandler = datahandler;
    }

    public String getContentType()
    {
        return dataHandler.getContentType();
    }

    public InputStream getInputStream()
        throws IOException
    {
        return dataHandler.getInputStream();
    }

    public String getName()
    {
        return dataHandler.getName();
    }

    public OutputStream getOutputStream()
        throws IOException
    {
        return dataHandler.getOutputStream();
    }

    DataHandler dataHandler;
}
