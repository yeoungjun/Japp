// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.internet;

import com.sun.mail.util.LineOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import javax.activation.DataHandler;
import javax.mail.MessagingException;

// Referenced classes of package javax.mail.internet:
//            MimeBodyPart

public class PreencodedMimeBodyPart extends MimeBodyPart
{

    public PreencodedMimeBodyPart(String s)
    {
        encoding = s;
    }

    public String getEncoding()
        throws MessagingException
    {
        return encoding;
    }

    protected void updateHeaders()
        throws MessagingException
    {
        super.updateHeaders();
        MimeBodyPart.setEncoding(this, encoding);
    }

    public void writeTo(OutputStream outputstream)
        throws IOException, MessagingException
    {
        LineOutputStream lineoutputstream;
        Enumeration enumeration;
        if(outputstream instanceof LineOutputStream)
            lineoutputstream = (LineOutputStream)outputstream;
        else
            lineoutputstream = new LineOutputStream(outputstream);
        enumeration = getAllHeaderLines();
        do
        {
            if(!enumeration.hasMoreElements())
            {
                lineoutputstream.writeln();
                getDataHandler().writeTo(outputstream);
                outputstream.flush();
                return;
            }
            lineoutputstream.writeln((String)enumeration.nextElement());
        } while(true);
    }

    private String encoding;
}
