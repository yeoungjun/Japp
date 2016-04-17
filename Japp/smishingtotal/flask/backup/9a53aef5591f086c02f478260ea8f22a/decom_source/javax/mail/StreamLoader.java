// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail;

import java.io.IOException;
import java.io.InputStream;

interface StreamLoader
{

    public abstract void load(InputStream inputstream)
        throws IOException;
}
