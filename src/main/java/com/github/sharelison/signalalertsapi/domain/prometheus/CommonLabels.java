package com.github.sharelison.signalalertsapi.domain.prometheus;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommonLabels {

    @JsonProperty("severity")
    private String severity;
    @JsonProperty("cluster")
    private String cluster;
    @JsonProperty("alertname")
    private String alertname;
    @JsonProperty("prometheus")
    private String prometheus;

    public String getSeverity(){
        return this.severity;
    }

    public void setSeverity(String severity){
        this.severity = severity;
    }

    public String getCluster(){
        return this.cluster;
    }

    public void setCluster(String cluster){
        this.cluster = cluster;
    }

    public String getAlertname(){
        return this.alertname;
    }

    public void setAlertname(String alertname){
        this.alertname = alertname;
    }

    public String getPrometheus(){
        return this.prometheus;
    }

    public void setPrometheus(String prometheus){
        this.prometheus = prometheus;
    }

}