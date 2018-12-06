package cn.com.ihappy.ihappy.module.video;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.ihappy.ihappy.MainActivity;
import cn.com.ihappy.ihappy.R;
import cn.com.ihappy.ihappy.base.RxLazyFragment;

public class MainVideoFragment extends RxLazyFragment {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_main_video;
    }

    @Override
    public void finishCreateView(Bundle state) {
        mToolbar.setTitle(this.menuBean.title);
        mToolbar.setNavigationIcon(R.drawable.ic_navigation_drawer);
    }

    @OnClick(R.id.toolbar)
    public void showMenu() {
        Activity mainActivity = getActivity();
        if (mainActivity instanceof MainActivity) {
            ((MainActivity) mainActivity).toggleDrawer();
        }
    }
}
