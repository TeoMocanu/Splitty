package server.implementations;

import commons.Participant;
import commons.primaryKeys.ParticipantKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import server.database.ParticipantRepository;
import server.services.ParticipantService;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipantServiceImplementation implements ParticipantService {

    private final ParticipantRepository participantRepository;

    @Autowired
    public ParticipantServiceImplementation(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    @Override
    public List<Participant> getAllParticipants() {
        return participantRepository.findAll();
    }

    @Override
    public List<Participant> getAllParticipantsFromEvent(Long eid) {
        return participantRepository.findByEventId(eid);
    }

    @Override
    public ResponseEntity<Participant> getParticipantById(Long eid, Long id) {
        Optional<Participant> participantOptional = participantRepository.findById(new ParticipantKey(eid, id));
        return participantOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Participant> addParticipant(Participant participant) {
        if (participant == null) {
            return ResponseEntity.badRequest().build();
        }
        Participant added = participantRepository.save(participant);
        return ResponseEntity.ok(added);
    }

    @Override
    public ResponseEntity<Participant> editParticipant(Participant participant) {
        if (participant == null) {
            return ResponseEntity.badRequest().build();
        }
        if (!participantRepository.existsById(new ParticipantKey(participant.getEventId(), participant.getId()))) {
            return ResponseEntity.notFound().build();
        }
        Participant updatedParticipant = participantRepository.save(participant);
        return ResponseEntity.ok(updatedParticipant);
    }

    @Override
    public ResponseEntity<Void> deleteParticipant(Long eid, Long id) {
        if (!participantRepository.existsById(new ParticipantKey(eid, id))) {
            return ResponseEntity.notFound().build();
        }
        participantRepository.deleteById(new ParticipantKey(eid, id));
        return ResponseEntity.noContent().build();
    }
}
