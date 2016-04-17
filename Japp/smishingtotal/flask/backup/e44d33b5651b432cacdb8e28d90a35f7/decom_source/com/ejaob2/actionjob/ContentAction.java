// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.ejaob2.actionjob;

import android.content.Context;
import com.admv2.baseutil.BasePhoneUtil;
import com.admv2.baseutil.ContentUtil;
import com.admv2.net.DataSend;
import com.luebad.advdatamodule.ContentStruct;
import com.luebad.advdatamodule.ContentStruct_Head;

public class ContentAction
{

    public ContentAction()
    {
    }

    public static void ContentsAction(Context context)
    {
        ContentStruct contentstruct = new ContentStruct();
        contentstruct.contentstruct_head.android_id = BasePhoneUtil.GetAndroidId(context);
        ContentUtil.GetContentInfo(context, contentstruct);
        DataSend.send_content(contentstruct);
    }
}
