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
        return alertmanageralert.getAlerts().stream().map(alert -> alert.getAnnotations().getSummary()).collect(Collectors.joining("\n"));
    }

    public String toSignalAlertMessage() {
        StringBuilder alertMessageSb = new StringBuilder("ALERT\n\n");

        if(alertmanageralert != null) {
            alertmanageralert.getAlerts().forEach(a -> {
                alertMessageSb.append("Name: ").append(a.getLabels().getAlertname())
                        .append("\nCluster: ").append(a.getLabels().getCluster())
                        .append("\nStatus: ").append(a.getStatus())
                        .append("\nSeverity: ").append(a.getLabels().getSeverity())
                        .append("\nStarts at: ").append(a.getStartsAt())
                        .append("\nEnds at: ").append(a.getEndsAt())
                        .append("\nDescription: ").append(a.getAnnotations().getDescription())
                        .append("\n\n");
            });
        }

        return alertMessageSb.toString();
    }

}