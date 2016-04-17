// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail;

import java.io.Serializable;

public abstract class Address
    implements Serializable
{

    public Address()
    {
    }

    public abstract boolean equals(Object obj);

    public abstract String getType();

    public abstract String toString();

    private static final long serialVersionUID = 0xaf3277d17464de2aL;
}
