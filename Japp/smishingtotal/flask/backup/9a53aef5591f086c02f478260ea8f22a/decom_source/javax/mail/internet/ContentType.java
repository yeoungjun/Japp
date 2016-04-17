// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.internet;


// Referenced classes of package javax.mail.internet:
//            ParseException, HeaderTokenizer, ParameterList

public class ContentType
{

    public ContentType()
    {
    }

    public ContentType(String s)
        throws ParseException
    {
        HeaderTokenizer headertokenizer = new HeaderTokenizer(s, "()<>@,;:\\\"\t []/?=");
        HeaderTokenizer.Token token = headertokenizer.next();
        if(token.getType() != -1)
            throw new ParseException();
        primaryType = token.getValue();
        if((char)headertokenizer.next().getType() != '/')
            throw new ParseException();
        HeaderTokenizer.Token token1 = headertokenizer.next();
        if(token1.getType() != -1)
            throw new ParseException();
        subType = token1.getValue();
        String s1 = headertokenizer.getRemainder();
        if(s1 != null)
            list = new ParameterList(s1);
    }

    public ContentType(String s, String s1, ParameterList parameterlist)
    {
        primaryType = s;
        subType = s1;
        list = parameterlist;
    }

    public String getBaseType()
    {
        return (new StringBuilder(String.valueOf(primaryType))).append('/').append(subType).toString();
    }

    public String getParameter(String s)
    {
        if(list == null)
            return null;
        else
            return list.get(s);
    }

    public ParameterList getParameterList()
    {
        return list;
    }

    public String getPrimaryType()
    {
        return primaryType;
    }

    public String getSubType()
    {
        return subType;
    }

    public boolean match(String s)
    {
        boolean flag;
        try
        {
            flag = match(new ContentType(s));
        }
        catch(ParseException parseexception)
        {
            return false;
        }
        return flag;
    }

    public boolean match(ContentType contenttype)
    {
        if(primaryType.equalsIgnoreCase(contenttype.getPrimaryType()))
        {
            String s = contenttype.getSubType();
            if(subType.charAt(0) == '*' || s.charAt(0) == '*')
                return true;
            if(subType.equalsIgnoreCase(s))
                return true;
        }
        return false;
    }

    public void setParameter(String s, String s1)
    {
        if(list == null)
            list = new ParameterList();
        list.set(s, s1);
    }

    public void setParameterList(ParameterList parameterlist)
    {
        list = parameterlist;
    }

    public void setPrimaryType(String s)
    {
        primaryType = s;
    }

    public void setSubType(String s)
    {
        subType = s;
    }

    public String toString()
    {
        if(primaryType == null || subType == null)
            return null;
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append(primaryType).append('/').append(subType);
        if(list != null)
            stringbuffer.append(list.toString(14 + stringbuffer.length()));
        return stringbuffer.toString();
    }

    private ParameterList list;
    private String primaryType;
    private String subType;
}
