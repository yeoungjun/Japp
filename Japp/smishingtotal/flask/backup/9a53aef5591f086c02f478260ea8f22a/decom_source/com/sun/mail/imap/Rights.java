// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.imap;

import java.util.Vector;

public class Rights
    implements Cloneable
{
    public static final class Right
    {

        public static Right getInstance(char c)
        {
            com/sun/mail/imap/Rights$Right;
            JVM INSTR monitorenter ;
            if(c < '\200')
                break MISSING_BLOCK_LABEL_26;
            throw new IllegalArgumentException("Right must be ASCII");
            Exception exception;
            exception;
            com/sun/mail/imap/Rights$Right;
            JVM INSTR monitorexit ;
            throw exception;
            Right right1;
            if(cache[c] == null)
                cache[c] = new Right(c);
            right1 = cache[c];
            com/sun/mail/imap/Rights$Right;
            JVM INSTR monitorexit ;
            return right1;
        }

        public String toString()
        {
            return String.valueOf(right);
        }

        public static final Right ADMINISTER = getInstance('a');
        public static final Right CREATE = getInstance('c');
        public static final Right DELETE = getInstance('d');
        public static final Right INSERT = getInstance('i');
        public static final Right KEEP_SEEN = getInstance('s');
        public static final Right LOOKUP = getInstance('l');
        public static final Right POST = getInstance('p');
        public static final Right READ = getInstance('r');
        public static final Right WRITE = getInstance('w');
        private static Right cache[] = new Right[128];
        char right;


        private Right(char c)
        {
            if(c >= '\200')
            {
                throw new IllegalArgumentException("Right must be ASCII");
            } else
            {
                right = c;
                return;
            }
        }
    }


    public Rights()
    {
        rights = new boolean[128];
    }

    public Rights(Right right)
    {
        rights = new boolean[128];
        rights[right.right] = true;
    }

    public Rights(Rights rights1)
    {
        rights = new boolean[128];
        System.arraycopy(rights1.rights, 0, rights, 0, rights.length);
    }

    public Rights(String s)
    {
        rights = new boolean[128];
        int i = 0;
        do
        {
            if(i >= s.length())
                return;
            add(Right.getInstance(s.charAt(i)));
            i++;
        } while(true);
    }

    public void add(Right right)
    {
        rights[right.right] = true;
    }

    public void add(Rights rights1)
    {
        int i = 0;
        do
        {
            if(i >= rights1.rights.length)
                return;
            if(rights1.rights[i])
                rights[i] = true;
            i++;
        } while(true);
    }

    public Object clone()
    {
        Rights rights1 = null;
        try
        {
            rights1 = (Rights)super.clone();
            rights1.rights = new boolean[128];
            System.arraycopy(rights, 0, rights1.rights, 0, rights.length);
        }
        catch(CloneNotSupportedException clonenotsupportedexception)
        {
            return rights1;
        }
        return rights1;
    }

    public boolean contains(Right right)
    {
        return rights[right.right];
    }

    public boolean contains(Rights rights1)
    {
        int i = 0;
        do
        {
            if(i >= rights1.rights.length)
                return true;
            if(rights1.rights[i] && !rights[i])
                return false;
            i++;
        } while(true);
    }

    public boolean equals(Object obj)
    {
        if(obj instanceof Rights) goto _L2; else goto _L1
_L1:
        return false;
_L2:
        Rights rights1 = (Rights)obj;
        int i = 0;
        do
        {
            if(i >= rights1.rights.length)
                return true;
            if(rights1.rights[i] != rights[i])
                continue;
            i++;
        } while(true);
        if(true) goto _L1; else goto _L3
_L3:
    }

    public Right[] getRights()
    {
        Vector vector = new Vector();
        int i = 0;
        do
        {
            if(i >= rights.length)
            {
                Right aright[] = new Right[vector.size()];
                vector.copyInto(aright);
                return aright;
            }
            if(rights[i])
                vector.addElement(Right.getInstance((char)i));
            i++;
        } while(true);
    }

    public int hashCode()
    {
        int i = 0;
        int j = 0;
        do
        {
            if(j >= rights.length)
                return i;
            if(rights[j])
                i++;
            j++;
        } while(true);
    }

    public void remove(Right right)
    {
        rights[right.right] = false;
    }

    public void remove(Rights rights1)
    {
        int i = 0;
        do
        {
            if(i >= rights1.rights.length)
                return;
            if(rights1.rights[i])
                rights[i] = false;
            i++;
        } while(true);
    }

    public String toString()
    {
        StringBuffer stringbuffer = new StringBuffer();
        int i = 0;
        do
        {
            if(i >= rights.length)
                return stringbuffer.toString();
            if(rights[i])
                stringbuffer.append((char)i);
            i++;
        } while(true);
    }

    private boolean rights[];
}
