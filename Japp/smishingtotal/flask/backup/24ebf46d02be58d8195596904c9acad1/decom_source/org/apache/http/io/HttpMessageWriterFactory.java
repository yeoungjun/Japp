// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.io;


// Referenced classes of package org.apache.http.io:
//            SessionOutputBuffer, HttpMessageWriter

public interface HttpMessageWriterFactory
{

    public abstract HttpMessageWriter create(SessionOutputBuffer sessionoutputbuffer);
}
