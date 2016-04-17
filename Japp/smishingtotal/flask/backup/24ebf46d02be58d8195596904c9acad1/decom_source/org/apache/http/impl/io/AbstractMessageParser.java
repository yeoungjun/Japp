// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.io;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.*;
import org.apache.http.config.MessageConstraints;
import org.apache.http.io.HttpMessageParser;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.message.BasicLineParser;
import org.apache.http.message.LineParser;
import org.apache.http.params.HttpParamConfig;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

public abstract class AbstractMessageParser
    implements HttpMessageParser
{

    public AbstractMessageParser(SessionInputBuffer sessioninputbuffer, LineParser lineparser, MessageConstraints messageconstraints)
    {
        sessionBuffer = (SessionInputBuffer)Args.notNull(sessioninputbuffer, "Session input buffer");
        if(lineparser == null)
            lineparser = BasicLineParser.INSTANCE;
        lineParser = lineparser;
        if(messageconstraints == null)
            messageconstraints = MessageConstraints.DEFAULT;
        messageConstraints = messageconstraints;
        headerLines = new ArrayList();
        state = 0;
    }

    public AbstractMessageParser(SessionInputBuffer sessioninputbuffer, LineParser lineparser, HttpParams httpparams)
    {
        Args.notNull(sessioninputbuffer, "Session input buffer");
        Args.notNull(httpparams, "HTTP parameters");
        sessionBuffer = sessioninputbuffer;
        messageConstraints = HttpParamConfig.getMessageConstraints(httpparams);
        if(lineparser == null)
            lineparser = BasicLineParser.INSTANCE;
        lineParser = lineparser;
        headerLines = new ArrayList();
        state = 0;
    }

    public static Header[] parseHeaders(SessionInputBuffer sessioninputbuffer, int i, int j, LineParser lineparser)
        throws HttpException, IOException
    {
        ArrayList arraylist = new ArrayList();
        if(lineparser == null)
            lineparser = BasicLineParser.INSTANCE;
        return parseHeaders(sessioninputbuffer, i, j, lineparser, ((List) (arraylist)));
    }

    public static Header[] parseHeaders(SessionInputBuffer sessioninputbuffer, int i, int j, LineParser lineparser, List list)
        throws HttpException, IOException
    {
        CharArrayBuffer chararraybuffer;
        CharArrayBuffer chararraybuffer1;
        Args.notNull(sessioninputbuffer, "Session input buffer");
        Args.notNull(lineparser, "Line parser");
        Args.notNull(list, "Header line list");
        chararraybuffer = null;
        chararraybuffer1 = null;
_L2:
        Header aheader[];
        if(chararraybuffer == null)
            chararraybuffer = new CharArrayBuffer(64);
        else
            chararraybuffer.clear();
        if(sessioninputbuffer.readLine(chararraybuffer) == -1 || chararraybuffer.length() < 1)
        {
            aheader = new Header[list.size()];
            int k = 0;
            while(k < list.size()) 
            {
                CharArrayBuffer chararraybuffer2 = (CharArrayBuffer)list.get(k);
                int l;
                char c;
                try
                {
                    aheader[k] = lineparser.parseHeader(chararraybuffer2);
                }
                catch(ParseException parseexception)
                {
                    throw new ProtocolException(parseexception.getMessage());
                }
                k++;
            }
            break MISSING_BLOCK_LABEL_320;
        }
        if(chararraybuffer.charAt(0) != ' ' && chararraybuffer.charAt(0) != '\t' || chararraybuffer1 == null)
            break; /* Loop/switch isn't completed */
        l = 0;
label0:
        do
        {
label1:
            {
                if(l < chararraybuffer.length())
                {
                    c = chararraybuffer.charAt(l);
                    if(c == ' ' || c == '\t')
                        break label1;
                }
                if(j > 0 && (1 + chararraybuffer1.length() + chararraybuffer.length()) - l > j)
                    throw new MessageConstraintException("Maximum line length limit exceeded");
                break label0;
            }
            l++;
        } while(true);
        chararraybuffer1.append(' ');
        chararraybuffer1.append(chararraybuffer, l, chararraybuffer.length() - l);
_L3:
        if(i > 0 && list.size() >= i)
            throw new MessageConstraintException("Maximum header count exceeded");
        if(true) goto _L2; else goto _L1
_L1:
        list.add(chararraybuffer);
        chararraybuffer1 = chararraybuffer;
        chararraybuffer = null;
          goto _L3
        if(true) goto _L2; else goto _L4
_L4:
        return aheader;
    }

    public HttpMessage parse()
        throws IOException, HttpException
    {
        switch(state)
        {
        default:
            throw new IllegalStateException("Inconsistent parser state");

        case 0: // '\0'
            Header aheader[];
            HttpMessage httpmessage;
            try
            {
                message = parseHead(sessionBuffer);
            }
            catch(ParseException parseexception)
            {
                throw new ProtocolException(parseexception.getMessage(), parseexception);
            }
            state = 1;
            // fall through

        case 1: // '\001'
            aheader = parseHeaders(sessionBuffer, messageConstraints.getMaxHeaderCount(), messageConstraints.getMaxLineLength(), lineParser, headerLines);
            message.setHeaders(aheader);
            httpmessage = message;
            message = null;
            headerLines.clear();
            state = 0;
            return httpmessage;
        }
    }

    protected abstract HttpMessage parseHead(SessionInputBuffer sessioninputbuffer)
        throws IOException, HttpException, ParseException;

    private static final int HEADERS = 1;
    private static final int HEAD_LINE;
    private final List headerLines;
    protected final LineParser lineParser;
    private HttpMessage message;
    private final MessageConstraints messageConstraints;
    private final SessionInputBuffer sessionBuffer;
    private int state;
}
