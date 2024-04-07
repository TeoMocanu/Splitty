package server.services;

import commons.Participant;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ParticipantService {

    List<Participant> getAllParticipants();

    List<Participant> getAllParticipantsFromEvent(Long eid);

    ResponseEntity<Participant> getParticipantById(Long eid, Long id);

    ResponseEntity<Participant> addParticipant(Participant participant);

    ResponseEntity<Participant> editParticipant(Participant participant);

    ResponseEntity<Void> deleteParticipant(Long eid, Long id);
}
