package cn.com.ihappy.ihappy.beans;

public class SubMenuBean {

    public String title;

    public String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static class ResponseBean <T> {
        private Integer error_code;
        private String reason;
        private T result;

    }
}
