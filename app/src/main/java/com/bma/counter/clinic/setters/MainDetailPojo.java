package com.bma.counter.clinic.setters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Montu on 4/5/2018.
 */

public class MainDetailPojo {


    @SerializedName("appointmentId")
    @Expose
    private Integer appointmentId;
    @SerializedName("patientName")
    @Expose
    private String patientName;
    @SerializedName("doctorId")
    @Expose
    private Integer doctorId;
    @SerializedName("doctorName")
    @Expose
    private String doctorName;
    @SerializedName("currQueueCount")
    @Expose
    private Integer currQueueCount;
    @SerializedName("qrCodeName")
    @Expose
    private String qrCodeName;
    @SerializedName("clinicRoomNumber")
    @Expose
    private String clinicRoomNumber;
    @SerializedName("appointmentUniqueToken")
    @Expose
    private String appointmentUniqueToken;
    @SerializedName("appointmentCreated")
    @Expose
    private String appointmentCreated;

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Integer getCurrQueueCount() {
        return currQueueCount;
    }

    public void setCurrQueueCount(Integer currQueueCount) {
        this.currQueueCount = currQueueCount;
    }

    public String getQrCodeName() {
        return qrCodeName;
    }

    public void setQrCodeName(String qrCodeName) {
        this.qrCodeName = qrCodeName;
    }

    public String getClinicRoomNumber() {
        return clinicRoomNumber;
    }

    public void setClinicRoomNumber(String clinicRoomNumber) {
        this.clinicRoomNumber = clinicRoomNumber;
    }

    public String getAppointmentUniqueToken() {
        return appointmentUniqueToken;
    }

    public void setAppointmentUniqueToken(String appointmentUniqueToken) {
        this.appointmentUniqueToken = appointmentUniqueToken;
    }

    public String getAppointmentCreated() {
        return appointmentCreated;
    }

    public void setAppointmentCreated(String appointmentCreated) {
        this.appointmentCreated = appointmentCreated;
    }

}
