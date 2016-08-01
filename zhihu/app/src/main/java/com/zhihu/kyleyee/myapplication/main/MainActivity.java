package com.zhihu.kyleyee.myapplication.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.zhihu.kyleyee.myapplication.R;
import com.zhihu.kyleyee.myapplication.base.BaseActivity;
import com.zhihu.kyleyee.myapplication.manager.ApiManager;
import com.zhihu.kyleyee.myapplication.model.New;
import com.zhihu.kyleyee.myapplication.model.Themes;
import com.zhihu.kyleyee.myapplication.modul.HomeFragment;
import com.zhihu.kyleyee.myapplication.modul.ThemeCommonFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 主界面
 * Created by kyleYee on 2016/6/29.
 */
public class MainActivity extends BaseActivity implements HomeFragment.ToolbarScrollListener {

    @Bind(R.id.toolbar_home)
    Toolbar mToolbar;
    @Bind(R.id.navigation)
    NavigationView mNavigation;
    @Bind(R.id.drawer)
    DrawerLayout mDrawer;

    private FragmentManager mFragmentManager;
    private List<Fragment> mListFragment;
    private ThemeCommonFragment mFragment;
    private HomeFragment mHomeFragment;
    private Themes mThemes;
    private boolean isHomeFragment = true;

    public static void startMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initToolbar();
        initNavigation();
        initFragment();
    }

    //初始化Fragment
    private void initFragment() {
        mListFragment = new ArrayList<>();
        mFragmentManager = getSupportFragmentManager();
        mHomeFragment = new HomeFragment();
        mHomeFragment.setToolbarScrollListener(this);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framgent, mHomeFragment);
        fragmentTransaction.commit();
    }

    /**
     * 初始化侧边导航栏
     */
    private void initNavigation() {
        final Menu menu = mNavigation.getMenu();
        ApiManager.getInstance().getThemes(new ApiManager.ResultCallBack() {
            @Override
            public void onTaskSuccess(Object data) {
                mThemes = (Themes) data;
                if (mThemes != null) {
                    for (int i = 0; i < mThemes.others.size(); i++) {
                        mFragment = new ThemeCommonFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("themes", mThemes.others.get(i));
                        mFragment.setArguments(bundle);
                        mListFragment.add(mFragment);
                        menu.add(0, i, 0, mThemes.others.get(i).name).setIcon(R.drawable.ic_add_black_24dp);
                    }
                }
            }

            @Override
            public void onError(Object error) {

            }

            @Override
            public void onFinish() {

            }
        });

        setNavigationListener();
    }

    private void setNavigationListener() {
        mNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item == null)
                    return false;

                if (item.getItemId() == R.id.home) {
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.framgent, mHomeFragment);
                    fragmentTransaction.commit();
                    mToolbar.setTitle("首页");
                    isHomeFragment = true;
                } else {
                    if (mThemes == null)
                        return false;
                    isHomeFragment = false;
                    mToolbar.setTitle(item.getTitle());
                    FragmentTransaction commonTransaction = mFragmentManager.beginTransaction();
                    commonTransaction.replace(R.id.framgent, mListFragment.get(item.getItemId()));
                    commonTransaction.commit();
                }
                mNavigation.setCheckedItem(item.getItemId());
                mDrawer.closeDrawers();
                return true;
            }
        });
    }


    /**
     * 初始化Toolbar
     */
    private void initToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.openDrawer(mNavigation);
            }
        });

    }

    @Override
    public void setTitle(String content) {
        if (content == null) return;
        mToolbar.setTitle(content);
    }

    @Override
    public void onBackPressed() {
        if (!isHomeFragment) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.framgent, mHomeFragment);
            fragmentTransaction.commit();
            mToolbar.setTitle("首页");
            isHomeFragment = true;
            return;
        }
        super.onBackPressed();
    }
}
