// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;

// Referenced classes of package org.apache.commons.io.filefilter:
//            IOFileFilter

public class FalseFileFilter
    implements IOFileFilter, Serializable
{

    protected FalseFileFilter()
    {
    }

    public boolean accept(File file)
    {
        return false;
    }

    public boolean accept(File file, String s)
    {
        return false;
    }

    public static final IOFileFilter FALSE;
    public static final IOFileFilter INSTANCE;

    static 
    {
        FALSE = new FalseFileFilter();
        INSTANCE = FALSE;
    }
}
