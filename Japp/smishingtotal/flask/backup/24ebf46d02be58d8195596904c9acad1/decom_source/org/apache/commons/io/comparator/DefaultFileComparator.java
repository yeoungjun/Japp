// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io.comparator;

import java.io.File;
import java.io.Serializable;
import java.util.Comparator;

// Referenced classes of package org.apache.commons.io.comparator:
//            AbstractFileComparator, ReverseComparator

public class DefaultFileComparator extends AbstractFileComparator
    implements Serializable
{

    public DefaultFileComparator()
    {
    }

    public int compare(File file, File file1)
    {
        return file.compareTo(file1);
    }

    public volatile int compare(Object obj, Object obj1)
    {
        return compare((File)obj, (File)obj1);
    }

    public static final Comparator DEFAULT_COMPARATOR;
    public static final Comparator DEFAULT_REVERSE;

    static 
    {
        DEFAULT_COMPARATOR = new DefaultFileComparator();
        DEFAULT_REVERSE = new ReverseComparator(DEFAULT_COMPARATOR);
    }
}
