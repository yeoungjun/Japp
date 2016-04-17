// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.activation;

import java.util.*;

// Referenced classes of package javax.activation:
//            MimeTypeParseException

public class MimeTypeParameterList
{

    public MimeTypeParameterList()
    {
        parameters = new Hashtable();
    }

    public MimeTypeParameterList(String s)
        throws MimeTypeParseException
    {
        parameters = new Hashtable();
        parse(s);
    }

    private static boolean isTokenChar(char c)
    {
        return c > ' ' && c < '\177' && "()<>@,;:/[]?=\\\"".indexOf(c) < 0;
    }

    private static String quote(String s)
    {
        boolean flag;
        int i;
        int j;
        flag = false;
        i = s.length();
        j = 0;
_L5:
        if(j < i && !flag) goto _L2; else goto _L1
_L1:
        if(!flag) goto _L4; else goto _L3
_L3:
        StringBuffer stringbuffer;
        int k;
        stringbuffer = new StringBuffer();
        stringbuffer.ensureCapacity((int)(1.5D * (double)i));
        stringbuffer.append('"');
        k = 0;
_L6:
        if(k < i)
            break MISSING_BLOCK_LABEL_100;
        stringbuffer.append('"');
        s = stringbuffer.toString();
_L4:
        return s;
_L2:
        if(isTokenChar(s.charAt(j)))
            flag = false;
        else
            flag = true;
        j++;
          goto _L5
        char c = s.charAt(k);
        if(c == '\\' || c == '"')
            stringbuffer.append('\\');
        stringbuffer.append(c);
        k++;
          goto _L6
    }

    private static int skipWhiteSpace(String s, int i)
    {
        int j = s.length();
        do
        {
            if(i >= j || !Character.isWhitespace(s.charAt(i)))
                return i;
            i++;
        } while(true);
    }

    private static String unquote(String s)
    {
        int i;
        StringBuffer stringbuffer;
        boolean flag;
        int j;
        i = s.length();
        stringbuffer = new StringBuffer();
        stringbuffer.ensureCapacity(i);
        flag = false;
        j = 0;
_L2:
        char c;
        if(j >= i)
            return stringbuffer.toString();
        c = s.charAt(j);
        if(flag || c == '\\')
            break; /* Loop/switch isn't completed */
        stringbuffer.append(c);
_L3:
        j++;
        if(true) goto _L2; else goto _L1
_L1:
        if(flag)
        {
            stringbuffer.append(c);
            flag = false;
        } else
        {
            flag = true;
        }
          goto _L3
        if(true) goto _L2; else goto _L4
_L4:
    }

    public String get(String s)
    {
        return (String)parameters.get(s.trim().toLowerCase(Locale.ENGLISH));
    }

    public Enumeration getNames()
    {
        return parameters.keys();
    }

    public boolean isEmpty()
    {
        return parameters.isEmpty();
    }

    protected void parse(String s)
        throws MimeTypeParseException
    {
        if(s != null) goto _L2; else goto _L1
_L1:
        int i;
        return;
_L2:
        int j;
        if((i = s.length()) <= 0)
            continue; /* Loop/switch isn't completed */
        j = skipWhiteSpace(s, 0);
_L12:
label0:
        {
            if(j < i && s.charAt(j) == ';')
                break label0;
            if(j < i)
                throw new MimeTypeParseException("More characters encountered in input than expected.");
        }
        if(true) goto _L1; else goto _L3
_L3:
        int k = skipWhiteSpace(s, j + 1);
        if(k >= i) goto _L1; else goto _L4
_L4:
        String s1;
        int j1;
        char c;
        int l = k;
        int i1;
        do
        {
            if(k >= i || !isTokenChar(s.charAt(k)))
            {
                s1 = s.substring(l, k).toLowerCase(Locale.ENGLISH);
                i1 = skipWhiteSpace(s, k);
                if(i1 >= i || s.charAt(i1) != '=')
                    throw new MimeTypeParseException("Couldn't find the '=' that separates a parameter name from its value.");
                break;
            }
            k++;
        } while(true);
        j1 = skipWhiteSpace(s, i1 + 1);
        if(j1 >= i)
            throw new MimeTypeParseException((new StringBuilder("Couldn't find a value for parameter named ")).append(s1).toString());
        c = s.charAt(j1);
        if(c != '"') goto _L6; else goto _L5
_L5:
        int l1;
        int i2;
        l1 = j1 + 1;
        if(l1 >= i)
            throw new MimeTypeParseException("Encountered unterminated quoted parameter value.");
        i2 = l1;
_L11:
        if(l1 < i) goto _L8; else goto _L7
_L7:
        if(c != '"')
            throw new MimeTypeParseException("Encountered unterminated quoted parameter value.");
        break; /* Loop/switch isn't completed */
_L8:
        if((c = s.charAt(l1)) == '"') goto _L7; else goto _L9
_L9:
        if(c == '\\')
            l1++;
        l1++;
        if(true) goto _L11; else goto _L10
_L10:
        String s2;
        s2 = unquote(s.substring(i2, l1));
        j1 = l1 + 1;
_L13:
        parameters.put(s1, s2);
        j = skipWhiteSpace(s, j1);
          goto _L12
_L6:
label1:
        {
            if(!isTokenChar(c))
                break MISSING_BLOCK_LABEL_370;
            int k1 = j1;
            for(; j1 < i && isTokenChar(s.charAt(j1)); j1++)
                break label1;

            s2 = s.substring(k1, j1);
        }
          goto _L13
        throw new MimeTypeParseException((new StringBuilder("Unexpected character encountered at index ")).append(j1).toString());
          goto _L13
    }

    public void remove(String s)
    {
        parameters.remove(s.trim().toLowerCase(Locale.ENGLISH));
    }

    public void set(String s, String s1)
    {
        parameters.put(s.trim().toLowerCase(Locale.ENGLISH), s1);
    }

    public int size()
    {
        return parameters.size();
    }

    public String toString()
    {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.ensureCapacity(16 * parameters.size());
        Enumeration enumeration = parameters.keys();
        do
        {
            if(!enumeration.hasMoreElements())
                return stringbuffer.toString();
            String s = (String)enumeration.nextElement();
            stringbuffer.append("; ");
            stringbuffer.append(s);
            stringbuffer.append('=');
            stringbuffer.append(quote((String)parameters.get(s)));
        } while(true);
    }

    private static final String TSPECIALS = "()<>@,;:/[]?=\\\"";
    private Hashtable parameters;
}
