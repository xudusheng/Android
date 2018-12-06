package cn.com.ihappy.ihappy.module.video;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.components.support.RxFragment;

import cn.com.ihappy.ihappy.R;
import cn.com.ihappy.ihappy.base.RxLazyFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainVideoFragment extends RxLazyFragment {


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_main_video;
    }

    @Override
    public void finishCreateView(Bundle state) {

    }
}
