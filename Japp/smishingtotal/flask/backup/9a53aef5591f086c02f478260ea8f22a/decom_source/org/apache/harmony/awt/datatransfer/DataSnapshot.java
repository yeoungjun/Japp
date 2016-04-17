// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.harmony.awt.datatransfer;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.SystemFlavorMap;
import java.util.*;

// Referenced classes of package org.apache.harmony.awt.datatransfer:
//            DataProvider, RawBitmap

public class DataSnapshot
    implements DataProvider
{

    public DataSnapshot(DataProvider dataprovider)
    {
        int i;
        nativeFormats = dataprovider.getNativeFormats();
        text = dataprovider.getText();
        fileList = dataprovider.getFileList();
        url = dataprovider.getURL();
        html = dataprovider.getHTML();
        rawBitmap = dataprovider.getRawBitmap();
        serializedObjects = Collections.synchronizedMap(new HashMap());
        i = 0;
_L2:
        if(i >= nativeFormats.length)
            return;
        DataFlavor dataflavor1 = SystemFlavorMap.decodeDataFlavor(nativeFormats[i]);
        DataFlavor dataflavor = dataflavor1;
_L3:
        if(dataflavor != null)
        {
            Class class1 = dataflavor.getRepresentationClass();
            byte abyte0[] = dataprovider.getSerializedObject(class1);
            if(abyte0 != null)
                serializedObjects.put(class1, abyte0);
        }
        i++;
        if(true) goto _L2; else goto _L1
_L1:
        ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        dataflavor = null;
          goto _L3
    }

    public String[] getFileList()
    {
        return fileList;
    }

    public String getHTML()
    {
        return html;
    }

    public String[] getNativeFormats()
    {
        return nativeFormats;
    }

    public RawBitmap getRawBitmap()
    {
        return rawBitmap;
    }

    public short[] getRawBitmapBuffer16()
    {
        if(rawBitmap != null && (rawBitmap.buffer instanceof short[]))
            return (short[])rawBitmap.buffer;
        else
            return null;
    }

    public int[] getRawBitmapBuffer32()
    {
        if(rawBitmap != null && (rawBitmap.buffer instanceof int[]))
            return (int[])rawBitmap.buffer;
        else
            return null;
    }

    public byte[] getRawBitmapBuffer8()
    {
        if(rawBitmap != null && (rawBitmap.buffer instanceof byte[]))
            return (byte[])rawBitmap.buffer;
        else
            return null;
    }

    public int[] getRawBitmapHeader()
    {
        if(rawBitmap != null)
            return rawBitmap.getHeader();
        else
            return null;
    }

    public byte[] getSerializedObject(Class class1)
    {
        return (byte[])serializedObjects.get(class1);
    }

    public byte[] getSerializedObject(String s)
    {
        byte abyte0[];
        try
        {
            abyte0 = getSerializedObject(SystemFlavorMap.decodeDataFlavor(s).getRepresentationClass());
        }
        catch(Exception exception)
        {
            return null;
        }
        return abyte0;
    }

    public String getText()
    {
        return text;
    }

    public String getURL()
    {
        return url;
    }

    public boolean isNativeFormatAvailable(String s)
    {
        if(s != null) goto _L2; else goto _L1
_L1:
        return false;
_L2:
        if(!s.equals("text/plain"))
            break; /* Loop/switch isn't completed */
        if(text != null)
            return true;
        if(true) goto _L1; else goto _L3
_L3:
        if(!s.equals("application/x-java-file-list"))
            break; /* Loop/switch isn't completed */
        if(fileList != null)
            return true;
        if(true) goto _L1; else goto _L4
_L4:
        if(!s.equals("application/x-java-url"))
            break; /* Loop/switch isn't completed */
        if(url != null)
            return true;
        if(true) goto _L1; else goto _L5
_L5:
        if(!s.equals("text/html"))
            break; /* Loop/switch isn't completed */
        if(html != null)
            return true;
        if(true) goto _L1; else goto _L6
_L6:
        if(s.equals("image/x-java-image"))
        {
            if(rawBitmap != null)
                return true;
        } else
        {
            boolean flag;
            try
            {
                DataFlavor dataflavor = SystemFlavorMap.decodeDataFlavor(s);
                flag = serializedObjects.containsKey(dataflavor.getRepresentationClass());
            }
            catch(Exception exception)
            {
                return false;
            }
            return flag;
        }
        if(true) goto _L1; else goto _L7
_L7:
    }

    private final String fileList[];
    private final String html;
    private final String nativeFormats[];
    private final RawBitmap rawBitmap;
    private final Map serializedObjects;
    private final String text;
    private final String url;
}
