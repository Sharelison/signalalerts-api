package com.github.sharelison.signalalertsapi.service;

import com.github.sharelison.signalalertsapi.domain.prometheus.SignalAlert;
import com.github.sharelison.signalalertsapi.exception.SignalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
@Service
public class SignalService {

    private static final String REGISTER_CMD = "%s -u %s register";
    private final Runtime runtime;

    @Value("${signal.phonenumber.sender}")
    private String phoneNumberSender;

    @Value("${signal.phonenumber.recipient}")
    private String phoneNumberRecipient;

    @Value("${signal.client}")
    private String signalCli;

    public SignalService() {
        runtime = Runtime.getRuntime();
    }

    public void register() throws IOException, InterruptedException {
        log.info("registering number {}", phoneNumberSender);
        Process process = runtime.exec(new String[]{signalCli, "-u", phoneNumberSender, "register"});
        handleProcessResult(process, "Failed to register number");
        log.info("registered successfully");
    }

    public void verifyNumber(String verificationCode) throws IOException, InterruptedException {
        log.info("verifying number {}", phoneNumberSender);
        Process process = runtime.exec(new String[]{signalCli, "-u", phoneNumberSender, "verify", verificationCode});
        handleProcessResult(process, "Failed to verify number");
        log.info("verified successfully");
    }

    public void sendMessage(SignalAlert message) throws IOException, InterruptedException {
       sendMessage(message.toSignalAlertMessage());
    }

    public void sendMessage(String message) throws IOException, InterruptedException {
        log.info("sending message");
        Process process = runtime.exec(new String[]{signalCli, "-u", phoneNumberSender, "send", "-m", message, phoneNumberRecipient});
        handleProcessResult(process, "Failed to send message");
        log.info("message sent");
    }

    public void handleProcessResult(Process process, String onErrorMessage) throws InterruptedException, IOException {
        if(process.waitFor() != 0) {
            log.error("error from signal client command, output:");
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line;
            while((line = br.readLine()) != null) {
                log.error(line);
            }
            throw new SignalException(onErrorMessage);
        }
    }
}
