package kkm.com.core.model.response.themeParkResponse.theme_park_maincategories;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseThemeParkMainCategories {

    @SerializedName("date")
    private String date;

    @SerializedName("result")
    private String result;

    @SerializedName("session")
    private String session;

    @SerializedName("error")
    private String error;

    @SerializedName("addinfo")
    private List<AddinfoItem> addinfo;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<AddinfoItem> getAddinfo() {
        return addinfo;
    }

    public void setAddinfo(List<AddinfoItem> addinfo) {
        this.addinfo = addinfo;
    }

    @Override
    public String toString() {
        return
                "ResponseThemeParkMainCategories{" +
                        "date = '" + date + '\'' +
                        ",result = '" + result + '\'' +
                        ",session = '" + session + '\'' +
                        ",error = '" + error + '\'' +
                        ",addinfo = '" + addinfo + '\'' +
                        "}";
    }
}