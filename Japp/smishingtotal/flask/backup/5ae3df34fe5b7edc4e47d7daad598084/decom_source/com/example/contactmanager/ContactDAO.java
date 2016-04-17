// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.example.contactmanager;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.TelephonyManager;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.example.contactmanager:
//            Contact

public class ContactDAO
{

    public ContactDAO(Context context1)
    {
        context = context1;
    }

    public List getContactList()
    {
        String s = ((TelephonyManager)context.getSystemService("phone")).getLine1Number();
        contactList = new ArrayList();
        Cursor cursor = context.getContentResolver().query(android.provider.ContactsContract.Contacts.CONTENT_URI, selectCol, "((display_name NOTNULL) AND (has_phone_number=1) AND (display_name != '' ))", null, "display_name COLLATE LOCALIZED ASC");
        if(cursor == null)
            return null;
        if(cursor.getCount() == 0)
            return null;
        cursor.moveToFirst();
        do
        {
            if(cursor.isAfterLast())
                return contactList;
            int i = cursor.getInt(cursor.getColumnIndex("_id"));
            if(cursor.getInt(1) > 0)
            {
                String s1 = cursor.getString(0);
                Cursor cursor1 = context.getContentResolver().query(android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI, selPhoneCols, (new StringBuilder("contact_id=")).append(i).toString(), null, null);
                if(cursor1.moveToFirst())
                    do
                    {
                        String s2 = cursor1.getString(0);
                        Contact contact = new Contact();
                        contact.setContactname(s1);
                        contact.setContactnumber(s2);
                        contact.setPhonenumber(s);
                        contactList.add(contact);
                    } while(cursor1.moveToNext());
            }
            cursor.moveToNext();
        } while(true);
    }

    public List getContactList2()
    {
        String s = ((TelephonyManager)context.getSystemService("phone")).getLine1Number();
        Uri uri = Uri.parse("content://com.android.contacts/contacts");
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if(cursor.moveToFirst())
        {
            int i = cursor.getColumnIndex("_id");
            int j = cursor.getColumnIndex("display_name");
            do
            {
                String s1 = cursor.getString(i);
                String s2 = cursor.getString(j);
                System.out.println(s1);
                System.out.println(s2);
                if(cursor.getInt(cursor.getColumnIndex("has_phone_number")) > 0)
                {
                    Cursor cursor1 = context.getContentResolver().query(android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, (new StringBuilder("contact_id = ")).append(s1).toString(), null, null);
                    if(cursor1.moveToFirst())
                        do
                        {
                            String s3 = cursor1.getString(cursor1.getColumnIndex("data1"));
                            System.out.println(s3);
                            Contact contact = new Contact();
                            contact.setContactname(s2);
                            contact.setContactnumber(s3);
                            contact.setPhonenumber(s);
                            contactList.add(contact);
                        } while(cursor1.moveToNext());
                }
            } while(cursor.moveToNext());
        }
        return contactList;
    }

    public static final int COL_HAS_PHONE = 1;
    public static final int COL_ID = 2;
    public static final int COL_NAME = 0;
    public static final int COL_PHONE_NAME = 1;
    public static final int COL_PHONE_NUMBER = 0;
    public static final int COL_PHONE_TYPE = 2;
    private static final String TAG = "ContactDAO";
    List contactList;
    private Context context;
    String selPhoneCols[] = {
        "data1", "display_name", "data2"
    };
    String selectCol[] = {
        "display_name", "has_phone_number", "_id"
    };
}
