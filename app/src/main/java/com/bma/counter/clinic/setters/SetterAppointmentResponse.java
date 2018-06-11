package com.bma.counter.clinic.setters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SetterAppointmentResponse {


        @SerializedName("data")
        @Expose
        private Data data;
        @SerializedName("okay")
        @Expose
        private Boolean okay;
        @SerializedName("message")
        @Expose
        private String message;

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        public Boolean getOkay() {
            return okay;
        }

        public void setOkay(Boolean okay) {
            this.okay = okay;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

}
