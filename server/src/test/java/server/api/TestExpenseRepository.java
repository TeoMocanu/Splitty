package server.api;

import commons.Expense;
import commons.primaryKeys.ExpenseKey;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.ExpenseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class TestExpenseRepository implements ExpenseRepository {
    public final List<Expense> expenses = new ArrayList<>();
    private int nextLong = 0;
    public final List<String> calledMethods = new ArrayList<>();

    private void call(String name) {
        calledMethods.add(name);
    }

    @Override
    public List<Expense> findByEventId(Long eid) {
        call("findByEventId");
        return expenses.stream().filter(e -> e.getEventId() == eid).toList();
    }

    @Override
    public List<Expense> findByEventIdAndPayerId(Long eid, Long pid) {
        return null;
    }

    @Override
    public List<Expense> findByEventIdAndDebtorsId(Long eid, Long pid) {
        return null;
    }

    @Override
    public void flush() {
        throw new NotImplementedException();
    }

    @Override
    public <S extends Expense> S saveAndFlush(S entity) {
        throw new NotImplementedException();
    }

    @Override
    public <S extends Expense> List<S> saveAllAndFlush(Iterable<S> entities) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteAllInBatch(Iterable<Expense> entities) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<ExpenseKey> expenseKeys) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteAllInBatch() {
        throw new NotImplementedException();
    }

    @Override
    public Expense getOne(ExpenseKey expenseKey) {
        throw new NotImplementedException();
    }

    @Override
    public Expense getById(ExpenseKey expenseKey) {
        call("getById");
        return findById(expenseKey).orElse(null);
    }

    @Override
    public Expense getReferenceById(ExpenseKey expenseKey) {
        throw new NotImplementedException();
    }

    @Override
    public <S extends Expense> Optional<S> findOne(Example<S> example) {
        throw new NotImplementedException();
    }

    @Override
    public <S extends Expense> List<S> findAll(Example<S> example) {
        throw new NotImplementedException();
    }

    @Override
    public <S extends Expense> List<S> findAll(Example<S> example, Sort sort) {
        throw new NotImplementedException();
    }

    @Override
    public <S extends Expense> Page<S> findAll(Example<S> example, Pageable pageable) {
        throw new NotImplementedException();
    }

    @Override
    public <S extends Expense> long count(Example<S> example) {
        throw new NotImplementedException();
    }

    @Override
    public <S extends Expense> boolean exists(Example<S> example) {
        throw new NotImplementedException();
    }

    @Override
    public <S extends Expense, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        throw new NotImplementedException();
    }

    @Override
    public <S extends Expense> S save(S entity) {
        call("save");
        if(entity.getId() == 0)
            entity.setId(++nextLong);
        for(Expense e: expenses)
            if(entity.getExpenseKey().equals(e.getExpenseKey())){
                expenses.remove(e);
                break;
            }
        expenses.add(entity);
        return entity;
    }

    @Override
    public <S extends Expense> List<S> saveAll(Iterable<S> entities) {
        throw new NotImplementedException();
    }

    @Override
    public Optional<Expense> findById(ExpenseKey expenseKey) {
        call("findById");
        return expenses.stream().filter(e -> e.getExpenseKey().equals(expenseKey)).findFirst();
    }

    @Override
    public boolean existsById(ExpenseKey expenseKey) {
        throw new NotImplementedException();
    }

    @Override
    public List<Expense> findAll() {
        call("findAll");
        return expenses;
    }

    @Override
    public List<Expense> findAllById(Iterable<ExpenseKey> expenseKeys) {
        throw new NotImplementedException();
    }

    @Override
    public long count() {
        throw new NotImplementedException();
    }

    @Override
    public void deleteById(ExpenseKey expenseKey) {
        call("deleteById");
        for(Expense e: expenses)
            if(e.getExpenseKey().equals(expenseKey))
                expenses.remove(e);
    }

    @Override
    public void delete(Expense entity) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteAllById(Iterable<? extends ExpenseKey> expenseKeys) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteAll(Iterable<? extends Expense> entities) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteAll() {
        throw new NotImplementedException();
    }

    @Override
    public List<Expense> findAll(Sort sort) {
        throw new NotImplementedException();
    }

    @Override
    public Page<Expense> findAll(Pageable pageable) {
        throw new NotImplementedException();
    }
}