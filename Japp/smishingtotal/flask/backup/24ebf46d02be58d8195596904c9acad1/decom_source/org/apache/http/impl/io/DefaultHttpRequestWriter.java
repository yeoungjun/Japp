// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.io;

import java.io.IOException;
import org.apache.http.HttpMessage;
import org.apache.http.HttpRequest;
import org.apache.http.io.SessionOutputBuffer;
import org.apache.http.message.LineFormatter;

// Referenced classes of package org.apache.http.impl.io:
//            AbstractMessageWriter

public class DefaultHttpRequestWriter extends AbstractMessageWriter
{

    public DefaultHttpRequestWriter(SessionOutputBuffer sessionoutputbuffer)
    {
        this(sessionoutputbuffer, null);
    }

    public DefaultHttpRequestWriter(SessionOutputBuffer sessionoutputbuffer, LineFormatter lineformatter)
    {
        super(sessionoutputbuffer, lineformatter);
    }

    protected volatile void writeHeadLine(HttpMessage httpmessage)
        throws IOException
    {
        writeHeadLine((HttpRequest)httpmessage);
    }

    protected void writeHeadLine(HttpRequest httprequest)
        throws IOException
    {
        lineFormatter.formatRequestLine(lineBuf, httprequest.getRequestLine());
        sessionBuffer.writeLine(lineBuf);
    }
}
