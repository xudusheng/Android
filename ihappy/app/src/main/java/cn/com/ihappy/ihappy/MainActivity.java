package cn.com.ihappy.ihappy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.ihappy.ihappy.base.RxBaseActivity;
import cn.com.ihappy.ihappy.module.meizi.MainMeiziFragment;
import cn.com.ihappy.ihappy.module.news.MainNewsFragment;
import cn.com.ihappy.ihappy.module.shuaige.MainShuaigeFragment;
import cn.com.ihappy.ihappy.module.video.MainVideoFragment;

public class MainActivity extends RxBaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;

    private Fragment[] fragments;
    private int currentTabIndex;
    private int index;
    private MainVideoFragment mMainVideoFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        this.initFragments();
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        switch (menuItem.getItemId()) {
            case R.id.item_video:
                // 小微视频
                changeFragmentIndex(menuItem, 0);
                return true;
            case R.id.item_meizi:
                // 美女壁纸
                changeFragmentIndex(menuItem, 1);
                return true;
            case R.id.item_shuaige:
                //帅哥壁纸
                changeFragmentIndex(menuItem, 2);
                return true;
            case R.id.item_news:
                // 我的收藏
                changeFragmentIndex(menuItem, 3);
                return true;
        }
        return false;
    }

    //TODO: 初始化Fragments
    private void initFragments() {
        mMainVideoFragment = new MainVideoFragment();
        MainMeiziFragment mainMeiziFragment = new MainMeiziFragment();
        MainShuaigeFragment mainShuaigeFragment = new MainShuaigeFragment();
        MainNewsFragment mainNewsFragment = new MainNewsFragment();

        fragments = new Fragment[]{
                mMainVideoFragment,
                mainMeiziFragment,
                mainShuaigeFragment,
                mainNewsFragment
        };
        // 添加显示第一个fragment
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, mMainVideoFragment)
                .show(mMainVideoFragment).commit();
    }

    /**
     * 切换Fragment的下标
     */
    private void changeFragmentIndex(MenuItem item, int currentIndex) {
        index = currentIndex;
        switchFragment();
        item.setChecked(true);
    }
    /**
     * Fragment切换
     */
    private void switchFragment() {
        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        trx.hide(fragments[currentTabIndex]);
        if (!fragments[index].isAdded()) {
            trx.add(R.id.container, fragments[index]);
        }
        trx.show(fragments[index]).commit();
        currentTabIndex = index;
    }

    @OnClick(R.id.toggleDrawer_button)
    public void toggleDrawer() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }
}
