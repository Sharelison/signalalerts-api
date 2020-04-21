package com.github.sharelison.signalalertsapi.service;

import com.github.sharelison.signalalertsapi.domain.prometheus.SignalAlert;
import com.github.sharelison.signalalertsapi.exception.SignalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class SignalService {

    private static final String SIGNAL_CLI = "signal-cli";
    private static final String REGISTER_CMD = "%s -u %s register";
    private static final String VERIFY_CMD = "%s -u %s verify %s";
    private static final String SEND_MSG_CMD = "%s -u %s send -m %s %s";

    private final Runtime runtime;

    @Value("${signal.phonenumber.sender}")
    private String phoneNumberSender;

    @Value("${signal.phonenumber.recipient}")
    private String phoneNumberRecipient;

    public SignalService() throws IOException, InterruptedException {
        runtime = Runtime.getRuntime();
        registerNumber();
    }

    private void registerNumber() throws IOException, InterruptedException {
        log.info("registering number {}", phoneNumberSender);
        Process process = runtime.exec(String.format(REGISTER_CMD, SIGNAL_CLI, phoneNumberSender));
        handleProcessResult(process, "Failed to register number");
        log.info("registered successfully");
    }

    public void verifyNumber(String verificationCode) throws IOException, InterruptedException {
        log.info("verifying number {}", phoneNumberSender);
        Process process = runtime.exec(String.format(VERIFY_CMD, SIGNAL_CLI, phoneNumberSender, verificationCode));
        handleProcessResult(process, "Failed to verify number");
        log.info("verified successfully");
    }

    public void sendMessage(SignalAlert message) throws IOException, InterruptedException {
        log.info("sending message");
        Process process = runtime.exec(String.format(SEND_MSG_CMD, SIGNAL_CLI, phoneNumberSender, message, phoneNumberRecipient));
        handleProcessResult(process, "Failed to send message");
        log.info("message sent");
    }

    public void handleProcessResult(Process process, String onErrorMessage) throws InterruptedException {
        if(process.waitFor() != 0)
            throw new SignalException(onErrorMessage);
    }
}
