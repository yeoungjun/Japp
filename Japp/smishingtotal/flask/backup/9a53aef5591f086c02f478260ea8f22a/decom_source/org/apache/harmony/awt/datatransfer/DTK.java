// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.harmony.awt.datatransfer;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.SystemFlavorMap;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DropTargetContext;
import java.awt.dnd.peer.DragSourceContextPeer;
import java.awt.dnd.peer.DropTargetContextPeer;
import java.nio.charset.Charset;
import org.apache.harmony.awt.ContextStorage;
import org.apache.harmony.awt.internal.nls.Messages;
import org.apache.harmony.misc.SystemUtils;

// Referenced classes of package org.apache.harmony.awt.datatransfer:
//            DataTransferThread, TextFlavor, DataProvider, NativeClipboard

public abstract class DTK
{

    protected DTK()
    {
        nativeClipboard = null;
        nativeSelection = null;
        dataTransferThread.start();
    }

    private static DTK createDTK()
    {
        String s;
        switch(SystemUtils.getOS())
        {
        default:
            throw new RuntimeException(Messages.getString("awt.4E"));

        case 2: // '\002'
            break MISSING_BLOCK_LABEL_53;

        case 1: // '\001'
            s = "org.apache.harmony.awt.datatransfer.windows.WinDTK";
            break;
        }
_L1:
        DTK dtk;
        try
        {
            dtk = (DTK)Class.forName(s).newInstance();
        }
        catch(Exception exception)
        {
            throw new RuntimeException(exception);
        }
        return dtk;
        s = "org.apache.harmony.awt.datatransfer.linux.LinuxDTK";
          goto _L1
    }

    public static DTK getDTK()
    {
label0:
        {
            synchronized(ContextStorage.getContextLock())
            {
                if(!ContextStorage.shutdownPending())
                    break label0;
            }
            return null;
        }
        DTK dtk = ContextStorage.getDTK();
        if(dtk != null)
            break MISSING_BLOCK_LABEL_32;
        dtk = createDTK();
        ContextStorage.setDTK(dtk);
        obj;
        JVM INSTR monitorexit ;
        return dtk;
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
    }

    protected void appendSystemFlavorMap(SystemFlavorMap systemflavormap, DataFlavor dataflavor, String s)
    {
        systemflavormap.addFlavorForUnencodedNative(s, dataflavor);
        systemflavormap.addUnencodedNativeForFlavor(dataflavor, s);
    }

    protected void appendSystemFlavorMap(SystemFlavorMap systemflavormap, String as[], String s, String s1)
    {
        TextFlavor.addUnicodeClasses(systemflavormap, s1, s);
        int i = 0;
        do
        {
            if(i >= as.length)
                return;
            if(as[i] != null && Charset.isSupported(as[i]))
                TextFlavor.addCharsetClasses(systemflavormap, s1, s, as[i]);
            i++;
        } while(true);
    }

    public abstract DragSourceContextPeer createDragSourceContextPeer(DragGestureEvent draggestureevent);

    public abstract DropTargetContextPeer createDropTargetContextPeer(DropTargetContext droptargetcontext);

    protected String[] getCharsets()
    {
        return (new String[] {
            "UTF-16", "UTF-8", "unicode", "ISO-8859-1", "US-ASCII"
        });
    }

    public String getDefaultCharset()
    {
        return "unicode";
    }

    public NativeClipboard getNativeClipboard()
    {
        if(nativeClipboard == null)
            nativeClipboard = newNativeClipboard();
        return nativeClipboard;
    }

    public NativeClipboard getNativeSelection()
    {
        if(nativeSelection == null)
            nativeSelection = newNativeSelection();
        return nativeSelection;
    }

    public SystemFlavorMap getSystemFlavorMap()
    {
        this;
        JVM INSTR monitorenter ;
        SystemFlavorMap systemflavormap = systemFlavorMap;
        this;
        JVM INSTR monitorexit ;
        return systemflavormap;
        Exception exception;
        exception;
        throw exception;
    }

    public abstract void initDragAndDrop();

    public void initSystemFlavorMap(SystemFlavorMap systemflavormap)
    {
        String as[] = getCharsets();
        appendSystemFlavorMap(systemflavormap, DataFlavor.stringFlavor, "text/plain");
        appendSystemFlavorMap(systemflavormap, as, "plain", "text/plain");
        appendSystemFlavorMap(systemflavormap, as, "html", "text/html");
        appendSystemFlavorMap(systemflavormap, DataProvider.urlFlavor, "application/x-java-url");
        appendSystemFlavorMap(systemflavormap, as, "uri-list", "application/x-java-url");
        appendSystemFlavorMap(systemflavormap, DataFlavor.javaFileListFlavor, "application/x-java-file-list");
        appendSystemFlavorMap(systemflavormap, DataFlavor.imageFlavor, "image/x-java-image");
    }

    protected abstract NativeClipboard newNativeClipboard();

    protected abstract NativeClipboard newNativeSelection();

    public abstract void runEventLoop();

    public void setSystemFlavorMap(SystemFlavorMap systemflavormap)
    {
        this;
        JVM INSTR monitorenter ;
        systemFlavorMap = systemflavormap;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    protected final DataTransferThread dataTransferThread = new DataTransferThread(this);
    private NativeClipboard nativeClipboard;
    private NativeClipboard nativeSelection;
    protected SystemFlavorMap systemFlavorMap;
}
