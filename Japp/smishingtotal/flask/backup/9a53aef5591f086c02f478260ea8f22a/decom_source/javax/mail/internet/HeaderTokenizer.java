// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.internet;


// Referenced classes of package javax.mail.internet:
//            ParseException

public class HeaderTokenizer
{
    public static class Token
    {

        public int getType()
        {
            return type;
        }

        public String getValue()
        {
            return value;
        }

        public static final int ATOM = -1;
        public static final int COMMENT = -3;
        public static final int EOF = -4;
        public static final int QUOTEDSTRING = -2;
        private int type;
        private String value;

        public Token(int i, String s)
        {
            type = i;
            value = s;
        }
    }


    public HeaderTokenizer(String s)
    {
        this(s, "()<>@,;:\\\"\t .[]");
    }

    public HeaderTokenizer(String s, String s1)
    {
        this(s, s1, true);
    }

    public HeaderTokenizer(String s, String s1, boolean flag)
    {
        if(s == null)
            s = "";
        string = s;
        skipComments = flag;
        delimiters = s1;
        peekPos = 0;
        nextPos = 0;
        currentPos = 0;
        maxPos = string.length();
    }

    private static String filterToken(String s, int i, int j)
    {
        StringBuffer stringbuffer;
        boolean flag;
        boolean flag1;
        int k;
        stringbuffer = new StringBuffer();
        flag = false;
        flag1 = false;
        k = i;
_L2:
        char c;
        if(k >= j)
            return stringbuffer.toString();
        c = s.charAt(k);
        if(c != '\n' || !flag1)
            break; /* Loop/switch isn't completed */
        flag1 = false;
_L3:
        k++;
        if(true) goto _L2; else goto _L1
_L1:
        if(!flag)
        {
            if(c == '\\')
            {
                flag = true;
                flag1 = false;
            } else
            if(c == '\r')
            {
                flag1 = true;
            } else
            {
                stringbuffer.append(c);
                flag1 = false;
            }
        } else
        {
            stringbuffer.append(c);
            flag1 = false;
            flag = false;
        }
          goto _L3
        if(true) goto _L2; else goto _L4
_L4:
    }

    private Token getNext()
        throws ParseException
    {
        boolean flag;
        char c;
        if(currentPos >= maxPos)
            return EOFToken;
        if(skipWhiteSpace() == -4)
            return EOFToken;
        flag = false;
        c = string.charAt(currentPos);
_L15:
        int l;
        if(c != '(')
        {
            if(c != '"')
                break MISSING_BLOCK_LABEL_431;
            l = 1 + currentPos;
            currentPos = l;
            break MISSING_BLOCK_LABEL_68;
        }
        i = 1 + currentPos;
        currentPos = i;
        j = 1;
_L2:
        if(j <= 0 || currentPos >= maxPos)
        {
            if(j != 0)
                throw new ParseException("Unbalanced comments");
            break; /* Loop/switch isn't completed */
        }
        c1 = string.charAt(currentPos);
        if(c1 != '\\')
            break; /* Loop/switch isn't completed */
        currentPos = 1 + currentPos;
        flag = true;
_L3:
        currentPos = 1 + currentPos;
        if(true) goto _L2; else goto _L1
_L1:
        if(c1 == '\r')
            flag = true;
        else
        if(c1 == '(')
            j++;
        else
        if(c1 == ')')
            j--;
          goto _L3
        if(true) goto _L2; else goto _L4
_L4:
        if(!skipComments)
        {
            String s;
            if(flag)
                s = filterToken(string, i, -1 + currentPos);
            else
                s = string.substring(i, -1 + currentPos);
            return new Token(-3, s);
        }
        if(skipWhiteSpace() == -4)
            return EOFToken;
        c = string.charAt(currentPos);
        continue; /* Loop/switch isn't completed */
_L6:
        char c3;
        int i;
        int j;
        char c1;
        if(currentPos >= maxPos)
            throw new ParseException("Unbalanced quoted string");
        c3 = string.charAt(currentPos);
        if(c3 == '\\')
        {
            currentPos = 1 + currentPos;
            flag = true;
        } else
        {
            if(c3 != '\r')
                continue; /* Loop/switch isn't completed */
            flag = true;
        }
_L8:
        currentPos = 1 + currentPos;
        if(true) goto _L6; else goto _L5
_L5:
        if(c3 != '"') goto _L8; else goto _L7
_L7:
        currentPos = 1 + currentPos;
        String s1;
        if(flag)
            s1 = filterToken(string, l, -1 + currentPos);
        else
            s1 = string.substring(l, -1 + currentPos);
        return new Token(-2, s1);
        int k;
        if(c < ' ' || c >= '\177' || delimiters.indexOf(c) >= 0)
        {
            currentPos = 1 + currentPos;
            return new Token(c, new String(new char[] {
                c
            }));
        }
        k = currentPos;
_L13:
        if(currentPos < maxPos) goto _L10; else goto _L9
_L9:
        char c2;
        return new Token(-1, string.substring(k, currentPos));
_L10:
        if((c2 = string.charAt(currentPos)) < ' ' || c2 >= '\177' || c2 == '(' || c2 == ' ' || c2 == '"' || delimiters.indexOf(c2) >= 0) goto _L9; else goto _L11
_L11:
        currentPos = 1 + currentPos;
        if(true) goto _L13; else goto _L12
_L12:
        if(true) goto _L15; else goto _L14
_L14:
    }

    private int skipWhiteSpace()
    {
        do
        {
            if(currentPos >= maxPos)
                return -4;
            char c = string.charAt(currentPos);
            if(c != ' ' && c != '\t' && c != '\r' && c != '\n')
                return currentPos;
            currentPos = 1 + currentPos;
        } while(true);
    }

    public String getRemainder()
    {
        return string.substring(nextPos);
    }

    public Token next()
        throws ParseException
    {
        currentPos = nextPos;
        Token token = getNext();
        int i = currentPos;
        peekPos = i;
        nextPos = i;
        return token;
    }

    public Token peek()
        throws ParseException
    {
        currentPos = peekPos;
        Token token = getNext();
        peekPos = currentPos;
        return token;
    }

    private static final Token EOFToken = new Token(-4, null);
    public static final String MIME = "()<>@,;:\\\"\t []/?=";
    public static final String RFC822 = "()<>@,;:\\\"\t .[]";
    private int currentPos;
    private String delimiters;
    private int maxPos;
    private int nextPos;
    private int peekPos;
    private boolean skipComments;
    private String string;

}
