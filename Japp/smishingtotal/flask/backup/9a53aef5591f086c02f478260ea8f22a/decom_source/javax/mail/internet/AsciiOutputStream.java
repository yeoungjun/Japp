// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.internet;

import java.io.*;

// Referenced classes of package javax.mail.internet:
//            MimeUtility

class AsciiOutputStream extends OutputStream
{

    public AsciiOutputStream(boolean flag, boolean flag1)
    {
        ascii = 0;
        non_ascii = 0;
        linelen = 0;
        longLine = false;
        badEOL = false;
        checkEOL = false;
        lastb = 0;
        ret = 0;
        breakOnNonAscii = flag;
        boolean flag2 = false;
        if(flag1)
        {
            flag2 = false;
            if(flag)
                flag2 = true;
        }
        checkEOL = flag2;
    }

    private final void check(int i)
        throws IOException
    {
        int j = i & 0xff;
        if(checkEOL && (lastb == 13 && j != 10 || lastb != 13 && j == 10))
            badEOL = true;
        if(j == 13 || j == 10)
        {
            linelen = 0;
        } else
        {
            linelen = 1 + linelen;
            if(linelen > 998)
                longLine = true;
        }
        if(MimeUtility.nonascii(j))
        {
            non_ascii = 1 + non_ascii;
            if(breakOnNonAscii)
            {
                ret = 3;
                throw new EOFException();
            }
        } else
        {
            ascii = 1 + ascii;
        }
        lastb = j;
    }

    public int getAscii()
    {
        int i = 3;
        if(ret != 0)
            i = ret;
        else
        if(!badEOL)
        {
            if(non_ascii == 0)
                return !longLine ? 1 : 2;
            if(ascii > non_ascii)
                return 2;
        }
        return i;
    }

    public void write(int i)
        throws IOException
    {
        check(i);
    }

    public void write(byte abyte0[])
        throws IOException
    {
        write(abyte0, 0, abyte0.length);
    }

    public void write(byte abyte0[], int i, int j)
        throws IOException
    {
        int k = j + i;
        int l = i;
        do
        {
            if(l >= k)
                return;
            check(abyte0[l]);
            l++;
        } while(true);
    }

    private int ascii;
    private boolean badEOL;
    private boolean breakOnNonAscii;
    private boolean checkEOL;
    private int lastb;
    private int linelen;
    private boolean longLine;
    private int non_ascii;
    private int ret;
}
