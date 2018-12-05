package cn.com.ihappy.ihappy.manager;

import java.util.List;

import cn.com.ihappy.ihappy.beans.ConfigBean;
import cn.com.ihappy.ihappy.beans.ForceUpdateBean;
import cn.com.ihappy.ihappy.beans.MenuBean;

public class ConfigManager {
    private static ConfigManager mShareManager;

    public ForceUpdateBean forceUpdateBean;
    public List<MenuBean> menuList;

    //单例
    public static ConfigManager shareManager() {
        if (mShareManager == null) {
            mShareManager = new ConfigManager();
        }
        return mShareManager;
    }

    public void setConfigBean(ConfigBean configBean) {
        this.setForceUpdateBean(configBean.forceUpdate);
        this.setMenuList(configBean.menus);
    }

    public ForceUpdateBean getForceUpdateBean() {
        return forceUpdateBean;
    }

    private void setForceUpdateBean(ForceUpdateBean forceUpdateBean) {
        this.forceUpdateBean = forceUpdateBean;
    }

    public List<MenuBean> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<MenuBean> menuList) {
        this.menuList = menuList;
    }
}
