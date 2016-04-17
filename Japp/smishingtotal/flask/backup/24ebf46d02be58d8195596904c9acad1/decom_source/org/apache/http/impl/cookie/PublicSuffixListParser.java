// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.cookie;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

// Referenced classes of package org.apache.http.impl.cookie:
//            PublicSuffixFilter

public class PublicSuffixListParser
{

    PublicSuffixListParser(PublicSuffixFilter publicsuffixfilter)
    {
        filter = publicsuffixfilter;
    }

    private boolean readLine(Reader reader, StringBuilder stringbuilder)
        throws IOException
    {
        stringbuilder.setLength(0);
        boolean flag = false;
        do
        {
            char c;
label0:
            {
                int i = reader.read();
                if(i != -1)
                {
                    c = (char)i;
                    if(c != '\n')
                        break label0;
                }
                boolean flag1 = false;
                if(i != -1)
                    flag1 = true;
                return flag1;
            }
            if(Character.isWhitespace(c))
                flag = true;
            if(!flag)
                stringbuilder.append(c);
            if(stringbuilder.length() > 256)
                throw new IOException("Line too long");
        } while(true);
    }

    public void parse(Reader reader)
        throws IOException
    {
        ArrayList arraylist = new ArrayList();
        ArrayList arraylist1 = new ArrayList();
        BufferedReader bufferedreader = new BufferedReader(reader);
        StringBuilder stringbuilder = new StringBuilder(256);
        boolean flag = true;
        do
        {
            if(!flag)
                break;
            flag = readLine(bufferedreader, stringbuilder);
            String s = stringbuilder.toString();
            if(s.length() != 0 && !s.startsWith("//"))
            {
                if(s.startsWith("."))
                    s = s.substring(1);
                boolean flag1 = s.startsWith("!");
                if(flag1)
                    s = s.substring(1);
                if(flag1)
                    arraylist1.add(s);
                else
                    arraylist.add(s);
            }
        } while(true);
        filter.setPublicSuffixes(arraylist);
        filter.setExceptions(arraylist1);
    }

    private static final int MAX_LINE_LEN = 256;
    private final PublicSuffixFilter filter;
}
