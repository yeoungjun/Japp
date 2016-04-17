// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import java.io.*;
import org.apache.http.*;
import org.apache.http.entity.HttpEntityWrapper;

// Referenced classes of package org.apache.http.impl.client:
//            RequestWrapper

public class EntityEnclosingRequestWrapper extends RequestWrapper
    implements HttpEntityEnclosingRequest
{
    class EntityWrapper extends HttpEntityWrapper
    {

        public void consumeContent()
            throws IOException
        {
            consumed = true;
            super.consumeContent();
        }

        public InputStream getContent()
            throws IOException
        {
            consumed = true;
            return super.getContent();
        }

        public void writeTo(OutputStream outputstream)
            throws IOException
        {
            consumed = true;
            super.writeTo(outputstream);
        }

        final EntityEnclosingRequestWrapper this$0;

        EntityWrapper(HttpEntity httpentity)
        {
            this$0 = EntityEnclosingRequestWrapper.this;
            super(httpentity);
        }
    }


    public EntityEnclosingRequestWrapper(HttpEntityEnclosingRequest httpentityenclosingrequest)
        throws ProtocolException
    {
        super(httpentityenclosingrequest);
        setEntity(httpentityenclosingrequest.getEntity());
    }

    public boolean expectContinue()
    {
        Header header = getFirstHeader("Expect");
        return header != null && "100-continue".equalsIgnoreCase(header.getValue());
    }

    public HttpEntity getEntity()
    {
        return entity;
    }

    public boolean isRepeatable()
    {
        return entity == null || entity.isRepeatable() || !consumed;
    }

    public void setEntity(HttpEntity httpentity)
    {
        EntityWrapper entitywrapper;
        if(httpentity != null)
            entitywrapper = new EntityWrapper(httpentity);
        else
            entitywrapper = null;
        entity = entitywrapper;
        consumed = false;
    }

    private boolean consumed;
    private HttpEntity entity;


/*
    static boolean access$002(EntityEnclosingRequestWrapper entityenclosingrequestwrapper, boolean flag)
    {
        entityenclosingrequestwrapper.consumed = flag;
        return flag;
    }

*/
}
