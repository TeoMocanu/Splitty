package server.api;

import commons.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import server.database.ParticipantRepository;
import commons.Participant;
import commons.primaryKeys.ParticipantKey;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ParticipantControllerTest {

    @Mock
    private ParticipantRepository participantRepository;

    @InjectMocks
    private ParticipantController participantController;

    private Event event1;
    private Event event2;
    private Participant participant1;
    private Participant participant2;
    private List<Participant> participantList;

    @BeforeEach
    public void setUp() {
        event1 = new Event("event1");
        event2 = new Event("event2");
        participant1 = new Participant(event1,"Mario","mario@gmail.com","123456789","987654321");
        participant2 = new Participant(event2,"Mihai","mihai@gmail.com","312654987","789456123");
        participantList = Arrays.asList(participant1, participant2);
    }

    @Test
    void getAllParticipantsTest() {
        when(participantRepository.findAll()).thenReturn(participantList);
        List<Participant> results = participantController.getAllParticipants();

        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals(participantList, results);
        verify(participantRepository).findAll();
    }

    @Test
    void getAllParticipantsFromEventTest() {
        Long eventId = 1L;
        when(participantRepository.findByEventId(eventId)).thenReturn(participantList);
        List<Participant> results = participantController.getAllParticipantsFromEvent(eventId);

        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals(participantList, results);
        verify(participantRepository).findByEventId(eventId);
    }

    @Test
    void getParticipantByIdTest() {
        ParticipantKey key = new ParticipantKey(participant1.getEventId(), participant1.getId());
        when(participantRepository.findById(key)).thenReturn(Optional.of(participant1));
        ResponseEntity<Participant> response = participantController.getParticipantById(participant1.getEventId(), participant1.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(participant1, response.getBody());
        verify(participantRepository).findById(key);
    }

    @Test
    void addParticipantTest() {
        when(participantRepository.save(participant1)).thenReturn(participant1);
        ResponseEntity<Participant> response = participantController.addParticipant(participant1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(participant1, response.getBody());
        verify(participantRepository).save(participant1);
    }

    @Test
    void addParticipant_WithNull_ShouldReturnBadRequest() {
        ResponseEntity<Participant> response = participantController.addParticipant(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(participantRepository, never()).save(any(Participant.class));
    }

    @Test
    void updateParticipantTest() {
        ParticipantKey key = new ParticipantKey(participant1.getEventId(), participant1.getId());
        when(participantRepository.existsById(key)).thenReturn(true);
        when(participantRepository.save(participant1)).thenReturn(participant1);
        ResponseEntity<Participant> response = participantController.updateParticipant(participant1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(participant1, response.getBody());
        verify(participantRepository).save(participant1);
    }

}
