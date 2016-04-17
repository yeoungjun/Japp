// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;

// Referenced classes of package org.apache.commons.io.filefilter:
//            AbstractFileFilter, NotFileFilter, IOFileFilter

public class CanWriteFileFilter extends AbstractFileFilter
    implements Serializable
{

    protected CanWriteFileFilter()
    {
    }

    public boolean accept(File file)
    {
        return file.canWrite();
    }

    public static final IOFileFilter CANNOT_WRITE;
    public static final IOFileFilter CAN_WRITE;

    static 
    {
        CAN_WRITE = new CanWriteFileFilter();
        CANNOT_WRITE = new NotFileFilter(CAN_WRITE);
    }
}
