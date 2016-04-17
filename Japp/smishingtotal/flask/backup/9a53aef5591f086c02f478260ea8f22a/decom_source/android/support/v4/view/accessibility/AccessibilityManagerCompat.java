// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package android.support.v4.view.accessibility;

import android.view.accessibility.AccessibilityManager;
import java.util.Collections;
import java.util.List;

// Referenced classes of package android.support.v4.view.accessibility:
//            AccessibilityManagerCompatIcs

public class AccessibilityManagerCompat
{
    static class AccessibilityManagerIcsImpl extends AccessibilityManagerStubImpl
    {

        public boolean addAccessibilityStateChangeListener(AccessibilityManager accessibilitymanager, AccessibilityStateChangeListenerCompat accessibilitystatechangelistenercompat)
        {
            return AccessibilityManagerCompatIcs.addAccessibilityStateChangeListener(accessibilitymanager, accessibilitystatechangelistenercompat.mListener);
        }

        public List getEnabledAccessibilityServiceList(AccessibilityManager accessibilitymanager, int i)
        {
            return AccessibilityManagerCompatIcs.getEnabledAccessibilityServiceList(accessibilitymanager, i);
        }

        public List getInstalledAccessibilityServiceList(AccessibilityManager accessibilitymanager)
        {
            return AccessibilityManagerCompatIcs.getInstalledAccessibilityServiceList(accessibilitymanager);
        }

        public boolean isTouchExplorationEnabled(AccessibilityManager accessibilitymanager)
        {
            return AccessibilityManagerCompatIcs.isTouchExplorationEnabled(accessibilitymanager);
        }

        public Object newAccessiblityStateChangeListener(AccessibilityStateChangeListenerCompat accessibilitystatechangelistenercompat)
        {
            return AccessibilityManagerCompatIcs.newAccessibilityStateChangeListener(accessibilitystatechangelistenercompat. new AccessibilityManagerCompatIcs.AccessibilityStateChangeListenerBridge() {

                public void onAccessibilityStateChanged(boolean flag)
                {
                    listener.onAccessibilityStateChanged(flag);
                }

                final AccessibilityManagerIcsImpl this$0;
                final AccessibilityStateChangeListenerCompat val$listener;

            
            {
                this$0 = final_accessibilitymanagericsimpl;
                listener = AccessibilityStateChangeListenerCompat.this;
                super();
            }
            });
        }

        public boolean removeAccessibilityStateChangeListener(AccessibilityManager accessibilitymanager, AccessibilityStateChangeListenerCompat accessibilitystatechangelistenercompat)
        {
            return AccessibilityManagerCompatIcs.removeAccessibilityStateChangeListener(accessibilitymanager, accessibilitystatechangelistenercompat.mListener);
        }

        AccessibilityManagerIcsImpl()
        {
        }
    }

    static class AccessibilityManagerStubImpl
        implements AccessibilityManagerVersionImpl
    {

        public boolean addAccessibilityStateChangeListener(AccessibilityManager accessibilitymanager, AccessibilityStateChangeListenerCompat accessibilitystatechangelistenercompat)
        {
            return false;
        }

        public List getEnabledAccessibilityServiceList(AccessibilityManager accessibilitymanager, int i)
        {
            return Collections.emptyList();
        }

        public List getInstalledAccessibilityServiceList(AccessibilityManager accessibilitymanager)
        {
            return Collections.emptyList();
        }

        public boolean isTouchExplorationEnabled(AccessibilityManager accessibilitymanager)
        {
            return false;
        }

        public Object newAccessiblityStateChangeListener(AccessibilityStateChangeListenerCompat accessibilitystatechangelistenercompat)
        {
            return null;
        }

        public boolean removeAccessibilityStateChangeListener(AccessibilityManager accessibilitymanager, AccessibilityStateChangeListenerCompat accessibilitystatechangelistenercompat)
        {
            return false;
        }

        AccessibilityManagerStubImpl()
        {
        }
    }

    static interface AccessibilityManagerVersionImpl
    {

        public abstract boolean addAccessibilityStateChangeListener(AccessibilityManager accessibilitymanager, AccessibilityStateChangeListenerCompat accessibilitystatechangelistenercompat);

        public abstract List getEnabledAccessibilityServiceList(AccessibilityManager accessibilitymanager, int i);

        public abstract List getInstalledAccessibilityServiceList(AccessibilityManager accessibilitymanager);

        public abstract boolean isTouchExplorationEnabled(AccessibilityManager accessibilitymanager);

        public abstract Object newAccessiblityStateChangeListener(AccessibilityStateChangeListenerCompat accessibilitystatechangelistenercompat);

        public abstract boolean removeAccessibilityStateChangeListener(AccessibilityManager accessibilitymanager, AccessibilityStateChangeListenerCompat accessibilitystatechangelistenercompat);
    }

    public static abstract class AccessibilityStateChangeListenerCompat
    {

        public abstract void onAccessibilityStateChanged(boolean flag);

        final Object mListener = AccessibilityManagerCompat.IMPL.newAccessiblityStateChangeListener(this);

        public AccessibilityStateChangeListenerCompat()
        {
        }
    }


    public AccessibilityManagerCompat()
    {
    }

    public static boolean addAccessibilityStateChangeListener(AccessibilityManager accessibilitymanager, AccessibilityStateChangeListenerCompat accessibilitystatechangelistenercompat)
    {
        return IMPL.addAccessibilityStateChangeListener(accessibilitymanager, accessibilitystatechangelistenercompat);
    }

    public static List getEnabledAccessibilityServiceList(AccessibilityManager accessibilitymanager, int i)
    {
        return IMPL.getEnabledAccessibilityServiceList(accessibilitymanager, i);
    }

    public static List getInstalledAccessibilityServiceList(AccessibilityManager accessibilitymanager)
    {
        return IMPL.getInstalledAccessibilityServiceList(accessibilitymanager);
    }

    public static boolean isTouchExplorationEnabled(AccessibilityManager accessibilitymanager)
    {
        return IMPL.isTouchExplorationEnabled(accessibilitymanager);
    }

    public static boolean removeAccessibilityStateChangeListener(AccessibilityManager accessibilitymanager, AccessibilityStateChangeListenerCompat accessibilitystatechangelistenercompat)
    {
        return IMPL.removeAccessibilityStateChangeListener(accessibilitymanager, accessibilitystatechangelistenercompat);
    }

    private static final AccessibilityManagerVersionImpl IMPL;

    static 
    {
        if(android.os.Build.VERSION.SDK_INT >= 14)
            IMPL = new AccessibilityManagerIcsImpl();
        else
            IMPL = new AccessibilityManagerStubImpl();
    }

}
