// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.message;

import org.apache.http.HeaderElement;
import org.apache.http.NameValuePair;
import org.apache.http.util.Args;
import org.apache.http.util.LangUtils;

public class BasicHeaderElement
    implements HeaderElement, Cloneable
{

    public BasicHeaderElement(String s, String s1)
    {
        this(s, s1, null);
    }

    public BasicHeaderElement(String s, String s1, NameValuePair anamevaluepair[])
    {
        name = (String)Args.notNull(s, "Name");
        value = s1;
        if(anamevaluepair != null)
        {
            parameters = anamevaluepair;
            return;
        } else
        {
            parameters = new NameValuePair[0];
            return;
        }
    }

    public Object clone()
        throws CloneNotSupportedException
    {
        return super.clone();
    }

    public boolean equals(Object obj)
    {
        BasicHeaderElement basicheaderelement;
        if(this != obj)
            if(obj instanceof HeaderElement)
            {
                if(!name.equals((basicheaderelement = (BasicHeaderElement)obj).name) || !LangUtils.equals(value, basicheaderelement.value) || !LangUtils.equals(parameters, basicheaderelement.parameters))
                    return false;
            } else
            {
                return false;
            }
        return true;
    }

    public String getName()
    {
        return name;
    }

    public NameValuePair getParameter(int i)
    {
        return parameters[i];
    }

    public NameValuePair getParameterByName(String s)
    {
        Args.notNull(s, "Name");
        NameValuePair anamevaluepair[] = parameters;
        int i = anamevaluepair.length;
        int j = 0;
        do
        {
label0:
            {
                NameValuePair namevaluepair = null;
                if(j < i)
                {
                    NameValuePair namevaluepair1 = anamevaluepair[j];
                    if(!namevaluepair1.getName().equalsIgnoreCase(s))
                        break label0;
                    namevaluepair = namevaluepair1;
                }
                return namevaluepair;
            }
            j++;
        } while(true);
    }

    public int getParameterCount()
    {
        return parameters.length;
    }

    public NameValuePair[] getParameters()
    {
        return (NameValuePair[])parameters.clone();
    }

    public String getValue()
    {
        return value;
    }

    public int hashCode()
    {
        int i = LangUtils.hashCode(LangUtils.hashCode(17, name), value);
        NameValuePair anamevaluepair[] = parameters;
        int j = anamevaluepair.length;
        for(int k = 0; k < j; k++)
            i = LangUtils.hashCode(i, anamevaluepair[k]);

        return i;
    }

    public String toString()
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(name);
        if(value != null)
        {
            stringbuilder.append("=");
            stringbuilder.append(value);
        }
        NameValuePair anamevaluepair[] = parameters;
        int i = anamevaluepair.length;
        for(int j = 0; j < i; j++)
        {
            NameValuePair namevaluepair = anamevaluepair[j];
            stringbuilder.append("; ");
            stringbuilder.append(namevaluepair);
        }

        return stringbuilder.toString();
    }

    private final String name;
    private final NameValuePair parameters[];
    private final String value;
}
