// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.handlers;

import javax.activation.ActivationDataFlavor;

// Referenced classes of package com.sun.mail.handlers:
//            text_plain

public class text_xml extends text_plain
{

    public text_xml()
    {
    }

    protected ActivationDataFlavor getDF()
    {
        return myDF;
    }

    private static ActivationDataFlavor myDF = new ActivationDataFlavor(java/lang/String, "text/xml", "XML String");

}
