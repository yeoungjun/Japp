// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io.input;

import java.io.*;

public class ClassLoaderObjectInputStream extends ObjectInputStream
{

    public ClassLoaderObjectInputStream(ClassLoader classloader, InputStream inputstream)
        throws IOException, StreamCorruptedException
    {
        super(inputstream);
        classLoader = classloader;
    }

    protected Class resolveClass(ObjectStreamClass objectstreamclass)
        throws IOException, ClassNotFoundException
    {
        Class class1 = Class.forName(objectstreamclass.getName(), false, classLoader);
        if(class1 != null)
            return class1;
        else
            return super.resolveClass(objectstreamclass);
    }

    private final ClassLoader classLoader;
}
