// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io.comparator;

import java.io.File;
import java.io.Serializable;
import java.util.Comparator;

// Referenced classes of package org.apache.commons.io.comparator:
//            AbstractFileComparator, ReverseComparator

public class DirectoryFileComparator extends AbstractFileComparator
    implements Serializable
{

    public DirectoryFileComparator()
    {
    }

    private int getType(File file)
    {
        return !file.isDirectory() ? 2 : 1;
    }

    public int compare(File file, File file1)
    {
        return getType(file) - getType(file1);
    }

    public volatile int compare(Object obj, Object obj1)
    {
        return compare((File)obj, (File)obj1);
    }

    public static final Comparator DIRECTORY_COMPARATOR;
    public static final Comparator DIRECTORY_REVERSE;

    static 
    {
        DIRECTORY_COMPARATOR = new DirectoryFileComparator();
        DIRECTORY_REVERSE = new ReverseComparator(DIRECTORY_COMPARATOR);
    }
}
