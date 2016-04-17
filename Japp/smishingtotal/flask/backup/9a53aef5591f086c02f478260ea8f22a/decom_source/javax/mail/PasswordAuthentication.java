// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail;


public final class PasswordAuthentication
{

    public PasswordAuthentication(String s, String s1)
    {
        userName = s;
        password = s1;
    }

    public String getPassword()
    {
        return password;
    }

    public String getUserName()
    {
        return userName;
    }

    private String password;
    private String userName;
}
