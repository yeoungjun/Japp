// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.activation;

import java.io.IOException;
import java.io.OutputStream;
import myjava.awt.datatransfer.DataFlavor;
import myjava.awt.datatransfer.UnsupportedFlavorException;

// Referenced classes of package javax.activation:
//            DataSource

public interface DataContentHandler
{

    public abstract Object getContent(DataSource datasource)
        throws IOException;

    public abstract Object getTransferData(DataFlavor dataflavor, DataSource datasource)
        throws UnsupportedFlavorException, IOException;

    public abstract DataFlavor[] getTransferDataFlavors();

    public abstract void writeTo(Object obj, String s, OutputStream outputstream)
        throws IOException;
}
