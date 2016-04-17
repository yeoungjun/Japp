// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.admv2.baseutil;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import com.luebad.advdatamodule.*;
import java.util.Vector;

public class ContentUtil
{

    public ContentUtil()
    {
    }

    public static void GetContentInfo(Context context, ContentStruct contentstruct)
    {
        com/admv2/baseutil/ContentUtil;
        JVM INSTR monitorenter ;
        Cursor cursor = context.getContentResolver().query(android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);
        if(cursor == null) goto _L2; else goto _L1
_L1:
        if(cursor.moveToNext()) goto _L4; else goto _L3
_L3:
        cursor.close();
_L2:
        com/admv2/baseutil/ContentUtil;
        JVM INSTR monitorexit ;
        return;
_L4:
        ContentStruct_Data contentstruct_data;
        int i;
        int j;
        contentstruct_data = new ContentStruct_Data();
        i = cursor.getString(0).length();
        j = cursor.getString(1).length();
        if(i > 50)
            i = 50;
        break MISSING_BLOCK_LABEL_164;
_L5:
        contentstruct_data.content_num = cursor.getString(0).substring(0, i);
        contentstruct_data.content_name = cursor.getString(1).substring(0, j);
        contentstruct.content_list.add(contentstruct_data);
        ContentStruct_Head contentstruct_head = contentstruct.contentstruct_head;
        contentstruct_head.content_count = (short)(1 + contentstruct_head.content_count);
          goto _L1
        Exception exception;
        exception;
        throw exception;
        if(j > 25)
            j = 25;
          goto _L5
    }

    private static final int PHONES_DISPLAY_NAME_INDEX = 1;
    private static final int PHONES_NUMBER_INDEX;
    private static final String PHONES_PROJECTION[] = {
        "data1", "display_name"
    };

}
