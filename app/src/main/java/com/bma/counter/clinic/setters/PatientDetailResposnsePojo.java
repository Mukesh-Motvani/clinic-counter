package com.bma.counter.clinic.setters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Montu on 4/5/2018.
 */

public class PatientDetailResposnsePojo {

    @SerializedName("iat")
    @Expose
    private Integer iat;
    @SerializedName("sub")
    @Expose
    private String sub;
    @SerializedName("exp")
    @Expose
    private Integer exp;
    @SerializedName("aud")
    @Expose
    private String aud;
    @SerializedName("iss")
    @Expose
    private String iss;
    @SerializedName("data")
    @Expose
    private String data;

    public Integer getIat() {
        return iat;
    }

    public void setIat(Integer iat) {
        this.iat = iat;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public String getAud() {
        return aud;
    }

    public void setAud(String aud) {
        this.aud = aud;
    }

    public String getIss() {
        return iss;
    }

    public void setIss(String iss) {
        this.iss = iss;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
