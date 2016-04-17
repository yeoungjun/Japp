// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.lang3;

import java.io.*;

// Referenced classes of package org.apache.commons.lang3:
//            SerializationException

public class SerializationUtils
{
    static class ClassLoaderAwareObjectInputStream extends ObjectInputStream
    {

        protected Class resolveClass(ObjectStreamClass objectstreamclass)
            throws IOException, ClassNotFoundException
        {
            String s = objectstreamclass.getName();
            Class class1;
            try
            {
                class1 = Class.forName(s, false, classLoader);
            }
            catch(ClassNotFoundException classnotfoundexception)
            {
                return Class.forName(s, false, Thread.currentThread().getContextClassLoader());
            }
            return class1;
        }

        private ClassLoader classLoader;

        public ClassLoaderAwareObjectInputStream(InputStream inputstream, ClassLoader classloader)
            throws IOException
        {
            super(inputstream);
            classLoader = classloader;
        }
    }


    public SerializationUtils()
    {
    }

    public static Serializable clone(Serializable serializable)
    {
        if(serializable != null) goto _L2; else goto _L1
_L1:
        Serializable serializable1 = null;
_L4:
        return serializable1;
_L2:
        ByteArrayInputStream bytearrayinputstream;
        ClassLoaderAwareObjectInputStream classloaderawareobjectinputstream;
        bytearrayinputstream = new ByteArrayInputStream(serialize(serializable));
        classloaderawareobjectinputstream = null;
        ClassLoaderAwareObjectInputStream classloaderawareobjectinputstream1 = new ClassLoaderAwareObjectInputStream(bytearrayinputstream, serializable.getClass().getClassLoader());
        serializable1 = (Serializable)classloaderawareobjectinputstream1.readObject();
        if(classloaderawareobjectinputstream1 != null)
        {
            try
            {
                classloaderawareobjectinputstream1.close();
            }
            catch(IOException ioexception2)
            {
                throw new SerializationException("IOException on closing cloned object data InputStream.", ioexception2);
            }
            return serializable1;
        }
        if(true) goto _L4; else goto _L3
_L3:
        ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
_L8:
        throw new SerializationException("ClassNotFoundException while reading cloned object data", classnotfoundexception);
        Exception exception;
        exception;
_L5:
        IOException ioexception1;
        if(classloaderawareobjectinputstream != null)
            try
            {
                classloaderawareobjectinputstream.close();
            }
            catch(IOException ioexception)
            {
                throw new SerializationException("IOException on closing cloned object data InputStream.", ioexception);
            }
        throw exception;
        ioexception1;
_L6:
        throw new SerializationException("IOException while reading cloned object data", ioexception1);
        exception;
        classloaderawareobjectinputstream = classloaderawareobjectinputstream1;
          goto _L5
        ioexception1;
        classloaderawareobjectinputstream = classloaderawareobjectinputstream1;
          goto _L6
        classnotfoundexception;
        classloaderawareobjectinputstream = classloaderawareobjectinputstream1;
        if(true) goto _L8; else goto _L7
_L7:
    }

    public static Object deserialize(InputStream inputstream)
    {
        ObjectInputStream objectinputstream;
        if(inputstream == null)
            throw new IllegalArgumentException("The InputStream must not be null");
        objectinputstream = null;
        ObjectInputStream objectinputstream1 = new ObjectInputStream(inputstream);
        Object obj = objectinputstream1.readObject();
        ClassNotFoundException classnotfoundexception;
        Exception exception;
        IOException ioexception1;
        if(objectinputstream1 != null)
            try
            {
                objectinputstream1.close();
            }
            catch(IOException ioexception2)
            {
                return obj;
            }
        return obj;
        classnotfoundexception;
_L4:
        throw new SerializationException(classnotfoundexception);
        exception;
_L1:
        if(objectinputstream != null)
            try
            {
                objectinputstream.close();
            }
            catch(IOException ioexception) { }
        throw exception;
        ioexception1;
_L2:
        throw new SerializationException(ioexception1);
        exception;
        objectinputstream = objectinputstream1;
          goto _L1
        ioexception1;
        objectinputstream = objectinputstream1;
          goto _L2
        classnotfoundexception;
        objectinputstream = objectinputstream1;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public static Object deserialize(byte abyte0[])
    {
        if(abyte0 == null)
            throw new IllegalArgumentException("The byte[] must not be null");
        else
            return deserialize(((InputStream) (new ByteArrayInputStream(abyte0))));
    }

    public static void serialize(Serializable serializable, OutputStream outputstream)
    {
        ObjectOutputStream objectoutputstream;
        if(outputstream == null)
            throw new IllegalArgumentException("The OutputStream must not be null");
        objectoutputstream = null;
        ObjectOutputStream objectoutputstream1 = new ObjectOutputStream(outputstream);
        objectoutputstream1.writeObject(serializable);
        if(objectoutputstream1 == null)
            break MISSING_BLOCK_LABEL_38;
        objectoutputstream1.close();
        return;
        IOException ioexception;
        ioexception;
_L4:
        throw new SerializationException(ioexception);
        Exception exception;
        exception;
_L2:
        IOException ioexception2;
        if(objectoutputstream != null)
            try
            {
                objectoutputstream.close();
            }
            catch(IOException ioexception1) { }
        throw exception;
        ioexception2;
        return;
        exception;
        objectoutputstream = objectoutputstream1;
        if(true) goto _L2; else goto _L1
_L1:
        ioexception;
        objectoutputstream = objectoutputstream1;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public static byte[] serialize(Serializable serializable)
    {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream(512);
        serialize(serializable, ((OutputStream) (bytearrayoutputstream)));
        return bytearrayoutputstream.toByteArray();
    }
}
