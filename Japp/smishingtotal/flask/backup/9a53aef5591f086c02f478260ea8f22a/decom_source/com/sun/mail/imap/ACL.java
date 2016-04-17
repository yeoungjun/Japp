// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.imap;


// Referenced classes of package com.sun.mail.imap:
//            Rights

public class ACL
    implements Cloneable
{

    public ACL(String s)
    {
        name = s;
        rights = new Rights();
    }

    public ACL(String s, Rights rights1)
    {
        name = s;
        rights = rights1;
    }

    public Object clone()
        throws CloneNotSupportedException
    {
        ACL acl = (ACL)super.clone();
        acl.rights = (Rights)rights.clone();
        return acl;
    }

    public String getName()
    {
        return name;
    }

    public Rights getRights()
    {
        return rights;
    }

    public void setRights(Rights rights1)
    {
        rights = rights1;
    }

    private String name;
    private Rights rights;
}
