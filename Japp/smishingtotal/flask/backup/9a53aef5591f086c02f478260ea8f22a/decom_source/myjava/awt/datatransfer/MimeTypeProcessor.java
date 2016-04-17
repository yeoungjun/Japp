// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package myjava.awt.datatransfer;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

final class MimeTypeProcessor
{
    static final class MimeType
        implements Cloneable, Serializable
    {

        void addParameter(String s, String s1)
        {
            if(s1 != null)
            {
                if(s1.charAt(0) == '"' && s1.charAt(-1 + s1.length()) == '"')
                    s1 = s1.substring(1, -2 + s1.length());
                if(s1.length() != 0)
                {
                    parameters.put(s, s1);
                    return;
                }
            }
        }

        void addSystemParameter(String s, Object obj)
        {
            systemParameters.put(s, obj);
        }

        public Object clone()
        {
            MimeType mimetype = new MimeType(primaryType, subType);
            mimetype.parameters = (Hashtable)parameters.clone();
            mimetype.systemParameters = (Hashtable)systemParameters.clone();
            return mimetype;
        }

        boolean equals(MimeType mimetype)
        {
            if(mimetype == null)
                return false;
            else
                return getFullType().equals(mimetype.getFullType());
        }

        String getFullType()
        {
            return (new StringBuilder(String.valueOf(primaryType))).append("/").append(subType).toString();
        }

        String getParameter(String s)
        {
            return (String)parameters.get(s);
        }

        String getPrimaryType()
        {
            return primaryType;
        }

        String getSubType()
        {
            return subType;
        }

        Object getSystemParameter(String s)
        {
            return systemParameters.get(s);
        }

        void removeParameter(String s)
        {
            parameters.remove(s);
        }

        private static final long serialVersionUID = 0xa31ba7d0705c1214L;
        private Hashtable parameters;
        private String primaryType;
        private String subType;
        private Hashtable systemParameters;






        MimeType()
        {
            primaryType = null;
            subType = null;
            parameters = null;
            systemParameters = null;
        }

        MimeType(String s, String s1)
        {
            primaryType = s;
            subType = s1;
            parameters = new Hashtable();
            systemParameters = new Hashtable();
        }
    }

    private static final class StringPosition
    {

        int i;

        private StringPosition()
        {
            i = 0;
        }

        StringPosition(StringPosition stringposition)
        {
            this();
        }
    }


    private MimeTypeProcessor()
    {
    }

    static String assemble(MimeType mimetype)
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(mimetype.getFullType());
        Enumeration enumeration = mimetype.parameters.keys();
        do
        {
            if(!enumeration.hasMoreElements())
                return stringbuilder.toString();
            String s = (String)enumeration.nextElement();
            String s1 = (String)mimetype.parameters.get(s);
            stringbuilder.append("; ");
            stringbuilder.append(s);
            stringbuilder.append("=\"");
            stringbuilder.append(s1);
            stringbuilder.append('"');
        } while(true);
    }

    private static int getNextMeaningfulIndex(String s, int i)
    {
        do
        {
            if(i >= s.length() || isMeaningfulChar(s.charAt(i)))
                return i;
            i++;
        } while(true);
    }

    private static boolean isMeaningfulChar(char c)
    {
        return c >= '!' && c <= '~';
    }

    private static boolean isTSpecialChar(char c)
    {
        return c == '(' || c == ')' || c == '[' || c == ']' || c == '<' || c == '>' || c == '@' || c == ',' || c == ';' || c == ':' || c == '\\' || c == '"' || c == '/' || c == '?' || c == '=';
    }

    static MimeType parse(String s)
    {
        if(instance == null)
            instance = new MimeTypeProcessor();
        MimeType mimetype = new MimeType();
        if(s != null)
        {
            StringPosition stringposition = new StringPosition(null);
            retrieveType(s, mimetype, stringposition);
            retrieveParams(s, mimetype, stringposition);
        }
        return mimetype;
    }

    private static void retrieveParam(String s, MimeType mimetype, StringPosition stringposition)
    {
        String s1 = retrieveToken(s, stringposition).toLowerCase();
        stringposition.i = getNextMeaningfulIndex(s, stringposition.i);
        if(stringposition.i >= s.length() || s.charAt(stringposition.i) != '=')
            throw new IllegalArgumentException();
        stringposition.i = 1 + stringposition.i;
        stringposition.i = getNextMeaningfulIndex(s, stringposition.i);
        if(stringposition.i >= s.length())
            throw new IllegalArgumentException();
        String s2;
        if(s.charAt(stringposition.i) == '"')
            s2 = retrieveQuoted(s, stringposition);
        else
            s2 = retrieveToken(s, stringposition);
        mimetype.parameters.put(s1, s2);
    }

    private static void retrieveParams(String s, MimeType mimetype, StringPosition stringposition)
    {
        mimetype.parameters = new Hashtable();
        mimetype.systemParameters = new Hashtable();
        do
        {
            stringposition.i = getNextMeaningfulIndex(s, stringposition.i);
            if(stringposition.i >= s.length())
                return;
            if(s.charAt(stringposition.i) != ';')
                throw new IllegalArgumentException();
            stringposition.i = 1 + stringposition.i;
            retrieveParam(s, mimetype, stringposition);
        } while(true);
    }

    private static String retrieveQuoted(String s, StringPosition stringposition)
    {
        StringBuilder stringbuilder = new StringBuilder();
        boolean flag = true;
        stringposition.i = 1 + stringposition.i;
        do
        {
            if(s.charAt(stringposition.i) == '"' && flag)
            {
                stringposition.i = 1 + stringposition.i;
                return stringbuilder.toString();
            }
            int i = stringposition.i;
            stringposition.i = i + 1;
            char c = s.charAt(i);
            if(!flag)
                flag = true;
            else
            if(c == '\\')
                flag = false;
            if(flag)
                stringbuilder.append(c);
        } while(stringposition.i != s.length());
        throw new IllegalArgumentException();
    }

    private static String retrieveToken(String s, StringPosition stringposition)
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringposition.i = getNextMeaningfulIndex(s, stringposition.i);
        if(stringposition.i >= s.length() || isTSpecialChar(s.charAt(stringposition.i)))
            throw new IllegalArgumentException();
        do
        {
            int i = stringposition.i;
            stringposition.i = i + 1;
            stringbuilder.append(s.charAt(i));
        } while(stringposition.i < s.length() && isMeaningfulChar(s.charAt(stringposition.i)) && !isTSpecialChar(s.charAt(stringposition.i)));
        return stringbuilder.toString();
    }

    private static void retrieveType(String s, MimeType mimetype, StringPosition stringposition)
    {
        mimetype.primaryType = retrieveToken(s, stringposition).toLowerCase();
        stringposition.i = getNextMeaningfulIndex(s, stringposition.i);
        if(stringposition.i >= s.length() || s.charAt(stringposition.i) != '/')
        {
            throw new IllegalArgumentException();
        } else
        {
            stringposition.i = 1 + stringposition.i;
            mimetype.subType = retrieveToken(s, stringposition).toLowerCase();
            return;
        }
    }

    private static MimeTypeProcessor instance;
}
