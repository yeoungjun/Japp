// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.message;


public class ParserCursor
{

    public ParserCursor(int i, int j)
    {
        if(i < 0)
            throw new IndexOutOfBoundsException("Lower bound cannot be negative");
        if(i > j)
        {
            throw new IndexOutOfBoundsException("Lower bound cannot be greater then upper bound");
        } else
        {
            lowerBound = i;
            upperBound = j;
            pos = i;
            return;
        }
    }

    public boolean atEnd()
    {
        return pos >= upperBound;
    }

    public int getLowerBound()
    {
        return lowerBound;
    }

    public int getPos()
    {
        return pos;
    }

    public int getUpperBound()
    {
        return upperBound;
    }

    public String toString()
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append('[');
        stringbuilder.append(Integer.toString(lowerBound));
        stringbuilder.append('>');
        stringbuilder.append(Integer.toString(pos));
        stringbuilder.append('>');
        stringbuilder.append(Integer.toString(upperBound));
        stringbuilder.append(']');
        return stringbuilder.toString();
    }

    public void updatePos(int i)
    {
        if(i < lowerBound)
            throw new IndexOutOfBoundsException((new StringBuilder()).append("pos: ").append(i).append(" < lowerBound: ").append(lowerBound).toString());
        if(i > upperBound)
        {
            throw new IndexOutOfBoundsException((new StringBuilder()).append("pos: ").append(i).append(" > upperBound: ").append(upperBound).toString());
        } else
        {
            pos = i;
            return;
        }
    }

    private final int lowerBound;
    private int pos;
    private final int upperBound;
}
