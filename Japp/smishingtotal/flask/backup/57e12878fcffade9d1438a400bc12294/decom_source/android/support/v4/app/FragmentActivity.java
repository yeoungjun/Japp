// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package android.support.v4.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.*;
import android.support.v4.util.SparseArrayCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.*;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

// Referenced classes of package android.support.v4.app:
//            FragmentManagerImpl, LoaderManagerImpl, Fragment, ActivityCompatHoneycomb, 
//            FragmentManager, LoaderManager

public class FragmentActivity extends Activity
{
    static class FragmentTag
    {

        public static final int Fragment[] = {
            0x1010003, 0x10100d0, 0x10100d1
        };
        public static final int Fragment_id = 1;
        public static final int Fragment_name = 0;
        public static final int Fragment_tag = 2;


        FragmentTag()
        {
        }
    }

    static final class NonConfigurationInstances
    {

        Object activity;
        HashMap children;
        Object custom;
        ArrayList fragments;
        SparseArrayCompat loaders;

        NonConfigurationInstances()
        {
        }
    }


    public FragmentActivity()
    {
    }

    void doReallyStop(boolean flag)
    {
        if(!mReallyStopped)
        {
            mReallyStopped = true;
            mRetaining = flag;
            mHandler.removeMessages(1);
            onReallyStop();
        }
    }

    public void dump(String s, FileDescriptor filedescriptor, PrintWriter printwriter, String as[])
    {
        if(android.os.Build.VERSION.SDK_INT < 11);
        printwriter.print(s);
        printwriter.print("Local FragmentActivity ");
        printwriter.print(Integer.toHexString(System.identityHashCode(this)));
        printwriter.println(" State:");
        String s1 = (new StringBuilder()).append(s).append("  ").toString();
        printwriter.print(s1);
        printwriter.print("mCreated=");
        printwriter.print(mCreated);
        printwriter.print("mResumed=");
        printwriter.print(mResumed);
        printwriter.print(" mStopped=");
        printwriter.print(mStopped);
        printwriter.print(" mReallyStopped=");
        printwriter.println(mReallyStopped);
        printwriter.print(s1);
        printwriter.print("mLoadersStarted=");
        printwriter.println(mLoadersStarted);
        if(mLoaderManager != null)
        {
            printwriter.print(s);
            printwriter.print("Loader Manager ");
            printwriter.print(Integer.toHexString(System.identityHashCode(mLoaderManager)));
            printwriter.println(":");
            mLoaderManager.dump((new StringBuilder()).append(s).append("  ").toString(), filedescriptor, printwriter, as);
        }
        mFragments.dump(s, filedescriptor, printwriter, as);
    }

    public Object getLastCustomNonConfigurationInstance()
    {
        NonConfigurationInstances nonconfigurationinstances = (NonConfigurationInstances)getLastNonConfigurationInstance();
        if(nonconfigurationinstances != null)
            return nonconfigurationinstances.custom;
        else
            return null;
    }

    LoaderManagerImpl getLoaderManager(int i, boolean flag, boolean flag1)
    {
        if(mAllLoaderManagers == null)
            mAllLoaderManagers = new SparseArrayCompat();
        LoaderManagerImpl loadermanagerimpl = (LoaderManagerImpl)mAllLoaderManagers.get(i);
        if(loadermanagerimpl == null)
        {
            if(flag1)
            {
                loadermanagerimpl = new LoaderManagerImpl(this, flag);
                mAllLoaderManagers.put(i, loadermanagerimpl);
            }
            return loadermanagerimpl;
        } else
        {
            loadermanagerimpl.updateActivity(this);
            return loadermanagerimpl;
        }
    }

    public FragmentManager getSupportFragmentManager()
    {
        return mFragments;
    }

    public LoaderManager getSupportLoaderManager()
    {
        if(mLoaderManager != null)
        {
            return mLoaderManager;
        } else
        {
            mCheckedForLoaderManager = true;
            mLoaderManager = getLoaderManager(-1, mLoadersStarted, true);
            return mLoaderManager;
        }
    }

    void invalidateSupportFragmentIndex(int i)
    {
        if(mAllLoaderManagers != null)
        {
            LoaderManagerImpl loadermanagerimpl = (LoaderManagerImpl)mAllLoaderManagers.get(i);
            if(loadermanagerimpl != null && !loadermanagerimpl.mRetaining)
            {
                loadermanagerimpl.doDestroy();
                mAllLoaderManagers.remove(i);
            }
        }
    }

    protected void onActivityResult(int i, int j, Intent intent)
    {
        int k = i >> 16;
        if(k != 0)
        {
            int l = k - 1;
            if(mFragments.mActive == null || l < 0 || l >= mFragments.mActive.size())
            {
                Log.w("FragmentActivity", (new StringBuilder()).append("Activity result fragment index out of range: 0x").append(Integer.toHexString(i)).toString());
                return;
            }
            Fragment fragment = (Fragment)mFragments.mActive.get(l);
            if(fragment == null)
            {
                Log.w("FragmentActivity", (new StringBuilder()).append("Activity result no fragment exists for index: 0x").append(Integer.toHexString(i)).toString());
                return;
            } else
            {
                fragment.onActivityResult(0xffff & i, j, intent);
                return;
            }
        } else
        {
            super.onActivityResult(i, j, intent);
            return;
        }
    }

    public void onAttachFragment(Fragment fragment)
    {
    }

    public void onBackPressed()
    {
        if(!mFragments.popBackStackImmediate())
            finish();
    }

    public void onConfigurationChanged(Configuration configuration)
    {
        super.onConfigurationChanged(configuration);
        mFragments.dispatchConfigurationChanged(configuration);
    }

    protected void onCreate(Bundle bundle)
    {
        mFragments.attachActivity(this);
        if(getLayoutInflater().getFactory() == null)
            getLayoutInflater().setFactory(this);
        super.onCreate(bundle);
        NonConfigurationInstances nonconfigurationinstances = (NonConfigurationInstances)getLastNonConfigurationInstance();
        if(nonconfigurationinstances != null)
            mAllLoaderManagers = nonconfigurationinstances.loaders;
        if(bundle != null)
        {
            android.os.Parcelable parcelable = bundle.getParcelable("android:support:fragments");
            FragmentManagerImpl fragmentmanagerimpl = mFragments;
            ArrayList arraylist;
            if(nonconfigurationinstances != null)
                arraylist = nonconfigurationinstances.fragments;
            else
                arraylist = null;
            fragmentmanagerimpl.restoreAllState(parcelable, arraylist);
        }
        mFragments.dispatchCreate();
    }

    public boolean onCreatePanelMenu(int i, Menu menu)
    {
        if(i == 0)
        {
            boolean flag = super.onCreatePanelMenu(i, menu) | mFragments.dispatchCreateOptionsMenu(menu, getMenuInflater());
            if(android.os.Build.VERSION.SDK_INT >= 11)
                return flag;
            else
                return true;
        } else
        {
            return super.onCreatePanelMenu(i, menu);
        }
    }

    public View onCreateView(String s, Context context, AttributeSet attributeset)
    {
        if(!"fragment".equals(s))
            return super.onCreateView(s, context, attributeset);
        String s1 = attributeset.getAttributeValue(null, "class");
        TypedArray typedarray = context.obtainStyledAttributes(attributeset, FragmentTag.Fragment);
        if(s1 == null)
            s1 = typedarray.getString(0);
        int i = typedarray.getResourceId(1, -1);
        String s2 = typedarray.getString(2);
        typedarray.recycle();
        int j = 0;
        if(false)
            j = null.getId();
        if(j == -1 && i == -1 && s2 == null)
            throw new IllegalArgumentException((new StringBuilder()).append(attributeset.getPositionDescription()).append(": Must specify unique android:id, android:tag, or have a parent with an id for ").append(s1).toString());
        Fragment fragment = null;
        if(i != -1)
            fragment = mFragments.findFragmentById(i);
        if(fragment == null && s2 != null)
            fragment = mFragments.findFragmentByTag(s2);
        if(fragment == null && j != -1)
            fragment = mFragments.findFragmentById(j);
        if(FragmentManagerImpl.DEBUG)
            Log.v("FragmentActivity", (new StringBuilder()).append("onCreateView: id=0x").append(Integer.toHexString(i)).append(" fname=").append(s1).append(" existing=").append(fragment).toString());
        if(fragment == null)
        {
            fragment = Fragment.instantiate(this, s1);
            fragment.mFromLayout = true;
            int k;
            if(i != 0)
                k = i;
            else
                k = j;
            fragment.mFragmentId = k;
            fragment.mContainerId = j;
            fragment.mTag = s2;
            fragment.mInLayout = true;
            fragment.mFragmentManager = mFragments;
            fragment.onInflate(this, attributeset, fragment.mSavedFragmentState);
            mFragments.addFragment(fragment, true);
        } else
        {
            if(fragment.mInLayout)
                throw new IllegalArgumentException((new StringBuilder()).append(attributeset.getPositionDescription()).append(": Duplicate id 0x").append(Integer.toHexString(i)).append(", tag ").append(s2).append(", or parent id 0x").append(Integer.toHexString(j)).append(" with another fragment for ").append(s1).toString());
            fragment.mInLayout = true;
            if(!fragment.mRetaining)
                fragment.onInflate(this, attributeset, fragment.mSavedFragmentState);
            mFragments.moveToState(fragment);
        }
        if(fragment.mView == null)
            throw new IllegalStateException((new StringBuilder()).append("Fragment ").append(s1).append(" did not create a view.").toString());
        if(i != 0)
            fragment.mView.setId(i);
        if(fragment.mView.getTag() == null)
            fragment.mView.setTag(s2);
        return fragment.mView;
    }

    protected void onDestroy()
    {
        super.onDestroy();
        doReallyStop(false);
        mFragments.dispatchDestroy();
        if(mLoaderManager != null)
            mLoaderManager.doDestroy();
    }

    public boolean onKeyDown(int i, KeyEvent keyevent)
    {
        if(android.os.Build.VERSION.SDK_INT < 5 && i == 4 && keyevent.getRepeatCount() == 0)
        {
            onBackPressed();
            return true;
        } else
        {
            return super.onKeyDown(i, keyevent);
        }
    }

    public void onLowMemory()
    {
        super.onLowMemory();
        mFragments.dispatchLowMemory();
    }

    public boolean onMenuItemSelected(int i, MenuItem menuitem)
    {
        if(super.onMenuItemSelected(i, menuitem))
            return true;
        switch(i)
        {
        default:
            return false;

        case 0: // '\0'
            return mFragments.dispatchOptionsItemSelected(menuitem);

        case 6: // '\006'
            return mFragments.dispatchContextItemSelected(menuitem);
        }
    }

    public void onPanelClosed(int i, Menu menu)
    {
        i;
        JVM INSTR tableswitch 0 0: default 20
    //                   0 27;
           goto _L1 _L2
_L1:
        super.onPanelClosed(i, menu);
        return;
_L2:
        mFragments.dispatchOptionsMenuClosed(menu);
        if(true) goto _L1; else goto _L3
_L3:
    }

    protected void onPause()
    {
        super.onPause();
        mResumed = false;
        if(mHandler.hasMessages(2))
        {
            mHandler.removeMessages(2);
            onResumeFragments();
        }
        mFragments.dispatchPause();
    }

    protected void onPostResume()
    {
        super.onPostResume();
        mHandler.removeMessages(2);
        onResumeFragments();
        mFragments.execPendingActions();
    }

    public boolean onPreparePanel(int i, View view, Menu menu)
    {
        if(i == 0 && menu != null)
        {
            if(mOptionsMenuInvalidated)
            {
                mOptionsMenuInvalidated = false;
                menu.clear();
                onCreatePanelMenu(i, menu);
            }
            boolean flag = super.onPreparePanel(i, view, menu) | mFragments.dispatchPrepareOptionsMenu(menu);
            boolean flag1 = false;
            if(flag)
            {
                boolean flag2 = menu.hasVisibleItems();
                flag1 = false;
                if(flag2)
                    flag1 = true;
            }
            return flag1;
        } else
        {
            return super.onPreparePanel(i, view, menu);
        }
    }

    void onReallyStop()
    {
        if(mLoadersStarted)
        {
            mLoadersStarted = false;
            if(mLoaderManager != null)
                if(!mRetaining)
                    mLoaderManager.doStop();
                else
                    mLoaderManager.doRetain();
        }
        mFragments.dispatchReallyStop();
    }

    protected void onResume()
    {
        super.onResume();
        mHandler.sendEmptyMessage(2);
        mResumed = true;
        mFragments.execPendingActions();
    }

    protected void onResumeFragments()
    {
        mFragments.dispatchResume();
    }

    public Object onRetainCustomNonConfigurationInstance()
    {
        return null;
    }

    public final Object onRetainNonConfigurationInstance()
    {
        if(mStopped)
            doReallyStop(true);
        Object obj = onRetainCustomNonConfigurationInstance();
        ArrayList arraylist = mFragments.retainNonConfig();
        SparseArrayCompat sparsearraycompat = mAllLoaderManagers;
        boolean flag = false;
        if(sparsearraycompat != null)
        {
            int i = -1 + mAllLoaderManagers.size();
            while(i >= 0) 
            {
                LoaderManagerImpl loadermanagerimpl = (LoaderManagerImpl)mAllLoaderManagers.valueAt(i);
                if(loadermanagerimpl.mRetaining)
                {
                    flag = true;
                } else
                {
                    loadermanagerimpl.doDestroy();
                    mAllLoaderManagers.removeAt(i);
                }
                i--;
            }
        }
        if(arraylist == null && !flag && obj == null)
        {
            return null;
        } else
        {
            NonConfigurationInstances nonconfigurationinstances = new NonConfigurationInstances();
            nonconfigurationinstances.activity = null;
            nonconfigurationinstances.custom = obj;
            nonconfigurationinstances.children = null;
            nonconfigurationinstances.fragments = arraylist;
            nonconfigurationinstances.loaders = mAllLoaderManagers;
            return nonconfigurationinstances;
        }
    }

    protected void onSaveInstanceState(Bundle bundle)
    {
        super.onSaveInstanceState(bundle);
        android.os.Parcelable parcelable = mFragments.saveAllState();
        if(parcelable != null)
            bundle.putParcelable("android:support:fragments", parcelable);
    }

    protected void onStart()
    {
        super.onStart();
        mStopped = false;
        mReallyStopped = false;
        mHandler.removeMessages(1);
        if(!mCreated)
        {
            mCreated = true;
            mFragments.dispatchActivityCreated();
        }
        mFragments.noteStateNotSaved();
        mFragments.execPendingActions();
        if(mLoadersStarted) goto _L2; else goto _L1
_L1:
        mLoadersStarted = true;
        if(mLoaderManager == null) goto _L4; else goto _L3
_L3:
        mLoaderManager.doStart();
_L6:
        mCheckedForLoaderManager = true;
_L2:
        mFragments.dispatchStart();
        if(mAllLoaderManagers != null)
        {
            for(int i = -1 + mAllLoaderManagers.size(); i >= 0; i--)
            {
                LoaderManagerImpl loadermanagerimpl = (LoaderManagerImpl)mAllLoaderManagers.valueAt(i);
                loadermanagerimpl.finishRetain();
                loadermanagerimpl.doReportStart();
            }

        }
        break; /* Loop/switch isn't completed */
_L4:
        if(!mCheckedForLoaderManager)
            mLoaderManager = getLoaderManager(-1, mLoadersStarted, false);
        if(true) goto _L6; else goto _L5
_L5:
    }

    protected void onStop()
    {
        super.onStop();
        mStopped = true;
        mHandler.sendEmptyMessage(1);
        mFragments.dispatchStop();
    }

    public void startActivityForResult(Intent intent, int i)
    {
        if(i != -1 && (0xffff0000 & i) != 0)
        {
            throw new IllegalArgumentException("Can only use lower 16 bits for requestCode");
        } else
        {
            super.startActivityForResult(intent, i);
            return;
        }
    }

    public void startActivityFromFragment(Fragment fragment, Intent intent, int i)
    {
        if(i == -1)
        {
            super.startActivityForResult(intent, -1);
            return;
        }
        if((0xffff0000 & i) != 0)
        {
            throw new IllegalArgumentException("Can only use lower 16 bits for requestCode");
        } else
        {
            super.startActivityForResult(intent, (1 + fragment.mIndex << 16) + (0xffff & i));
            return;
        }
    }

    public void supportInvalidateOptionsMenu()
    {
        if(android.os.Build.VERSION.SDK_INT >= 11)
        {
            ActivityCompatHoneycomb.invalidateOptionsMenu(this);
            return;
        } else
        {
            mOptionsMenuInvalidated = true;
            return;
        }
    }

    private static final String FRAGMENTS_TAG = "android:support:fragments";
    private static final int HONEYCOMB = 11;
    static final int MSG_REALLY_STOPPED = 1;
    static final int MSG_RESUME_PENDING = 2;
    private static final String TAG = "FragmentActivity";
    SparseArrayCompat mAllLoaderManagers;
    boolean mCheckedForLoaderManager;
    boolean mCreated;
    final FragmentManagerImpl mFragments = new FragmentManagerImpl();
    final Handler mHandler = new Handler() {

        public void handleMessage(Message message)
        {
            message.what;
            JVM INSTR tableswitch 1 2: default 28
        //                       1 34
        //                       2 53;
               goto _L1 _L2 _L3
_L1:
            super.handleMessage(message);
_L5:
            return;
_L2:
            if(!mStopped) goto _L5; else goto _L4
_L4:
            doReallyStop(false);
            return;
_L3:
            onResumeFragments();
            mFragments.execPendingActions();
            return;
        }

        final FragmentActivity this$0;

            
            {
                this$0 = FragmentActivity.this;
                super();
            }
    };
    LoaderManagerImpl mLoaderManager;
    boolean mLoadersStarted;
    boolean mOptionsMenuInvalidated;
    boolean mReallyStopped;
    boolean mResumed;
    boolean mRetaining;
    boolean mStopped;
}
