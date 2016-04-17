// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io.comparator;

import java.io.File;
import java.io.Serializable;
import java.util.Comparator;
import org.apache.commons.io.FileUtils;

// Referenced classes of package org.apache.commons.io.comparator:
//            AbstractFileComparator, ReverseComparator

public class SizeFileComparator extends AbstractFileComparator
    implements Serializable
{

    public SizeFileComparator()
    {
        sumDirectoryContents = false;
    }

    public SizeFileComparator(boolean flag)
    {
        sumDirectoryContents = flag;
    }

    public int compare(File file, File file1)
    {
        long l;
        long l1;
        long l2;
        if(file.isDirectory())
        {
            if(sumDirectoryContents && file.exists())
                l = FileUtils.sizeOfDirectory(file);
            else
                l = 0L;
        } else
        {
            l = file.length();
        }
        if(file1.isDirectory())
        {
            if(sumDirectoryContents && file1.exists())
                l1 = FileUtils.sizeOfDirectory(file1);
            else
                l1 = 0L;
        } else
        {
            l1 = file1.length();
        }
        l2 = l - l1;
        if(l2 < 0L)
            return -1;
        return l2 <= 0L ? 0 : 1;
    }

    public volatile int compare(Object obj, Object obj1)
    {
        return compare((File)obj, (File)obj1);
    }

    public String toString()
    {
        return (new StringBuilder()).append(super.toString()).append("[sumDirectoryContents=").append(sumDirectoryContents).append("]").toString();
    }

    public static final Comparator SIZE_COMPARATOR;
    public static final Comparator SIZE_REVERSE;
    public static final Comparator SIZE_SUMDIR_COMPARATOR;
    public static final Comparator SIZE_SUMDIR_REVERSE;
    private final boolean sumDirectoryContents;

    static 
    {
        SIZE_COMPARATOR = new SizeFileComparator();
        SIZE_REVERSE = new ReverseComparator(SIZE_COMPARATOR);
        SIZE_SUMDIR_COMPARATOR = new SizeFileComparator(true);
        SIZE_SUMDIR_REVERSE = new ReverseComparator(SIZE_SUMDIR_COMPARATOR);
    }
}
