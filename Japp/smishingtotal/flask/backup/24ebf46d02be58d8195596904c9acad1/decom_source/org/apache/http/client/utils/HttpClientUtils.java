// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client.utils;

import java.io.Closeable;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

public class HttpClientUtils
{

    private HttpClientUtils()
    {
    }

    public static void closeQuietly(HttpResponse httpresponse)
    {
        org.apache.http.HttpEntity httpentity;
        if(httpresponse == null)
            break MISSING_BLOCK_LABEL_19;
        httpentity = httpresponse.getEntity();
        if(httpentity == null)
            break MISSING_BLOCK_LABEL_19;
        EntityUtils.consume(httpentity);
        return;
        IOException ioexception;
        ioexception;
    }

    public static void closeQuietly(HttpClient httpclient)
    {
        if(httpclient == null || !(httpclient instanceof Closeable))
            break MISSING_BLOCK_LABEL_20;
        ((Closeable)httpclient).close();
        return;
        IOException ioexception;
        ioexception;
    }

    public static void closeQuietly(CloseableHttpResponse closeablehttpresponse)
    {
        if(closeablehttpresponse == null)
            break MISSING_BLOCK_LABEL_30;
        EntityUtils.consume(closeablehttpresponse.getEntity());
        Exception exception;
        try
        {
            closeablehttpresponse.close();
            return;
        }
        catch(IOException ioexception) { }
        break MISSING_BLOCK_LABEL_30;
        exception;
        closeablehttpresponse.close();
        throw exception;
    }
}
