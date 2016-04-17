// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail;


public class Provider
{
    public static class Type
    {

        public String toString()
        {
            return type;
        }

        public static final Type STORE = new Type("STORE");
        public static final Type TRANSPORT = new Type("TRANSPORT");
        private String type;


        private Type(String s)
        {
            type = s;
        }
    }


    public Provider(Type type1, String s, String s1, String s2, String s3)
    {
        type = type1;
        protocol = s;
        className = s1;
        vendor = s2;
        version = s3;
    }

    public String getClassName()
    {
        return className;
    }

    public String getProtocol()
    {
        return protocol;
    }

    public Type getType()
    {
        return type;
    }

    public String getVendor()
    {
        return vendor;
    }

    public String getVersion()
    {
        return version;
    }

    public String toString()
    {
        String s = (new StringBuilder("javax.mail.Provider[")).append(type).append(",").append(protocol).append(",").append(className).toString();
        if(vendor != null)
            s = (new StringBuilder(String.valueOf(s))).append(",").append(vendor).toString();
        if(version != null)
            s = (new StringBuilder(String.valueOf(s))).append(",").append(version).toString();
        return (new StringBuilder(String.valueOf(s))).append("]").toString();
    }

    private String className;
    private String protocol;
    private Type type;
    private String vendor;
    private String version;
}
