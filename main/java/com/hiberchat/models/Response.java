package com.hiberchat.models;

public class Response {
    private String responseText;
    private int responseCode;

    public Response() {}

    public Response(String responseText, int responseCode) {
        this.responseText = responseText;
        this.responseCode = responseCode;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public int getResponseCode() {
        return responseCode;
    }
}
