package cn.com.ihappy.ihappy.module.video;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.ihappy.ihappy.R;
import cn.com.ihappy.ihappy.beans.video.VideoItemBean;
import cn.com.ihappy.ihappy.utils.L;


public class VideoDetailActivity extends AppCompatActivity {

    private Context mContext;
   private GridView mGridView;
    private WebView mWebView;
    VideoDetailAdapter mDetailAdapter;
    ArrayList<VideoItemBean> videoList = new ArrayList<>();
    private String baseUrl;
    private String detail_url;

    private int currentPosition;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);

        mContext = this;
        mGridView = findViewById(R.id.video_detail_gridview);
        mWebView = findViewById(R.id.wv_video_detail);
        setTitle(getIntent().getStringExtra("title"));
        this.baseUrl = getIntent().getStringExtra("base_url");
        this.detail_url = getIntent().getStringExtra("detail_url");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentPosition = position;
                mDetailAdapter.notifyDataSetChanged();
                fetchplayer();
            }
        });
        mDetailAdapter = new VideoDetailAdapter();
        mGridView.setAdapter(mDetailAdapter);
        //拉取详情
        fetchHtml();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void fetchHtml() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                String url = detail_url;
                if (url.startsWith("http") == false) {
                    url = baseUrl + url;
                }

                Document document = null;
                try {
                    document = Jsoup.connect(url).timeout(5000).get();
                    Elements summary_elements = document.getElementsByClass("summary");
                    if (summary_elements.size() > 0) {
                        Element summary_element = summary_elements.first();
                        String summary = summary_element.text();
                        L.e(summary);
                    }

                    Elements items = document.getElementsByClass("dslist-group-item");
                    for (Element oneElement : items) {
                        Element a_link_element = oneElement.selectFirst("a");
                        String name = a_link_element.text();
                        String href = a_link_element.attr("href");
                        L.e("name = " + name + "            href = " + href);
                        Map<String, String> hashMap = new HashMap<>();
                        hashMap.put("name", name);
                        hashMap.put("href", href);
                        VideoItemBean itemBean = new VideoItemBean();
                        itemBean.item_href = href;
                        itemBean.item_name = name;
                        videoList.add(itemBean);
                    }

                    mGridView.post(new Runnable() {
                        @Override
                        public void run() {
                            currentPosition = 0;
                            mDetailAdapter.notifyDataSetChanged();
                            fetchplayer();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void fetchplayer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                VideoItemBean itemBean = videoList.get(currentPosition);
                String url = itemBean.item_href;
                if (url.startsWith("http") == false) {
                    url = baseUrl + url;
                }

                Document document = null;
                try {
                    document = Jsoup.connect(url).timeout(5000).get();
                    Elements player_iframes = document.getElementsByTag("iframe");

                    if (player_iframes.size() > 0) {
                        Element player_iframe = player_iframes.first();
                        final String video_src = player_iframe.attr("src");
                        L.e("video_src = " + video_src);

                        mWebView.post(new Runnable() {
                            @Override
                            public void run() {
                                mWebView.loadUrl(video_src);
                            }
                        });
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private class VideoDetailAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return videoList.size();
        }

        @Override
        public Object getItem(int position) {
            return videoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            L.e("getView = " + position);
            View item_view = null;
            if (convertView != null)
                item_view = convertView;
            else
                item_view = View.inflate(mContext, R.layout.item_video_button, null);

            VideoItemBean itemBean = videoList.get(position);
            TextView text_video_button = item_view.findViewById(R.id.text_video_button);
            text_video_button.setText(itemBean.item_name);
            if (position == currentPosition) {
                text_video_button.setTextColor(R.color.red);
            } else {
                text_video_button.setTextColor(R.color.black_alpha_60);
            }
            return item_view;
        }

    }
}
