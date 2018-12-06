package cn.com.ihappy.ihappy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.ihappy.ihappy.base.RxBaseActivity;
import cn.com.ihappy.ihappy.base.RxLazyFragment;
import cn.com.ihappy.ihappy.beans.MenuBean;
import cn.com.ihappy.ihappy.manager.ConfigManager;
import cn.com.ihappy.ihappy.module.meizi.MainMeiziFragment;
import cn.com.ihappy.ihappy.module.news.MainNewsFragment;
import cn.com.ihappy.ihappy.module.shuaige.MainShuaigeFragment;
import cn.com.ihappy.ihappy.module.video.MainVideoFragment;

public class MainActivity extends RxBaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;

    private ArrayList<RxLazyFragment> fragmentList;
    private int currentTabIndex;
    private int index;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        this.initFragments();
        this.initNavigationView();
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        index = menuItem.getOrder();
        switchFragment();
        menuItem.setChecked(true);
        return true;
    }

    //TODO 初始化Fragments
    @SuppressLint("ResourceType")
    private void initFragments() {
        this.fragmentList = new ArrayList<>();
        List<MenuBean> menuBeanList = ConfigManager.shareManager().menuList;
        for (MenuBean menuBean : menuBeanList) {
            if (menuBean.enable == false) {
                continue;
            }
            RxLazyFragment menuFragment = this.fragmentWithMenu(menuBean);
            menuFragment.menuBean = menuBean;
            if (menuFragment != null) {
                fragmentList.add(menuFragment);
            }
        }
        if (fragmentList.size() > 0) {
            RxLazyFragment firstFragment = fragmentList.get(0);
            // 添加显示第一个fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.app_container, firstFragment)
                    .show(firstFragment).commit();

        }

    }

    private RxLazyFragment fragmentWithMenu(MenuBean menuBean) {
        switch (menuBean.getMenuType()) {
            case Bizhi:
                return new MainMeiziFragment();
            case Q2002:
                return new MainVideoFragment();
            case Video:
                return new MainVideoFragment();
            case Shuaige:
                return new MainShuaigeFragment();
            case HotNews:
                return new MainNewsFragment();
        }
        return new MainNewsFragment();
    }

    private void initNavigationView() {
        this.mNavigationView.setNavigationItemSelectedListener(this);
        Menu menu = this.mNavigationView.getMenu();
        menu.removeGroup(0);
        List<MenuBean> menuBeanList = ConfigManager.shareManager().menuList;
        for (int i = 0; i < menuBeanList.size(); i++) {
            MenuBean menuBean = menuBeanList.get(i);
            menu.add(0, menuBean.type, i, menuBean.title);//需要获取id的话，id就等于1；
        }
    }


    /**
     * Fragment切换
     */
    private void switchFragment() {
        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        trx.hide(this.fragmentList.get(currentTabIndex));

        RxLazyFragment newFragment = this.fragmentList.get(index);
        if (!newFragment.isAdded()) {
            trx.add(R.id.app_container, newFragment);
        }
        trx.show(newFragment).commit();
        currentTabIndex = index;
    }


    public void toggleDrawer() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }
}
