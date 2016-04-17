// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client.methods;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import org.apache.http.*;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.client.methods:
//            HttpRequestBase

public class HttpOptions extends HttpRequestBase
{

    public HttpOptions()
    {
    }

    public HttpOptions(String s)
    {
        setURI(URI.create(s));
    }

    public HttpOptions(URI uri)
    {
        setURI(uri);
    }

    public Set getAllowedMethods(HttpResponse httpresponse)
    {
        Args.notNull(httpresponse, "HTTP response");
        HeaderIterator headeriterator = httpresponse.headerIterator("Allow");
        HashSet hashset = new HashSet();
        while(headeriterator.hasNext()) 
        {
            HeaderElement aheaderelement[] = headeriterator.nextHeader().getElements();
            int i = aheaderelement.length;
            int j = 0;
            while(j < i) 
            {
                hashset.add(aheaderelement[j].getName());
                j++;
            }
        }
        return hashset;
    }

    public String getMethod()
    {
        return "OPTIONS";
    }

    public static final String METHOD_NAME = "OPTIONS";
}
