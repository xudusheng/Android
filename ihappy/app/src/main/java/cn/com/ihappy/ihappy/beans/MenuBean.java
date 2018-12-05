package cn.com.ihappy.ihappy.beans;

import java.util.List;

public class MenuBean {
    public enum MenuType {
        Reader,//阅读器
        Q2002,
        Video,//原生视频接口
        HotNews,//头条新闻
        Bizhi,//美女壁纸
        Shuaige,//帅哥壁纸
        Welfare//福利
    }

    public String title;//模块标题
    public String menuId;//模块ID
    public Integer type;//模块类型
    public MenuType menuType;//模块类型
    public String rootUrl;//基础URL
    public boolean enable;//模块是否可用，可用则显示，不可以则不显示。
    public List<SubMenuBean> subMenus;
    public List<String> unavailible_url_list;//不可用的URL列表

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;

    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRootUrl() {
        return rootUrl;
    }

    public void setRootUrl(String rootUrl) {
        this.rootUrl = rootUrl;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public List<SubMenuBean> getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(List<SubMenuBean> subMenus) {
        this.subMenus = subMenus;
    }

    public List<String> getUnavailible_url_list() {
        return unavailible_url_list;
    }

    public void setUnavailible_url_list(List<String> unavailible_url_list) {
        this.unavailible_url_list = unavailible_url_list;
    }

    public MenuType getMenuType() {
        if (menuType!=null) {
            return menuType;
        }
        MenuType mType;
        switch (type){
            case 0: mType = MenuType.Reader;
            case 1: mType = MenuType.Video;
            case 2:mType = MenuType.Q2002;
            case 3:mType = MenuType.HotNews;
            case 4:mType = MenuType.Bizhi;
            case 5:mType = MenuType.Shuaige;
            case 6:mType = MenuType.Welfare;
            default:mType = MenuType.HotNews;
        }
        this.setMenuType(mType);
        return mType;
    }

    public void setMenuType(MenuType menuType) {
        this.menuType = menuType;
    }
}