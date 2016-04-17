// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package myjava.awt.datatransfer;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.util.*;
import org.apache.harmony.awt.datatransfer.DTK;
import org.apache.harmony.awt.internal.nls.Messages;

// Referenced classes of package myjava.awt.datatransfer:
//            MimeTypeProcessor, UnsupportedFlavorException, Transferable

public class DataFlavor
    implements Externalizable, Cloneable
{

    public DataFlavor()
    {
        mimeInfo = null;
        humanPresentableName = null;
        representationClass = null;
    }

    public DataFlavor(Class class1, String s)
    {
        mimeInfo = new MimeTypeProcessor.MimeType("application", "x-java-serialized-object");
        if(s != null)
            humanPresentableName = s;
        else
            humanPresentableName = "application/x-java-serialized-object";
        mimeInfo.addParameter("class", class1.getName());
        representationClass = class1;
    }

    public DataFlavor(String s)
        throws ClassNotFoundException
    {
        init(s, null, null);
    }

    public DataFlavor(String s, String s1)
    {
        try
        {
            init(s, s1, null);
            return;
        }
        catch(ClassNotFoundException classnotfoundexception)
        {
            throw new IllegalArgumentException(Messages.getString("awt.16C", mimeInfo.getParameter("class")), classnotfoundexception);
        }
    }

    public DataFlavor(String s, String s1, ClassLoader classloader)
        throws ClassNotFoundException
    {
        init(s, s1, classloader);
    }

    private static List fetchTextFlavors(List list, String s)
    {
        LinkedList linkedlist = new LinkedList();
        Iterator iterator = list.iterator();
        do
        {
            if(!iterator.hasNext())
            {
                if(linkedlist.isEmpty())
                    linkedlist = null;
                return linkedlist;
            }
            DataFlavor dataflavor = (DataFlavor)iterator.next();
            if(dataflavor.isFlavorTextType())
            {
                if(dataflavor.mimeInfo.getFullType().equals(s))
                {
                    if(!linkedlist.contains(dataflavor))
                        linkedlist.add(dataflavor);
                    iterator.remove();
                }
            } else
            {
                iterator.remove();
            }
        } while(true);
    }

    private String getCharset()
    {
        String s;
        if(mimeInfo == null || isCharsetRedundant())
        {
            s = "";
        } else
        {
            s = mimeInfo.getParameter("charset");
            if(isCharsetRequired() && (s == null || s.length() == 0))
                return DTK.getDTK().getDefaultCharset();
            if(s == null)
                return "";
        }
        return s;
    }

    private static List getFlavors(List list, Class class1)
    {
        LinkedList linkedlist = new LinkedList();
        Iterator iterator = list.iterator();
        do
        {
            DataFlavor dataflavor;
            do
            {
                if(!iterator.hasNext())
                {
                    if(linkedlist.isEmpty())
                        list = null;
                    return list;
                }
                dataflavor = (DataFlavor)iterator.next();
            } while(!dataflavor.representationClass.equals(class1));
            linkedlist.add(dataflavor);
        } while(true);
    }

    private static List getFlavors(List list, String as[])
    {
        LinkedList linkedlist = new LinkedList();
        Iterator iterator = list.iterator();
        do
        {
            if(!iterator.hasNext())
            {
                if(linkedlist.isEmpty())
                    list = null;
                return list;
            }
            DataFlavor dataflavor = (DataFlavor)iterator.next();
            if(isCharsetSupported(dataflavor.getCharset()))
            {
                int i = as.length;
                int j = 0;
                while(j < i) 
                {
                    if(Charset.forName(as[j]).equals(Charset.forName(dataflavor.getCharset())))
                        linkedlist.add(dataflavor);
                    j++;
                }
            } else
            {
                iterator.remove();
            }
        } while(true);
    }

    private String getKeyInfo()
    {
        String s = (new StringBuilder(String.valueOf(mimeInfo.getFullType()))).append(";class=").append(representationClass.getName()).toString();
        if(!mimeInfo.getPrimaryType().equals("text") || isUnicodeFlavor())
            return s;
        else
            return (new StringBuilder(String.valueOf(s))).append(";charset=").append(getCharset().toLowerCase()).toString();
    }

    public static final DataFlavor getTextPlainUnicodeFlavor()
    {
        if(plainUnicodeFlavor == null)
            plainUnicodeFlavor = new DataFlavor((new StringBuilder("text/plain; charset=")).append(DTK.getDTK().getDefaultCharset()).append("; class=java.io.InputStream").toString(), "Plain Text");
        return plainUnicodeFlavor;
    }

    private void init(String s, String s1, ClassLoader classloader)
        throws ClassNotFoundException
    {
        String s2;
        Class class1;
        try
        {
            mimeInfo = MimeTypeProcessor.parse(s);
        }
        catch(IllegalArgumentException illegalargumentexception)
        {
            throw new IllegalArgumentException(Messages.getString("awt.16D", s));
        }
        if(s1 != null)
            humanPresentableName = s1;
        else
            humanPresentableName = (new StringBuilder(String.valueOf(mimeInfo.getPrimaryType()))).append('/').append(mimeInfo.getSubType()).toString();
        s2 = mimeInfo.getParameter("class");
        if(s2 == null)
        {
            s2 = "java.io.InputStream";
            mimeInfo.addParameter("class", s2);
        }
        if(classloader == null)
            class1 = Class.forName(s2);
        else
            class1 = classloader.loadClass(s2);
        representationClass = class1;
    }

    private boolean isByteCodeFlavor()
    {
        return representationClass != null && (representationClass.equals(java/io/InputStream) || representationClass.equals(java/nio/ByteBuffer) || representationClass.equals([B));
    }

    private boolean isCharsetRedundant()
    {
        String s = mimeInfo.getFullType();
        return s.equals("text/rtf") || s.equals("text/tab-separated-values") || s.equals("text/t140") || s.equals("text/rfc822-headers") || s.equals("text/parityfec");
    }

    private boolean isCharsetRequired()
    {
        String s = mimeInfo.getFullType();
        return s.equals("text/sgml") || s.equals("text/xml") || s.equals("text/html") || s.equals("text/enriched") || s.equals("text/richtext") || s.equals("text/uri-list") || s.equals("text/directory") || s.equals("text/css") || s.equals("text/calendar") || s.equals("application/x-java-serialized-object") || s.equals("text/plain");
    }

    private static boolean isCharsetSupported(String s)
    {
        boolean flag;
        try
        {
            flag = Charset.isSupported(s);
        }
        catch(IllegalCharsetNameException illegalcharsetnameexception)
        {
            return false;
        }
        return flag;
    }

    private boolean isUnicodeFlavor()
    {
        return representationClass != null && (representationClass.equals(java/io/Reader) || representationClass.equals(java/lang/String) || representationClass.equals(java/nio/CharBuffer) || representationClass.equals([C));
    }

    private static List selectBestByAlphabet(List list)
    {
        String as[];
        LinkedList linkedlist;
        int i;
        as = new String[list.size()];
        linkedlist = new LinkedList();
        i = 0;
_L3:
        if(i < as.length) goto _L2; else goto _L1
_L1:
        Iterator iterator;
        Arrays.sort(as, String.CASE_INSENSITIVE_ORDER);
        iterator = list.iterator();
_L4:
        if(!iterator.hasNext())
        {
            if(linkedlist.isEmpty())
                linkedlist = null;
            return linkedlist;
        }
        break MISSING_BLOCK_LABEL_84;
_L2:
        as[i] = ((DataFlavor)list.get(i)).getCharset();
        i++;
          goto _L3
        DataFlavor dataflavor = (DataFlavor)iterator.next();
        if(as[0].equalsIgnoreCase(dataflavor.getCharset()))
            linkedlist.add(dataflavor);
          goto _L4
    }

    private static DataFlavor selectBestByCharset(List list)
    {
        List list1 = getFlavors(list, new String[] {
            "UTF-16", "UTF-8", "UTF-16BE", "UTF-16LE"
        });
        if(list1 == null)
        {
            String as[] = new String[1];
            as[0] = DTK.getDTK().getDefaultCharset();
            list1 = getFlavors(list, as);
            if(list1 == null)
            {
                list1 = getFlavors(list, new String[] {
                    "US-ASCII"
                });
                if(list1 == null)
                    list1 = selectBestByAlphabet(list);
            }
        }
        if(list1 != null)
        {
            if(list1.size() == 1)
                return (DataFlavor)list1.get(0);
            else
                return selectBestFlavorWOCharset(list1);
        } else
        {
            return null;
        }
    }

    private static DataFlavor selectBestFlavorWCharset(List list)
    {
        List list1 = getFlavors(list, java/io/Reader);
        if(list1 != null)
            return (DataFlavor)list1.get(0);
        List list2 = getFlavors(list, java/lang/String);
        if(list2 != null)
            return (DataFlavor)list2.get(0);
        List list3 = getFlavors(list, java/nio/CharBuffer);
        if(list3 != null)
            return (DataFlavor)list3.get(0);
        List list4 = getFlavors(list, [C);
        if(list4 != null)
            return (DataFlavor)list4.get(0);
        else
            return selectBestByCharset(list);
    }

    private static DataFlavor selectBestFlavorWOCharset(List list)
    {
        List list1 = getFlavors(list, java/io/InputStream);
        if(list1 != null)
            return (DataFlavor)list1.get(0);
        List list2 = getFlavors(list, java/nio/ByteBuffer);
        if(list2 != null)
            return (DataFlavor)list2.get(0);
        List list3 = getFlavors(list, [B);
        if(list3 != null)
            return (DataFlavor)list3.get(0);
        else
            return (DataFlavor)list.get(0);
    }

    public static final DataFlavor selectBestTextFlavor(DataFlavor adataflavor[])
    {
        List list;
        if(adataflavor != null)
            if(!(list = sortTextFlavorsByType(new LinkedList(Arrays.asList(adataflavor)))).isEmpty())
            {
                List list1 = (List)list.get(0);
                if(list1.size() == 1)
                    return (DataFlavor)list1.get(0);
                if(((DataFlavor)list1.get(0)).getCharset().length() == 0)
                    return selectBestFlavorWOCharset(list1);
                else
                    return selectBestFlavorWCharset(list1);
            }
        return null;
    }

    private static List sortTextFlavorsByType(List list)
    {
        LinkedList linkedlist = new LinkedList();
        String as[] = sortedTextFlavors;
        int i = as.length;
        int j = 0;
        do
        {
            if(j >= i)
            {
                if(!list.isEmpty())
                    linkedlist.addLast(list);
                return linkedlist;
            }
            List list1 = fetchTextFlavors(list, as[j]);
            if(list1 != null)
                linkedlist.addLast(list1);
            j++;
        } while(true);
    }

    protected static final Class tryToLoadClass(String s, ClassLoader classloader)
        throws ClassNotFoundException
    {
        Class class3 = Class.forName(s);
        return class3;
        ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        Class class2 = ClassLoader.getSystemClassLoader().loadClass(s);
        return class2;
        ClassNotFoundException classnotfoundexception1;
        classnotfoundexception1;
        ClassLoader classloader1;
        classloader1 = Thread.currentThread().getContextClassLoader();
        if(classloader1 == null)
            break MISSING_BLOCK_LABEL_49;
        Class class1 = classloader1.loadClass(s);
        return class1;
        ClassNotFoundException classnotfoundexception2;
        classnotfoundexception2;
        return classloader.loadClass(s);
    }

    public Object clone()
        throws CloneNotSupportedException
    {
        DataFlavor dataflavor = new DataFlavor();
        dataflavor.humanPresentableName = humanPresentableName;
        dataflavor.representationClass = representationClass;
        MimeTypeProcessor.MimeType mimetype;
        if(mimeInfo != null)
            mimetype = (MimeTypeProcessor.MimeType)mimeInfo.clone();
        else
            mimetype = null;
        dataflavor.mimeInfo = mimetype;
        return dataflavor;
    }

    public boolean equals(Object obj)
    {
        if(obj == null || !(obj instanceof DataFlavor))
            return false;
        else
            return equals((DataFlavor)obj);
    }

    public boolean equals(String s)
    {
        if(s == null)
            return false;
        else
            return isMimeTypeEqual(s);
    }

    public boolean equals(DataFlavor dataflavor)
    {
        if(dataflavor != this) goto _L2; else goto _L1
_L1:
        return true;
_L2:
        if(dataflavor == null)
            return false;
        if(mimeInfo != null)
            break; /* Loop/switch isn't completed */
        if(dataflavor.mimeInfo != null)
            return false;
        if(true) goto _L1; else goto _L3
_L3:
        if(!mimeInfo.equals(dataflavor.mimeInfo) || !representationClass.equals(dataflavor.representationClass))
            return false;
        if(mimeInfo.getPrimaryType().equals("text") && !isUnicodeFlavor())
        {
            String s = getCharset();
            String s1 = dataflavor.getCharset();
            if(!isCharsetSupported(s) || !isCharsetSupported(s1))
                return s.equalsIgnoreCase(s1);
            else
                return Charset.forName(s).equals(Charset.forName(s1));
        }
        if(true) goto _L1; else goto _L4
_L4:
    }

    public final Class getDefaultRepresentationClass()
    {
        return java/io/InputStream;
    }

    public final String getDefaultRepresentationClassAsString()
    {
        return getDefaultRepresentationClass().getName();
    }

    public String getHumanPresentableName()
    {
        return humanPresentableName;
    }

    MimeTypeProcessor.MimeType getMimeInfo()
    {
        return mimeInfo;
    }

    public String getMimeType()
    {
        if(mimeInfo != null)
            return MimeTypeProcessor.assemble(mimeInfo);
        else
            return null;
    }

    public String getParameter(String s)
    {
        String s1 = s.toLowerCase();
        if(s1.equals("humanpresentablename"))
            return humanPresentableName;
        if(mimeInfo != null)
            return mimeInfo.getParameter(s1);
        else
            return null;
    }

    public String getPrimaryType()
    {
        if(mimeInfo != null)
            return mimeInfo.getPrimaryType();
        else
            return null;
    }

    public Reader getReaderForText(Transferable transferable)
        throws UnsupportedFlavorException, IOException
    {
        Object obj = transferable.getTransferData(this);
        if(obj == null)
            throw new IllegalArgumentException(Messages.getString("awt.16E"));
        if(obj instanceof Reader)
        {
            Reader reader = (Reader)obj;
            reader.reset();
            return reader;
        }
        if(obj instanceof String)
            return new StringReader((String)obj);
        if(obj instanceof CharBuffer)
            return new CharArrayReader(((CharBuffer)obj).array());
        if(obj instanceof char[])
            return new CharArrayReader((char[])obj);
        String s = getCharset();
        Object obj1;
        if(obj instanceof InputStream)
        {
            obj1 = (InputStream)obj;
            ((InputStream) (obj1)).reset();
        } else
        if(obj instanceof ByteBuffer)
            obj1 = new ByteArrayInputStream(((ByteBuffer)obj).array());
        else
        if(obj instanceof byte[])
            obj1 = new ByteArrayInputStream((byte[])obj);
        else
            throw new IllegalArgumentException(Messages.getString("awt.16F"));
        if(s.length() == 0)
            return new InputStreamReader(((InputStream) (obj1)));
        else
            return new InputStreamReader(((InputStream) (obj1)), s);
    }

    public Class getRepresentationClass()
    {
        return representationClass;
    }

    public String getSubType()
    {
        if(mimeInfo != null)
            return mimeInfo.getSubType();
        else
            return null;
    }

    public int hashCode()
    {
        return getKeyInfo().hashCode();
    }

    public boolean isFlavorJavaFileListType()
    {
        return java/util/List.isAssignableFrom(representationClass) && isMimeTypeEqual(javaFileListFlavor);
    }

    public boolean isFlavorRemoteObjectType()
    {
        return isMimeTypeEqual("application/x-java-remote-object") && isRepresentationClassRemote();
    }

    public boolean isFlavorSerializedObjectType()
    {
        return isMimeTypeSerializedObject() && isRepresentationClassSerializable();
    }

    public boolean isFlavorTextType()
    {
        if(!equals(stringFlavor) && !equals(plainTextFlavor))
        {
            if(mimeInfo != null && !mimeInfo.getPrimaryType().equals("text"))
                return false;
            String s = getCharset();
            if(isByteCodeFlavor())
            {
                if(s.length() != 0)
                    return isCharsetSupported(s);
            } else
            {
                return isUnicodeFlavor();
            }
        }
        return true;
    }

    public boolean isMimeTypeEqual(String s)
    {
        boolean flag;
        try
        {
            flag = mimeInfo.equals(MimeTypeProcessor.parse(s));
        }
        catch(IllegalArgumentException illegalargumentexception)
        {
            return false;
        }
        return flag;
    }

    public final boolean isMimeTypeEqual(DataFlavor dataflavor)
    {
        if(mimeInfo != null)
            return mimeInfo.equals(dataflavor.mimeInfo);
        return dataflavor.mimeInfo == null;
    }

    public boolean isMimeTypeSerializedObject()
    {
        return isMimeTypeEqual("application/x-java-serialized-object");
    }

    public boolean isRepresentationClassByteBuffer()
    {
        return java/nio/ByteBuffer.isAssignableFrom(representationClass);
    }

    public boolean isRepresentationClassCharBuffer()
    {
        return java/nio/CharBuffer.isAssignableFrom(representationClass);
    }

    public boolean isRepresentationClassInputStream()
    {
        return java/io/InputStream.isAssignableFrom(representationClass);
    }

    public boolean isRepresentationClassReader()
    {
        return java/io/Reader.isAssignableFrom(representationClass);
    }

    public boolean isRepresentationClassRemote()
    {
        return false;
    }

    public boolean isRepresentationClassSerializable()
    {
        return java/io/Serializable.isAssignableFrom(representationClass);
    }

    public boolean match(DataFlavor dataflavor)
    {
        return equals(dataflavor);
    }

    protected String normalizeMimeType(String s)
    {
        return s;
    }

    protected String normalizeMimeTypeParameter(String s, String s1)
    {
        return s1;
    }

    public void readExternal(ObjectInput objectinput)
        throws IOException, ClassNotFoundException
    {
        this;
        JVM INSTR monitorenter ;
        Class class1;
        humanPresentableName = (String)objectinput.readObject();
        mimeInfo = (MimeTypeProcessor.MimeType)objectinput.readObject();
        if(mimeInfo == null)
            break MISSING_BLOCK_LABEL_56;
        class1 = Class.forName(mimeInfo.getParameter("class"));
_L1:
        representationClass = class1;
        this;
        JVM INSTR monitorexit ;
        return;
        class1 = null;
          goto _L1
        Exception exception;
        exception;
        throw exception;
    }

    public void setHumanPresentableName(String s)
    {
        humanPresentableName = s;
    }

    public String toString()
    {
        return (new StringBuilder(String.valueOf(getClass().getName()))).append("[MimeType=(").append(getMimeType()).append(");humanPresentableName=").append(humanPresentableName).append("]").toString();
    }

    public void writeExternal(ObjectOutput objectoutput)
        throws IOException
    {
        this;
        JVM INSTR monitorenter ;
        objectoutput.writeObject(humanPresentableName);
        objectoutput.writeObject(mimeInfo);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public static final DataFlavor javaFileListFlavor = new DataFlavor("application/x-java-file-list; class=java.util.List", "application/x-java-file-list");
    public static final String javaJVMLocalObjectMimeType = "application/x-java-jvm-local-objectref";
    public static final String javaRemoteObjectMimeType = "application/x-java-remote-object";
    public static final String javaSerializedObjectMimeType = "application/x-java-serialized-object";
    public static final DataFlavor plainTextFlavor = new DataFlavor("text/plain; charset=unicode; class=java.io.InputStream", "Plain Text");
    private static DataFlavor plainUnicodeFlavor = null;
    private static final long serialVersionUID = 0x741da5db78a37333L;
    private static final String sortedTextFlavors[] = {
        "text/sgml", "text/xml", "text/html", "text/rtf", "text/enriched", "text/richtext", "text/uri-list", "text/tab-separated-values", "text/t140", "text/rfc822-headers", 
        "text/parityfec", "text/directory", "text/css", "text/calendar", "application/x-java-serialized-object", "text/plain"
    };
    public static final DataFlavor stringFlavor = new DataFlavor("application/x-java-serialized-object; class=java.lang.String", "Unicode String");
    private String humanPresentableName;
    private MimeTypeProcessor.MimeType mimeInfo;
    private Class representationClass;

}
