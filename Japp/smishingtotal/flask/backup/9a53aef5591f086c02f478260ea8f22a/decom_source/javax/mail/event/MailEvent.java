// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.event;

import java.util.EventObject;

public abstract class MailEvent extends EventObject
{

    public MailEvent(Object obj)
    {
        super(obj);
    }

    public abstract void dispatch(Object obj);

    private static final long serialVersionUID = 0x199f49e8c4a31af7L;
}
