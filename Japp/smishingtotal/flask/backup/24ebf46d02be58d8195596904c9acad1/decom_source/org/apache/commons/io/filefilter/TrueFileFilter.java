// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;

// Referenced classes of package org.apache.commons.io.filefilter:
//            IOFileFilter

public class TrueFileFilter
    implements IOFileFilter, Serializable
{

    protected TrueFileFilter()
    {
    }

    public boolean accept(File file)
    {
        return true;
    }

    public boolean accept(File file, String s)
    {
        return true;
    }

    public static final IOFileFilter INSTANCE;
    public static final IOFileFilter TRUE;

    static 
    {
        TRUE = new TrueFileFilter();
        INSTANCE = TRUE;
    }
}
