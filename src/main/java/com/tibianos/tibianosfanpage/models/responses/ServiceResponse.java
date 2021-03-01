package com.tibianos.tibianosfanpage.models.responses;

import com.fasterxml.jackson.databind.JsonNode;

public class ServiceResponse {
    
    private String responseCode;
    private JsonNode responseMessage;

    public ServiceResponse(String reponseCode, JsonNode responseMessage) {
        this.responseCode = reponseCode;
        this.responseMessage = responseMessage;
    }

    public String getReponseCode() {
        return responseCode;
    }

    public void setReponseCode(String reponseCode) {
        this.responseCode = reponseCode;
    }

    public JsonNode getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(JsonNode responseMessage) {
        this.responseMessage = responseMessage;
    }

    @Override
    public String toString() {
        return "ServiceResponse [responseCode=" + responseCode + ", responseMessage=" + responseMessage + "]";
    }
    
}
