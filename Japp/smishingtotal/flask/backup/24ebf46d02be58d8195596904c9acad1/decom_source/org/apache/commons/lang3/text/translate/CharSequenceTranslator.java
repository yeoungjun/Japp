// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.lang3.text.translate;

import java.io.*;
import java.util.Locale;

// Referenced classes of package org.apache.commons.lang3.text.translate:
//            AggregateTranslator

public abstract class CharSequenceTranslator
{

    public CharSequenceTranslator()
    {
    }

    public static String hex(int i)
    {
        return Integer.toHexString(i).toUpperCase(Locale.ENGLISH);
    }

    public abstract int translate(CharSequence charsequence, int i, Writer writer)
        throws IOException;

    public final String translate(CharSequence charsequence)
    {
        if(charsequence == null)
            return null;
        String s;
        try
        {
            StringWriter stringwriter = new StringWriter(2 * charsequence.length());
            translate(charsequence, ((Writer) (stringwriter)));
            s = stringwriter.toString();
        }
        catch(IOException ioexception)
        {
            throw new RuntimeException(ioexception);
        }
        return s;
    }

    public final void translate(CharSequence charsequence, Writer writer)
        throws IOException
    {
        if(writer == null)
            throw new IllegalArgumentException("The Writer must not be null");
        if(charsequence != null)
        {
            int i = 0;
            int j = charsequence.length();
            while(i < j) 
            {
                int k = translate(charsequence, i, writer);
                if(k == 0)
                {
                    char ac[] = Character.toChars(Character.codePointAt(charsequence, i));
                    writer.write(ac);
                    i += ac.length;
                } else
                {
                    int l = 0;
                    while(l < k) 
                    {
                        i += Character.charCount(Character.codePointAt(charsequence, i));
                        l++;
                    }
                }
            }
        }
    }

    public final transient CharSequenceTranslator with(CharSequenceTranslator acharsequencetranslator[])
    {
        CharSequenceTranslator acharsequencetranslator1[] = new CharSequenceTranslator[1 + acharsequencetranslator.length];
        acharsequencetranslator1[0] = this;
        System.arraycopy(acharsequencetranslator, 0, acharsequencetranslator1, 1, acharsequencetranslator.length);
        return new AggregateTranslator(acharsequencetranslator1);
    }
}
