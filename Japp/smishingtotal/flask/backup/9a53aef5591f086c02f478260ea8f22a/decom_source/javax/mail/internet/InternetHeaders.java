// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.internet;

import com.sun.mail.util.LineInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import javax.mail.Header;
import javax.mail.MessagingException;

public class InternetHeaders
{
    protected static final class InternetHeader extends Header
    {

        public String getValue()
        {
            int j;
            int i = line.indexOf(':');
            if(i < 0)
                return line;
            j = i + 1;
_L5:
            if(j < line.length()) goto _L2; else goto _L1
_L1:
            char c;
            return line.substring(j);
_L2:
            if((c = line.charAt(j)) != ' ' && c != '\t' && c != '\r' && c != '\n') goto _L1; else goto _L3
_L3:
            j++;
            if(true) goto _L5; else goto _L4
_L4:
        }

        String line;

        public InternetHeader(String s)
        {
            super("", "");
            int i = s.indexOf(':');
            if(i < 0)
                name = s.trim();
            else
                name = s.substring(0, i).trim();
            line = s;
        }

        public InternetHeader(String s, String s1)
        {
            super(s, "");
            if(s1 != null)
            {
                line = (new StringBuilder(String.valueOf(s))).append(": ").append(s1).toString();
                return;
            } else
            {
                line = null;
                return;
            }
        }
    }

    static class matchEnum
        implements Enumeration
    {

        private InternetHeader nextMatch()
        {
_L6:
            if(e.hasNext()) goto _L2; else goto _L1
_L1:
            InternetHeader internetheader = null;
_L4:
            return internetheader;
_L2:
            internetheader = (InternetHeader)e.next();
            if(internetheader.line == null)
                continue; /* Loop/switch isn't completed */
            if(names != null)
                break; /* Loop/switch isn't completed */
            if(match)
                return null;
            if(true) goto _L4; else goto _L3
_L3:
            int i = 0;
_L7:
            if(i >= names.length)
            {
                if(!match)
                    return internetheader;
            } else
            {
label0:
                {
                    if(!names[i].equalsIgnoreCase(internetheader.getName()))
                        break label0;
                    if(match)
                        return internetheader;
                }
            }
            if(true) goto _L6; else goto _L5
_L5:
            i++;
              goto _L7
            if(true) goto _L6; else goto _L8
_L8:
        }

        public boolean hasMoreElements()
        {
            if(next_header == null)
                next_header = nextMatch();
            return next_header != null;
        }

        public Object nextElement()
        {
            if(next_header == null)
                next_header = nextMatch();
            if(next_header == null)
                throw new NoSuchElementException("No more headers");
            InternetHeader internetheader = next_header;
            next_header = null;
            if(want_line)
                return internetheader.line;
            else
                return new Header(internetheader.getName(), internetheader.getValue());
        }

        private Iterator e;
        private boolean match;
        private String names[];
        private InternetHeader next_header;
        private boolean want_line;

        matchEnum(List list, String as[], boolean flag, boolean flag1)
        {
            e = list.iterator();
            names = as;
            match = flag;
            want_line = flag1;
            next_header = null;
        }
    }


    public InternetHeaders()
    {
        headers = new ArrayList(40);
        headers.add(new InternetHeader("Return-Path", null));
        headers.add(new InternetHeader("Received", null));
        headers.add(new InternetHeader("Resent-Date", null));
        headers.add(new InternetHeader("Resent-From", null));
        headers.add(new InternetHeader("Resent-Sender", null));
        headers.add(new InternetHeader("Resent-To", null));
        headers.add(new InternetHeader("Resent-Cc", null));
        headers.add(new InternetHeader("Resent-Bcc", null));
        headers.add(new InternetHeader("Resent-Message-Id", null));
        headers.add(new InternetHeader("Date", null));
        headers.add(new InternetHeader("From", null));
        headers.add(new InternetHeader("Sender", null));
        headers.add(new InternetHeader("Reply-To", null));
        headers.add(new InternetHeader("To", null));
        headers.add(new InternetHeader("Cc", null));
        headers.add(new InternetHeader("Bcc", null));
        headers.add(new InternetHeader("Message-Id", null));
        headers.add(new InternetHeader("In-Reply-To", null));
        headers.add(new InternetHeader("References", null));
        headers.add(new InternetHeader("Subject", null));
        headers.add(new InternetHeader("Comments", null));
        headers.add(new InternetHeader("Keywords", null));
        headers.add(new InternetHeader("Errors-To", null));
        headers.add(new InternetHeader("MIME-Version", null));
        headers.add(new InternetHeader("Content-Type", null));
        headers.add(new InternetHeader("Content-Transfer-Encoding", null));
        headers.add(new InternetHeader("Content-MD5", null));
        headers.add(new InternetHeader(":", null));
        headers.add(new InternetHeader("Content-Length", null));
        headers.add(new InternetHeader("Status", null));
    }

    public InternetHeaders(InputStream inputstream)
        throws MessagingException
    {
        headers = new ArrayList(40);
        load(inputstream);
    }

    public void addHeader(String s, String s1)
    {
        int i = headers.size();
        boolean flag;
        int j;
        if(!s.equalsIgnoreCase("Received") && !s.equalsIgnoreCase("Return-Path"))
            flag = false;
        else
            flag = true;
        if(flag)
            i = 0;
        j = -1 + headers.size();
        do
        {
            if(j < 0)
            {
                headers.add(i, new InternetHeader(s, s1));
                return;
            }
            InternetHeader internetheader = (InternetHeader)headers.get(j);
            if(s.equalsIgnoreCase(internetheader.getName()))
            {
                if(!flag)
                    break;
                i = j;
            }
            if(internetheader.getName().equals(":"))
                i = j;
            j--;
        } while(true);
        headers.add(j + 1, new InternetHeader(s, s1));
    }

    public void addHeaderLine(String s)
    {
        char c;
        InternetHeader internetheader;
        try
        {
            c = s.charAt(0);
        }
        catch(StringIndexOutOfBoundsException stringindexoutofboundsexception)
        {
            return;
        }
        catch(NoSuchElementException nosuchelementexception)
        {
            return;
        }
        if(c != ' ' && c != '\t')
            break MISSING_BLOCK_LABEL_79;
        internetheader = (InternetHeader)headers.get(-1 + headers.size());
        internetheader.line = (new StringBuilder(String.valueOf(internetheader.line))).append("\r\n").append(s).toString();
        return;
        headers.add(new InternetHeader(s));
        return;
    }

    public Enumeration getAllHeaderLines()
    {
        return getNonMatchingHeaderLines(null);
    }

    public Enumeration getAllHeaders()
    {
        return new matchEnum(headers, null, false, false);
    }

    public String getHeader(String s, String s1)
    {
        String as[] = getHeader(s);
        if(as == null)
            return null;
        if(as.length == 1 || s1 == null)
            return as[0];
        StringBuffer stringbuffer = new StringBuffer(as[0]);
        int i = 1;
        do
        {
            if(i >= as.length)
                return stringbuffer.toString();
            stringbuffer.append(s1);
            stringbuffer.append(as[i]);
            i++;
        } while(true);
    }

    public String[] getHeader(String s)
    {
        Iterator iterator = headers.iterator();
        ArrayList arraylist = new ArrayList();
        do
        {
            do
            {
                InternetHeader internetheader;
                if(!iterator.hasNext())
                    if(arraylist.size() == 0)
                        return null;
                    else
                        return (String[])arraylist.toArray(new String[arraylist.size()]);
                internetheader = (InternetHeader)iterator.next();
            } while(!s.equalsIgnoreCase(internetheader.getName()) || internetheader.line == null);
            arraylist.add(internetheader.getValue());
        } while(true);
    }

    public Enumeration getMatchingHeaderLines(String as[])
    {
        return new matchEnum(headers, as, true, true);
    }

    public Enumeration getMatchingHeaders(String as[])
    {
        return new matchEnum(headers, as, true, false);
    }

    public Enumeration getNonMatchingHeaderLines(String as[])
    {
        return new matchEnum(headers, as, false, true);
    }

    public Enumeration getNonMatchingHeaders(String as[])
    {
        return new matchEnum(headers, as, false, false);
    }

    public void load(InputStream inputstream)
        throws MessagingException
    {
        LineInputStream lineinputstream;
        String s;
        StringBuffer stringbuffer;
        lineinputstream = new LineInputStream(inputstream);
        s = null;
        stringbuffer = new StringBuffer();
_L7:
        String s1;
        try
        {
            s1 = lineinputstream.readLine();
        }
        catch(IOException ioexception)
        {
            throw new MessagingException("Error in input stream", ioexception);
        }
        if(s1 == null) goto _L2; else goto _L1
_L1:
        if(!s1.startsWith(" ") && !s1.startsWith("\t")) goto _L2; else goto _L3
_L3:
        if(s == null)
            break MISSING_BLOCK_LABEL_64;
        stringbuffer.append(s);
        s = null;
        stringbuffer.append("\r\n");
        stringbuffer.append(s1);
_L8:
        if(s1 == null) goto _L5; else goto _L4
_L4:
        if(s1.length() > 0) goto _L7; else goto _L6
_L6:
        return;
_L2:
        if(s == null)
            break MISSING_BLOCK_LABEL_106;
        addHeaderLine(s);
        break MISSING_BLOCK_LABEL_147;
        if(stringbuffer.length() > 0)
        {
            addHeaderLine(stringbuffer.toString());
            stringbuffer.setLength(0);
        }
        break MISSING_BLOCK_LABEL_147;
_L5:
        return;
        s = s1;
          goto _L8
    }

    public void removeHeader(String s)
    {
        int i = 0;
        do
        {
            if(i >= headers.size())
                return;
            InternetHeader internetheader = (InternetHeader)headers.get(i);
            if(s.equalsIgnoreCase(internetheader.getName()))
                internetheader.line = null;
            i++;
        } while(true);
    }

    public void setHeader(String s, String s1)
    {
        boolean flag;
        int i;
        flag = false;
        i = 0;
_L6:
        InternetHeader internetheader;
        if(i >= headers.size())
        {
            if(!flag)
                addHeader(s, s1);
            return;
        }
        internetheader = (InternetHeader)headers.get(i);
        if(!s.equalsIgnoreCase(internetheader.getName())) goto _L2; else goto _L1
_L1:
        if(flag)
            break MISSING_BLOCK_LABEL_166;
        if(internetheader.line == null) goto _L4; else goto _L3
_L3:
        int j = internetheader.line.indexOf(':');
        if(j < 0) goto _L4; else goto _L5
_L5:
        internetheader.line = (new StringBuilder(String.valueOf(internetheader.line.substring(0, j + 1)))).append(" ").append(s1).toString();
_L7:
        flag = true;
_L2:
        i++;
          goto _L6
_L4:
        internetheader.line = (new StringBuilder(String.valueOf(s))).append(": ").append(s1).toString();
          goto _L7
        headers.remove(i);
        i--;
          goto _L2
    }

    protected List headers;
}
