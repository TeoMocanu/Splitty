package commons.primary_keys;

import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class ParticipantKey implements Serializable {
    @Id
    @Column(name = "event_id")
    public long eventId;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "participant_id")
    public long id;

    public ParticipantKey(long eventId, long id) {
        this.eventId = eventId;
        this.id = id;
    }

}
