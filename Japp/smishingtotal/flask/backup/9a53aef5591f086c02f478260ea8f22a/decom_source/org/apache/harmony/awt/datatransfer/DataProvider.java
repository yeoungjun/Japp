// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.harmony.awt.datatransfer;

import java.awt.datatransfer.DataFlavor;

// Referenced classes of package org.apache.harmony.awt.datatransfer:
//            RawBitmap

public interface DataProvider
{

    public abstract String[] getFileList();

    public abstract String getHTML();

    public abstract String[] getNativeFormats();

    public abstract RawBitmap getRawBitmap();

    public abstract byte[] getSerializedObject(Class class1);

    public abstract String getText();

    public abstract String getURL();

    public abstract boolean isNativeFormatAvailable(String s);

    public static final String FORMAT_FILE_LIST = "application/x-java-file-list";
    public static final String FORMAT_HTML = "text/html";
    public static final String FORMAT_IMAGE = "image/x-java-image";
    public static final String FORMAT_TEXT = "text/plain";
    public static final String FORMAT_URL = "application/x-java-url";
    public static final String TYPE_FILELIST = "application/x-java-file-list";
    public static final String TYPE_HTML = "text/html";
    public static final String TYPE_IMAGE = "image/x-java-image";
    public static final String TYPE_PLAINTEXT = "text/plain";
    public static final String TYPE_SERIALIZED = "application/x-java-serialized-object";
    public static final String TYPE_TEXTENCODING = "application/x-java-text-encoding";
    public static final String TYPE_URILIST = "text/uri-list";
    public static final String TYPE_URL = "application/x-java-url";
    public static final DataFlavor uriFlavor = new DataFlavor("text/uri-list", "URI");
    public static final DataFlavor urlFlavor = new DataFlavor("application/x-java-url;class=java.net.URL", "URL");

}
