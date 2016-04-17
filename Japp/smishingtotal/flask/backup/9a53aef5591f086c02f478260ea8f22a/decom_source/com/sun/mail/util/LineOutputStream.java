// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.util;

import java.io.FilterOutputStream;
import java.io.OutputStream;
import javax.mail.MessagingException;

// Referenced classes of package com.sun.mail.util:
//            ASCIIUtility

public class LineOutputStream extends FilterOutputStream
{

    public LineOutputStream(OutputStream outputstream)
    {
        super(outputstream);
    }

    public void writeln()
        throws MessagingException
    {
        try
        {
            out.write(newline);
            return;
        }
        catch(Exception exception)
        {
            throw new MessagingException("IOException", exception);
        }
    }

    public void writeln(String s)
        throws MessagingException
    {
        try
        {
            byte abyte0[] = ASCIIUtility.getBytes(s);
            out.write(abyte0);
            out.write(newline);
            return;
        }
        catch(Exception exception)
        {
            throw new MessagingException("IOException", exception);
        }
    }

    private static byte newline[];

    static 
    {
        newline = new byte[2];
        newline[0] = 13;
        newline[1] = 10;
    }
}
