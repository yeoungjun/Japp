// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ProtocolException;
import com.sun.mail.iap.Response;
import java.util.Vector;

// Referenced classes of package com.sun.mail.imap.protocol:
//            BASE64MailboxDecoder

public class Namespaces
{
    public static class Namespace
    {

        public char delimiter;
        public String prefix;

        public Namespace(Response response)
            throws ProtocolException
        {
            if(response.readByte() != 40)
                throw new ProtocolException("Missing '(' at start of Namespace");
            prefix = BASE64MailboxDecoder.decode(response.readString());
            response.skipSpaces();
            if(response.peekByte() == 34)
            {
                response.readByte();
                delimiter = (char)response.readByte();
                if(delimiter == '\\')
                    delimiter = (char)response.readByte();
                if(response.readByte() != 34)
                    throw new ProtocolException("Missing '\"' at end of QUOTED_CHAR");
            } else
            {
                String s = response.readAtom();
                if(s == null)
                    throw new ProtocolException("Expected NIL, got null");
                if(!s.equalsIgnoreCase("NIL"))
                    throw new ProtocolException((new StringBuilder("Expected NIL, got ")).append(s).toString());
                delimiter = '\0';
            }
            if(response.peekByte() != 41)
            {
                response.skipSpaces();
                response.readString();
                response.skipSpaces();
                response.readStringList();
            }
            if(response.readByte() != 41)
                throw new ProtocolException("Missing ')' at end of Namespace");
            else
                return;
        }
    }


    public Namespaces(Response response)
        throws ProtocolException
    {
        personal = getNamespaces(response);
        otherUsers = getNamespaces(response);
        shared = getNamespaces(response);
    }

    private Namespace[] getNamespaces(Response response)
        throws ProtocolException
    {
        response.skipSpaces();
        if(response.peekByte() == 40)
        {
            Vector vector = new Vector();
            response.readByte();
            do
                vector.addElement(new Namespace(response));
            while(response.peekByte() != 41);
            response.readByte();
            Namespace anamespace[] = new Namespace[vector.size()];
            vector.copyInto(anamespace);
            return anamespace;
        }
        String s = response.readAtom();
        if(s == null)
            throw new ProtocolException("Expected NIL, got null");
        if(!s.equalsIgnoreCase("NIL"))
            throw new ProtocolException((new StringBuilder("Expected NIL, got ")).append(s).toString());
        else
            return null;
    }

    public Namespace otherUsers[];
    public Namespace personal[];
    public Namespace shared[];
}
