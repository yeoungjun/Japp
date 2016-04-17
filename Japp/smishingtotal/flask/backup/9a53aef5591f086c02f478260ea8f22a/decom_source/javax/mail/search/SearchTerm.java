// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.search;

import java.io.Serializable;
import javax.mail.Message;

public abstract class SearchTerm
    implements Serializable
{

    public SearchTerm()
    {
    }

    public abstract boolean match(Message message);

    private static final long serialVersionUID = 0xa3ae133bc1b1c4abL;
}
