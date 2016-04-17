// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http;

import java.io.Serializable;
import org.apache.http.util.Args;

public class ProtocolVersion
    implements Serializable, Cloneable
{

    public ProtocolVersion(String s, int i, int j)
    {
        protocol = (String)Args.notNull(s, "Protocol name");
        major = Args.notNegative(i, "Protocol minor version");
        minor = Args.notNegative(j, "Protocol minor version");
    }

    public Object clone()
        throws CloneNotSupportedException
    {
        return super.clone();
    }

    public int compareToVersion(ProtocolVersion protocolversion)
    {
        Args.notNull(protocolversion, "Protocol version");
        Args.check(protocol.equals(protocolversion.protocol), "Versions for different protocols cannot be compared: %s %s", new Object[] {
            this, protocolversion
        });
        int i = getMajor() - protocolversion.getMajor();
        if(i == 0)
            i = getMinor() - protocolversion.getMinor();
        return i;
    }

    public final boolean equals(Object obj)
    {
        if(this != obj)
        {
            if(!(obj instanceof ProtocolVersion))
                return false;
            ProtocolVersion protocolversion = (ProtocolVersion)obj;
            if(!protocol.equals(protocolversion.protocol) || major != protocolversion.major || minor != protocolversion.minor)
                return false;
        }
        return true;
    }

    public ProtocolVersion forVersion(int i, int j)
    {
        if(i == major && j == minor)
            return this;
        else
            return new ProtocolVersion(protocol, i, j);
    }

    public final int getMajor()
    {
        return major;
    }

    public final int getMinor()
    {
        return minor;
    }

    public final String getProtocol()
    {
        return protocol;
    }

    public final boolean greaterEquals(ProtocolVersion protocolversion)
    {
        return isComparable(protocolversion) && compareToVersion(protocolversion) >= 0;
    }

    public final int hashCode()
    {
        return protocol.hashCode() ^ 0x186a0 * major ^ minor;
    }

    public boolean isComparable(ProtocolVersion protocolversion)
    {
        return protocolversion != null && protocol.equals(protocolversion.protocol);
    }

    public final boolean lessEquals(ProtocolVersion protocolversion)
    {
        return isComparable(protocolversion) && compareToVersion(protocolversion) <= 0;
    }

    public String toString()
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(protocol);
        stringbuilder.append('/');
        stringbuilder.append(Integer.toString(major));
        stringbuilder.append('.');
        stringbuilder.append(Integer.toString(minor));
        return stringbuilder.toString();
    }

    private static final long serialVersionUID = 0x7c37246eac22717cL;
    protected final int major;
    protected final int minor;
    protected final String protocol;
}
