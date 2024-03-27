package server.api;

import commons.Debt;
import commons.Event;
import commons.Participant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import server.database.EventRepository;
import server.database.ParticipantRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.OK;

//@DataJpaTest
public class DebtControllerTest {
    private TestDebtRepository repo;
    private EventRepository eventRepository = new TestEventRepository();
    private EventController eventController = new EventController(eventRepository);
    private ParticipantRepository participantRepository = new TestParticipantRepository();
    private ParticipantController participantController = new ParticipantController(participantRepository);
    private DebtController sut;

    @BeforeEach
    public void setup() {
        repo = new TestDebtRepository();
        sut = new DebtController(repo, eventController);
    }

    @Test
    public void addDebtWithValidAmount() {
        Event event = eventController.addEvent(new Event("testEvent")).getBody();
        Participant debtor = participantController.addParticipant(new Participant("debtor", event)).getBody();
        Participant creditor = participantController.addParticipant(new Participant("creditor", event)).getBody();

        double validAmount = 50;
        var actual = sut.addDebt(getDebt(event, debtor, creditor, validAmount));
        assertEquals(OK, actual.getStatusCode());
    }


    @Test
    public void databaseIsUsed() {
        Event event = eventController.addEvent(new Event("testEvent")).getBody();
        Participant debtor = participantController.addParticipant(new Participant("debtor", event)).getBody();
        Participant creditor = participantController.addParticipant(new Participant("creditor", event)).getBody();

        double validAmount = 10;
        sut.addDebt(getDebt(event, debtor, creditor, validAmount));
        assertTrue(repo.calledMethods.contains("save"));
    }

    private static Debt getDebt(Event event, Participant debtor, Participant creditor, double amount) {
        return new Debt(event, debtor, creditor, amount);
    }

}
