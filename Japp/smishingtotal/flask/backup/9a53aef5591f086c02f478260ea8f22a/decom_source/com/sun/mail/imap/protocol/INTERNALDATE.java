// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;
import java.text.*;
import java.util.Date;
import java.util.Locale;
import javax.mail.internet.MailDateFormat;

// Referenced classes of package com.sun.mail.imap.protocol:
//            Item, FetchResponse

public class INTERNALDATE
    implements Item
{

    public INTERNALDATE(FetchResponse fetchresponse)
        throws ParsingException
    {
        msgno = fetchresponse.getNumber();
        fetchresponse.skipSpaces();
        String s = fetchresponse.readString();
        if(s == null)
            throw new ParsingException("INTERNALDATE is NIL");
        try
        {
            date = mailDateFormat.parse(s);
            return;
        }
        catch(ParseException parseexception)
        {
            throw new ParsingException("INTERNALDATE parse error");
        }
    }

    public static String format(Date date1)
    {
        StringBuffer stringbuffer = new StringBuffer();
        synchronized(df)
        {
            df.format(date1, stringbuffer, new FieldPosition(0));
        }
        int i = -date1.getTimezoneOffset();
        int j;
        int k;
        if(i < 0)
        {
            stringbuffer.append('-');
            i = -i;
        } else
        {
            stringbuffer.append('+');
        }
        j = i / 60;
        k = i % 60;
        stringbuffer.append(Character.forDigit(j / 10, 10));
        stringbuffer.append(Character.forDigit(j % 10, 10));
        stringbuffer.append(Character.forDigit(k / 10, 10));
        stringbuffer.append(Character.forDigit(k % 10, 10));
        return stringbuffer.toString();
        exception;
        simpledateformat;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public Date getDate()
    {
        return date;
    }

    private static SimpleDateFormat df;
    private static MailDateFormat mailDateFormat = new MailDateFormat();
    static final char name[] = {
        'I', 'N', 'T', 'E', 'R', 'N', 'A', 'L', 'D', 'A', 
        'T', 'E'
    };
    protected Date date;
    public int msgno;

    static 
    {
        df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss ", Locale.US);
    }
}
