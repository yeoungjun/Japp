// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.activation.registries;


public class MailcapTokenizer
{

    public MailcapTokenizer(String s)
    {
        data = s;
        dataIndex = 0;
        dataLength = s.length();
        currentToken = 1;
        currentTokenValue = "";
        isAutoquoting = false;
        autoquoteChar = ';';
    }

    private static String fixEscapeSequences(String s)
    {
        int i;
        StringBuffer stringbuffer;
        int j;
        i = s.length();
        stringbuffer = new StringBuffer();
        stringbuffer.ensureCapacity(i);
        j = 0;
_L2:
        char c;
        if(j >= i)
            return stringbuffer.toString();
        c = s.charAt(j);
        if(c == '\\')
            break; /* Loop/switch isn't completed */
        stringbuffer.append(c);
_L3:
        j++;
        if(true) goto _L2; else goto _L1
_L1:
        if(j < i - 1)
        {
            stringbuffer.append(s.charAt(j + 1));
            j++;
        } else
        {
            stringbuffer.append(c);
        }
          goto _L3
        if(true) goto _L2; else goto _L4
_L4:
    }

    private static boolean isControlChar(char c)
    {
        return Character.isISOControl(c);
    }

    private static boolean isSpecialChar(char c)
    {
        switch(c)
        {
        default:
            return false;

        case 34: // '"'
        case 40: // '('
        case 41: // ')'
        case 44: // ','
        case 47: // '/'
        case 58: // ':'
        case 59: // ';'
        case 60: // '<'
        case 61: // '='
        case 62: // '>'
        case 63: // '?'
        case 64: // '@'
        case 91: // '['
        case 92: // '\\'
        case 93: // ']'
            return true;
        }
    }

    private static boolean isStringTokenChar(char c)
    {
        return !isSpecialChar(c) && !isControlChar(c) && !isWhiteSpaceChar(c);
    }

    private static boolean isWhiteSpaceChar(char c)
    {
        return Character.isWhitespace(c);
    }

    public static String nameForToken(int i)
    {
        switch(i)
        {
        default:
            return "really unknown";

        case 0: // '\0'
            return "unknown";

        case 1: // '\001'
            return "start";

        case 2: // '\002'
            return "string";

        case 5: // '\005'
            return "EOI";

        case 47: // '/'
            return "'/'";

        case 59: // ';'
            return "';'";

        case 61: // '='
            return "'='";
        }
    }

    private void processAutoquoteToken()
    {
        int i = dataIndex;
        boolean flag = false;
        do
        {
            if(dataIndex >= dataLength || flag)
            {
                currentToken = 2;
                currentTokenValue = fixEscapeSequences(data.substring(i, dataIndex));
                return;
            }
            if(data.charAt(dataIndex) != autoquoteChar)
                dataIndex = 1 + dataIndex;
            else
                flag = true;
        } while(true);
    }

    private void processStringToken()
    {
        int i = dataIndex;
        do
        {
            if(dataIndex >= dataLength || !isStringTokenChar(data.charAt(dataIndex)))
            {
                currentToken = 2;
                currentTokenValue = data.substring(i, dataIndex);
                return;
            }
            dataIndex = 1 + dataIndex;
        } while(true);
    }

    public int getCurrentToken()
    {
        return currentToken;
    }

    public String getCurrentTokenValue()
    {
        return currentTokenValue;
    }

    public int nextToken()
    {
        if(dataIndex >= dataLength)
            break MISSING_BLOCK_LABEL_247;
_L3:
        if(dataIndex < dataLength && isWhiteSpaceChar(data.charAt(dataIndex))) goto _L2; else goto _L1
_L1:
        if(dataIndex < dataLength)
        {
            int i = data.charAt(dataIndex);
            if(isAutoquoting)
            {
                if(i == 59 || i == 61)
                {
                    currentToken = i;
                    currentTokenValue = (new Character(i)).toString();
                    dataIndex = 1 + dataIndex;
                } else
                {
                    processAutoquoteToken();
                }
            } else
            if(isStringTokenChar(i))
                processStringToken();
            else
            if(i == 47 || i == 59 || i == 61)
            {
                currentToken = i;
                currentTokenValue = (new Character(i)).toString();
                dataIndex = 1 + dataIndex;
            } else
            {
                currentToken = 0;
                currentTokenValue = (new Character(i)).toString();
                dataIndex = 1 + dataIndex;
            }
        } else
        {
            currentToken = 5;
            currentTokenValue = null;
        }
_L4:
        return currentToken;
_L2:
        dataIndex = 1 + dataIndex;
          goto _L3
        currentToken = 5;
        currentTokenValue = null;
          goto _L4
    }

    public void setIsAutoquoting(boolean flag)
    {
        isAutoquoting = flag;
    }

    public static final int EOI_TOKEN = 5;
    public static final int EQUALS_TOKEN = 61;
    public static final int SEMICOLON_TOKEN = 59;
    public static final int SLASH_TOKEN = 47;
    public static final int START_TOKEN = 1;
    public static final int STRING_TOKEN = 2;
    public static final int UNKNOWN_TOKEN;
    private char autoquoteChar;
    private int currentToken;
    private String currentTokenValue;
    private String data;
    private int dataIndex;
    private int dataLength;
    private boolean isAutoquoting;
}
