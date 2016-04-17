// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.activation;

import java.beans.Beans;
import java.io.*;

// Referenced classes of package javax.activation:
//            CommandObject, DataHandler

public class CommandInfo
{

    public CommandInfo(String s, String s1)
    {
        verb = s;
        className = s1;
    }

    public String getCommandClass()
    {
        return className;
    }

    public String getCommandName()
    {
        return verb;
    }

    public Object getCommandObject(DataHandler datahandler, ClassLoader classloader)
        throws IOException, ClassNotFoundException
    {
        Object obj = Beans.instantiate(classloader, className);
        if(obj != null)
            if(obj instanceof CommandObject)
                ((CommandObject)obj).setCommandContext(verb, datahandler);
            else
            if((obj instanceof Externalizable) && datahandler != null)
            {
                java.io.InputStream inputstream = datahandler.getInputStream();
                if(inputstream != null)
                {
                    ((Externalizable)obj).readExternal(new ObjectInputStream(inputstream));
                    return obj;
                }
            }
        return obj;
    }

    private String className;
    private String verb;
}
