// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.activation.registries;

import java.io.*;
import java.util.*;

// Referenced classes of package com.sun.activation.registries:
//            LogSupport, MailcapParseException, MailcapTokenizer

public class MailcapFile
{

    public MailcapFile()
    {
        type_hash = new HashMap();
        fallback_hash = new HashMap();
        native_commands = new HashMap();
        if(LogSupport.isLoggable())
            LogSupport.log("new MailcapFile: default");
    }

    public MailcapFile(InputStream inputstream)
        throws IOException
    {
        type_hash = new HashMap();
        fallback_hash = new HashMap();
        native_commands = new HashMap();
        if(LogSupport.isLoggable())
            LogSupport.log("new MailcapFile: InputStream");
        parse(new BufferedReader(new InputStreamReader(inputstream, "iso-8859-1")));
    }

    public MailcapFile(String s)
        throws IOException
    {
        FileReader filereader;
        type_hash = new HashMap();
        fallback_hash = new HashMap();
        native_commands = new HashMap();
        if(LogSupport.isLoggable())
            LogSupport.log((new StringBuilder("new MailcapFile: file ")).append(s).toString());
        filereader = null;
        FileReader filereader1 = new FileReader(s);
        parse(new BufferedReader(filereader1));
        if(filereader1 == null)
            break MISSING_BLOCK_LABEL_93;
        filereader1.close();
        return;
        Exception exception;
        exception;
_L2:
        if(filereader != null)
            try
            {
                filereader.close();
            }
            catch(IOException ioexception) { }
        throw exception;
        IOException ioexception1;
        ioexception1;
        return;
        exception;
        filereader = filereader1;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private Map mergeResults(Map map, Map map1)
    {
        Iterator iterator = map1.keySet().iterator();
        HashMap hashmap = new HashMap(map);
        do
        {
            if(!iterator.hasNext())
                return hashmap;
            String s = (String)iterator.next();
            List list = (List)hashmap.get(s);
            if(list == null)
            {
                hashmap.put(s, map1.get(s));
            } else
            {
                List list1 = (List)map1.get(s);
                ArrayList arraylist = new ArrayList(list);
                arraylist.addAll(list1);
                hashmap.put(s, arraylist);
            }
        } while(true);
    }

    private void parse(Reader reader)
        throws IOException
    {
        BufferedReader bufferedreader;
        String s;
        bufferedreader = new BufferedReader(reader);
        s = null;
_L2:
        String s2;
        String s1 = bufferedreader.readLine();
        if(s1 == null)
            return;
        s2 = s1.trim();
        if(s2.charAt(0) == '#') goto _L2; else goto _L1
_L1:
        if(s2.charAt(-1 + s2.length()) != '\\')
            break MISSING_BLOCK_LABEL_113;
label0:
        {
            if(s == null)
                break label0;
            String s3;
            MailcapParseException mailcapparseexception;
            MailcapParseException mailcapparseexception1;
            try
            {
                s = (new StringBuilder(String.valueOf(s))).append(s2.substring(0, -1 + s2.length())).toString();
            }
            catch(StringIndexOutOfBoundsException stringindexoutofboundsexception) { }
        }
          goto _L2
        s = s2.substring(0, -1 + s2.length());
          goto _L2
        if(s == null) goto _L4; else goto _L3
_L3:
        s3 = (new StringBuilder(String.valueOf(s))).append(s2).toString();
        s = s3;
        parseLine(s);
_L5:
        s = null;
          goto _L2
_L4:
        parseLine(s2);
          goto _L2
        mailcapparseexception1;
          goto _L2
        mailcapparseexception;
          goto _L5
    }

    protected static void reportParseError(int i, int j, int k, int l, String s)
        throws MailcapParseException
    {
        if(LogSupport.isLoggable())
            LogSupport.log((new StringBuilder("PARSE ERROR: Encountered a ")).append(MailcapTokenizer.nameForToken(l)).append(" token (").append(s).append(") while expecting a ").append(MailcapTokenizer.nameForToken(i)).append(", a ").append(MailcapTokenizer.nameForToken(j)).append(", or a ").append(MailcapTokenizer.nameForToken(k)).append(" token.").toString());
        throw new MailcapParseException((new StringBuilder("Encountered a ")).append(MailcapTokenizer.nameForToken(l)).append(" token (").append(s).append(") while expecting a ").append(MailcapTokenizer.nameForToken(i)).append(", a ").append(MailcapTokenizer.nameForToken(j)).append(", or a ").append(MailcapTokenizer.nameForToken(k)).append(" token.").toString());
    }

    protected static void reportParseError(int i, int j, int k, String s)
        throws MailcapParseException
    {
        throw new MailcapParseException((new StringBuilder("Encountered a ")).append(MailcapTokenizer.nameForToken(k)).append(" token (").append(s).append(") while expecting a ").append(MailcapTokenizer.nameForToken(i)).append(" or a ").append(MailcapTokenizer.nameForToken(j)).append(" token.").toString());
    }

    protected static void reportParseError(int i, int j, String s)
        throws MailcapParseException
    {
        throw new MailcapParseException((new StringBuilder("Encountered a ")).append(MailcapTokenizer.nameForToken(j)).append(" token (").append(s).append(") while expecting a ").append(MailcapTokenizer.nameForToken(i)).append(" token.").toString());
    }

    public void appendToMailcap(String s)
    {
        if(LogSupport.isLoggable())
            LogSupport.log((new StringBuilder("appendToMailcap: ")).append(s).toString());
        try
        {
            parse(new StringReader(s));
            return;
        }
        catch(IOException ioexception)
        {
            return;
        }
    }

    public Map getMailcapFallbackList(String s)
    {
        Map map1;
label0:
        {
            Map map = (Map)fallback_hash.get(s);
            int i = s.indexOf('/');
            if(!s.substring(i + 1).equals("*"))
            {
                String s1 = (new StringBuilder(String.valueOf(s.substring(0, i + 1)))).append("*").toString();
                map1 = (Map)fallback_hash.get(s1);
                if(map1 != null)
                {
                    if(map == null)
                        break label0;
                    map = mergeResults(map, map1);
                }
            }
            return map;
        }
        return map1;
    }

    public Map getMailcapList(String s)
    {
        Map map1;
label0:
        {
            Map map = (Map)type_hash.get(s);
            int i = s.indexOf('/');
            if(!s.substring(i + 1).equals("*"))
            {
                String s1 = (new StringBuilder(String.valueOf(s.substring(0, i + 1)))).append("*").toString();
                map1 = (Map)type_hash.get(s1);
                if(map1 != null)
                {
                    if(map == null)
                        break label0;
                    map = mergeResults(map, map1);
                }
            }
            return map;
        }
        return map1;
    }

    public String[] getMimeTypes()
    {
        HashSet hashset = new HashSet(type_hash.keySet());
        hashset.addAll(fallback_hash.keySet());
        hashset.addAll(native_commands.keySet());
        return (String[])hashset.toArray(new String[hashset.size()]);
    }

    public String[] getNativeCommands(String s)
    {
        String as[] = (String[])null;
        List list = (List)native_commands.get(s.toLowerCase(Locale.ENGLISH));
        if(list != null)
            as = (String[])list.toArray(new String[list.size()]);
        return as;
    }

    protected void parseLine(String s)
        throws MailcapParseException, IOException
    {
        MailcapTokenizer mailcaptokenizer;
        String s3;
        LinkedHashMap linkedhashmap;
        int k;
        Map map1;
        mailcaptokenizer = new MailcapTokenizer(s);
        mailcaptokenizer.setIsAutoquoting(false);
        if(LogSupport.isLoggable())
            LogSupport.log((new StringBuilder("parse: ")).append(s).toString());
        int i = mailcaptokenizer.nextToken();
        if(i != 2)
            reportParseError(2, i, mailcaptokenizer.getCurrentTokenValue());
        String s1 = mailcaptokenizer.getCurrentTokenValue().toLowerCase(Locale.ENGLISH);
        String s2 = "*";
        int j = mailcaptokenizer.nextToken();
        if(j != 47 && j != 59)
            reportParseError(47, 59, j, mailcaptokenizer.getCurrentTokenValue());
        if(j == 47)
        {
            int k1 = mailcaptokenizer.nextToken();
            if(k1 != 2)
                reportParseError(2, k1, mailcaptokenizer.getCurrentTokenValue());
            s2 = mailcaptokenizer.getCurrentTokenValue().toLowerCase(Locale.ENGLISH);
            j = mailcaptokenizer.nextToken();
        }
        s3 = (new StringBuilder(String.valueOf(s1))).append("/").append(s2).toString();
        if(LogSupport.isLoggable())
            LogSupport.log((new StringBuilder("  Type: ")).append(s3).toString());
        linkedhashmap = new LinkedHashMap();
        if(j != 59)
            reportParseError(59, j, mailcaptokenizer.getCurrentTokenValue());
        mailcaptokenizer.setIsAutoquoting(true);
        k = mailcaptokenizer.nextToken();
        mailcaptokenizer.setIsAutoquoting(false);
        if(k != 2 && k != 59)
            reportParseError(2, 59, k, mailcaptokenizer.getCurrentTokenValue());
        if(k == 2)
        {
            List list2 = (List)native_commands.get(s3);
            boolean flag;
            int l;
            String s4;
            int i1;
            int j1;
            if(list2 == null)
            {
                ArrayList arraylist = new ArrayList();
                arraylist.add(s);
                native_commands.put(s3, arraylist);
            } else
            {
                list2.add(s);
            }
        }
        if(k != 59)
            k = mailcaptokenizer.nextToken();
        if(k != 59) goto _L2; else goto _L1
_L1:
        flag = false;
        Map map;
        do
        {
            l = mailcaptokenizer.nextToken();
            if(l != 2)
                reportParseError(2, l, mailcaptokenizer.getCurrentTokenValue());
            s4 = mailcaptokenizer.getCurrentTokenValue().toLowerCase(Locale.ENGLISH);
            i1 = mailcaptokenizer.nextToken();
            if(i1 != 61 && i1 != 59 && i1 != 5)
                reportParseError(61, 59, 5, i1, mailcaptokenizer.getCurrentTokenValue());
            if(i1 != 61)
                continue;
            mailcaptokenizer.setIsAutoquoting(true);
            j1 = mailcaptokenizer.nextToken();
            mailcaptokenizer.setIsAutoquoting(false);
            if(j1 != 2)
                reportParseError(2, j1, mailcaptokenizer.getCurrentTokenValue());
            String s8 = mailcaptokenizer.getCurrentTokenValue();
            if(s4.startsWith("x-java-"))
            {
                String s9 = s4.substring(7);
                if(s9.equals("fallback-entry") && s8.equalsIgnoreCase("true"))
                {
                    flag = true;
                } else
                {
                    if(LogSupport.isLoggable())
                        LogSupport.log((new StringBuilder("    Command: ")).append(s9).append(", Class: ").append(s8).toString());
                    Object obj = (List)linkedhashmap.get(s9);
                    if(obj == null)
                    {
                        obj = new ArrayList();
                        linkedhashmap.put(s9, obj);
                    }
                    if(addReverse)
                        ((List) (obj)).add(0, s8);
                    else
                        ((List) (obj)).add(s8);
                }
            }
            i1 = mailcaptokenizer.nextToken();
        } while(i1 == 59);
        if(flag)
            map = fallback_hash;
        else
            map = type_hash;
        map1 = (Map)map.get(s3);
        if(map1 != null) goto _L4; else goto _L3
_L3:
        map.put(s3, linkedhashmap);
_L6:
        return;
_L4:
        if(LogSupport.isLoggable())
            LogSupport.log((new StringBuilder("Merging commands for type ")).append(s3).toString());
        Iterator iterator = map1.keySet().iterator();
        do
        {
            if(!iterator.hasNext())
            {
                Iterator iterator2 = linkedhashmap.keySet().iterator();
                while(iterator2.hasNext()) 
                {
                    String s7 = (String)iterator2.next();
                    if(!map1.containsKey(s7))
                        map1.put(s7, (List)linkedhashmap.get(s7));
                }
                continue; /* Loop/switch isn't completed */
            }
            String s5 = (String)iterator.next();
            List list = (List)map1.get(s5);
            List list1 = (List)linkedhashmap.get(s5);
            if(list1 != null)
            {
                Iterator iterator1 = list1.iterator();
                while(iterator1.hasNext()) 
                {
                    String s6 = (String)iterator1.next();
                    if(!list.contains(s6))
                        if(addReverse)
                            list.add(0, s6);
                        else
                            list.add(s6);
                }
            }
        } while(true);
_L2:
        if(k != 5)
        {
            reportParseError(5, 59, k, mailcaptokenizer.getCurrentTokenValue());
            return;
        }
        if(true) goto _L6; else goto _L5
_L5:
    }

    private static boolean addReverse = false;
    private Map fallback_hash;
    private Map native_commands;
    private Map type_hash;

    static 
    {
        try
        {
            addReverse = Boolean.getBoolean("javax.activation.addreverse");
        }
        catch(Throwable throwable) { }
    }
}
