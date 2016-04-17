// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.activation;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.*;
import java.util.*;

class SecuritySupport
{

    private SecuritySupport()
    {
    }

    public static ClassLoader getContextClassLoader()
    {
        return (ClassLoader)AccessController.doPrivileged(new PrivilegedAction() {

            public Object run()
            {
                ClassLoader classloader;
                try
                {
                    classloader = Thread.currentThread().getContextClassLoader();
                }
                catch(SecurityException securityexception)
                {
                    return null;
                }
                return classloader;
            }

        });
    }

    public static InputStream getResourceAsStream(final Class c, final String name)
        throws IOException
    {
        InputStream inputstream;
        try
        {
            inputstream = (InputStream)AccessController.doPrivileged(new PrivilegedExceptionAction() {

                public Object run()
                    throws IOException
                {
                    return c.getResourceAsStream(name);
                }

                private final Class val$c;
                private final String val$name;

            
            {
                c = class1;
                name = s;
                super();
            }
            });
        }
        catch(PrivilegedActionException privilegedactionexception)
        {
            throw (IOException)privilegedactionexception.getException();
        }
        return inputstream;
    }

    public static URL[] getResources(final ClassLoader cl, final String name)
    {
        return (URL[])AccessController.doPrivileged(new PrivilegedAction() {

            public Object run()
            {
                URL aurl[] = (URL[])null;
                ArrayList arraylist;
                Enumeration enumeration;
                arraylist = new ArrayList();
                enumeration = cl.getResources(name);
_L2:
                if(enumeration == null)
                    break MISSING_BLOCK_LABEL_41;
                if(enumeration.hasMoreElements())
                    break MISSING_BLOCK_LABEL_71;
                if(arraylist.size() > 0)
                {
                    aurl = new URL[arraylist.size()];
                    return (URL[])arraylist.toArray(aurl);
                }
                break MISSING_BLOCK_LABEL_105;
                URL url = (URL)enumeration.nextElement();
                if(url == null) goto _L2; else goto _L1
_L1:
                arraylist.add(url);
                  goto _L2
                IOException ioexception;
                ioexception;
                return aurl;
                SecurityException securityexception;
                securityexception;
                return aurl;
            }

            private final ClassLoader val$cl;
            private final String val$name;

            
            {
                cl = classloader;
                name = s;
                super();
            }
        });
    }

    public static URL[] getSystemResources(final String name)
    {
        return (URL[])AccessController.doPrivileged(new PrivilegedAction() {

            public Object run()
            {
                URL aurl[] = (URL[])null;
                ArrayList arraylist;
                Enumeration enumeration;
                arraylist = new ArrayList();
                enumeration = ClassLoader.getSystemResources(name);
_L2:
                if(enumeration == null)
                    break MISSING_BLOCK_LABEL_37;
                if(enumeration.hasMoreElements())
                    break MISSING_BLOCK_LABEL_67;
                if(arraylist.size() > 0)
                {
                    aurl = new URL[arraylist.size()];
                    return (URL[])arraylist.toArray(aurl);
                }
                break MISSING_BLOCK_LABEL_101;
                URL url = (URL)enumeration.nextElement();
                if(url == null) goto _L2; else goto _L1
_L1:
                arraylist.add(url);
                  goto _L2
                IOException ioexception;
                ioexception;
                return aurl;
                SecurityException securityexception;
                securityexception;
                return aurl;
            }

            private final String val$name;

            
            {
                name = s;
                super();
            }
        });
    }

    public static InputStream openStream(final URL url)
        throws IOException
    {
        InputStream inputstream;
        try
        {
            inputstream = (InputStream)AccessController.doPrivileged(new PrivilegedExceptionAction() {

                public Object run()
                    throws IOException
                {
                    return url.openStream();
                }

                private final URL val$url;

            
            {
                url = url1;
                super();
            }
            });
        }
        catch(PrivilegedActionException privilegedactionexception)
        {
            throw (IOException)privilegedactionexception.getException();
        }
        return inputstream;
    }
}
