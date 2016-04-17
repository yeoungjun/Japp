// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail;

import javax.activation.DataSource;

// Referenced classes of package javax.mail:
//            MessagingException, BodyPart

public interface MultipartDataSource
    extends DataSource
{

    public abstract BodyPart getBodyPart(int i)
        throws MessagingException;

    public abstract int getCount();
}
