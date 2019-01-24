package cn.com.ihappy.ihappy.module.video;


import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.ihappy.ihappy.MainActivity;
import cn.com.ihappy.ihappy.R;
import cn.com.ihappy.ihappy.base.RxLazyFragment;
import cn.com.ihappy.ihappy.beans.video.HtmlVideoBean;
import cn.com.ihappy.ihappy.module.meizi.ImageAdapter;
import cn.com.ihappy.ihappy.utils.L;

public class MainVideoFragment extends RxLazyFragment {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.video_recyclerView)
    RecyclerView mRecyclerView;

    VideoAdapter mVideoAdapter;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_main_video;
    }

    @Override
    public void finishCreateView(Bundle state) {
        mToolbar.setTitle(this.menuBean.title);
        mToolbar.setNavigationIcon(R.drawable.ic_navigation_drawer);


        //使用瀑布流布局,第一个参数 spanCount 列数,第二个参数 orentation 排列方向
        StaggeredGridLayoutManager recyclerViewLayoutManager =
                new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        //线性布局Manager
//        LinearLayoutManager recyclerViewLayoutManager = new LinearLayoutManager(this);
//        recyclerViewLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //网络布局Manager
//        GridLayoutManager recyclerViewLayoutManager = new GridLayoutManager(this, 3);
        //给recyclerView设置LayoutManager

        mRecyclerView.setLayoutManager(recyclerViewLayoutManager);

        mVideoAdapter = new VideoAdapter(this.getContext());
        //设置adapter
        mRecyclerView.setAdapter(mVideoAdapter);

        mVideoAdapter.setOnItemClickListener(new VideoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                HtmlVideoBean videoBean = mVideoAdapter.videoList.get(position);
                L.e(videoBean.toString());
            }
        });

        this.fetchHtml();
    }

    @OnClick(R.id.toolbar)
    public void showMenu() {
        Activity mainActivity = getActivity();
        if (mainActivity instanceof MainActivity) {
            ((MainActivity) mainActivity).toggleDrawer();
        }
    }

    public void fetchHtml() {


        new Thread(new Runnable() {
            @Override
            public void run() {
                String path = "http://www.k2938.com/type/1.html";
                Document document = null;
                try {
                    document = Jsoup.connect(path).timeout(5000).get();
                    Log.e("e", document.html());
                    String htmlString = document.html();
                    Elements items = document.getElementsByClass("movie-item");
                    for (Element oneElement : items) {
                        Element a_link_element = oneElement.selectFirst("a");
                        String name = a_link_element.attr("title");
                        String href = a_link_element.attr("href");
                        Log.i("title", name);

                        Element image_element = oneElement.selectFirst("img");
                        String imageurl = image_element.attr("src");

                        HtmlVideoBean htmlVideoBean = new HtmlVideoBean();
                        htmlVideoBean.setName(name);
                        htmlVideoBean.setHref(href);
                        htmlVideoBean.setImageurl(imageurl);

                        mVideoAdapter.videoList.add(htmlVideoBean);
                        Log.i("image", htmlVideoBean.toString());

                    }

                    mRecyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            mVideoAdapter.notifyDataSetChanged();
                        }
                    });


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }


}
