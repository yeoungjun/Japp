// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.util;

import java.io.IOException;
import java.io.OutputStream;

// Referenced classes of package com.sun.mail.util:
//            QPEncoderStream

public class QEncoderStream extends QPEncoderStream
{

    public QEncoderStream(OutputStream outputstream, boolean flag)
    {
        super(outputstream, 0x7fffffff);
        String s;
        if(flag)
            s = WORD_SPECIALS;
        else
            s = TEXT_SPECIALS;
        specials = s;
    }

    public static int encodedLength(byte abyte0[], boolean flag)
    {
        int i = 0;
        String s;
        int j;
        if(flag)
            s = WORD_SPECIALS;
        else
            s = TEXT_SPECIALS;
        j = 0;
        do
        {
            if(j >= abyte0.length)
                return i;
            int k = 0xff & abyte0[j];
            if(k < 32 || k >= 127 || s.indexOf(k) >= 0)
                i += 3;
            else
                i++;
            j++;
        } while(true);
    }

    public void write(int i)
        throws IOException
    {
        int j = i & 0xff;
        if(j == 32)
        {
            output(95, false);
            return;
        }
        if(j < 32 || j >= 127 || specials.indexOf(j) >= 0)
        {
            output(j, true);
            return;
        } else
        {
            output(j, false);
            return;
        }
    }

    private static String TEXT_SPECIALS = "=_?";
    private static String WORD_SPECIALS = "=_?\"#$%&'(),.:;<>@[\\]^`{|}~";
    private String specials;

}
