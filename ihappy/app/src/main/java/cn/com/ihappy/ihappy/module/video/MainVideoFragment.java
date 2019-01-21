package cn.com.ihappy.ihappy.module.video;


import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

public class MainVideoFragment extends RxLazyFragment {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.video_gridview)
    GridView mGridView;

    VideoAdapter mVideoAdapter = new VideoAdapter(MainVideoFragment.this);


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_main_video;
    }

    @Override
    public void finishCreateView(Bundle state) {
        mToolbar.setTitle(this.menuBean.title);
        mToolbar.setNavigationIcon(R.drawable.ic_navigation_drawer);

        mGridView.setAdapter(mVideoAdapter);
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

                    mGridView.post(new Runnable() {
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
