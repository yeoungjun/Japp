// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.activation;

import com.sun.activation.registries.LogSupport;
import com.sun.activation.registries.MailcapFile;
import java.io.*;
import java.util.*;

// Referenced classes of package javax.activation:
//            CommandMap, CommandInfo, SecuritySupport, DataContentHandler

public class MailcapCommandMap extends CommandMap
{

    public MailcapCommandMap()
    {
        ArrayList arraylist;
        arraylist = new ArrayList(5);
        arraylist.add(null);
        LogSupport.log("MailcapCommandMap: load HOME");
        String s = System.getProperty("user.home");
        if(s == null)
            break MISSING_BLOCK_LABEL_84;
        MailcapFile mailcapfile1 = loadFile((new StringBuilder(String.valueOf(s))).append(File.separator).append(".mailcap").toString());
        MailcapFile mailcapfile;
        if(mailcapfile1 != null)
            try
            {
                arraylist.add(mailcapfile1);
            }
            catch(SecurityException securityexception) { }
        LogSupport.log("MailcapCommandMap: load SYS");
        mailcapfile = loadFile((new StringBuilder(String.valueOf(System.getProperty("java.home")))).append(File.separator).append("lib").append(File.separator).append("mailcap").toString());
        Exception exception;
        if(mailcapfile != null)
            try
            {
                arraylist.add(mailcapfile);
            }
            catch(SecurityException securityexception1) { }
        LogSupport.log("MailcapCommandMap: load JAR");
        loadAllResources(arraylist, "mailcap");
        LogSupport.log("MailcapCommandMap: load DEF");
        javax/activation/MailcapCommandMap;
        JVM INSTR monitorenter ;
        if(defDB == null)
            defDB = loadResource("mailcap.default");
        javax/activation/MailcapCommandMap;
        JVM INSTR monitorexit ;
        if(defDB != null)
            arraylist.add(defDB);
        DB = new MailcapFile[arraylist.size()];
        DB = (MailcapFile[])arraylist.toArray(DB);
        return;
        exception;
        javax/activation/MailcapCommandMap;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public MailcapCommandMap(InputStream inputstream)
    {
        this();
        LogSupport.log("MailcapCommandMap: load PROG");
        if(DB[0] != null)
            break MISSING_BLOCK_LABEL_32;
        DB[0] = new MailcapFile(inputstream);
        return;
        IOException ioexception;
        ioexception;
    }

    public MailcapCommandMap(String s)
        throws IOException
    {
        this();
        if(LogSupport.isLoggable())
            LogSupport.log((new StringBuilder("MailcapCommandMap: load PROG from ")).append(s).toString());
        if(DB[0] == null)
            DB[0] = new MailcapFile(s);
    }

    private void appendCmdsToList(Map map, List list)
    {
        Iterator iterator = map.keySet().iterator();
        do
        {
            if(!iterator.hasNext())
                return;
            String s = (String)iterator.next();
            Iterator iterator1 = ((List)map.get(s)).iterator();
            while(iterator1.hasNext()) 
                list.add(new CommandInfo(s, (String)iterator1.next()));
        } while(true);
    }

    private void appendPrefCmdsToList(Map map, List list)
    {
        Iterator iterator = map.keySet().iterator();
        do
        {
            String s;
            do
            {
                if(!iterator.hasNext())
                    return;
                s = (String)iterator.next();
            } while(checkForVerb(list, s));
            list.add(new CommandInfo(s, (String)((List)map.get(s)).get(0)));
        } while(true);
    }

    private boolean checkForVerb(List list, String s)
    {
        Iterator iterator = list.iterator();
        do
            if(!iterator.hasNext())
                return false;
        while(!((CommandInfo)iterator.next()).getCommandName().equals(s));
        return true;
    }

    private DataContentHandler getDataContentHandler(String s)
    {
        if(LogSupport.isLoggable())
            LogSupport.log("    got content-handler");
        if(LogSupport.isLoggable())
            LogSupport.log((new StringBuilder("      class ")).append(s).toString());
        ClassLoader classloader = SecuritySupport.getContextClassLoader();
        if(classloader != null)
            break MISSING_BLOCK_LABEL_59;
        ClassLoader classloader1 = getClass().getClassLoader();
        classloader = classloader1;
        Class class3 = classloader.loadClass(s);
        Class class2 = class3;
_L2:
        if(class2 == null)
            break; /* Loop/switch isn't completed */
        return (DataContentHandler)class2.newInstance();
        Exception exception;
        exception;
        Class class1 = Class.forName(s);
        class2 = class1;
        if(true) goto _L2; else goto _L1
        IllegalAccessException illegalaccessexception;
        illegalaccessexception;
        if(LogSupport.isLoggable())
            LogSupport.log((new StringBuilder("Can't load DCH ")).append(s).toString(), illegalaccessexception);
_L1:
        return null;
        ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        if(LogSupport.isLoggable())
            LogSupport.log((new StringBuilder("Can't load DCH ")).append(s).toString(), classnotfoundexception);
        continue; /* Loop/switch isn't completed */
        InstantiationException instantiationexception;
        instantiationexception;
        if(LogSupport.isLoggable())
            LogSupport.log((new StringBuilder("Can't load DCH ")).append(s).toString(), instantiationexception);
        if(true) goto _L1; else goto _L3
_L3:
    }

    private void loadAllResources(List list, String s)
    {
        boolean flag = false;
        ClassLoader classloader = SecuritySupport.getContextClassLoader();
        flag = false;
        if(classloader != null)
            break MISSING_BLOCK_LABEL_23;
        classloader = getClass().getClassLoader();
        flag = false;
        if(classloader == null) goto _L2; else goto _L1
_L1:
        java.net.URL aurl[] = SecuritySupport.getResources(classloader, s);
_L5:
        int i;
        flag = false;
        MailcapFile mailcapfile;
        int j;
        java.net.URL url;
        InputStream inputstream;
        Exception exception1;
        SecurityException securityexception;
        IOException ioexception1;
        IOException ioexception2;
        IOException ioexception3;
        if(aurl != null)
            try
            {
                if(LogSupport.isLoggable())
                    LogSupport.log("MailcapCommandMap: getResources");
                break MISSING_BLOCK_LABEL_425;
            }
            catch(Exception exception)
            {
                if(LogSupport.isLoggable())
                    LogSupport.log((new StringBuilder("MailcapCommandMap: can't load ")).append(s).toString(), exception);
            }
          goto _L3
_L8:
        j = aurl.length;
        if(i < j) goto _L4; else goto _L3
_L3:
        if(!flag)
        {
            if(LogSupport.isLoggable())
                LogSupport.log("MailcapCommandMap: !anyLoaded");
            mailcapfile = loadResource((new StringBuilder("/")).append(s).toString());
            if(mailcapfile != null)
                list.add(mailcapfile);
        }
        return;
_L2:
        aurl = SecuritySupport.getSystemResources(s);
          goto _L5
_L4:
        url = aurl[i];
        inputstream = null;
        if(LogSupport.isLoggable())
            LogSupport.log((new StringBuilder("MailcapCommandMap: URL ")).append(url).toString());
        inputstream = SecuritySupport.openStream(url);
        if(inputstream == null) goto _L7; else goto _L6
_L6:
        list.add(new MailcapFile(inputstream));
        flag = true;
        if(LogSupport.isLoggable())
            LogSupport.log((new StringBuilder("MailcapCommandMap: successfully loaded mailcap file from URL: ")).append(url).toString());
_L10:
        if(inputstream == null)
            break MISSING_BLOCK_LABEL_234;
        inputstream.close();
_L12:
        i++;
          goto _L8
_L7:
        if(!LogSupport.isLoggable()) goto _L10; else goto _L9
_L9:
        LogSupport.log((new StringBuilder("MailcapCommandMap: not loading mailcap file from URL: ")).append(url).toString());
          goto _L10
        ioexception2;
        if(LogSupport.isLoggable())
            LogSupport.log((new StringBuilder("MailcapCommandMap: can't load ")).append(url).toString(), ioexception2);
        if(inputstream == null) goto _L12; else goto _L11
_L11:
        inputstream.close();
          goto _L12
        ioexception3;
          goto _L12
        securityexception;
        if(LogSupport.isLoggable())
            LogSupport.log((new StringBuilder("MailcapCommandMap: can't load ")).append(url).toString(), securityexception);
        if(inputstream == null) goto _L12; else goto _L13
_L13:
        inputstream.close();
          goto _L12
        ioexception1;
          goto _L12
        exception1;
        if(inputstream == null)
            break MISSING_BLOCK_LABEL_379;
        try
        {
            inputstream.close();
        }
        catch(IOException ioexception) { }
        throw exception1;
        IOException ioexception4;
        ioexception4;
          goto _L12
        flag = false;
        i = 0;
          goto _L8
    }

    private MailcapFile loadFile(String s)
    {
        MailcapFile mailcapfile;
        try
        {
            mailcapfile = new MailcapFile(s);
        }
        catch(IOException ioexception)
        {
            return null;
        }
        return mailcapfile;
    }

    private MailcapFile loadResource(String s)
    {
        InputStream inputstream = null;
        inputstream = SecuritySupport.getResourceAsStream(getClass(), s);
        if(inputstream == null)
            break MISSING_BLOCK_LABEL_62;
        MailcapFile mailcapfile;
        mailcapfile = new MailcapFile(inputstream);
        if(LogSupport.isLoggable())
            LogSupport.log((new StringBuilder("MailcapCommandMap: successfully loaded mailcap file: ")).append(s).toString());
        Exception exception;
        SecurityException securityexception;
        IOException ioexception1;
        IOException ioexception2;
        IOException ioexception3;
        if(inputstream != null)
            try
            {
                inputstream.close();
            }
            catch(IOException ioexception4)
            {
                return mailcapfile;
            }
        return mailcapfile;
        if(LogSupport.isLoggable())
            LogSupport.log((new StringBuilder("MailcapCommandMap: not loading mailcap file: ")).append(s).toString());
        if(inputstream != null)
            try
            {
                inputstream.close();
            }
            catch(IOException ioexception5) { }
        return null;
        ioexception2;
        if(LogSupport.isLoggable())
            LogSupport.log((new StringBuilder("MailcapCommandMap: can't load ")).append(s).toString(), ioexception2);
        if(inputstream != null)
            try
            {
                inputstream.close();
            }
            // Misplaced declaration of an exception variable
            catch(IOException ioexception3) { }
        continue; /* Loop/switch isn't completed */
        securityexception;
        if(LogSupport.isLoggable())
            LogSupport.log((new StringBuilder("MailcapCommandMap: can't load ")).append(s).toString(), securityexception);
        if(inputstream != null)
            try
            {
                inputstream.close();
            }
            // Misplaced declaration of an exception variable
            catch(IOException ioexception1) { }
        if(true) goto _L2; else goto _L1
_L1:
        break MISSING_BLOCK_LABEL_189;
_L2:
        break MISSING_BLOCK_LABEL_96;
        exception;
        if(inputstream != null)
            try
            {
                inputstream.close();
            }
            catch(IOException ioexception) { }
        throw exception;
    }

    public void addMailcap(String s)
    {
        this;
        JVM INSTR monitorenter ;
        LogSupport.log("MailcapCommandMap: add to PROG");
        if(DB[0] == null)
            DB[0] = new MailcapFile();
        DB[0].appendToMailcap(s);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public DataContentHandler createDataContentHandler(String s)
    {
        this;
        JVM INSTR monitorenter ;
        if(LogSupport.isLoggable())
            LogSupport.log((new StringBuilder("MailcapCommandMap: createDataContentHandler for ")).append(s).toString());
        if(s == null) goto _L2; else goto _L1
_L1:
        s = s.toLowerCase(Locale.ENGLISH);
          goto _L2
_L10:
        int i;
        if(i < DB.length) goto _L4; else goto _L3
_L3:
        int j = 0;
_L11:
        int k = DB.length;
        if(j < k) goto _L6; else goto _L5
_L5:
        DataContentHandler datacontenthandler1 = null;
_L7:
        this;
        JVM INSTR monitorexit ;
        return datacontenthandler1;
_L4:
        if(DB[i] == null)
            break; /* Loop/switch isn't completed */
        Map map1;
        if(LogSupport.isLoggable())
            LogSupport.log((new StringBuilder("  search DB #")).append(i).toString());
        map1 = DB[i].getMailcapList(s);
        if(map1 == null)
            break; /* Loop/switch isn't completed */
        List list1 = (List)map1.get("content-handler");
        if(list1 == null)
            break; /* Loop/switch isn't completed */
        datacontenthandler1 = getDataContentHandler((String)list1.get(0));
        if(datacontenthandler1 == null) goto _L8; else goto _L7
_L6:
        if(DB[j] == null)
            break; /* Loop/switch isn't completed */
        Map map;
        if(LogSupport.isLoggable())
            LogSupport.log((new StringBuilder("  search fallback DB #")).append(j).toString());
        map = DB[j].getMailcapFallbackList(s);
        if(map == null)
            break; /* Loop/switch isn't completed */
        List list = (List)map.get("content-handler");
        if(list == null)
            break; /* Loop/switch isn't completed */
        DataContentHandler datacontenthandler = getDataContentHandler((String)list.get(0));
        datacontenthandler1 = datacontenthandler;
        if(datacontenthandler1 == null) goto _L9; else goto _L7
        Exception exception;
        exception;
        throw exception;
_L2:
        i = 0;
          goto _L10
_L8:
        i++;
          goto _L10
_L9:
        j++;
          goto _L11
    }

    public CommandInfo[] getAllCommands(String s)
    {
        this;
        JVM INSTR monitorenter ;
        ArrayList arraylist = new ArrayList();
        if(s == null) goto _L2; else goto _L1
_L1:
        s = s.toLowerCase(Locale.ENGLISH);
          goto _L2
_L10:
        int i;
        if(i < DB.length) goto _L4; else goto _L3
_L3:
        int j = 0;
_L11:
        if(j < DB.length) goto _L6; else goto _L5
_L5:
        CommandInfo acommandinfo[] = (CommandInfo[])arraylist.toArray(new CommandInfo[arraylist.size()]);
        this;
        JVM INSTR monitorexit ;
        return acommandinfo;
_L4:
        if(DB[i] != null) goto _L8; else goto _L7
_L8:
        Map map1 = DB[i].getMailcapList(s);
        if(map1 == null) goto _L7; else goto _L9
_L9:
        appendCmdsToList(map1, arraylist);
          goto _L7
        Exception exception;
        exception;
        throw exception;
_L6:
        if(DB[j] == null)
            break MISSING_BLOCK_LABEL_172;
        Map map = DB[j].getMailcapFallbackList(s);
        if(map == null)
            break MISSING_BLOCK_LABEL_172;
        appendCmdsToList(map, arraylist);
        break MISSING_BLOCK_LABEL_172;
_L2:
        i = 0;
          goto _L10
_L7:
        i++;
          goto _L10
        j++;
          goto _L11
    }

    public CommandInfo getCommand(String s, String s1)
    {
        this;
        JVM INSTR monitorenter ;
        if(s == null) goto _L2; else goto _L1
_L1:
        s = s.toLowerCase(Locale.ENGLISH);
          goto _L2
_L9:
        int i;
        if(i < DB.length) goto _L4; else goto _L3
_L3:
        int j = 0;
_L10:
        int k = DB.length;
        if(j < k) goto _L6; else goto _L5
_L5:
        CommandInfo commandinfo = null;
_L8:
        this;
        JVM INSTR monitorexit ;
        return commandinfo;
_L4:
        if(DB[i] == null)
            break; /* Loop/switch isn't completed */
        Map map1 = DB[i].getMailcapList(s);
        if(map1 == null)
            break; /* Loop/switch isn't completed */
        List list1 = (List)map1.get(s1);
        if(list1 == null)
            break; /* Loop/switch isn't completed */
        String s3 = (String)list1.get(0);
        if(s3 == null)
            break; /* Loop/switch isn't completed */
        commandinfo = new CommandInfo(s1, s3);
        continue; /* Loop/switch isn't completed */
        Exception exception;
        exception;
        throw exception;
_L6:
        if(DB[j] == null)
            break MISSING_BLOCK_LABEL_231;
        Map map = DB[j].getMailcapFallbackList(s);
        if(map == null)
            break MISSING_BLOCK_LABEL_231;
        List list = (List)map.get(s1);
        if(list == null)
            break MISSING_BLOCK_LABEL_231;
        String s2 = (String)list.get(0);
        if(s2 == null)
            break MISSING_BLOCK_LABEL_231;
        commandinfo = new CommandInfo(s1, s2);
        if(true) goto _L8; else goto _L7
_L2:
        i = 0;
          goto _L9
_L7:
        i++;
          goto _L9
        j++;
          goto _L10
    }

    public String[] getMimeTypes()
    {
        this;
        JVM INSTR monitorenter ;
        ArrayList arraylist = new ArrayList();
        int i = 0;
_L6:
        String as1[];
        if(i < DB.length)
            break MISSING_BLOCK_LABEL_46;
        as1 = (String[])arraylist.toArray(new String[arraylist.size()]);
        this;
        JVM INSTR monitorexit ;
        return as1;
        if(DB[i] != null) goto _L2; else goto _L1
_L2:
        String as[] = DB[i].getMimeTypes();
        if(as == null) goto _L1; else goto _L3
_L3:
        int j = 0;
_L4:
        if(j >= as.length)
            break; /* Loop/switch isn't completed */
        if(!arraylist.contains(as[j]))
            arraylist.add(as[j]);
        j++;
        if(true) goto _L4; else goto _L1
        Exception exception;
        exception;
        throw exception;
_L1:
        i++;
        if(true) goto _L6; else goto _L5
_L5:
    }

    public String[] getNativeCommands(String s)
    {
        this;
        JVM INSTR monitorenter ;
        ArrayList arraylist = new ArrayList();
        if(s == null) goto _L2; else goto _L1
_L1:
        s = s.toLowerCase(Locale.ENGLISH);
          goto _L2
_L8:
        int i;
        String as1[];
        if(i < DB.length)
            break MISSING_BLOCK_LABEL_60;
        as1 = (String[])arraylist.toArray(new String[arraylist.size()]);
        this;
        JVM INSTR monitorexit ;
        return as1;
        if(DB[i] != null) goto _L4; else goto _L3
_L4:
        String as[] = DB[i].getNativeCommands(s);
        if(as == null) goto _L3; else goto _L5
_L5:
        int j = 0;
_L6:
        if(j >= as.length)
            break; /* Loop/switch isn't completed */
        if(!arraylist.contains(as[j]))
            arraylist.add(as[j]);
        j++;
        if(true) goto _L6; else goto _L3
        Exception exception;
        exception;
        throw exception;
_L2:
        i = 0;
        continue; /* Loop/switch isn't completed */
_L3:
        i++;
        if(true) goto _L8; else goto _L7
_L7:
    }

    public CommandInfo[] getPreferredCommands(String s)
    {
        this;
        JVM INSTR monitorenter ;
        ArrayList arraylist = new ArrayList();
        if(s == null) goto _L2; else goto _L1
_L1:
        s = s.toLowerCase(Locale.ENGLISH);
          goto _L2
_L10:
        int i;
        if(i < DB.length) goto _L4; else goto _L3
_L3:
        int j = 0;
_L11:
        if(j < DB.length) goto _L6; else goto _L5
_L5:
        CommandInfo acommandinfo[] = (CommandInfo[])arraylist.toArray(new CommandInfo[arraylist.size()]);
        this;
        JVM INSTR monitorexit ;
        return acommandinfo;
_L4:
        if(DB[i] != null) goto _L8; else goto _L7
_L8:
        Map map1 = DB[i].getMailcapList(s);
        if(map1 == null) goto _L7; else goto _L9
_L9:
        appendPrefCmdsToList(map1, arraylist);
          goto _L7
        Exception exception;
        exception;
        throw exception;
_L6:
        if(DB[j] == null)
            break MISSING_BLOCK_LABEL_172;
        Map map = DB[j].getMailcapFallbackList(s);
        if(map == null)
            break MISSING_BLOCK_LABEL_172;
        appendPrefCmdsToList(map, arraylist);
        break MISSING_BLOCK_LABEL_172;
_L2:
        i = 0;
          goto _L10
_L7:
        i++;
          goto _L10
        j++;
          goto _L11
    }

    private static final int PROG;
    private static MailcapFile defDB = null;
    private MailcapFile DB[];

}
