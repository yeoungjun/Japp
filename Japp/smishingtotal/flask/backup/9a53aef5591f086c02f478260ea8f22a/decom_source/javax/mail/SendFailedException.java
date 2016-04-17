// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail;


// Referenced classes of package javax.mail:
//            MessagingException, Address

public class SendFailedException extends MessagingException
{

    public SendFailedException()
    {
    }

    public SendFailedException(String s)
    {
        super(s);
    }

    public SendFailedException(String s, Exception exception)
    {
        super(s, exception);
    }

    public SendFailedException(String s, Exception exception, Address aaddress[], Address aaddress1[], Address aaddress2[])
    {
        super(s, exception);
        validSent = aaddress;
        validUnsent = aaddress1;
        invalid = aaddress2;
    }

    public Address[] getInvalidAddresses()
    {
        return invalid;
    }

    public Address[] getValidSentAddresses()
    {
        return validSent;
    }

    public Address[] getValidUnsentAddresses()
    {
        return validUnsent;
    }

    private static final long serialVersionUID = 0xa6623d341bc51ecfL;
    protected transient Address invalid[];
    protected transient Address validSent[];
    protected transient Address validUnsent[];
}
