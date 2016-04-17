// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.imap.protocol;

import com.sun.mail.iap.*;
import com.sun.mail.util.*;
import java.io.*;
import java.util.Properties;
import java.util.Vector;
import javax.security.auth.callback.*;
import javax.security.sasl.*;

// Referenced classes of package com.sun.mail.imap.protocol:
//            SaslAuthenticator, IMAPProtocol

public class IMAPSaslAuthenticator
    implements SaslAuthenticator
{

    public IMAPSaslAuthenticator(IMAPProtocol imapprotocol, String s, Properties properties, boolean flag, PrintStream printstream, String s1)
    {
        pr = imapprotocol;
        name = s;
        props = properties;
        debug = flag;
        out = printstream;
        host = s1;
    }

    public boolean authenticate(String as[], final String r0, String s, final String u0, final String p0)
        throws ProtocolException
    {
        IMAPProtocol imapprotocol = pr;
        imapprotocol;
        JVM INSTR monitorenter ;
        Vector vector = new Vector();
        Response response;
        boolean flag;
        response = null;
        flag = false;
        if(!debug) goto _L2; else goto _L1
_L1:
        out.print("IMAP SASL DEBUG: Mechanisms:");
        int i = 0;
_L6:
        if(i < as.length) goto _L4; else goto _L3
_L3:
        out.println();
_L2:
        CallbackHandler callbackhandler = new CallbackHandler() {

            public void handle(Callback acallback[])
            {
                int j;
                if(debug)
                    out.println((new StringBuilder("IMAP SASL DEBUG: callback length: ")).append(acallback.length).toString());
                j = 0;
_L2:
                if(j >= acallback.length)
                    return;
                if(debug)
                    out.println((new StringBuilder("IMAP SASL DEBUG: callback ")).append(j).append(": ").append(acallback[j]).toString());
                if(!(acallback[j] instanceof NameCallback))
                    break; /* Loop/switch isn't completed */
                ((NameCallback)acallback[j]).setName(u0);
_L3:
                j++;
                if(true) goto _L2; else goto _L1
_L1:
                RealmChoiceCallback realmchoicecallback;
                if(acallback[j] instanceof PasswordCallback)
                    ((PasswordCallback)acallback[j]).setPassword(p0.toCharArray());
                else
                if(acallback[j] instanceof RealmCallback)
                {
                    RealmCallback realmcallback = (RealmCallback)acallback[j];
                    String s3;
                    if(r0 != null)
                        s3 = r0;
                    else
                        s3 = realmcallback.getDefaultText();
                    realmcallback.setText(s3);
                } else
                if(acallback[j] instanceof RealmChoiceCallback)
                {
label0:
                    {
                        realmchoicecallback = (RealmChoiceCallback)acallback[j];
                        if(r0 != null)
                            break label0;
                        realmchoicecallback.setSelectedIndex(realmchoicecallback.getDefaultChoice());
                    }
                }
                  goto _L3
                String as1[];
                int k;
                as1 = realmchoicecallback.getChoices();
                k = 0;
_L4:
                if(k < as1.length)
                {
label1:
                    {
                        if(!as1[k].equals(r0))
                            break label1;
                        realmchoicecallback.setSelectedIndex(k);
                    }
                }
                  goto _L3
                k++;
                  goto _L4
            }

            final IMAPSaslAuthenticator this$0;
            private final String val$p0;
            private final String val$r0;
            private final String val$u0;

            
            {
                this$0 = IMAPSaslAuthenticator.this;
                u0 = s;
                p0 = s1;
                r0 = s2;
                super();
            }
        };
        SaslClient saslclient = Sasl.createSaslClient(as, s, name, host, props, callbackhandler);
        if(saslclient != null)
            break; /* Loop/switch isn't completed */
        if(debug)
            out.println("IMAP SASL DEBUG: No SASL support");
        imapprotocol;
        JVM INSTR monitorexit ;
        return false;
_L4:
        out.print((new StringBuilder(" ")).append(as[i]).toString());
        i++;
        if(true) goto _L6; else goto _L5
        SaslException saslexception;
        saslexception;
        if(debug)
            out.println((new StringBuilder("IMAP SASL DEBUG: Failed to create SASL client: ")).append(saslexception).toString());
        imapprotocol;
        JVM INSTR monitorexit ;
        return false;
_L5:
        if(debug)
            out.println((new StringBuilder("IMAP SASL DEBUG: SASL client ")).append(saslclient.getMechanismName()).toString());
        String s1 = pr.writeCommand((new StringBuilder("AUTHENTICATE ")).append(saslclient.getMechanismName()).toString(), null);
        OutputStream outputstream;
        ByteArrayOutputStream bytearrayoutputstream;
        byte abyte0[];
        boolean flag1;
        outputstream = pr.getIMAPOutputStream();
        bytearrayoutputstream = new ByteArrayOutputStream();
        abyte0 = (new byte[] {
            13, 10
        });
        flag1 = saslclient.getMechanismName().equals("XGWTRUSTEDAPP");
_L7:
        if(!flag)
            break MISSING_BLOCK_LABEL_417;
        String s2;
        if(!saslclient.isComplete())
            break MISSING_BLOCK_LABEL_760;
        s2 = (String)saslclient.getNegotiatedProperty("javax.security.sasl.qop");
        if(s2 == null)
            break MISSING_BLOCK_LABEL_760;
        if(!s2.equalsIgnoreCase("auth-int") && !s2.equalsIgnoreCase("auth-conf"))
            break MISSING_BLOCK_LABEL_760;
        if(debug)
            out.println("IMAP SASL DEBUG: Mechanism requires integrity or confidentiality");
        imapprotocol;
        JVM INSTR monitorexit ;
        return false;
        Exception exception1;
        exception1;
        if(debug)
            out.println((new StringBuilder("IMAP SASL DEBUG: AUTHENTICATE Exception: ")).append(exception1).toString());
        imapprotocol;
        JVM INSTR monitorexit ;
        return false;
        byte abyte1[];
        response = pr.readResponse();
        if(!response.isContinuation())
            break MISSING_BLOCK_LABEL_709;
        abyte1 = (byte[])null;
        if(!saslclient.isComplete())
        {
            byte abyte3[] = response.readByteArray().getNewBytes();
            if(abyte3.length > 0)
                abyte3 = BASE64DecoderStream.decode(abyte3);
            if(debug)
                out.println((new StringBuilder("IMAP SASL DEBUG: challenge: ")).append(ASCIIUtility.toString(abyte3, 0, abyte3.length)).append(" :").toString());
            abyte1 = saslclient.evaluateChallenge(abyte3);
        }
        if(abyte1 != null)
            break MISSING_BLOCK_LABEL_599;
        if(debug)
            out.println("IMAP SASL DEBUG: no response");
        outputstream.write(abyte0);
        outputstream.flush();
        bytearrayoutputstream.reset();
          goto _L7
        Exception exception2;
        exception2;
        Response response1;
        if(debug)
            exception2.printStackTrace();
        response1 = Response.byeResponse(exception2);
        response = response1;
        flag = true;
          goto _L7
        byte abyte2[];
        if(debug)
            out.println((new StringBuilder("IMAP SASL DEBUG: response: ")).append(ASCIIUtility.toString(abyte1, 0, abyte1.length)).append(" :").toString());
        abyte2 = BASE64EncoderStream.encode(abyte1);
        if(!flag1)
            break MISSING_BLOCK_LABEL_664;
        bytearrayoutputstream.write("XGWTRUSTEDAPP ".getBytes());
        bytearrayoutputstream.write(abyte2);
        bytearrayoutputstream.write(abyte0);
        outputstream.write(bytearrayoutputstream.toByteArray());
        outputstream.flush();
        bytearrayoutputstream.reset();
          goto _L7
        Exception exception;
        exception;
        imapprotocol;
        JVM INSTR monitorexit ;
        throw exception;
        if(!response.isTagged() || !response.getTag().equals(s1))
            break MISSING_BLOCK_LABEL_736;
        flag = true;
          goto _L7
        if(!response.isBYE())
            break MISSING_BLOCK_LABEL_750;
        flag = true;
          goto _L7
        vector.addElement(response);
          goto _L7
        Response aresponse[] = new Response[vector.size()];
        vector.copyInto(aresponse);
        pr.notifyResponseHandlers(aresponse);
        pr.handleResult(response);
        pr.setCapabilities(response);
        imapprotocol;
        JVM INSTR monitorexit ;
        return true;
    }

    private boolean debug;
    private String host;
    private String name;
    private PrintStream out;
    private IMAPProtocol pr;
    private Properties props;


}
