// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.io;

import java.io.IOException;
import org.apache.http.HttpMessage;
import org.apache.http.HttpResponse;
import org.apache.http.io.SessionOutputBuffer;
import org.apache.http.message.LineFormatter;

// Referenced classes of package org.apache.http.impl.io:
//            AbstractMessageWriter

public class DefaultHttpResponseWriter extends AbstractMessageWriter
{

    public DefaultHttpResponseWriter(SessionOutputBuffer sessionoutputbuffer)
    {
        super(sessionoutputbuffer, null);
    }

    public DefaultHttpResponseWriter(SessionOutputBuffer sessionoutputbuffer, LineFormatter lineformatter)
    {
        super(sessionoutputbuffer, lineformatter);
    }

    protected volatile void writeHeadLine(HttpMessage httpmessage)
        throws IOException
    {
        writeHeadLine((HttpResponse)httpmessage);
    }

    protected void writeHeadLine(HttpResponse httpresponse)
        throws IOException
    {
        lineFormatter.formatStatusLine(lineBuf, httpresponse.getStatusLine());
        sessionBuffer.writeLine(lineBuf);
    }
}
