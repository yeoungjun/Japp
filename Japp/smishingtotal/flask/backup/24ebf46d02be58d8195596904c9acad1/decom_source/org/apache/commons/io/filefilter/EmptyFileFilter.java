// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;

// Referenced classes of package org.apache.commons.io.filefilter:
//            AbstractFileFilter, NotFileFilter, IOFileFilter

public class EmptyFileFilter extends AbstractFileFilter
    implements Serializable
{

    protected EmptyFileFilter()
    {
    }

    public boolean accept(File file)
    {
        boolean flag;
label0:
        {
            boolean flag1;
label1:
            {
                flag = true;
                if(!file.isDirectory())
                    break label0;
                File afile[] = file.listFiles();
                if(afile != null)
                {
                    int i = afile.length;
                    flag1 = false;
                    if(i != 0)
                        break label1;
                }
                flag1 = flag;
            }
            return flag1;
        }
        if(file.length() != 0L)
            flag = false;
        return flag;
    }

    public static final IOFileFilter EMPTY;
    public static final IOFileFilter NOT_EMPTY;

    static 
    {
        EMPTY = new EmptyFileFilter();
        NOT_EMPTY = new NotFileFilter(EMPTY);
    }
}
