// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.activation;

import java.io.*;
import java.util.Locale;

// Referenced classes of package javax.activation:
//            MimeTypeParameterList, MimeTypeParseException

public class MimeType
    implements Externalizable
{

    public MimeType()
    {
        primaryType = "application";
        subType = "*";
        parameters = new MimeTypeParameterList();
    }

    public MimeType(String s)
        throws MimeTypeParseException
    {
        parse(s);
    }

    public MimeType(String s, String s1)
        throws MimeTypeParseException
    {
        if(isValidToken(s))
        {
            primaryType = s.toLowerCase(Locale.ENGLISH);
            if(isValidToken(s1))
            {
                subType = s1.toLowerCase(Locale.ENGLISH);
                parameters = new MimeTypeParameterList();
                return;
            } else
            {
                throw new MimeTypeParseException("Sub type is invalid.");
            }
        } else
        {
            throw new MimeTypeParseException("Primary type is invalid.");
        }
    }

    private static boolean isTokenChar(char c)
    {
        return c > ' ' && c < '\177' && "()<>@,;:/[]?=\\\"".indexOf(c) < 0;
    }

    private boolean isValidToken(String s)
    {
        int i;
        boolean flag;
        i = s.length();
        flag = false;
        if(i <= 0) goto _L2; else goto _L1
_L1:
        int j = 0;
_L7:
        if(j < i) goto _L4; else goto _L3
_L3:
        flag = true;
_L2:
        return flag;
_L4:
        boolean flag1;
        flag1 = isTokenChar(s.charAt(j));
        flag = false;
        if(!flag1) goto _L2; else goto _L5
_L5:
        j++;
        if(true) goto _L7; else goto _L6
_L6:
    }

    private void parse(String s)
        throws MimeTypeParseException
    {
        int i = s.indexOf('/');
        int j = s.indexOf(';');
        if(i < 0 && j < 0)
            throw new MimeTypeParseException("Unable to find a sub type.");
        if(i < 0 && j >= 0)
            throw new MimeTypeParseException("Unable to find a sub type.");
        if(i >= 0 && j < 0)
        {
            primaryType = s.substring(0, i).trim().toLowerCase(Locale.ENGLISH);
            subType = s.substring(i + 1).trim().toLowerCase(Locale.ENGLISH);
            parameters = new MimeTypeParameterList();
        } else
        if(i < j)
        {
            primaryType = s.substring(0, i).trim().toLowerCase(Locale.ENGLISH);
            subType = s.substring(i + 1, j).trim().toLowerCase(Locale.ENGLISH);
            parameters = new MimeTypeParameterList(s.substring(j));
        } else
        {
            throw new MimeTypeParseException("Unable to find a sub type.");
        }
        if(!isValidToken(primaryType))
            throw new MimeTypeParseException("Primary type is invalid.");
        if(!isValidToken(subType))
            throw new MimeTypeParseException("Sub type is invalid.");
        else
            return;
    }

    public String getBaseType()
    {
        return (new StringBuilder(String.valueOf(primaryType))).append("/").append(subType).toString();
    }

    public String getParameter(String s)
    {
        return parameters.get(s);
    }

    public MimeTypeParameterList getParameters()
    {
        return parameters;
    }

    public String getPrimaryType()
    {
        return primaryType;
    }

    public String getSubType()
    {
        return subType;
    }

    public boolean match(String s)
        throws MimeTypeParseException
    {
        return match(new MimeType(s));
    }

    public boolean match(MimeType mimetype)
    {
        return primaryType.equals(mimetype.getPrimaryType()) && (subType.equals("*") || mimetype.getSubType().equals("*") || subType.equals(mimetype.getSubType()));
    }

    public void readExternal(ObjectInput objectinput)
        throws IOException, ClassNotFoundException
    {
        try
        {
            parse(objectinput.readUTF());
            return;
        }
        catch(MimeTypeParseException mimetypeparseexception)
        {
            throw new IOException(mimetypeparseexception.toString());
        }
    }

    public void removeParameter(String s)
    {
        parameters.remove(s);
    }

    public void setParameter(String s, String s1)
    {
        parameters.set(s, s1);
    }

    public void setPrimaryType(String s)
        throws MimeTypeParseException
    {
        if(!isValidToken(primaryType))
        {
            throw new MimeTypeParseException("Primary type is invalid.");
        } else
        {
            primaryType = s.toLowerCase(Locale.ENGLISH);
            return;
        }
    }

    public void setSubType(String s)
        throws MimeTypeParseException
    {
        if(!isValidToken(subType))
        {
            throw new MimeTypeParseException("Sub type is invalid.");
        } else
        {
            subType = s.toLowerCase(Locale.ENGLISH);
            return;
        }
    }

    public String toString()
    {
        return (new StringBuilder(String.valueOf(getBaseType()))).append(parameters.toString()).toString();
    }

    public void writeExternal(ObjectOutput objectoutput)
        throws IOException
    {
        objectoutput.writeUTF(toString());
        objectoutput.flush();
    }

    private static final String TSPECIALS = "()<>@,;:/[]?=\\\"";
    private MimeTypeParameterList parameters;
    private String primaryType;
    private String subType;
}
