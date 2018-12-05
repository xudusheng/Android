package cn.com.ihappy.ihappy.beans;

public class ForceUpdateBean {
    public boolean enable;//是否需要升级
    public boolean isForce;//是否强制升级
    public String updateMessage;//升级信息
    public String url;//升级下载地址
    public String version;//版本号

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isForce() {
        return isForce;
    }

    public void setForce(boolean force) {
        isForce = force;
    }

    public String getUpdateMessage() {
        return updateMessage;
    }

    public void setUpdateMessage(String updateMessage) {
        this.updateMessage = updateMessage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
