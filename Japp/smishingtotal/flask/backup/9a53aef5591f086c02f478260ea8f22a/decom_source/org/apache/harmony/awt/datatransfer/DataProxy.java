// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.harmony.awt.datatransfer;

import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.SystemFlavorMap;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.DataBufferUShort;
import java.awt.image.DirectColorModel;
import java.awt.image.Raster;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import org.apache.harmony.awt.internal.nls.Messages;

// Referenced classes of package org.apache.harmony.awt.datatransfer:
//            RawBitmap, DataProvider

public final class DataProxy
    implements Transferable
{

    public DataProxy(DataProvider dataprovider)
    {
        data = dataprovider;
    }

    private BufferedImage createBufferedImage(RawBitmap rawbitmap)
    {
        Object obj;
        java.awt.image.WritableRaster writableraster;
        if(rawbitmap == null || rawbitmap.buffer == null || rawbitmap.width <= 0 || rawbitmap.height <= 0)
            return null;
        if(rawbitmap.bits == 32 && (rawbitmap.buffer instanceof int[]))
        {
            if(!isRGB(rawbitmap) && !isBGR(rawbitmap))
                return null;
            int ai3[] = new int[3];
            ai3[0] = rawbitmap.rMask;
            ai3[1] = rawbitmap.gMask;
            ai3[2] = rawbitmap.bMask;
            int ai4[] = (int[])rawbitmap.buffer;
            DirectColorModel directcolormodel = new DirectColorModel(24, rawbitmap.rMask, rawbitmap.gMask, rawbitmap.bMask);
            writableraster = Raster.createPackedRaster(new DataBufferInt(ai4, ai4.length), rawbitmap.width, rawbitmap.height, rawbitmap.stride, ai3, null);
            obj = directcolormodel;
        } else
        {
label0:
            {
                if(rawbitmap.bits != 24 || !(rawbitmap.buffer instanceof byte[]))
                    break label0;
                int ai1[] = {
                    8, 8, 8
                };
                int ai2[];
                byte abyte0[];
                if(isRGB(rawbitmap))
                {
                    ai2 = new int[3];
                    ai2[1] = 1;
                    ai2[2] = 2;
                } else
                if(isBGR(rawbitmap))
                {
                    ai2 = new int[3];
                    ai2[0] = 2;
                    ai2[1] = 1;
                } else
                {
                    return null;
                }
                abyte0 = (byte[])rawbitmap.buffer;
                obj = new ComponentColorModel(ColorSpace.getInstance(1000), ai1, false, false, 1, 0);
                writableraster = Raster.createInterleavedRaster(new DataBufferByte(abyte0, abyte0.length), rawbitmap.width, rawbitmap.height, rawbitmap.stride, 3, ai2, null);
            }
        }
_L3:
        int i;
        if(obj == null || writableraster == null)
            return null;
        else
            return new BufferedImage(((java.awt.image.ColorModel) (obj)), writableraster, false, null);
        if(rawbitmap.bits == 16) goto _L2; else goto _L1
_L1:
        i = rawbitmap.bits;
        obj = null;
        writableraster = null;
        if(i != 15) goto _L3; else goto _L2
_L2:
        boolean flag = rawbitmap.buffer instanceof short[];
        obj = null;
        writableraster = null;
        if(flag)
        {
            int ai[] = new int[3];
            ai[0] = rawbitmap.rMask;
            ai[1] = rawbitmap.gMask;
            ai[2] = rawbitmap.bMask;
            short aword0[] = (short[])rawbitmap.buffer;
            obj = new DirectColorModel(rawbitmap.bits, rawbitmap.rMask, rawbitmap.gMask, rawbitmap.bMask);
            writableraster = Raster.createPackedRaster(new DataBufferUShort(aword0, aword0.length), rawbitmap.width, rawbitmap.height, rawbitmap.stride, ai, null);
        }
          goto _L3
    }

    private String getCharset(DataFlavor dataflavor)
    {
        return dataflavor.getParameter("charset");
    }

    private Object getFileList(DataFlavor dataflavor)
        throws IOException, UnsupportedFlavorException
    {
        if(!data.isNativeFormatAvailable("application/x-java-file-list"))
            throw new UnsupportedFlavorException(dataflavor);
        String as[] = data.getFileList();
        if(as == null)
            throw new IOException(Messages.getString("awt.4F"));
        else
            return Arrays.asList(as);
    }

    private Object getHTML(DataFlavor dataflavor)
        throws IOException, UnsupportedFlavorException
    {
        if(!data.isNativeFormatAvailable("text/html"))
            throw new UnsupportedFlavorException(dataflavor);
        String s = data.getHTML();
        if(s == null)
            throw new IOException(Messages.getString("awt.4F"));
        else
            return getTextRepresentation(s, dataflavor);
    }

    private Image getImage(DataFlavor dataflavor)
        throws IOException, UnsupportedFlavorException
    {
        if(!data.isNativeFormatAvailable("image/x-java-image"))
            throw new UnsupportedFlavorException(dataflavor);
        RawBitmap rawbitmap = data.getRawBitmap();
        if(rawbitmap == null)
            throw new IOException(Messages.getString("awt.4F"));
        else
            return createBufferedImage(rawbitmap);
    }

    private Object getPlainText(DataFlavor dataflavor)
        throws IOException, UnsupportedFlavorException
    {
        if(!data.isNativeFormatAvailable("text/plain"))
            throw new UnsupportedFlavorException(dataflavor);
        String s = data.getText();
        if(s == null)
            throw new IOException(Messages.getString("awt.4F"));
        else
            return getTextRepresentation(s, dataflavor);
    }

    private Object getSerializedObject(DataFlavor dataflavor)
        throws IOException, UnsupportedFlavorException
    {
        String s = SystemFlavorMap.encodeDataFlavor(dataflavor);
        if(s == null || !data.isNativeFormatAvailable(s))
            throw new UnsupportedFlavorException(dataflavor);
        byte abyte0[] = data.getSerializedObject(dataflavor.getRepresentationClass());
        if(abyte0 == null)
            throw new IOException(Messages.getString("awt.4F"));
        ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(abyte0);
        Object obj;
        try
        {
            obj = (new ObjectInputStream(bytearrayinputstream)).readObject();
        }
        catch(ClassNotFoundException classnotfoundexception)
        {
            throw new IOException(classnotfoundexception.getMessage());
        }
        return obj;
    }

    private Object getTextRepresentation(String s, DataFlavor dataflavor)
        throws UnsupportedFlavorException, IOException
    {
        if(dataflavor.getRepresentationClass() == java/lang/String)
            return s;
        if(dataflavor.isRepresentationClassReader())
            return new StringReader(s);
        if(dataflavor.isRepresentationClassCharBuffer())
            return CharBuffer.wrap(s);
        if(dataflavor.getRepresentationClass() == [C)
        {
            char ac[] = new char[s.length()];
            s.getChars(0, s.length(), ac, 0);
            return ac;
        }
        String s1 = getCharset(dataflavor);
        if(dataflavor.getRepresentationClass() == [B)
            return s.getBytes(s1);
        if(dataflavor.isRepresentationClassByteBuffer())
            return ByteBuffer.wrap(s.getBytes(s1));
        if(dataflavor.isRepresentationClassInputStream())
            return new ByteArrayInputStream(s.getBytes(s1));
        else
            throw new UnsupportedFlavorException(dataflavor);
    }

    private Object getURL(DataFlavor dataflavor)
        throws IOException, UnsupportedFlavorException
    {
        if(!data.isNativeFormatAvailable("application/x-java-url"))
            throw new UnsupportedFlavorException(dataflavor);
        String s = data.getURL();
        if(s == null)
            throw new IOException(Messages.getString("awt.4F"));
        URL url = new URL(s);
        if(dataflavor.getRepresentationClass().isAssignableFrom(java/net/URL))
            return url;
        if(dataflavor.isFlavorTextType())
            return getTextRepresentation(url.toString(), dataflavor);
        else
            throw new UnsupportedFlavorException(dataflavor);
    }

    private boolean isBGR(RawBitmap rawbitmap)
    {
        return rawbitmap.rMask == 255 && rawbitmap.gMask == 65280 && rawbitmap.bMask == 0xff0000;
    }

    private boolean isRGB(RawBitmap rawbitmap)
    {
        return rawbitmap.rMask == 0xff0000 && rawbitmap.gMask == 65280 && rawbitmap.bMask == 255;
    }

    public DataProvider getDataProvider()
    {
        return data;
    }

    public Object getTransferData(DataFlavor dataflavor)
        throws UnsupportedFlavorException, IOException
    {
        String s = (new StringBuilder(String.valueOf(dataflavor.getPrimaryType()))).append("/").append(dataflavor.getSubType()).toString();
        if(dataflavor.isFlavorTextType())
        {
            if(s.equalsIgnoreCase("text/html"))
                return getHTML(dataflavor);
            if(s.equalsIgnoreCase("text/uri-list"))
                return getURL(dataflavor);
            else
                return getPlainText(dataflavor);
        }
        if(dataflavor.isFlavorJavaFileListType())
            return getFileList(dataflavor);
        if(dataflavor.isFlavorSerializedObjectType())
            return getSerializedObject(dataflavor);
        if(dataflavor.equals(DataProvider.urlFlavor))
            return getURL(dataflavor);
        if(s.equalsIgnoreCase("image/x-java-image") && java/awt/Image.isAssignableFrom(dataflavor.getRepresentationClass()))
            return getImage(dataflavor);
        else
            throw new UnsupportedFlavorException(dataflavor);
    }

    public DataFlavor[] getTransferDataFlavors()
    {
        ArrayList arraylist;
        String as[];
        int i;
        arraylist = new ArrayList();
        as = data.getNativeFormats();
        i = 0;
_L2:
        if(i >= as.length)
            return (DataFlavor[])arraylist.toArray(new DataFlavor[arraylist.size()]);
        Iterator iterator = flavorMap.getFlavorsForNative(as[i]).iterator();
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
            DataFlavor dataflavor = (DataFlavor)iterator.next();
            if(!arraylist.contains(dataflavor))
                arraylist.add(dataflavor);
        } while(true);
        if(true) goto _L2; else goto _L1
_L1:
    }

    public boolean isDataFlavorSupported(DataFlavor dataflavor)
    {
        DataFlavor adataflavor[] = getTransferDataFlavors();
        int i = 0;
        do
        {
            if(i >= adataflavor.length)
                return false;
            if(adataflavor[i].equals(dataflavor))
                return true;
            i++;
        } while(true);
    }

    public static final Class charsetTextClasses[] = {
        [B, java/nio/ByteBuffer, java/io/InputStream
    };
    public static final Class unicodeTextClasses[] = {
        java/lang/String, java/io/Reader, java/nio/CharBuffer, [C
    };
    private final DataProvider data;
    private final SystemFlavorMap flavorMap = (SystemFlavorMap)SystemFlavorMap.getDefaultFlavorMap();

}
