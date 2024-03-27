package server.api;

import commons.Debt;
import commons.Event;
import commons.Participant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.database.EventRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.OK;

public class DebtControllerTest {

    public int nextInt;
    private TestDebtRepository repo;
    private EventRepository eventRepository = new TestEventRepository();
    private EventController eventController = new EventController(eventRepository);

    private DebtController sut;

    @BeforeEach
    public void setup() {
        repo = new TestDebtRepository();
        sut = new DebtController(repo, eventController);
    }

    @Test
    public void addDebtWithValidAmount() {
        long validId = 123;
        Event event = new Event("testEvent");
        Participant debtor = new Participant("debtor", event);
        Participant creditor = new Participant("creditor", event);
        double validAmount = 50;
        var actual = sut.addDebt(validId, getDebt(event, debtor, creditor, validAmount));
        assertEquals(OK, actual.getStatusCode());
    }


    @Test
    public void databaseIsUsed() {
        long validId = 123;
        Event event = new Event("testEvent");
        Participant debtor = new Participant("debtor", event);
        Participant creditor = new Participant("creditor", event);
        double amount = 10;
        sut.addDebt(validId, getDebt(event, debtor, creditor, amount));
        repo.calledMethods.contains("save");
    }

    private static Debt getDebt(Event event, Participant debtor, Participant creditor, double amount) {
        return new Debt(event, debtor, creditor, amount);
    }

}
