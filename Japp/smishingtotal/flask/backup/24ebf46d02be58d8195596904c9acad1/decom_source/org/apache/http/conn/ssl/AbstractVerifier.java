// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.conn.ssl;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.*;
import javax.net.ssl.*;
import javax.security.auth.x500.X500Principal;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.util.InetAddressUtils;

// Referenced classes of package org.apache.http.conn.ssl:
//            X509HostnameVerifier

public abstract class AbstractVerifier
    implements X509HostnameVerifier
{

    public AbstractVerifier()
    {
    }

    public static boolean acceptableCountryWildcard(String s)
    {
        for(String as[] = s.split("\\."); as.length != 3 || as[2].length() != 2 || Arrays.binarySearch(BAD_COUNTRY_2LDS, as[1]) < 0;)
            return true;

        return false;
    }

    public static int countDots(String s)
    {
        int i = 0;
        for(int j = 0; j < s.length(); j++)
            if(s.charAt(j) == '.')
                i++;

        return i;
    }

    public static String[] getCNs(X509Certificate x509certificate)
    {
        LinkedList linkedlist = new LinkedList();
        StringTokenizer stringtokenizer = new StringTokenizer(x509certificate.getSubjectX500Principal().toString(), ",+");
        do
        {
            if(!stringtokenizer.hasMoreTokens())
                break;
            String s = stringtokenizer.nextToken().trim();
            if(s.length() > 3 && s.substring(0, 3).equalsIgnoreCase("CN="))
                linkedlist.add(s.substring(3));
        } while(true);
        if(!linkedlist.isEmpty())
        {
            String as[] = new String[linkedlist.size()];
            linkedlist.toArray(as);
            return as;
        } else
        {
            return null;
        }
    }

    public static String[] getDNSSubjectAlts(X509Certificate x509certificate)
    {
        return getSubjectAlts(x509certificate, null);
    }

    private static String[] getSubjectAlts(X509Certificate x509certificate, String s)
    {
        LinkedList linkedlist;
        Collection collection;
        byte byte0;
        Iterator iterator;
        List list;
        Collection collection1;
        if(isIPAddress(s))
            byte0 = 7;
        else
            byte0 = 2;
        linkedlist = new LinkedList();
        collection1 = x509certificate.getSubjectAlternativeNames();
        collection = collection1;
_L2:
        if(collection != null)
        {
            iterator = collection.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                list = (List)iterator.next();
                if(((Integer)list.get(0)).intValue() == byte0)
                    linkedlist.add((String)list.get(1));
            } while(true);
        }
        if(!linkedlist.isEmpty())
        {
            String as[] = new String[linkedlist.size()];
            linkedlist.toArray(as);
            return as;
        } else
        {
            return null;
        }
        CertificateParsingException certificateparsingexception;
        certificateparsingexception;
        collection = null;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private static boolean isIPAddress(String s)
    {
        return s != null && (InetAddressUtils.isIPv4Address(s) || InetAddressUtils.isIPv6Address(s));
    }

    private String normaliseIPv6Address(String s)
    {
        if(s == null || !InetAddressUtils.isIPv6Address(s))
            return s;
        String s1;
        try
        {
            s1 = InetAddress.getByName(s).getHostAddress();
        }
        catch(UnknownHostException unknownhostexception)
        {
            log.error((new StringBuilder()).append("Unexpected error converting ").append(s).toString(), unknownhostexception);
            return s;
        }
        return s1;
    }

    public final void verify(String s, X509Certificate x509certificate)
        throws SSLException
    {
        verify(s, getCNs(x509certificate), getSubjectAlts(x509certificate, s));
    }

    public final void verify(String s, SSLSocket sslsocket)
        throws IOException
    {
        if(s == null)
            throw new NullPointerException("host to verify is null");
        SSLSession sslsession = sslsocket.getSession();
        if(sslsession == null)
        {
            sslsocket.getInputStream().available();
            sslsession = sslsocket.getSession();
            if(sslsession == null)
            {
                sslsocket.startHandshake();
                sslsession = sslsocket.getSession();
            }
        }
        verify(s, (X509Certificate)sslsession.getPeerCertificates()[0]);
    }

    public final void verify(String s, String as[], String as1[], boolean flag)
        throws SSLException
    {
        LinkedList linkedlist = new LinkedList();
        if(as != null && as.length > 0 && as[0] != null)
            linkedlist.add(as[0]);
        if(as1 != null)
        {
            int i = as1.length;
            for(int j = 0; j < i; j++)
            {
                String s8 = as1[j];
                if(s8 != null)
                    linkedlist.add(s8);
            }

        }
        if(linkedlist.isEmpty())
        {
            String s7 = (new StringBuilder()).append("Certificate for <").append(s).append("> doesn't contain CN or DNS subjectAlt").toString();
            SSLException sslexception = new SSLException(s7);
            throw sslexception;
        }
        StringBuilder stringbuilder = new StringBuilder();
        String s1 = normaliseIPv6Address(s.trim().toLowerCase(Locale.US));
        boolean flag1 = false;
        Iterator iterator = linkedlist.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            String s2 = ((String)iterator.next()).toLowerCase(Locale.US);
            stringbuilder.append(" <");
            stringbuilder.append(s2);
            stringbuilder.append('>');
            if(iterator.hasNext())
                stringbuilder.append(" OR");
            String as2[] = s2.split("\\.");
            boolean flag2;
            if(as2.length >= 3 && as2[0].endsWith("*") && acceptableCountryWildcard(s2) && !isIPAddress(s))
                flag2 = true;
            else
                flag2 = false;
            if(flag2)
            {
                String s3 = as2[0];
                if(s3.length() > 1)
                {
                    String s4 = s3.substring(0, -1 + s3.length());
                    String s5 = s2.substring(s3.length());
                    String s6 = s1.substring(s4.length());
                    if(s1.startsWith(s4) && s6.endsWith(s5))
                        flag1 = true;
                    else
                        flag1 = false;
                } else
                {
                    flag1 = s1.endsWith(s2.substring(1));
                }
                if(flag1 && flag)
                    if(countDots(s1) == countDots(s2))
                        flag1 = true;
                    else
                        flag1 = false;
            } else
            {
                flag1 = s1.equals(normaliseIPv6Address(s2));
            }
        } while(!flag1);
        if(!flag1)
            throw new SSLException((new StringBuilder()).append("hostname in certificate didn't match: <").append(s).append("> !=").append(stringbuilder).toString());
        else
            return;
    }

    public final boolean verify(String s, SSLSession sslsession)
    {
        try
        {
            verify(s, (X509Certificate)sslsession.getPeerCertificates()[0]);
        }
        catch(SSLException sslexception)
        {
            return false;
        }
        return true;
    }

    private static final String BAD_COUNTRY_2LDS[] = {
        "ac", "co", "com", "ed", "edu", "go", "gouv", "gov", "info", "lg", 
        "ne", "net", "or", "org"
    };
    private final Log log = LogFactory.getLog(getClass());

    static 
    {
        Arrays.sort(BAD_COUNTRY_2LDS);
    }
}
