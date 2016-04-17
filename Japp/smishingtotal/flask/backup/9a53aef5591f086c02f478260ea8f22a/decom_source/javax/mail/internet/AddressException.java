// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.internet;


// Referenced classes of package javax.mail.internet:
//            ParseException

public class AddressException extends ParseException
{

    public AddressException()
    {
        ref = null;
        pos = -1;
    }

    public AddressException(String s)
    {
        super(s);
        ref = null;
        pos = -1;
    }

    public AddressException(String s, String s1)
    {
        super(s);
        ref = null;
        pos = -1;
        ref = s1;
    }

    public AddressException(String s, String s1, int i)
    {
        super(s);
        ref = null;
        pos = -1;
        ref = s1;
        pos = i;
    }

    public int getPos()
    {
        return pos;
    }

    public String getRef()
    {
        return ref;
    }

    public String toString()
    {
        String s = super.toString();
        if(ref == null)
            return s;
        String s1 = (new StringBuilder(String.valueOf(s))).append(" in string ``").append(ref).append("''").toString();
        if(pos < 0)
            return s1;
        else
            return (new StringBuilder(String.valueOf(s1))).append(" at position ").append(pos).toString();
    }

    private static final long serialVersionUID = 0x7ec48f3eab5368f0L;
    protected int pos;
    protected String ref;
}
