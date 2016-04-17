// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;

// Referenced classes of package org.apache.commons.io.filefilter:
//            AbstractFileFilter, IOFileFilter

public class FileFileFilter extends AbstractFileFilter
    implements Serializable
{

    protected FileFileFilter()
    {
    }

    public boolean accept(File file)
    {
        return file.isFile();
    }

    public static final IOFileFilter FILE = new FileFileFilter();

}
