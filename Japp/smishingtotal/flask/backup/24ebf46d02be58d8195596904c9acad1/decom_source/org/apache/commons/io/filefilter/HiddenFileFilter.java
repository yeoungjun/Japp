// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;

// Referenced classes of package org.apache.commons.io.filefilter:
//            AbstractFileFilter, NotFileFilter, IOFileFilter

public class HiddenFileFilter extends AbstractFileFilter
    implements Serializable
{

    protected HiddenFileFilter()
    {
    }

    public boolean accept(File file)
    {
        return file.isHidden();
    }

    public static final IOFileFilter HIDDEN;
    public static final IOFileFilter VISIBLE;

    static 
    {
        HIDDEN = new HiddenFileFilter();
        VISIBLE = new NotFileFilter(HIDDEN);
    }
}
