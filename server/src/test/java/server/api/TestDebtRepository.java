package server.api;

import commons.Debt;
import commons.Expense;
import commons.primaryKeys.DebtKey;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.DebtRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class TestDebtRepository implements DebtRepository {

    public final List<Debt> debts = new ArrayList<>();
    public final List<String> calledMethods = new ArrayList<>();

    private void call(String name) {
        calledMethods.add(name);
    }

    @Override
    public void flush() {
        throw new NotImplementedException();
    }

    @Override
    public <S extends Debt> S saveAndFlush(S entity) {
        throw new NotImplementedException();
    }

    @Override
    public <S extends Debt> List<S> saveAllAndFlush(Iterable<S> entities) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteAllInBatch(Iterable<Debt> entities) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<DebtKey> debtKeys) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteAllInBatch() {
        throw new NotImplementedException();
    }

    @Override
    public Debt getOne(DebtKey debtKey) {
        throw new NotImplementedException();
    }

    @Override
    public Debt getById(DebtKey debtKey) {
        call("getById");
        for (Debt debt : debts) {
            if (debt.getDebtKey().equals(debtKey)) {
                return debt;
            }
        }
        return null;
    }

    @Override
    public Debt getReferenceById(DebtKey debtKey) {
        throw new NotImplementedException();
    }

    @Override
    public <S extends Debt> Optional<S> findOne(Example<S> example) {
        throw new NotImplementedException();
    }

    @Override
    public <S extends Debt> List<S> findAll(Example<S> example) {
        throw new NotImplementedException();
    }

    @Override
    public <S extends Debt> List<S> findAll(Example<S> example, Sort sort) {
        throw new NotImplementedException();
    }

    @Override
    public <S extends Debt> Page<S> findAll(Example<S> example, Pageable pageable) {
        throw new NotImplementedException();
    }

    @Override
    public <S extends Debt> long count(Example<S> example) {
        throw new NotImplementedException();
    }

    @Override
    public <S extends Debt> boolean exists(Example<S> example) {
        throw new NotImplementedException();
    }

    @Override
    public <S extends Debt, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        throw new NotImplementedException();
    }

    @Override
    public <S extends Debt> S save(S entity) {
        call("save");
        if (entity.getId() == 0) {
            entity.setId(debts.size() + 1);
        }
        //keeping the debts unique
        for (Debt debt : debts) {
            if (debt.getDebtKey().equals(entity.getDebtKey())) {
                debts.remove(debt);
                break;
            }
        }
        debts.add(entity);
        return entity;
    }

    @Override
    public <S extends Debt> List<S> saveAll(Iterable<S> entities) {
        throw new NotImplementedException();
    }

    @Override
    public Optional<Debt> findById(DebtKey debtKey) {
        call("findById");
        for (Debt debt : debts) {
            if (debt.getDebtKey().equals(debtKey)) {
                return Optional.of(debt);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean existsById(DebtKey debtKey) {
        throw new NotImplementedException();
    }

    @Override
    public List<Debt> findAll() {
        call("findAll");
        return debts;
    }

    @Override
    public List<Debt> findAllById(Iterable<DebtKey> debtKeys) {
        throw new NotImplementedException();
    }

    @Override
    public long count() {
        throw new NotImplementedException();
    }

    @Override
    public void deleteById(DebtKey debtKey) {
        call("deleteById");
        for (Debt debt : debts) {
            if (debt.getDebtKey().equals(debtKey)) {
                debts.remove(debt);
                break;
            }
        }
    }

    @Override
    public void delete(Debt entity) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteAllById(Iterable<? extends DebtKey> debtKeys) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteAll(Iterable<? extends Debt> entities) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteAll() {
        throw new NotImplementedException();
    }

    @Override
    public List<Debt> findAll(Sort sort) {
        throw new NotImplementedException();
    }

    @Override
    public Page<Debt> findAll(Pageable pageable) {
        throw new NotImplementedException();
    }

    @Override
    public List<Debt> findByEventId(Long eid) {
        call("findByEventId");
        List<Debt> result = new ArrayList<>();
        for(Debt debt: debts){
            if(debt.getEventId() == eid){
                result.add(debt);
            }
        }
        return result;
    }
}
