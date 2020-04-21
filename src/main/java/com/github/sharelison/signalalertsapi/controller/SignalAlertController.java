package com.github.sharelison.signalalertsapi.controller;

import com.github.sharelison.signalalertsapi.domain.prometheus.SignalAlert;
import com.github.sharelison.signalalertsapi.service.SignalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class SignalAlertController {

    private final SignalService signalService;

    @Autowired
    public SignalAlertController(SignalService signalService) {
        this.signalService = signalService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String verifyNumber(@RequestParam("code") String code) throws IOException, InterruptedException {
        signalService.verifyNumber(code);
        return "Successfully verified";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String sendMessage(@RequestBody SignalAlert signalAlert) throws IOException, InterruptedException {
        signalService.sendMessage(signalAlert);
        return "Alert message sent";
    }
}
