package com.github.sharelison.signalalertsapi.domain.prometheus;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Annotations {

    @JsonProperty("summary")
    private String summary;
    @JsonProperty("description")
    private String description;

    public String getSummary(){
        return this.summary;
    }

    public void setSummary(String summary){
        this.summary = summary;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description){
        this.description = description;
    }

}