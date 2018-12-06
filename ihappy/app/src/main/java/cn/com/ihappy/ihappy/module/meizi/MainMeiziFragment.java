package cn.com.ihappy.ihappy.module.meizi;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.ihappy.ihappy.MainActivity;
import cn.com.ihappy.ihappy.R;
import cn.com.ihappy.ihappy.base.RxLazyFragment;
import cn.com.ihappy.ihappy.beans.meizi.MeituBean;
import cn.com.ihappy.ihappy.utils.L;

public class MainMeiziFragment extends RxLazyFragment {
    private ArrayList<MeituBean> meituList;

    @BindView(R.id.meizi_recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_main_meizi;
    }

    @Override
    public void finishCreateView(Bundle state) {
        mToolbar.setTitle(this.menuBean.title);
        mToolbar.setNavigationIcon(R.drawable.ic_navigation_drawer);


        //使用瀑布流布局,第一个参数 spanCount 列数,第二个参数 orentation 排列方向
        StaggeredGridLayoutManager recyclerViewLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //线性布局Manager
//        LinearLayoutManager recyclerViewLayoutManager = new LinearLayoutManager(this);
//        recyclerViewLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //网络布局Manager
//        GridLayoutManager recyclerViewLayoutManager = new GridLayoutManager(this, 3);
        //给recyclerView设置LayoutManager

        mRecyclerView.setLayoutManager(recyclerViewLayoutManager);
        //读取数据源
        this.readMeiziResourc();
        ImageAdapter adapter = new ImageAdapter(this.meituList, this.getContext());
        //设置adapter
        mRecyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.toolbar)
    public void showMenu() {
        Activity mainActivity = getActivity();
        if (mainActivity instanceof MainActivity) {
            ((MainActivity) mainActivity).toggleDrawer();
        }
    }

    private void readMeiziResourc() {
        //meizi_zipai是我的文件名，这里应该根据具体文件更改
        InputStream input = getResources().openRawResource(R.raw.meizi_zipai);
        Reader reader = new InputStreamReader(input);
        StringBuffer stringBuffer = new StringBuffer();
        char b[] = new char[1024];
        int len = -1;
        try {
            while ((len = reader.read(b)) != -1) {
                stringBuffer.append(b);
            }
        } catch (IOException e) {
            L.e("IOException = " + e.getMessage());
        }
        String string = stringBuffer.toString();

        String[] splitResult = string.split(";");

        ArrayList<String> urlList = new ArrayList<>(Arrays.asList(splitResult));
        this.meituList = new ArrayList<>();
        for (String url : urlList) {
            MeituBean meituBean = new MeituBean();
            meituBean.image_src = url;
            this.meituList.add(meituBean);
        }
    }
}
