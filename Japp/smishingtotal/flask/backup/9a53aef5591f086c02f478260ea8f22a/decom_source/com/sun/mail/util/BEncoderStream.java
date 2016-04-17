// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.util;

import java.io.OutputStream;

// Referenced classes of package com.sun.mail.util:
//            BASE64EncoderStream

public class BEncoderStream extends BASE64EncoderStream
{

    public BEncoderStream(OutputStream outputstream)
    {
        super(outputstream, 0x7fffffff);
    }

    public static int encodedLength(byte abyte0[])
    {
        return 4 * ((2 + abyte0.length) / 3);
    }
}
