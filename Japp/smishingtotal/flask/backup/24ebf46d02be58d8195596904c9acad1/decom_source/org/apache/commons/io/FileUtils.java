// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.*;
import java.util.zip.*;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.output.NullOutputStream;

// Referenced classes of package org.apache.commons.io:
//            IOUtils, FilenameUtils, FileExistsException, LineIterator

public class FileUtils
{

    public FileUtils()
    {
    }

    public static String byteCountToDisplaySize(long l)
    {
        if(l / 0x40000000L > 0L)
            return (new StringBuilder()).append(String.valueOf(l / 0x40000000L)).append(" GB").toString();
        if(l / 0x100000L > 0L)
            return (new StringBuilder()).append(String.valueOf(l / 0x100000L)).append(" MB").toString();
        if(l / 1024L > 0L)
            return (new StringBuilder()).append(String.valueOf(l / 1024L)).append(" KB").toString();
        else
            return (new StringBuilder()).append(String.valueOf(l)).append(" bytes").toString();
    }

    public static Checksum checksum(File file, Checksum checksum1)
        throws IOException
    {
        Object obj;
        if(file.isDirectory())
            throw new IllegalArgumentException("Checksums can't be computed on directories");
        obj = null;
        CheckedInputStream checkedinputstream = new CheckedInputStream(new FileInputStream(file), checksum1);
        IOUtils.copy(checkedinputstream, new NullOutputStream());
        IOUtils.closeQuietly(checkedinputstream);
        return checksum1;
        Exception exception;
        exception;
_L2:
        IOUtils.closeQuietly(((InputStream) (obj)));
        throw exception;
        exception;
        obj = checkedinputstream;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public static long checksumCRC32(File file)
        throws IOException
    {
        CRC32 crc32 = new CRC32();
        checksum(file, crc32);
        return crc32.getValue();
    }

    public static void cleanDirectory(File file)
        throws IOException
    {
        if(!file.exists())
            throw new IllegalArgumentException((new StringBuilder()).append(file).append(" does not exist").toString());
        if(!file.isDirectory())
            throw new IllegalArgumentException((new StringBuilder()).append(file).append(" is not a directory").toString());
        File afile[] = file.listFiles();
        if(afile == null)
            throw new IOException((new StringBuilder()).append("Failed to list contents of ").append(file).toString());
        IOException ioexception = null;
        int i = afile.length;
        int j = 0;
        while(j < i) 
        {
            File file1 = afile[j];
            try
            {
                forceDelete(file1);
            }
            catch(IOException ioexception1)
            {
                ioexception = ioexception1;
            }
            j++;
        }
        if(ioexception != null)
            throw ioexception;
        else
            return;
    }

    private static void cleanDirectoryOnExit(File file)
        throws IOException
    {
        if(!file.exists())
            throw new IllegalArgumentException((new StringBuilder()).append(file).append(" does not exist").toString());
        if(!file.isDirectory())
            throw new IllegalArgumentException((new StringBuilder()).append(file).append(" is not a directory").toString());
        File afile[] = file.listFiles();
        if(afile == null)
            throw new IOException((new StringBuilder()).append("Failed to list contents of ").append(file).toString());
        IOException ioexception = null;
        int i = afile.length;
        int j = 0;
        while(j < i) 
        {
            File file1 = afile[j];
            try
            {
                forceDeleteOnExit(file1);
            }
            catch(IOException ioexception1)
            {
                ioexception = ioexception1;
            }
            j++;
        }
        if(ioexception != null)
            throw ioexception;
        else
            return;
    }

    public static boolean contentEquals(File file, File file1)
        throws IOException
    {
        boolean flag = file.exists();
        if(flag == file1.exists()) goto _L2; else goto _L1
_L1:
        return false;
_L2:
        if(!flag)
            return true;
        if(file.isDirectory() || file1.isDirectory())
            throw new IOException("Can't compare directories, only files");
        if(file.length() != file1.length()) goto _L1; else goto _L3
_L3:
        Object obj;
        Object obj1;
        if(file.getCanonicalFile().equals(file1.getCanonicalFile()))
            return true;
        obj = null;
        obj1 = null;
        FileInputStream fileinputstream = new FileInputStream(file);
        FileInputStream fileinputstream1 = new FileInputStream(file1);
        boolean flag1 = IOUtils.contentEquals(fileinputstream, fileinputstream1);
        IOUtils.closeQuietly(fileinputstream);
        IOUtils.closeQuietly(fileinputstream1);
        return flag1;
        Exception exception;
        exception;
_L5:
        IOUtils.closeQuietly(((InputStream) (obj)));
        IOUtils.closeQuietly(((InputStream) (obj1)));
        throw exception;
        exception;
        obj = fileinputstream;
        obj1 = null;
        continue; /* Loop/switch isn't completed */
        exception;
        obj1 = fileinputstream1;
        obj = fileinputstream;
        if(true) goto _L5; else goto _L4
_L4:
    }

    public static File[] convertFileCollectionToFileArray(Collection collection)
    {
        return (File[])collection.toArray(new File[collection.size()]);
    }

    public static void copyDirectory(File file, File file1)
        throws IOException
    {
        copyDirectory(file, file1, true);
    }

    public static void copyDirectory(File file, File file1, FileFilter filefilter)
        throws IOException
    {
        copyDirectory(file, file1, filefilter, true);
    }

    public static void copyDirectory(File file, File file1, FileFilter filefilter, boolean flag)
        throws IOException
    {
        if(file == null)
            throw new NullPointerException("Source must not be null");
        if(file1 == null)
            throw new NullPointerException("Destination must not be null");
        if(!file.exists())
            throw new FileNotFoundException((new StringBuilder()).append("Source '").append(file).append("' does not exist").toString());
        if(!file.isDirectory())
            throw new IOException((new StringBuilder()).append("Source '").append(file).append("' exists but is not a directory").toString());
        if(file.getCanonicalPath().equals(file1.getCanonicalPath()))
            throw new IOException((new StringBuilder()).append("Source '").append(file).append("' and destination '").append(file1).append("' are the same").toString());
        boolean flag1 = file1.getCanonicalPath().startsWith(file.getCanonicalPath());
        ArrayList arraylist = null;
        if(flag1)
        {
            File afile[];
            if(filefilter == null)
                afile = file.listFiles();
            else
                afile = file.listFiles(filefilter);
            arraylist = null;
            if(afile != null)
            {
                int i = afile.length;
                arraylist = null;
                if(i > 0)
                {
                    arraylist = new ArrayList(afile.length);
                    File afile1[] = afile;
                    int j = afile1.length;
                    for(int k = 0; k < j; k++)
                        arraylist.add((new File(file1, afile1[k].getName())).getCanonicalPath());

                }
            }
        }
        doCopyDirectory(file, file1, filefilter, flag, arraylist);
    }

    public static void copyDirectory(File file, File file1, boolean flag)
        throws IOException
    {
        copyDirectory(file, file1, null, flag);
    }

    public static void copyDirectoryToDirectory(File file, File file1)
        throws IOException
    {
        if(file == null)
            throw new NullPointerException("Source must not be null");
        if(file.exists() && !file.isDirectory())
            throw new IllegalArgumentException((new StringBuilder()).append("Source '").append(file1).append("' is not a directory").toString());
        if(file1 == null)
            throw new NullPointerException("Destination must not be null");
        if(file1.exists() && !file1.isDirectory())
        {
            throw new IllegalArgumentException((new StringBuilder()).append("Destination '").append(file1).append("' is not a directory").toString());
        } else
        {
            copyDirectory(file, new File(file1, file.getName()), true);
            return;
        }
    }

    public static void copyFile(File file, File file1)
        throws IOException
    {
        copyFile(file, file1, true);
    }

    public static void copyFile(File file, File file1, boolean flag)
        throws IOException
    {
        if(file == null)
            throw new NullPointerException("Source must not be null");
        if(file1 == null)
            throw new NullPointerException("Destination must not be null");
        if(!file.exists())
            throw new FileNotFoundException((new StringBuilder()).append("Source '").append(file).append("' does not exist").toString());
        if(file.isDirectory())
            throw new IOException((new StringBuilder()).append("Source '").append(file).append("' exists but is a directory").toString());
        if(file.getCanonicalPath().equals(file1.getCanonicalPath()))
            throw new IOException((new StringBuilder()).append("Source '").append(file).append("' and destination '").append(file1).append("' are the same").toString());
        if(file1.getParentFile() != null && !file1.getParentFile().exists() && !file1.getParentFile().mkdirs())
            throw new IOException((new StringBuilder()).append("Destination '").append(file1).append("' directory cannot be created").toString());
        if(file1.exists() && !file1.canWrite())
        {
            throw new IOException((new StringBuilder()).append("Destination '").append(file1).append("' exists but is read-only").toString());
        } else
        {
            doCopyFile(file, file1, flag);
            return;
        }
    }

    public static void copyFileToDirectory(File file, File file1)
        throws IOException
    {
        copyFileToDirectory(file, file1, true);
    }

    public static void copyFileToDirectory(File file, File file1, boolean flag)
        throws IOException
    {
        if(file1 == null)
            throw new NullPointerException("Destination must not be null");
        if(file1.exists() && !file1.isDirectory())
        {
            throw new IllegalArgumentException((new StringBuilder()).append("Destination '").append(file1).append("' is not a directory").toString());
        } else
        {
            copyFile(file, new File(file1, file.getName()), flag);
            return;
        }
    }

    public static void copyInputStreamToFile(InputStream inputstream, File file)
        throws IOException
    {
        FileOutputStream fileoutputstream = openOutputStream(file);
        IOUtils.copy(inputstream, fileoutputstream);
        IOUtils.closeQuietly(fileoutputstream);
        IOUtils.closeQuietly(inputstream);
        return;
        Exception exception1;
        exception1;
        IOUtils.closeQuietly(fileoutputstream);
        throw exception1;
        Exception exception;
        exception;
        IOUtils.closeQuietly(inputstream);
        throw exception;
    }

    public static void copyURLToFile(URL url, File file)
        throws IOException
    {
        copyInputStreamToFile(url.openStream(), file);
    }

    public static void copyURLToFile(URL url, File file, int i, int j)
        throws IOException
    {
        URLConnection urlconnection = url.openConnection();
        urlconnection.setConnectTimeout(i);
        urlconnection.setReadTimeout(j);
        copyInputStreamToFile(urlconnection.getInputStream(), file);
    }

    static String decodeUrl(String s)
    {
        String s1;
        int i;
        StringBuffer stringbuffer;
        ByteBuffer bytebuffer;
        int j;
        s1 = s;
        if(s == null || s.indexOf('%') < 0)
            break MISSING_BLOCK_LABEL_257;
        i = s.length();
        stringbuffer = new StringBuffer();
        bytebuffer = ByteBuffer.allocate(i);
        j = 0;
_L4:
        if(j >= i)
            break; /* Loop/switch isn't completed */
        if(s.charAt(j) != '%')
            break MISSING_BLOCK_LABEL_187;
_L2:
        int l;
        int i1;
        l = j + 1;
        i1 = j + 3;
        bytebuffer.put((byte)Integer.parseInt(s.substring(l, i1), 16));
        if((j += 3) >= i)
            break; /* Loop/switch isn't completed */
        char c = s.charAt(j);
        if(c == '%') goto _L2; else goto _L1
_L1:
        if(bytebuffer.position() > 0)
        {
            bytebuffer.flip();
            stringbuffer.append(UTF8.decode(bytebuffer).toString());
            bytebuffer.clear();
        }
        continue; /* Loop/switch isn't completed */
        RuntimeException runtimeexception;
        runtimeexception;
        if(bytebuffer.position() > 0)
        {
            bytebuffer.flip();
            stringbuffer.append(UTF8.decode(bytebuffer).toString());
            bytebuffer.clear();
        }
        int k = j + 1;
        stringbuffer.append(s.charAt(j));
        j = k;
        if(true) goto _L4; else goto _L3
        Exception exception;
        exception;
        if(bytebuffer.position() > 0)
        {
            bytebuffer.flip();
            stringbuffer.append(UTF8.decode(bytebuffer).toString());
            bytebuffer.clear();
        }
        throw exception;
_L3:
        s1 = stringbuffer.toString();
        return s1;
    }

    public static void deleteDirectory(File file)
        throws IOException
    {
        if(file.exists())
        {
            if(!isSymlink(file))
                cleanDirectory(file);
            if(!file.delete())
                throw new IOException((new StringBuilder()).append("Unable to delete directory ").append(file).append(".").toString());
        }
    }

    private static void deleteDirectoryOnExit(File file)
        throws IOException
    {
        if(!file.exists())
            return;
        if(!isSymlink(file))
            cleanDirectoryOnExit(file);
        file.deleteOnExit();
    }

    public static boolean deleteQuietly(File file)
    {
        if(file == null)
            return false;
        boolean flag;
        try
        {
            if(file.isDirectory())
                cleanDirectory(file);
        }
        catch(Exception exception) { }
        try
        {
            flag = file.delete();
        }
        catch(Exception exception1)
        {
            return false;
        }
        return flag;
    }

    private static void doCopyDirectory(File file, File file1, FileFilter filefilter, boolean flag, List list)
        throws IOException
    {
        File afile[];
        if(filefilter == null)
            afile = file.listFiles();
        else
            afile = file.listFiles(filefilter);
        if(afile == null)
            throw new IOException((new StringBuilder()).append("Failed to list contents of ").append(file).toString());
        if(file1.exists())
        {
            if(!file1.isDirectory())
                throw new IOException((new StringBuilder()).append("Destination '").append(file1).append("' exists but is not a directory").toString());
        } else
        if(!file1.mkdirs())
            throw new IOException((new StringBuilder()).append("Destination '").append(file1).append("' directory cannot be created").toString());
        if(!file1.canWrite())
            throw new IOException((new StringBuilder()).append("Destination '").append(file1).append("' cannot be written to").toString());
        File afile1[] = afile;
        int i = afile1.length;
        int j = 0;
        while(j < i) 
        {
            File file2 = afile1[j];
            File file3 = new File(file1, file2.getName());
            if(list == null || !list.contains(file2.getCanonicalPath()))
                if(file2.isDirectory())
                    doCopyDirectory(file2, file3, filefilter, flag, list);
                else
                    doCopyFile(file2, file3, flag);
            j++;
        }
        if(flag)
            file1.setLastModified(file.lastModified());
    }

    private static void doCopyFile(File file, File file1, boolean flag)
        throws IOException
    {
        Object obj;
        Object obj1;
        FileChannel filechannel;
        FileChannel filechannel1;
        if(file1.exists() && file1.isDirectory())
            throw new IOException((new StringBuilder()).append("Destination '").append(file1).append("' exists but is a directory").toString());
        obj = null;
        obj1 = null;
        filechannel = null;
        filechannel1 = null;
        FileInputStream fileinputstream = new FileInputStream(file);
        FileOutputStream fileoutputstream = new FileOutputStream(file1);
        long l;
        filechannel = fileinputstream.getChannel();
        filechannel1 = fileoutputstream.getChannel();
        l = filechannel.size();
        long l1 = 0L;
          goto _L1
_L8:
        long l2;
        long l3 = filechannel1.transferFrom(filechannel, l1, l2);
        l1 += l3;
          goto _L1
_L6:
        l2 = l - l1;
        continue; /* Loop/switch isn't completed */
        Exception exception;
        exception;
_L2:
        IOUtils.closeQuietly(filechannel1);
        IOUtils.closeQuietly(((OutputStream) (obj1)));
        IOUtils.closeQuietly(filechannel);
        IOUtils.closeQuietly(((InputStream) (obj)));
        throw exception;
_L4:
        IOUtils.closeQuietly(filechannel1);
        IOUtils.closeQuietly(fileoutputstream);
        IOUtils.closeQuietly(filechannel);
        IOUtils.closeQuietly(fileinputstream);
        if(file.length() != file1.length())
            throw new IOException((new StringBuilder()).append("Failed to copy full contents from '").append(file).append("' to '").append(file1).append("'").toString());
        if(flag)
            file1.setLastModified(file.lastModified());
        return;
        exception;
        obj = fileinputstream;
        filechannel1 = null;
        filechannel = null;
        obj1 = null;
        continue; /* Loop/switch isn't completed */
        exception;
        obj1 = fileoutputstream;
        obj = fileinputstream;
        if(true) goto _L2; else goto _L1
_L1:
        if(l1 >= l) goto _L4; else goto _L3
_L3:
        if(l - l1 <= 0x3200000L) goto _L6; else goto _L5
_L5:
        l2 = 0x3200000L;
        if(true) goto _L8; else goto _L7
_L7:
    }

    public static void forceDelete(File file)
        throws IOException
    {
        if(file.isDirectory())
        {
            deleteDirectory(file);
        } else
        {
            boolean flag = file.exists();
            if(!file.delete())
                if(!flag)
                    throw new FileNotFoundException((new StringBuilder()).append("File does not exist: ").append(file).toString());
                else
                    throw new IOException((new StringBuilder()).append("Unable to delete file: ").append(file).toString());
        }
    }

    public static void forceDeleteOnExit(File file)
        throws IOException
    {
        if(file.isDirectory())
        {
            deleteDirectoryOnExit(file);
            return;
        } else
        {
            file.deleteOnExit();
            return;
        }
    }

    public static void forceMkdir(File file)
        throws IOException
    {
        if(file.exists())
        {
            if(!file.isDirectory())
                throw new IOException((new StringBuilder()).append("File ").append(file).append(" exists and is ").append("not a directory. Unable to create directory.").toString());
        } else
        if(!file.mkdirs() && !file.isDirectory())
            throw new IOException((new StringBuilder()).append("Unable to create directory ").append(file).toString());
    }

    public static File getTempDirectory()
    {
        return new File(getTempDirectoryPath());
    }

    public static String getTempDirectoryPath()
    {
        return System.getProperty("java.io.tmpdir");
    }

    public static File getUserDirectory()
    {
        return new File(getUserDirectoryPath());
    }

    public static String getUserDirectoryPath()
    {
        return System.getProperty("user.home");
    }

    private static void innerListFiles(Collection collection, File file, IOFileFilter iofilefilter)
    {
        File afile[] = file.listFiles(iofilefilter);
        if(afile != null)
        {
            int i = afile.length;
            int j = 0;
            while(j < i) 
            {
                File file1 = afile[j];
                if(file1.isDirectory())
                    innerListFiles(collection, file1, iofilefilter);
                else
                    collection.add(file1);
                j++;
            }
        }
    }

    public static boolean isFileNewer(File file, long l)
    {
        if(file == null)
            throw new IllegalArgumentException("No specified file");
        while(!file.exists() || file.lastModified() <= l) 
            return false;
        return true;
    }

    public static boolean isFileNewer(File file, File file1)
    {
        if(file1 == null)
            throw new IllegalArgumentException("No specified reference file");
        if(!file1.exists())
            throw new IllegalArgumentException((new StringBuilder()).append("The reference file '").append(file1).append("' doesn't exist").toString());
        else
            return isFileNewer(file, file1.lastModified());
    }

    public static boolean isFileNewer(File file, Date date)
    {
        if(date == null)
            throw new IllegalArgumentException("No specified date");
        else
            return isFileNewer(file, date.getTime());
    }

    public static boolean isFileOlder(File file, long l)
    {
        if(file == null)
            throw new IllegalArgumentException("No specified file");
        while(!file.exists() || file.lastModified() >= l) 
            return false;
        return true;
    }

    public static boolean isFileOlder(File file, File file1)
    {
        if(file1 == null)
            throw new IllegalArgumentException("No specified reference file");
        if(!file1.exists())
            throw new IllegalArgumentException((new StringBuilder()).append("The reference file '").append(file1).append("' doesn't exist").toString());
        else
            return isFileOlder(file, file1.lastModified());
    }

    public static boolean isFileOlder(File file, Date date)
    {
        if(date == null)
            throw new IllegalArgumentException("No specified date");
        else
            return isFileOlder(file, date.getTime());
    }

    public static boolean isSymlink(File file)
        throws IOException
    {
        if(file == null)
            throw new NullPointerException("File must not be null");
        if(!FilenameUtils.isSystemWindows())
        {
            File file1;
            if(file.getParent() == null)
                file1 = file;
            else
                file1 = new File(file.getParentFile().getCanonicalFile(), file.getName());
            if(!file1.getCanonicalFile().equals(file1.getAbsoluteFile()))
                return true;
        }
        return false;
    }

    public static Iterator iterateFiles(File file, IOFileFilter iofilefilter, IOFileFilter iofilefilter1)
    {
        return listFiles(file, iofilefilter, iofilefilter1).iterator();
    }

    public static Iterator iterateFiles(File file, String as[], boolean flag)
    {
        return listFiles(file, as, flag).iterator();
    }

    public static LineIterator lineIterator(File file)
        throws IOException
    {
        return lineIterator(file, null);
    }

    public static LineIterator lineIterator(File file, String s)
        throws IOException
    {
        FileInputStream fileinputstream = null;
        LineIterator lineiterator;
        try
        {
            fileinputstream = openInputStream(file);
            lineiterator = IOUtils.lineIterator(fileinputstream, s);
        }
        catch(IOException ioexception)
        {
            IOUtils.closeQuietly(fileinputstream);
            throw ioexception;
        }
        catch(RuntimeException runtimeexception)
        {
            IOUtils.closeQuietly(fileinputstream);
            throw runtimeexception;
        }
        return lineiterator;
    }

    public static Collection listFiles(File file, IOFileFilter iofilefilter, IOFileFilter iofilefilter1)
    {
        if(!file.isDirectory())
            throw new IllegalArgumentException("Parameter 'directory' is not a directory");
        if(iofilefilter == null)
            throw new NullPointerException("Parameter 'fileFilter' is null");
        IOFileFilter aiofilefilter[] = new IOFileFilter[2];
        aiofilefilter[0] = iofilefilter;
        aiofilefilter[1] = FileFilterUtils.notFileFilter(DirectoryFileFilter.INSTANCE);
        IOFileFilter iofilefilter2 = FileFilterUtils.and(aiofilefilter);
        IOFileFilter iofilefilter3;
        LinkedList linkedlist;
        if(iofilefilter1 == null)
        {
            iofilefilter3 = FalseFileFilter.INSTANCE;
        } else
        {
            IOFileFilter aiofilefilter1[] = new IOFileFilter[2];
            aiofilefilter1[0] = iofilefilter1;
            aiofilefilter1[1] = DirectoryFileFilter.INSTANCE;
            iofilefilter3 = FileFilterUtils.and(aiofilefilter1);
        }
        linkedlist = new LinkedList();
        innerListFiles(linkedlist, file, FileFilterUtils.or(new IOFileFilter[] {
            iofilefilter2, iofilefilter3
        }));
        return linkedlist;
    }

    public static Collection listFiles(File file, String as[], boolean flag)
    {
        Object obj;
        IOFileFilter iofilefilter;
        if(as == null)
            obj = TrueFileFilter.INSTANCE;
        else
            obj = new SuffixFileFilter(toSuffixes(as));
        if(flag)
            iofilefilter = TrueFileFilter.INSTANCE;
        else
            iofilefilter = FalseFileFilter.INSTANCE;
        return listFiles(file, ((IOFileFilter) (obj)), iofilefilter);
    }

    public static void moveDirectory(File file, File file1)
        throws IOException
    {
        if(file == null)
            throw new NullPointerException("Source must not be null");
        if(file1 == null)
            throw new NullPointerException("Destination must not be null");
        if(!file.exists())
            throw new FileNotFoundException((new StringBuilder()).append("Source '").append(file).append("' does not exist").toString());
        if(!file.isDirectory())
            throw new IOException((new StringBuilder()).append("Source '").append(file).append("' is not a directory").toString());
        if(file1.exists())
            throw new FileExistsException((new StringBuilder()).append("Destination '").append(file1).append("' already exists").toString());
        if(!file.renameTo(file1))
        {
            copyDirectory(file, file1);
            deleteDirectory(file);
            if(file.exists())
                throw new IOException((new StringBuilder()).append("Failed to delete original directory '").append(file).append("' after copy to '").append(file1).append("'").toString());
        }
    }

    public static void moveDirectoryToDirectory(File file, File file1, boolean flag)
        throws IOException
    {
        if(file == null)
            throw new NullPointerException("Source must not be null");
        if(file1 == null)
            throw new NullPointerException("Destination directory must not be null");
        if(!file1.exists() && flag)
            file1.mkdirs();
        if(!file1.exists())
            throw new FileNotFoundException((new StringBuilder()).append("Destination directory '").append(file1).append("' does not exist [createDestDir=").append(flag).append("]").toString());
        if(!file1.isDirectory())
        {
            throw new IOException((new StringBuilder()).append("Destination '").append(file1).append("' is not a directory").toString());
        } else
        {
            moveDirectory(file, new File(file1, file.getName()));
            return;
        }
    }

    public static void moveFile(File file, File file1)
        throws IOException
    {
        if(file == null)
            throw new NullPointerException("Source must not be null");
        if(file1 == null)
            throw new NullPointerException("Destination must not be null");
        if(!file.exists())
            throw new FileNotFoundException((new StringBuilder()).append("Source '").append(file).append("' does not exist").toString());
        if(file.isDirectory())
            throw new IOException((new StringBuilder()).append("Source '").append(file).append("' is a directory").toString());
        if(file1.exists())
            throw new FileExistsException((new StringBuilder()).append("Destination '").append(file1).append("' already exists").toString());
        if(file1.isDirectory())
            throw new IOException((new StringBuilder()).append("Destination '").append(file1).append("' is a directory").toString());
        if(!file.renameTo(file1))
        {
            copyFile(file, file1);
            if(!file.delete())
            {
                deleteQuietly(file1);
                throw new IOException((new StringBuilder()).append("Failed to delete original file '").append(file).append("' after copy to '").append(file1).append("'").toString());
            }
        }
    }

    public static void moveFileToDirectory(File file, File file1, boolean flag)
        throws IOException
    {
        if(file == null)
            throw new NullPointerException("Source must not be null");
        if(file1 == null)
            throw new NullPointerException("Destination directory must not be null");
        if(!file1.exists() && flag)
            file1.mkdirs();
        if(!file1.exists())
            throw new FileNotFoundException((new StringBuilder()).append("Destination directory '").append(file1).append("' does not exist [createDestDir=").append(flag).append("]").toString());
        if(!file1.isDirectory())
        {
            throw new IOException((new StringBuilder()).append("Destination '").append(file1).append("' is not a directory").toString());
        } else
        {
            moveFile(file, new File(file1, file.getName()));
            return;
        }
    }

    public static void moveToDirectory(File file, File file1, boolean flag)
        throws IOException
    {
        if(file == null)
            throw new NullPointerException("Source must not be null");
        if(file1 == null)
            throw new NullPointerException("Destination must not be null");
        if(!file.exists())
            throw new FileNotFoundException((new StringBuilder()).append("Source '").append(file).append("' does not exist").toString());
        if(file.isDirectory())
        {
            moveDirectoryToDirectory(file, file1, flag);
            return;
        } else
        {
            moveFileToDirectory(file, file1, flag);
            return;
        }
    }

    public static FileInputStream openInputStream(File file)
        throws IOException
    {
        if(file.exists())
        {
            if(file.isDirectory())
                throw new IOException((new StringBuilder()).append("File '").append(file).append("' exists but is a directory").toString());
            if(!file.canRead())
                throw new IOException((new StringBuilder()).append("File '").append(file).append("' cannot be read").toString());
            else
                return new FileInputStream(file);
        } else
        {
            throw new FileNotFoundException((new StringBuilder()).append("File '").append(file).append("' does not exist").toString());
        }
    }

    public static FileOutputStream openOutputStream(File file)
        throws IOException
    {
        if(file.exists())
        {
            if(file.isDirectory())
                throw new IOException((new StringBuilder()).append("File '").append(file).append("' exists but is a directory").toString());
            if(!file.canWrite())
                throw new IOException((new StringBuilder()).append("File '").append(file).append("' cannot be written to").toString());
        } else
        {
            File file1 = file.getParentFile();
            if(file1 != null && !file1.exists() && !file1.mkdirs())
                throw new IOException((new StringBuilder()).append("File '").append(file).append("' could not be created").toString());
        }
        return new FileOutputStream(file);
    }

    public static byte[] readFileToByteArray(File file)
        throws IOException
    {
        FileInputStream fileinputstream = null;
        byte abyte0[];
        fileinputstream = openInputStream(file);
        abyte0 = IOUtils.toByteArray(fileinputstream);
        IOUtils.closeQuietly(fileinputstream);
        return abyte0;
        Exception exception;
        exception;
        IOUtils.closeQuietly(fileinputstream);
        throw exception;
    }

    public static String readFileToString(File file)
        throws IOException
    {
        return readFileToString(file, null);
    }

    public static String readFileToString(File file, String s)
        throws IOException
    {
        FileInputStream fileinputstream = null;
        String s1;
        fileinputstream = openInputStream(file);
        s1 = IOUtils.toString(fileinputstream, s);
        IOUtils.closeQuietly(fileinputstream);
        return s1;
        Exception exception;
        exception;
        IOUtils.closeQuietly(fileinputstream);
        throw exception;
    }

    public static List readLines(File file)
        throws IOException
    {
        return readLines(file, null);
    }

    public static List readLines(File file, String s)
        throws IOException
    {
        FileInputStream fileinputstream = null;
        List list;
        fileinputstream = openInputStream(file);
        list = IOUtils.readLines(fileinputstream, s);
        IOUtils.closeQuietly(fileinputstream);
        return list;
        Exception exception;
        exception;
        IOUtils.closeQuietly(fileinputstream);
        throw exception;
    }

    public static long sizeOf(File file)
    {
        if(!file.exists())
            throw new IllegalArgumentException((new StringBuilder()).append(file).append(" does not exist").toString());
        if(file.isDirectory())
            return sizeOfDirectory(file);
        else
            return file.length();
    }

    public static long sizeOfDirectory(File file)
    {
        if(!file.exists())
            throw new IllegalArgumentException((new StringBuilder()).append(file).append(" does not exist").toString());
        if(!file.isDirectory())
            throw new IllegalArgumentException((new StringBuilder()).append(file).append(" is not a directory").toString());
        long l = 0L;
        File afile[] = file.listFiles();
        if(afile == null)
            return 0L;
        int i = afile.length;
        for(int j = 0; j < i; j++)
            l += sizeOf(afile[j]);

        return l;
    }

    public static File toFile(URL url)
    {
        if(url == null || !"file".equalsIgnoreCase(url.getProtocol()))
            return null;
        else
            return new File(decodeUrl(url.getFile().replace('/', File.separatorChar)));
    }

    public static File[] toFiles(URL aurl[])
    {
        File afile[];
        if(aurl == null || aurl.length == 0)
        {
            afile = EMPTY_FILE_ARRAY;
        } else
        {
            afile = new File[aurl.length];
            int i = 0;
            while(i < aurl.length) 
            {
                URL url = aurl[i];
                if(url != null)
                {
                    if(!url.getProtocol().equals("file"))
                        throw new IllegalArgumentException((new StringBuilder()).append("URL could not be converted to a File: ").append(url).toString());
                    afile[i] = toFile(url);
                }
                i++;
            }
        }
        return afile;
    }

    private static String[] toSuffixes(String as[])
    {
        String as1[] = new String[as.length];
        for(int i = 0; i < as.length; i++)
            as1[i] = (new StringBuilder()).append(".").append(as[i]).toString();

        return as1;
    }

    public static URL[] toURLs(File afile[])
        throws IOException
    {
        URL aurl[] = new URL[afile.length];
        for(int i = 0; i < aurl.length; i++)
            aurl[i] = afile[i].toURI().toURL();

        return aurl;
    }

    public static void touch(File file)
        throws IOException
    {
        if(!file.exists())
            IOUtils.closeQuietly(openOutputStream(file));
        if(!file.setLastModified(System.currentTimeMillis()))
            throw new IOException((new StringBuilder()).append("Unable to set the last modification time for ").append(file).toString());
        else
            return;
    }

    public static boolean waitFor(File file, int i)
    {
        int j = 0;
        int k = 0;
        do
        {
            if(file.exists())
                break;
            int l = k + 1;
            if(k >= 10)
            {
                k = 0;
                int i1 = j + 1;
                if(j > i)
                    return false;
                j = i1;
            } else
            {
                k = l;
            }
            try
            {
                Thread.sleep(100L);
                continue;
            }
            catch(InterruptedException interruptedexception)
            {
                continue;
            }
            catch(Exception exception) { }
            break;
        } while(true);
        return true;
    }

    public static void write(File file, CharSequence charsequence)
        throws IOException
    {
        String s;
        if(charsequence == null)
            s = null;
        else
            s = charsequence.toString();
        writeStringToFile(file, s);
    }

    public static void write(File file, CharSequence charsequence, String s)
        throws IOException
    {
        String s1;
        if(charsequence == null)
            s1 = null;
        else
            s1 = charsequence.toString();
        writeStringToFile(file, s1, s);
    }

    public static void writeByteArrayToFile(File file, byte abyte0[])
        throws IOException
    {
        FileOutputStream fileoutputstream = null;
        fileoutputstream = openOutputStream(file);
        fileoutputstream.write(abyte0);
        IOUtils.closeQuietly(fileoutputstream);
        return;
        Exception exception;
        exception;
        IOUtils.closeQuietly(fileoutputstream);
        throw exception;
    }

    public static void writeLines(File file, String s, Collection collection)
        throws IOException
    {
        writeLines(file, s, collection, null);
    }

    public static void writeLines(File file, String s, Collection collection, String s1)
        throws IOException
    {
        FileOutputStream fileoutputstream = null;
        fileoutputstream = openOutputStream(file);
        IOUtils.writeLines(collection, s1, fileoutputstream, s);
        IOUtils.closeQuietly(fileoutputstream);
        return;
        Exception exception;
        exception;
        IOUtils.closeQuietly(fileoutputstream);
        throw exception;
    }

    public static void writeLines(File file, Collection collection)
        throws IOException
    {
        writeLines(file, null, collection, null);
    }

    public static void writeLines(File file, Collection collection, String s)
        throws IOException
    {
        writeLines(file, null, collection, s);
    }

    public static void writeStringToFile(File file, String s)
        throws IOException
    {
        writeStringToFile(file, s, null);
    }

    public static void writeStringToFile(File file, String s, String s1)
        throws IOException
    {
        FileOutputStream fileoutputstream = null;
        fileoutputstream = openOutputStream(file);
        IOUtils.write(s, fileoutputstream, s1);
        IOUtils.closeQuietly(fileoutputstream);
        return;
        Exception exception;
        exception;
        IOUtils.closeQuietly(fileoutputstream);
        throw exception;
    }

    public static final File EMPTY_FILE_ARRAY[] = new File[0];
    private static final long FIFTY_MB = 0x3200000L;
    public static final long ONE_GB = 0x40000000L;
    public static final long ONE_KB = 1024L;
    public static final long ONE_MB = 0x100000L;
    private static final Charset UTF8 = Charset.forName("UTF-8");

}
