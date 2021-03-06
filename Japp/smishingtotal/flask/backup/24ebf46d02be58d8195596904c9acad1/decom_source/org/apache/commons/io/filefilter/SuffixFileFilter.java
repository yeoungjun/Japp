// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.io.IOCase;

// Referenced classes of package org.apache.commons.io.filefilter:
//            AbstractFileFilter

public class SuffixFileFilter extends AbstractFileFilter
    implements Serializable
{

    public SuffixFileFilter(String s)
    {
        this(s, IOCase.SENSITIVE);
    }

    public SuffixFileFilter(String s, IOCase iocase)
    {
        if(s == null)
            throw new IllegalArgumentException("The suffix must not be null");
        suffixes = (new String[] {
            s
        });
        if(iocase == null)
            iocase = IOCase.SENSITIVE;
        caseSensitivity = iocase;
    }

    public SuffixFileFilter(List list)
    {
        this(list, IOCase.SENSITIVE);
    }

    public SuffixFileFilter(List list, IOCase iocase)
    {
        if(list == null)
            throw new IllegalArgumentException("The list of suffixes must not be null");
        suffixes = (String[])list.toArray(new String[list.size()]);
        if(iocase == null)
            iocase = IOCase.SENSITIVE;
        caseSensitivity = iocase;
    }

    public SuffixFileFilter(String as[])
    {
        this(as, IOCase.SENSITIVE);
    }

    public SuffixFileFilter(String as[], IOCase iocase)
    {
        if(as == null)
            throw new IllegalArgumentException("The array of suffixes must not be null");
        suffixes = new String[as.length];
        System.arraycopy(as, 0, suffixes, 0, as.length);
        if(iocase == null)
            iocase = IOCase.SENSITIVE;
        caseSensitivity = iocase;
    }

    public boolean accept(File file)
    {
        String s = file.getName();
        String as[] = suffixes;
        int i = as.length;
        for(int j = 0; j < i; j++)
        {
            String s1 = as[j];
            if(caseSensitivity.checkEndsWith(s, s1))
                return true;
        }

        return false;
    }

    public boolean accept(File file, String s)
    {
        String as[] = suffixes;
        int i = as.length;
        for(int j = 0; j < i; j++)
        {
            String s1 = as[j];
            if(caseSensitivity.checkEndsWith(s, s1))
                return true;
        }

        return false;
    }

    public String toString()
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(super.toString());
        stringbuilder.append("(");
        if(suffixes != null)
        {
            for(int i = 0; i < suffixes.length; i++)
            {
                if(i > 0)
                    stringbuilder.append(",");
                stringbuilder.append(suffixes[i]);
            }

        }
        stringbuilder.append(")");
        return stringbuilder.toString();
    }

    private final IOCase caseSensitivity;
    private final String suffixes[];
}
