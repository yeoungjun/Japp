// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.internet;

import java.util.*;
import javax.mail.Address;

// Referenced classes of package javax.mail.internet:
//            AddressException

public class NewsAddress extends Address
{

    public NewsAddress()
    {
    }

    public NewsAddress(String s)
    {
        this(s, null);
    }

    public NewsAddress(String s, String s1)
    {
        newsgroup = s;
        host = s1;
    }

    public static NewsAddress[] parse(String s)
        throws AddressException
    {
        StringTokenizer stringtokenizer = new StringTokenizer(s, ",");
        Vector vector = new Vector();
        do
        {
            if(!stringtokenizer.hasMoreTokens())
            {
                int i = vector.size();
                NewsAddress anewsaddress[] = new NewsAddress[i];
                if(i > 0)
                    vector.copyInto(anewsaddress);
                return anewsaddress;
            }
            vector.addElement(new NewsAddress(stringtokenizer.nextToken()));
        } while(true);
    }

    public static String toString(Address aaddress[])
    {
        if(aaddress == null || aaddress.length == 0)
            return null;
        StringBuffer stringbuffer = new StringBuffer(((NewsAddress)aaddress[0]).toString());
        int i = 1;
        do
        {
            if(i >= aaddress.length)
                return stringbuffer.toString();
            stringbuffer.append(",").append(((NewsAddress)aaddress[i]).toString());
            i++;
        } while(true);
    }

    public boolean equals(Object obj)
    {
        NewsAddress newsaddress;
        if(obj instanceof NewsAddress)
            if(newsgroup.equals((newsaddress = (NewsAddress)obj).newsgroup) && (host == null && newsaddress.host == null || host != null && newsaddress.host != null && host.equalsIgnoreCase(newsaddress.host)))
                return true;
        return false;
    }

    public String getHost()
    {
        return host;
    }

    public String getNewsgroup()
    {
        return newsgroup;
    }

    public String getType()
    {
        return "news";
    }

    public int hashCode()
    {
        String s = newsgroup;
        int i = 0;
        if(s != null)
            i = 0 + newsgroup.hashCode();
        if(host != null)
            i += host.toLowerCase(Locale.ENGLISH).hashCode();
        return i;
    }

    public void setHost(String s)
    {
        host = s;
    }

    public void setNewsgroup(String s)
    {
        newsgroup = s;
    }

    public String toString()
    {
        return newsgroup;
    }

    private static final long serialVersionUID = 0xc5a91ca0e4341391L;
    protected String host;
    protected String newsgroup;
}
