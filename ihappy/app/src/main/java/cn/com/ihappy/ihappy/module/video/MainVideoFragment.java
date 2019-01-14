package cn.com.ihappy.ihappy.module.video;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

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

    public void fetchHtml () throws Exception {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();//实例化DocumentBuilderFactory对象
        DocumentBuilder bulider = dbf.newDocumentBuilder();
        Document doc = bulider.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("http://www.k2938.com"));


        XPathFactory factory = XPathFactory.newInstance();//实例化XPathFactory对象
        XPath xpath = factory.newXPath();


//        XPathExpression compile = xpath.compile("//student");//选取student节点
//        NodeList nodes = (NodeList)compile.evaluate(doc, XPathConstants.NODESET);//获取student节点的所有节点
//        for (int i = 0; i < nodes.getLength(); i ++) {
//            NodeList childNodes = nodes.item(i).getChildNodes(); //获取一个student节点所有的子节点，返回集合
//            //遍历所有子节点，获取节点的名称与数据，将其存与Students对象的属性进行匹配并存入到该对象
//        }

        XPathExpression pageXpath = xpath.compile("//div[@class =\"pages\"]//ul");//选取pages节点
        NodeList page_nodes = (NodeList)pageXpath.evaluate(doc, XPathConstants.NODESET);
        if (page_nodes.getLength() > 0) {
            Node nextPage_node = page_nodes.item(0);
            NodeList childElements = nextPage_node.getChildNodes();
        }

    }
}
