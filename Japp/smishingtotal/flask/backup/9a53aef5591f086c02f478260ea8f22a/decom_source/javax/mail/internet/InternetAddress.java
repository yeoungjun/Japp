// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.internet;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import javax.mail.Address;
import javax.mail.Session;

// Referenced classes of package javax.mail.internet:
//            AddressException, MimeUtility

public class InternetAddress extends Address
    implements Cloneable
{

    public InternetAddress()
    {
    }

    public InternetAddress(String s)
        throws AddressException
    {
        InternetAddress ainternetaddress[] = parse(s, true);
        if(ainternetaddress.length != 1)
        {
            throw new AddressException("Illegal address", s);
        } else
        {
            address = ainternetaddress[0].address;
            personal = ainternetaddress[0].personal;
            encodedPersonal = ainternetaddress[0].encodedPersonal;
            return;
        }
    }

    public InternetAddress(String s, String s1)
        throws UnsupportedEncodingException
    {
        this(s, s1, null);
    }

    public InternetAddress(String s, String s1, String s2)
        throws UnsupportedEncodingException
    {
        address = s;
        setPersonal(s1, s2);
    }

    public InternetAddress(String s, boolean flag)
        throws AddressException
    {
        this(s);
        if(flag)
            checkAddress(address, true, true);
    }

    private static void checkAddress(String s, boolean flag, boolean flag1)
        throws AddressException
    {
        if(s.indexOf('"') < 0) goto _L2; else goto _L1
_L1:
        return;
_L2:
        int i = 0;
        if(!flag) goto _L4; else goto _L3
_L3:
        i = 0;
_L6:
        int j;
        int k;
        k = indexOfAny(s, ",:", i);
        if(k >= 0)
        {
label0:
            {
                if(s.charAt(i) != '@')
                    throw new AddressException("Illegal route-addr", s);
                if(s.charAt(k) != ':')
                    break label0;
                i = k + 1;
            }
        }
_L4:
        j = s.indexOf('@', i);
        if(j >= 0)
        {
            if(j == i)
                throw new AddressException("Missing local name", s);
            break MISSING_BLOCK_LABEL_111;
        }
        break; /* Loop/switch isn't completed */
        i = k + 1;
        if(true) goto _L6; else goto _L5
        String s1;
        String s2;
        if(j == -1 + s.length())
            throw new AddressException("Missing domain", s);
        s1 = s.substring(i, j);
        s2 = s.substring(j + 1);
_L8:
        if(indexOfAny(s, " \t\n\r") >= 0)
            throw new AddressException("Illegal whitespace in address", s);
        break; /* Loop/switch isn't completed */
_L5:
        if(flag1)
            throw new AddressException("Missing final '@domain'", s);
        s1 = s;
        s2 = null;
        if(true) goto _L8; else goto _L7
_L7:
        if(indexOfAny(s1, "()<>,;:\\\"[]@") >= 0)
            throw new AddressException("Illegal character in local name", s);
        if(s2 != null && s2.indexOf('[') < 0 && indexOfAny(s2, "()<>,;:\\\"[]@") >= 0)
            throw new AddressException("Illegal character in domain", s);
        if(true) goto _L1; else goto _L9
_L9:
    }

    public static InternetAddress getLocalAddress(Session session)
    {
        String s = null;
        if(session != null) goto _L2; else goto _L1
_L1:
        String s1;
        String s2;
        s2 = System.getProperty("user.name");
        s1 = InetAddress.getLocalHost().getHostName();
_L4:
        if(s != null || s2 == null)
            break MISSING_BLOCK_LABEL_77;
        if(s2.length() == 0 || s1 == null)
            break MISSING_BLOCK_LABEL_77;
        if(s1.length() != 0)
            s = (new StringBuilder(String.valueOf(s2))).append("@").append(s1).toString();
        if(s == null)
            break MISSING_BLOCK_LABEL_203;
        return new InternetAddress(s);
_L2:
        s = session.getProperty("mail.from");
        s1 = null;
        s2 = null;
        if(s != null) goto _L4; else goto _L3
_L3:
        s2 = session.getProperty("mail.user");
        if(s2 == null)
            break MISSING_BLOCK_LABEL_128;
        if(s2.length() != 0)
            break MISSING_BLOCK_LABEL_136;
        s2 = session.getProperty("user.name");
        if(s2 == null)
            break MISSING_BLOCK_LABEL_149;
        if(s2.length() != 0)
            break MISSING_BLOCK_LABEL_156;
        s2 = System.getProperty("user.name");
        s1 = session.getProperty("mail.host");
        if(s1 == null) goto _L6; else goto _L5
_L5:
        if(s1.length() != 0) goto _L4; else goto _L6
_L6:
        InetAddress inetaddress = InetAddress.getLocalHost();
        if(inetaddress == null) goto _L4; else goto _L7
_L7:
        String s3 = inetaddress.getHostName();
        s1 = s3;
          goto _L4
        UnknownHostException unknownhostexception;
        unknownhostexception;
_L9:
        return null;
        AddressException addressexception;
        addressexception;
        continue; /* Loop/switch isn't completed */
        SecurityException securityexception;
        securityexception;
        if(true) goto _L9; else goto _L8
_L8:
    }

    private static int indexOfAny(String s, String s1)
    {
        return indexOfAny(s, s1, 0);
    }

    private static int indexOfAny(String s, String s1, int i)
    {
        int j;
        int k;
        int l;
        try
        {
            j = s.length();
        }
        catch(StringIndexOutOfBoundsException stringindexoutofboundsexception)
        {
            return -1;
        }
        k = i;
          goto _L1
_L3:
        l = s1.indexOf(s.charAt(k));
        if(l >= 0)
            break MISSING_BLOCK_LABEL_48;
        k++;
_L1:
        if(k < j) goto _L3; else goto _L2
_L2:
        k = -1;
        return k;
    }

    private boolean isSimple()
    {
        return address == null || indexOfAny(address, "()<>,;:\\\"[]") < 0;
    }

    private static int lengthOfFirstSegment(String s)
    {
        int i = s.indexOf("\r\n");
        if(i != -1)
            return i;
        else
            return s.length();
    }

    private static int lengthOfLastSegment(String s, int i)
    {
        int j = s.lastIndexOf("\r\n");
        if(j != -1)
            return -2 + (s.length() - j);
        else
            return i + s.length();
    }

    public static InternetAddress[] parse(String s)
        throws AddressException
    {
        return parse(s, true);
    }

    public static InternetAddress[] parse(String s, boolean flag)
        throws AddressException
    {
        return parse(s, flag, false);
    }

    private static InternetAddress[] parse(String s, boolean flag, boolean flag1)
        throws AddressException
    {
        int i;
        int j;
        int k;
        boolean flag2;
        boolean flag3;
        boolean flag4;
        Vector vector;
        int l;
        int i1;
        int j1;
        i = -1;
        j = -1;
        k = s.length();
        flag2 = false;
        flag3 = false;
        flag4 = false;
        vector = new Vector();
        l = -1;
        i1 = l;
        j1 = 0;
_L12:
        if(j1 >= k)
        {
            if(i1 >= 0)
            {
                if(l == -1)
                    l = j1;
                String s3 = s.substring(i1, l).trim();
                AddressException addressexception;
                InternetAddress internetaddress;
                int k1;
                AddressException addressexception1;
                String s1;
                InternetAddress internetaddress1;
                StringTokenizer stringtokenizer;
                String s2;
                InternetAddress internetaddress2;
                AddressException addressexception2;
                AddressException addressexception3;
                AddressException addressexception4;
                AddressException addressexception5;
                boolean flag5;
                AddressException addressexception6;
                AddressException addressexception7;
                AddressException addressexception8;
                int l1;
                int i2;
                AddressException addressexception9;
                InternetAddress ainternetaddress[];
                if(flag4 || flag || flag1)
                {
                    if(flag || !flag1)
                        checkAddress(s3, flag3, false);
                    InternetAddress internetaddress3 = new InternetAddress();
                    internetaddress3.setAddress(s3);
                    if(i >= 0)
                        internetaddress3.encodedPersonal = unquote(s.substring(i, j).trim());
                    vector.addElement(internetaddress3);
                } else
                {
                    StringTokenizer stringtokenizer1 = new StringTokenizer(s3);
                    while(stringtokenizer1.hasMoreTokens()) 
                    {
                        String s4 = stringtokenizer1.nextToken();
                        checkAddress(s4, false, false);
                        InternetAddress internetaddress4 = new InternetAddress();
                        internetaddress4.setAddress(s4);
                        vector.addElement(internetaddress4);
                    }
                }
            }
            ainternetaddress = new InternetAddress[vector.size()];
            vector.copyInto(ainternetaddress);
            return ainternetaddress;
        }
        s.charAt(j1);
        JVM INSTR lookupswitch 13: default 288
    //                   9: 298
    //                   10: 298
    //                   13: 298
    //                   32: 298
    //                   34: 685
    //                   40: 304
    //                   41: 459
    //                   44: 852
    //                   58: 1063
    //                   59: 1104
    //                   60: 476
    //                   62: 668
    //                   91: 776;
           goto _L1 _L2 _L2 _L2 _L2 _L3 _L4 _L5 _L6 _L7 _L8 _L9 _L10 _L11
_L2:
        break; /* Loop/switch isn't completed */
_L1:
        if(i1 == -1)
            i1 = j1;
_L20:
        j1++;
          goto _L12
_L4:
        flag4 = true;
        if(i1 >= 0 && l == -1)
            l = j1;
        if(i == -1)
            i = j1 + 1;
        l1 = j1 + 1;
        i2 = 1;
_L18:
        if(l1 >= k || i2 <= 0)
        {
            if(i2 > 0)
            {
                addressexception9 = new AddressException("Missing ')'", s, l1);
                throw addressexception9;
            }
            break MISSING_BLOCK_LABEL_440;
        }
        s.charAt(l1);
        JVM INSTR lookupswitch 3: default 416
    //                   40: 428
    //                   41: 434
    //                   92: 422;
           goto _L13 _L14 _L15 _L16
_L15:
        break MISSING_BLOCK_LABEL_434;
_L13:
        break; /* Loop/switch isn't completed */
_L16:
        break; /* Loop/switch isn't completed */
_L19:
        l1++;
        if(true) goto _L18; else goto _L17
_L17:
        l1++;
          goto _L19
_L14:
        i2++;
          goto _L19
        i2--;
          goto _L19
        j1 = l1 - 1;
        if(j == -1)
            j = j1;
          goto _L20
_L5:
        addressexception8 = new AddressException("Missing '('", s, j1);
        throw addressexception8;
_L9:
        flag4 = true;
        if(flag3)
        {
            addressexception5 = new AddressException("Extra route-addr", s, j1);
            throw addressexception5;
        }
        if(!flag2)
        {
            i = i1;
            if(i >= 0)
                j = j1;
            i1 = j1 + 1;
        }
        flag5 = false;
        j1++;
_L27:
        if(j1 < k) goto _L22; else goto _L21
_L22:
        s.charAt(j1);
        JVM INSTR lookupswitch 3: default 604
    //                   34: 616
    //                   62: 633
    //                   92: 610;
           goto _L23 _L24 _L25 _L26
_L23:
        j1++;
          goto _L27
_L26:
        j1++;
          goto _L23
_L24:
        if(flag5)
            flag5 = false;
        else
            flag5 = true;
          goto _L23
_L25:
        if(!flag5) goto _L21; else goto _L23
_L21:
        if(j1 >= k)
            if(flag5)
            {
                addressexception6 = new AddressException("Missing '\"'", s, j1);
                throw addressexception6;
            } else
            {
                addressexception7 = new AddressException("Missing '>'", s, j1);
                throw addressexception7;
            }
        flag3 = true;
        l = j1;
          goto _L20
_L10:
        addressexception4 = new AddressException("Missing '<'", s, j1);
        throw addressexception4;
_L3:
        flag4 = true;
        if(i1 == -1)
            i1 = j1;
        j1++;
_L32:
        if(j1 < k) goto _L29; else goto _L28
_L28:
        if(j1 >= k)
        {
            addressexception3 = new AddressException("Missing '\"'", s, j1);
            throw addressexception3;
        }
          goto _L20
_L29:
        s.charAt(j1);
        JVM INSTR lookupswitch 2: default 764
    //                   34: 708
    //                   92: 770;
           goto _L30 _L28 _L31
_L30:
        j1++;
          goto _L32
_L31:
        j1++;
          goto _L30
_L11:
        flag4 = true;
        j1++;
_L37:
        if(j1 < k) goto _L34; else goto _L33
_L33:
        if(j1 >= k)
        {
            addressexception2 = new AddressException("Missing ']'", s, j1);
            throw addressexception2;
        }
          goto _L20
_L34:
        s.charAt(j1);
        JVM INSTR tableswitch 92 93: default 840
    //                   92 846
    //                   93 789;
           goto _L35 _L36 _L33
_L35:
        j1++;
          goto _L37
_L36:
        j1++;
          goto _L35
_L6:
        if(i1 == -1)
        {
            l = -1;
            i1 = l;
            flag4 = false;
            flag3 = false;
        } else
        if(flag2)
        {
            flag3 = false;
        } else
        {
            if(l == -1)
                l = j1;
            s1 = s.substring(i1, l).trim();
            if(flag4 || flag || flag1)
            {
                if(flag || !flag1)
                    checkAddress(s1, flag3, false);
                internetaddress1 = new InternetAddress();
                internetaddress1.setAddress(s1);
                if(i >= 0)
                {
                    internetaddress1.encodedPersonal = unquote(s.substring(i, j).trim());
                    j = -1;
                    i = j;
                }
                vector.addElement(internetaddress1);
            } else
            {
                stringtokenizer = new StringTokenizer(s1);
                while(stringtokenizer.hasMoreTokens()) 
                {
                    s2 = stringtokenizer.nextToken();
                    checkAddress(s2, false, false);
                    internetaddress2 = new InternetAddress();
                    internetaddress2.setAddress(s2);
                    vector.addElement(internetaddress2);
                }
            }
            l = -1;
            i1 = l;
            flag4 = false;
            flag3 = false;
        }
          goto _L20
_L7:
        flag4 = true;
        if(flag2)
        {
            addressexception1 = new AddressException("Nested group", s, j1);
            throw addressexception1;
        }
        flag2 = true;
        if(i1 == -1)
            i1 = j1;
          goto _L20
_L8:
        if(i1 == -1)
            i1 = j1;
        if(!flag2)
        {
            addressexception = new AddressException("Illegal semicolon, not in group", s, j1);
            throw addressexception;
        }
        if(i1 == -1)
            i1 = j1;
        internetaddress = new InternetAddress();
        k1 = j1 + 1;
        internetaddress.setAddress(s.substring(i1, k1).trim());
        vector.addElement(internetaddress);
        l = -1;
        i1 = l;
        flag2 = false;
        flag3 = false;
          goto _L20
    }

    public static InternetAddress[] parseHeader(String s, boolean flag)
        throws AddressException
    {
        return parse(s, flag, true);
    }

    private static String quotePhrase(String s)
    {
        int i = s.length();
        boolean flag = false;
        int j = 0;
        do
        {
            if(j >= i)
            {
                if(flag)
                {
                    StringBuffer stringbuffer1 = new StringBuffer(i + 2);
                    stringbuffer1.append('"').append(s).append('"');
                    s = stringbuffer1.toString();
                }
                return s;
            }
            char c = s.charAt(j);
            if(c == '"' || c == '\\')
            {
                StringBuffer stringbuffer = new StringBuffer(i + 3);
                stringbuffer.append('"');
                int k = 0;
                do
                {
                    if(k >= i)
                    {
                        stringbuffer.append('"');
                        return stringbuffer.toString();
                    }
                    char c1 = s.charAt(k);
                    if(c1 == '"' || c1 == '\\')
                        stringbuffer.append('\\');
                    stringbuffer.append(c1);
                    k++;
                } while(true);
            }
            if(c < ' ' && c != '\r' && c != '\n' && c != '\t' || c >= '\177' || rfc822phrase.indexOf(c) >= 0)
                flag = true;
            j++;
        } while(true);
    }

    public static String toString(Address aaddress[])
    {
        return toString(aaddress, 0);
    }

    public static String toString(Address aaddress[], int i)
    {
        if(aaddress == null || aaddress.length == 0)
            return null;
        StringBuffer stringbuffer = new StringBuffer();
        int j = 0;
        do
        {
            if(j >= aaddress.length)
                return stringbuffer.toString();
            if(j != 0)
            {
                stringbuffer.append(", ");
                i += 2;
            }
            String s = aaddress[j].toString();
            if(i + lengthOfFirstSegment(s) > 76)
            {
                stringbuffer.append("\r\n\t");
                i = 8;
            }
            stringbuffer.append(s);
            i = lengthOfLastSegment(s, i);
            j++;
        } while(true);
    }

    private static String unquote(String s)
    {
        if(!s.startsWith("\"") || !s.endsWith("\"")) goto _L2; else goto _L1
_L1:
        s = s.substring(1, -1 + s.length());
        if(s.indexOf('\\') < 0) goto _L2; else goto _L3
_L3:
        StringBuffer stringbuffer;
        int i;
        stringbuffer = new StringBuffer(s.length());
        i = 0;
_L7:
        if(i < s.length()) goto _L5; else goto _L4
_L4:
        s = stringbuffer.toString();
_L2:
        return s;
_L5:
        char c = s.charAt(i);
        if(c == '\\' && i < -1 + s.length())
        {
            i++;
            c = s.charAt(i);
        }
        stringbuffer.append(c);
        i++;
        if(true) goto _L7; else goto _L6
_L6:
    }

    public Object clone()
    {
        InternetAddress internetaddress;
        try
        {
            internetaddress = (InternetAddress)super.clone();
        }
        catch(CloneNotSupportedException clonenotsupportedexception)
        {
            return null;
        }
        return internetaddress;
    }

    public boolean equals(Object obj)
    {
        if(obj instanceof InternetAddress)
        {
            String s = ((InternetAddress)obj).getAddress();
            if(s == address)
                return true;
            if(address != null && address.equalsIgnoreCase(s))
                return true;
        }
        return false;
    }

    public String getAddress()
    {
        return address;
    }

    public InternetAddress[] getGroup(boolean flag)
        throws AddressException
    {
        String s = getAddress();
        int i;
        if(s.endsWith(";"))
            if((i = s.indexOf(':')) >= 0)
                return parseHeader(s.substring(i + 1, -1 + s.length()), flag);
        return null;
    }

    public String getPersonal()
    {
        if(personal != null)
            return personal;
        if(encodedPersonal != null)
        {
            String s;
            try
            {
                personal = MimeUtility.decodeText(encodedPersonal);
                s = personal;
            }
            catch(Exception exception)
            {
                return encodedPersonal;
            }
            return s;
        } else
        {
            return null;
        }
    }

    public String getType()
    {
        return "rfc822";
    }

    public int hashCode()
    {
        if(address == null)
            return 0;
        else
            return address.toLowerCase(Locale.ENGLISH).hashCode();
    }

    public boolean isGroup()
    {
        return address != null && address.endsWith(";") && address.indexOf(':') > 0;
    }

    public void setAddress(String s)
    {
        address = s;
    }

    public void setPersonal(String s)
        throws UnsupportedEncodingException
    {
        personal = s;
        if(s != null)
        {
            encodedPersonal = MimeUtility.encodeWord(s);
            return;
        } else
        {
            encodedPersonal = null;
            return;
        }
    }

    public void setPersonal(String s, String s1)
        throws UnsupportedEncodingException
    {
        personal = s;
        if(s != null)
        {
            encodedPersonal = MimeUtility.encodeWord(s, s1, null);
            return;
        } else
        {
            encodedPersonal = null;
            return;
        }
    }

    public String toString()
    {
        if(encodedPersonal == null && personal != null)
            try
            {
                encodedPersonal = MimeUtility.encodeWord(personal);
            }
            catch(UnsupportedEncodingException unsupportedencodingexception) { }
        if(encodedPersonal != null)
            return (new StringBuilder(String.valueOf(quotePhrase(encodedPersonal)))).append(" <").append(address).append(">").toString();
        if(isGroup() || isSimple())
            return address;
        else
            return (new StringBuilder("<")).append(address).append(">").toString();
    }

    public String toUnicodeString()
    {
        String s = getPersonal();
        if(s != null)
            return (new StringBuilder(String.valueOf(quotePhrase(s)))).append(" <").append(address).append(">").toString();
        if(isGroup() || isSimple())
            return address;
        else
            return (new StringBuilder("<")).append(address).append(">").toString();
    }

    public void validate()
        throws AddressException
    {
        checkAddress(getAddress(), true, true);
    }

    private static final String rfc822phrase = "()<>@,;:\\\"\t .[]".replace(' ', '\0').replace('\t', '\0');
    private static final long serialVersionUID = 0x97cfa9a447d75349L;
    private static final String specialsNoDot = "()<>,;:\\\"[]@";
    private static final String specialsNoDotNoAt = "()<>,;:\\\"[]";
    protected String address;
    protected String encodedPersonal;
    protected String personal;

}
