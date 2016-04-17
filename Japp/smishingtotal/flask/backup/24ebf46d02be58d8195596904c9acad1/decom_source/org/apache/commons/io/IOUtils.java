// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io;

import java.io.*;
import java.net.Socket;
import java.util.*;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.io.output.StringBuilderWriter;

// Referenced classes of package org.apache.commons.io:
//            LineIterator

public class IOUtils
{

    public IOUtils()
    {
    }

    public static void closeQuietly(Closeable closeable)
    {
        if(closeable == null)
            break MISSING_BLOCK_LABEL_10;
        closeable.close();
        return;
        IOException ioexception;
        ioexception;
    }

    public static void closeQuietly(InputStream inputstream)
    {
        closeQuietly(((Closeable) (inputstream)));
    }

    public static void closeQuietly(OutputStream outputstream)
    {
        closeQuietly(((Closeable) (outputstream)));
    }

    public static void closeQuietly(Reader reader)
    {
        closeQuietly(((Closeable) (reader)));
    }

    public static void closeQuietly(Writer writer)
    {
        closeQuietly(((Closeable) (writer)));
    }

    public static void closeQuietly(Socket socket)
    {
        if(socket == null)
            break MISSING_BLOCK_LABEL_8;
        socket.close();
        return;
        IOException ioexception;
        ioexception;
    }

    public static boolean contentEquals(InputStream inputstream, InputStream inputstream1)
        throws IOException
    {
        int i;
        if(!(inputstream instanceof BufferedInputStream))
            inputstream = new BufferedInputStream(inputstream);
        if(!(inputstream1 instanceof BufferedInputStream))
            inputstream1 = new BufferedInputStream(inputstream1);
        i = inputstream.read();
_L7:
        if(-1 == i) goto _L2; else goto _L1
_L1:
        if(i == inputstream1.read()) goto _L4; else goto _L3
_L3:
        return false;
_L4:
        i = inputstream.read();
        continue; /* Loop/switch isn't completed */
_L2:
        if(inputstream1.read() != -1) goto _L3; else goto _L5
_L5:
        return true;
        if(true) goto _L7; else goto _L6
_L6:
    }

    public static boolean contentEquals(Reader reader, Reader reader1)
        throws IOException
    {
        int i;
        if(!(reader instanceof BufferedReader))
            reader = new BufferedReader(reader);
        if(!(reader1 instanceof BufferedReader))
            reader1 = new BufferedReader(reader1);
        i = reader.read();
_L7:
        if(-1 == i) goto _L2; else goto _L1
_L1:
        if(i == reader1.read()) goto _L4; else goto _L3
_L3:
        return false;
_L4:
        i = reader.read();
        continue; /* Loop/switch isn't completed */
_L2:
        if(reader1.read() != -1) goto _L3; else goto _L5
_L5:
        return true;
        if(true) goto _L7; else goto _L6
_L6:
    }

    public static int copy(InputStream inputstream, OutputStream outputstream)
        throws IOException
    {
        long l = copyLarge(inputstream, outputstream);
        if(l > 0x7fffffffL)
            return -1;
        else
            return (int)l;
    }

    public static int copy(Reader reader, Writer writer)
        throws IOException
    {
        long l = copyLarge(reader, writer);
        if(l > 0x7fffffffL)
            return -1;
        else
            return (int)l;
    }

    public static void copy(InputStream inputstream, Writer writer)
        throws IOException
    {
        copy(((Reader) (new InputStreamReader(inputstream))), writer);
    }

    public static void copy(InputStream inputstream, Writer writer, String s)
        throws IOException
    {
        if(s == null)
        {
            copy(inputstream, writer);
            return;
        } else
        {
            copy(((Reader) (new InputStreamReader(inputstream, s))), writer);
            return;
        }
    }

    public static void copy(Reader reader, OutputStream outputstream)
        throws IOException
    {
        OutputStreamWriter outputstreamwriter = new OutputStreamWriter(outputstream);
        copy(reader, ((Writer) (outputstreamwriter)));
        outputstreamwriter.flush();
    }

    public static void copy(Reader reader, OutputStream outputstream, String s)
        throws IOException
    {
        if(s == null)
        {
            copy(reader, outputstream);
            return;
        } else
        {
            OutputStreamWriter outputstreamwriter = new OutputStreamWriter(outputstream, s);
            copy(reader, ((Writer) (outputstreamwriter)));
            outputstreamwriter.flush();
            return;
        }
    }

    public static long copyLarge(InputStream inputstream, OutputStream outputstream)
        throws IOException
    {
        byte abyte0[] = new byte[4096];
        long l = 0L;
        do
        {
            int i = inputstream.read(abyte0);
            if(-1 != i)
            {
                outputstream.write(abyte0, 0, i);
                l += i;
            } else
            {
                return l;
            }
        } while(true);
    }

    public static long copyLarge(Reader reader, Writer writer)
        throws IOException
    {
        char ac[] = new char[4096];
        long l = 0L;
        do
        {
            int i = reader.read(ac);
            if(-1 != i)
            {
                writer.write(ac, 0, i);
                l += i;
            } else
            {
                return l;
            }
        } while(true);
    }

    public static LineIterator lineIterator(InputStream inputstream, String s)
        throws IOException
    {
        InputStreamReader inputstreamreader;
        if(s == null)
            inputstreamreader = new InputStreamReader(inputstream);
        else
            inputstreamreader = new InputStreamReader(inputstream, s);
        return new LineIterator(inputstreamreader);
    }

    public static LineIterator lineIterator(Reader reader)
    {
        return new LineIterator(reader);
    }

    public static List readLines(InputStream inputstream)
        throws IOException
    {
        return readLines(((Reader) (new InputStreamReader(inputstream))));
    }

    public static List readLines(InputStream inputstream, String s)
        throws IOException
    {
        if(s == null)
            return readLines(inputstream);
        else
            return readLines(((Reader) (new InputStreamReader(inputstream, s))));
    }

    public static List readLines(Reader reader)
        throws IOException
    {
        BufferedReader bufferedreader = new BufferedReader(reader);
        ArrayList arraylist = new ArrayList();
        for(String s = bufferedreader.readLine(); s != null; s = bufferedreader.readLine())
            arraylist.add(s);

        return arraylist;
    }

    public static long skip(InputStream inputstream, long l)
        throws IOException
    {
        if(l < 0L)
            throw new IllegalArgumentException((new StringBuilder()).append("Skip count must be non-negative, actual: ").append(l).toString());
        if(SKIP_BYTE_BUFFER == null)
            SKIP_BYTE_BUFFER = new byte[2048];
        long l1 = l;
        do
        {
            long l2;
label0:
            {
                if(l1 > 0L)
                {
                    l2 = inputstream.read(SKIP_BYTE_BUFFER, 0, (int)Math.min(l1, 2048L));
                    if(l2 >= 0L)
                        break label0;
                }
                return l - l1;
            }
            l1 -= l2;
        } while(true);
    }

    public static long skip(Reader reader, long l)
        throws IOException
    {
        if(l < 0L)
            throw new IllegalArgumentException((new StringBuilder()).append("Skip count must be non-negative, actual: ").append(l).toString());
        if(SKIP_CHAR_BUFFER == null)
            SKIP_CHAR_BUFFER = new char[2048];
        long l1 = l;
        do
        {
            long l2;
label0:
            {
                if(l1 > 0L)
                {
                    l2 = reader.read(SKIP_CHAR_BUFFER, 0, (int)Math.min(l1, 2048L));
                    if(l2 >= 0L)
                        break label0;
                }
                return l - l1;
            }
            l1 -= l2;
        } while(true);
    }

    public static void skipFully(InputStream inputstream, long l)
        throws IOException
    {
        if(l < 0L)
            throw new IllegalArgumentException((new StringBuilder()).append("Bytes to skip must not be negative: ").append(l).toString());
        long l1 = skip(inputstream, l);
        if(l1 != l)
            throw new EOFException((new StringBuilder()).append("Bytes to skip: ").append(l).append(" actual: ").append(l1).toString());
        else
            return;
    }

    public static void skipFully(Reader reader, long l)
        throws IOException
    {
        long l1 = skip(reader, l);
        if(l1 != l)
            throw new EOFException((new StringBuilder()).append("Bytes to skip: ").append(l).append(" actual: ").append(l1).toString());
        else
            return;
    }

    public static InputStream toBufferedInputStream(InputStream inputstream)
        throws IOException
    {
        return ByteArrayOutputStream.toBufferedInputStream(inputstream);
    }

    public static byte[] toByteArray(InputStream inputstream)
        throws IOException
    {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        copy(inputstream, bytearrayoutputstream);
        return bytearrayoutputstream.toByteArray();
    }

    public static byte[] toByteArray(Reader reader)
        throws IOException
    {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        copy(reader, bytearrayoutputstream);
        return bytearrayoutputstream.toByteArray();
    }

    public static byte[] toByteArray(Reader reader, String s)
        throws IOException
    {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        copy(reader, bytearrayoutputstream, s);
        return bytearrayoutputstream.toByteArray();
    }

    public static byte[] toByteArray(String s)
        throws IOException
    {
        return s.getBytes();
    }

    public static char[] toCharArray(InputStream inputstream)
        throws IOException
    {
        CharArrayWriter chararraywriter = new CharArrayWriter();
        copy(inputstream, chararraywriter);
        return chararraywriter.toCharArray();
    }

    public static char[] toCharArray(InputStream inputstream, String s)
        throws IOException
    {
        CharArrayWriter chararraywriter = new CharArrayWriter();
        copy(inputstream, chararraywriter, s);
        return chararraywriter.toCharArray();
    }

    public static char[] toCharArray(Reader reader)
        throws IOException
    {
        CharArrayWriter chararraywriter = new CharArrayWriter();
        copy(reader, chararraywriter);
        return chararraywriter.toCharArray();
    }

    public static InputStream toInputStream(CharSequence charsequence)
    {
        return toInputStream(charsequence.toString());
    }

    public static InputStream toInputStream(CharSequence charsequence, String s)
        throws IOException
    {
        return toInputStream(charsequence.toString(), s);
    }

    public static InputStream toInputStream(String s)
    {
        return new ByteArrayInputStream(s.getBytes());
    }

    public static InputStream toInputStream(String s, String s1)
        throws IOException
    {
        byte abyte0[];
        if(s1 != null)
            abyte0 = s.getBytes(s1);
        else
            abyte0 = s.getBytes();
        return new ByteArrayInputStream(abyte0);
    }

    public static String toString(InputStream inputstream)
        throws IOException
    {
        StringBuilderWriter stringbuilderwriter = new StringBuilderWriter();
        copy(inputstream, stringbuilderwriter);
        return stringbuilderwriter.toString();
    }

    public static String toString(InputStream inputstream, String s)
        throws IOException
    {
        StringBuilderWriter stringbuilderwriter = new StringBuilderWriter();
        copy(inputstream, stringbuilderwriter, s);
        return stringbuilderwriter.toString();
    }

    public static String toString(Reader reader)
        throws IOException
    {
        StringBuilderWriter stringbuilderwriter = new StringBuilderWriter();
        copy(reader, stringbuilderwriter);
        return stringbuilderwriter.toString();
    }

    public static String toString(byte abyte0[])
        throws IOException
    {
        return new String(abyte0);
    }

    public static String toString(byte abyte0[], String s)
        throws IOException
    {
        if(s == null)
            return new String(abyte0);
        else
            return new String(abyte0, s);
    }

    public static void write(CharSequence charsequence, OutputStream outputstream)
        throws IOException
    {
        if(charsequence != null)
            write(charsequence.toString(), outputstream);
    }

    public static void write(CharSequence charsequence, OutputStream outputstream, String s)
        throws IOException
    {
        if(charsequence != null)
            write(charsequence.toString(), outputstream, s);
    }

    public static void write(CharSequence charsequence, Writer writer)
        throws IOException
    {
        if(charsequence != null)
            write(charsequence.toString(), writer);
    }

    public static void write(String s, OutputStream outputstream)
        throws IOException
    {
        if(s != null)
            outputstream.write(s.getBytes());
    }

    public static void write(String s, OutputStream outputstream, String s1)
        throws IOException
    {
label0:
        {
            if(s != null)
            {
                if(s1 != null)
                    break label0;
                write(s, outputstream);
            }
            return;
        }
        outputstream.write(s.getBytes(s1));
    }

    public static void write(String s, Writer writer)
        throws IOException
    {
        if(s != null)
            writer.write(s);
    }

    public static void write(StringBuffer stringbuffer, OutputStream outputstream)
        throws IOException
    {
        if(stringbuffer != null)
            outputstream.write(stringbuffer.toString().getBytes());
    }

    public static void write(StringBuffer stringbuffer, OutputStream outputstream, String s)
        throws IOException
    {
label0:
        {
            if(stringbuffer != null)
            {
                if(s != null)
                    break label0;
                write(stringbuffer, outputstream);
            }
            return;
        }
        outputstream.write(stringbuffer.toString().getBytes(s));
    }

    public static void write(StringBuffer stringbuffer, Writer writer)
        throws IOException
    {
        if(stringbuffer != null)
            writer.write(stringbuffer.toString());
    }

    public static void write(byte abyte0[], OutputStream outputstream)
        throws IOException
    {
        if(abyte0 != null)
            outputstream.write(abyte0);
    }

    public static void write(byte abyte0[], Writer writer)
        throws IOException
    {
        if(abyte0 != null)
            writer.write(new String(abyte0));
    }

    public static void write(byte abyte0[], Writer writer, String s)
        throws IOException
    {
label0:
        {
            if(abyte0 != null)
            {
                if(s != null)
                    break label0;
                write(abyte0, writer);
            }
            return;
        }
        writer.write(new String(abyte0, s));
    }

    public static void write(char ac[], OutputStream outputstream)
        throws IOException
    {
        if(ac != null)
            outputstream.write((new String(ac)).getBytes());
    }

    public static void write(char ac[], OutputStream outputstream, String s)
        throws IOException
    {
label0:
        {
            if(ac != null)
            {
                if(s != null)
                    break label0;
                write(ac, outputstream);
            }
            return;
        }
        outputstream.write((new String(ac)).getBytes(s));
    }

    public static void write(char ac[], Writer writer)
        throws IOException
    {
        if(ac != null)
            writer.write(ac);
    }

    public static void writeLines(Collection collection, String s, OutputStream outputstream)
        throws IOException
    {
        if(collection != null)
        {
            if(s == null)
                s = LINE_SEPARATOR;
            Iterator iterator = collection.iterator();
            while(iterator.hasNext()) 
            {
                Object obj = iterator.next();
                if(obj != null)
                    outputstream.write(obj.toString().getBytes());
                outputstream.write(s.getBytes());
            }
        }
    }

    public static void writeLines(Collection collection, String s, OutputStream outputstream, String s1)
        throws IOException
    {
        if(s1 != null) goto _L2; else goto _L1
_L1:
        writeLines(collection, s, outputstream);
_L4:
        return;
_L2:
        if(collection != null)
        {
            if(s == null)
                s = LINE_SEPARATOR;
            Iterator iterator = collection.iterator();
            while(iterator.hasNext()) 
            {
                Object obj = iterator.next();
                if(obj != null)
                    outputstream.write(obj.toString().getBytes(s1));
                outputstream.write(s.getBytes(s1));
            }
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    public static void writeLines(Collection collection, String s, Writer writer)
        throws IOException
    {
        if(collection != null)
        {
            if(s == null)
                s = LINE_SEPARATOR;
            Iterator iterator = collection.iterator();
            while(iterator.hasNext()) 
            {
                Object obj = iterator.next();
                if(obj != null)
                    writer.write(obj.toString());
                writer.write(s);
            }
        }
    }

    private static final int DEFAULT_BUFFER_SIZE = 4096;
    public static final char DIR_SEPARATOR = 0;
    public static final char DIR_SEPARATOR_UNIX = 47;
    public static final char DIR_SEPARATOR_WINDOWS = 92;
    public static final String LINE_SEPARATOR;
    public static final String LINE_SEPARATOR_UNIX = "\n";
    public static final String LINE_SEPARATOR_WINDOWS = "\r\n";
    private static final int SKIP_BUFFER_SIZE = 2048;
    private static byte SKIP_BYTE_BUFFER[];
    private static char SKIP_CHAR_BUFFER[];

    static 
    {
        DIR_SEPARATOR = File.separatorChar;
        StringBuilderWriter stringbuilderwriter = new StringBuilderWriter(4);
        PrintWriter printwriter = new PrintWriter(stringbuilderwriter);
        printwriter.println();
        LINE_SEPARATOR = stringbuilderwriter.toString();
        printwriter.close();
    }
}
