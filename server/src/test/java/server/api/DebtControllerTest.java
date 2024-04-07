package server.api;

import commons.Debt;
import commons.Event;
import commons.Participant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.database.EventRepository;
import server.database.ParticipantRepository;
import server.implementations.EventServiceImplementation;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.*;

//@DataJpaTest
public class DebtControllerTest {
    private TestDebtRepository repo;
    private EventRepository eventRepository = new TestEventRepository();
    private EventServiceImplementation eventServiceImplementation = new EventServiceImplementation(eventRepository);
    private ParticipantRepository participantRepository = new TestParticipantRepository();
    private ParticipantController participantController = new ParticipantController(participantRepository);
    private DebtController sut;

    @BeforeEach
    public void setup() {
        repo = new TestDebtRepository();
        sut = new DebtController(repo, eventServiceImplementation);
    }

    @Test
    public void addDebtWithValidAmount() {
        Event event = eventServiceImplementation.addEvent(new Event("testEvent")).getBody();
        Participant debtor = participantController.addParticipant(new Participant("debtor", event)).getBody();
        Participant creditor = participantController.addParticipant(new Participant("creditor", event)).getBody();

        double validAmount = 50;
        Debt debt = getDebt(event, debtor, creditor, validAmount);
        var addResponse = sut.addDebt(debt);
        assertEquals(OK, addResponse.getStatusCode());
        assertEquals(debt, addResponse.getBody());
    }

    @Test
    public void addDebtWithInvalidEventId() {
        Event event = eventServiceImplementation.addEvent(new Event("testEvent")).getBody();
        Participant debtor = participantController.addParticipant(new Participant("debtor", event)).getBody();
        Participant creditor = participantController.addParticipant(new Participant("creditor", event)).getBody();
        event.setId(-1);

        double validAmount = 50;
        Debt debt = getDebt(event, debtor, creditor, validAmount);
        var addResponse = sut.addDebt(debt);
        assertEquals(BAD_REQUEST, addResponse.getStatusCode());
    }

    @Test
    public void editValidDebt() {
        Event event = eventServiceImplementation.addEvent(new Event("testEvent")).getBody();
        Participant debtor = participantController.addParticipant(new Participant("debtor", event)).getBody();
        Participant creditor = participantController.addParticipant(new Participant("creditor", event)).getBody();

        double validAmount = 50;
        Debt debt = sut.addDebt(new Debt(event, debtor, creditor, validAmount)).getBody();

        debt.setAmount(51);
        var editResponse = sut.editDebt(debt);
        assertEquals(OK, editResponse.getStatusCode());
        assertEquals(debt, editResponse.getBody());
    }

    @Test
    public void editInvalidDebt() {
        // setting an invalid id
        Event event2 = eventServiceImplementation.addEvent(new Event("testEvent")).getBody();
        Participant debtor2 = participantController.addParticipant(new Participant("debtor", event2)).getBody();
        Participant creditor2 = participantController.addParticipant(new Participant("creditor", event2)).getBody();
        double validAmount2 = 50;
        Debt debt2 = new Debt(event2, debtor2, creditor2, validAmount2);

        var editResponse = sut.editDebt(debt2);
        assertEquals(BAD_REQUEST, editResponse.getStatusCode());
    }

    @Test
    public void deleteValidDebt() {
        Event event = eventServiceImplementation.addEvent(new Event("testEvent")).getBody();
        Participant debtor = participantController.addParticipant(new Participant("debtor", event)).getBody();
        Participant creditor = participantController.addParticipant(new Participant("creditor", event)).getBody();

        double validAmount = 50;
        Debt debt = sut.addDebt(new Debt(event, debtor, creditor, validAmount)).getBody();
        var deleteResponse = sut.deleteDebt(debt.getEventId(), debt.getId());
        assertEquals(OK, deleteResponse.getStatusCode());
    }

    @Test
    public void deleteInvalidDebt() {
        Event event = eventServiceImplementation.addEvent(new Event("testEvent")).getBody();
        Participant debtor = participantController.addParticipant(new Participant("debtor", event)).getBody();
        Participant creditor = participantController.addParticipant(new Participant("creditor", event)).getBody();

        double validAmount = 50;
        Debt debt = sut.addDebt(new Debt(event, debtor, creditor, validAmount)).getBody();

        //invalid id
        var deleteResponse = sut.deleteDebt(debt.getEventId(), -5);
        assertEquals(BAD_REQUEST, deleteResponse.getStatusCode());
    }

    @Test
    public void getAll() {
        Event event = eventServiceImplementation.addEvent(new Event("testEvent")).getBody();
        Event event2 = eventServiceImplementation.addEvent(new Event("testEvent")).getBody();
        Participant debtor = participantController.addParticipant(new Participant("debtor", event)).getBody();
        Participant debtor2 = participantController.addParticipant(new Participant("debtor", event2)).getBody();
        Participant creditor = participantController.addParticipant(new Participant("creditor", event)).getBody();
        Participant creditor2 = participantController.addParticipant(new Participant("creditor", event2)).getBody();

        double validAmount = 50;
        Debt debt = sut.addDebt(new Debt(event, debtor, creditor, validAmount)).getBody();
        Debt debt2 = sut.addDebt(new Debt(event2, debtor2, creditor2, validAmount)).getBody();
        var response = sut.getAll();
        assertEquals(OK, response.getStatusCode());
        assertEquals(List.of(debt, debt2), response.getBody());
    }

    @Test
    public void getAllFromEvent() {
        Event event = eventServiceImplementation.addEvent(new Event("testEvent")).getBody();
        Event event2 = eventServiceImplementation.addEvent(new Event("testEvent")).getBody();
        Participant debtor = participantController.addParticipant(new Participant("debtor", event)).getBody();
        Participant debtor2 = participantController.addParticipant(new Participant("debtor", event2)).getBody();
        Participant creditor = participantController.addParticipant(new Participant("creditor", event)).getBody();
        Participant creditor2 = participantController.addParticipant(new Participant("creditor", event2)).getBody();

        double validAmount = 50;
        Debt debt = sut.addDebt(new Debt(event, debtor, creditor, validAmount)).getBody();
        Debt debt2 = sut.addDebt(new Debt(event2, debtor2, creditor2, validAmount)).getBody();
        var response = sut.getAllFromEvent(event.getId());
        assertEquals(OK, response.getStatusCode());
        assertEquals(List.of(debt), response.getBody());
    }

    @Test
    public void getAllFromEventNoDebts() {
        Event event = eventServiceImplementation.addEvent(new Event("testEvent")).getBody();

        var response = sut.getAllFromEvent(event.getId());
        assertEquals(NO_CONTENT, response.getStatusCode());
    }
    @Test
    public void getAllNoDebts() {
        Event event = eventServiceImplementation.addEvent(new Event("testEvent")).getBody();

        var response = sut.getAll();
        assertEquals(NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void getAllFromInvalidEvent() {
        Event event = eventServiceImplementation.addEvent(new Event("testEvent")).getBody();
        event.setId(-1);
        var response = sut.getAllFromEvent(event.getId());
        assertEquals(BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void getDebtByIds() {
        Event event = eventServiceImplementation.addEvent(new Event("testEvent")).getBody();
        Participant debtor = participantController.addParticipant(new Participant("debtor", event)).getBody();
        Participant creditor = participantController.addParticipant(new Participant("creditor", event)).getBody();
        double validAmount = 50;
        Debt debt = sut.addDebt(new Debt(event, debtor, creditor, validAmount)).getBody();
        var response = sut.getDebtByIds(debt.getEventId(), debt.getId());
        assertEquals(OK, response.getStatusCode());
        assertEquals(debt, response.getBody());
    }
    @Test
    public void getDebtByIdsNoSUchDebt() {
        Event event = eventServiceImplementation.addEvent(new Event("testEvent")).getBody();
        Participant debtor = participantController.addParticipant(new Participant("debtor", event)).getBody();
        Participant creditor = participantController.addParticipant(new Participant("creditor", event)).getBody();
        double validAmount = 50;
        Debt debt = sut.addDebt(new Debt(event, debtor, creditor, validAmount)).getBody();
        var response = sut.getDebtByIds(debt.getEventId(), -debt.getId());
        assertEquals(NOT_FOUND, response.getStatusCode());
    }


    @Test
    public void databaseIsUsed() {
        Event event = eventServiceImplementation.addEvent(new Event("testEvent")).getBody();
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
