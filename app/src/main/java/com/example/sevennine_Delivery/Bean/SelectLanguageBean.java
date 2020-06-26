package com.example.sevennine_Delivery.Bean;

public class SelectLanguageBean {

    String vendor,imageicon,lang_letter;

    int languageid;

    public SelectLanguageBean(String vendor, int languageid, String imageicon) {
        this.languageid=languageid;
        this.vendor = vendor;
        this.imageicon=imageicon;
    }
    public String getVendor() {
        return vendor;
    }


    public int getLanguageid() {
        return languageid;
    }

    public String getLang_letter() {
        return lang_letter;
    }

    public String getImageicon() {
        return imageicon;
    }

    public void setImageicon(String imageicon) {
        this.imageicon = imageicon;
    }


}




