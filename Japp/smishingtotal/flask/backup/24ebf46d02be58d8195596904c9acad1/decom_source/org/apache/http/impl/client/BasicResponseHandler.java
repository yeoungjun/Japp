// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.*;
import org.apache.http.util.EntityUtils;

public class BasicResponseHandler
    implements ResponseHandler
{

    public BasicResponseHandler()
    {
    }

    public volatile Object handleResponse(HttpResponse httpresponse)
        throws ClientProtocolException, IOException
    {
        return handleResponse(httpresponse);
    }

    public String handleResponse(HttpResponse httpresponse)
        throws HttpResponseException, IOException
    {
        StatusLine statusline = httpresponse.getStatusLine();
        org.apache.http.HttpEntity httpentity = httpresponse.getEntity();
        if(statusline.getStatusCode() >= 300)
        {
            EntityUtils.consume(httpentity);
            throw new HttpResponseException(statusline.getStatusCode(), statusline.getReasonPhrase());
        }
        if(httpentity == null)
            return null;
        else
            return EntityUtils.toString(httpentity);
    }
}
