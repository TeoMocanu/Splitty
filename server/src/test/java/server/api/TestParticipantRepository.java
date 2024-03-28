package server.api;

import commons.Participant;
import commons.primaryKeys.ParticipantKey;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;
import server.database.ParticipantRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class TestParticipantRepository implements ParticipantRepository {

    public final List<Participant> participants = new ArrayList<>();
    private int nextLong = 0;
    public final List<String> calledMethods = new ArrayList<>();

    private void call(String name) {
        calledMethods.add(name);
    }
    @Override
    public void flush() {
        throw new NotImplementedException();
    }

    @Override
    public <S extends Participant> S saveAndFlush(S entity) {
        throw new NotImplementedException();
    }

    @Override
    public <S extends Participant> List<S> saveAllAndFlush(Iterable<S> entities) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteAllInBatch(Iterable<Participant> entities) {
        throw new NotImplementedException();

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<ParticipantKey> participantKeys) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteAllInBatch() {
        throw new NotImplementedException();
    }

    @Override
    public Participant getOne(ParticipantKey participantKey) {
        throw new NotImplementedException();
    }

    @Override
    public Participant getById(ParticipantKey participantKey) {
        throw new NotImplementedException();
    }

    @Override
    public Participant getReferenceById(ParticipantKey participantKey) {
        throw new NotImplementedException();
    }

    @Override
    public <S extends Participant> Optional<S> findOne(Example<S> example) {
        throw new NotImplementedException();
    }

    @Override
    public <S extends Participant> List<S> findAll(Example<S> example) {
        throw new NotImplementedException();
    }

    @Override
    public <S extends Participant> List<S> findAll(Example<S> example, Sort sort) {
        throw new NotImplementedException();
    }

    @Override
    public <S extends Participant> Page<S> findAll(Example<S> example, Pageable pageable) {
        throw new NotImplementedException();
    }

    @Override
    public <S extends Participant> long count(Example<S> example) {
        throw new NotImplementedException();
    }

    @Override
    public <S extends Participant> boolean exists(Example<S> example) {
        throw new NotImplementedException();
    }

    @Override
    public <S extends Participant, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        throw new NotImplementedException();
    }

    @Override
    public <S extends Participant> S save(S entity) {
        call("save");
        if(entity.getId() == 0)
            entity.setId(++nextLong);
        for(Participant p: participants)
            if(p.getParticipantKey().equals(entity.getParticipantKey())){
                participants.remove(p);
                break;
            }
        participants.add(entity);
        return entity;
    }

    @Override
    public <S extends Participant> List<S> saveAll(Iterable<S> entities) {
        throw new NotImplementedException();
    }

    @Override
    public Optional<Participant> findById(ParticipantKey participantKey) {
        return participants.stream().filter(p -> p.getParticipantKey().equals(participantKey)).findFirst();
    }

    @Override
    public boolean existsById(ParticipantKey participantKey) {
        throw new NotImplementedException();
    }

    @Override
    public List<Participant> findAll() {
        call("findAll");
        return participants;
    }

    @Override
    public List<Participant> findAllById(Iterable<ParticipantKey> participantKeys) {
        throw new NotImplementedException();
    }

    @Override
    public long count() {
        throw new NotImplementedException();
    }

    @Override
    public void deleteById(ParticipantKey participantKey) {
        call("delete");
        for(Participant p: participants)
            if(p.getParticipantKey().equals(participantKey)){
                participants.remove(p);
                break;
            }
    }

    @Override
    public void delete(Participant entity) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteAllById(Iterable<? extends ParticipantKey> participantKeys) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteAll(Iterable<? extends Participant> entities) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteAll() {
        throw new NotImplementedException();
    }

    @Override
    public List<Participant> findAll(Sort sort) {
        throw new NotImplementedException();
    }

    @Override
    public Page<Participant> findAll(Pageable pageable) {
        throw new NotImplementedException();
    }

    @Override
    public List<Participant> findByEventId(Long eid) {
        call("findByEventId");
        return participants.stream().filter(p -> p.getEventId() == eid).toList();
    }
}
