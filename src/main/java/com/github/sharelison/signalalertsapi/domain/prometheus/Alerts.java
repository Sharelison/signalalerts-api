package com.github.sharelison.signalalertsapi.domain.prometheus;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Alerts {

    @JsonProperty("generatorURL")
    private String generatorURL;
    @JsonProperty("annotations")
    private Annotations annotations;
    @JsonProperty("startsAt")
    private String startsAt;
    @JsonProperty("endsAt")
    private String endsAt;
    @JsonProperty("status")
    private String status;
    @JsonProperty("labels")
    private Labels labels;

    public String getGeneratorURL(){
        return this.generatorURL;
    }

    public void setGeneratorURL(String generatorURL){
        this.generatorURL = generatorURL;
    }

    public Annotations getAnnotations(){
        return this.annotations;
    }

    public void setAnnotations(Annotations annotations){
        this.annotations = annotations;
    }

    public String getStartsAt(){
        return this.startsAt;
    }

    public void setStartsAt(String startsAt){
        this.startsAt = startsAt;
    }

    public String getEndsAt(){
        return this.endsAt;
    }

    public void setEndsAt(String endsAt){
        this.endsAt = endsAt;
    }

    public String getStatus(){
        return this.status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public Labels getLabels(){
        return this.labels;
    }

    public void setLabels(Labels labels){
        this.labels = labels;
    }

}