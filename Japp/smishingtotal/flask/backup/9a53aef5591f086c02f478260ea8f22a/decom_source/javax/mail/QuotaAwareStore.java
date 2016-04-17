// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail;


// Referenced classes of package javax.mail:
//            MessagingException, Quota

public interface QuotaAwareStore
{

    public abstract Quota[] getQuota(String s)
        throws MessagingException;

    public abstract void setQuota(Quota quota)
        throws MessagingException;
}
