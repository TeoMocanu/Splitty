package server.api;

import commons.Participant;
import commons.primaryKeys.ParticipantKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.ParticipantRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/participants")
public class ParticipantController {

    private final ParticipantRepository participantRepository;

    @Autowired
    public ParticipantController(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    @GetMapping("/getAllParticipants")
    public List<Participant> getAllParticipants() {
        return participantRepository.findAll();
    }

    @GetMapping("/getAllParticipantsFromEvent/{eid}")
    public List<Participant> getAllParticipantsFromEvent(@PathVariable("eid") Long eid) {
        return participantRepository.findByEventId(eid);
    }

    @GetMapping("/getParticipantById/{eid}/{id}")
    public ResponseEntity<Participant> getParticipantById(@PathVariable("eid") Long eid, @PathVariable("id") Long id) {
        Optional<Participant> participantOptional = participantRepository.findById(new ParticipantKey(eid, id));
        return participantOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/addParticipant")
    public ResponseEntity<Participant> addParticipant(@RequestBody Participant participant) {
        if (participant == null) {
            return ResponseEntity.badRequest().build();
        }
        Participant added = participantRepository.save(participant);
        return ResponseEntity.ok(added);
    }

    @PutMapping("/editParticipant")
    public ResponseEntity<Participant> editParticipant(@RequestBody Participant participant) {
        if (participant == null) {
            return ResponseEntity.badRequest().build();
        }
        if (!participantRepository.existsById(new ParticipantKey(participant.getEventId(), participant.getId()))) {
            return ResponseEntity.notFound().build();
        }
        Participant updatedParticipant = participantRepository.save(participant);
        return ResponseEntity.ok(updatedParticipant);
    }

    @DeleteMapping("/deleteParticipant/{eid}/{id}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable("eid") Long eid, @PathVariable("id") Long id) {
        if (!participantRepository.existsById(new ParticipantKey(eid, id))) {
            return ResponseEntity.notFound().build();
        }
        participantRepository.deleteById(new ParticipantKey(eid, id));
        return ResponseEntity.noContent().build();
    }
}