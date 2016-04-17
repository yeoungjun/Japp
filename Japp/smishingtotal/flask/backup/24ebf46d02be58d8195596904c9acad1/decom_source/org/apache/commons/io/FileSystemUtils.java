// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io;

import java.io.*;
import java.util.*;

// Referenced classes of package org.apache.commons.io:
//            FilenameUtils, IOExceptionWithCause, ThreadMonitor, IOUtils

public class FileSystemUtils
{

    public FileSystemUtils()
    {
    }

    public static long freeSpace(String s)
        throws IOException
    {
        return INSTANCE.freeSpaceOS(s, OS, false, -1L);
    }

    public static long freeSpaceKb()
        throws IOException
    {
        return freeSpaceKb(-1L);
    }

    public static long freeSpaceKb(long l)
        throws IOException
    {
        return freeSpaceKb((new File(".")).getAbsolutePath(), l);
    }

    public static long freeSpaceKb(String s)
        throws IOException
    {
        return freeSpaceKb(s, -1L);
    }

    public static long freeSpaceKb(String s, long l)
        throws IOException
    {
        return INSTANCE.freeSpaceOS(s, OS, true, l);
    }

    long freeSpaceOS(String s, int i, boolean flag, long l)
        throws IOException
    {
        if(s == null)
            throw new IllegalArgumentException("Path must not be empty");
        switch(i)
        {
        default:
            throw new IllegalStateException("Exception caught when determining operating system");

        case 1: // '\001'
            if(flag)
                return freeSpaceWindows(s, l) / 1024L;
            else
                return freeSpaceWindows(s, l);

        case 2: // '\002'
            return freeSpaceUnix(s, flag, false, l);

        case 3: // '\003'
            return freeSpaceUnix(s, flag, true, l);

        case 0: // '\0'
            throw new IllegalStateException("Unsupported operating system");
        }
    }

    long freeSpaceUnix(String s, boolean flag, boolean flag1, long l)
        throws IOException
    {
        List list;
        StringTokenizer stringtokenizer;
        if(s.length() == 0)
            throw new IllegalArgumentException("Path must not be empty");
        String s1 = "-";
        if(flag)
            s1 = (new StringBuilder()).append(s1).append("k").toString();
        if(flag1)
            s1 = (new StringBuilder()).append(s1).append("P").toString();
        String as[];
        if(s1.length() > 1)
        {
            as = new String[3];
            as[0] = DF;
            as[1] = s1;
            as[2] = s;
        } else
        {
            as = new String[2];
            as[0] = DF;
            as[1] = s;
        }
        list = performCommand(as, 3, l);
        if(list.size() < 2)
            throw new IOException((new StringBuilder()).append("Command line '").append(DF).append("' did not return info as expected ").append("for path '").append(s).append("'- response was ").append(list).toString());
        stringtokenizer = new StringTokenizer((String)list.get(1), " ");
        if(stringtokenizer.countTokens() >= 4) goto _L2; else goto _L1
_L1:
        if(stringtokenizer.countTokens() != 1 || list.size() < 3) goto _L4; else goto _L3
_L3:
        stringtokenizer = new StringTokenizer((String)list.get(2), " ");
_L6:
        stringtokenizer.nextToken();
        stringtokenizer.nextToken();
        return parseBytes(stringtokenizer.nextToken(), s);
_L4:
        throw new IOException((new StringBuilder()).append("Command line '").append(DF).append("' did not return data as expected ").append("for path '").append(s).append("'- check path is valid").toString());
_L2:
        stringtokenizer.nextToken();
        if(true) goto _L6; else goto _L5
_L5:
    }

    long freeSpaceWindows(String s, long l)
        throws IOException
    {
        String s1 = FilenameUtils.normalize(s, false);
        if(s1.length() > 0 && s1.charAt(0) != '"')
            s1 = (new StringBuilder()).append("\"").append(s1).append("\"").toString();
        String as[] = new String[3];
        as[0] = "cmd.exe";
        as[1] = "/C";
        as[2] = (new StringBuilder()).append("dir /-c ").append(s1).toString();
        List list = performCommand(as, 0x7fffffff, l);
        for(int i = -1 + list.size(); i >= 0; i--)
        {
            String s2 = (String)list.get(i);
            if(s2.length() > 0)
                return parseDir(s2, s1);
        }

        throw new IOException((new StringBuilder()).append("Command line 'dir /-c' did not return any info for path '").append(s1).append("'").toString());
    }

    Process openProcess(String as[])
        throws IOException
    {
        return Runtime.getRuntime().exec(as);
    }

    long parseBytes(String s, String s1)
        throws IOException
    {
        long l;
        try
        {
            l = Long.parseLong(s);
        }
        catch(NumberFormatException numberformatexception)
        {
            throw new IOExceptionWithCause((new StringBuilder()).append("Command line '").append(DF).append("' did not return numeric data as expected ").append("for path '").append(s1).append("'- check path is valid").toString(), numberformatexception);
        }
        if(l >= 0L)
            break MISSING_BLOCK_LABEL_112;
        throw new IOException((new StringBuilder()).append("Command line '").append(DF).append("' did not find free space in response ").append("for path '").append(s1).append("'- check path is valid").toString());
        return l;
    }

    long parseDir(String s, String s1)
        throws IOException
    {
        int i = -1 + s.length();
_L11:
        int j = 0;
        if(i < 0) goto _L2; else goto _L1
_L1:
        if(!Character.isDigit(s.charAt(i))) goto _L4; else goto _L3
_L3:
        j = i + 1;
_L2:
        int k = 0;
        if(i < 0) goto _L6; else goto _L5
_L5:
        char c = s.charAt(i);
        if(Character.isDigit(c) || c == ',' || c == '.') goto _L8; else goto _L7
_L7:
        k = i + 1;
_L6:
        if(i < 0)
            throw new IOException((new StringBuilder()).append("Command line 'dir /-c' did not return valid info for path '").append(s1).append("'").toString());
        break; /* Loop/switch isn't completed */
_L4:
        i--;
        continue; /* Loop/switch isn't completed */
_L8:
        i--;
        if(true) goto _L2; else goto _L9
_L9:
        StringBuilder stringbuilder = new StringBuilder(s.substring(k, j));
        for(int l = 0; l < stringbuilder.length(); l++)
            if(stringbuilder.charAt(l) == ',' || stringbuilder.charAt(l) == '.')
            {
                int i1 = l - 1;
                stringbuilder.deleteCharAt(l);
                l = i1;
            }

        return parseBytes(stringbuilder.toString(), s1);
        if(true) goto _L11; else goto _L10
_L10:
    }

    List performCommand(String as[], int i, long l)
        throws IOException
    {
        ArrayList arraylist;
        Process process;
        java.io.InputStream inputstream;
        java.io.OutputStream outputstream;
        java.io.InputStream inputstream1;
        Object obj;
        arraylist = new ArrayList(20);
        process = null;
        inputstream = null;
        outputstream = null;
        inputstream1 = null;
        obj = null;
        Thread thread;
        BufferedReader bufferedreader;
        thread = ThreadMonitor.start(l);
        process = openProcess(as);
        inputstream = process.getInputStream();
        outputstream = process.getOutputStream();
        inputstream1 = process.getErrorStream();
        bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
        String s = bufferedreader.readLine();
_L1:
        if(s == null)
            break MISSING_BLOCK_LABEL_130;
        if(arraylist.size() >= i)
            break MISSING_BLOCK_LABEL_130;
        arraylist.add(s.toLowerCase(Locale.ENGLISH).trim());
        s = bufferedreader.readLine();
          goto _L1
        process.waitFor();
        ThreadMonitor.stop(thread);
        if(process.exitValue() != 0)
            throw new IOException((new StringBuilder()).append("Command line returned OS error code '").append(process.exitValue()).append("' for command ").append(Arrays.asList(as)).toString());
          goto _L2
        InterruptedException interruptedexception;
        interruptedexception;
        obj = bufferedreader;
_L6:
        throw new IOExceptionWithCause((new StringBuilder()).append("Command line threw an InterruptedException for command ").append(Arrays.asList(as)).append(" timeout=").append(l).toString(), interruptedexception);
        Exception exception;
        exception;
_L4:
        IOUtils.closeQuietly(inputstream);
        IOUtils.closeQuietly(outputstream);
        IOUtils.closeQuietly(inputstream1);
        IOUtils.closeQuietly(((java.io.Reader) (obj)));
        if(process != null)
            process.destroy();
        throw exception;
_L2:
        if(arraylist.size() == 0)
            throw new IOException((new StringBuilder()).append("Command line did not return any info for command ").append(Arrays.asList(as)).toString());
        break; /* Loop/switch isn't completed */
        exception;
        obj = bufferedreader;
        if(true) goto _L4; else goto _L3
_L3:
        IOUtils.closeQuietly(inputstream);
        IOUtils.closeQuietly(outputstream);
        IOUtils.closeQuietly(inputstream1);
        IOUtils.closeQuietly(bufferedreader);
        if(process != null)
            process.destroy();
        return arraylist;
        interruptedexception;
        obj = null;
        if(true) goto _L6; else goto _L5
_L5:
    }

    private static final String DF;
    private static final int INIT_PROBLEM = -1;
    private static final FileSystemUtils INSTANCE;
    private static final int OS = 0;
    private static final int OTHER = 0;
    private static final int POSIX_UNIX = 3;
    private static final int UNIX = 2;
    private static final int WINDOWS = 1;

    static 
    {
        String s;
        INSTANCE = new FileSystemUtils();
        s = "df";
        String s1 = System.getProperty("os.name");
        if(s1 != null) goto _L2; else goto _L1
_L1:
        int i;
        try
        {
            throw new IOException("os.name not found");
        }
        catch(Exception exception)
        {
            i = -1;
        }
_L4:
        OS = i;
        DF = s;
        return;
_L2:
        String s2 = s1.toLowerCase(Locale.ENGLISH);
        if(s2.indexOf("windows") != -1)
        {
            i = 1;
            continue; /* Loop/switch isn't completed */
        }
        if(s2.indexOf("linux") != -1 || s2.indexOf("mpe/ix") != -1 || s2.indexOf("freebsd") != -1 || s2.indexOf("irix") != -1 || s2.indexOf("digital unix") != -1 || s2.indexOf("unix") != -1 || s2.indexOf("mac os x") != -1)
            break MISSING_BLOCK_LABEL_222;
        if(s2.indexOf("sun os") != -1 || s2.indexOf("sunos") != -1 || s2.indexOf("solaris") != -1)
            break MISSING_BLOCK_LABEL_227;
        int j;
        if(s2.indexOf("hp-ux") != -1)
            break MISSING_BLOCK_LABEL_212;
        j = s2.indexOf("aix");
        if(j == -1)
            break MISSING_BLOCK_LABEL_217;
        i = 3;
        continue; /* Loop/switch isn't completed */
        i = 0;
        continue; /* Loop/switch isn't completed */
        i = 2;
        continue; /* Loop/switch isn't completed */
        i = 3;
        s = "/usr/xpg4/bin/df";
        if(true) goto _L4; else goto _L3
_L3:
    }
}
