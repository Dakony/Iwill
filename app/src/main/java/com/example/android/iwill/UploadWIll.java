package com.example.android.iwill;

public class UploadWIll {
    public String title_will;
    public String url;

    public UploadWIll(){

    }

    public UploadWIll(String title_will, String url) {
        this.title_will = title_will;
        this.url = url;
    }

    public String getTitle_will() {
        return title_will;
    }

    public void setTitle_will(String title_will) {
        this.title_will = title_will;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
