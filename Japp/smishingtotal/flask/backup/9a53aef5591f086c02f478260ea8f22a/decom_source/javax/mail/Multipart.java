// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

// Referenced classes of package javax.mail:
//            MessagingException, BodyPart, MultipartDataSource, Part

public abstract class Multipart
{

    protected Multipart()
    {
        parts = new Vector();
        contentType = "multipart/mixed";
    }

    public void addBodyPart(BodyPart bodypart)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        if(parts == null)
            parts = new Vector();
        parts.addElement(bodypart);
        bodypart.setParent(this);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void addBodyPart(BodyPart bodypart, int i)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        if(parts == null)
            parts = new Vector();
        parts.insertElementAt(bodypart, i);
        bodypart.setParent(this);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public BodyPart getBodyPart(int i)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        if(parts == null)
            throw new IndexOutOfBoundsException("No such BodyPart");
        break MISSING_BLOCK_LABEL_24;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        BodyPart bodypart = (BodyPart)parts.elementAt(i);
        this;
        JVM INSTR monitorexit ;
        return bodypart;
    }

    public String getContentType()
    {
        return contentType;
    }

    public int getCount()
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        Vector vector = parts;
        if(vector != null) goto _L2; else goto _L1
_L1:
        int j = 0;
_L4:
        this;
        JVM INSTR monitorexit ;
        return j;
_L2:
        int i = parts.size();
        j = i;
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
        throw exception;
    }

    public Part getParent()
    {
        this;
        JVM INSTR monitorenter ;
        Part part = parent;
        this;
        JVM INSTR monitorexit ;
        return part;
        Exception exception;
        exception;
        throw exception;
    }

    public void removeBodyPart(int i)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        if(parts == null)
            throw new IndexOutOfBoundsException("No such BodyPart");
        break MISSING_BLOCK_LABEL_24;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        BodyPart bodypart = (BodyPart)parts.elementAt(i);
        parts.removeElementAt(i);
        bodypart.setParent(null);
        this;
        JVM INSTR monitorexit ;
    }

    public boolean removeBodyPart(BodyPart bodypart)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        if(parts == null)
            throw new MessagingException("No such body part");
        break MISSING_BLOCK_LABEL_24;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        boolean flag;
        flag = parts.removeElement(bodypart);
        bodypart.setParent(null);
        this;
        JVM INSTR monitorexit ;
        return flag;
    }

    protected void setMultipartDataSource(MultipartDataSource multipartdatasource)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        int i;
        contentType = multipartdatasource.getContentType();
        i = multipartdatasource.getCount();
        int j = 0;
_L2:
        if(j >= i)
            return;
        addBodyPart(multipartdatasource.getBodyPart(j));
        j++;
        if(true) goto _L2; else goto _L1
_L1:
        Exception exception;
        exception;
        throw exception;
    }

    public void setParent(Part part)
    {
        this;
        JVM INSTR monitorenter ;
        parent = part;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public abstract void writeTo(OutputStream outputstream)
        throws IOException, MessagingException;

    protected String contentType;
    protected Part parent;
    protected Vector parts;
}
