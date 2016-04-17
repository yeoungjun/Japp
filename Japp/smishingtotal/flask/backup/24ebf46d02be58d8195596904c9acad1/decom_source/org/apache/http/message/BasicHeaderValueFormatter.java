// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.message;

import org.apache.http.HeaderElement;
import org.apache.http.NameValuePair;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

// Referenced classes of package org.apache.http.message:
//            HeaderValueFormatter

public class BasicHeaderValueFormatter
    implements HeaderValueFormatter
{

    public BasicHeaderValueFormatter()
    {
    }

    public static String formatElements(HeaderElement aheaderelement[], boolean flag, HeaderValueFormatter headervalueformatter)
    {
        if(headervalueformatter == null)
            headervalueformatter = INSTANCE;
        return headervalueformatter.formatElements(null, aheaderelement, flag).toString();
    }

    public static String formatHeaderElement(HeaderElement headerelement, boolean flag, HeaderValueFormatter headervalueformatter)
    {
        if(headervalueformatter == null)
            headervalueformatter = INSTANCE;
        return headervalueformatter.formatHeaderElement(null, headerelement, flag).toString();
    }

    public static String formatNameValuePair(NameValuePair namevaluepair, boolean flag, HeaderValueFormatter headervalueformatter)
    {
        if(headervalueformatter == null)
            headervalueformatter = INSTANCE;
        return headervalueformatter.formatNameValuePair(null, namevaluepair, flag).toString();
    }

    public static String formatParameters(NameValuePair anamevaluepair[], boolean flag, HeaderValueFormatter headervalueformatter)
    {
        if(headervalueformatter == null)
            headervalueformatter = INSTANCE;
        return headervalueformatter.formatParameters(null, anamevaluepair, flag).toString();
    }

    protected void doFormatValue(CharArrayBuffer chararraybuffer, String s, boolean flag)
    {
        boolean flag1 = flag;
        if(!flag1)
        {
            for(int j = 0; j < s.length() && !flag1; j++)
                flag1 = isSeparator(s.charAt(j));

        }
        if(flag1)
            chararraybuffer.append('"');
        for(int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            if(isUnsafe(c))
                chararraybuffer.append('\\');
            chararraybuffer.append(c);
        }

        if(flag1)
            chararraybuffer.append('"');
    }

    protected int estimateElementsLen(HeaderElement aheaderelement[])
    {
        int i;
        if(aheaderelement == null || aheaderelement.length < 1)
        {
            i = 0;
        } else
        {
            i = 2 * (-1 + aheaderelement.length);
            int j = aheaderelement.length;
            int k = 0;
            while(k < j) 
            {
                i += estimateHeaderElementLen(aheaderelement[k]);
                k++;
            }
        }
        return i;
    }

    protected int estimateHeaderElementLen(HeaderElement headerelement)
    {
        if(headerelement != null) goto _L2; else goto _L1
_L1:
        int i = 0;
_L4:
        return i;
_L2:
        i = headerelement.getName().length();
        String s = headerelement.getValue();
        if(s != null)
            i += 3 + s.length();
        int j = headerelement.getParameterCount();
        if(j > 0)
        {
            int k = 0;
            while(k < j) 
            {
                i += 2 + estimateNameValuePairLen(headerelement.getParameter(k));
                k++;
            }
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    protected int estimateNameValuePairLen(NameValuePair namevaluepair)
    {
        int i;
        if(namevaluepair == null)
        {
            i = 0;
        } else
        {
            i = namevaluepair.getName().length();
            String s = namevaluepair.getValue();
            if(s != null)
                return i + (3 + s.length());
        }
        return i;
    }

    protected int estimateParametersLen(NameValuePair anamevaluepair[])
    {
        int i;
        if(anamevaluepair == null || anamevaluepair.length < 1)
        {
            i = 0;
        } else
        {
            i = 2 * (-1 + anamevaluepair.length);
            int j = anamevaluepair.length;
            int k = 0;
            while(k < j) 
            {
                i += estimateNameValuePairLen(anamevaluepair[k]);
                k++;
            }
        }
        return i;
    }

    public CharArrayBuffer formatElements(CharArrayBuffer chararraybuffer, HeaderElement aheaderelement[], boolean flag)
    {
        Args.notNull(aheaderelement, "Header element array");
        int i = estimateElementsLen(aheaderelement);
        CharArrayBuffer chararraybuffer1 = chararraybuffer;
        int j;
        if(chararraybuffer1 == null)
            chararraybuffer1 = new CharArrayBuffer(i);
        else
            chararraybuffer1.ensureCapacity(i);
        for(j = 0; j < aheaderelement.length; j++)
        {
            if(j > 0)
                chararraybuffer1.append(", ");
            formatHeaderElement(chararraybuffer1, aheaderelement[j], flag);
        }

        return chararraybuffer1;
    }

    public CharArrayBuffer formatHeaderElement(CharArrayBuffer chararraybuffer, HeaderElement headerelement, boolean flag)
    {
        Args.notNull(headerelement, "Header element");
        int i = estimateHeaderElementLen(headerelement);
        CharArrayBuffer chararraybuffer1 = chararraybuffer;
        String s;
        int j;
        if(chararraybuffer1 == null)
            chararraybuffer1 = new CharArrayBuffer(i);
        else
            chararraybuffer1.ensureCapacity(i);
        chararraybuffer1.append(headerelement.getName());
        s = headerelement.getValue();
        if(s != null)
        {
            chararraybuffer1.append('=');
            doFormatValue(chararraybuffer1, s, flag);
        }
        j = headerelement.getParameterCount();
        if(j > 0)
        {
            for(int k = 0; k < j; k++)
            {
                chararraybuffer1.append("; ");
                formatNameValuePair(chararraybuffer1, headerelement.getParameter(k), flag);
            }

        }
        return chararraybuffer1;
    }

    public CharArrayBuffer formatNameValuePair(CharArrayBuffer chararraybuffer, NameValuePair namevaluepair, boolean flag)
    {
        Args.notNull(namevaluepair, "Name / value pair");
        int i = estimateNameValuePairLen(namevaluepair);
        CharArrayBuffer chararraybuffer1 = chararraybuffer;
        String s;
        if(chararraybuffer1 == null)
            chararraybuffer1 = new CharArrayBuffer(i);
        else
            chararraybuffer1.ensureCapacity(i);
        chararraybuffer1.append(namevaluepair.getName());
        s = namevaluepair.getValue();
        if(s != null)
        {
            chararraybuffer1.append('=');
            doFormatValue(chararraybuffer1, s, flag);
        }
        return chararraybuffer1;
    }

    public CharArrayBuffer formatParameters(CharArrayBuffer chararraybuffer, NameValuePair anamevaluepair[], boolean flag)
    {
        Args.notNull(anamevaluepair, "Header parameter array");
        int i = estimateParametersLen(anamevaluepair);
        CharArrayBuffer chararraybuffer1 = chararraybuffer;
        int j;
        if(chararraybuffer1 == null)
            chararraybuffer1 = new CharArrayBuffer(i);
        else
            chararraybuffer1.ensureCapacity(i);
        for(j = 0; j < anamevaluepair.length; j++)
        {
            if(j > 0)
                chararraybuffer1.append("; ");
            formatNameValuePair(chararraybuffer1, anamevaluepair[j], flag);
        }

        return chararraybuffer1;
    }

    protected boolean isSeparator(char c)
    {
        return " ;,:@()<>\\\"/[]?={}\t".indexOf(c) >= 0;
    }

    protected boolean isUnsafe(char c)
    {
        return "\"\\".indexOf(c) >= 0;
    }

    public static final BasicHeaderValueFormatter DEFAULT = new BasicHeaderValueFormatter();
    public static final BasicHeaderValueFormatter INSTANCE = new BasicHeaderValueFormatter();
    public static final String SEPARATORS = " ;,:@()<>\\\"/[]?={}\t";
    public static final String UNSAFE_CHARS = "\"\\";

}
