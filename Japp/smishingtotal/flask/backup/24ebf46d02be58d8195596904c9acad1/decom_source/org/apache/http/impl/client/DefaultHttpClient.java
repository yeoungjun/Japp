// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import java.nio.charset.Charset;
import org.apache.http.HttpVersion;
import org.apache.http.client.protocol.*;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.params.*;
import org.apache.http.protocol.*;
import org.apache.http.util.VersionInfo;

// Referenced classes of package org.apache.http.impl.client:
//            AbstractHttpClient

public class DefaultHttpClient extends AbstractHttpClient
{

    public DefaultHttpClient()
    {
        super(null, null);
    }

    public DefaultHttpClient(ClientConnectionManager clientconnectionmanager)
    {
        super(clientconnectionmanager, null);
    }

    public DefaultHttpClient(ClientConnectionManager clientconnectionmanager, HttpParams httpparams)
    {
        super(clientconnectionmanager, httpparams);
    }

    public DefaultHttpClient(HttpParams httpparams)
    {
        super(null, httpparams);
    }

    public static void setDefaultHttpParams(HttpParams httpparams)
    {
        HttpProtocolParams.setVersion(httpparams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(httpparams, HTTP.DEF_CONTENT_CHARSET.name());
        HttpConnectionParams.setTcpNoDelay(httpparams, true);
        HttpConnectionParams.setSocketBufferSize(httpparams, 8192);
        VersionInfo versioninfo = VersionInfo.loadVersionInfo("org.apache.http.client", org/apache/http/impl/client/DefaultHttpClient.getClassLoader());
        String s;
        if(versioninfo != null)
            s = versioninfo.getRelease();
        else
            s = "UNAVAILABLE";
        HttpProtocolParams.setUserAgent(httpparams, (new StringBuilder()).append("Apache-HttpClient/").append(s).append(" (java 1.5)").toString());
    }

    protected HttpParams createHttpParams()
    {
        SyncBasicHttpParams syncbasichttpparams = new SyncBasicHttpParams();
        setDefaultHttpParams(syncbasichttpparams);
        return syncbasichttpparams;
    }

    protected BasicHttpProcessor createHttpProcessor()
    {
        BasicHttpProcessor basichttpprocessor = new BasicHttpProcessor();
        basichttpprocessor.addInterceptor(new RequestDefaultHeaders());
        basichttpprocessor.addInterceptor(new RequestContent());
        basichttpprocessor.addInterceptor(new RequestTargetHost());
        basichttpprocessor.addInterceptor(new RequestClientConnControl());
        basichttpprocessor.addInterceptor(new RequestUserAgent());
        basichttpprocessor.addInterceptor(new RequestExpectContinue());
        basichttpprocessor.addInterceptor(new RequestAddCookies());
        basichttpprocessor.addInterceptor(new ResponseProcessCookies());
        basichttpprocessor.addInterceptor(new RequestAuthCache());
        basichttpprocessor.addInterceptor(new RequestTargetAuthentication());
        basichttpprocessor.addInterceptor(new RequestProxyAuthentication());
        return basichttpprocessor;
    }
}
