// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.search;

import javax.mail.*;

// Referenced classes of package javax.mail.search:
//            StringTerm

public final class BodyTerm extends StringTerm
{

    public BodyTerm(String s)
    {
        super(s);
    }

    private boolean matchPart(Part part)
    {
        String s;
        if(!part.isMimeType("text/*"))
            break MISSING_BLOCK_LABEL_36;
        s = (String)part.getContent();
        if(s == null)
            return false;
        return super.match(s);
        if(!part.isMimeType("multipart/*")) goto _L2; else goto _L1
_L1:
        Multipart multipart;
        int i;
        multipart = (Multipart)part.getContent();
        i = multipart.getCount();
        int j = 0;
          goto _L3
_L2:
        boolean flag;
        try
        {
            if(!part.isMimeType("message/rfc822"))
                break; /* Loop/switch isn't completed */
            flag = matchPart((Part)part.getContent());
        }
        catch(Exception exception)
        {
            break; /* Loop/switch isn't completed */
        }
        return flag;
_L3:
        if(j < i) goto _L5; else goto _L4
_L4:
        return false;
_L5:
        if(matchPart(((Part) (multipart.getBodyPart(j)))))
            return true;
        j++;
          goto _L3
        if(true) goto _L5; else goto _L6
_L6:
    }

    public boolean equals(Object obj)
    {
        if(!(obj instanceof BodyTerm))
            return false;
        else
            return super.equals(obj);
    }

    public boolean match(Message message)
    {
        return matchPart(message);
    }

    private static final long serialVersionUID = 0xbc27456ee3cb54e7L;
}
