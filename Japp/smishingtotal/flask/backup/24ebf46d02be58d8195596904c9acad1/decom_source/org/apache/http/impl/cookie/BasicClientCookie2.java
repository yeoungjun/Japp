// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.cookie;

import java.io.Serializable;
import java.util.Date;
import org.apache.http.cookie.SetCookie2;

// Referenced classes of package org.apache.http.impl.cookie:
//            BasicClientCookie

public class BasicClientCookie2 extends BasicClientCookie
    implements SetCookie2, Serializable
{

    public BasicClientCookie2(String s, String s1)
    {
        super(s, s1);
    }

    public Object clone()
        throws CloneNotSupportedException
    {
        BasicClientCookie2 basicclientcookie2 = (BasicClientCookie2)super.clone();
        if(ports != null)
            basicclientcookie2.ports = (int[])ports.clone();
        return basicclientcookie2;
    }

    public String getCommentURL()
    {
        return commentURL;
    }

    public int[] getPorts()
    {
        return ports;
    }

    public boolean isExpired(Date date)
    {
        return discard || super.isExpired(date);
    }

    public boolean isPersistent()
    {
        return !discard && super.isPersistent();
    }

    public void setCommentURL(String s)
    {
        commentURL = s;
    }

    public void setDiscard(boolean flag)
    {
        discard = flag;
    }

    public void setPorts(int ai[])
    {
        ports = ai;
    }

    private static final long serialVersionUID = 0x9485a8e1f84fbb1fL;
    private String commentURL;
    private boolean discard;
    private int ports[];
}
