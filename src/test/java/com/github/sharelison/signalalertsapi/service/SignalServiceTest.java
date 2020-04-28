package com.github.sharelison.signalalertsapi.service;

import com.github.sharelison.signalalertsapi.domain.prometheus.AlertmanagerAlert;
import com.github.sharelison.signalalertsapi.exception.SignalException;
import com.github.sharelison.signalalertsapi.testutil.Operation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.io.InputStream;

@RunWith(MockitoJUnitRunner.class)
public class SignalServiceTest {

    @Mock
    Runtime runtime;

    @Mock
    Process process;

    @Mock
    AlertmanagerAlert alertmanagerAlert;

    SignalService signalService = new SignalService();

    private static final String TEST_PHONE_NUMBER_SENDER= "0033366";
    private static final String TEST_PHONE_NUMBER_RECIPIENT= "00332244664411";
    private static final String SIGNAL_CLI = "myclient";
    private static final String VERIFICATION_CODE = "1234";
    private static final String TEST_MESSAGE = "Hello from test";

    @Before
    public void doBefore() throws IOException {
        ReflectionTestUtils.setField(signalService, "phoneNumberSender", TEST_PHONE_NUMBER_SENDER);
        ReflectionTestUtils.setField(signalService, "phoneNumberRecipient", TEST_PHONE_NUMBER_RECIPIENT);
        ReflectionTestUtils.setField(signalService, "signalCli", SIGNAL_CLI);
        ReflectionTestUtils.setField(signalService, "runtime", runtime);
    }

    @Test
    public void testRegisterShouldExecuteRegisterCommand() throws IOException, InterruptedException {
        testCommandExecution(new String[]{SIGNAL_CLI, "-u", TEST_PHONE_NUMBER_SENDER, "register"},
                Operation.REGISTER);
    }

    @Test
    public void testVerifyShouldExecuteVerifyCommand() throws IOException, InterruptedException {
        testCommandExecution(new String[]{SIGNAL_CLI, "-u", TEST_PHONE_NUMBER_SENDER, "verify", VERIFICATION_CODE},
                Operation.VERIFY);
    }

    @Test
    public void testSendShouldExecuteSendCommandForCustomMessages() throws IOException, InterruptedException {
        testCommandExecution(new String[]{SIGNAL_CLI, "-u", TEST_PHONE_NUMBER_SENDER, "send", "-m", TEST_MESSAGE, TEST_PHONE_NUMBER_RECIPIENT}, Operation.SEND);
    }

    @Test
    public void testSendAlertShouldExecuteCommandForPrometheusalert() throws IOException, InterruptedException {
        Mockito.when(alertmanagerAlert.toSignalAlertMessage()).thenReturn(TEST_MESSAGE);
        testCommandExecution(new String[]{SIGNAL_CLI, "-u", TEST_PHONE_NUMBER_SENDER, "send", "-m", TEST_MESSAGE, TEST_PHONE_NUMBER_RECIPIENT}, Operation.SEND_ALERT);
    }

    private void testCommandExecution(String[] expectedExecCmd, Operation operation) throws IOException, InterruptedException {
        Mockito.when(runtime.exec(expectedExecCmd)).thenReturn(process);
        Mockito.when(process.waitFor()).thenReturn(0);

        switch (operation) {
            case REGISTER:
                signalService.register();
                break;
            case VERIFY:
                signalService.verifyNumber(VERIFICATION_CODE);
                break;
            case SEND:
                signalService.sendMessage(TEST_MESSAGE);
                break;
            case SEND_ALERT:
                signalService.sendMessage(alertmanagerAlert);
                break;
        }

        Mockito.verify(runtime, Mockito.times(1)).exec(expectedExecCmd);
        Mockito.verify(process, Mockito.times(1)).waitFor();
    }
}
