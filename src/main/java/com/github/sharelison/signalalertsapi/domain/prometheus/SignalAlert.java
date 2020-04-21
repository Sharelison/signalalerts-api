package com.github.sharelison.signalalertsapi.domain.prometheus;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.stream.Collectors;

public class SignalAlert {

    @JsonProperty("alertmanager-alert")
    private Alertmanageralert alertmanageralert;

    public Alertmanageralert getAlertmanageralert(){
        return this.alertmanageralert;
    }

    public void setAlertmanageralert(Alertmanageralert alertmanageralert){
        this.alertmanageralert = alertmanageralert;
    }

    @Override
    public String toString() {
        return alertmanageralert.getAlerts().stream().map(alert -> alert.getAnnotations().getSummary()).collect(Collectors.joining());
    }

}