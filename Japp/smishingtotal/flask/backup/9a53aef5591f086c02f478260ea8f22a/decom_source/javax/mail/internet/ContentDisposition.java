// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.internet;


// Referenced classes of package javax.mail.internet:
//            ParseException, HeaderTokenizer, ParameterList

public class ContentDisposition
{

    public ContentDisposition()
    {
    }

    public ContentDisposition(String s)
        throws ParseException
    {
        HeaderTokenizer headertokenizer = new HeaderTokenizer(s, "()<>@,;:\\\"\t []/?=");
        HeaderTokenizer.Token token = headertokenizer.next();
        if(token.getType() != -1)
            throw new ParseException();
        disposition = token.getValue();
        String s1 = headertokenizer.getRemainder();
        if(s1 != null)
            list = new ParameterList(s1);
    }

    public ContentDisposition(String s, ParameterList parameterlist)
    {
        disposition = s;
        list = parameterlist;
    }

    public String getDisposition()
    {
        return disposition;
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

    public void setDisposition(String s)
    {
        disposition = s;
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

    public String toString()
    {
        if(disposition == null)
            return null;
        if(list == null)
        {
            return disposition;
        } else
        {
            StringBuffer stringbuffer = new StringBuffer(disposition);
            stringbuffer.append(list.toString(21 + stringbuffer.length()));
            return stringbuffer.toString();
        }
    }

    private String disposition;
    private ParameterList list;
}
