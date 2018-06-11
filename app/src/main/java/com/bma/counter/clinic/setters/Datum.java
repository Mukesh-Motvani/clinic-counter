package com.bma.counter.clinic.setters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("optionId")
    @Expose
    private Integer optionId;
    @SerializedName("optionName")
    @Expose
    private String optionName;
    @SerializedName("optionVal")
    @Expose
    private String optionVal;

    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public String getOptionVal() {
        return optionVal;
    }

    public void setOptionVal(String optionVal) {
        this.optionVal = optionVal;
    }

}
