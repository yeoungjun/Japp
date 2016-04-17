// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.message;

import org.apache.http.*;

// Referenced classes of package org.apache.http.message:
//            BasicHttpRequest

public class BasicHttpEntityEnclosingRequest extends BasicHttpRequest
    implements HttpEntityEnclosingRequest
{

    public BasicHttpEntityEnclosingRequest(String s, String s1)
    {
        super(s, s1);
    }

    public BasicHttpEntityEnclosingRequest(String s, String s1, ProtocolVersion protocolversion)
    {
        super(s, s1, protocolversion);
    }

    public BasicHttpEntityEnclosingRequest(RequestLine requestline)
    {
        super(requestline);
    }

    public boolean expectContinue()
    {
        Header header = getFirstHeader("Expect");
        return header != null && "100-continue".equalsIgnoreCase(header.getValue());
    }

    public HttpEntity getEntity()
    {
        return entity;
    }

    public void setEntity(HttpEntity httpentity)
    {
        entity = httpentity;
    }

    private HttpEntity entity;
}
