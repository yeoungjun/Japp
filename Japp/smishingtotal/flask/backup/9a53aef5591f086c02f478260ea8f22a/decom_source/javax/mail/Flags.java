// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail;

import java.io.Serializable;
import java.util.*;

public class Flags
    implements Cloneable, Serializable
{
    public static final class Flag
    {

        public static final Flag ANSWERED = new Flag(1);
        public static final Flag DELETED = new Flag(2);
        public static final Flag DRAFT = new Flag(4);
        public static final Flag FLAGGED = new Flag(8);
        public static final Flag RECENT = new Flag(16);
        public static final Flag SEEN = new Flag(32);
        public static final Flag USER = new Flag(0x80000000);
        private int bit;



        private Flag(int i)
        {
            bit = i;
        }
    }


    public Flags()
    {
        system_flags = 0;
        user_flags = null;
    }

    public Flags(String s)
    {
        system_flags = 0;
        user_flags = null;
        user_flags = new Hashtable(1);
        user_flags.put(s.toLowerCase(Locale.ENGLISH), s);
    }

    public Flags(Flag flag)
    {
        system_flags = 0;
        user_flags = null;
        system_flags = system_flags | flag.bit;
    }

    public Flags(Flags flags)
    {
        system_flags = 0;
        user_flags = null;
        system_flags = flags.system_flags;
        if(flags.user_flags != null)
            user_flags = (Hashtable)flags.user_flags.clone();
    }

    public void add(String s)
    {
        if(user_flags == null)
            user_flags = new Hashtable(1);
        user_flags.put(s.toLowerCase(Locale.ENGLISH), s);
    }

    public void add(Flag flag)
    {
        system_flags = system_flags | flag.bit;
    }

    public void add(Flags flags)
    {
        system_flags = system_flags | flags.system_flags;
        if(flags.user_flags == null) goto _L2; else goto _L1
_L1:
        Enumeration enumeration;
        if(user_flags == null)
            user_flags = new Hashtable(1);
        enumeration = flags.user_flags.keys();
_L5:
        if(enumeration.hasMoreElements()) goto _L3; else goto _L2
_L2:
        return;
_L3:
        String s = (String)enumeration.nextElement();
        user_flags.put(s, flags.user_flags.get(s));
        if(true) goto _L5; else goto _L4
_L4:
    }

    public Object clone()
    {
        Flags flags;
        try
        {
            flags = (Flags)super.clone();
        }
        catch(CloneNotSupportedException clonenotsupportedexception)
        {
            flags = null;
        }
        if(user_flags != null && flags != null)
            flags.user_flags = (Hashtable)user_flags.clone();
        return flags;
    }

    public boolean contains(String s)
    {
        if(user_flags == null)
            return false;
        else
            return user_flags.containsKey(s.toLowerCase(Locale.ENGLISH));
    }

    public boolean contains(Flag flag)
    {
        return (system_flags & flag.bit) != 0;
    }

    public boolean contains(Flags flags)
    {
        if((flags.system_flags & system_flags) == flags.system_flags) goto _L2; else goto _L1
_L1:
        return false;
_L2:
        if(flags.user_flags == null) goto _L4; else goto _L3
_L3:
        if(user_flags == null) goto _L1; else goto _L5
_L5:
        Enumeration enumeration = flags.user_flags.keys();
_L7:
        if(enumeration.hasMoreElements())
            continue; /* Loop/switch isn't completed */
_L4:
        return true;
        if(user_flags.containsKey(enumeration.nextElement())) goto _L7; else goto _L6
_L6:
        return false;
    }

    public boolean equals(Object obj)
    {
        Flags flags;
        if(obj instanceof Flags)
            if((flags = (Flags)obj).system_flags == system_flags)
            {
                if(flags.user_flags == null && user_flags == null)
                    return true;
                if(flags.user_flags != null && user_flags != null && flags.user_flags.size() == user_flags.size())
                {
                    Enumeration enumeration = flags.user_flags.keys();
                    do
                        if(!enumeration.hasMoreElements())
                            return true;
                    while(user_flags.containsKey(enumeration.nextElement()));
                    return false;
                }
            }
        return false;
    }

    public Flag[] getSystemFlags()
    {
        Vector vector = new Vector();
        if((1 & system_flags) != 0)
            vector.addElement(Flag.ANSWERED);
        if((2 & system_flags) != 0)
            vector.addElement(Flag.DELETED);
        if((4 & system_flags) != 0)
            vector.addElement(Flag.DRAFT);
        if((8 & system_flags) != 0)
            vector.addElement(Flag.FLAGGED);
        if((0x10 & system_flags) != 0)
            vector.addElement(Flag.RECENT);
        if((0x20 & system_flags) != 0)
            vector.addElement(Flag.SEEN);
        if((0x80000000 & system_flags) != 0)
            vector.addElement(Flag.USER);
        Flag aflag[] = new Flag[vector.size()];
        vector.copyInto(aflag);
        return aflag;
    }

    public String[] getUserFlags()
    {
        Vector vector = new Vector();
        if(user_flags == null) goto _L2; else goto _L1
_L1:
        Enumeration enumeration = user_flags.elements();
_L5:
        if(enumeration.hasMoreElements()) goto _L3; else goto _L2
_L2:
        String as[] = new String[vector.size()];
        vector.copyInto(as);
        return as;
_L3:
        vector.addElement(enumeration.nextElement());
        if(true) goto _L5; else goto _L4
_L4:
    }

    public int hashCode()
    {
        int i = system_flags;
        if(user_flags == null) goto _L2; else goto _L1
_L1:
        Enumeration enumeration = user_flags.keys();
_L5:
        if(enumeration.hasMoreElements()) goto _L3; else goto _L2
_L2:
        return i;
_L3:
        i += ((String)enumeration.nextElement()).hashCode();
        if(true) goto _L5; else goto _L4
_L4:
    }

    public void remove(String s)
    {
        if(user_flags != null)
            user_flags.remove(s.toLowerCase(Locale.ENGLISH));
    }

    public void remove(Flag flag)
    {
        system_flags = system_flags & (-1 ^ flag.bit);
    }

    public void remove(Flags flags)
    {
        system_flags = system_flags & (-1 ^ flags.system_flags);
        if(flags.user_flags != null && user_flags != null)
        {
            Enumeration enumeration = flags.user_flags.keys();
            while(enumeration.hasMoreElements()) 
                user_flags.remove(enumeration.nextElement());
        }
    }

    private static final int ANSWERED_BIT = 1;
    private static final int DELETED_BIT = 2;
    private static final int DRAFT_BIT = 4;
    private static final int FLAGGED_BIT = 8;
    private static final int RECENT_BIT = 16;
    private static final int SEEN_BIT = 32;
    private static final int USER_BIT = 0x80000000;
    private static final long serialVersionUID = 0x56a5b06539097bc4L;
    private int system_flags;
    private Hashtable user_flags;
}
