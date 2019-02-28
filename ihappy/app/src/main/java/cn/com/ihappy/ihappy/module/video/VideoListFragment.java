package cn.com.ihappy.ihappy.module.video;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

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
import cn.com.ihappy.ihappy.beans.SubMenuBean;
import cn.com.ihappy.ihappy.beans.video.HtmlVideoBean;
import cn.com.ihappy.ihappy.utils.L;

public class VideoListFragment extends Fragment {

    private RecyclerView mRecyclerView;

    private VideoAdapter mVideoAdapter;

    private String baseUrl;
    private SubMenuBean subMenuBean;

    private String firstPageUrl;
    private String nextPageUrl;

    private SmartRefreshLayout mSmartRefreshLayout;
    public VideoListFragment(String baseUrl, SubMenuBean subMenuBean) {
        super();
        this.baseUrl = baseUrl;
        this.subMenuBean = subMenuBean;
        this.firstPageUrl = this.subMenuBean.url;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_list, container, false);
        mSmartRefreshLayout = view.findViewById(R.id.srl_video_list);
        mRecyclerView = view.findViewById(R.id.rv_video_list);
        this.createUI();
        return view;
    }

    private void createUI() {

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
        mVideoAdapter = new VideoAdapter(getContext());
        //设置adapter
        mRecyclerView.setAdapter(mVideoAdapter);

        mVideoAdapter.setOnItemClickListener(new VideoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                HtmlVideoBean videoBean = mVideoAdapter.videoList.get(position);
                L.e(videoBean.toString());
                Intent intent = new Intent(getContext(), VideoDetailActivity.class);
                intent.putExtra("detail_url", videoBean.getHref());
                intent.putExtra("title", videoBean.getName());
                startActivity(intent);

            }
        });

        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                fetchHtml(true);
            }
        });
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                fetchHtml(false);
            }
        });

        mSmartRefreshLayout.setEnableRefresh(true);
        fetchHtml(true);

    }

    public void fetchHtml(final boolean isFirstPage) {

        String url = isFirstPage?this.firstPageUrl:this.nextPageUrl;
        if (url.startsWith("http") == false) {
            url = this.baseUrl + url;
        }

        final int position = mVideoAdapter.videoList.size();
        final String finalUrl = url;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Document document = null;
                try {
                    document = Jsoup.connect(finalUrl).timeout(5000).get();

                    String htmlString = document.html();
                    Log.i("htmlString", htmlString);

                    //解析下一页的地址
                    Elements elements_a = document.getElementsByTag("a");
                    for (Element element_a : elements_a) {
                        String text = element_a.text();
                        if (text.equals("下一页")) {
                            nextPageUrl = element_a.attr("href");
                            break;
                        }
                    }


                    if (isFirstPage) {
                        mVideoAdapter.videoList.clear();
                    }
                    Elements items = document.getElementsByClass("movie-item");
                    for (Element oneElement : items) {
                        Element a_link_element = oneElement.selectFirst("a");
                        String name = a_link_element.attr("title");
                        String href = a_link_element.attr("href");
                        Log.i("title", name);

                        Element image_element = oneElement.selectFirst("img");
                        String imageurl = image_element.attr("src");

                        if (href.startsWith("http") == false) {
                            href = baseUrl + href;
                        }

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
                            if (isFirstPage){
                                mSmartRefreshLayout.finishRefresh();
                            }else  {
                                mSmartRefreshLayout.finishLoadMore();
                            }
                            mVideoAdapter.notifyItemInserted(position);
//                            mRecyclerView.
                        }
                    });


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }


}
