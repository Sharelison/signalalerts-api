package com.github.sharelison.signalalertsapi.domain.prometheus;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Alertmanageralert {

    @JsonProperty("alerts")
    private List<Alerts> alerts;
    @JsonProperty("commonLabels")
    private CommonLabels commonLabels;
    @JsonProperty("externalURL")
    private String externalURL;
    @JsonProperty("groupLabels")
    private GroupLabels groupLabels;
    @JsonProperty("receiver")
    private String receiver;
    @JsonProperty("version")
    private String version;
    @JsonProperty("commonAnnotations")
    private CommonAnnotations commonAnnotations;
    @JsonProperty("groupKey")
    private String groupKey;
    @JsonProperty("status")
    private String status;

    public List<Alerts> getAlerts(){
        return this.alerts;
    }

    public void setAlerts(List<Alerts> alerts){
        this.alerts = alerts;
    }

    public CommonLabels getCommonLabels(){
        return this.commonLabels;
    }

    public void setCommonLabels(CommonLabels commonLabels){
        this.commonLabels = commonLabels;
    }

    public String getExternalURL(){
        return this.externalURL;
    }

    public void setExternalURL(String externalURL){
        this.externalURL = externalURL;
    }

    public GroupLabels getGroupLabels(){
        return this.groupLabels;
    }

    public void setGroupLabels(GroupLabels groupLabels){
        this.groupLabels = groupLabels;
    }

    public String getReceiver(){
        return this.receiver;
    }

    public void setReceiver(String receiver){
        this.receiver = receiver;
    }

    public String getVersion(){
        return this.version;
    }

    public void setVersion(String version){
        this.version = version;
    }

    public CommonAnnotations getCommonAnnotations(){
        return this.commonAnnotations;
    }

    public void setCommonAnnotations(CommonAnnotations commonAnnotations){
        this.commonAnnotations = commonAnnotations;
    }

    public String getGroupKey(){
        return this.groupKey;
    }

    public void setGroupKey(String groupKey){
        this.groupKey = groupKey;
    }

    public String getStatus(){
        return this.status;
    }

    public void setStatus(String status){
        this.status = status;
    }

}