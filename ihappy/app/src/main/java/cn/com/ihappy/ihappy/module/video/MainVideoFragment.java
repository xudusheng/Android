package cn.com.ihappy.ihappy.module.video;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.ihappy.ihappy.MainActivity;
import cn.com.ihappy.ihappy.R;
import cn.com.ihappy.ihappy.base.RxLazyFragment;
import cn.com.ihappy.ihappy.utils.L;

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

        new XPathParse().fetchHtml();
    }

    @OnClick(R.id.toolbar)
    public void showMenu() {
        Activity mainActivity = getActivity();
        if (mainActivity instanceof MainActivity) {
            ((MainActivity) mainActivity).toggleDrawer();
        }
    }
}


//异步获取网络xml
class XPathParse  {
    public void fetchHtml() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String path = "http://www.k2938.com/type/1.html";
                org.jsoup.nodes.Document document = null;
                try {
                    document = Jsoup.connect(path).timeout(5000).get();
                    Log.e("e", document.html());
                    String htmlString = document.html();
                    Elements items = document.getElementsByClass("movie-item");
                    for (org.jsoup.nodes.Element oneElement : items) {
                        org.jsoup.nodes.Element a_link_element = oneElement.selectFirst("a");
                        String name = a_link_element.attr("title");
                        String href = a_link_element.attr("href");
                        Log.i("title", name);

                        org.jsoup.nodes.Element image_element = oneElement.selectFirst("img");
                        String imageurl = image_element.attr("src");

                        Log.i("image", imageurl);


                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
