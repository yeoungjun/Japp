// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.conn.scheme;

import java.util.Locale;
import org.apache.http.util.Args;
import org.apache.http.util.LangUtils;

// Referenced classes of package org.apache.http.conn.scheme:
//            SchemeLayeredSocketFactory, LayeredSchemeSocketFactory, SchemeLayeredSocketFactoryAdaptor2, LayeredSocketFactory, 
//            SchemeLayeredSocketFactoryAdaptor, SchemeSocketFactoryAdaptor, LayeredSocketFactoryAdaptor, SocketFactoryAdaptor, 
//            SchemeSocketFactory, SocketFactory

public final class Scheme
{

    public Scheme(String s, int i, SchemeSocketFactory schemesocketfactory)
    {
        Args.notNull(s, "Scheme name");
        boolean flag;
        if(i > 0 && i <= 65535)
            flag = true;
        else
            flag = false;
        Args.check(flag, "Port is invalid");
        Args.notNull(schemesocketfactory, "Socket factory");
        name = s.toLowerCase(Locale.ENGLISH);
        defaultPort = i;
        if(schemesocketfactory instanceof SchemeLayeredSocketFactory)
        {
            layered = true;
            socketFactory = schemesocketfactory;
            return;
        }
        if(schemesocketfactory instanceof LayeredSchemeSocketFactory)
        {
            layered = true;
            socketFactory = new SchemeLayeredSocketFactoryAdaptor2((LayeredSchemeSocketFactory)schemesocketfactory);
            return;
        } else
        {
            layered = false;
            socketFactory = schemesocketfactory;
            return;
        }
    }

    public Scheme(String s, SocketFactory socketfactory, int i)
    {
        Args.notNull(s, "Scheme name");
        Args.notNull(socketfactory, "Socket factory");
        boolean flag;
        if(i > 0 && i <= 65535)
            flag = true;
        else
            flag = false;
        Args.check(flag, "Port is invalid");
        name = s.toLowerCase(Locale.ENGLISH);
        if(socketfactory instanceof LayeredSocketFactory)
        {
            socketFactory = new SchemeLayeredSocketFactoryAdaptor((LayeredSocketFactory)socketfactory);
            layered = true;
        } else
        {
            socketFactory = new SchemeSocketFactoryAdaptor(socketfactory);
            layered = false;
        }
        defaultPort = i;
    }

    public final boolean equals(Object obj)
    {
        Scheme scheme;
        if(this != obj)
            if(obj instanceof Scheme)
            {
                if(!name.equals((scheme = (Scheme)obj).name) || defaultPort != scheme.defaultPort || layered != scheme.layered)
                    return false;
            } else
            {
                return false;
            }
        return true;
    }

    public final int getDefaultPort()
    {
        return defaultPort;
    }

    public final String getName()
    {
        return name;
    }

    public final SchemeSocketFactory getSchemeSocketFactory()
    {
        return socketFactory;
    }

    public final SocketFactory getSocketFactory()
    {
        if(socketFactory instanceof SchemeSocketFactoryAdaptor)
            return ((SchemeSocketFactoryAdaptor)socketFactory).getFactory();
        if(layered)
            return new LayeredSocketFactoryAdaptor((LayeredSchemeSocketFactory)socketFactory);
        else
            return new SocketFactoryAdaptor(socketFactory);
    }

    public int hashCode()
    {
        return LangUtils.hashCode(LangUtils.hashCode(LangUtils.hashCode(17, defaultPort), name), layered);
    }

    public final boolean isLayered()
    {
        return layered;
    }

    public final int resolvePort(int i)
    {
        if(i <= 0)
            i = defaultPort;
        return i;
    }

    public final String toString()
    {
        if(stringRep == null)
        {
            StringBuilder stringbuilder = new StringBuilder();
            stringbuilder.append(name);
            stringbuilder.append(':');
            stringbuilder.append(Integer.toString(defaultPort));
            stringRep = stringbuilder.toString();
        }
        return stringRep;
    }

    private final int defaultPort;
    private final boolean layered;
    private final String name;
    private final SchemeSocketFactory socketFactory;
    private String stringRep;
}
