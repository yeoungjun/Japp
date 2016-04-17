// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail;

import com.sun.mail.util.LineInputStream;
import java.io.*;
import java.lang.reflect.Constructor;
import java.net.InetAddress;
import java.net.URL;
import java.security.*;
import java.util.*;

// Referenced classes of package javax.mail:
//            NoSuchProviderException, Provider, URLName, Store, 
//            Transport, StreamLoader, MessagingException, PasswordAuthentication, 
//            Address, Authenticator, Folder

public final class Session
{

    private Session(Properties properties, Authenticator authenticator1)
    {
        debug = false;
        props = properties;
        authenticator = authenticator1;
        if(Boolean.valueOf(properties.getProperty("mail.debug")).booleanValue())
            debug = true;
        if(debug)
            pr("DEBUG: JavaMail version 1.4.1");
        Class class1;
        if(authenticator1 != null)
            class1 = authenticator1.getClass();
        else
            class1 = getClass();
        loadProviders(class1);
        loadAddressMap(class1);
    }

    private static ClassLoader getContextClassLoader()
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

    public static Session getDefaultInstance(Properties properties)
    {
        return getDefaultInstance(properties, null);
    }

    public static Session getDefaultInstance(Properties properties, Authenticator authenticator1)
    {
        javax/mail/Session;
        JVM INSTR monitorenter ;
        if(defaultSession != null) goto _L2; else goto _L1
_L1:
        defaultSession = new Session(properties, authenticator1);
_L4:
        Session session = defaultSession;
        javax/mail/Session;
        JVM INSTR monitorexit ;
        return session;
_L2:
        if(defaultSession.authenticator == authenticator1) goto _L4; else goto _L3
_L3:
        if(defaultSession.authenticator == null || authenticator1 == null)
            break; /* Loop/switch isn't completed */
        if(defaultSession.authenticator.getClass().getClassLoader() == authenticator1.getClass().getClassLoader()) goto _L4; else goto _L5
_L5:
        throw new SecurityException("Access to default session denied");
        Exception exception;
        exception;
        javax/mail/Session;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public static Session getInstance(Properties properties)
    {
        return new Session(properties, null);
    }

    public static Session getInstance(Properties properties, Authenticator authenticator1)
    {
        return new Session(properties, authenticator1);
    }

    private static InputStream getResourceAsStream(final Class c, final String name)
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

    private static URL[] getResources(final ClassLoader cl, final String name)
    {
        return (URL[])AccessController.doPrivileged(new PrivilegedAction() {

            public Object run()
            {
                URL aurl[] = (URL[])null;
                Vector vector;
                Enumeration enumeration;
                vector = new Vector();
                enumeration = cl.getResources(name);
_L2:
                if(enumeration == null)
                    break MISSING_BLOCK_LABEL_41;
                if(enumeration.hasMoreElements())
                    break MISSING_BLOCK_LABEL_63;
                if(vector.size() <= 0)
                    break MISSING_BLOCK_LABEL_94;
                aurl = new URL[vector.size()];
                vector.copyInto(aurl);
                return aurl;
                URL url = (URL)enumeration.nextElement();
                if(url == null) goto _L2; else goto _L1
_L1:
                vector.addElement(url);
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

    private Object getService(Provider provider, URLName urlname)
        throws NoSuchProviderException
    {
        Class class2;
        if(provider == null)
            throw new NoSuchProviderException("null");
        if(urlname == null)
        {
            String s = provider.getProtocol();
            urlname = new URLName(s, null, -1, null, null, null);
        }
        ClassLoader classloader;
        Class aclass[];
        Object obj;
        ClassLoader classloader1;
        Class class3;
        Class class4;
        if(authenticator != null)
            classloader = authenticator.getClass().getClassLoader();
        else
            classloader = getClass().getClassLoader();
        classloader1 = getContextClassLoader();
        class2 = null;
        if(classloader1 == null)
            break MISSING_BLOCK_LABEL_85;
        class4 = classloader1.loadClass(provider.getClassName());
        class2 = class4;
_L4:
        if(class2 != null)
            break MISSING_BLOCK_LABEL_104;
        class3 = classloader.loadClass(provider.getClassName());
        class2 = class3;
_L2:
        Exception exception;
        Exception exception1;
        Class class1;
        try
        {
            aclass = (new Class[] {
                javax/mail/Session, javax/mail/URLName
            });
            obj = class2.getConstructor(aclass).newInstance(new Object[] {
                this, urlname
            });
        }
        catch(Exception exception2)
        {
            if(debug)
                exception2.printStackTrace(getDebugOut());
            throw new NoSuchProviderException(provider.getProtocol());
        }
        return obj;
        exception;
        try
        {
            class1 = Class.forName(provider.getClassName());
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception1)
        {
            if(debug)
                exception1.printStackTrace(getDebugOut());
            throw new NoSuchProviderException(provider.getProtocol());
        }
        class2 = class1;
        if(true) goto _L2; else goto _L1
_L1:
        ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        class2 = null;
        if(true) goto _L4; else goto _L3
_L3:
    }

    private Store getStore(Provider provider, URLName urlname)
        throws NoSuchProviderException
    {
        if(provider == null || provider.getType() != Provider.Type.STORE)
            throw new NoSuchProviderException("invalid provider");
        Store store;
        try
        {
            store = (Store)getService(provider, urlname);
        }
        catch(ClassCastException classcastexception)
        {
            throw new NoSuchProviderException("incorrect class");
        }
        return store;
    }

    private static URL[] getSystemResources(final String name)
    {
        return (URL[])AccessController.doPrivileged(new PrivilegedAction() {

            public Object run()
            {
                URL aurl[] = (URL[])null;
                Vector vector;
                Enumeration enumeration;
                vector = new Vector();
                enumeration = ClassLoader.getSystemResources(name);
_L2:
                if(enumeration == null)
                    break MISSING_BLOCK_LABEL_37;
                if(enumeration.hasMoreElements())
                    break MISSING_BLOCK_LABEL_59;
                if(vector.size() <= 0)
                    break MISSING_BLOCK_LABEL_90;
                aurl = new URL[vector.size()];
                vector.copyInto(aurl);
                return aurl;
                URL url = (URL)enumeration.nextElement();
                if(url == null) goto _L2; else goto _L1
_L1:
                vector.addElement(url);
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

    private Transport getTransport(Provider provider, URLName urlname)
        throws NoSuchProviderException
    {
        if(provider == null || provider.getType() != Provider.Type.TRANSPORT)
            throw new NoSuchProviderException("invalid provider");
        Transport transport;
        try
        {
            transport = (Transport)getService(provider, urlname);
        }
        catch(ClassCastException classcastexception)
        {
            throw new NoSuchProviderException("incorrect class");
        }
        return transport;
    }

    private void loadAddressMap(Class class1)
    {
        StreamLoader streamloader;
        streamloader = new StreamLoader() {

            public void load(InputStream inputstream)
                throws IOException
            {
                addressMap.load(inputstream);
            }

            final Session this$0;

            
            {
                this$0 = Session.this;
                super();
            }
        };
        loadResource("/META-INF/javamail.default.address.map", class1, streamloader);
        loadAllResources("META-INF/javamail.address.map", class1, streamloader);
        loadFile((new StringBuilder(String.valueOf(System.getProperty("java.home")))).append(File.separator).append("lib").append(File.separator).append("javamail.address.map").toString(), streamloader);
_L2:
        if(addressMap.isEmpty())
        {
            if(debug)
                pr("DEBUG: failed to load address map, using defaults");
            addressMap.put("rfc822", "smtp");
        }
        return;
        SecurityException securityexception;
        securityexception;
        if(debug)
            pr((new StringBuilder("DEBUG: can't get java.home: ")).append(securityexception).toString());
        if(true) goto _L2; else goto _L1
_L1:
    }

    private void loadAllResources(String s, Class class1, StreamLoader streamloader)
    {
        boolean flag = false;
        ClassLoader classloader = getContextClassLoader();
        flag = false;
        if(classloader != null)
            break MISSING_BLOCK_LABEL_22;
        classloader = class1.getClassLoader();
        flag = false;
        if(classloader == null) goto _L2; else goto _L1
_L1:
        URL aurl[] = getResources(classloader, s);
          goto _L3
_L15:
        int j = aurl.length;
        int i;
        if(i < j) goto _L5; else goto _L4
_L4:
        if(!flag)
        {
            if(debug)
                pr("DEBUG: !anyLoaded");
            loadResource((new StringBuilder("/")).append(s).toString(), class1, streamloader);
        }
        return;
_L2:
        aurl = getSystemResources(s);
          goto _L3
_L5:
        URL url = aurl[i];
        InputStream inputstream = null;
        if(debug)
            pr((new StringBuilder("DEBUG: URL ")).append(url).toString());
        inputstream = openStream(url);
        if(inputstream == null) goto _L7; else goto _L6
_L6:
        streamloader.load(inputstream);
        flag = true;
        if(debug)
            pr((new StringBuilder("DEBUG: successfully loaded resource: ")).append(url).toString());
_L9:
        if(inputstream == null)
            break MISSING_BLOCK_LABEL_206;
        inputstream.close();
_L11:
        i++;
        continue; /* Loop/switch isn't completed */
_L7:
        if(!debug) goto _L9; else goto _L8
_L8:
        pr((new StringBuilder("DEBUG: not loading resource: ")).append(url).toString());
          goto _L9
        IOException ioexception2;
        ioexception2;
        if(debug)
            pr((new StringBuilder("DEBUG: ")).append(ioexception2).toString());
        if(inputstream == null) goto _L11; else goto _L10
_L10:
        inputstream.close();
          goto _L11
        IOException ioexception3;
        ioexception3;
          goto _L11
        SecurityException securityexception;
        securityexception;
        if(debug)
            pr((new StringBuilder("DEBUG: ")).append(securityexception).toString());
        if(inputstream == null) goto _L11; else goto _L12
_L12:
        inputstream.close();
          goto _L11
        IOException ioexception1;
        ioexception1;
          goto _L11
        Exception exception1;
        exception1;
        if(inputstream == null)
            break MISSING_BLOCK_LABEL_354;
        Exception exception;
        try
        {
            inputstream.close();
        }
        catch(IOException ioexception) { }
        throw exception1;
        exception;
        if(debug)
            pr((new StringBuilder("DEBUG: ")).append(exception).toString());
          goto _L4
        IOException ioexception4;
        ioexception4;
          goto _L11
_L3:
        flag = false;
        if(aurl == null) goto _L4; else goto _L13
_L13:
        flag = false;
        i = 0;
        if(true) goto _L15; else goto _L14
_L14:
    }

    private void loadFile(String s, StreamLoader streamloader)
    {
        BufferedInputStream bufferedinputstream = null;
        BufferedInputStream bufferedinputstream1 = new BufferedInputStream(new FileInputStream(s));
        streamloader.load(bufferedinputstream1);
        if(debug)
            pr((new StringBuilder("DEBUG: successfully loaded file: ")).append(s).toString());
        if(bufferedinputstream1 == null)
            break MISSING_BLOCK_LABEL_239;
        bufferedinputstream1.close();
_L2:
        return;
        IOException ioexception;
        ioexception;
_L7:
        if(debug)
        {
            pr((new StringBuilder("DEBUG: not loading file: ")).append(s).toString());
            pr((new StringBuilder("DEBUG: ")).append(ioexception).toString());
        }
        if(bufferedinputstream == null) goto _L2; else goto _L1
_L1:
        try
        {
            bufferedinputstream.close();
            return;
        }
        catch(IOException ioexception2)
        {
            return;
        }
        SecurityException securityexception;
        securityexception;
_L6:
        if(debug)
        {
            pr((new StringBuilder("DEBUG: not loading file: ")).append(s).toString());
            pr((new StringBuilder("DEBUG: ")).append(securityexception).toString());
        }
        if(bufferedinputstream == null) goto _L2; else goto _L3
_L3:
        try
        {
            bufferedinputstream.close();
            return;
        }
        catch(IOException ioexception3)
        {
            return;
        }
        Exception exception;
        exception;
_L5:
        IOException ioexception4;
        if(bufferedinputstream != null)
            try
            {
                bufferedinputstream.close();
            }
            catch(IOException ioexception1) { }
        throw exception;
        ioexception4;
        return;
        exception;
        bufferedinputstream = bufferedinputstream1;
        if(true) goto _L5; else goto _L4
_L4:
        securityexception;
        bufferedinputstream = bufferedinputstream1;
          goto _L6
        ioexception;
        bufferedinputstream = bufferedinputstream1;
          goto _L7
    }

    private void loadProviders(Class class1)
    {
        StreamLoader streamloader = new StreamLoader() {

            public void load(InputStream inputstream)
                throws IOException
            {
                loadProvidersFromStream(inputstream);
            }

            final Session this$0;

            
            {
                this$0 = Session.this;
                super();
            }
        };
        loadFile((new StringBuilder(String.valueOf(System.getProperty("java.home")))).append(File.separator).append("lib").append(File.separator).append("javamail.providers").toString(), streamloader);
_L2:
        loadAllResources("META-INF/javamail.providers", class1, streamloader);
        loadResource("/META-INF/javamail.default.providers", class1, streamloader);
        if(providers.size() == 0)
        {
            if(debug)
                pr("DEBUG: failed to load any providers, using defaults");
            addProvider(new Provider(Provider.Type.STORE, "imap", "com.sun.mail.imap.IMAPStore", "Sun Microsystems, Inc.", "1.4.1"));
            addProvider(new Provider(Provider.Type.STORE, "imaps", "com.sun.mail.imap.IMAPSSLStore", "Sun Microsystems, Inc.", "1.4.1"));
            addProvider(new Provider(Provider.Type.STORE, "pop3", "com.sun.mail.pop3.POP3Store", "Sun Microsystems, Inc.", "1.4.1"));
            addProvider(new Provider(Provider.Type.STORE, "pop3s", "com.sun.mail.pop3.POP3SSLStore", "Sun Microsystems, Inc.", "1.4.1"));
            addProvider(new Provider(Provider.Type.TRANSPORT, "smtp", "com.sun.mail.smtp.SMTPTransport", "Sun Microsystems, Inc.", "1.4.1"));
            addProvider(new Provider(Provider.Type.TRANSPORT, "smtps", "com.sun.mail.smtp.SMTPSSLTransport", "Sun Microsystems, Inc.", "1.4.1"));
        }
        if(debug)
        {
            pr("DEBUG: Tables of loaded providers");
            pr((new StringBuilder("DEBUG: Providers Listed By Class Name: ")).append(providersByClassName.toString()).toString());
            pr((new StringBuilder("DEBUG: Providers Listed By Protocol: ")).append(providersByProtocol.toString()).toString());
        }
        return;
        SecurityException securityexception;
        securityexception;
        if(debug)
            pr((new StringBuilder("DEBUG: can't get java.home: ")).append(securityexception).toString());
        if(true) goto _L2; else goto _L1
_L1:
    }

    private void loadProvidersFromStream(InputStream inputstream)
        throws IOException
    {
        if(inputstream == null) goto _L2; else goto _L1
_L1:
        LineInputStream lineinputstream = new LineInputStream(inputstream);
_L5:
        String s = lineinputstream.readLine();
        if(s != null) goto _L3; else goto _L2
_L2:
        return;
_L3:
        if(!s.startsWith("#"))
        {
            Provider.Type type = null;
            String s1 = null;
            String s2 = null;
            String s3 = null;
            String s4 = null;
            StringTokenizer stringtokenizer = new StringTokenizer(s, ";");
            do
            {
                if(!stringtokenizer.hasMoreTokens())
                {
                    String s5;
                    int i;
                    String s6;
                    if(type == null || s1 == null || s2 == null || s1.length() <= 0 || s2.length() <= 0)
                    {
                        if(debug)
                            pr((new StringBuilder("DEBUG: Bad provider entry: ")).append(s).toString());
                    } else
                    {
                        addProvider(new Provider(type, s1, s2, s3, s4));
                    }
                    break;
                }
                s5 = stringtokenizer.nextToken().trim();
                i = s5.indexOf("=");
                if(s5.startsWith("protocol="))
                    s1 = s5.substring(i + 1);
                else
                if(s5.startsWith("type="))
                {
                    s6 = s5.substring(i + 1);
                    if(s6.equalsIgnoreCase("store"))
                        type = Provider.Type.STORE;
                    else
                    if(s6.equalsIgnoreCase("transport"))
                        type = Provider.Type.TRANSPORT;
                } else
                if(s5.startsWith("class="))
                    s2 = s5.substring(i + 1);
                else
                if(s5.startsWith("vendor="))
                    s3 = s5.substring(i + 1);
                else
                if(s5.startsWith("version="))
                    s4 = s5.substring(i + 1);
            } while(true);
        }
        if(true) goto _L5; else goto _L4
_L4:
    }

    private void loadResource(String s, Class class1, StreamLoader streamloader)
    {
        InputStream inputstream = null;
        inputstream = getResourceAsStream(class1, s);
        if(inputstream == null) goto _L2; else goto _L1
_L1:
        streamloader.load(inputstream);
        if(debug)
            pr((new StringBuilder("DEBUG: successfully loaded resource: ")).append(s).toString());
_L6:
        if(inputstream == null)
            break MISSING_BLOCK_LABEL_61;
        inputstream.close();
_L3:
        return;
_L2:
        if(debug)
            pr((new StringBuilder("DEBUG: not loading resource: ")).append(s).toString());
        continue; /* Loop/switch isn't completed */
        IOException ioexception2;
        ioexception2;
        if(debug)
            pr((new StringBuilder("DEBUG: ")).append(ioexception2).toString());
        if(inputstream != null)
            try
            {
                inputstream.close();
                return;
            }
            catch(IOException ioexception3)
            {
                return;
            }
          goto _L3
        SecurityException securityexception;
        securityexception;
        if(debug)
            pr((new StringBuilder("DEBUG: ")).append(securityexception).toString());
        if(inputstream == null) goto _L3; else goto _L4
_L4:
        try
        {
            inputstream.close();
            return;
        }
        catch(IOException ioexception1)
        {
            return;
        }
        Exception exception;
        exception;
        if(inputstream != null)
            try
            {
                inputstream.close();
            }
            catch(IOException ioexception) { }
        throw exception;
        IOException ioexception4;
        ioexception4;
        return;
        if(true) goto _L6; else goto _L5
_L5:
    }

    private static InputStream openStream(final URL url)
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

    private void pr(String s)
    {
        getDebugOut().println(s);
    }

    public void addProvider(Provider provider)
    {
        this;
        JVM INSTR monitorenter ;
        providers.addElement(provider);
        providersByClassName.put(provider.getClassName(), provider);
        if(!providersByProtocol.containsKey(provider.getProtocol()))
            providersByProtocol.put(provider.getProtocol(), provider);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public boolean getDebug()
    {
        this;
        JVM INSTR monitorenter ;
        boolean flag = debug;
        this;
        JVM INSTR monitorexit ;
        return flag;
        Exception exception;
        exception;
        throw exception;
    }

    public PrintStream getDebugOut()
    {
        this;
        JVM INSTR monitorenter ;
        if(out != null) goto _L2; else goto _L1
_L1:
        PrintStream printstream = System.out;
_L4:
        this;
        JVM INSTR monitorexit ;
        return printstream;
_L2:
        printstream = out;
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
        throw exception;
    }

    public Folder getFolder(URLName urlname)
        throws MessagingException
    {
        Store store = getStore(urlname);
        store.connect();
        return store.getFolder(urlname);
    }

    public PasswordAuthentication getPasswordAuthentication(URLName urlname)
    {
        return (PasswordAuthentication)authTable.get(urlname);
    }

    public Properties getProperties()
    {
        return props;
    }

    public String getProperty(String s)
    {
        return props.getProperty(s);
    }

    public Provider getProvider(String s)
        throws NoSuchProviderException
    {
        this;
        JVM INSTR monitorenter ;
        if(s == null)
            break MISSING_BLOCK_LABEL_13;
        if(s.length() > 0)
            break MISSING_BLOCK_LABEL_29;
        throw new NoSuchProviderException("Invalid protocol: null");
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        String s1 = props.getProperty((new StringBuilder("mail.")).append(s).append(".class").toString());
        Provider provider;
        provider = null;
        if(s1 == null)
            break MISSING_BLOCK_LABEL_118;
        if(debug)
            pr((new StringBuilder("DEBUG: mail.")).append(s).append(".class property exists and points to ").append(s1).toString());
        provider = (Provider)providersByClassName.get(s1);
        if(provider == null) goto _L2; else goto _L1
_L1:
        Provider provider2 = provider;
_L4:
        this;
        JVM INSTR monitorexit ;
        return provider2;
_L2:
        Provider provider1 = (Provider)providersByProtocol.get(s);
        if(provider1 != null)
            break MISSING_BLOCK_LABEL_175;
        throw new NoSuchProviderException((new StringBuilder("No provider for ")).append(s).toString());
        if(debug)
            pr((new StringBuilder("DEBUG: getProvider() returning ")).append(provider1.toString()).toString());
        provider2 = provider1;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public Provider[] getProviders()
    {
        this;
        JVM INSTR monitorenter ;
        Provider aprovider[];
        aprovider = new Provider[providers.size()];
        providers.copyInto(aprovider);
        this;
        JVM INSTR monitorexit ;
        return aprovider;
        Exception exception;
        exception;
        throw exception;
    }

    public Store getStore()
        throws NoSuchProviderException
    {
        return getStore(getProperty("mail.store.protocol"));
    }

    public Store getStore(String s)
        throws NoSuchProviderException
    {
        return getStore(new URLName(s, null, -1, null, null, null));
    }

    public Store getStore(Provider provider)
        throws NoSuchProviderException
    {
        return getStore(provider, null);
    }

    public Store getStore(URLName urlname)
        throws NoSuchProviderException
    {
        return getStore(getProvider(urlname.getProtocol()), urlname);
    }

    public Transport getTransport()
        throws NoSuchProviderException
    {
        return getTransport(getProperty("mail.transport.protocol"));
    }

    public Transport getTransport(String s)
        throws NoSuchProviderException
    {
        return getTransport(new URLName(s, null, -1, null, null, null));
    }

    public Transport getTransport(Address address)
        throws NoSuchProviderException
    {
        String s = (String)addressMap.get(address.getType());
        if(s == null)
            throw new NoSuchProviderException((new StringBuilder("No provider for Address type: ")).append(address.getType()).toString());
        else
            return getTransport(s);
    }

    public Transport getTransport(Provider provider)
        throws NoSuchProviderException
    {
        return getTransport(provider, null);
    }

    public Transport getTransport(URLName urlname)
        throws NoSuchProviderException
    {
        return getTransport(getProvider(urlname.getProtocol()), urlname);
    }

    public PasswordAuthentication requestPasswordAuthentication(InetAddress inetaddress, int i, String s, String s1, String s2)
    {
        if(authenticator != null)
            return authenticator.requestPasswordAuthentication(inetaddress, i, s, s1, s2);
        else
            return null;
    }

    public void setDebug(boolean flag)
    {
        this;
        JVM INSTR monitorenter ;
        debug = flag;
        if(!flag)
            break MISSING_BLOCK_LABEL_18;
        pr("DEBUG: setDebug: JavaMail version 1.4.1");
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void setDebugOut(PrintStream printstream)
    {
        this;
        JVM INSTR monitorenter ;
        out = printstream;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void setPasswordAuthentication(URLName urlname, PasswordAuthentication passwordauthentication)
    {
        if(passwordauthentication == null)
        {
            authTable.remove(urlname);
            return;
        } else
        {
            authTable.put(urlname, passwordauthentication);
            return;
        }
    }

    public void setProtocolForAddress(String s, String s1)
    {
        this;
        JVM INSTR monitorenter ;
        if(s1 != null) goto _L2; else goto _L1
_L1:
        addressMap.remove(s);
_L4:
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        addressMap.put(s, s1);
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
        throw exception;
    }

    public void setProvider(Provider provider)
        throws NoSuchProviderException
    {
        this;
        JVM INSTR monitorenter ;
        if(provider != null)
            break MISSING_BLOCK_LABEL_24;
        throw new NoSuchProviderException("Can't set null provider");
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        providersByProtocol.put(provider.getProtocol(), provider);
        props.put((new StringBuilder("mail.")).append(provider.getProtocol()).append(".class").toString(), provider.getClassName());
        this;
        JVM INSTR monitorexit ;
    }

    private static Session defaultSession = null;
    private final Properties addressMap = new Properties();
    private final Hashtable authTable = new Hashtable();
    private final Authenticator authenticator;
    private boolean debug;
    private PrintStream out;
    private final Properties props;
    private final Vector providers = new Vector();
    private final Hashtable providersByClassName = new Hashtable();
    private final Hashtable providersByProtocol = new Hashtable();



}
