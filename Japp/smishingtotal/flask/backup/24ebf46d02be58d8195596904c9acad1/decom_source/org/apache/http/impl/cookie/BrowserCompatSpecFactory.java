// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.cookie;

import java.util.Collection;
import org.apache.http.cookie.*;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

// Referenced classes of package org.apache.http.impl.cookie:
//            BrowserCompatSpec

public class BrowserCompatSpecFactory
    implements CookieSpecFactory, CookieSpecProvider
{
    public static final class SecurityLevel extends Enum
    {

        public static SecurityLevel valueOf(String s)
        {
            return (SecurityLevel)Enum.valueOf(org/apache/http/impl/cookie/BrowserCompatSpecFactory$SecurityLevel, s);
        }

        public static SecurityLevel[] values()
        {
            return (SecurityLevel[])$VALUES.clone();
        }

        private static final SecurityLevel $VALUES[];
        public static final SecurityLevel SECURITYLEVEL_DEFAULT;
        public static final SecurityLevel SECURITYLEVEL_IE_MEDIUM;

        static 
        {
            SECURITYLEVEL_DEFAULT = new SecurityLevel("SECURITYLEVEL_DEFAULT", 0);
            SECURITYLEVEL_IE_MEDIUM = new SecurityLevel("SECURITYLEVEL_IE_MEDIUM", 1);
            SecurityLevel asecuritylevel[] = new SecurityLevel[2];
            asecuritylevel[0] = SECURITYLEVEL_DEFAULT;
            asecuritylevel[1] = SECURITYLEVEL_IE_MEDIUM;
            $VALUES = asecuritylevel;
        }

        private SecurityLevel(String s, int i)
        {
            super(s, i);
        }
    }


    public BrowserCompatSpecFactory()
    {
        this(null, SecurityLevel.SECURITYLEVEL_DEFAULT);
    }

    public BrowserCompatSpecFactory(String as[])
    {
        this(null, SecurityLevel.SECURITYLEVEL_DEFAULT);
    }

    public BrowserCompatSpecFactory(String as[], SecurityLevel securitylevel)
    {
        datepatterns = as;
        securityLevel = securitylevel;
    }

    public CookieSpec create(HttpContext httpcontext)
    {
        return new BrowserCompatSpec(datepatterns);
    }

    public CookieSpec newInstance(HttpParams httpparams)
    {
        if(httpparams != null)
        {
            Collection collection = (Collection)httpparams.getParameter("http.protocol.cookie-datepatterns");
            String as[] = null;
            if(collection != null)
                as = (String[])collection.toArray(new String[collection.size()]);
            return new BrowserCompatSpec(as, securityLevel);
        } else
        {
            return new BrowserCompatSpec(null, securityLevel);
        }
    }

    private final String datepatterns[];
    private final SecurityLevel securityLevel;
}
