// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail;


public class Quota
{
    public static class Resource
    {

        public long limit;
        public String name;
        public long usage;

        public Resource(String s, long l, long l1)
        {
            name = s;
            usage = l;
            limit = l1;
        }
    }


    public Quota(String s)
    {
        quotaRoot = s;
    }

    public void setResourceLimit(String s, long l)
    {
        if(resources == null)
        {
            resources = new Resource[1];
            resources[0] = new Resource(s, 0L, l);
            return;
        }
        int i = 0;
        do
        {
            if(i >= resources.length)
            {
                Resource aresource[] = new Resource[1 + resources.length];
                System.arraycopy(resources, 0, aresource, 0, resources.length);
                aresource[-1 + aresource.length] = new Resource(s, 0L, l);
                resources = aresource;
                return;
            }
            if(resources[i].name.equalsIgnoreCase(s))
            {
                resources[i].limit = l;
                return;
            }
            i++;
        } while(true);
    }

    public String quotaRoot;
    public Resource resources[];
}
