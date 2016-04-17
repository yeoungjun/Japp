// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;
import com.sun.mail.iap.Response;
import java.io.PrintStream;
import java.util.Vector;
import javax.mail.internet.ParameterList;

// Referenced classes of package com.sun.mail.imap.protocol:
//            Item, FetchResponse, ENVELOPE

public class BODYSTRUCTURE
    implements Item
{

    public BODYSTRUCTURE(FetchResponse fetchresponse)
        throws ParsingException
    {
        lines = -1;
        size = -1;
        if(parseDebug)
            System.out.println("DEBUG IMAP: parsing BODYSTRUCTURE");
        msgno = fetchresponse.getNumber();
        if(parseDebug)
            System.out.println((new StringBuilder("DEBUG IMAP: msgno ")).append(msgno).toString());
        fetchresponse.skipSpaces();
        if(fetchresponse.readByte() != 40)
            throw new ParsingException("BODYSTRUCTURE parse error: missing ``('' at start");
        if(fetchresponse.peekByte() != 40) goto _L2; else goto _L1
_L1:
        if(parseDebug)
            System.out.println("DEBUG IMAP: parsing multipart");
        type = "multipart";
        processedType = MULTI;
        Vector vector = new Vector(1);
        do
        {
            vector.addElement(new BODYSTRUCTURE(fetchresponse));
            fetchresponse.skipSpaces();
        } while(fetchresponse.peekByte() == 40);
        bodies = new BODYSTRUCTURE[vector.size()];
        vector.copyInto(bodies);
        subtype = fetchresponse.readString();
        if(parseDebug)
            System.out.println((new StringBuilder("DEBUG IMAP: subtype ")).append(subtype).toString());
        if(fetchresponse.readByte() != 41) goto _L4; else goto _L3
_L3:
        if(parseDebug)
            System.out.println("DEBUG IMAP: parse DONE");
_L6:
        return;
_L4:
        if(parseDebug)
            System.out.println("DEBUG IMAP: parsing extension data");
        cParams = parseParameters(fetchresponse);
        if(fetchresponse.readByte() == 41)
        {
            if(parseDebug)
            {
                System.out.println("DEBUG IMAP: body parameters DONE");
                return;
            }
            continue; /* Loop/switch isn't completed */
        }
        byte byte1 = fetchresponse.readByte();
        byte byte2;
        if(byte1 == 40)
        {
            if(parseDebug)
                System.out.println("DEBUG IMAP: parse disposition");
            disposition = fetchresponse.readString();
            if(parseDebug)
                System.out.println((new StringBuilder("DEBUG IMAP: disposition ")).append(disposition).toString());
            dParams = parseParameters(fetchresponse);
            if(fetchresponse.readByte() != 41)
                throw new ParsingException("BODYSTRUCTURE parse error: missing ``)'' at end of disposition in multipart");
            if(parseDebug)
                System.out.println("DEBUG IMAP: disposition DONE");
        } else
        if(byte1 == 78 || byte1 == 110)
        {
            if(parseDebug)
                System.out.println("DEBUG IMAP: disposition NIL");
            fetchresponse.skip(2);
        } else
        {
            throw new ParsingException((new StringBuilder("BODYSTRUCTURE parse error: ")).append(type).append("/").append(subtype).append(": ").append("bad multipart disposition, b ").append(byte1).toString());
        }
        byte2 = fetchresponse.readByte();
        if(byte2 == 41)
        {
            if(parseDebug)
            {
                System.out.println("DEBUG IMAP: no body-fld-lang");
                return;
            }
            continue; /* Loop/switch isn't completed */
        }
        if(byte2 != 32)
            throw new ParsingException("BODYSTRUCTURE parse error: missing space after disposition");
        if(fetchresponse.peekByte() != 40)
            break; /* Loop/switch isn't completed */
        language = fetchresponse.readStringList();
        if(parseDebug)
            System.out.println((new StringBuilder("DEBUG IMAP: language len ")).append(language.length).toString());
_L7:
        while(fetchresponse.readByte() == 32) 
            parseBodyExtension(fetchresponse);
        if(true) goto _L6; else goto _L5
_L5:
        String s1 = fetchresponse.readString();
        if(s1 != null)
        {
            language = (new String[] {
                s1
            });
            if(parseDebug)
                System.out.println((new StringBuilder("DEBUG IMAP: language ")).append(s1).toString());
        }
          goto _L7
_L2:
        if(parseDebug)
            System.out.println("DEBUG IMAP: single part");
        type = fetchresponse.readString();
        if(parseDebug)
            System.out.println((new StringBuilder("DEBUG IMAP: type ")).append(type).toString());
        processedType = SINGLE;
        subtype = fetchresponse.readString();
        if(parseDebug)
            System.out.println((new StringBuilder("DEBUG IMAP: subtype ")).append(subtype).toString());
        if(type == null)
        {
            type = "application";
            subtype = "octet-stream";
        }
        cParams = parseParameters(fetchresponse);
        if(parseDebug)
            System.out.println((new StringBuilder("DEBUG IMAP: cParams ")).append(cParams).toString());
        id = fetchresponse.readString();
        if(parseDebug)
            System.out.println((new StringBuilder("DEBUG IMAP: id ")).append(id).toString());
        description = fetchresponse.readString();
        if(parseDebug)
            System.out.println((new StringBuilder("DEBUG IMAP: description ")).append(description).toString());
        encoding = fetchresponse.readString();
        if(parseDebug)
            System.out.println((new StringBuilder("DEBUG IMAP: encoding ")).append(encoding).toString());
        size = fetchresponse.readNumber();
        if(parseDebug)
            System.out.println((new StringBuilder("DEBUG IMAP: size ")).append(size).toString());
        if(size < 0)
            throw new ParsingException("BODYSTRUCTURE parse error: bad ``size'' element");
        if(type.equalsIgnoreCase("text"))
        {
            lines = fetchresponse.readNumber();
            if(parseDebug)
                System.out.println((new StringBuilder("DEBUG IMAP: lines ")).append(lines).toString());
            if(lines < 0)
                throw new ParsingException("BODYSTRUCTURE parse error: bad ``lines'' element");
        } else
        if(type.equalsIgnoreCase("message") && subtype.equalsIgnoreCase("rfc822"))
        {
            processedType = NESTED;
            envelope = new ENVELOPE(fetchresponse);
            BODYSTRUCTURE abodystructure[] = new BODYSTRUCTURE[1];
            abodystructure[0] = new BODYSTRUCTURE(fetchresponse);
            bodies = abodystructure;
            lines = fetchresponse.readNumber();
            if(parseDebug)
                System.out.println((new StringBuilder("DEBUG IMAP: lines ")).append(lines).toString());
            if(lines < 0)
                throw new ParsingException("BODYSTRUCTURE parse error: bad ``lines'' element");
        } else
        {
            fetchresponse.skipSpaces();
            if(Character.isDigit((char)fetchresponse.peekByte()))
                throw new ParsingException((new StringBuilder("BODYSTRUCTURE parse error: server erroneously included ``lines'' element with type ")).append(type).append("/").append(subtype).toString());
        }
        if(fetchresponse.peekByte() != 41)
            break; /* Loop/switch isn't completed */
        fetchresponse.readByte();
        if(parseDebug)
        {
            System.out.println("DEBUG IMAP: parse DONE");
            return;
        }
        if(true) goto _L6; else goto _L8
_L8:
        md5 = fetchresponse.readString();
        if(fetchresponse.readByte() != 41)
            break; /* Loop/switch isn't completed */
        if(parseDebug)
        {
            System.out.println("DEBUG IMAP: no MD5 DONE");
            return;
        }
        if(true) goto _L6; else goto _L9
_L9:
        byte byte0 = fetchresponse.readByte();
        if(byte0 != 40) goto _L11; else goto _L10
_L10:
        disposition = fetchresponse.readString();
        if(parseDebug)
            System.out.println((new StringBuilder("DEBUG IMAP: disposition ")).append(disposition).toString());
        dParams = parseParameters(fetchresponse);
        if(parseDebug)
            System.out.println((new StringBuilder("DEBUG IMAP: dParams ")).append(dParams).toString());
        if(fetchresponse.readByte() != 41)
            throw new ParsingException("BODYSTRUCTURE parse error: missing ``)'' at end of disposition");
          goto _L12
_L11:
        if(byte0 != 78 && byte0 != 110) goto _L14; else goto _L13
_L13:
        if(parseDebug)
            System.out.println("DEBUG IMAP: disposition NIL");
        fetchresponse.skip(2);
_L12:
        if(fetchresponse.readByte() == 41)
        {
            if(parseDebug)
            {
                System.out.println("DEBUG IMAP: disposition DONE");
                return;
            }
        } else
        {
label0:
            {
                if(fetchresponse.peekByte() == 40)
                {
                    language = fetchresponse.readStringList();
                    if(parseDebug)
                        System.out.println((new StringBuilder("DEBUG IMAP: language len ")).append(language.length).toString());
                } else
                {
                    String s = fetchresponse.readString();
                    if(s != null)
                    {
                        language = (new String[] {
                            s
                        });
                        if(parseDebug)
                            System.out.println((new StringBuilder("DEBUG IMAP: language ")).append(s).toString());
                    }
                }
                for(; fetchresponse.readByte() == 32; parseBodyExtension(fetchresponse))
                    break label0;

                if(parseDebug)
                {
                    System.out.println("DEBUG IMAP: all DONE");
                    return;
                }
            }
        }
        if(true) goto _L6; else goto _L14
_L14:
        throw new ParsingException((new StringBuilder("BODYSTRUCTURE parse error: ")).append(type).append("/").append(subtype).append(": ").append("bad single part disposition, b ").append(byte0).toString());
    }

    private void parseBodyExtension(Response response)
        throws ParsingException
    {
        response.skipSpaces();
        byte byte0 = response.peekByte();
        if(byte0 == 40)
        {
            response.skip(1);
            do
                parseBodyExtension(response);
            while(response.readByte() != 41);
            return;
        }
        if(Character.isDigit((char)byte0))
        {
            response.readNumber();
            return;
        } else
        {
            response.readString();
            return;
        }
    }

    private ParameterList parseParameters(Response response)
        throws ParsingException
    {
        response.skipSpaces();
        byte byte0 = response.readByte();
        if(byte0 == 40)
        {
            ParameterList parameterlist = new ParameterList();
            do
            {
                String s = response.readString();
                if(parseDebug)
                    System.out.println((new StringBuilder("DEBUG IMAP: parameter name ")).append(s).toString());
                if(s == null)
                    throw new ParsingException((new StringBuilder("BODYSTRUCTURE parse error: ")).append(type).append("/").append(subtype).append(": ").append("null name in parameter list").toString());
                String s1 = response.readString();
                if(parseDebug)
                    System.out.println((new StringBuilder("DEBUG IMAP: parameter value ")).append(s1).toString());
                parameterlist.set(s, s1);
            } while(response.readByte() != 41);
            parameterlist.set(null, "DONE");
            return parameterlist;
        }
        if(byte0 == 78 || byte0 == 110)
        {
            if(parseDebug)
                System.out.println("DEBUG IMAP: parameter list NIL");
            response.skip(2);
            return null;
        } else
        {
            throw new ParsingException("Parameter list parse error");
        }
    }

    public boolean isMulti()
    {
        return processedType == MULTI;
    }

    public boolean isNested()
    {
        return processedType == NESTED;
    }

    public boolean isSingle()
    {
        return processedType == SINGLE;
    }

    private static int MULTI;
    private static int NESTED;
    private static int SINGLE;
    static final char name[];
    private static boolean parseDebug;
    public String attachment;
    public BODYSTRUCTURE bodies[];
    public ParameterList cParams;
    public ParameterList dParams;
    public String description;
    public String disposition;
    public String encoding;
    public ENVELOPE envelope;
    public String id;
    public String language[];
    public int lines;
    public String md5;
    public int msgno;
    private int processedType;
    public int size;
    public String subtype;
    public String type;

    static 
    {
        boolean flag;
        flag = true;
        name = (new char[] {
            'B', 'O', 'D', 'Y', 'S', 'T', 'R', 'U', 'C', 'T', 
            'U', 'R', 'E'
        });
        SINGLE = ((flag) ? 1 : 0);
        MULTI = 2;
        NESTED = 3;
        parseDebug = false;
        String s = System.getProperty("mail.imap.parse.debug");
        if(s == null)
            break MISSING_BLOCK_LABEL_121;
        if(!s.equalsIgnoreCase("true"))
            break MISSING_BLOCK_LABEL_121;
_L1:
        parseDebug = flag;
        break MISSING_BLOCK_LABEL_127;
        flag = false;
          goto _L1
        SecurityException securityexception;
        securityexception;
    }
}
