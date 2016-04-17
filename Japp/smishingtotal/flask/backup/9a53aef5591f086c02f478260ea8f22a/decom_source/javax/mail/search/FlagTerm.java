// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.search;

import javax.mail.Flags;
import javax.mail.Message;

// Referenced classes of package javax.mail.search:
//            SearchTerm

public final class FlagTerm extends SearchTerm
{

    public FlagTerm(Flags flags1, boolean flag)
    {
        flags = flags1;
        set = flag;
    }

    public boolean equals(Object obj)
    {
        FlagTerm flagterm;
        if(obj instanceof FlagTerm)
            if((flagterm = (FlagTerm)obj).set == set && flagterm.flags.equals(flags))
                return true;
        return false;
    }

    public Flags getFlags()
    {
        return (Flags)flags.clone();
    }

    public boolean getTestSet()
    {
        return set;
    }

    public int hashCode()
    {
        if(set)
            return flags.hashCode();
        else
            return -1 ^ flags.hashCode();
    }

    public boolean match(Message message)
    {
        boolean flag = true;
        Flags flags1 = message.getFlags();
        Exception exception;
        javax.mail.Flags.Flag aflag[];
        int i;
        String as[];
        int j;
        if(set)
            if(flags1.contains(flags))
                return flag;
            else
                return false;
        aflag = flags.getSystemFlags();
        i = 0;
_L6:
        if(i < aflag.length) goto _L2; else goto _L1
_L1:
        as = flags.getUserFlags();
        j = 0;
_L4:
        if(j >= as.length)
            break; /* Loop/switch isn't completed */
        boolean flag1;
        if(flags1.contains(as[j]))
            return false;
        j++;
        continue; /* Loop/switch isn't completed */
_L2:
        flag1 = flags1.contains(aflag[i]);
        if(flag1)
            return false;
        i++;
        continue; /* Loop/switch isn't completed */
        if(true) goto _L4; else goto _L3
        exception;
        flag = false;
_L3:
        return flag;
        if(true) goto _L6; else goto _L5
_L5:
    }

    private static final long serialVersionUID = 0xfe03fdfcf298e8c9L;
    protected Flags flags;
    protected boolean set;
}
