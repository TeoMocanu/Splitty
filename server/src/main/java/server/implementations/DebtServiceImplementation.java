package server.implementations;

import commons.Debt;
import commons.primaryKeys.DebtKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import server.database.DebtRepository;
import server.services.DebtService;

import java.util.List;

@Service
public class DebtServiceImplementation implements DebtService {

    @Autowired
    private final DebtRepository repo;
    private final EventServiceImplementation eventServiceImplementation;

    public DebtServiceImplementation(DebtRepository repo, EventServiceImplementation eventServiceImplementation) {
        this.repo = repo;
        this.eventServiceImplementation = eventServiceImplementation;
    }

    @Override
    public ResponseEntity<List<Debt>> getAllFromEvent(long idEvent) {
        if(idEvent <= 0 || eventServiceImplementation.getEventById(idEvent) == null)
            return ResponseEntity.badRequest().build();
        List<Debt> debts = repo.findAllByEventId(idEvent);
        if (debts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(debts);
    }

    @Override
    public ResponseEntity<Debt> addDebt(Debt debt) {
        long idEvent = debt.getEventId();
        if(idEvent <= 0 || eventServiceImplementation.getEventById(idEvent) == null)
            return ResponseEntity.badRequest().build();
        Debt saved = repo.save(debt);
        return ResponseEntity.ok(saved);
    }

    @Override
    public ResponseEntity<List<Debt>> getAll() {
        List<Debt> debts = repo.findAll();
        if (debts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(debts);
    }

    @Override
    public ResponseEntity<Debt> getDebtByIds(long eid, long id) {
        DebtKey key = new DebtKey(eid, id);
        if (repo.findById(key).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(repo.findById(key).get());
    }

    @Override
    public ResponseEntity<Debt> deleteDebt(long eid, long id) {
        DebtKey key = new DebtKey(eid, id);
        if (repo.findById(key).isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        repo.deleteById(key);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Debt> editDebt(Debt debt) {
        DebtKey key = debt.getDebtKey();
        //Debt debttt = repo.findById(key).orElse(null);
        if (repo.findById(key).isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Debt updatedDebt = repo.save(debt);
        return ResponseEntity.ok(updatedDebt);
    }
}
