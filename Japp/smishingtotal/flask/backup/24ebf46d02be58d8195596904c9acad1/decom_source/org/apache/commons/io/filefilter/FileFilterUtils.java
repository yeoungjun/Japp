// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io.filefilter;

import java.io.*;
import java.util.*;
import org.apache.commons.io.IOCase;

// Referenced classes of package org.apache.commons.io.filefilter:
//            IOFileFilter, AgeFileFilter, AndFileFilter, DelegateFileFilter, 
//            DirectoryFileFilter, FalseFileFilter, FileFileFilter, MagicNumberFileFilter, 
//            NameFileFilter, NotFileFilter, OrFileFilter, PrefixFileFilter, 
//            SizeFileFilter, SuffixFileFilter, TrueFileFilter

public class FileFilterUtils
{

    public FileFilterUtils()
    {
    }

    public static IOFileFilter ageFileFilter(long l)
    {
        return new AgeFileFilter(l);
    }

    public static IOFileFilter ageFileFilter(long l, boolean flag)
    {
        return new AgeFileFilter(l, flag);
    }

    public static IOFileFilter ageFileFilter(File file)
    {
        return new AgeFileFilter(file);
    }

    public static IOFileFilter ageFileFilter(File file, boolean flag)
    {
        return new AgeFileFilter(file, flag);
    }

    public static IOFileFilter ageFileFilter(Date date)
    {
        return new AgeFileFilter(date);
    }

    public static IOFileFilter ageFileFilter(Date date, boolean flag)
    {
        return new AgeFileFilter(date, flag);
    }

    public static transient IOFileFilter and(IOFileFilter aiofilefilter[])
    {
        return new AndFileFilter(toList(aiofilefilter));
    }

    public static IOFileFilter andFileFilter(IOFileFilter iofilefilter, IOFileFilter iofilefilter1)
    {
        return new AndFileFilter(iofilefilter, iofilefilter1);
    }

    public static IOFileFilter asFileFilter(FileFilter filefilter)
    {
        return new DelegateFileFilter(filefilter);
    }

    public static IOFileFilter asFileFilter(FilenameFilter filenamefilter)
    {
        return new DelegateFileFilter(filenamefilter);
    }

    public static IOFileFilter directoryFileFilter()
    {
        return DirectoryFileFilter.DIRECTORY;
    }

    public static IOFileFilter falseFileFilter()
    {
        return FalseFileFilter.FALSE;
    }

    public static IOFileFilter fileFileFilter()
    {
        return FileFileFilter.FILE;
    }

    private static Collection filter(IOFileFilter iofilefilter, Iterable iterable, Collection collection)
    {
        if(iofilefilter == null)
            throw new IllegalArgumentException("file filter is null");
        if(iterable != null)
        {
            Iterator iterator = iterable.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                File file = (File)iterator.next();
                if(file == null)
                    throw new IllegalArgumentException("file collection contains null");
                if(iofilefilter.accept(file))
                    collection.add(file);
            } while(true);
        }
        return collection;
    }

    public static File[] filter(IOFileFilter iofilefilter, Iterable iterable)
    {
        List list = filterList(iofilefilter, iterable);
        return (File[])list.toArray(new File[list.size()]);
    }

    public static transient File[] filter(IOFileFilter iofilefilter, File afile[])
    {
        if(iofilefilter == null)
            throw new IllegalArgumentException("file filter is null");
        if(afile == null)
            return new File[0];
        ArrayList arraylist = new ArrayList();
        int i = afile.length;
        for(int j = 0; j < i; j++)
        {
            File file = afile[j];
            if(file == null)
                throw new IllegalArgumentException("file array contains null");
            if(iofilefilter.accept(file))
                arraylist.add(file);
        }

        return (File[])arraylist.toArray(new File[arraylist.size()]);
    }

    public static List filterList(IOFileFilter iofilefilter, Iterable iterable)
    {
        return (List)filter(iofilefilter, iterable, new ArrayList());
    }

    public static transient List filterList(IOFileFilter iofilefilter, File afile[])
    {
        return Arrays.asList(filter(iofilefilter, afile));
    }

    public static Set filterSet(IOFileFilter iofilefilter, Iterable iterable)
    {
        return (Set)filter(iofilefilter, iterable, new HashSet());
    }

    public static transient Set filterSet(IOFileFilter iofilefilter, File afile[])
    {
        return new HashSet(Arrays.asList(filter(iofilefilter, afile)));
    }

    public static IOFileFilter magicNumberFileFilter(String s)
    {
        return new MagicNumberFileFilter(s);
    }

    public static IOFileFilter magicNumberFileFilter(String s, long l)
    {
        return new MagicNumberFileFilter(s, l);
    }

    public static IOFileFilter magicNumberFileFilter(byte abyte0[])
    {
        return new MagicNumberFileFilter(abyte0);
    }

    public static IOFileFilter magicNumberFileFilter(byte abyte0[], long l)
    {
        return new MagicNumberFileFilter(abyte0, l);
    }

    public static IOFileFilter makeCVSAware(IOFileFilter iofilefilter)
    {
        if(iofilefilter == null)
        {
            return cvsFilter;
        } else
        {
            IOFileFilter aiofilefilter[] = new IOFileFilter[2];
            aiofilefilter[0] = iofilefilter;
            aiofilefilter[1] = cvsFilter;
            return and(aiofilefilter);
        }
    }

    public static IOFileFilter makeDirectoryOnly(IOFileFilter iofilefilter)
    {
        if(iofilefilter == null)
            return DirectoryFileFilter.DIRECTORY;
        else
            return new AndFileFilter(DirectoryFileFilter.DIRECTORY, iofilefilter);
    }

    public static IOFileFilter makeFileOnly(IOFileFilter iofilefilter)
    {
        if(iofilefilter == null)
            return FileFileFilter.FILE;
        else
            return new AndFileFilter(FileFileFilter.FILE, iofilefilter);
    }

    public static IOFileFilter makeSVNAware(IOFileFilter iofilefilter)
    {
        if(iofilefilter == null)
        {
            return svnFilter;
        } else
        {
            IOFileFilter aiofilefilter[] = new IOFileFilter[2];
            aiofilefilter[0] = iofilefilter;
            aiofilefilter[1] = svnFilter;
            return and(aiofilefilter);
        }
    }

    public static IOFileFilter nameFileFilter(String s)
    {
        return new NameFileFilter(s);
    }

    public static IOFileFilter nameFileFilter(String s, IOCase iocase)
    {
        return new NameFileFilter(s, iocase);
    }

    public static IOFileFilter notFileFilter(IOFileFilter iofilefilter)
    {
        return new NotFileFilter(iofilefilter);
    }

    public static transient IOFileFilter or(IOFileFilter aiofilefilter[])
    {
        return new OrFileFilter(toList(aiofilefilter));
    }

    public static IOFileFilter orFileFilter(IOFileFilter iofilefilter, IOFileFilter iofilefilter1)
    {
        return new OrFileFilter(iofilefilter, iofilefilter1);
    }

    public static IOFileFilter prefixFileFilter(String s)
    {
        return new PrefixFileFilter(s);
    }

    public static IOFileFilter prefixFileFilter(String s, IOCase iocase)
    {
        return new PrefixFileFilter(s, iocase);
    }

    public static IOFileFilter sizeFileFilter(long l)
    {
        return new SizeFileFilter(l);
    }

    public static IOFileFilter sizeFileFilter(long l, boolean flag)
    {
        return new SizeFileFilter(l, flag);
    }

    public static IOFileFilter sizeRangeFileFilter(long l, long l1)
    {
        return new AndFileFilter(new SizeFileFilter(l, true), new SizeFileFilter(1L + l1, false));
    }

    public static IOFileFilter suffixFileFilter(String s)
    {
        return new SuffixFileFilter(s);
    }

    public static IOFileFilter suffixFileFilter(String s, IOCase iocase)
    {
        return new SuffixFileFilter(s, iocase);
    }

    public static transient List toList(IOFileFilter aiofilefilter[])
    {
        if(aiofilefilter == null)
            throw new IllegalArgumentException("The filters must not be null");
        ArrayList arraylist = new ArrayList(aiofilefilter.length);
        for(int i = 0; i < aiofilefilter.length; i++)
        {
            if(aiofilefilter[i] == null)
                throw new IllegalArgumentException((new StringBuilder()).append("The filter[").append(i).append("] is null").toString());
            arraylist.add(aiofilefilter[i]);
        }

        return arraylist;
    }

    public static IOFileFilter trueFileFilter()
    {
        return TrueFileFilter.TRUE;
    }

    private static final IOFileFilter cvsFilter;
    private static final IOFileFilter svnFilter;

    static 
    {
        IOFileFilter aiofilefilter[] = new IOFileFilter[2];
        aiofilefilter[0] = directoryFileFilter();
        aiofilefilter[1] = nameFileFilter("CVS");
        cvsFilter = notFileFilter(and(aiofilefilter));
        IOFileFilter aiofilefilter1[] = new IOFileFilter[2];
        aiofilefilter1[0] = directoryFileFilter();
        aiofilefilter1[1] = nameFileFilter(".svn");
        svnFilter = notFileFilter(and(aiofilefilter1));
    }
}
