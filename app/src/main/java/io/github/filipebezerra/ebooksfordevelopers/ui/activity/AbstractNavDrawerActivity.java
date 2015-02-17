package io.github.filipebezerra.ebooksfordevelopers.ui.activity;

import android.content.res.*;
import android.os.*;
import android.support.v4.view.*;
import android.support.v4.widget.*;
import android.support.v7.app.*;
import android.view.*;
import android.widget.*;
import io.github.filipebezerra.ebooksfordevelopers.*;
import io.github.filipebezerra.ebooksfordevelopers.model.*;
import io.github.filipebezerra.ebooksfordevelopers.widget.*;

public abstract class AbstractNavDrawerActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private ListView mDrawerList;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    private NavDrawerActivityConfiguration navConf ;

    protected abstract NavDrawerActivityConfiguration getNavDrawerConfiguration();

    protected abstract void onNavItemSelected(int id);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        navConf = getNavDrawerConfiguration();

        mTitle = mDrawerTitle = getTitle();

        mDrawerLayout = (DrawerLayout) findViewById(navConf.getDrawerLayoutId());
        mDrawerList = (ListView) findViewById(navConf.getLeftDrawerId());
        mDrawerList.setAdapter(navConf.getBaseAdapter());
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        this.initDrawerShadow();
        this.initDrawerIcon();

        mDrawerToggle = new ActionBarDrawerToggle(
			this,
			mDrawerLayout,
			mToolbar,
			navConf.getDrawerOpenDesc(),
			navConf.getDrawerCloseDesc()
        );

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    protected void initDrawerIcon() {
        setActionBarIcon(R.drawable.ic_menu_white_24dp);
    }

    protected void initDrawerShadow() {
        int drawerShadow = navConf.getDrawerShadow();
        if (drawerShadow != 0) {
            mDrawerLayout.setDrawerShadow(drawerShadow, GravityCompat.START);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (navConf.getActionMenuItemsToHideWhenDrawerOpen() != null) {
            boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
            for (int iItem : navConf.getActionMenuItemsToHideWhenDrawerOpen()) {
                menu.findItem(iItem).setVisible(!drawerOpen);
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            if (this.mDrawerLayout.isDrawerOpen(this.mDrawerList)) {
                this.mDrawerLayout.closeDrawer(Gravity.LEFT);
            } else {
                if (this.mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    this.mDrawerLayout.closeDrawer(Gravity.RIGHT);
                }

                this.mDrawerLayout.openDrawer(Gravity.LEFT);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (this.mDrawerLayout.isDrawerOpen(this.mDrawerList)) {
            mDrawerLayout.closeDrawer(mDrawerList);
            return;
        }
        super.onBackPressed();
    }

    protected DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    protected ActionBarDrawerToggle getDrawerToggle() {
        return mDrawerToggle;
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    public void selectItem(int position) {
        NavDrawerItem selectedItem = navConf.getNavItems()[position];

        this.onNavItemSelected(selectedItem.getId());
        mDrawerList.setItemChecked(position, true);

        if (selectedItem.updateActionBarSubtitle()) {
            setTitle(selectedItem.getLabel());
        }

        if (this.mDrawerLayout.isDrawerOpen(this.mDrawerList)) {
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    public void openLeftDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.openDrawer(Gravity.LEFT);
        }
    }

    public void openRightDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.openDrawer(Gravity.RIGHT);
        }
    }

}
