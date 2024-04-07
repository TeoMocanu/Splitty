package server.api;

import commons.emails.Invitation;
import commons.emails.Reminder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import server.services.EmailerService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EmailerControllerTest {

    @Mock
    private EmailerService emailerService;

    @InjectMocks
    private EmailerController emailerController;

    @Captor
    private ArgumentCaptor<String> emailCaptor;

    @Captor
    private ArgumentCaptor<String> subjectCaptor;

    @Captor
    private ArgumentCaptor<String> textCaptor;

    private Invitation invitation;
    private Reminder reminder;

    @BeforeEach
    public void setUp() {
        invitation = new Invitation("mario@gmail.com","Cinema",1L);
        reminder = new Reminder("mihai@gmail.com", "Cinema", 30, "Mario");
    }

    @Test
    public void sendInvitationTest() {
        //Invitation invitation = new Invitation("mario@gmail.com", "Cinema", 1L);

        emailerController.sendMail(invitation);

        verify(emailerService).sendMail(emailCaptor.capture(), subjectCaptor.capture(), textCaptor.capture());

        String capturedEmail = emailCaptor.getValue();
        String capturedSubject = subjectCaptor.getValue();
        String capturedText = textCaptor.getValue();

        assertEquals("mario@gmail.com", capturedEmail);
        assertEquals("Invitation to join event", capturedSubject);
        assertTrue(capturedText.contains("You have been invited to join our 'Cinema' event."));
        assertTrue(capturedText.contains("Best wishes,"));
        assertTrue(capturedText.contains("Splitty - group 04"));
    }

    @Test
    public void sendReminderTest() {
        //Reminder reminder = new Reminder("debtor@example.com", "John Doe", "50.00", "Cinema Night");

        emailerController.sendMail(reminder);

        verify(emailerService).sendMail(emailCaptor.capture(), subjectCaptor.capture(), textCaptor.capture());

        String capturedEmail = emailCaptor.getValue();
        String capturedSubject = subjectCaptor.getValue();
        String capturedText = textCaptor.getValue();

        assertEquals("mihai@gmail.com", capturedEmail);
        assertEquals("Reminder to pay", capturedSubject);
        assertTrue(capturedText.contains("Good evening,"));
        assertTrue(capturedText.contains("Please pay as soon as possible."));
        assertTrue(capturedText.contains("Best wishes,"));
        assertTrue(capturedText.contains("Splitty - group 04"));
    }

}
