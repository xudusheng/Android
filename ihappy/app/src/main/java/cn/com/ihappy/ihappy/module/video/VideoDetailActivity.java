package cn.com.ihappy.ihappy.module.video;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.com.ihappy.ihappy.R;
import cn.com.ihappy.ihappy.beans.video.HtmlVideoBean;
import cn.com.ihappy.ihappy.utils.L;


public class VideoDetailActivity extends AppCompatActivity {

    GridView mGridView;
    SimpleAdapter mSimpleAdapter;
    List<Map<String, String>> videoList = new ArrayList<>();


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);

        setTitle("试一下");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mGridView = findViewById(R.id.video_detail_gridview);
        mSimpleAdapter = new SimpleAdapter(getApplicationContext(), videoList, R.layout.item_video_button, new String[]{"name"}, new int[]{R.id.text_video_button});
        mGridView.setAdapter(mSimpleAdapter);
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
                String path = getIntent().getStringExtra("detail_url");
                Document document = null;
                try {
                    document = Jsoup.connect(path).timeout(5000).get();
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
                        videoList.add(hashMap);

                    }

                    mGridView.post(new Runnable() {
                        @Override
                        public void run() {
                            mSimpleAdapter.notifyDataSetChanged();
                        }
                    });


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
}
