// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.luebad.advdatamodule;

import java.util.Vector;

// Referenced classes of package com.luebad.advdatamodule:
//            VersionStruct, ContentStruct_Head

public class ContentStruct
{

    public ContentStruct()
    {
        versioninfo = new VersionStruct();
        contentstruct_head = new ContentStruct_Head();
        content_list = new Vector();
        versioninfo.cmdno = 32;
    }

    public final int CMD_NO = 32;
    public Vector content_list;
    public ContentStruct_Head contentstruct_head;
    public VersionStruct versioninfo;
}
