// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.message;

import java.io.Serializable;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

// Referenced classes of package org.apache.http.message:
//            BasicLineFormatter

public class BasicStatusLine
    implements StatusLine, Cloneable, Serializable
{

    public BasicStatusLine(ProtocolVersion protocolversion, int i, String s)
    {
        protoVersion = (ProtocolVersion)Args.notNull(protocolversion, "Version");
        statusCode = Args.notNegative(i, "Status code");
        reasonPhrase = s;
    }

    public Object clone()
        throws CloneNotSupportedException
    {
        return super.clone();
    }

    public ProtocolVersion getProtocolVersion()
    {
        return protoVersion;
    }

    public String getReasonPhrase()
    {
        return reasonPhrase;
    }

    public int getStatusCode()
    {
        return statusCode;
    }

    public String toString()
    {
        return BasicLineFormatter.INSTANCE.formatStatusLine(null, this).toString();
    }

    private static final long serialVersionUID = 0xde17a42b501ecf7bL;
    private final ProtocolVersion protoVersion;
    private final String reasonPhrase;
    private final int statusCode;
}
