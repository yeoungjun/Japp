// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.harmony.awt.datatransfer;

import java.awt.datatransfer.DataFlavor;
import java.util.Comparator;

public class FlavorsComparator
    implements Comparator
{

    public FlavorsComparator()
    {
    }

    public int compare(DataFlavor dataflavor, DataFlavor dataflavor1)
    {
        byte byte0 = -1;
        if(!dataflavor.isFlavorTextType() && !dataflavor1.isFlavorTextType())
            byte0 = 0;
        else
        if(dataflavor.isFlavorTextType() || !dataflavor1.isFlavorTextType())
        {
            if(dataflavor.isFlavorTextType() && !dataflavor1.isFlavorTextType())
                return 1;
            if(DataFlavor.selectBestTextFlavor(new DataFlavor[] {
    dataflavor, dataflavor1
}) != dataflavor)
                return 1;
        }
        return byte0;
    }

    public volatile int compare(Object obj, Object obj1)
    {
        return compare((DataFlavor)obj, (DataFlavor)obj1);
    }
}
