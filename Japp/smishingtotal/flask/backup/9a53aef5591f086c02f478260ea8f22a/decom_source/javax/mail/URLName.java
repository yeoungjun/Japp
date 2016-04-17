// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail;

import java.io.*;
import java.net.*;
import java.util.BitSet;
import java.util.Locale;

public class URLName
{

    public URLName(String s)
    {
        hostAddressKnown = false;
        port = -1;
        hashCode = 0;
        parseString(s);
    }

    public URLName(String s, String s1, int i, String s2, String s3, String s4)
    {
        hostAddressKnown = false;
        port = -1;
        hashCode = 0;
        protocol = s;
        host = s1;
        port = i;
        if(s2 == null) goto _L2; else goto _L1
_L1:
        int j = s2.indexOf('#');
        if(j == -1) goto _L2; else goto _L3
_L3:
        file = s2.substring(0, j);
        ref = s2.substring(j + 1);
_L5:
        if(doEncode)
            s3 = encode(s3);
        username = s3;
        if(doEncode)
            s4 = encode(s4);
        password = s4;
        return;
_L2:
        file = s2;
        ref = null;
        if(true) goto _L5; else goto _L4
_L4:
    }

    public URLName(URL url)
    {
        this(url.toString());
    }

    private static String _encode(String s)
    {
        StringBuffer stringbuffer;
        ByteArrayOutputStream bytearrayoutputstream;
        OutputStreamWriter outputstreamwriter;
        int i;
        stringbuffer = new StringBuffer(s.length());
        bytearrayoutputstream = new ByteArrayOutputStream(10);
        outputstreamwriter = new OutputStreamWriter(bytearrayoutputstream);
        i = 0;
_L2:
        char c;
        if(i >= s.length())
            return stringbuffer.toString();
        c = s.charAt(i);
        if(!dontNeedEncoding.get(c))
            break; /* Loop/switch isn't completed */
        if(c == ' ')
            c = '+';
        stringbuffer.append(c);
_L3:
        i++;
        if(true) goto _L2; else goto _L1
_L1:
        outputstreamwriter.write(c);
        outputstreamwriter.flush();
        byte abyte0[];
        int j;
        abyte0 = bytearrayoutputstream.toByteArray();
        j = 0;
_L4:
        if(j < abyte0.length)
            break MISSING_BLOCK_LABEL_134;
        bytearrayoutputstream.reset();
          goto _L3
        IOException ioexception;
        ioexception;
        bytearrayoutputstream.reset();
          goto _L3
        stringbuffer.append('%');
        char c1 = Character.forDigit(0xf & abyte0[j] >> 4, 16);
        if(Character.isLetter(c1))
            c1 -= ' ';
        stringbuffer.append(c1);
        char c2 = Character.forDigit(0xf & abyte0[j], 16);
        if(Character.isLetter(c2))
            c2 -= ' ';
        stringbuffer.append(c2);
        j++;
          goto _L4
    }

    static String decode(String s)
    {
        if(s != null) goto _L2; else goto _L1
_L1:
        s = null;
_L4:
        return s;
_L2:
        if(indexOfAny(s, "+%") == -1) goto _L4; else goto _L3
_L3:
        StringBuffer stringbuffer;
        int i;
        stringbuffer = new StringBuffer();
        i = 0;
_L10:
        if(i < s.length()) goto _L6; else goto _L5
_L5:
        String s1 = stringbuffer.toString();
        String s2 = new String(s1.getBytes("8859_1"));
        s1 = s2;
_L12:
        return s1;
_L6:
        char c = s.charAt(i);
        c;
        JVM INSTR lookupswitch 2: default 100
    //                   37: 122
    //                   43: 112;
           goto _L7 _L8 _L9
_L8:
        break MISSING_BLOCK_LABEL_122;
_L7:
        stringbuffer.append(c);
_L11:
        i++;
          goto _L10
_L9:
        stringbuffer.append(' ');
          goto _L11
        int j = i + 1;
        int k = i + 3;
        try
        {
            stringbuffer.append((char)Integer.parseInt(s.substring(j, k), 16));
        }
        catch(NumberFormatException numberformatexception)
        {
            throw new IllegalArgumentException();
        }
        i += 2;
          goto _L11
        UnsupportedEncodingException unsupportedencodingexception;
        unsupportedencodingexception;
          goto _L12
    }

    static String encode(String s)
    {
        if(s == null)
        {
            s = null;
        } else
        {
            int i = 0;
            while(i < s.length()) 
            {
                char c = s.charAt(i);
                if(c == ' ' || !dontNeedEncoding.get(c))
                    return _encode(s);
                i++;
            }
        }
        return s;
    }

    private InetAddress getHostAddress()
    {
        this;
        JVM INSTR monitorenter ;
        if(!hostAddressKnown) goto _L2; else goto _L1
_L1:
        InetAddress inetaddress = hostAddress;
_L4:
        this;
        JVM INSTR monitorexit ;
        return inetaddress;
_L2:
        String s = host;
        inetaddress = null;
        if(s == null) goto _L4; else goto _L3
_L3:
        hostAddress = InetAddress.getByName(host);
_L5:
        hostAddressKnown = true;
        inetaddress = hostAddress;
          goto _L4
        UnknownHostException unknownhostexception;
        unknownhostexception;
        hostAddress = null;
          goto _L5
        Exception exception;
        exception;
        throw exception;
    }

    private static int indexOfAny(String s, String s1)
    {
        return indexOfAny(s, s1, 0);
    }

    private static int indexOfAny(String s, String s1, int i)
    {
        int j;
        int k;
        int l;
        try
        {
            j = s.length();
        }
        catch(StringIndexOutOfBoundsException stringindexoutofboundsexception)
        {
            return -1;
        }
        k = i;
          goto _L1
_L3:
        l = s1.indexOf(s.charAt(k));
        if(l >= 0)
            break MISSING_BLOCK_LABEL_48;
        k++;
_L1:
        if(k < j) goto _L3; else goto _L2
_L2:
        k = -1;
        return k;
    }

    public boolean equals(Object obj)
    {
        if(obj instanceof URLName) goto _L2; else goto _L1
_L1:
        URLName urlname;
        return false;
_L2:
        if((urlname = (URLName)obj).protocol == null || !urlname.protocol.equals(protocol)) goto _L4; else goto _L3
_L3:
        InetAddress inetaddress;
        InetAddress inetaddress1;
        inetaddress = getHostAddress();
        inetaddress1 = urlname.getHostAddress();
        if(inetaddress == null || inetaddress1 == null) goto _L6; else goto _L5
_L5:
        if(!inetaddress.equals(inetaddress1)) goto _L4; else goto _L7
_L7:
        if(username == urlname.username || username != null && username.equals(urlname.username))
        {
            String s;
            String s1;
            if(file == null)
                s = "";
            else
                s = file;
            if(urlname.file == null)
                s1 = "";
            else
                s1 = urlname.file;
            if(s.equals(s1) && port == urlname.port)
                return true;
        }
_L4:
        if(true) goto _L1; else goto _L6
_L6:
        if(host == null || urlname.host == null)
            continue; /* Loop/switch isn't completed */
        if(host.equalsIgnoreCase(urlname.host)) goto _L7; else goto _L8
_L8:
        return false;
        if(host == urlname.host) goto _L7; else goto _L9
_L9:
        return false;
    }

    public String getFile()
    {
        return file;
    }

    public String getHost()
    {
        return host;
    }

    public String getPassword()
    {
        if(doEncode)
            return decode(password);
        else
            return password;
    }

    public int getPort()
    {
        return port;
    }

    public String getProtocol()
    {
        return protocol;
    }

    public String getRef()
    {
        return ref;
    }

    public URL getURL()
        throws MalformedURLException
    {
        return new URL(getProtocol(), getHost(), getPort(), getFile());
    }

    public String getUsername()
    {
        if(doEncode)
            return decode(username);
        else
            return username;
    }

    public int hashCode()
    {
        InetAddress inetaddress;
        if(hashCode != 0)
            return hashCode;
        if(protocol != null)
            hashCode = hashCode + protocol.hashCode();
        inetaddress = getHostAddress();
        if(inetaddress == null) goto _L2; else goto _L1
_L1:
        hashCode = hashCode + inetaddress.hashCode();
_L4:
        if(username != null)
            hashCode = hashCode + username.hashCode();
        if(file != null)
            hashCode = hashCode + file.hashCode();
        hashCode = hashCode + port;
        return hashCode;
_L2:
        if(host != null)
            hashCode = hashCode + host.toLowerCase(Locale.ENGLISH).hashCode();
        if(true) goto _L4; else goto _L3
_L3:
    }

    protected void parseString(String s)
    {
        int i;
        int j;
        password = null;
        username = null;
        host = null;
        ref = null;
        file = null;
        protocol = null;
        port = -1;
        i = s.length();
        j = s.indexOf(':');
        if(j != -1)
            protocol = s.substring(0, j);
        if(!s.regionMatches(j + 1, "//", 0, 2)) goto _L2; else goto _L1
_L1:
        int l = s.indexOf('/', j + 3);
        String s1;
        int j1;
        if(l != -1)
        {
            s1 = s.substring(j + 3, l);
            int k;
            int i1;
            String s2;
            int k1;
            if(l + 1 < i)
                file = s.substring(l + 1);
            else
                file = "";
        } else
        {
            s1 = s.substring(j + 3);
        }
        i1 = s1.indexOf('@');
        if(i1 != -1)
        {
            String s3 = s1.substring(0, i1);
            s1 = s1.substring(i1 + 1);
            k1 = s3.indexOf(':');
            if(k1 != -1)
            {
                username = s3.substring(0, k1);
                password = s3.substring(k1 + 1);
            } else
            {
                username = s3;
            }
        }
        if(s1.length() > 0 && s1.charAt(0) == '[')
            j1 = s1.indexOf(':', s1.indexOf(']'));
        else
            j1 = s1.indexOf(':');
        if(j1 != -1)
        {
            s2 = s1.substring(j1 + 1);
            if(s2.length() > 0)
                try
                {
                    port = Integer.parseInt(s2);
                }
                catch(NumberFormatException numberformatexception)
                {
                    port = -1;
                }
            host = s1.substring(0, j1);
        } else
        {
            host = s1;
        }
_L4:
        if(file != null)
        {
            k = file.indexOf('#');
            if(k != -1)
            {
                ref = file.substring(k + 1);
                file = file.substring(0, k);
            }
        }
        return;
_L2:
        if(j + 1 < i)
            file = s.substring(j + 1);
        if(true) goto _L4; else goto _L3
_L3:
    }

    public String toString()
    {
        if(fullURL == null)
        {
            StringBuffer stringbuffer = new StringBuffer();
            if(protocol != null)
            {
                stringbuffer.append(protocol);
                stringbuffer.append(":");
            }
            if(username != null || host != null)
            {
                stringbuffer.append("//");
                if(username != null)
                {
                    stringbuffer.append(username);
                    if(password != null)
                    {
                        stringbuffer.append(":");
                        stringbuffer.append(password);
                    }
                    stringbuffer.append("@");
                }
                if(host != null)
                    stringbuffer.append(host);
                if(port != -1)
                {
                    stringbuffer.append(":");
                    stringbuffer.append(Integer.toString(port));
                }
                if(file != null)
                    stringbuffer.append("/");
            }
            if(file != null)
                stringbuffer.append(file);
            if(ref != null)
            {
                stringbuffer.append("#");
                stringbuffer.append(ref);
            }
            fullURL = stringbuffer.toString();
        }
        return fullURL;
    }

    static final int caseDiff = 32;
    private static boolean doEncode;
    static BitSet dontNeedEncoding;
    private String file;
    protected String fullURL;
    private int hashCode;
    private String host;
    private InetAddress hostAddress;
    private boolean hostAddressKnown;
    private String password;
    private int port;
    private String protocol;
    private String ref;
    private String username;

    static 
    {
        boolean flag;
        flag = true;
        doEncode = flag;
        if(Boolean.getBoolean("mail.URLName.dontencode"))
            flag = false;
        int i;
        int j;
        int k;
        try
        {
            doEncode = flag;
        }
        catch(Exception exception) { }
        dontNeedEncoding = new BitSet(256);
        i = 97;
        if(i <= 122) goto _L2; else goto _L1
_L1:
        j = 65;
_L5:
        if(j <= 90) goto _L4; else goto _L3
_L3:
        k = 48;
_L6:
        if(k > 57)
        {
            dontNeedEncoding.set(32);
            dontNeedEncoding.set(45);
            dontNeedEncoding.set(95);
            dontNeedEncoding.set(46);
            dontNeedEncoding.set(42);
            return;
        }
        break MISSING_BLOCK_LABEL_129;
_L2:
        dontNeedEncoding.set(i);
        i++;
        break MISSING_BLOCK_LABEL_36;
_L4:
        dontNeedEncoding.set(j);
        j++;
          goto _L5
        dontNeedEncoding.set(k);
        k++;
          goto _L6
    }
}
