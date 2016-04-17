// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.imap.protocol;

import com.sun.mail.iap.Argument;
import java.io.IOException;
import java.util.*;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.search.*;

class SearchSequence
{

    SearchSequence()
    {
    }

    private static Argument and(AndTerm andterm, String s)
        throws SearchException, IOException
    {
        SearchTerm asearchterm[] = andterm.getTerms();
        Argument argument = generateSequence(asearchterm[0], s);
        int i = 1;
        do
        {
            if(i >= asearchterm.length)
                return argument;
            argument.append(generateSequence(asearchterm[i], s));
            i++;
        } while(true);
    }

    private static Argument body(BodyTerm bodyterm, String s)
        throws SearchException, IOException
    {
        Argument argument = new Argument();
        argument.writeAtom("BODY");
        argument.writeString(bodyterm.getPattern(), s);
        return argument;
    }

    private static Argument flag(FlagTerm flagterm)
        throws SearchException
    {
        boolean flag1;
        Argument argument;
        javax.mail.Flags.Flag aflag[];
        String as[];
        int i;
        flag1 = flagterm.getTestSet();
        argument = new Argument();
        Flags flags = flagterm.getFlags();
        aflag = flags.getSystemFlags();
        as = flags.getUserFlags();
        if(aflag.length == 0 && as.length == 0)
            throw new SearchException("Invalid FlagTerm");
        i = 0;
_L3:
        if(i < aflag.length) goto _L2; else goto _L1
_L1:
        int j = 0;
_L4:
        if(j >= as.length)
            return argument;
        break MISSING_BLOCK_LABEL_289;
_L2:
        if(aflag[i] == javax.mail.Flags.Flag.DELETED)
        {
            String s5;
            if(flag1)
                s5 = "DELETED";
            else
                s5 = "UNDELETED";
            argument.writeAtom(s5);
        } else
        if(aflag[i] == javax.mail.Flags.Flag.ANSWERED)
        {
            String s4;
            if(flag1)
                s4 = "ANSWERED";
            else
                s4 = "UNANSWERED";
            argument.writeAtom(s4);
        } else
        if(aflag[i] == javax.mail.Flags.Flag.DRAFT)
        {
            String s3;
            if(flag1)
                s3 = "DRAFT";
            else
                s3 = "UNDRAFT";
            argument.writeAtom(s3);
        } else
        if(aflag[i] == javax.mail.Flags.Flag.FLAGGED)
        {
            String s2;
            if(flag1)
                s2 = "FLAGGED";
            else
                s2 = "UNFLAGGED";
            argument.writeAtom(s2);
        } else
        if(aflag[i] == javax.mail.Flags.Flag.RECENT)
        {
            String s1;
            if(flag1)
                s1 = "RECENT";
            else
                s1 = "OLD";
            argument.writeAtom(s1);
        } else
        if(aflag[i] == javax.mail.Flags.Flag.SEEN)
        {
            String s;
            if(flag1)
                s = "SEEN";
            else
                s = "UNSEEN";
            argument.writeAtom(s);
        }
        i++;
          goto _L3
        String s6;
        if(flag1)
            s6 = "KEYWORD";
        else
            s6 = "UNKEYWORD";
        argument.writeAtom(s6);
        argument.writeAtom(as[j]);
        j++;
          goto _L4
    }

    private static Argument from(String s, String s1)
        throws SearchException, IOException
    {
        Argument argument = new Argument();
        argument.writeAtom("FROM");
        argument.writeString(s, s1);
        return argument;
    }

    static Argument generateSequence(SearchTerm searchterm, String s)
        throws SearchException, IOException
    {
        if(searchterm instanceof AndTerm)
            return and((AndTerm)searchterm, s);
        if(searchterm instanceof OrTerm)
            return or((OrTerm)searchterm, s);
        if(searchterm instanceof NotTerm)
            return not((NotTerm)searchterm, s);
        if(searchterm instanceof HeaderTerm)
            return header((HeaderTerm)searchterm, s);
        if(searchterm instanceof FlagTerm)
            return flag((FlagTerm)searchterm);
        if(searchterm instanceof FromTerm)
            return from(((FromTerm)searchterm).getAddress().toString(), s);
        if(searchterm instanceof FromStringTerm)
            return from(((FromStringTerm)searchterm).getPattern(), s);
        if(searchterm instanceof RecipientTerm)
        {
            RecipientTerm recipientterm = (RecipientTerm)searchterm;
            return recipient(recipientterm.getRecipientType(), recipientterm.getAddress().toString(), s);
        }
        if(searchterm instanceof RecipientStringTerm)
        {
            RecipientStringTerm recipientstringterm = (RecipientStringTerm)searchterm;
            return recipient(recipientstringterm.getRecipientType(), recipientstringterm.getPattern(), s);
        }
        if(searchterm instanceof SubjectTerm)
            return subject((SubjectTerm)searchterm, s);
        if(searchterm instanceof BodyTerm)
            return body((BodyTerm)searchterm, s);
        if(searchterm instanceof SizeTerm)
            return size((SizeTerm)searchterm);
        if(searchterm instanceof SentDateTerm)
            return sentdate((SentDateTerm)searchterm);
        if(searchterm instanceof ReceivedDateTerm)
            return receiveddate((ReceivedDateTerm)searchterm);
        if(searchterm instanceof MessageIDTerm)
            return messageid((MessageIDTerm)searchterm, s);
        else
            throw new SearchException("Search too complex");
    }

    private static Argument header(HeaderTerm headerterm, String s)
        throws SearchException, IOException
    {
        Argument argument = new Argument();
        argument.writeAtom("HEADER");
        argument.writeString(headerterm.getHeaderName());
        argument.writeString(headerterm.getPattern(), s);
        return argument;
    }

    private static boolean isAscii(String s)
    {
        int i = s.length();
        int j = 0;
        do
        {
            if(j >= i)
                return true;
            if(s.charAt(j) > '\177')
                return false;
            j++;
        } while(true);
    }

    static boolean isAscii(SearchTerm searchterm)
    {
        if(!(searchterm instanceof AndTerm) && !(searchterm instanceof OrTerm)) goto _L2; else goto _L1
_L1:
        SearchTerm asearchterm[];
        int i;
        if(searchterm instanceof AndTerm)
            asearchterm = ((AndTerm)searchterm).getTerms();
        else
            asearchterm = ((OrTerm)searchterm).getTerms();
        i = 0;
_L7:
        if(i < asearchterm.length) goto _L4; else goto _L3
_L3:
        return true;
_L4:
        if(!isAscii(asearchterm[i]))
            return false;
        i++;
        continue; /* Loop/switch isn't completed */
_L2:
        if(searchterm instanceof NotTerm)
            return isAscii(((NotTerm)searchterm).getTerm());
        if(searchterm instanceof StringTerm)
            return isAscii(((StringTerm)searchterm).getPattern());
        if(!(searchterm instanceof AddressTerm)) goto _L3; else goto _L5
_L5:
        return isAscii(((AddressTerm)searchterm).getAddress().toString());
        if(true) goto _L7; else goto _L6
_L6:
    }

    private static Argument messageid(MessageIDTerm messageidterm, String s)
        throws SearchException, IOException
    {
        Argument argument = new Argument();
        argument.writeAtom("HEADER");
        argument.writeString("Message-ID");
        argument.writeString(messageidterm.getPattern(), s);
        return argument;
    }

    private static Argument not(NotTerm notterm, String s)
        throws SearchException, IOException
    {
        Argument argument = new Argument();
        argument.writeAtom("NOT");
        SearchTerm searchterm = notterm.getTerm();
        if((searchterm instanceof AndTerm) || (searchterm instanceof FlagTerm))
        {
            argument.writeArgument(generateSequence(searchterm, s));
            return argument;
        } else
        {
            argument.append(generateSequence(searchterm, s));
            return argument;
        }
    }

    private static Argument or(OrTerm orterm, String s)
        throws SearchException, IOException
    {
        SearchTerm asearchterm[] = orterm.getTerms();
        if(asearchterm.length <= 2) goto _L2; else goto _L1
_L1:
        Object obj;
        int i;
        obj = asearchterm[0];
        i = 1;
_L6:
        if(i < asearchterm.length) goto _L4; else goto _L3
_L3:
        asearchterm = ((OrTerm)obj).getTerms();
_L2:
        Argument argument;
        argument = new Argument();
        if(asearchterm.length > 1)
            argument.writeAtom("OR");
        OrTerm orterm1;
        if((asearchterm[0] instanceof AndTerm) || (asearchterm[0] instanceof FlagTerm))
            argument.writeArgument(generateSequence(asearchterm[0], s));
        else
            argument.append(generateSequence(asearchterm[0], s));
        if(asearchterm.length > 1)
        {
            if(!(asearchterm[1] instanceof AndTerm) && !(asearchterm[1] instanceof FlagTerm))
                break; /* Loop/switch isn't completed */
            argument.writeArgument(generateSequence(asearchterm[1], s));
        }
        return argument;
_L4:
        orterm1 = new OrTerm(((SearchTerm) (obj)), asearchterm[i]);
        i++;
        obj = orterm1;
        if(true) goto _L6; else goto _L5
_L5:
        argument.append(generateSequence(asearchterm[1], s));
        return argument;
    }

    private static Argument receiveddate(DateTerm dateterm)
        throws SearchException
    {
        Argument argument = new Argument();
        String s = toIMAPDate(dateterm.getDate());
        switch(dateterm.getComparison())
        {
        default:
            throw new SearchException("Cannot handle Date Comparison");

        case 5: // '\005'
            argument.writeAtom((new StringBuilder("SINCE ")).append(s).toString());
            return argument;

        case 3: // '\003'
            argument.writeAtom((new StringBuilder("ON ")).append(s).toString());
            return argument;

        case 2: // '\002'
            argument.writeAtom((new StringBuilder("BEFORE ")).append(s).toString());
            return argument;

        case 6: // '\006'
            argument.writeAtom((new StringBuilder("OR SINCE ")).append(s).append(" ON ").append(s).toString());
            return argument;

        case 1: // '\001'
            argument.writeAtom((new StringBuilder("OR BEFORE ")).append(s).append(" ON ").append(s).toString());
            return argument;

        case 4: // '\004'
            argument.writeAtom((new StringBuilder("NOT ON ")).append(s).toString());
            return argument;
        }
    }

    private static Argument recipient(javax.mail.Message.RecipientType recipienttype, String s, String s1)
        throws SearchException, IOException
    {
        Argument argument = new Argument();
        if(recipienttype == javax.mail.Message.RecipientType.TO)
            argument.writeAtom("TO");
        else
        if(recipienttype == javax.mail.Message.RecipientType.CC)
            argument.writeAtom("CC");
        else
        if(recipienttype == javax.mail.Message.RecipientType.BCC)
            argument.writeAtom("BCC");
        else
            throw new SearchException("Illegal Recipient type");
        argument.writeString(s, s1);
        return argument;
    }

    private static Argument sentdate(DateTerm dateterm)
        throws SearchException
    {
        Argument argument = new Argument();
        String s = toIMAPDate(dateterm.getDate());
        switch(dateterm.getComparison())
        {
        default:
            throw new SearchException("Cannot handle Date Comparison");

        case 5: // '\005'
            argument.writeAtom((new StringBuilder("SENTSINCE ")).append(s).toString());
            return argument;

        case 3: // '\003'
            argument.writeAtom((new StringBuilder("SENTON ")).append(s).toString());
            return argument;

        case 2: // '\002'
            argument.writeAtom((new StringBuilder("SENTBEFORE ")).append(s).toString());
            return argument;

        case 6: // '\006'
            argument.writeAtom((new StringBuilder("OR SENTSINCE ")).append(s).append(" SENTON ").append(s).toString());
            return argument;

        case 1: // '\001'
            argument.writeAtom((new StringBuilder("OR SENTBEFORE ")).append(s).append(" SENTON ").append(s).toString());
            return argument;

        case 4: // '\004'
            argument.writeAtom((new StringBuilder("NOT SENTON ")).append(s).toString());
            return argument;
        }
    }

    private static Argument size(SizeTerm sizeterm)
        throws SearchException
    {
        Argument argument = new Argument();
        sizeterm.getComparison();
        JVM INSTR tableswitch 2 5: default 44
    //                   2 72
    //                   3 44
    //                   4 44
    //                   5 55;
           goto _L1 _L2 _L1 _L1 _L3
_L1:
        throw new SearchException("Cannot handle Comparison");
_L3:
        argument.writeAtom("LARGER");
_L5:
        argument.writeNumber(sizeterm.getNumber());
        return argument;
_L2:
        argument.writeAtom("SMALLER");
        if(true) goto _L5; else goto _L4
_L4:
    }

    private static Argument subject(SubjectTerm subjectterm, String s)
        throws SearchException, IOException
    {
        Argument argument = new Argument();
        argument.writeAtom("SUBJECT");
        argument.writeString(subjectterm.getPattern(), s);
        return argument;
    }

    private static String toIMAPDate(Date date)
    {
        StringBuffer stringbuffer = new StringBuffer();
        cal.setTime(date);
        stringbuffer.append(cal.get(5)).append("-");
        stringbuffer.append(monthTable[cal.get(2)]).append('-');
        stringbuffer.append(cal.get(1));
        return stringbuffer.toString();
    }

    private static Calendar cal = new GregorianCalendar();
    private static String monthTable[] = {
        "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", 
        "Nov", "Dec"
    };

}
