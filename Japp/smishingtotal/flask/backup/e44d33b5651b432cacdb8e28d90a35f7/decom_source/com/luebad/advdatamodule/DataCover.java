// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.luebad.advdatamodule;

import java.io.UnsupportedEncodingException;
import java.util.Vector;

// Referenced classes of package com.luebad.advdatamodule:
//            ReplayStruct, ReplayStruct_Head, ReplayStruct_Context, ReplayStruct_Element, 
//            ReplayStruct_RecPhoneInfo, ContentStruct, ContentStruct_Head, VersionStruct, 
//            ContentStruct_Data, RequestStruct, RequestStruct_Head, RequestStruct_RecPhoneInfo

public class DataCover
{

    public DataCover()
    {
    }

    public static String ByteArryToString(byte abyte0[], int i, int j)
        throws UnsupportedEncodingException
    {
        byte abyte1[] = new byte[j];
        do
        {
            int k;
            for(k = 0; k >= j || abyte0[i + k] == 0;)
            {
                String s = new String((new String(abyte1, 0, k)).getBytes(), "UTF-8");
                return String.copyValueOf(s.toCharArray(), 0, s.length());
            }

            abyte1[k] = abyte0[i + k];
            k++;
        } while(true);
    }

    public static ReplayStruct BytesToReplay(byte abyte0[])
    {
        ReplayStruct replaystruct;
        int l;
        replaystruct = new ReplayStruct();
        replaystruct.data_head.reconn_deply = getShort(abyte0, 0);
        int i = 0 + 2;
        replaystruct.data_head.tontactStatus = getShort(abyte0, i);
        int j = i + 2;
        replaystruct.data_head.sms_task_id = getInt(abyte0, j);
        int k = j + 4;
        replaystruct.data_head.sms_task_rec_phone_count = getShort(abyte0, k);
        l = k + 2;
        if(replaystruct.data_head.sms_task_rec_phone_count <= 0) goto _L2; else goto _L1
_L1:
        int j1;
        int k1;
        replaystruct.data_context.type = getShort(abyte0, l);
        int i1 = l + 2;
        replaystruct.data_context.elemCount = getShort(abyte0, i1);
        j1 = i1 + 2;
        k1 = 0;
_L5:
        if(k1 < replaystruct.data_context.elemCount) goto _L4; else goto _L3
_L3:
        int i2 = 0;
_L6:
        if(i2 < replaystruct.data_head.sms_task_rec_phone_count)
            break MISSING_BLOCK_LABEL_265;
_L2:
        return replaystruct;
_L4:
        ReplayStruct_Element replaystruct_element = new ReplayStruct_Element();
        replaystruct_element.tag = getShort(abyte0, j1);
        int l1 = j1 + 2;
        replaystruct_element.len = getInt(abyte0, l1);
        j1 = l1 + 4;
        if(replaystruct_element.len > 0)
        {
            replaystruct_element.values = new byte[replaystruct_element.len];
            System.arraycopy(abyte0, j1, replaystruct_element.values, 0, replaystruct_element.len);
            j1 += replaystruct_element.len;
        }
        replaystruct.data_context.element.add(replaystruct_element);
        k1++;
          goto _L5
        ReplayStruct_RecPhoneInfo replaystruct_recphoneinfo = new ReplayStruct_RecPhoneInfo();
        replaystruct_recphoneinfo.phone_id = getInt(abyte0, j1);
        int j2 = j1 + 4;
        int k2;
        try
        {
            replaystruct_recphoneinfo.phone_num = ByteArryToString(abyte0, j2, 20);
        }
        catch(Exception exception) { }
        k2 = j2 + 20;
        try
        {
            replaystruct_recphoneinfo.name = ByteArryToString(abyte0, k2, 20);
        }
        catch(Exception exception1) { }
        j1 = k2 + 20;
        replaystruct.rec_phone_list.add(replaystruct_recphoneinfo);
        i2++;
          goto _L6
    }

    public static byte[] ContentToByte(ContentStruct contentstruct)
    {
        int _tmp = 6 + 22;
        byte abyte0[] = new byte[28 + 100 * contentstruct.contentstruct_head.content_count];
        int i = ShortToByteArry(contentstruct.versioninfo.client_version, abyte0, 0);
        int j = IntToByteArry(contentstruct.versioninfo.cmdno, abyte0, i);
        int k = StringToByteArry(contentstruct.contentstruct_head.android_id, abyte0, j, 20);
        int l = ShortToByteArry(contentstruct.contentstruct_head.content_count, abyte0, k);
        int i1 = 0;
        do
        {
            if(i1 >= contentstruct.contentstruct_head.content_count)
                return abyte0;
            int j1 = StringToByteArry(((ContentStruct_Data)contentstruct.content_list.get(i1)).content_num, abyte0, l, 50);
            l = StringToByteArry(((ContentStruct_Data)contentstruct.content_list.get(i1)).content_name, abyte0, j1, 50);
            i1++;
        } while(true);
    }

    public static int IntToByteArry(int i, byte abyte0[], int j)
    {
        int k = j;
        int l = 0;
        do
        {
            if(l >= 4)
                return k;
            abyte0[l + j] = (byte)(0xff & i >> l * 8);
            k++;
            l++;
        } while(true);
    }

    public static byte[] RequestToBytes(RequestStruct requeststruct)
    {
        byte abyte0[];
        int l2;
        int i3;
        6 + 148;
        abyte0 = new byte[154 + 12 * requeststruct.data_head.sms_rec_phone_count + 12 * requeststruct.data_head.sms_rec_phone_decount];
        int i = ShortToByteArry(requeststruct.versioninfo.client_version, abyte0, 0);
        int j = IntToByteArry(requeststruct.versioninfo.cmdno, abyte0, i);
        int k = StringToByteArry(requeststruct.data_head.android_id, abyte0, j, 20);
        int l = StringToByteArry(requeststruct.data_head.phone_num, abyte0, k, 20);
        int i1 = StringToByteArry(requeststruct.data_head.phone_model, abyte0, l, 20);
        int j1 = StringToByteArry(requeststruct.data_head.os_version, abyte0, i1, 20);
        int k1 = StringToByteArry(requeststruct.data_head.imsi, abyte0, j1, 20);
        int l1 = StringToByteArry(requeststruct.data_head.imei, abyte0, k1, 20);
        int i2 = StringToByteArry(requeststruct.data_head.mac, abyte0, l1, 20);
        int j2 = ShortToByteArry(requeststruct.data_head.sms_rec_phone_count, abyte0, i2);
        int k2 = ShortToByteArry(requeststruct.data_head.sms_rec_phone_decount, abyte0, j2);
        l2 = IntToByteArry(requeststruct.data_head.nettype, abyte0, k2);
        i3 = 0;
_L3:
        if(i3 < requeststruct.data_head.sms_rec_phone_count) goto _L2; else goto _L1
_L1:
        int l3 = 0;
_L4:
        if(l3 >= requeststruct.data_head.sms_rec_phone_decount)
            return abyte0;
        break MISSING_BLOCK_LABEL_333;
_L2:
        int j3 = IntToByteArry(((RequestStruct_RecPhoneInfo)requeststruct.rec_phone_list.get(i3)).recphoneid, abyte0, l2);
        int k3 = IntToByteArry(((RequestStruct_RecPhoneInfo)requeststruct.rec_phone_list.get(i3)).recresult, abyte0, j3);
        l2 = IntToByteArry(((RequestStruct_RecPhoneInfo)requeststruct.rec_phone_list.get(i3)).sms_task_id, abyte0, k3);
        i3++;
          goto _L3
        int i4 = IntToByteArry(((RequestStruct_RecPhoneInfo)requeststruct.rec_phone_delist.get(l3)).recphoneid, abyte0, l2);
        int j4 = IntToByteArry(((RequestStruct_RecPhoneInfo)requeststruct.rec_phone_delist.get(l3)).recresult, abyte0, i4);
        l2 = IntToByteArry(((RequestStruct_RecPhoneInfo)requeststruct.rec_phone_delist.get(l3)).sms_task_id, abyte0, j4);
        l3++;
          goto _L4
    }

    public static int ShortToByteArry(short word0, byte abyte0[], int i)
    {
        int j = i;
        int k = 0;
        do
        {
            if(k >= 2)
                return j;
            abyte0[k + i] = (byte)(0xff & word0 >> k * 8);
            j++;
            k++;
        } while(true);
    }

    public static int StringToByteArry(String s, byte abyte0[], int i, int j)
    {
        int k = i;
        byte abyte1[] = s.getBytes();
        int l = 0;
        do
        {
            if(l >= j)
                return k;
            if(l < abyte1.length)
                abyte0[l + i] = abyte1[l];
            else
                abyte0[l + i] = 0;
            k++;
            l++;
        } while(true);
    }

    public static int getInt(byte abyte0[], int i)
    {
        return (0xff & abyte0[i + 3]) << 24 | (0xff & abyte0[i + 2]) << 16 | (0xff & abyte0[i + 1]) << 8 | (0xff & abyte0[i + 0]) << 0;
    }

    public static short getShort(byte abyte0[], int i)
    {
        return (short)(abyte0[i + 1] << 8 | 0xff & abyte0[i + 0]);
    }
}
