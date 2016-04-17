// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.activation;

import java.io.*;
import java.net.URL;
import myjava.awt.datatransfer.*;

// Referenced classes of package javax.activation:
//            URLDataSource, MimeTypeParseException, MimeType, CommandMap, 
//            DataContentHandlerFactory, DataSourceDataContentHandler, ObjectDataContentHandler, SecuritySupport, 
//            CommandInfo, DataContentHandler, DataSource, DataHandlerDataSource, 
//            UnsupportedDataTypeException

public class DataHandler
    implements Transferable
{

    public DataHandler(Object obj, String s)
    {
        dataSource = null;
        objDataSource = null;
        object = null;
        objectMimeType = null;
        currentCommandMap = null;
        transferFlavors = emptyFlavors;
        dataContentHandler = null;
        factoryDCH = null;
        oldFactory = null;
        shortType = null;
        object = obj;
        objectMimeType = s;
        oldFactory = factory;
    }

    public DataHandler(URL url)
    {
        dataSource = null;
        objDataSource = null;
        object = null;
        objectMimeType = null;
        currentCommandMap = null;
        transferFlavors = emptyFlavors;
        dataContentHandler = null;
        factoryDCH = null;
        oldFactory = null;
        shortType = null;
        dataSource = new URLDataSource(url);
        oldFactory = factory;
    }

    public DataHandler(DataSource datasource)
    {
        dataSource = null;
        objDataSource = null;
        object = null;
        objectMimeType = null;
        currentCommandMap = null;
        transferFlavors = emptyFlavors;
        dataContentHandler = null;
        factoryDCH = null;
        oldFactory = null;
        shortType = null;
        dataSource = datasource;
        oldFactory = factory;
    }

    private String getBaseType()
    {
        this;
        JVM INSTR monitorenter ;
        String s1;
        if(shortType != null)
            break MISSING_BLOCK_LABEL_29;
        s1 = getContentType();
        shortType = (new MimeType(s1)).getBaseType();
_L1:
        String s = shortType;
        this;
        JVM INSTR monitorexit ;
        return s;
        MimeTypeParseException mimetypeparseexception;
        mimetypeparseexception;
        shortType = s1;
          goto _L1
        Exception exception;
        exception;
        throw exception;
    }

    private CommandMap getCommandMap()
    {
        this;
        JVM INSTR monitorenter ;
        if(currentCommandMap == null) goto _L2; else goto _L1
_L1:
        CommandMap commandmap1 = currentCommandMap;
_L4:
        this;
        JVM INSTR monitorexit ;
        return commandmap1;
_L2:
        CommandMap commandmap = CommandMap.getDefaultCommandMap();
        commandmap1 = commandmap;
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
        throw exception;
    }

    private DataContentHandler getDataContentHandler()
    {
        this;
        JVM INSTR monitorenter ;
        if(factory != oldFactory)
        {
            oldFactory = factory;
            factoryDCH = null;
            dataContentHandler = null;
            transferFlavors = emptyFlavors;
        }
        if(dataContentHandler == null) goto _L2; else goto _L1
_L1:
        DataContentHandler datacontenthandler = dataContentHandler;
_L7:
        this;
        JVM INSTR monitorexit ;
        return datacontenthandler;
_L2:
        String s;
        s = getBaseType();
        if(factoryDCH == null && factory != null)
            factoryDCH = factory.createDataContentHandler(s);
        if(factoryDCH != null)
            dataContentHandler = factoryDCH;
        if(dataContentHandler != null) goto _L4; else goto _L3
_L3:
        if(dataSource == null) goto _L6; else goto _L5
_L5:
        dataContentHandler = getCommandMap().createDataContentHandler(s, dataSource);
_L4:
        if(dataSource == null)
            break MISSING_BLOCK_LABEL_182;
        dataContentHandler = new DataSourceDataContentHandler(dataContentHandler, dataSource);
_L8:
        datacontenthandler = dataContentHandler;
          goto _L7
_L6:
        dataContentHandler = getCommandMap().createDataContentHandler(s);
          goto _L4
        Exception exception;
        exception;
        throw exception;
        dataContentHandler = new ObjectDataContentHandler(dataContentHandler, object, objectMimeType);
          goto _L8
    }

    public static void setDataContentHandlerFactory(DataContentHandlerFactory datacontenthandlerfactory)
    {
        javax/activation/DataHandler;
        JVM INSTR monitorenter ;
        if(factory != null)
            throw new Error("DataContentHandlerFactory already defined");
        break MISSING_BLOCK_LABEL_25;
        Exception exception;
        exception;
        javax/activation/DataHandler;
        JVM INSTR monitorexit ;
        throw exception;
        SecurityManager securitymanager = System.getSecurityManager();
        if(securitymanager == null)
            break MISSING_BLOCK_LABEL_37;
        securitymanager.checkSetFactory();
_L2:
        factory = datacontenthandlerfactory;
        javax/activation/DataHandler;
        JVM INSTR monitorexit ;
        return;
        SecurityException securityexception;
        securityexception;
        if(javax/activation/DataHandler.getClassLoader() == datacontenthandlerfactory.getClass().getClassLoader()) goto _L2; else goto _L1
_L1:
        throw securityexception;
    }

    public CommandInfo[] getAllCommands()
    {
        if(dataSource != null)
            return getCommandMap().getAllCommands(getBaseType(), dataSource);
        else
            return getCommandMap().getAllCommands(getBaseType());
    }

    public Object getBean(CommandInfo commandinfo)
    {
        ClassLoader classloader;
        Object obj;
        try
        {
            classloader = SecuritySupport.getContextClassLoader();
        }
        catch(IOException ioexception)
        {
            return null;
        }
        catch(ClassNotFoundException classnotfoundexception)
        {
            return null;
        }
        if(classloader != null)
            break MISSING_BLOCK_LABEL_19;
        classloader = getClass().getClassLoader();
        obj = commandinfo.getCommandObject(this, classloader);
        return obj;
    }

    public CommandInfo getCommand(String s)
    {
        if(dataSource != null)
            return getCommandMap().getCommand(getBaseType(), s, dataSource);
        else
            return getCommandMap().getCommand(getBaseType(), s);
    }

    public Object getContent()
        throws IOException
    {
        if(object != null)
            return object;
        else
            return getDataContentHandler().getContent(getDataSource());
    }

    public String getContentType()
    {
        if(dataSource != null)
            return dataSource.getContentType();
        else
            return objectMimeType;
    }

    public DataSource getDataSource()
    {
        if(dataSource == null)
        {
            if(objDataSource == null)
                objDataSource = new DataHandlerDataSource(this);
            return objDataSource;
        } else
        {
            return dataSource;
        }
    }

    public InputStream getInputStream()
        throws IOException
    {
        if(dataSource != null)
            return dataSource.getInputStream();
        final DataContentHandler fdch = getDataContentHandler();
        if(fdch == null)
            throw new UnsupportedDataTypeException((new StringBuilder("no DCH for MIME type ")).append(getBaseType()).toString());
        if((fdch instanceof ObjectDataContentHandler) && ((ObjectDataContentHandler)fdch).getDCH() == null)
        {
            throw new UnsupportedDataTypeException((new StringBuilder("no object DCH for MIME type ")).append(getBaseType()).toString());
        } else
        {
            final PipedOutputStream pos = new PipedOutputStream();
            PipedInputStream pipedinputstream = new PipedInputStream(pos);
            (new Thread(new Runnable() {

                public void run()
                {
                    Exception exception;
                    try
                    {
                        fdch.writeTo(object, objectMimeType, pos);
                    }
                    catch(IOException ioexception1)
                    {
                        try
                        {
                            pos.close();
                            return;
                        }
                        catch(IOException ioexception2)
                        {
                            return;
                        }
                    }
                    finally { }
                    try
                    {
                        pos.close();
                        return;
                    }
                    catch(IOException ioexception3)
                    {
                        return;
                    }
                    try
                    {
                        pos.close();
                    }
                    catch(IOException ioexception) { }
                    throw exception;
                }

                final DataHandler this$0;
                private final DataContentHandler val$fdch;
                private final PipedOutputStream val$pos;

            
            {
                this$0 = DataHandler.this;
                pos = pipedoutputstream;
                fdch = datacontenthandler;
                super();
            }
            }, "DataHandler.getInputStream")).start();
            return pipedinputstream;
        }
    }

    public String getName()
    {
        if(dataSource != null)
            return dataSource.getName();
        else
            return null;
    }

    public OutputStream getOutputStream()
        throws IOException
    {
        if(dataSource != null)
            return dataSource.getOutputStream();
        else
            return null;
    }

    public CommandInfo[] getPreferredCommands()
    {
        if(dataSource != null)
            return getCommandMap().getPreferredCommands(getBaseType(), dataSource);
        else
            return getCommandMap().getPreferredCommands(getBaseType());
    }

    public Object getTransferData(DataFlavor dataflavor)
        throws UnsupportedFlavorException, IOException
    {
        return getDataContentHandler().getTransferData(dataflavor, dataSource);
    }

    public DataFlavor[] getTransferDataFlavors()
    {
        this;
        JVM INSTR monitorenter ;
        DataFlavor adataflavor[];
        if(factory != oldFactory)
            transferFlavors = emptyFlavors;
        if(transferFlavors == emptyFlavors)
            transferFlavors = getDataContentHandler().getTransferDataFlavors();
        adataflavor = transferFlavors;
        this;
        JVM INSTR monitorexit ;
        return adataflavor;
        Exception exception;
        exception;
        throw exception;
    }

    public boolean isDataFlavorSupported(DataFlavor dataflavor)
    {
        DataFlavor adataflavor[] = getTransferDataFlavors();
        int i = 0;
        do
        {
            if(i >= adataflavor.length)
                return false;
            if(adataflavor[i].equals(dataflavor))
                return true;
            i++;
        } while(true);
    }

    public void setCommandMap(CommandMap commandmap)
    {
        this;
        JVM INSTR monitorenter ;
        if(commandmap == currentCommandMap && commandmap != null)
            break MISSING_BLOCK_LABEL_31;
        transferFlavors = emptyFlavors;
        dataContentHandler = null;
        currentCommandMap = commandmap;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void writeTo(OutputStream outputstream)
        throws IOException
    {
        byte abyte0[];
        InputStream inputstream;
        if(dataSource == null)
            break MISSING_BLOCK_LABEL_60;
        abyte0 = new byte[8192];
        inputstream = dataSource.getInputStream();
_L1:
        int i = inputstream.read(abyte0);
        if(i <= 0)
        {
            inputstream.close();
            return;
        }
        outputstream.write(abyte0, 0, i);
          goto _L1
        Exception exception;
        exception;
        inputstream.close();
        throw exception;
        getDataContentHandler().writeTo(object, objectMimeType, outputstream);
        return;
    }

    private static final DataFlavor emptyFlavors[] = new DataFlavor[0];
    private static DataContentHandlerFactory factory = null;
    private CommandMap currentCommandMap;
    private DataContentHandler dataContentHandler;
    private DataSource dataSource;
    private DataContentHandler factoryDCH;
    private DataSource objDataSource;
    private Object object;
    private String objectMimeType;
    private DataContentHandlerFactory oldFactory;
    private String shortType;
    private DataFlavor transferFlavors[];



}
