// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.lang3.text.translate;

import java.io.IOException;
import java.io.Writer;

// Referenced classes of package org.apache.commons.lang3.text.translate:
//            CharSequenceTranslator

public class OctalUnescaper extends CharSequenceTranslator
{

    public OctalUnescaper()
    {
    }

    public int translate(CharSequence charsequence, int i, Writer writer)
        throws IOException
    {
        if(charsequence.charAt(i) == '\\' && i < -1 + charsequence.length() && Character.isDigit(charsequence.charAt(i + 1)))
        {
            int j = i + 1;
            int k = i + 2;
            do
            {
                if(k >= charsequence.length() || !Character.isDigit(charsequence.charAt(k)))
                    break;
                k++;
                if(Integer.parseInt(charsequence.subSequence(j, k).toString(), 10) <= OCTAL_MAX)
                    continue;
                k--;
                break;
            } while(true);
            writer.write(Integer.parseInt(charsequence.subSequence(j, k).toString(), 8));
            return (k + 1) - j;
        } else
        {
            return 0;
        }
    }

    private static int OCTAL_MAX = 377;

}
