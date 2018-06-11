package com.bma.counter.clinic.setters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("appointmentId")
    @Expose
    private Integer appointmentId;
    @SerializedName("approxAppointmentTime")
    @Expose
    private String approxAppointmentTime;
    @SerializedName("clinicNumber")
    @Expose
    private String clinicNumber;
    @SerializedName("doctorName")
    @Expose
    private String doctorName;
    @SerializedName("doctorId")
    @Expose
    private Integer doctorId;
    @SerializedName("currQueueCount")
    @Expose
    private Integer currQueueCount;
    @SerializedName("doctorStatus")
    @Expose
    private String doctorStatus;
    @SerializedName("queueSize")
    @Expose
    private Integer queueSize;
    @SerializedName("totalCheckupTime")
    @Expose
    private Integer totalCheckupTime;
    @SerializedName("avgTime")
    @Expose
    private Integer avgTime;
    @SerializedName("avgTimeSet")
    @Expose
    private Boolean avgTimeSet;

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getApproxAppointmentTime() {
        return approxAppointmentTime;
    }

    public void setApproxAppointmentTime(String approxAppointmentTime) {
        this.approxAppointmentTime = approxAppointmentTime;
    }

    public String getClinicNumber() {
        return clinicNumber;
    }

    public void setClinicNumber(String clinicNumber) {
        this.clinicNumber = clinicNumber;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getCurrQueueCount() {
        return currQueueCount;
    }

    public void setCurrQueueCount(Integer currQueueCount) {
        this.currQueueCount = currQueueCount;
    }

    public String getDoctorStatus() {
        return doctorStatus;
    }

    public void setDoctorStatus(String doctorStatus) {
        this.doctorStatus = doctorStatus;
    }

    public Integer getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(Integer queueSize) {
        this.queueSize = queueSize;
    }

    public Integer getTotalCheckupTime() {
        return totalCheckupTime;
    }

    public void setTotalCheckupTime(Integer totalCheckupTime) {
        this.totalCheckupTime = totalCheckupTime;
    }

    public Integer getAvgTime() {
        return avgTime;
    }

    public void setAvgTime(Integer avgTime) {
        this.avgTime = avgTime;
    }

    public Boolean getAvgTimeSet() {
        return avgTimeSet;
    }

    public void setAvgTimeSet(Boolean avgTimeSet) {
        this.avgTimeSet = avgTimeSet;
    }


}
