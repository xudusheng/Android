package cn.com.ihappy.ihappy.beans.video;

public class HtmlVideoBean {
    private String name;
    private String href;
    private String imageurl;
    private String update;
    private String other;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    @Override
    public String toString() {
        return "[视频名称："+this.name+"]"+"[图片地址："+this.imageurl+"]"+"[链接地址："+this.href+"]";
    }
}
