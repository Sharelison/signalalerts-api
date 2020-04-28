package com.github.sharelison.signalalertsapi.domain.prometheus;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
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
            return new ObjectMapper()
                    .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                    .writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.warn("Could not serialize object with objectmapper, {}", e.getMessage(), e);
        }

        return "Could not serialize alert to string";
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