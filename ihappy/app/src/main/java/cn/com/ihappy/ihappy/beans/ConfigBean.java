package cn.com.ihappy.ihappy.beans;

import android.view.Menu;

import java.util.List;

public class ConfigBean {
    public ForceUpdateBean forceUpdate;
    public List<MenuBean> menus;

    public ForceUpdateBean getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(ForceUpdateBean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public List<MenuBean> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuBean> menus) {
        this.menus = menus;
    }
}


