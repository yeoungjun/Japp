// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http;

import java.util.Iterator;

public interface TokenIterator
    extends Iterator
{

    public abstract boolean hasNext();

    public abstract String nextToken();
}
