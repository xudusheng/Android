package cn.com.ihappy.ihappy.module.video;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.ihappy.ihappy.MainActivity;
import cn.com.ihappy.ihappy.R;
import cn.com.ihappy.ihappy.base.RxLazyFragment;
import cn.com.ihappy.ihappy.beans.MenuBean;
import cn.com.ihappy.ihappy.beans.SubMenuBean;

public class MainVideoFragment extends RxLazyFragment {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.st_main_video)
    SlidingTabLayout mSlidingTabLayout;

    @BindView(R.id.vp_main_video)
    ViewPager mViewPager;

    private VideoListPagerAdapter mPagerAdapter;
    private ArrayList<VideoListFragment> videoListFragments = new ArrayList<>();
    private MenuBean mMenuBean;


    public MainVideoFragment(MenuBean mMenuBean) {
        this.mMenuBean = mMenuBean;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_main_video;
    }

    @Override
    public void finishCreateView(Bundle state) {
        mToolbar.setTitle(this.mMenuBean.title);
        mToolbar.setNavigationIcon(R.drawable.ic_navigation_drawer);


        for (SubMenuBean subMenuBean : this.mMenuBean.subMenus) {
            VideoListFragment fragment = new VideoListFragment(this.mMenuBean.rooturl, subMenuBean);
            videoListFragments.add(fragment);
        }

        mPagerAdapter = new VideoListPagerAdapter(getFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mSlidingTabLayout.setViewPager(mViewPager);

    }

    @OnClick(R.id.toolbar)
    public void showMenu() {
        Activity mainActivity = getActivity();
        if (mainActivity instanceof MainActivity) {
            ((MainActivity) mainActivity).toggleDrawer();
        }
    }


    private class VideoListPagerAdapter extends FragmentPagerAdapter {
        public VideoListPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mMenuBean.subMenus.get(position).title;
        }

        @Override
        public Fragment getItem(int i) {
            return videoListFragments.get(i);
        }

        @Override
        public int getCount() {
            return mMenuBean.subMenus.size();
        }
    }
}
