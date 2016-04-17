// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package myjava.awt.datatransfer;

import java.io.IOException;

// Referenced classes of package myjava.awt.datatransfer:
//            UnsupportedFlavorException, DataFlavor

public interface Transferable
{

    public abstract Object getTransferData(DataFlavor dataflavor)
        throws UnsupportedFlavorException, IOException;

    public abstract DataFlavor[] getTransferDataFlavors();

    public abstract boolean isDataFlavorSupported(DataFlavor dataflavor);
}
