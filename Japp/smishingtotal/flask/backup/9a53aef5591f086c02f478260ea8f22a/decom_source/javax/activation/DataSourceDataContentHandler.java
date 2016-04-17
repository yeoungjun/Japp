// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.activation;

import java.io.IOException;
import java.io.OutputStream;
import myjava.awt.datatransfer.DataFlavor;
import myjava.awt.datatransfer.UnsupportedFlavorException;

// Referenced classes of package javax.activation:
//            DataContentHandler, DataSource, ActivationDataFlavor, UnsupportedDataTypeException

class DataSourceDataContentHandler
    implements DataContentHandler
{

    public DataSourceDataContentHandler(DataContentHandler datacontenthandler, DataSource datasource)
    {
        ds = null;
        transferFlavors = null;
        dch = null;
        ds = datasource;
        dch = datacontenthandler;
    }

    public Object getContent(DataSource datasource)
        throws IOException
    {
        if(dch != null)
            return dch.getContent(datasource);
        else
            return datasource.getInputStream();
    }

    public Object getTransferData(DataFlavor dataflavor, DataSource datasource)
        throws UnsupportedFlavorException, IOException
    {
        if(dch != null)
            return dch.getTransferData(dataflavor, datasource);
        if(dataflavor.equals(getTransferDataFlavors()[0]))
            return datasource.getInputStream();
        else
            throw new UnsupportedFlavorException(dataflavor);
    }

    public DataFlavor[] getTransferDataFlavors()
    {
        if(transferFlavors == null)
            if(dch != null)
            {
                transferFlavors = dch.getTransferDataFlavors();
            } else
            {
                transferFlavors = new DataFlavor[1];
                transferFlavors[0] = new ActivationDataFlavor(ds.getContentType(), ds.getContentType());
            }
        return transferFlavors;
    }

    public void writeTo(Object obj, String s, OutputStream outputstream)
        throws IOException
    {
        if(dch != null)
        {
            dch.writeTo(obj, s, outputstream);
            return;
        } else
        {
            throw new UnsupportedDataTypeException((new StringBuilder("no DCH for content type ")).append(ds.getContentType()).toString());
        }
    }

    private DataContentHandler dch;
    private DataSource ds;
    private DataFlavor transferFlavors[];
}
