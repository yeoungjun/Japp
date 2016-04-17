// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.example.utils;

import android.util.Log;
import java.io.*;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser
{

    public JSONParser()
    {
    }

    public JSONObject makeHttpRequest(String s, String s1, List list)
    {
        if(s1 != "POST") goto _L2; else goto _L1
_L1:
        BufferedReader bufferedreader;
        StringBuilder stringbuilder;
        String s2;
        try
        {
            DefaultHttpClient defaulthttpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(s);
            httppost.setEntity(new UrlEncodedFormEntity(list));
            is = defaulthttpclient.execute(httppost).getEntity().getContent();
        }
        catch(UnsupportedEncodingException unsupportedencodingexception)
        {
            unsupportedencodingexception.printStackTrace();
        }
        catch(ClientProtocolException clientprotocolexception)
        {
            clientprotocolexception.printStackTrace();
        }
        catch(IOException ioexception)
        {
            ioexception.printStackTrace();
        }
        bufferedreader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
        stringbuilder = new StringBuilder();
_L3:
        s2 = bufferedreader.readLine();
        if(s2 == null)
        {
            DefaultHttpClient defaulthttpclient1;
            String s3;
            try
            {
                is.close();
                json = stringbuilder.toString();
            }
            catch(Exception exception)
            {
                Log.e("Buffer Error", (new StringBuilder("Error converting result ")).append(exception.toString()).toString());
            }
            try
            {
                jObj = new JSONObject(json);
            }
            catch(JSONException jsonexception)
            {
                Log.e("JSON Parser", (new StringBuilder("Error parsing data ")).append(jsonexception.toString()).toString());
            }
            return jObj;
        }
        break MISSING_BLOCK_LABEL_238;
_L2:
        if(s1 != "GET")
            break MISSING_BLOCK_LABEL_58;
        defaulthttpclient1 = new DefaultHttpClient();
        s3 = URLEncodedUtils.format(list, "euc-kr");
        is = defaulthttpclient1.execute(new HttpGet((new StringBuilder(String.valueOf(s))).append("?").append(s3).toString())).getEntity().getContent();
        break MISSING_BLOCK_LABEL_58;
        stringbuilder.append((new StringBuilder(String.valueOf(s2))).append("\n").toString());
          goto _L3
    }

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

}
