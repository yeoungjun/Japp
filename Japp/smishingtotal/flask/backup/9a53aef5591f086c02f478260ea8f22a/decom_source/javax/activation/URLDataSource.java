// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.activation;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

// Referenced classes of package javax.activation:
//            DataSource

public class URLDataSource
    implements DataSource
{

    public URLDataSource(URL url1)
    {
        url = null;
        url_conn = null;
        url = url1;
    }

    public String getContentType()
    {
        URLConnection urlconnection;
        String s;
        try
        {
            if(url_conn == null)
                url_conn = url.openConnection();
        }
        catch(IOException ioexception) { }
        urlconnection = url_conn;
        s = null;
        if(urlconnection != null)
            s = url_conn.getContentType();
        if(s == null)
            s = "application/octet-stream";
        return s;
    }

    public InputStream getInputStream()
        throws IOException
    {
        return url.openStream();
    }

    public String getName()
    {
        return url.getFile();
    }

    public OutputStream getOutputStream()
        throws IOException
    {
        url_conn = url.openConnection();
        if(url_conn != null)
        {
            url_conn.setDoOutput(true);
            return url_conn.getOutputStream();
        } else
        {
            return null;
        }
    }

    public URL getURL()
    {
        return url;
    }

    private URL url;
    private URLConnection url_conn;
}
