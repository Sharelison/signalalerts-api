package com.github.sharelison.signalalertsapi.domain.prometheus;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sharelison.signalalertsapi.exception.SignalException;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AlertmanagerAlert {

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

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new SignalException(e.getMessage());
        }
    }

    public String toSignalAlertMessage() {
        StringBuilder alertMessageSb = new StringBuilder("ALERT\n\n");

        this.getAlerts().forEach(a -> {
            alertMessageSb.append("Name: ").append(a.getLabels().getAlertname())
                    .append("\nCluster: ").append(a.getLabels().getCluster())
                    .append("\nStatus: ").append(a.getStatus())
                    .append("\nSeverity: ").append(a.getLabels().getSeverity())
                    .append("\nStarts at: ").append(a.getStartsAt())
                    .append("\nEnds at: ").append(a.getEndsAt())
                    .append("\nDescription: ").append(a.getAnnotations().getDescription())
                    .append("\n\n");
        });

        return alertMessageSb.toString();
    }

}