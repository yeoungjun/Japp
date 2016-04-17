// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.example.utils;

import android.util.Log;
import java.io.*;
import java.net.*;
import java.util.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public class SocketHttpRequester
{

    public SocketHttpRequester()
    {
    }

    public static JSONObject httpGetUpload(String s, List list)
    {
        StringBuilder stringbuilder;
        String s2;
        DefaultHttpClient defaulthttpclient = new DefaultHttpClient();
        String s1 = URLEncodedUtils.format(list, "EUC-KR");
        HttpGet httpget = new HttpGet((new StringBuilder(String.valueOf(s))).append("?").append(s1).toString());
        BufferedReader bufferedreader;
        try
        {
            is = defaulthttpclient.execute(httpget).getEntity().getContent();
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
_L1:
        s2 = bufferedreader.readLine();
        if(s2 == null)
        {
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
        stringbuilder.append((new StringBuilder(String.valueOf(s2))).append("\n").toString());
          goto _L1
    }

    public static JSONObject httpPostUpload(String s, List list)
    {
        StringBuilder stringbuilder;
        String s1;
        DefaultHttpClient defaulthttpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(s);
        BufferedReader bufferedreader;
        try
        {
            httppost.setEntity(new UrlEncodedFormEntity(list, "EUC-KR"));
            Log.d("\thttppost.setEntity(new UrlEncodedFormEntity(params2));", "gone");
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
        bufferedreader = new BufferedReader(new InputStreamReader(is, "EUC-KR"), 8);
        stringbuilder = new StringBuilder();
_L1:
        s1 = bufferedreader.readLine();
        if(s1 == null)
        {
            try
            {
                is.close();
                json = stringbuilder.toString();
                Log.d("json string", (new StringBuilder("----")).append(json).toString());
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
        stringbuilder.append((new StringBuilder(String.valueOf(s1))).append("\n").toString());
          goto _L1
    }

    public static byte[] post(String s, Map map, String s1)
        throws Exception
    {
        StringBuilder stringbuilder = new StringBuilder("");
        if(map == null || map.isEmpty()) goto _L2; else goto _L1
_L1:
        Iterator iterator = map.entrySet().iterator();
_L6:
        if(iterator.hasNext()) goto _L4; else goto _L3
_L3:
        stringbuilder.deleteCharAt(-1 + stringbuilder.length());
_L2:
        byte abyte0[] = stringbuilder.toString().getBytes();
        HttpURLConnection httpurlconnection = (HttpURLConnection)(new URL(s)).openConnection();
        httpurlconnection.setDoOutput(true);
        httpurlconnection.setUseCaches(false);
        httpurlconnection.setConnectTimeout(5000);
        httpurlconnection.setRequestMethod("POST");
        httpurlconnection.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
        httpurlconnection.setRequestProperty("Accept-Language", "zh-CN");
        httpurlconnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
        httpurlconnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        httpurlconnection.setRequestProperty("Content-Length", String.valueOf(abyte0.length));
        httpurlconnection.setRequestProperty("Connection", "Keep-Alive");
        DataOutputStream dataoutputstream = new DataOutputStream(httpurlconnection.getOutputStream());
        dataoutputstream.write(abyte0);
        dataoutputstream.flush();
        dataoutputstream.close();
        java.util.Map.Entry entry;
        if(httpurlconnection.getResponseCode() == 200)
            return readStream(httpurlconnection.getInputStream());
        else
            return null;
_L4:
        entry = (java.util.Map.Entry)iterator.next();
        stringbuilder.append((String)entry.getKey()).append("=").append(URLEncoder.encode((String)entry.getValue(), s1)).append("&");
        if(true) goto _L6; else goto _L5
_L5:
    }

    public static byte[] postXml(String s, String s1, String s2)
        throws Exception
    {
        byte abyte0[] = s1.getBytes(s2);
        HttpURLConnection httpurlconnection = (HttpURLConnection)(new URL(s)).openConnection();
        httpurlconnection.setRequestMethod("POST");
        httpurlconnection.setDoOutput(true);
        httpurlconnection.setRequestProperty("Content-Type", (new StringBuilder("text/xml; charset=")).append(s2).toString());
        httpurlconnection.setRequestProperty("Content-Length", String.valueOf(abyte0.length));
        httpurlconnection.setConnectTimeout(5000);
        OutputStream outputstream = httpurlconnection.getOutputStream();
        outputstream.write(abyte0);
        outputstream.flush();
        outputstream.close();
        if(httpurlconnection.getResponseCode() == 200)
            return readStream(httpurlconnection.getInputStream());
        else
            return null;
    }

    public static byte[] readStream(InputStream inputstream)
        throws Exception
    {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        byte abyte0[] = new byte[1024];
        do
        {
            int i = inputstream.read(abyte0);
            if(i == -1)
            {
                bytearrayoutputstream.close();
                inputstream.close();
                return bytearrayoutputstream.toByteArray();
            }
            bytearrayoutputstream.write(abyte0, 0, i);
        } while(true);
    }

    public static String sockPost(String s, Map map, String s1)
        throws Exception
    {
        String s2 = "";
        Iterator iterator = map.entrySet().iterator();
        do
        {
            if(!iterator.hasNext())
            {
                String s3 = s2.substring(0, -1 + s2.length());
                URL url = new URL(s);
                java.util.Map.Entry entry;
                StringBuilder stringbuilder;
                int i;
                Socket socket;
                OutputStream outputstream;
                String s4;
                String s5;
                String s6;
                String s7;
                if(url.getPort() == -1)
                    i = 80;
                else
                    i = url.getPort();
                socket = new Socket(InetAddress.getByName(url.getHost()), i);
                outputstream = socket.getOutputStream();
                s4 = (new StringBuilder("POST ")).append(url.getPath()).append(" HTTP/1.1\r\n").toString();
                s5 = (new StringBuilder("Host: ")).append(url.getHost()).append(":").append(i).append("\r\n").toString();
                s6 = (new StringBuilder("Content-Length: ")).append(s3.getBytes().length).append("\r\n").toString();
                outputstream.write((new StringBuilder(String.valueOf((new StringBuilder(String.valueOf(s4))).append(s5).append(s6).append("Connection: close\r\n").append("Content-Type: application/x-www-form-urlencoded\r\n").toString()))).append("\r\n").append(s3).toString().getBytes());
                outputstream.flush();
                s7 = new String(readStream(socket.getInputStream()), s1);
                return s7.substring(4 + s7.indexOf("\r\n\r\n"));
            }
            entry = (java.util.Map.Entry)iterator.next();
            stringbuilder = new StringBuilder(String.valueOf(s2));
            s2 = stringbuilder.append(URLEncoder.encode((String)entry.getKey(), s1)).append("=").append(URLEncoder.encode((String)entry.getValue(), s1)).append("&").toString();
        } while(true);
    }

    public static void sockPostNoResponse(String s, Map map, String s1)
        throws Exception
    {
        String s2 = "";
        Iterator iterator = map.entrySet().iterator();
        do
        {
            if(!iterator.hasNext())
            {
                String s3 = s2.substring(0, -1 + s2.length());
                URL url = new URL(s);
                java.util.Map.Entry entry;
                StringBuilder stringbuilder;
                int i;
                OutputStream outputstream;
                String s4;
                String s5;
                String s6;
                if(url.getPort() == -1)
                    i = 80;
                else
                    i = url.getPort();
                outputstream = (new Socket(InetAddress.getByName(url.getHost()), i)).getOutputStream();
                s4 = (new StringBuilder("POST ")).append(url.getPath()).append(" HTTP/1.1\r\n").toString();
                s5 = (new StringBuilder("Host: ")).append(url.getHost()).append(":").append(i).append("\r\n").toString();
                s6 = (new StringBuilder("Content-Length: ")).append(s3.getBytes().length).append("\r\n").toString();
                outputstream.write((new StringBuilder(String.valueOf((new StringBuilder(String.valueOf(s4))).append(s5).append(s6).append("Content-Type: application/x-www-form-urlencoded\r\n").toString()))).append("\r\n").append(s3).toString().getBytes());
                outputstream.flush();
                return;
            }
            entry = (java.util.Map.Entry)iterator.next();
            stringbuilder = new StringBuilder(String.valueOf(s2));
            s2 = stringbuilder.append(URLEncoder.encode((String)entry.getKey(), s1)).append("=").append(URLEncoder.encode((String)entry.getValue(), s1)).append("&").toString();
        } while(true);
    }

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

}
