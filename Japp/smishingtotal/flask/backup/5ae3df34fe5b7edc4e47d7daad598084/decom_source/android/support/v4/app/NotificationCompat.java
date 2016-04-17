// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package android.support.v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.RemoteViews;

// Referenced classes of package android.support.v4.app:
//            NotificationCompatHoneycomb

public class NotificationCompat
{
    public static class Builder
    {

        private void setFlag(int i, boolean flag)
        {
            if(flag)
            {
                Notification notification1 = mNotification;
                notification1.flags = i | notification1.flags;
                return;
            } else
            {
                Notification notification = mNotification;
                notification.flags = notification.flags & ~i;
                return;
            }
        }

        public Notification getNotification()
        {
            return NotificationCompat.IMPL.getNotification(this);
        }

        public Builder setAutoCancel(boolean flag)
        {
            setFlag(16, flag);
            return this;
        }

        public Builder setContent(RemoteViews remoteviews)
        {
            mNotification.contentView = remoteviews;
            return this;
        }

        public Builder setContentInfo(CharSequence charsequence)
        {
            mContentInfo = charsequence;
            return this;
        }

        public Builder setContentIntent(PendingIntent pendingintent)
        {
            mContentIntent = pendingintent;
            return this;
        }

        public Builder setContentText(CharSequence charsequence)
        {
            mContentText = charsequence;
            return this;
        }

        public Builder setContentTitle(CharSequence charsequence)
        {
            mContentTitle = charsequence;
            return this;
        }

        public Builder setDefaults(int i)
        {
            mNotification.defaults = i;
            if((i & 4) != 0)
            {
                Notification notification = mNotification;
                notification.flags = 1 | notification.flags;
            }
            return this;
        }

        public Builder setDeleteIntent(PendingIntent pendingintent)
        {
            mNotification.deleteIntent = pendingintent;
            return this;
        }

        public Builder setFullScreenIntent(PendingIntent pendingintent, boolean flag)
        {
            mFullScreenIntent = pendingintent;
            setFlag(128, flag);
            return this;
        }

        public Builder setLargeIcon(Bitmap bitmap)
        {
            mLargeIcon = bitmap;
            return this;
        }

        public Builder setLights(int i, int j, int k)
        {
            boolean flag = true;
            mNotification.ledARGB = i;
            mNotification.ledOnMS = j;
            mNotification.ledOffMS = k;
            boolean flag1;
            Notification notification;
            int l;
            if(mNotification.ledOnMS != 0 && mNotification.ledOffMS != 0)
                flag1 = flag;
            else
                flag1 = false;
            notification = mNotification;
            l = -2 & mNotification.flags;
            if(!flag1)
                flag = false;
            notification.flags = flag | l;
            return this;
        }

        public Builder setNumber(int i)
        {
            mNumber = i;
            return this;
        }

        public Builder setOngoing(boolean flag)
        {
            setFlag(2, flag);
            return this;
        }

        public Builder setOnlyAlertOnce(boolean flag)
        {
            setFlag(8, flag);
            return this;
        }

        public Builder setSmallIcon(int i)
        {
            mNotification.icon = i;
            return this;
        }

        public Builder setSmallIcon(int i, int j)
        {
            mNotification.icon = i;
            mNotification.iconLevel = j;
            return this;
        }

        public Builder setSound(Uri uri)
        {
            mNotification.sound = uri;
            mNotification.audioStreamType = -1;
            return this;
        }

        public Builder setSound(Uri uri, int i)
        {
            mNotification.sound = uri;
            mNotification.audioStreamType = i;
            return this;
        }

        public Builder setTicker(CharSequence charsequence)
        {
            mNotification.tickerText = charsequence;
            return this;
        }

        public Builder setTicker(CharSequence charsequence, RemoteViews remoteviews)
        {
            mNotification.tickerText = charsequence;
            mTickerView = remoteviews;
            return this;
        }

        public Builder setVibrate(long al[])
        {
            mNotification.vibrate = al;
            return this;
        }

        public Builder setWhen(long l)
        {
            mNotification.when = l;
            return this;
        }

        CharSequence mContentInfo;
        PendingIntent mContentIntent;
        CharSequence mContentText;
        CharSequence mContentTitle;
        Context mContext;
        PendingIntent mFullScreenIntent;
        Bitmap mLargeIcon;
        Notification mNotification;
        int mNumber;
        RemoteViews mTickerView;

        public Builder(Context context)
        {
            mNotification = new Notification();
            mContext = context;
            mNotification.when = System.currentTimeMillis();
            mNotification.audioStreamType = -1;
        }
    }

    static interface NotificationCompatImpl
    {

        public abstract Notification getNotification(Builder builder);
    }

    static class NotificationCompatImplBase
        implements NotificationCompatImpl
    {

        public Notification getNotification(Builder builder)
        {
            Notification notification = builder.mNotification;
            notification.setLatestEventInfo(builder.mContext, builder.mContentTitle, builder.mContentText, builder.mContentIntent);
            return notification;
        }

        NotificationCompatImplBase()
        {
        }
    }

    static class NotificationCompatImplHoneycomb
        implements NotificationCompatImpl
    {

        public Notification getNotification(Builder builder)
        {
            return NotificationCompatHoneycomb.add(builder.mContext, builder.mNotification, builder.mContentTitle, builder.mContentText, builder.mContentInfo, builder.mTickerView, builder.mNumber, builder.mContentIntent, builder.mFullScreenIntent, builder.mLargeIcon);
        }

        NotificationCompatImplHoneycomb()
        {
        }
    }


    public NotificationCompat()
    {
    }

    public static final int FLAG_HIGH_PRIORITY = 128;
    private static final NotificationCompatImpl IMPL;

    static 
    {
        if(android.os.Build.VERSION.SDK_INT >= 11)
            IMPL = new NotificationCompatImplHoneycomb();
        else
            IMPL = new NotificationCompatImplBase();
    }

}
