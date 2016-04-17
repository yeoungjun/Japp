// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.activation.registries;

import java.io.*;
import java.util.Hashtable;
import java.util.StringTokenizer;

// Referenced classes of package com.sun.activation.registries:
//            LineTokenizer, LogSupport, MimeTypeEntry

public class MimeTypeFile
{

    public MimeTypeFile()
    {
        fname = null;
        type_hash = new Hashtable();
    }

    public MimeTypeFile(InputStream inputstream)
        throws IOException
    {
        fname = null;
        type_hash = new Hashtable();
        parse(new BufferedReader(new InputStreamReader(inputstream, "iso-8859-1")));
    }

    public MimeTypeFile(String s)
        throws IOException
    {
        FileReader filereader;
        fname = null;
        type_hash = new Hashtable();
        fname = s;
        filereader = new FileReader(new File(fname));
        parse(new BufferedReader(filereader));
        Exception exception;
        try
        {
            filereader.close();
            return;
        }
        catch(IOException ioexception1)
        {
            return;
        }
        exception;
        try
        {
            filereader.close();
        }
        catch(IOException ioexception) { }
        throw exception;
    }

    private void parse(BufferedReader bufferedreader)
        throws IOException
    {
        String s = null;
        do
        {
            String s1 = bufferedreader.readLine();
            if(s1 == null)
            {
                if(s != null)
                    parseEntry(s);
                return;
            }
            String s2;
            int i;
            if(s == null)
                s2 = s1;
            else
                s2 = (new StringBuilder(String.valueOf(s))).append(s1).toString();
            i = s2.length();
            if(s2.length() > 0 && s2.charAt(i - 1) == '\\')
            {
                s = s2.substring(0, i - 1);
            } else
            {
                parseEntry(s2);
                s = null;
            }
        } while(true);
    }

    private void parseEntry(String s)
    {
        String s1;
        String s2;
        s1 = null;
        s2 = s.trim();
        break MISSING_BLOCK_LABEL_7;
label0:
        while(true) 
        {
            do
                return;
            while(s2.length() == 0 || s2.charAt(0) == '#');
            if(s2.indexOf('=') > 0)
            {
                LineTokenizer linetokenizer = new LineTokenizer(s2);
                do
                {
                    String s3;
                    String s4;
label1:
                    do
                        do
                        {
                            if(!linetokenizer.hasMoreTokens())
                                continue label0;
                            s3 = linetokenizer.nextToken();
                            boolean flag = linetokenizer.hasMoreTokens();
                            s4 = null;
                            if(flag)
                            {
                                boolean flag1 = linetokenizer.nextToken().equals("=");
                                s4 = null;
                                if(flag1)
                                {
                                    boolean flag2 = linetokenizer.hasMoreTokens();
                                    s4 = null;
                                    if(flag2)
                                        s4 = linetokenizer.nextToken();
                                }
                            }
                            if(s4 == null)
                            {
                                if(LogSupport.isLoggable())
                                {
                                    LogSupport.log((new StringBuilder("Bad .mime.types entry: ")).append(s2).toString());
                                    return;
                                }
                                continue label0;
                            }
                            if(!s3.equals("type"))
                                continue label1;
                            s1 = s4;
                        } while(true);
                    while(!s3.equals("exts"));
                    StringTokenizer stringtokenizer = new StringTokenizer(s4, ",");
                    while(stringtokenizer.hasMoreTokens()) 
                    {
                        String s5 = stringtokenizer.nextToken();
                        MimeTypeEntry mimetypeentry = new MimeTypeEntry(s1, s5);
                        type_hash.put(s5, mimetypeentry);
                        if(LogSupport.isLoggable())
                            LogSupport.log((new StringBuilder("Added: ")).append(mimetypeentry.toString()).toString());
                    }
                } while(true);
            }
            StringTokenizer stringtokenizer1 = new StringTokenizer(s2);
            if(stringtokenizer1.countTokens() != 0)
            {
                String s6 = stringtokenizer1.nextToken();
                while(stringtokenizer1.hasMoreTokens()) 
                {
                    String s7 = stringtokenizer1.nextToken();
                    MimeTypeEntry mimetypeentry1 = new MimeTypeEntry(s6, s7);
                    type_hash.put(s7, mimetypeentry1);
                    if(LogSupport.isLoggable())
                        LogSupport.log((new StringBuilder("Added: ")).append(mimetypeentry1.toString()).toString());
                }
            }
        }
    }

    public void appendToRegistry(String s)
    {
        try
        {
            parse(new BufferedReader(new StringReader(s)));
            return;
        }
        catch(IOException ioexception)
        {
            return;
        }
    }

    public String getMIMETypeString(String s)
    {
        MimeTypeEntry mimetypeentry = getMimeTypeEntry(s);
        if(mimetypeentry != null)
            return mimetypeentry.getMIMEType();
        else
            return null;
    }

    public MimeTypeEntry getMimeTypeEntry(String s)
    {
        return (MimeTypeEntry)type_hash.get(s);
    }

    private String fname;
    private Hashtable type_hash;
}
