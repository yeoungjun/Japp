// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail;

import java.util.*;
import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;

// Referenced classes of package javax.mail:
//            Service, MessagingException, Message, SendFailedException, 
//            Address, Session, URLName

public abstract class Transport extends Service
{

    public Transport(Session session, URLName urlname)
    {
        super(session, urlname);
        transportListeners = null;
    }

    public static void send(Message message)
        throws MessagingException
    {
        message.saveChanges();
        send0(message, message.getAllRecipients());
    }

    public static void send(Message message, Address aaddress[])
        throws MessagingException
    {
        message.saveChanges();
        send0(message, aaddress);
    }

    private static void send0(Message message, Address aaddress[])
        throws MessagingException
    {
        Hashtable hashtable;
        Vector vector;
        Vector vector1;
        Vector vector2;
        Session session;
        Transport transport1;
        if(aaddress == null || aaddress.length == 0)
            throw new SendFailedException("No recipient addresses");
        hashtable = new Hashtable();
        vector = new Vector();
        vector1 = new Vector();
        vector2 = new Vector();
        int i = 0;
        int j;
        do
        {
            if(i >= aaddress.length)
            {
                j = hashtable.size();
                if(j == 0)
                    throw new SendFailedException("No recipient addresses");
                break;
            }
            if(hashtable.containsKey(aaddress[i].getType()))
            {
                ((Vector)hashtable.get(aaddress[i].getType())).addElement(aaddress[i]);
            } else
            {
                Vector vector3 = new Vector();
                vector3.addElement(aaddress[i]);
                hashtable.put(aaddress[i].getType(), vector3);
            }
            i++;
        } while(true);
        Address address1;
        if(message.session != null)
            session = message.session;
        else
            session = Session.getDefaultInstance(System.getProperties(), null);
        if(j != 1) goto _L2; else goto _L1
_L1:
        address1 = aaddress[0];
        transport1 = session.getTransport(address1);
        transport1.connect();
        transport1.sendMessage(message, aaddress);
        transport1.close();
_L4:
        return;
        Exception exception1;
        exception1;
        transport1.close();
        throw exception1;
_L2:
        Object obj;
        boolean flag;
        Enumeration enumeration;
        obj = null;
        flag = false;
        enumeration = hashtable.elements();
_L5:
label0:
        {
            if(enumeration.hasMoreElements())
                break label0;
            if(flag || vector.size() != 0 || vector2.size() != 0)
            {
                Address aaddress5[] = (Address[])null;
                Address aaddress6[] = (Address[])null;
                Address aaddress7[] = (Address[])null;
                if(vector1.size() > 0)
                {
                    aaddress5 = new Address[vector1.size()];
                    vector1.copyInto(aaddress5);
                }
                if(vector2.size() > 0)
                {
                    aaddress6 = new Address[vector2.size()];
                    vector2.copyInto(aaddress6);
                }
                if(vector.size() > 0)
                {
                    aaddress7 = new Address[vector.size()];
                    vector.copyInto(aaddress7);
                }
                throw new SendFailedException("Sending failed", ((Exception) (obj)), aaddress5, aaddress6, aaddress7);
            }
        }
        if(true) goto _L4; else goto _L3
_L3:
        Address aaddress1[];
        Transport transport;
label1:
        {
            Vector vector4 = (Vector)enumeration.nextElement();
            aaddress1 = new Address[vector4.size()];
            vector4.copyInto(aaddress1);
            Address address = aaddress1[0];
            transport = session.getTransport(address);
            if(transport != null)
                break label1;
            int k1 = 0;
            while(k1 < aaddress1.length) 
            {
                vector.addElement(aaddress1[k1]);
                k1++;
            }
        }
          goto _L5
        transport.connect();
        transport.sendMessage(message, aaddress1);
        transport.close();
          goto _L5
        SendFailedException sendfailedexception;
        sendfailedexception;
        flag = true;
        if(obj != null) goto _L7; else goto _L6
_L6:
        obj = sendfailedexception;
_L17:
        Address aaddress2[] = sendfailedexception.getInvalidAddresses();
        if(aaddress2 == null) goto _L9; else goto _L8
_L8:
        int j1 = 0;
_L18:
        if(j1 < aaddress2.length) goto _L10; else goto _L9
_L9:
        Address aaddress3[] = sendfailedexception.getValidSentAddresses();
        if(aaddress3 == null) goto _L12; else goto _L11
_L11:
        int i1 = 0;
_L19:
        if(i1 < aaddress3.length) goto _L13; else goto _L12
_L12:
        Address aaddress4[] = sendfailedexception.getValidUnsentAddresses();
        if(aaddress4 == null) goto _L15; else goto _L14
_L14:
        int k = 0;
_L20:
        int l = aaddress4.length;
        if(k < l) goto _L16; else goto _L15
_L15:
        transport.close();
          goto _L5
_L7:
        ((MessagingException) (obj)).setNextException(sendfailedexception);
          goto _L17
        Exception exception;
        exception;
        transport.close();
        throw exception;
_L10:
        vector.addElement(aaddress2[j1]);
        j1++;
          goto _L18
_L13:
        vector1.addElement(aaddress3[i1]);
        i1++;
          goto _L19
_L16:
        vector2.addElement(aaddress4[k]);
        k++;
          goto _L20
        MessagingException messagingexception;
        messagingexception;
        flag = true;
        if(obj != null)
            break MISSING_BLOCK_LABEL_664;
        obj = messagingexception;
_L21:
        transport.close();
          goto _L5
        ((MessagingException) (obj)).setNextException(messagingexception);
          goto _L21
    }

    public void addTransportListener(TransportListener transportlistener)
    {
        this;
        JVM INSTR monitorenter ;
        if(transportListeners == null)
            transportListeners = new Vector();
        transportListeners.addElement(transportlistener);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    protected void notifyTransportListeners(int i, Address aaddress[], Address aaddress1[], Address aaddress2[], Message message)
    {
        if(transportListeners == null)
        {
            return;
        } else
        {
            queueEvent(new TransportEvent(this, i, aaddress, aaddress1, aaddress2, message), transportListeners);
            return;
        }
    }

    public void removeTransportListener(TransportListener transportlistener)
    {
        this;
        JVM INSTR monitorenter ;
        if(transportListeners != null)
            transportListeners.removeElement(transportlistener);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public abstract void sendMessage(Message message, Address aaddress[])
        throws MessagingException;

    private Vector transportListeners;
}
