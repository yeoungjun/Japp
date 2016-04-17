// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.conn.routing;

import java.net.InetAddress;
import org.apache.http.HttpHost;

public interface RouteInfo
{
    public static final class LayerType extends Enum
    {

        public static LayerType valueOf(String s)
        {
            return (LayerType)Enum.valueOf(org/apache/http/conn/routing/RouteInfo$LayerType, s);
        }

        public static LayerType[] values()
        {
            return (LayerType[])$VALUES.clone();
        }

        private static final LayerType $VALUES[];
        public static final LayerType LAYERED;
        public static final LayerType PLAIN;

        static 
        {
            PLAIN = new LayerType("PLAIN", 0);
            LAYERED = new LayerType("LAYERED", 1);
            LayerType alayertype[] = new LayerType[2];
            alayertype[0] = PLAIN;
            alayertype[1] = LAYERED;
            $VALUES = alayertype;
        }

        private LayerType(String s, int i)
        {
            super(s, i);
        }
    }

    public static final class TunnelType extends Enum
    {

        public static TunnelType valueOf(String s)
        {
            return (TunnelType)Enum.valueOf(org/apache/http/conn/routing/RouteInfo$TunnelType, s);
        }

        public static TunnelType[] values()
        {
            return (TunnelType[])$VALUES.clone();
        }

        private static final TunnelType $VALUES[];
        public static final TunnelType PLAIN;
        public static final TunnelType TUNNELLED;

        static 
        {
            PLAIN = new TunnelType("PLAIN", 0);
            TUNNELLED = new TunnelType("TUNNELLED", 1);
            TunnelType atunneltype[] = new TunnelType[2];
            atunneltype[0] = PLAIN;
            atunneltype[1] = TUNNELLED;
            $VALUES = atunneltype;
        }

        private TunnelType(String s, int i)
        {
            super(s, i);
        }
    }


    public abstract int getHopCount();

    public abstract HttpHost getHopTarget(int i);

    public abstract LayerType getLayerType();

    public abstract InetAddress getLocalAddress();

    public abstract HttpHost getProxyHost();

    public abstract HttpHost getTargetHost();

    public abstract TunnelType getTunnelType();

    public abstract boolean isLayered();

    public abstract boolean isSecure();

    public abstract boolean isTunnelled();
}
