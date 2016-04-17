// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.harmony.awt.datatransfer;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.SystemFlavorMap;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package org.apache.harmony.awt.datatransfer:
//            DataProvider, RawBitmap

public class DataSource
    implements DataProvider
{

    public DataSource(Transferable transferable)
    {
        contents = transferable;
    }

    private RawBitmap getImageBitmap(Image image)
    {
        if(!(image instanceof BufferedImage)) goto _L2; else goto _L1
_L1:
        BufferedImage bufferedimage1 = (BufferedImage)image;
        if(bufferedimage1.getType() != 1) goto _L2; else goto _L3
_L3:
        RawBitmap rawbitmap = getImageBitmap32(bufferedimage1);
_L5:
        return rawbitmap;
_L2:
        int i = image.getWidth(null);
        int j = image.getHeight(null);
        rawbitmap = null;
        if(i > 0)
        {
            rawbitmap = null;
            if(j > 0)
            {
                BufferedImage bufferedimage = new BufferedImage(i, j, 1);
                Graphics g = bufferedimage.getGraphics();
                g.drawImage(image, 0, 0, null);
                g.dispose();
                return getImageBitmap32(bufferedimage);
            }
        }
        if(true) goto _L5; else goto _L4
_L4:
    }

    private RawBitmap getImageBitmap32(BufferedImage bufferedimage)
    {
        int ai[] = new int[bufferedimage.getWidth() * bufferedimage.getHeight()];
        DataBufferInt databufferint = (DataBufferInt)bufferedimage.getRaster().getDataBuffer();
        int i = 0;
        int j = databufferint.getNumBanks();
        int ai1[] = databufferint.getOffsets();
        int k = 0;
        do
        {
            if(k >= j)
                return new RawBitmap(bufferedimage.getWidth(), bufferedimage.getHeight(), bufferedimage.getWidth(), 32, 0xff0000, 65280, 255, ai);
            int ai2[] = databufferint.getData(k);
            System.arraycopy(ai2, ai1[k], ai, i, ai2.length - ai1[k]);
            i += ai2.length - ai1[k];
            k++;
        } while(true);
    }

    private static java.util.List getNativesForFlavors(DataFlavor adataflavor[])
    {
        ArrayList arraylist;
        SystemFlavorMap systemflavormap;
        int i;
        arraylist = new ArrayList();
        systemflavormap = (SystemFlavorMap)SystemFlavorMap.getDefaultFlavorMap();
        i = 0;
_L2:
        if(i >= adataflavor.length)
            return arraylist;
        Iterator iterator = systemflavormap.getNativesForFlavor(adataflavor[i]).iterator();
        do
        {
label0:
            {
                if(iterator.hasNext())
                    break label0;
                i++;
            }
            if(true)
                continue;
            String s = (String)iterator.next();
            if(!arraylist.contains(s))
                arraylist.add(s);
        } while(true);
        if(true) goto _L2; else goto _L1
_L1:
    }

    private String getText(boolean flag)
    {
        DataFlavor adataflavor[];
        int i;
        adataflavor = contents.getTransferDataFlavors();
        i = 0;
_L2:
        DataFlavor dataflavor;
        if(i >= adataflavor.length)
            return null;
        dataflavor = adataflavor[i];
          goto _L1
_L4:
        i++;
        if(true) goto _L2; else goto _L1
_L1:
        if(!dataflavor.isFlavorTextType() || flag && !isHtmlFlavor(dataflavor)) goto _L4; else goto _L3
_L3:
        String s;
        if(java/lang/String.isAssignableFrom(dataflavor.getRepresentationClass()))
            return (String)contents.getTransferData(dataflavor);
        s = getTextFromReader(dataflavor.getReaderForText(contents));
        return s;
        Exception exception;
        exception;
          goto _L4
    }

    private String getTextFromReader(Reader reader)
        throws IOException
    {
        StringBuilder stringbuilder = new StringBuilder();
        char ac[] = new char[1024];
        do
        {
            int i = reader.read(ac);
            if(i <= 0)
                return stringbuilder.toString();
            stringbuilder.append(ac, 0, i);
        } while(true);
    }

    private boolean isHtmlFlavor(DataFlavor dataflavor)
    {
        return "html".equalsIgnoreCase(dataflavor.getSubType());
    }

    protected DataFlavor[] getDataFlavors()
    {
        if(flavors == null)
            flavors = contents.getTransferDataFlavors();
        return flavors;
    }

    public String[] getFileList()
    {
        String as[];
        try
        {
            java.util.List list = (java.util.List)contents.getTransferData(DataFlavor.javaFileListFlavor);
            as = (String[])list.toArray(new String[list.size()]);
        }
        catch(Exception exception)
        {
            return null;
        }
        return as;
    }

    public String getHTML()
    {
        return getText(true);
    }

    public String[] getNativeFormats()
    {
        return (String[])getNativeFormatsList().toArray(new String[0]);
    }

    public java.util.List getNativeFormatsList()
    {
        if(nativeFormats == null)
            nativeFormats = getNativesForFlavors(getDataFlavors());
        return nativeFormats;
    }

    public RawBitmap getRawBitmap()
    {
        DataFlavor adataflavor[];
        int i;
        adataflavor = contents.getTransferDataFlavors();
        i = 0;
_L2:
        DataFlavor dataflavor;
        if(i >= adataflavor.length)
            return null;
        dataflavor = adataflavor[i];
        Class class1 = dataflavor.getRepresentationClass();
        if(class1 == null || !java/awt/Image.isAssignableFrom(class1) || !dataflavor.isMimeTypeEqual(DataFlavor.imageFlavor) && !dataflavor.isFlavorSerializedObjectType())
            break MISSING_BLOCK_LABEL_86;
        RawBitmap rawbitmap = getImageBitmap((Image)contents.getTransferData(dataflavor));
        return rawbitmap;
        Throwable throwable;
        throwable;
        i++;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public byte[] getSerializedObject(Class class1)
    {
        byte abyte0[];
        try
        {
            DataFlavor dataflavor = new DataFlavor(class1, null);
            Serializable serializable = (Serializable)contents.getTransferData(dataflavor);
            ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
            (new ObjectOutputStream(bytearrayoutputstream)).writeObject(serializable);
            abyte0 = bytearrayoutputstream.toByteArray();
        }
        catch(Throwable throwable)
        {
            return null;
        }
        return abyte0;
    }

    public String getText()
    {
        return getText(false);
    }

    public String getURL()
    {
        String s2;
        try
        {
            s2 = ((URL)contents.getTransferData(urlFlavor)).toString();
        }
        catch(Exception exception)
        {
            String s1;
            try
            {
                s1 = ((URL)contents.getTransferData(uriFlavor)).toString();
            }
            catch(Exception exception1)
            {
                String s;
                try
                {
                    s = (new URL(getText())).toString();
                }
                catch(Exception exception2)
                {
                    return null;
                }
                return s;
            }
            return s1;
        }
        return s2;
    }

    public boolean isNativeFormatAvailable(String s)
    {
        return getNativeFormatsList().contains(s);
    }

    protected final Transferable contents;
    private DataFlavor flavors[];
    private java.util.List nativeFormats;
}
