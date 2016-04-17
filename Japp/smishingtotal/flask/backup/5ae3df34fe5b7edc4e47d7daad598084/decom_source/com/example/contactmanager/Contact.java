// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.example.contactmanager;


public class Contact
{

    public Contact()
    {
    }

    public String getContactname()
    {
        return contactname;
    }

    public String getContactnumber()
    {
        return contactnumber;
    }

    public String getPhonenumber()
    {
        return phonenumber;
    }

    public void setContactname(String s)
    {
        contactname = s;
    }

    public void setContactnumber(String s)
    {
        contactnumber = s;
    }

    public void setPhonenumber(String s)
    {
        phonenumber = s;
    }

    public String toString()
    {
        return (new StringBuilder("contactname=")).append(contactname).append(",contactnumber=").append(contactnumber).append(",phonenumber=").append(phonenumber).toString();
    }

    private String contactname;
    private String contactnumber;
    private String phonenumber;
}
