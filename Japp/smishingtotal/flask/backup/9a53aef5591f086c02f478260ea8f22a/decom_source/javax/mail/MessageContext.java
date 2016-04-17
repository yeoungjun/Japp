// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail;


// Referenced classes of package javax.mail:
//            MessagingException, Message, BodyPart, Multipart, 
//            Part, Session

public class MessageContext
{

    public MessageContext(Part part1)
    {
        part = part1;
    }

    private static Message getMessage(Part part1)
        throws MessagingException
    {
        do
        {
            if(part1 == null)
                return null;
            if(part1 instanceof Message)
                return (Message)part1;
            Multipart multipart = ((BodyPart)part1).getParent();
            if(multipart == null)
                return null;
            part1 = multipart.getParent();
        } while(true);
    }

    public Message getMessage()
    {
        Message message;
        try
        {
            message = getMessage(part);
        }
        catch(MessagingException messagingexception)
        {
            return null;
        }
        return message;
    }

    public Part getPart()
    {
        return part;
    }

    public Session getSession()
    {
        Message message = getMessage();
        if(message != null)
            return message.session;
        else
            return null;
    }

    private Part part;
}
