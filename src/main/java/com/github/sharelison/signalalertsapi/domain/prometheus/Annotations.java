package com.github.sharelison.signalalertsapi.domain.prometheus;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Annotations {

    @JsonProperty("summary")
    private String summary;
    @JsonProperty("message")
    private String message;

    public String getSummary(){
        return this.summary;
    }

    public void setSummary(String summary){
        this.summary = summary;
    }

    public String getMessage(){
        return this.message;
    }

    public void setMessage(String description){
        this.message = description;
    }

}