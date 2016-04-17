// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail;

import java.net.InetAddress;

// Referenced classes of package javax.mail:
//            PasswordAuthentication

public abstract class Authenticator
{

    public Authenticator()
    {
    }

    private void reset()
    {
        requestingSite = null;
        requestingPort = -1;
        requestingProtocol = null;
        requestingPrompt = null;
        requestingUserName = null;
    }

    protected final String getDefaultUserName()
    {
        return requestingUserName;
    }

    protected PasswordAuthentication getPasswordAuthentication()
    {
        return null;
    }

    protected final int getRequestingPort()
    {
        return requestingPort;
    }

    protected final String getRequestingPrompt()
    {
        return requestingPrompt;
    }

    protected final String getRequestingProtocol()
    {
        return requestingProtocol;
    }

    protected final InetAddress getRequestingSite()
    {
        return requestingSite;
    }

    final PasswordAuthentication requestPasswordAuthentication(InetAddress inetaddress, int i, String s, String s1, String s2)
    {
        reset();
        requestingSite = inetaddress;
        requestingPort = i;
        requestingProtocol = s;
        requestingPrompt = s1;
        requestingUserName = s2;
        return getPasswordAuthentication();
    }

    private int requestingPort;
    private String requestingPrompt;
    private String requestingProtocol;
    private InetAddress requestingSite;
    private String requestingUserName;
}
