package server.services;

import commons.Debt;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DebtService {

    ResponseEntity<List<Debt>> getAllFromEvent(long idEvent);

    ResponseEntity<Debt> addDebt(Debt debt);

    ResponseEntity<List<Debt>> getAll();

    ResponseEntity<Debt> getDebtByIds(long eid, long id);

    ResponseEntity<Debt> deleteDebt(long eid, long id);

    ResponseEntity<Debt> editDebt(Debt debt);

    ResponseEntity<List<Debt>> getAllFromParticipant(long eid, long pid);
}
