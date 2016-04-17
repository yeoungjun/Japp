// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;


// Referenced classes of package org.apache.http.impl.client:
//            DefaultRedirectStrategy

public class LaxRedirectStrategy extends DefaultRedirectStrategy
{

    public LaxRedirectStrategy()
    {
    }

    protected boolean isRedirectable(String s)
    {
        String as[] = REDIRECT_METHODS;
        int i = as.length;
        for(int j = 0; j < i; j++)
            if(as[j].equalsIgnoreCase(s))
                return true;

        return false;
    }

    private static final String REDIRECT_METHODS[] = {
        "GET", "POST", "HEAD"
    };

}
