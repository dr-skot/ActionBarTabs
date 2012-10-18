package com.scottshepherd.android.example.actionbartabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class MainActivity extends SherlockFragmentActivity {
    public static int THEME = R.style.Theme_Sherlock;
    public static int[] TAB_LABELS = new int[] {
    	R.string.tab_label1,
    	R.string.tab_label2,
    	R.string.tab_label3
    };

    @SuppressWarnings("rawtypes")
	public static Class[] FRAGMENT_CLASSES = new Class[] {
    	Fragment1.class, 
    	Fragment2.class, 
    	Fragment3.class
    };

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(THEME); //Used for theme switching in samples
        super.onCreate(savedInstanceState);

        // setContentView(R.layout.activity_main);

        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        for (int i = 0; i < TAB_LABELS.length; i++) {
            ActionBar.Tab tab = getSupportActionBar().newTab();
            tab.setText(TAB_LABELS[i]);
            tab.setTabListener(new TabListener(this, getString(TAB_LABELS[i]), FRAGMENT_CLASSES[i]));
            getSupportActionBar().addTab(tab);
        }
    }    
    
    public static class TabListener<T extends Fragment> implements ActionBar.TabListener {
        private Fragment mFragment;
        private final SherlockFragmentActivity mActivity;
        private final String mTag;
        private final Class<T> mClass;

        /** Constructor used each time a new tab is created.
          * @param activity  The host Activity, used to instantiate the fragment
          * @param tag  The identifier tag for the fragment
          * @param clz  The fragment's Class, used to instantiate the fragment
          */
        public TabListener(SherlockFragmentActivity activity, String tag, Class<T> clz) {
            mActivity = activity;
            mTag = tag;
            mClass = clz;
        }

        /* The following are each of the ActionBar.TabListener callbacks */

        public void onTabSelected(Tab tab, FragmentTransaction ft) {
            // Check if the fragment is already initialized
            if (mFragment == null) {
                // If not, instantiate and add it to the activity
                mFragment = Fragment.instantiate(mActivity, mClass.getName());
                if (null == mFragment) throw new RuntimeException("mFragment was null");
                if (null == mTag) throw new RuntimeException("mTag was null");
                
                ft.add(android.R.id.content, mFragment, mTag);
            } else {
                // If it exists, simply attach it in order to show it
                ft.attach(mFragment);
            }
        }

        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
            if (mFragment != null) {
                // Detach the fragment, because another one is being attached
                ft.detach(mFragment);
            }
        }

        public void onTabReselected(Tab tab, FragmentTransaction ft) {
            // User selected the already selected tab. Usually do nothing.
        }
    }
}

