// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.impl.client:
//            AbstractAuthenticationHandler

public class DefaultTargetAuthenticationHandler extends AbstractAuthenticationHandler
{

    public DefaultTargetAuthenticationHandler()
    {
    }

    protected List getAuthPreferences(HttpResponse httpresponse, HttpContext httpcontext)
    {
        List list = (List)httpresponse.getParams().getParameter("http.auth.target-scheme-pref");
        if(list != null)
            return list;
        else
            return super.getAuthPreferences(httpresponse, httpcontext);
    }

    public Map getChallenges(HttpResponse httpresponse, HttpContext httpcontext)
        throws MalformedChallengeException
    {
        Args.notNull(httpresponse, "HTTP response");
        return parseChallenges(httpresponse.getHeaders("WWW-Authenticate"));
    }

    public boolean isAuthenticationRequested(HttpResponse httpresponse, HttpContext httpcontext)
    {
        Args.notNull(httpresponse, "HTTP response");
        return httpresponse.getStatusLine().getStatusCode() == 401;
    }
}
