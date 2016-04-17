// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import org.apache.http.client.protocol.RequestAcceptEncoding;
import org.apache.http.client.protocol.ResponseContentEncoding;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpProcessor;

// Referenced classes of package org.apache.http.impl.client:
//            DefaultHttpClient

public class ContentEncodingHttpClient extends DefaultHttpClient
{

    public ContentEncodingHttpClient()
    {
        this(null);
    }

    public ContentEncodingHttpClient(ClientConnectionManager clientconnectionmanager, HttpParams httpparams)
    {
        super(clientconnectionmanager, httpparams);
    }

    public ContentEncodingHttpClient(HttpParams httpparams)
    {
        this(null, httpparams);
    }

    protected BasicHttpProcessor createHttpProcessor()
    {
        BasicHttpProcessor basichttpprocessor = super.createHttpProcessor();
        basichttpprocessor.addRequestInterceptor(new RequestAcceptEncoding());
        basichttpprocessor.addResponseInterceptor(new ResponseContentEncoding());
        return basichttpprocessor;
    }
}
