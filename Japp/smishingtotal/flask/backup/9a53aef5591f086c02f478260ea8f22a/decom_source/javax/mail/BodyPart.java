// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail;


// Referenced classes of package javax.mail:
//            Part, Multipart

public abstract class BodyPart
    implements Part
{

    public BodyPart()
    {
    }

    public Multipart getParent()
    {
        return parent;
    }

    void setParent(Multipart multipart)
    {
        parent = multipart;
    }

    protected Multipart parent;
}
