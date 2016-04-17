// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.lang3.text.translate;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.EnumSet;

// Referenced classes of package org.apache.commons.lang3.text.translate:
//            CharSequenceTranslator

public class NumericEntityUnescaper extends CharSequenceTranslator
{
    public static final class OPTION extends Enum
    {

        public static OPTION valueOf(String s)
        {
            return (OPTION)Enum.valueOf(org/apache/commons/lang3/text/translate/NumericEntityUnescaper$OPTION, s);
        }

        public static final OPTION[] values()
        {
            return (OPTION[])$VALUES.clone();
        }

        private static final OPTION $VALUES[];
        public static final OPTION errorIfNoSemiColon;
        public static final OPTION semiColonOptional;
        public static final OPTION semiColonRequired;

        static 
        {
            semiColonRequired = new OPTION("semiColonRequired", 0);
            semiColonOptional = new OPTION("semiColonOptional", 1);
            errorIfNoSemiColon = new OPTION("errorIfNoSemiColon", 2);
            OPTION aoption[] = new OPTION[3];
            aoption[0] = semiColonRequired;
            aoption[1] = semiColonOptional;
            aoption[2] = errorIfNoSemiColon;
            $VALUES = aoption;
        }

        private OPTION(String s, int i)
        {
            super(s, i);
        }
    }


    public transient NumericEntityUnescaper(OPTION aoption[])
    {
        if(aoption.length > 0)
        {
            options = EnumSet.copyOf(Arrays.asList(aoption));
            return;
        } else
        {
            OPTION aoption1[] = new OPTION[1];
            aoption1[0] = OPTION.semiColonRequired;
            options = EnumSet.copyOf(Arrays.asList(aoption1));
            return;
        }
    }

    public boolean isSet(OPTION option)
    {
        if(options == null)
            return false;
        else
            return options.contains(option);
    }

    public int translate(CharSequence charsequence, int i, Writer writer)
        throws IOException
    {
        int j;
        int k;
        boolean flag;
label0:
        {
            j = charsequence.length();
            if(charsequence.charAt(i) != '&' || i >= j - 2 || charsequence.charAt(i + 1) != '#')
                break MISSING_BLOCK_LABEL_399;
            k = i + 2;
            char c = charsequence.charAt(k);
            if(c != 'x')
            {
                flag = false;
                if(c != 'X')
                    break label0;
            }
            k++;
            flag = true;
            if(k == j)
                return 0;
        }
label1:
        {
            int j1;
            int l1;
            int j2;
            {
                int l;
                for(l = k; l < j && (charsequence.charAt(l) >= '0' && charsequence.charAt(l) <= '9' || charsequence.charAt(l) >= 'a' && charsequence.charAt(l) <= 'f' || charsequence.charAt(l) >= 'A' && charsequence.charAt(l) <= 'F'); l++);
                boolean flag1;
                if(l != j && charsequence.charAt(l) == ';')
                    flag1 = true;
                else
                    flag1 = false;
                if(!flag1)
                {
                    if(isSet(OPTION.semiColonRequired))
                        return 0;
                    if(isSet(OPTION.errorIfNoSemiColon))
                        throw new IllegalArgumentException("Semi-colon required at end of numeric entity");
                }
                if(!flag)
                    break label1;
                int i1;
                int k1;
                int i2;
                char ac[];
                int k2;
                try
                {
                    k2 = Integer.parseInt(charsequence.subSequence(k, l).toString(), 16);
                }
                catch(NumberFormatException numberformatexception)
                {
                    return 0;
                }
                j1 = k2;
            }
            if(j1 > 65535)
            {
                ac = Character.toChars(j1);
                writer.write(ac[0]);
                writer.write(ac[1]);
            } else
            {
                writer.write(j1);
            }
            k1 = (l + 2) - k;
            if(flag)
                l1 = 1;
            else
                l1 = 0;
            i2 = k1 + l1;
            if(flag1)
                j2 = 1;
            else
                j2 = 0;
            return j2 + i2;
        }
        i1 = Integer.parseInt(charsequence.subSequence(k, l).toString(), 10);
        j1 = i1;
        break MISSING_BLOCK_LABEL_279;
        return 0;
    }

    private final EnumSet options;
}
