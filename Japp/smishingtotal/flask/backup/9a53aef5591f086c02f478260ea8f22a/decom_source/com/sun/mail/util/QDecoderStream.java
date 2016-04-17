// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.util;

import java.io.IOException;
import java.io.InputStream;

// Referenced classes of package com.sun.mail.util:
//            QPDecoderStream, ASCIIUtility

public class QDecoderStream extends QPDecoderStream
{

    public QDecoderStream(InputStream inputstream)
    {
        super(inputstream);
    }

    public int read()
        throws IOException
    {
        int i = in.read();
        if(i == 95)
            i = 32;
        else
        if(i == 61)
        {
            ba[0] = (byte)in.read();
            ba[1] = (byte)in.read();
            int j;
            try
            {
                j = ASCIIUtility.parseInt(ba, 0, 2, 16);
            }
            catch(NumberFormatException numberformatexception)
            {
                throw new IOException((new StringBuilder("Error in QP stream ")).append(numberformatexception.getMessage()).toString());
            }
            return j;
        }
        return i;
    }
}
