package com.github.sharelison.signalalertsapi.controller;

import com.github.sharelison.signalalertsapi.domain.prometheus.AlertmanagerAlert;
import com.github.sharelison.signalalertsapi.service.SignalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
public class SignalAlertController {

    private final SignalService signalService;

    @Autowired
    public SignalAlertController(SignalService signalService) {
        this.signalService = signalService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/register")
    public String register() throws IOException, InterruptedException {
        signalService.register();
        return "Successfully registered number";
    }
    @RequestMapping(method = RequestMethod.GET, path = "/verify")
    public String verifyNumber(@RequestParam("code") String code) throws IOException, InterruptedException {
        signalService.verifyNumber(code);
        return "Successfully verified";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/send/prometheusalert")
    public String sendMessage(@RequestBody AlertmanagerAlert alert) throws IOException, InterruptedException {
        log.info("Alert prometheus message, {}", alert.toString());
        signalService.sendMessage(alert);
        return "Alert message sent";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/send", consumes = MediaType.ALL_VALUE)
    public String sendCustomMessage(@RequestBody String message) throws IOException, InterruptedException {
        signalService.sendMessage(message);
        return "Message sent";
    }
}
