// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail;

import java.util.Vector;

public class FetchProfile
{
    public static class Item
    {

        public static final Item CONTENT_INFO = new Item("CONTENT_INFO");
        public static final Item ENVELOPE = new Item("ENVELOPE");
        public static final Item FLAGS = new Item("FLAGS");
        private String name;


        protected Item(String s)
        {
            name = s;
        }
    }


    public FetchProfile()
    {
        specials = null;
        headers = null;
    }

    public void add(String s)
    {
        if(headers == null)
            headers = new Vector();
        headers.addElement(s);
    }

    public void add(Item item)
    {
        if(specials == null)
            specials = new Vector();
        specials.addElement(item);
    }

    public boolean contains(String s)
    {
        return headers != null && headers.contains(s);
    }

    public boolean contains(Item item)
    {
        return specials != null && specials.contains(item);
    }

    public String[] getHeaderNames()
    {
        if(headers == null)
        {
            return new String[0];
        } else
        {
            String as[] = new String[headers.size()];
            headers.copyInto(as);
            return as;
        }
    }

    public Item[] getItems()
    {
        if(specials == null)
        {
            return new Item[0];
        } else
        {
            Item aitem[] = new Item[specials.size()];
            specials.copyInto(aitem);
            return aitem;
        }
    }

    private Vector headers;
    private Vector specials;
}
