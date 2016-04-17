// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.internet;

import javax.mail.Session;

// Referenced classes of package javax.mail.internet:
//            InternetAddress

class UniqueValue
{

    UniqueValue()
    {
    }

    public static String getUniqueBoundaryValue()
    {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append("----=_Part_").append(getUniqueId()).append("_").append(stringbuffer.hashCode()).append('.').append(System.currentTimeMillis());
        return stringbuffer.toString();
    }

    private static int getUniqueId()
    {
        javax/mail/internet/UniqueValue;
        JVM INSTR monitorenter ;
        int i;
        i = id;
        id = i + 1;
        javax/mail/internet/UniqueValue;
        JVM INSTR monitorexit ;
        return i;
        Exception exception;
        exception;
        throw exception;
    }

    public static String getUniqueMessageIDValue(Session session)
    {
        InternetAddress internetaddress = InternetAddress.getLocalAddress(session);
        String s;
        StringBuffer stringbuffer;
        if(internetaddress != null)
            s = internetaddress.getAddress();
        else
            s = "javamailuser@localhost";
        stringbuffer = new StringBuffer();
        stringbuffer.append(stringbuffer.hashCode()).append('.').append(getUniqueId()).append('.').append(System.currentTimeMillis()).append('.').append("JavaMail.").append(s);
        return stringbuffer.toString();
    }

    private static int id = 0;

}
