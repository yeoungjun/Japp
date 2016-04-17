// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import org.apache.http.params.AbstractHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;

public class ClientParamsStack extends AbstractHttpParams
{

    public ClientParamsStack(ClientParamsStack clientparamsstack)
    {
        this(clientparamsstack.getApplicationParams(), clientparamsstack.getClientParams(), clientparamsstack.getRequestParams(), clientparamsstack.getOverrideParams());
    }

    public ClientParamsStack(ClientParamsStack clientparamsstack, HttpParams httpparams, HttpParams httpparams1, HttpParams httpparams2, HttpParams httpparams3)
    {
        if(httpparams == null)
            httpparams = clientparamsstack.getApplicationParams();
        if(httpparams1 == null)
            httpparams1 = clientparamsstack.getClientParams();
        if(httpparams2 == null)
            httpparams2 = clientparamsstack.getRequestParams();
        if(httpparams3 == null)
            httpparams3 = clientparamsstack.getOverrideParams();
        this(httpparams, httpparams1, httpparams2, httpparams3);
    }

    public ClientParamsStack(HttpParams httpparams, HttpParams httpparams1, HttpParams httpparams2, HttpParams httpparams3)
    {
        applicationParams = httpparams;
        clientParams = httpparams1;
        requestParams = httpparams2;
        overrideParams = httpparams3;
    }

    public HttpParams copy()
    {
        return this;
    }

    public final HttpParams getApplicationParams()
    {
        return applicationParams;
    }

    public final HttpParams getClientParams()
    {
        return clientParams;
    }

    public final HttpParams getOverrideParams()
    {
        return overrideParams;
    }

    public Object getParameter(String s)
    {
        Args.notNull(s, "Parameter name");
        HttpParams httpparams = overrideParams;
        Object obj = null;
        if(httpparams != null)
            obj = overrideParams.getParameter(s);
        if(obj == null && requestParams != null)
            obj = requestParams.getParameter(s);
        if(obj == null && clientParams != null)
            obj = clientParams.getParameter(s);
        if(obj == null && applicationParams != null)
            obj = applicationParams.getParameter(s);
        return obj;
    }

    public final HttpParams getRequestParams()
    {
        return requestParams;
    }

    public boolean removeParameter(String s)
    {
        throw new UnsupportedOperationException("Removing parameters in a stack is not supported.");
    }

    public HttpParams setParameter(String s, Object obj)
        throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException("Setting parameters in a stack is not supported.");
    }

    protected final HttpParams applicationParams;
    protected final HttpParams clientParams;
    protected final HttpParams overrideParams;
    protected final HttpParams requestParams;
}
