// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.activation;

import java.io.*;
import myjava.awt.datatransfer.DataFlavor;
import myjava.awt.datatransfer.UnsupportedFlavorException;

// Referenced classes of package javax.activation:
//            DataContentHandler, ActivationDataFlavor, UnsupportedDataTypeException, DataSource

class ObjectDataContentHandler
    implements DataContentHandler
{

    public ObjectDataContentHandler(DataContentHandler datacontenthandler, Object obj1, String s)
    {
        transferFlavors = null;
        dch = null;
        obj = obj1;
        mimeType = s;
        dch = datacontenthandler;
    }

    public Object getContent(DataSource datasource)
    {
        return obj;
    }

    public DataContentHandler getDCH()
    {
        return dch;
    }

    public Object getTransferData(DataFlavor dataflavor, DataSource datasource)
        throws UnsupportedFlavorException, IOException
    {
        if(dch != null)
            return dch.getTransferData(dataflavor, datasource);
        if(dataflavor.equals(getTransferDataFlavors()[0]))
            return obj;
        else
            throw new UnsupportedFlavorException(dataflavor);
    }

    public DataFlavor[] getTransferDataFlavors()
    {
        this;
        JVM INSTR monitorenter ;
        if(transferFlavors == null)
        {
            if(dch == null)
                break MISSING_BLOCK_LABEL_38;
            transferFlavors = dch.getTransferDataFlavors();
        }
_L1:
        DataFlavor adataflavor[] = transferFlavors;
        this;
        JVM INSTR monitorexit ;
        return adataflavor;
        transferFlavors = new DataFlavor[1];
        transferFlavors[0] = new ActivationDataFlavor(obj.getClass(), mimeType, mimeType);
          goto _L1
        Exception exception;
        exception;
        throw exception;
    }

    public void writeTo(Object obj1, String s, OutputStream outputstream)
        throws IOException
    {
        if(dch != null)
        {
            dch.writeTo(obj1, s, outputstream);
            return;
        }
        if(obj1 instanceof byte[])
        {
            outputstream.write((byte[])obj1);
            return;
        }
        if(obj1 instanceof String)
        {
            OutputStreamWriter outputstreamwriter = new OutputStreamWriter(outputstream);
            outputstreamwriter.write((String)obj1);
            outputstreamwriter.flush();
            return;
        } else
        {
            throw new UnsupportedDataTypeException((new StringBuilder("no object DCH for MIME type ")).append(mimeType).toString());
        }
    }

    private DataContentHandler dch;
    private String mimeType;
    private Object obj;
    private DataFlavor transferFlavors[];
}
