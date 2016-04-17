// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail;


// Referenced classes of package javax.mail:
//            MessagingException, Store

public class StoreClosedException extends MessagingException
{

    public StoreClosedException(Store store1)
    {
        this(store1, null);
    }

    public StoreClosedException(Store store1, String s)
    {
        super(s);
        store = store1;
    }

    public Store getStore()
    {
        return store;
    }

    private static final long serialVersionUID = 0xd4595255d6538f21L;
    private transient Store store;
}
