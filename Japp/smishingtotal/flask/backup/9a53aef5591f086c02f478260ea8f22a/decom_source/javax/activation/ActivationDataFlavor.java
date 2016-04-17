// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.activation;

import myjava.awt.datatransfer.DataFlavor;

// Referenced classes of package javax.activation:
//            MimeTypeParseException, MimeType

public class ActivationDataFlavor extends DataFlavor
{

    public ActivationDataFlavor(Class class1, String s)
    {
        super(class1, s);
        mimeType = null;
        mimeObject = null;
        humanPresentableName = null;
        representationClass = null;
        mimeType = super.getMimeType();
        representationClass = class1;
        humanPresentableName = s;
    }

    public ActivationDataFlavor(Class class1, String s, String s1)
    {
        super(s, s1);
        mimeType = null;
        mimeObject = null;
        humanPresentableName = null;
        representationClass = null;
        mimeType = s;
        humanPresentableName = s1;
        representationClass = class1;
    }

    public ActivationDataFlavor(String s, String s1)
    {
        super(s, s1);
        mimeType = null;
        mimeObject = null;
        humanPresentableName = null;
        representationClass = null;
        mimeType = s;
        try
        {
            representationClass = Class.forName("java.io.InputStream");
        }
        catch(ClassNotFoundException classnotfoundexception) { }
        humanPresentableName = s1;
    }

    public boolean equals(DataFlavor dataflavor)
    {
        return isMimeTypeEqual(dataflavor) && dataflavor.getRepresentationClass() == representationClass;
    }

    public String getHumanPresentableName()
    {
        return humanPresentableName;
    }

    public String getMimeType()
    {
        return mimeType;
    }

    public Class getRepresentationClass()
    {
        return representationClass;
    }

    public boolean isMimeTypeEqual(String s)
    {
        MimeType mimetype;
        try
        {
            if(mimeObject == null)
                mimeObject = new MimeType(mimeType);
            mimetype = new MimeType(s);
        }
        catch(MimeTypeParseException mimetypeparseexception)
        {
            return mimeType.equalsIgnoreCase(s);
        }
        return mimeObject.match(mimetype);
    }

    protected String normalizeMimeType(String s)
    {
        return s;
    }

    protected String normalizeMimeTypeParameter(String s, String s1)
    {
        return s1;
    }

    public void setHumanPresentableName(String s)
    {
        humanPresentableName = s;
    }

    private String humanPresentableName;
    private MimeType mimeObject;
    private String mimeType;
    private Class representationClass;
}
