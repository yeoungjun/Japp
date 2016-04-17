// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.activation;


// Referenced classes of package javax.activation:
//            MailcapCommandMap, DataContentHandler, DataSource, CommandInfo

public abstract class CommandMap
{

    public CommandMap()
    {
    }

    public static CommandMap getDefaultCommandMap()
    {
        if(defaultCommandMap == null)
            defaultCommandMap = new MailcapCommandMap();
        return defaultCommandMap;
    }

    public static void setDefaultCommandMap(CommandMap commandmap)
    {
        SecurityManager securitymanager = System.getSecurityManager();
        if(securitymanager != null)
            try
            {
                securitymanager.checkSetFactory();
            }
            catch(SecurityException securityexception)
            {
                if(javax/activation/CommandMap.getClassLoader() != commandmap.getClass().getClassLoader())
                    throw securityexception;
            }
        defaultCommandMap = commandmap;
    }

    public abstract DataContentHandler createDataContentHandler(String s);

    public DataContentHandler createDataContentHandler(String s, DataSource datasource)
    {
        return createDataContentHandler(s);
    }

    public abstract CommandInfo[] getAllCommands(String s);

    public CommandInfo[] getAllCommands(String s, DataSource datasource)
    {
        return getAllCommands(s);
    }

    public abstract CommandInfo getCommand(String s, String s1);

    public CommandInfo getCommand(String s, String s1, DataSource datasource)
    {
        return getCommand(s, s1);
    }

    public String[] getMimeTypes()
    {
        return null;
    }

    public abstract CommandInfo[] getPreferredCommands(String s);

    public CommandInfo[] getPreferredCommands(String s, DataSource datasource)
    {
        return getPreferredCommands(s);
    }

    private static CommandMap defaultCommandMap = null;

}
