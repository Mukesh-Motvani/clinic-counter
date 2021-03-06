package com.bma.counter.clinic.appSingletone;

import com.bma.counter.clinic.setters.SetterGeneralClass;

public class ModelSiteOption {
    private static final ModelSiteOption ourInstance = new ModelSiteOption();

    private SetterGeneralClass setterGeneralClass;
    private String byNote;
    private String emergencyNote;
    private String domainPath;
    private String emailId;
    private String MobileNumber;
    private String defaultLogoUrl;
    private String thanksNote;
    private String appOver;
    private String supportNumber;
    private String supportEmail;
    private String mainLogo;
    private String supportNote;

    public String getSupportNote() {
        return supportNote;
    }

    public void setSupportNote(String supportNote) {
        this.supportNote = supportNote;
    }

    public static ModelSiteOption getInstance() {
        return ourInstance;
    }

    private ModelSiteOption() {
    }
    public SetterGeneralClass getSetterGeneralClass() {
        return setterGeneralClass;
    }

    public void setSetterGeneralClass(SetterGeneralClass setterGeneralClass) {
        this.setterGeneralClass = setterGeneralClass;
    }


    public String getMainLogo() {
        return mainLogo;
    }

    public void setMainLogo(String mainLogo) {
        this.mainLogo = mainLogo;
    }

    public String getThanksNote() {
        return thanksNote;
    }

    public void setThanksNote(String thanksNote) {
        this.thanksNote = thanksNote;
    }

    public String getDefaultLogoUrl() {
        return defaultLogoUrl;
    }

    public void setDefaultLogoUrl(String defaultLogoUrl) {
        this.defaultLogoUrl = defaultLogoUrl;
    }

    public String getByNote() {
        return byNote;
    }

    public void setByNote(String byNote) {
        this.byNote = byNote;
    }

    public String getEmergencyNote() {
        return emergencyNote;
    }

    public void setEmergencyNote(String emergencyNote) {
        this.emergencyNote = emergencyNote;
    }

    public String getDomainPath() {
        return domainPath;
    }

    public void setDomainPath(String domainPath) {
        this.domainPath = domainPath;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getAppOver() {
        return appOver;
    }

    public void setAppOver(String appOver) {
        this.appOver = appOver;
    }

    public String getSupportNumber() {
        return supportNumber;
    }

    public void setSupportNumber(String supportNumber) {
        this.supportNumber = supportNumber;
    }

    public String getSupportEmail() {
        return supportEmail;
    }

    public void setSupportEmail(String supportEmail) {
        this.supportEmail = supportEmail;
    }
}
