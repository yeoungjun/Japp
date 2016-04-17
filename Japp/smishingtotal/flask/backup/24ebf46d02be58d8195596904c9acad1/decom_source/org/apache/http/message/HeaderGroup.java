// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.message;

import java.io.Serializable;
import java.util.*;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.util.CharArrayBuffer;

// Referenced classes of package org.apache.http.message:
//            BasicHeader, BasicListHeaderIterator

public class HeaderGroup
    implements Cloneable, Serializable
{

    public HeaderGroup()
    {
    }

    public void addHeader(Header header)
    {
        if(header == null)
        {
            return;
        } else
        {
            headers.add(header);
            return;
        }
    }

    public void clear()
    {
        headers.clear();
    }

    public Object clone()
        throws CloneNotSupportedException
    {
        return super.clone();
    }

    public boolean containsHeader(String s)
    {
        for(Iterator iterator1 = headers.iterator(); iterator1.hasNext();)
            if(((Header)iterator1.next()).getName().equalsIgnoreCase(s))
                return true;

        return false;
    }

    public HeaderGroup copy()
    {
        HeaderGroup headergroup = new HeaderGroup();
        headergroup.headers.addAll(headers);
        return headergroup;
    }

    public Header[] getAllHeaders()
    {
        return (Header[])headers.toArray(new Header[headers.size()]);
    }

    public Header getCondensedHeader(String s)
    {
        Header aheader[] = getHeaders(s);
        if(aheader.length == 0)
            return null;
        if(aheader.length == 1)
            return aheader[0];
        CharArrayBuffer chararraybuffer = new CharArrayBuffer(128);
        chararraybuffer.append(aheader[0].getValue());
        for(int i = 1; i < aheader.length; i++)
        {
            chararraybuffer.append(", ");
            chararraybuffer.append(aheader[i].getValue());
        }

        return new BasicHeader(s.toLowerCase(Locale.ENGLISH), chararraybuffer.toString());
    }

    public Header getFirstHeader(String s)
    {
        for(Iterator iterator1 = headers.iterator(); iterator1.hasNext();)
        {
            Header header = (Header)iterator1.next();
            if(header.getName().equalsIgnoreCase(s))
                return header;
        }

        return null;
    }

    public Header[] getHeaders(String s)
    {
        ArrayList arraylist = new ArrayList();
        Iterator iterator1 = headers.iterator();
        do
        {
            if(!iterator1.hasNext())
                break;
            Header header = (Header)iterator1.next();
            if(header.getName().equalsIgnoreCase(s))
                arraylist.add(header);
        } while(true);
        return (Header[])arraylist.toArray(new Header[arraylist.size()]);
    }

    public Header getLastHeader(String s)
    {
        for(int i = -1 + headers.size(); i >= 0; i--)
        {
            Header header = (Header)headers.get(i);
            if(header.getName().equalsIgnoreCase(s))
                return header;
        }

        return null;
    }

    public HeaderIterator iterator()
    {
        return new BasicListHeaderIterator(headers, null);
    }

    public HeaderIterator iterator(String s)
    {
        return new BasicListHeaderIterator(headers, s);
    }

    public void removeHeader(Header header)
    {
        if(header == null)
        {
            return;
        } else
        {
            headers.remove(header);
            return;
        }
    }

    public void setHeaders(Header aheader[])
    {
        clear();
        if(aheader == null)
        {
            return;
        } else
        {
            Collections.addAll(headers, aheader);
            return;
        }
    }

    public String toString()
    {
        return headers.toString();
    }

    public void updateHeader(Header header)
    {
        if(header == null)
            return;
        for(int i = 0; i < headers.size(); i++)
            if(((Header)headers.get(i)).getName().equalsIgnoreCase(header.getName()))
            {
                headers.set(i, header);
                return;
            }

        headers.add(header);
    }

    private static final long serialVersionUID = 0x243470d8cecee2c1L;
    private final List headers = new ArrayList(16);
}
