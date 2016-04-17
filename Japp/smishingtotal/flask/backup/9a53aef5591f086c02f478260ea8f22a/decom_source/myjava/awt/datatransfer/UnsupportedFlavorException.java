// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package myjava.awt.datatransfer;


// Referenced classes of package myjava.awt.datatransfer:
//            DataFlavor

public class UnsupportedFlavorException extends Exception
{

    public UnsupportedFlavorException(DataFlavor dataflavor)
    {
        super((new StringBuilder("flavor = ")).append(String.valueOf(dataflavor)).toString());
    }

    private static final long serialVersionUID = 0x4ab7272ac88f5cc1L;
}
