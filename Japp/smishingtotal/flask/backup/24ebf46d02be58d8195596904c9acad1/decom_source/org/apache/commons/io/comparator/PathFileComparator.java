// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io.comparator;

import java.io.File;
import java.io.Serializable;
import java.util.Comparator;
import org.apache.commons.io.IOCase;

// Referenced classes of package org.apache.commons.io.comparator:
//            AbstractFileComparator, ReverseComparator

public class PathFileComparator extends AbstractFileComparator
    implements Serializable
{

    public PathFileComparator()
    {
        caseSensitivity = IOCase.SENSITIVE;
    }

    public PathFileComparator(IOCase iocase)
    {
        if(iocase == null)
            iocase = IOCase.SENSITIVE;
        caseSensitivity = iocase;
    }

    public int compare(File file, File file1)
    {
        return caseSensitivity.checkCompareTo(file.getPath(), file1.getPath());
    }

    public volatile int compare(Object obj, Object obj1)
    {
        return compare((File)obj, (File)obj1);
    }

    public String toString()
    {
        return (new StringBuilder()).append(super.toString()).append("[caseSensitivity=").append(caseSensitivity).append("]").toString();
    }

    public static final Comparator PATH_COMPARATOR;
    public static final Comparator PATH_INSENSITIVE_COMPARATOR;
    public static final Comparator PATH_INSENSITIVE_REVERSE;
    public static final Comparator PATH_REVERSE;
    public static final Comparator PATH_SYSTEM_COMPARATOR;
    public static final Comparator PATH_SYSTEM_REVERSE;
    private final IOCase caseSensitivity;

    static 
    {
        PATH_COMPARATOR = new PathFileComparator();
        PATH_REVERSE = new ReverseComparator(PATH_COMPARATOR);
        PATH_INSENSITIVE_COMPARATOR = new PathFileComparator(IOCase.INSENSITIVE);
        PATH_INSENSITIVE_REVERSE = new ReverseComparator(PATH_INSENSITIVE_COMPARATOR);
        PATH_SYSTEM_COMPARATOR = new PathFileComparator(IOCase.SYSTEM);
        PATH_SYSTEM_REVERSE = new ReverseComparator(PATH_SYSTEM_COMPARATOR);
    }
}
