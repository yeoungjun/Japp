// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.internet;

import java.text.ParseException;

class MailDateParser
{

    public MailDateParser(char ac[])
    {
        index = 0;
        orig = null;
        orig = ac;
    }

    int getIndex()
    {
        return index;
    }

    public int parseAlphaTimeZone()
        throws ParseException
    {
        boolean flag = false;
        char ac[];
        int i;
        ac = orig;
        i = index;
        index = i + 1;
        ac[i];
        JVM INSTR lookupswitch 12: default 132
    //                   67: 428
    //                   69: 418
    //                   71: 319
    //                   77: 438
    //                   80: 448
    //                   85: 161
    //                   99: 428
    //                   101: 418
    //                   103: 319
    //                   109: 438
    //                   112: 448
    //                   117: 161;
           goto _L1 _L2 _L3 _L4 _L5 _L6 _L7 _L2 _L3 _L4 _L5 _L6 _L7
_L1:
        ArrayIndexOutOfBoundsException arrayindexoutofboundsexception;
        throw new ParseException("Bad Alpha TimeZone", index);
_L7:
        char c5;
        try
        {
            char ac6[] = orig;
            int l1 = index;
            index = l1 + 1;
            c5 = ac6[l1];
        }
        // Misplaced declaration of an exception variable
        catch(ArrayIndexOutOfBoundsException arrayindexoutofboundsexception)
        {
            throw new ParseException("Bad Alpha TimeZone", index);
        }
        if(c5 != 'T' && c5 != 't') goto _L9; else goto _L8
_L8:
        int j = 0;
_L21:
        if(!flag) goto _L11; else goto _L10
_L10:
        char c;
        char ac1[] = orig;
        int k = index;
        index = k + 1;
        c = ac1[k];
        if(c != 'S' && c != 's') goto _L13; else goto _L12
_L12:
        char ac2[] = orig;
        int l = index;
        index = l + 1;
        char c1 = ac2[l];
        if(c1 != 'T' && c1 != 't')
            throw new ParseException("Bad Alpha TimeZone", index);
          goto _L11
_L9:
        throw new ParseException("Bad Alpha TimeZone", index);
_L4:
        char c3;
        char ac4[] = orig;
        int j1 = index;
        index = j1 + 1;
        c3 = ac4[j1];
        if(c3 != 'M' && c3 != 'm') goto _L15; else goto _L14
_L14:
        char c4;
        char ac5[] = orig;
        int k1 = index;
        index = k1 + 1;
        c4 = ac5[k1];
        if(c4 != 'T' && c4 != 't') goto _L15; else goto _L16
_L15:
        throw new ParseException("Bad Alpha TimeZone", index);
_L3:
        j = 300;
        flag = true;
        continue; /* Loop/switch isn't completed */
_L2:
        j = 360;
        flag = true;
        continue; /* Loop/switch isn't completed */
_L5:
        j = 420;
        flag = true;
        continue; /* Loop/switch isn't completed */
_L6:
        j = 480;
        flag = true;
        continue; /* Loop/switch isn't completed */
_L13:
        if(c != 'D' && c != 'd') goto _L11; else goto _L17
_L17:
        char c2;
        char ac3[] = orig;
        int i1 = index;
        index = i1 + 1;
        c2 = ac3[i1];
        if(c2 != 'T' && c2 == 't') goto _L19; else goto _L18
_L18:
        j -= 60;
_L11:
        return j;
_L19:
        throw new ParseException("Bad Alpha TimeZone", index);
_L16:
        flag = false;
        j = 0;
        if(true) goto _L21; else goto _L20
_L20:
    }

    public int parseMonth()
        throws ParseException
    {
        char c;
        char c9;
        char c13;
        char ac16[];
        int i4;
        char c16;
        char c17;
        try
        {
            char ac[] = orig;
            int i = index;
            index = i + 1;
            c = ac[i];
        }
        catch(ArrayIndexOutOfBoundsException arrayindexoutofboundsexception)
        {
            break; /* Loop/switch isn't completed */
        }
        c;
        JVM INSTR lookupswitch 16: default 164
    //                   65: 502
    //                   68: 886
    //                   70: 332
    //                   74: 178
    //                   77: 417
    //                   78: 801
    //                   79: 716
    //                   83: 631
    //                   97: 502
    //                   100: 886
    //                   102: 332
    //                   106: 178
    //                   109: 417
    //                   110: 801
    //                   111: 716
    //                   115: 631;
           goto _L1 _L2 _L3 _L4 _L5 _L6 _L7 _L8 _L9 _L2 _L3 _L4 _L5 _L6 _L7 _L8 _L9
_L1:
        throw new ParseException("Bad Month", index);
_L5:
        ac16 = orig;
        i4 = index;
        index = i4 + 1;
        ac16[i4];
        JVM INSTR lookupswitch 4: default 975
    //                   65: 244
    //                   85: 288
    //                   97: 244
    //                   117: 288;
           goto _L1 _L10 _L11 _L10 _L11
_L10:
        char ac18[] = orig;
        int k4 = index;
        index = k4 + 1;
        c17 = ac18[k4];
        if(c17 != 'N' && c17 != 'n') goto _L1; else goto _L12
_L11:
        char ac17[] = orig;
        int j4 = index;
        index = j4 + 1;
        c16 = ac17[j4];
        char ac1[];
        int j;
        char c1;
        char ac2[];
        int k;
        char c2;
        char ac3[];
        int l;
        char c3;
        char ac4[];
        int i1;
        char c4;
        char ac5[];
        int j1;
        char c5;
        char ac6[];
        int k1;
        char c6;
        char ac7[];
        int l1;
        char c7;
        char ac8[];
        int i2;
        char c8;
        char ac9[];
        int j2;
        char ac10[];
        int k2;
        char c10;
        char ac11[];
        int l2;
        char c11;
        char ac12[];
        int i3;
        char c12;
        char ac13[];
        int j3;
        char ac14[];
        int k3;
        char c14;
        char ac15[];
        int l3;
        char c15;
        if(c16 == 'N' || c16 == 'n')
            return 5;
        continue; /* Loop/switch isn't completed */
_L4:
        ac14 = orig;
        k3 = index;
        index = k3 + 1;
        c14 = ac14[k3];
        if(c14 != 'E' && c14 != 'e') goto _L1; else goto _L13
_L13:
        ac15 = orig;
        l3 = index;
        index = l3 + 1;
        c15 = ac15[l3];
        if(c15 != 'B' && c15 != 'b') goto _L1; else goto _L14
_L6:
        ac12 = orig;
        i3 = index;
        index = i3 + 1;
        c12 = ac12[i3];
        if(c12 != 'A' && c12 != 'a') goto _L1; else goto _L15
_L15:
        ac13 = orig;
        j3 = index;
        index = j3 + 1;
        c13 = ac13[j3];
        if(c13 == 'R' || c13 == 'r')
            return 2;
        continue; /* Loop/switch isn't completed */
_L2:
        ac9 = orig;
        j2 = index;
        index = j2 + 1;
        c9 = ac9[j2];
        if(c9 != 'P' && c9 != 'p') goto _L17; else goto _L16
_L16:
        ac10 = orig;
        k2 = index;
        index = k2 + 1;
        c10 = ac10[k2];
        if(c10 != 'R' && c10 != 'r') goto _L1; else goto _L18
_L23:
        ac11 = orig;
        l2 = index;
        index = l2 + 1;
        c11 = ac11[l2];
        if(c11 != 'G' && c11 != 'g')
            break; /* Loop/switch isn't completed */
        else
            return 7;
_L9:
        ac7 = orig;
        l1 = index;
        index = l1 + 1;
        c7 = ac7[l1];
        if(c7 != 'E' && c7 != 'e')
            break; /* Loop/switch isn't completed */
        ac8 = orig;
        i2 = index;
        index = i2 + 1;
        c8 = ac8[i2];
        if(c8 != 'P' && c8 != 'p')
            break; /* Loop/switch isn't completed */
        else
            return 8;
_L8:
        ac5 = orig;
        j1 = index;
        index = j1 + 1;
        c5 = ac5[j1];
        if(c5 != 'C' && c5 != 'c')
            break; /* Loop/switch isn't completed */
        ac6 = orig;
        k1 = index;
        index = k1 + 1;
        c6 = ac6[k1];
        if(c6 != 'T' && c6 != 't')
            break; /* Loop/switch isn't completed */
        else
            return 9;
_L7:
        ac3 = orig;
        l = index;
        index = l + 1;
        c3 = ac3[l];
        if(c3 != 'O' && c3 != 'o')
            break; /* Loop/switch isn't completed */
        ac4 = orig;
        i1 = index;
        index = i1 + 1;
        c4 = ac4[i1];
        if(c4 != 'V' && c4 != 'v')
            break; /* Loop/switch isn't completed */
        else
            return 10;
_L3:
        ac1 = orig;
        j = index;
        index = j + 1;
        c1 = ac1[j];
        if(c1 != 'E' && c1 != 'e')
            break; /* Loop/switch isn't completed */
        ac2 = orig;
        k = index;
        index = k + 1;
        c2 = ac2[k];
        if(c2 == 'C' || c2 == 'c')
            return 11;
        else
            break; /* Loop/switch isn't completed */
_L12:
        return 0;
        if(c16 != 'L' && c16 != 'l') goto _L20; else goto _L19
_L20:
        break; /* Loop/switch isn't completed */
_L19:
        return 6;
_L14:
        return 1;
        if(c13 != 'Y' && c13 != 'y') goto _L22; else goto _L21
_L22:
        break; /* Loop/switch isn't completed */
_L21:
        return 4;
_L18:
        return 3;
_L17:
        if(c9 != 'U' && c9 != 'u') goto _L1; else goto _L23
    }

    public int parseNumber()
        throws ParseException
    {
        int i;
        boolean flag;
        int j;
        i = orig.length;
        flag = false;
        j = 0;
_L16:
        if(index < i) goto _L2; else goto _L1
_L1:
        if(!flag) goto _L4; else goto _L3
_L3:
        return j;
_L2:
        orig[index];
        JVM INSTR tableswitch 48 57: default 88
    //                   48 106
    //                   49 126
    //                   50 138
    //                   51 150
    //                   52 162
    //                   53 174
    //                   54 186
    //                   55 199
    //                   56 212
    //                   57 225;
           goto _L5 _L6 _L7 _L8 _L9 _L10 _L11 _L12 _L13 _L14 _L15
_L5:
        if(!flag)
            throw new ParseException("No Number found", index);
          goto _L3
_L6:
        j *= 10;
        flag = true;
_L17:
        index = 1 + index;
          goto _L16
_L7:
        j = 1 + j * 10;
        flag = true;
          goto _L17
_L8:
        j = 2 + j * 10;
        flag = true;
          goto _L17
_L9:
        j = 3 + j * 10;
        flag = true;
          goto _L17
_L10:
        j = 4 + j * 10;
        flag = true;
          goto _L17
_L11:
        j = 5 + j * 10;
        flag = true;
          goto _L17
_L12:
        j = 6 + j * 10;
        flag = true;
          goto _L17
_L13:
        j = 7 + j * 10;
        flag = true;
          goto _L17
_L14:
        j = 8 + j * 10;
        flag = true;
          goto _L17
_L15:
        j = 9 + j * 10;
        flag = true;
          goto _L17
_L4:
        throw new ParseException("No Number found", index);
    }

    public int parseNumericTimeZone()
        throws ParseException
    {
        char ac[] = orig;
        int i = index;
        index = i + 1;
        char c = ac[i];
        boolean flag;
        int j;
        int k;
        if(c == '+')
        {
            flag = true;
        } else
        {
            flag = false;
            if(c != '-')
                throw new ParseException("Bad Numeric TimeZone", index);
        }
        j = parseNumber();
        k = 60 * (j / 100) + j % 100;
        if(flag)
            k = -k;
        return k;
    }

    public int parseTimeZone()
        throws ParseException
    {
        if(index >= orig.length)
            throw new ParseException("No more characters", index);
        char c = orig[index];
        if(c == '+' || c == '-')
            return parseNumericTimeZone();
        else
            return parseAlphaTimeZone();
    }

    public int peekChar()
        throws ParseException
    {
        if(index < orig.length)
            return orig[index];
        else
            throw new ParseException("No more characters", index);
    }

    public void skipChar(char c)
        throws ParseException
    {
        if(index < orig.length)
        {
            if(orig[index] == c)
            {
                index = 1 + index;
                return;
            } else
            {
                throw new ParseException("Wrong char", index);
            }
        } else
        {
            throw new ParseException("No more characters", index);
        }
    }

    public boolean skipIfChar(char c)
        throws ParseException
    {
        if(index < orig.length)
        {
            if(orig[index] == c)
            {
                index = 1 + index;
                return true;
            } else
            {
                return false;
            }
        } else
        {
            throw new ParseException("No more characters", index);
        }
    }

    public void skipUntilNumber()
        throws ParseException
    {
label0:
        do
            try
            {
                switch(orig[index])
                {
                default:
                    index = 1 + index;
                    break;

                case 48: // '0'
                case 49: // '1'
                case 50: // '2'
                case 51: // '3'
                case 52: // '4'
                case 53: // '5'
                case 54: // '6'
                case 55: // '7'
                case 56: // '8'
                case 57: // '9'
                    break label0;
                }
            }
            catch(ArrayIndexOutOfBoundsException arrayindexoutofboundsexception)
            {
                throw new ParseException("No Number Found", index);
            }
        while(true);
    }

    public void skipWhiteSpace()
    {
        int i = orig.length;
        do
        {
            if(index >= i)
                return;
            switch(orig[index])
            {
            default:
                return;

            case 9: // '\t'
            case 10: // '\n'
            case 13: // '\r'
            case 32: // ' '
                index = 1 + index;
                break;
            }
        } while(true);
    }

    int index;
    char orig[];
}
