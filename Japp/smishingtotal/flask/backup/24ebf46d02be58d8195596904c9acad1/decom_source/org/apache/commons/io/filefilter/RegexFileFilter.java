// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.IOCase;

// Referenced classes of package org.apache.commons.io.filefilter:
//            AbstractFileFilter

public class RegexFileFilter extends AbstractFileFilter
    implements Serializable
{

    public RegexFileFilter(String s)
    {
        if(s == null)
        {
            throw new IllegalArgumentException("Pattern is missing");
        } else
        {
            pattern = Pattern.compile(s);
            return;
        }
    }

    public RegexFileFilter(String s, int i)
    {
        if(s == null)
        {
            throw new IllegalArgumentException("Pattern is missing");
        } else
        {
            pattern = Pattern.compile(s, i);
            return;
        }
    }

    public RegexFileFilter(String s, IOCase iocase)
    {
        if(s == null)
            throw new IllegalArgumentException("Pattern is missing");
        byte byte0 = 0;
        if(iocase != null)
        {
            boolean flag = iocase.isCaseSensitive();
            byte0 = 0;
            if(!flag)
                byte0 = 2;
        }
        pattern = Pattern.compile(s, byte0);
    }

    public RegexFileFilter(Pattern pattern1)
    {
        if(pattern1 == null)
        {
            throw new IllegalArgumentException("Pattern is missing");
        } else
        {
            pattern = pattern1;
            return;
        }
    }

    public boolean accept(File file, String s)
    {
        return pattern.matcher(s).matches();
    }

    private final Pattern pattern;
}
