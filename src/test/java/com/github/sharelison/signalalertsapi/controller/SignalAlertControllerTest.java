package com.github.sharelison.signalalertsapi.controller;

import com.github.sharelison.signalalertsapi.domain.prometheus.AlertmanagerAlert;
import com.github.sharelison.signalalertsapi.service.SignalService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class SignalAlertControllerTest {

    @Mock
    SignalService signalService;

    @InjectMocks
    SignalAlertController controller;

    private static final String VERIFICATION_CODE = "1234";

    @Test
    public void testRegisterShouldCallRegister() throws IOException, InterruptedException {
        controller.register();
        Mockito.verify(signalService, Mockito.times(1)).register();
    }

    @Test
    public void testVerifyShouldCallVerify() throws IOException, InterruptedException {
       controller.verifyNumber(VERIFICATION_CODE);
       Mockito.verify(signalService, Mockito.times(1)).verifyNumber(VERIFICATION_CODE);
    }

    @Test
    public void testSendShouldSendCustomMessage() throws IOException, InterruptedException {
       controller.sendCustomMessage("message134");
       Mockito.verify(signalService, Mockito.times(1)).sendMessage("message134");
    }

    @Test
    public void testSendShouldSendAlert() throws IOException, InterruptedException {
        AlertmanagerAlert myalert = new AlertmanagerAlert();
        controller.sendMessage(myalert);
        Mockito.verify(signalService, Mockito.times(1)).sendMessage(myalert);
    }
}
